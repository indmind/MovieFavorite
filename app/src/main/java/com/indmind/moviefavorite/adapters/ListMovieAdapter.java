package com.indmind.moviefavorite.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.indmind.moviefavorite.R;
import com.indmind.moviefavorite.models.Movie;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.indmind.moviefavorite.DatabaseContract.POSTER_BASE_URL;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.MovieViewHolder> {
    private final Context mContext;
    private final ArrayList<Movie> listMovie = new ArrayList<>();

    public ListMovieAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setMovies(List<Movie> listMovie) {
        this.listMovie.clear();
        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_row_movies, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int i) {
        Movie movie = listMovie.get(i);

        movieViewHolder.tvTitle.setText(movie.getTitle());
        movieViewHolder.tvScore.setText(String.valueOf(new DecimalFormat("0.0").format(movie.getVote_average())));

        Glide.with(mContext).load(POSTER_BASE_URL + movie.getPoster_path()).into(movieViewHolder.imgPoster);

        movieViewHolder.tvOverview.setText(movie.getOverview());
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_movie_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_movie_score)
        TextView tvScore;
        @BindView(R.id.tv_item_movie_overview)
        TextView tvOverview;
        @BindView(R.id.img_item_movie_poster)
        ImageView imgPoster;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
