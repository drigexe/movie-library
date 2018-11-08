package com.vysocki.yuri.movielibrary;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MovieListFragment extends Fragment {

    private OnMovieSelectedListener mCallback;
    private static final String LIST_TYPE_TAG = "LIST_TYPE_TAG";
    private MovieListViewModel movieListViewModel;
    private TextView textView;

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

        textView = view.findViewById(R.id.movielisttextview);

        Bundle bundle = this.getArguments();
        int listTypeId = bundle.getInt(LIST_TYPE_TAG);

        movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        movieListViewModel.init(listTypeId);
        movieListViewModel.getResponsePage().observe(getViewLifecycleOwner(), responsePageObserver);

        Button button = view.findViewById(R.id.buttonb);
        final Integer movieId = 241251;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onMovieSelected(movieId);
            }
        });

        return view;
    }

    private Observer<ResponsePage> responsePageObserver = new Observer<ResponsePage>() {
        @Override
        public void onChanged(@Nullable ResponsePage responsePage) {
            if (responsePage != null) {
                List<Movie> movies = responsePage.getMovieList();
                for (Movie movie : movies) {
                    String content = "";
                    content += "id: " + Integer.toString(movie.getMovieId()) + "\n";
                    content += "title: " + movie.getTitle() + "\n";
                    content += "release Date: " + movie.getReleaseDate() + "\n";
                    content += "poster path: " + movie.getPosterPath() + "\n";
                    textView.append(content);
                }
            } else {
                textView.setText("Something wrong");
            }
        }
    };

}
