package com.nmangla.movies.models;

import com.google.gson.annotations.SerializedName;

// Model to hold information about a movie in movie listing
public class MovieListItem {

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("popularity")
    private float mPopularity;

    @SerializedName("id")
    private int mId;

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public float getPopularity() {
        return mPopularity;
    }

    public void setPopularity(float popularity) {
        mPopularity = popularity;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
