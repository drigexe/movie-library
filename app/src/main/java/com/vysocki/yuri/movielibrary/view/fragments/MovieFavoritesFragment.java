package com.vysocki.yuri.movielibrary.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vysocki.yuri.movielibrary.R;
import com.vysocki.yuri.movielibrary.adapters.MovieAdapter;
import com.vysocki.yuri.movielibrary.model.Movie;
import com.vysocki.yuri.movielibrary.viewmodel.MovieFavoritesViewModel;

import java.util.List;

public class MovieFavoritesFragment extends Fragment {

    public static MovieFavoritesFragment newInstance() {
        return new MovieFavoritesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movies, container, false);

        TextView messageTextView = view.findViewById(R.id.text_view_favorites);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        MovieAdapter movieAdapter = new MovieAdapter(getContext());
        recyclerView.setAdapter(movieAdapter);

        MovieFavoritesViewModel movieFavoritesViewModel = ViewModelProviders.of(this).get(MovieFavoritesViewModel.class);
        movieFavoritesViewModel.init();
        List<Movie> movies = movieFavoritesViewModel.getMovies();

        if (movies != null) {
            recyclerView.setVisibility(View.VISIBLE);
            messageTextView.setVisibility(View.GONE);

            movieAdapter.setMovies(movies);
        } else {
            recyclerView.setVisibility(View.GONE);
            messageTextView.setVisibility(View.VISIBLE);
        }

        return view;
    }

}
