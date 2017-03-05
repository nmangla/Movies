package com.nmangla.movies.sync;

import android.util.Log;

import com.nmangla.movies.eventbus.servicecallevents.MovieCorrectResponseEvent;
import com.nmangla.movies.eventbus.servicecallevents.MovieErrorResponseEvent;
import com.nmangla.movies.eventbus.servicecallevents.MoviesCorrectResponseEvent;
import com.nmangla.movies.eventbus.servicecallevents.MoviesErrorResponseEvent;
import com.nmangla.movies.helpers.Util;
import com.nmangla.movies.sync.request.MovieRequest;
import com.nmangla.movies.sync.request.MoviesRequest;
import com.nmangla.movies.sync.response.MovieResponse;
import com.nmangla.movies.sync.response.MoviesResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Singleton to help with making all service calls
public class MoviesService {

    private static MoviesService sMoviesService;
    private final MoviesApi service;

    public static MoviesService get() {
        if (sMoviesService == null) {
            sMoviesService = new MoviesService();
        }
        return sMoviesService;
    }

    private MoviesService() {

        // Initializing Retrofit client
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(logging);

        OkHttpClient client = builder.build();

        // Base url for all service calls
        String url = "http://api.themoviedb.org/3/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        service = retrofit.create(MoviesApi.class);
    }

    // This value is used to discredit all previous service calls and thrown event for only the latest call
    private static String mGetMoviesId;

    public void getMovies(MoviesRequest request) {
        mGetMoviesId = UUID.randomUUID().toString();
        final String currentId = mGetMoviesId;

        Call<MoviesResponse> call = service.getMovies(request.getApiKey(), request.getPrimaryReleaseDate(),
                request.getSortBy(), request.getPage());

        call.enqueue((new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if(currentId.equals(mGetMoviesId)) {
                    // Successful response
                    if (response.isSuccessful()) {
                        EventBus.getDefault().post(new MoviesCorrectResponseEvent(response.body()));
                    } else {
                        EventBus.getDefault().post(new MoviesErrorResponseEvent());
                    }
                }
            }

            // Failed response
            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                if(currentId.equals(mGetMoviesId)) {
                    EventBus.getDefault().post(new MoviesErrorResponseEvent());

                    // Log error while making service call
                    Log.d(Util.TAG, "(FATAL) Failed response getMovies + " + "\n" + Arrays.toString(t.getStackTrace()) + "\n" + t.toString() + "\n" + t.getMessage());
                }
            }
        }));
    }

    // This value is used to discredit all previous service calls and thrown event for only the latest call
    private static String mGetMovieId;

    public void getMovie(MovieRequest request) {
        mGetMovieId = UUID.randomUUID().toString();
        final String currentId = mGetMovieId;

        Call<MovieResponse> call = service.getMovie(request.getMovieId(), request.getApiKey());

        call.enqueue((new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(currentId.equals(mGetMovieId)) {
                    // Successful response
                    if (response.isSuccessful()) {
                        EventBus.getDefault().post(new MovieCorrectResponseEvent(response.body()));
                    } else {
                        EventBus.getDefault().post(new MovieErrorResponseEvent());
                    }
                }
            }

            // Failed response
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                if(currentId.equals(mGetMovieId)) {
                    EventBus.getDefault().post(new MovieErrorResponseEvent());

                    // Log error while making service call
                    Log.d(Util.TAG, "(FATAL) Failed response getMovie + " + "\n" + Arrays.toString(t.getStackTrace()) + "\n" + t.toString() + "\n" + t.getMessage());
                }
            }
        }));
    }
}
