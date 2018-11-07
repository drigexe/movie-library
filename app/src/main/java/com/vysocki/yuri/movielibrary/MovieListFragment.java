package com.vysocki.yuri.movielibrary;

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

public class MovieListFragment extends Fragment {

    OnMovieSelectedListener mCallback;

    public interface OnMovieSelectedListener {
        public void onMovieSelected(Integer movieId);
    }

    private static final String LIST_TYPE_TAG = "LIST_TYPE_TAG";

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

        TextView textView = view.findViewById(R.id.movielisttextview);
        Bundle bundle = this.getArguments();
        String movieListType = Integer.toString(bundle.getInt(LIST_TYPE_TAG));
        textView.setText(movieListType);

        Button button = view.findViewById(R.id.buttonb);
        final Integer movieId = 25;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onMovieSelected(movieId);
            }
        });

        return view;
    }

}
