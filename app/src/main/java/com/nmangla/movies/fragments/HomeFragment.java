package com.nmangla.movies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nmangla.movies.R;
import com.nmangla.movies.activities.MovieDescriptionActivity;
import com.nmangla.movies.adapters.MovieAdapter;
import com.nmangla.movies.eventbus.servicecallevents.MoviesCorrectResponseEvent;
import com.nmangla.movies.eventbus.servicecallevents.MoviesErrorResponseEvent;
import com.nmangla.movies.helpers.EndlessRecyclerViewScrollListener;
import com.nmangla.movies.helpers.MoviesLab;
import com.nmangla.movies.helpers.Util;
import com.nmangla.movies.models.MovieListItem;
import com.nmangla.movies.sync.MoviesService;
import com.nmangla.movies.sync.request.MoviesRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<MovieListItem> mMovies;
    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private int mLastAdapterSize;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mRefreshingData = false;
    private EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;
    private static OnItemClickListener mOnItemClickListener;

    // If no internet connection or error during service call, activity pulls up a new fragment
    public interface OnItemClickListener {
        void onItemClick();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Registering to accept events
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        // If user swipes down, refresh data
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mMovies != null && !mMovies.isEmpty()) {
                    mMovies = new ArrayList<>();
                    MoviesLab.get().setMovies(mMovies);

                    mRefreshingData = true;
                    mEndlessRecyclerViewScrollListener.resetState();
                    makeServiceCall(1);
                }
            }
        });

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);

        // If data already loaded, no need to make service call again
        if(MoviesLab.get().getMovies() == null || MoviesLab.get().getMovies().isEmpty()) {
            mMovies = new ArrayList<>();
            MoviesLab.get().setMovies(mMovies);

            makeServiceCall(1);
        } else {
            mMovies = MoviesLab.get().getMovies();
            setupRecyclerView();
        }
        return view;
    }

    private void makeServiceCall(int page) {

        // Check internet connectivity
        if (Util.isOnline(getActivity())) {

            // Don't need to display progress bar if refresh through swipe down
            if(!mRefreshingData) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            // Create request to get movie listing
            MoviesRequest request = new MoviesRequest(page);
            MoviesService.get().getMovies(request);
        } else {
            if(mMovies == null || mMovies.isEmpty()) {

                // No internet connection, show different fragment
                mOnItemClickListener.onItemClick();
            } else {

                // Already some data is present, don't need to show a different fragment. Show message in snack bar
                Util.showSnackbar(getView(), getString(R.string.no_internet_connection));
            }
        }
    }

    // Setup recycler view
    private void setupRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Scroll listener to enable endless scrolling
        mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mLastAdapterSize = mAdapter.getItemCount();
                makeServiceCall(page);
            }
        };

        mRecyclerView.addOnScrollListener(mEndlessRecyclerViewScrollListener);

        mAdapter = new MovieAdapter(getActivity(), mMovies);
        mAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                // Open activity to show information about a particular movie
                Intent intent = MovieDescriptionActivity.newIntent(getActivity(), position);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    // Correct response from service call to get movie listing
    @Subscribe
    public void onMoviesCorrectResponseEvent(MoviesCorrectResponseEvent event) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mSwipeRefreshLayout.setRefreshing(false);

        if(mMovies == null || mMovies.isEmpty()) {
            if(mRefreshingData) {

                // No need to setup recycler view again if swipe to refresh
                mMovies.addAll(event.getMoviesResponse().getResults());
                mAdapter.setMovies(mMovies);
                mAdapter.notifyDataSetChanged();
                mRefreshingData = false;
            } else {
                mMovies.addAll(event.getMoviesResponse().getResults());
                setupRecyclerView();
            }
        } else {
            // Scroll to bottom, add more items to movie listing
            mMovies.addAll(event.getMoviesResponse().getResults());
            mAdapter.notifyItemRangeInserted(mLastAdapterSize, mMovies.size()-1);
        }
    }

    // Error response from service call to get movie listing
    @Subscribe
    public void onMoviesErrorResponseEvent(MoviesErrorResponseEvent event) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mSwipeRefreshLayout.setRefreshing(false);

        if(mMovies == null || mMovies.isEmpty()) {
            mOnItemClickListener.onItemClick();
        } else {
            Util.showSnackbar(getView(), getString(R.string.something_went_wrong));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Unregister from eventbus to no longer receive events
        EventBus.getDefault().unregister(this);
    }
}
