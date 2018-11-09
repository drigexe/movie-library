package com.vysocki.yuri.movielibrary.webservice;

import com.vysocki.yuri.movielibrary.model.Movie;
import com.vysocki.yuri.movielibrary.model.ResponsePage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.vysocki.yuri.movielibrary.GlobalConstants.API_KEY;

public interface TheMovieDBApi {

    @GET("movie/{movie_id}?api_key=" + API_KEY)
    Call<Movie> getMovie(
            @Path("movie_id") int movieId
    );

    @GET("movie/popular?api_key=" + API_KEY)
    Call<ResponsePage> getPopularMovies(
            @Query("page") int page
    );

    @GET("movie/top_rated?api_key=" + API_KEY)
    Call<ResponsePage> getTopRatedMovies(
            @Query("page") int page
    );

    @GET("movie/upcoming?api_key=" + API_KEY)
    Call<ResponsePage> getUpcomingMovies(
            @Query("page") int page
    );
}
