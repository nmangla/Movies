package com.nmangla.movies.sync.request;

import com.nmangla.movies.helpers.Util;

// Request to retrieve information about a particular movie
public class MovieRequest {

    private int mMovieId;

    private String mApiKey;

    public MovieRequest(int movieId) {
        mMovieId = movieId;
        mApiKey = Util.API_KEY;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public void setApiKey(String apiKey) {
        mApiKey = apiKey;
    }
}
