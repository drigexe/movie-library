package com.vysocki.yuri.movielibrary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePage {

    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<Movie> movieList;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
