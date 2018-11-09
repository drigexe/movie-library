package com.vysocki.yuri.movielibrary.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Movie extends RealmObject {

    @SerializedName("id")
    private int movieId;

    private String title;

    private String tagline;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
