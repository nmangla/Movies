package com.nmangla.movies.sync.response;

import com.google.gson.annotations.SerializedName;
import com.nmangla.movies.models.MovieListItem;

import java.util.List;

// Response expected for information about all movies
public class MoviesResponse {

    @SerializedName("page")
    private int mPage;

    @SerializedName("total_results")
    private int mTotalResults;

    @SerializedName("total_pages")
    private int mTotalPages;

    @SerializedName("results")
    private List<MovieListItem> mResults;

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int totalResults) {
        mTotalResults = totalResults;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int totalPages) {
        mTotalPages = totalPages;
    }

    public List<MovieListItem> getResults() {
        return mResults;
    }

    public void setResults(List<MovieListItem> results) {
        mResults = results;
    }
}
