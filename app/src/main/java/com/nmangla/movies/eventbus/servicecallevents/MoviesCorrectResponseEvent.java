package com.nmangla.movies.eventbus.servicecallevents;

import com.nmangla.movies.sync.response.MoviesResponse;

// Event for success response from service call to get list of movies
public class MoviesCorrectResponseEvent {
    private MoviesResponse mMoviesResponse;

    public MoviesCorrectResponseEvent(MoviesResponse moviesResponse) {
        mMoviesResponse = moviesResponse;
    }

    public MoviesResponse getMoviesResponse() {
        return mMoviesResponse;
    }

    public void setMoviesResponse(MoviesResponse moviesResponse) {
        mMoviesResponse = moviesResponse;
    }
}
