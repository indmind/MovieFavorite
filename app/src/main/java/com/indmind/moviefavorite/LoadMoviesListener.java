package com.indmind.moviefavorite;

import com.indmind.moviefavorite.models.Movie;

import java.util.ArrayList;

interface LoadMoviesListener {
    void onPostExecute(ArrayList<Movie> movies);
}
