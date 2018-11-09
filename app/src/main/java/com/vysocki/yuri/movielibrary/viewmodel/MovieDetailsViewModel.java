package com.vysocki.yuri.movielibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.vysocki.yuri.movielibrary.model.Movie;
import com.vysocki.yuri.movielibrary.repository.MovieRepository;

public class MovieDetailsViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<Movie> movie;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public void init(int movieId) {
        if (this.movie != null) {
            if (this.movie.getValue() != null) {
                return;
            }
        }
        this.movie = movieRepository.getMovie(movieId);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
