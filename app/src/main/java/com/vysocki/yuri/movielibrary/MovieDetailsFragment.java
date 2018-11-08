package com.vysocki.yuri.movielibrary;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.vysocki.yuri.movielibrary.GlobalConstants.BASE_IMAGES_URL;

public class MovieDetailsFragment extends Fragment {

    private static final String MOVIE_ID_TAG = "MOVIE_ID_TAG";
    private MovieDetailsViewModel movieDetailsViewModel;

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

        final TextView textView = view.findViewById(R.id.detailsTextView);
        final ImageView imageView = view.findViewById(R.id.detailsImageView);

        Bundle bundle = this.getArguments();
        int movieId = bundle.getInt(MOVIE_ID_TAG);

        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.init(movieId);
        movieDetailsViewModel.getMovie().observe(getViewLifecycleOwner(), new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if (movie != null) {

                    String content = "";
                    content += "title: " + movie.getTitle() + "\n";

                    if (movie.getTagline() == null) {
                        content += "No tagline loaded" + "\n";
                    } else {
                        content += "tagline: " + movie.getTagline() + "\n";
                    }

                    content += "release Date: " + movie.getReleaseDate();
                    textView.setText(content);

                    if (movie.getPosterPath() != null) {
                        Glide.with(getContext()).load(BASE_IMAGES_URL+ "w500/" + movie.getPosterPath()).into(imageView);
                    } else {
                        imageView.setImageResource(R.drawable.noimage);
                    }

                }
            }
        });

        return view;
    }
}
