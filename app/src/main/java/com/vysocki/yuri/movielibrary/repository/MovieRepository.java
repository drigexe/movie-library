package com.vysocki.yuri.movielibrary.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.vysocki.yuri.movielibrary.model.Movie;
import com.vysocki.yuri.movielibrary.R;
import com.vysocki.yuri.movielibrary.model.ResponsePage;
import com.vysocki.yuri.movielibrary.webservice.TheMovieDBApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vysocki.yuri.movielibrary.GlobalConstants.BASE_URL;

public class MovieRepository {

    private TheMovieDBApi theMovieDBApi;

    public MovieRepository(Application application) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        theMovieDBApi = retrofit.create(TheMovieDBApi.class);
    }

    public MutableLiveData<Movie> getMovie(int movieId) {
        final MutableLiveData<Movie> movie = new MutableLiveData<>();
        Call<Movie> call = theMovieDBApi.getMovie(movieId);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (!response.isSuccessful()) {
                    Movie errorResponse = new Movie();
                    errorResponse.setTitle(Integer.toString(response.code()));
                    movie.postValue(errorResponse);
                    return;
                }
                movie.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Movie errorMessage = new Movie();
                errorMessage.setTitle(t.getMessage());
                movie.postValue(errorMessage);
            }
        });
        return movie;
    }

    public MutableLiveData<ResponsePage> getMoviesPage(int movieListType, int page) {
        final MutableLiveData<ResponsePage> responsePage = new MutableLiveData<>();
        Call<ResponsePage> call;

        if (movieListType == R.id.nav_popular) {
            call = theMovieDBApi.getPopularMovies(page);
        }
        else if (movieListType == R.id.nav_upcoming) {
            call = theMovieDBApi.getUpcomingMovies(page);
        }
        else {
            call = theMovieDBApi.getTopRatedMovies(page);
        }

        call.enqueue(new Callback<ResponsePage>() {
            @Override
            public void onResponse(Call<ResponsePage> call, Response<ResponsePage> response) {
                if (!response.isSuccessful()) {
                    responsePage.postValue(null);
                    return;
                }
                responsePage.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponsePage> call, Throwable t) {
                responsePage.postValue(null);
            }
        });
        return responsePage;
    }

}
