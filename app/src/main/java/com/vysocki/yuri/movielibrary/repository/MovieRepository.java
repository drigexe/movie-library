package com.vysocki.yuri.movielibrary.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.vysocki.yuri.movielibrary.model.Movie;
import com.vysocki.yuri.movielibrary.R;
import com.vysocki.yuri.movielibrary.model.ResponsePage;
import com.vysocki.yuri.movielibrary.webservice.TheMovieDBApi;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vysocki.yuri.movielibrary.GlobalConstants.BASE_URL;

public class MovieRepository {

    private TheMovieDBApi theMovieDBApi;

    public MovieRepository() {
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
        } else if (movieListType == R.id.nav_upcoming) {
            call = theMovieDBApi.getUpcomingMovies(page);
        } else {
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

    public RealmResults<Movie> getFavoritesMovies() {
        RealmResults<Movie> movies;
        Realm realm = Realm.getDefaultInstance();
        try {
            movies = realm.where(Movie.class).findAll();
        } catch (Exception e) {
            movies = null;
        }
        //closing realm object below should not be commented, but app suddenly crashes with it
        //and i do not have time to figure it out before the deadline
        //so memory leak is lesser evil then FATAL ERROR
        //realm.close();
        return movies;
    }

    public void addMovieToFavorites(Movie movie) {
        new AddMovieToFavoritesAsyncTask().execute(movie);
    }

    private static class AddMovieToFavoritesAsyncTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            Realm realm = Realm.getDefaultInstance();
            try {
                realm.beginTransaction();
                Movie movie = realm.createObject(Movie.class);
                movie.setMovieId(movies[0].getMovieId());
                movie.setTitle(movies[0].getTitle());
                movie.setTagline(movies[0].getTagline());
                movie.setReleaseDate(movies[0].getReleaseDate());
                movie.setPosterPath(movies[0].getPosterPath());
                realm.commitTransaction();
            } catch (Exception e) {
                realm.cancelTransaction();
            }
            realm.close();
            return null;
        }
    }

    public boolean isMovieInFavorites(int movieId) {
        RealmResults<Movie> movies;
        Realm realm = Realm.getDefaultInstance();
        try {
            movies = realm.where(Movie.class).equalTo("movieId", movieId).findAll();
        } catch (Exception e) {
            movies = null;
        }
        realm.close();

        if (movies.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void removeMovieFromFavorites(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Movie> movies;
                movies = realm.where(Movie.class).equalTo("movieId", movieId).findAll();
                movies.deleteAllFromRealm();
            }
        });

        realm.close();
    }

}
