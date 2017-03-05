package com.nmangla.movies.sync;

import com.nmangla.movies.sync.response.MovieResponse;
import com.nmangla.movies.sync.response.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {

    // Get movie listing
    @GET("discover/movie")
    Call<MoviesResponse> getMovies(@Query("api_key") String apiKey,
                                   @Query("primary_release_date.lte") String primaryReleaseDate,
                                   @Query("sort_by") String sortBy,
                                   @Query("page") Integer page);

    // Get information about a particular movie
    @GET("movie/{movie_id}")
    Call<MovieResponse> getMovie(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
