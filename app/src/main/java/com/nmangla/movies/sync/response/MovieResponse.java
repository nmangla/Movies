package com.nmangla.movies.sync.response;

import com.google.gson.annotations.SerializedName;
import com.nmangla.movies.models.Genre;

import java.util.List;

// Response expected for information about a particular movie
public class MovieResponse {

    @SerializedName("overview")
    private String mOverview;

    @SerializedName("original_language")
    private String mLanguage;

    @SerializedName("runtime")
    private String mDuration;

    @SerializedName("genres")
    private List<Genre> mGenres;

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }
}
