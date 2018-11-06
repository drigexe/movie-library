package com.vysocki.yuri.movielibrary;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("movie_id")
    private int movieId;

    @SerializedName("original_title")
    private String title;

    private String tagline;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getTagline() {
        return tagline;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
