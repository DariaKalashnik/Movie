package com.project.stageone.movie.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.project.stageone.movie.R;
import com.project.stageone.movie.adapter.FavoriteMovieAdapter;
import com.project.stageone.movie.data.MovieContract.MovieEntry;
import com.project.stageone.movie.databinding.ActivityFavoriteBinding;
import com.project.stageone.movie.rest.Utils;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class FavoriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIE_LOADER = 0;
    private final Context mContext = FavoriteActivity.this;
    ActivityFavoriteBinding mBinding;
    private FavoriteMovieAdapter movieAdapter;
    private RecyclerView mRecycle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);

        mToolbar = findViewById(R.id.toolbar);
        Utils.setToolbar(this, mToolbar);

        mRecycle = findViewById(R.id.recycle);
        mRecycle.setHasFixedSize(true);
        mRecycle.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        movieAdapter = new FavoriteMovieAdapter(mContext);
        mRecycle.setItemAnimator(new SlideInUpAnimator());
        mRecycle.setAdapter(movieAdapter);

        // Kick off the loader
        getSupportLoaderManager().initLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,       // Parent activity context
                MovieEntry.CONTENT_URI,             // Provider content URI to query
                null,                     // Columns to include in the resulting Cursor
                null,                      // No selection clause
                null,                   // No selection arguments
                null);                     // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link FavoriteMovieAdapter} with this new cursor containing updated movie data
        movieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        movieAdapter.swapCursor(null);
    }
}
