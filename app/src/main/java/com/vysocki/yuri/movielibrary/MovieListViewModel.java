package com.vysocki.yuri.movielibrary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

public class MovieListViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<ResponsePage> responsePage;
    private int listTypeId;

    public MovieListViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public void init(int listTypeId) {
        if (this.responsePage != null) {
            return;
        }
        this.listTypeId = listTypeId;
        this.responsePage = movieRepository.getMoviesPage(this.listTypeId, 1);

    }

    public LiveData<ResponsePage> getResponsePage() {
        return responsePage;
    }
}
