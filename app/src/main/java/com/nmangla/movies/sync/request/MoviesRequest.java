package com.nmangla.movies.sync.request;

import com.nmangla.movies.helpers.Util;

// Request to retrieve information about all movies
public class MoviesRequest {

    private String mApiKey;

    private String mPrimaryReleaseDate;

    private String mSortBy;

    private int mPage;

    public MoviesRequest(int page) {
        mApiKey = Util.API_KEY;
        mPrimaryReleaseDate = Util.PRIMARY_RELEASE_DATE;
        mSortBy = Util.SORT_BY;
        mPage = page;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public void setApiKey(String apiKey) {
        mApiKey = apiKey;
    }

    public String getPrimaryReleaseDate() {
        return mPrimaryReleaseDate;
    }

    public void setPrimaryReleaseDate(String primaryReleaseDate) {
        mPrimaryReleaseDate = primaryReleaseDate;
    }

    public String getSortBy() {
        return mSortBy;
    }

    public void setSortBy(String sortBy) {
        mSortBy = sortBy;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }
}
