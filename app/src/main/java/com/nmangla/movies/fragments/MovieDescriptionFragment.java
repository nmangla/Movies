package com.nmangla.movies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nmangla.movies.R;
import com.nmangla.movies.activities.BookActivity;
import com.nmangla.movies.adapters.GenreAdapter;
import com.nmangla.movies.helpers.MoviesLab;
import com.nmangla.movies.helpers.Util;
import com.nmangla.movies.models.MovieListItem;
import com.nmangla.movies.sync.response.MovieResponse;
import com.squareup.picasso.Picasso;

// Fragment to display information about a particular movie
public class MovieDescriptionFragment extends Fragment {

    // Position in List of movie to be displayed
    private static final String ARG_MOVIE_POS = "moviePos";

    private MovieResponse mMovieResponse;

    private RecyclerView mRecyclerView;

    // Creating instance of the fragment
    public static MovieDescriptionFragment newInstance(int moviePos) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_POS, moviePos);
        MovieDescriptionFragment fragment = new MovieDescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_description, container, false);

        int moviePos = (int) getArguments().getSerializable(ARG_MOVIE_POS);

        // Both these objects contain all information we need to display in this fragment
        MovieListItem movieListItem = MoviesLab.get().getMovies().get(moviePos);
        mMovieResponse = MoviesLab.get().getSelectedMovie();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(movieListItem.getTitle());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initializing views
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView titleTextView = (TextView) view.findViewById(R.id.title);
        TextView popularityTextView = (TextView) view.findViewById(R.id.popularity);
        TextView languageTextView = (TextView) view.findViewById(R.id.language);
        TextView durationTextView = (TextView) view.findViewById(R.id.duration);
        TextView genresTextView = (TextView) view.findViewById(R.id.genres_label);
        TextView synopsisTextView = (TextView) view.findViewById(R.id.synopsis);
        Button bookButton = (Button) view.findViewById(R.id.book_button);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        // Loading poster image and putting placeholder if image takes time to load
        if(movieListItem.getPosterPath() != null && !movieListItem.getPosterPath().isEmpty()) {
            Picasso.with(getActivity()).load(Util.getBigImage(movieListItem.getPosterPath())).
                    placeholder(R.drawable.placeholder1).into(imageView);
        } else {
            Picasso.with(getActivity()).load(R.drawable.placeholder1).into(imageView);
        }

        // Showing relevant information
        titleTextView.setText(movieListItem.getTitle());
        popularityTextView.setText("Popularity: " + movieListItem.getPopularity());
        languageTextView.setText("Language: " + mMovieResponse.getLanguage());
        durationTextView.setText("Duration: " + mMovieResponse.getDuration() + " minutes");
        genresTextView.setText("List of Genres:");
        synopsisTextView.setText(mMovieResponse.getOverview());

        // Hide label if no genre information is available
        if(mMovieResponse.getGenres() == null || mMovieResponse.getGenres().isEmpty()) {
            genresTextView.setVisibility(View.GONE);
        }

        // Clicking on Book button opens new actitivy which contains the required webview
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookActivity.class);
                startActivity(intent);
            }
        });

        setupRecyclerView();

        return view;
    }

    // Setting up recycler view to display list of genres this movie falls under
    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GenreAdapter adapter = new GenreAdapter(getActivity(), mMovieResponse.getGenres());

        // Using fixedSize to optimize recycler view
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }
}
