package com.indmind.moviefavorite;

import android.net.Uri;

public class DatabaseContract {
    public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w92";
    private static final String AUTHORITY = "com.indmind.moviecataloguetwo";
    private static final String TABLE_NAME = "movie_table";
    private static final String SCHEME = "content";

    static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
}
