package com.vysocki.yuri.movielibrary.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.vysocki.yuri.movielibrary.model.Movie;
import com.vysocki.yuri.movielibrary.repository.MovieRepository;

import io.realm.RealmResults;

public class MovieFavoritesViewModel extends ViewModel {

    private MovieRepository movieRepository;
    private RealmResults<Movie> movies;

    public MovieFavoritesViewModel() {
        movieRepository = new MovieRepository();
    }

    public void init() {
        if (this.movies != null) {
            return;
        }
        this.movies = movieRepository.getFavoritesMovies();
    }

    public RealmResults<Movie> getMovies() {
        return movies;
    }
}
