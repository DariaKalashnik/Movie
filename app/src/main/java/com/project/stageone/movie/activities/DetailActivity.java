package com.project.stageone.movie.activities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.project.stageone.movie.R;
import com.project.stageone.movie.api.ApiInterface;
import com.project.stageone.movie.data.MovieContract.MovieEntry;
import com.project.stageone.movie.databinding.ActivityDetailBinding;
import com.project.stageone.movie.models.Movie;
import com.project.stageone.movie.rest.Utils;
import com.squareup.picasso.Picasso;

import static com.project.stageone.movie.rest.Utils.setLanguage;

@SuppressLint("Registered")
public class DetailActivity extends AppCompatActivity {

    public static Movie mMovie;
    private final Context mContext = DetailActivity.this;
    private final ContentValues values = new ContentValues();
    private ActivityDetailBinding mBinding;
    private boolean isFavorite;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        mToolbar = findViewById(R.id.toolbar);
        Utils.setToolbar(this, mToolbar);

        mMovie = getIntent().getParcelableExtra(MainActivity.EXTRA_MOVIE_KEY);

        if (mMovie != null) {
            getMovieData();
        }

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_trailers:
                                Utils.startIntent(mContext, TrailerActivity.class);
                                break;
                            case R.id.action_overview:
                                Utils.startIntent(mContext, ReviewActivity.class);
                                break;
                            case R.id.action_favorite:
                                Utils.startIntent(mContext, FavoriteActivity.class);
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Method to display the movie data
     */
    public void getMovieData() {
        Picasso.with(this)
                .load(ApiInterface.BACKDROP_POSTER_URL + mMovie.getBackdropPath())
                .into(mBinding.moviePoster);
        mBinding.movieTitleValue.setText(mMovie.getTitle());
        mBinding.movieReleaseDateValue.setText(mMovie.getReleaseDate());
        mBinding.movieVoteAverageValue.setText(String.valueOf(mMovie.getVoteAverage()));
        mBinding.movieOriginalLangValue.setText(setLanguage(mMovie.getOriginalLang()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_favorite:
                if (!isFavorite)
                    addToFavorite(mMovie);
                else
                    Utils.showToast(mContext, getResources().getString(R.string.toast_data_was_already_added));
                return true;
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addToFavorite(Movie movie) {
        Uri mUri;
        values.put(MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        values.put(MovieEntry.COLUMN_POSTER_PATH, movie.getBackdropPath());
        values.put(MovieEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
        mUri = getContentResolver().insert(MovieEntry.CONTENT_URI, values);
        if (mUri == null)
            Utils.showToast(mContext, getResources().getString(R.string.toast_data_insertion_failed));
        else
            isFavorite = true;
            Utils.showToast(mContext, getResources().getString(R.string.toast_data_insertion_success));
    }
}