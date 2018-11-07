package com.vysocki.yuri.movielibrary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MovieDetailsFragment extends Fragment {

    private static final String MOVIE_ID_TAG = "MOVIE_ID_TAG";

    public static MovieDetailsFragment newInstance(@NonNull Integer movieId) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIE_ID_TAG, movieId);
        movieDetailsFragment.setArguments(bundle);
        return movieDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        TextView textView = view.findViewById(R.id.detailsTextView);
        Bundle bundle = this.getArguments();
        String string = Integer.toString(bundle.getInt(MOVIE_ID_TAG));
        textView.setText(string);

        return view;
    }
}
