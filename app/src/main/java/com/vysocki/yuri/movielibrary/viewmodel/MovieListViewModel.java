package com.vysocki.yuri.movielibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.vysocki.yuri.movielibrary.repository.MovieRepository;
import com.vysocki.yuri.movielibrary.model.ResponsePage;

public class MovieListViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<ResponsePage> responsePage;
    private int listTypeId;

    public MovieListViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository();
    }

    public void init(int listTypeId) {
        if (this.responsePage != null) {
            if (this.responsePage.getValue() != null) {
                return;
            }
        }
        this.listTypeId = listTypeId;
        this.responsePage = movieRepository.getMoviesPage(this.listTypeId, 1);

    }

    public LiveData<ResponsePage> getResponsePage() {
        return responsePage;
    }
}
