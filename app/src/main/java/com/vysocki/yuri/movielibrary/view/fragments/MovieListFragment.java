package com.vysocki.yuri.movielibrary.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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

import com.vysocki.yuri.movielibrary.model.Movie;
import com.vysocki.yuri.movielibrary.adapters.MovieAdapter;
import com.vysocki.yuri.movielibrary.viewmodel.MovieListViewModel;
import com.vysocki.yuri.movielibrary.R;
import com.vysocki.yuri.movielibrary.model.ResponsePage;

import java.util.List;

public class MovieListFragment extends Fragment {

    private OnMovieSelectedListener mCallback;
    private static final String LIST_TYPE_TAG = "LIST_TYPE_TAG";
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private TextView errorTextView;

    public interface OnMovieSelectedListener {
        public void onMovieSelected(Integer movieId);
    }

    public static MovieListFragment newInstance(@NonNull Integer movieListType) {
        MovieListFragment movieListFragment = new MovieListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LIST_TYPE_TAG, movieListType);
        movieListFragment.setArguments(bundle);
        return movieListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnMovieSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        errorTextView = view.findViewById(R.id.text_view_error_list);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(getContext());
        recyclerView.setAdapter(movieAdapter);

        Bundle bundle = this.getArguments();
        int listTypeId = bundle.getInt(LIST_TYPE_TAG);

        MovieListViewModel movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        movieListViewModel.init(listTypeId);
        movieListViewModel.getResponsePage().observe(getViewLifecycleOwner(), responsePageObserver);

        //mCallback.onMovieSelected(297761);

        return view;
    }

    private Observer<ResponsePage> responsePageObserver = new Observer<ResponsePage>() {
        @Override
        public void onChanged(@Nullable ResponsePage responsePage) {
            if (responsePage != null) {
                recyclerView.setVisibility(View.VISIBLE);
                errorTextView.setVisibility(View.GONE);

                List<Movie> movies = responsePage.getMovieList();
                movieAdapter.setMovies(movies);
            } else {
                recyclerView.setVisibility(View.GONE);
                errorTextView.setText(R.string.error_text_view_list_load_error);
                errorTextView.setVisibility(View.VISIBLE);
            }
        }
    };

}
