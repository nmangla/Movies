package com.nmangla.movies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.nmangla.movies.R;
import com.nmangla.movies.eventbus.servicecallevents.MovieCorrectResponseEvent;
import com.nmangla.movies.eventbus.servicecallevents.MovieErrorResponseEvent;
import com.nmangla.movies.fragments.ErrorMessageFragment;
import com.nmangla.movies.fragments.MovieDescriptionFragment;
import com.nmangla.movies.fragments.ProgressBarFragment;
import com.nmangla.movies.helpers.MoviesLab;
import com.nmangla.movies.helpers.MyExceptionHandler;
import com.nmangla.movies.helpers.Util;
import com.nmangla.movies.models.MovieListItem;
import com.nmangla.movies.sync.MoviesService;
import com.nmangla.movies.sync.request.MovieRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MovieDescriptionActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE_POS = "movie.pos";

    private MovieListItem mMovieListItem;
    private int mMoviePos;
    private ErrorMessageFragment.OnItemClickListener mListener;
    private boolean mIsActivityInBackground;
    private Fragment mFragment;

    // Passing index of movie for retrieval
    public static Intent newIntent(Context context, int moviePos) {
        Intent intent = new Intent(context, MovieDescriptionActivity.class);
        intent.putExtra(EXTRA_MOVIE_POS, moviePos);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        setContentView(R.layout.fragment_activity);
        EventBus.getDefault().register(this);

        mMoviePos = (int) getIntent().getSerializableExtra(EXTRA_MOVIE_POS);
        mMovieListItem = MoviesLab.get().getMovies().get(mMoviePos);

        // Used to retry service calls
        mListener = new ErrorMessageFragment.OnItemClickListener() {
            @Override
            public void onItemClick() {
                makeServiceCall();
            }
        };

        makeServiceCall();
    }

    private void makeServiceCall() {

        // Check internet connectivity
        if (Util.isOnline(this)) {
            switchFragment(getProgressBarFragment());

            // Create request to get movie info
            MovieRequest request = new MovieRequest(mMovieListItem.getId());
            MoviesService.get().getMovie(request);
        } else {
            switchFragment(getErrorMessageFragment(getResources().getString(R.string.no_internet_connection)));
        }
    }

    // Successful response from service call
    @Subscribe
    public void onMovieCorrectResponseEvent(MovieCorrectResponseEvent event) {
        MoviesLab.get().setSelectedMovie(event.getMovieResponse());
        switchFragment(getMovieDescriptionFragment());
    }

    // Error response from service call
    @Subscribe
    public void onMovieErrorResponseEvent(MovieErrorResponseEvent event) {
        switchFragment(getErrorMessageFragment(getResources().getString(R.string.something_went_wrong)));

    }

    // Returns fragment to show error for service calls
    private ErrorMessageFragment getErrorMessageFragment(String message) {
        ErrorMessageFragment fragment = ErrorMessageFragment.newInstance(message, mMovieListItem.getTitle());
        fragment.setOnItemClickListener(mListener);
        return fragment;
    }

    // Returns main fragment
    private MovieDescriptionFragment getMovieDescriptionFragment() {
        return MovieDescriptionFragment.newInstance(mMoviePos);
    }

    // Returns fragment to show progress bar
    private ProgressBarFragment getProgressBarFragment() {
        return ProgressBarFragment.newInstance(mMovieListItem.getTitle());
    }

    // Switch fragments
    private void switchFragment(Fragment fragment) {

        // If activity in background, fragment is held until activity is back on foreground
        if (mIsActivityInBackground) {
            mFragment = fragment;
        } else {
            FragmentManager fm = getSupportFragmentManager();
            Fragment currentFragment = fm.findFragmentById(R.id.fragment_container);

            if (currentFragment != null) {
                fm.beginTransaction().remove(currentFragment).commit();
            }
            fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsActivityInBackground = false;
        if (mFragment != null) {
            switchFragment(mFragment);
            mFragment = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsActivityInBackground = true;
    }
}
