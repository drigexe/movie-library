package com.vysocki.yuri.movielibrary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

public class MovieDetailsViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<Movie> movie;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public void init(int movieId) {
        if (this.movie != null) {
            return;
        }
        this.movie = movieRepository.getMovie(movieId);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
