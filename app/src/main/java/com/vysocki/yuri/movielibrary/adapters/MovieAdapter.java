package com.vysocki.yuri.movielibrary.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vysocki.yuri.movielibrary.model.Movie;
import com.vysocki.yuri.movielibrary.R;

import java.util.ArrayList;
import java.util.List;

import static com.vysocki.yuri.movielibrary.GlobalConstants.BASE_IMAGES_URL;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private List<Movie> movies = new ArrayList<>();
    private Context mContext;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_item, viewGroup, false);
        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        Movie currentMovie = movies.get(i);
        movieHolder.textViewTitle.setText(currentMovie.getTitle());
        movieHolder.textViewReleaseDate.setText(currentMovie.getReleaseDate());
        if (currentMovie.getPosterPath() != null) {
            try {
                Glide.with(mContext).load(BASE_IMAGES_URL+ "w185/"
                        + currentMovie.getPosterPath())
                        .into(movieHolder.imageViewPoster);
            } catch (Exception e) {
                movieHolder.imageViewPoster.setImageResource(R.drawable.noimage);
            }

        } else {
            movieHolder.imageViewPoster.setImageResource(R.drawable.noimage);
        }

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewReleaseDate;
        private ImageView imageViewPoster;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title_list);
            textViewReleaseDate = itemView.findViewById(R.id.text_view_release_date_list);
            imageViewPoster = itemView.findViewById(R.id.image_view_list_poster);
        }
    }

}
