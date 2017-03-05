package com.nmangla.movies.models;

import com.google.gson.annotations.SerializedName;

// Model to hold genre information
public class Genre {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
