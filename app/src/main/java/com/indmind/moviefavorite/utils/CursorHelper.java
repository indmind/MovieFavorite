package com.indmind.moviefavorite.utils;

import android.database.Cursor;

import com.indmind.moviefavorite.models.Movie;

import java.util.ArrayList;

public class CursorHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> notesList = new ArrayList<>();
        while (notesCursor.moveToNext()) {

            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow("id"));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow("title"));
            String overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow("overview"));
            float rating = notesCursor.getFloat(notesCursor.getColumnIndexOrThrow("vote_average"));

            String posterPath = notesCursor.getString(notesCursor.getColumnIndexOrThrow("poster_path"));
            notesList.add(new Movie(
                    id, 0, false, rating, title, 0.0,
                    posterPath, "", "", "",
                    false, overview, "", null
            ));
        }
        return notesList;
    }
}
