package com.vysocki.yuri.movielibrary.view.fragments;

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
import com.vysocki.yuri.movielibrary.model.Movie;
import com.vysocki.yuri.movielibrary.viewmodel.MovieDetailsViewModel;
import com.vysocki.yuri.movielibrary.R;

import static com.vysocki.yuri.movielibrary.GlobalConstants.BASE_IMAGES_URL;

public class MovieDetailsFragment extends Fragment {

    private static final String MOVIE_ID_TAG = "MOVIE_ID_TAG";
    private MovieDetailsViewModel movieDetailsViewModel;
    private ImageView posterImageView;
    private TextView titleTextView;
    private TextView taglineTextView;
    private TextView releaseDateTextView;
    private TextView errorTextView;

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

        posterImageView = view.findViewById(R.id.detailsImageView);
        titleTextView = view.findViewById(R.id.text_view_title_details);
        taglineTextView = view.findViewById(R.id.text_view_tagline_details);
        releaseDateTextView = view.findViewById(R.id.text_view_release_date_details);
        errorTextView = view.findViewById(R.id.text_view_error_details);

        Bundle bundle = this.getArguments();
        int movieId = bundle.getInt(MOVIE_ID_TAG);

        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.init(movieId);
        movieDetailsViewModel.getMovie().observe(getViewLifecycleOwner(), movieObserver);

        return view;
    }

    private Observer<Movie> movieObserver = new Observer<Movie>() {
        @Override
        public void onChanged(@Nullable Movie movie) {
            if (movie != null) {
                errorTextView.setVisibility(View.GONE);
                setViewsVisibility(View.VISIBLE);

                //not all fields here is checked, because API documentation tells that
                //title and release date cannot be null on server in the first place
                titleTextView.setText(movie.getTitle());
                if (movie.getTagline() != null) {
                    taglineTextView.setText(movie.getTagline());
                }
                releaseDateTextView.setText(movie.getReleaseDate());

                if (movie.getPosterPath() != null) {
                    try {
                        Glide.with(getContext()).load(BASE_IMAGES_URL + "w500/" + movie.getPosterPath()).into(posterImageView);
                    } catch (Exception e) {
                        //default picture will stay
                    }

                } else {
                    setViewsVisibility(View.GONE);
                    errorTextView.setText(R.string.error_text_view_details_load_error);
                    errorTextView.setVisibility(View.VISIBLE);
                }
            }
        }

    };

    private void setViewsVisibility(int viewsVisibility) {
        posterImageView.setVisibility(viewsVisibility);
        titleTextView.setVisibility(viewsVisibility);
        taglineTextView.setVisibility(viewsVisibility);
        releaseDateTextView.setVisibility(viewsVisibility);
    }
}

