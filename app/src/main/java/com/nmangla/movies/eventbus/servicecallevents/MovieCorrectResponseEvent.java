package com.nmangla.movies.eventbus.servicecallevents;

import com.nmangla.movies.sync.response.MovieResponse;

// Event for success response from service call to get movie info
public class MovieCorrectResponseEvent {
    private MovieResponse mMovieResponse;

    public MovieCorrectResponseEvent(MovieResponse movieResponse) {
        mMovieResponse = movieResponse;
    }

    public MovieResponse getMovieResponse() {
        return mMovieResponse;
    }

    public void setMovieResponse(MovieResponse movieResponse) {
        mMovieResponse = movieResponse;
    }
}
