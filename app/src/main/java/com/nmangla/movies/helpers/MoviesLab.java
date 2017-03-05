package com.nmangla.movies.helpers;

import com.nmangla.movies.models.MovieListItem;
import com.nmangla.movies.sync.response.MovieResponse;

import java.util.List;

// Singleton object to store data to be used between activities
public class MoviesLab {

    private static MoviesLab sMoviesLab;

    public static MoviesLab get() {
        if (sMoviesLab == null) {
            sMoviesLab = new MoviesLab();
        }
        return sMoviesLab;
    }

    private MoviesLab() {
    }

    // Current list of movies
    private List<MovieListItem> mMovies;

    // Selected movie for which user wants to see information
    private MovieResponse mSelectedMovie;

    public List<MovieListItem> getMovies() {
        return mMovies;
    }

    public void setMovies(List<MovieListItem> movies) {
        mMovies = movies;
    }

    public MovieResponse getSelectedMovie() {
        return mSelectedMovie;
    }

    public void setSelectedMovie(MovieResponse selectedMovie) {
        mSelectedMovie = selectedMovie;
    }

    public void resetSingletonObject() {
        mMovies = null;
        mSelectedMovie = null;
    }
}
