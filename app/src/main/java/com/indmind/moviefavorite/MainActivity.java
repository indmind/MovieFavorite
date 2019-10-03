package com.indmind.moviefavorite;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.indmind.moviefavorite.adapters.ListMovieAdapter;
import com.indmind.moviefavorite.models.Movie;
import com.indmind.moviefavorite.utils.CursorHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.indmind.moviefavorite.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoadMoviesListener {
    private final String EXTRA_STATE = "extra_state";
    @BindView(R.id.rv_favorite_movie)
    RecyclerView rvFavoriteMovie;
    @BindView(R.id.tv_no_favorite)
    TextView tvNoFavorite;
    private ListMovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        adapter = new ListMovieAdapter(this);

        rvFavoriteMovie.setLayoutManager(new LinearLayoutManager(this));
        rvFavoriteMovie.setHasFixedSize(true);
        rvFavoriteMovie.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);

        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        if (savedInstanceState != null) {
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(EXTRA_STATE);

            if (movies != null) {
                adapter.setMovies(movies);
            }
        } else {
            new LoadMovieAsync(this, this).execute();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovie());
    }

    @Override
    public void onPostExecute(ArrayList<Movie> movies) {
        if (movies.size() > 0) {
            tvNoFavorite.setVisibility(View.GONE);
        } else {
            tvNoFavorite.setVisibility(View.VISIBLE);
        }

        adapter.setMovies(movies);
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMoviesListener> weakReference;

        private LoadMovieAsync(Context context, LoadMoviesListener listener) {
            weakContext = new WeakReference<>(context);
            weakReference = new WeakReference<>(listener);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);

            ArrayList<Movie> listMovie = new ArrayList<>();

            if (movies != null) {
                listMovie = CursorHelper.mapCursorToArrayList(movies);
            }

            weakReference.get().onPostExecute(listMovie);
        }
    }

    class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMovieAsync(context, (LoadMoviesListener) context).execute();
        }
    }
}
