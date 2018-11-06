package com.vysocki.yuri.movielibrary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

public class MovieListViewModel extends AndroidViewModel {
    //declare repository here
    //declare Livedata List of Movies here

    public MovieListViewModel(@NonNull Application application) {
        super(application);
        // instantiate repository here with application context
    }

    // add init(int movie_list_type_id) method, where call
      // repository.getSpecificMovieList(int page) depends on movie_list_type_id
      // and assign returned value to the Livedata List of Movies


    // add getMovieList method which will be observed by the fragment


    //add getPopularMovieList(int page), where need to call
      // repository.getMovieList(relativePath, page)

    //add getUpcomingMovieList(int page), where need to call
      // repository.getMovieList(relativePath, page)

    //add getTopRatedMovieList(int page), where need to call
      // repository.getMovieList(relativePath, page)
}
