package com.project.stageone.movie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.stageone.movie.R;
import com.project.stageone.movie.api.ApiInterface;
import com.project.stageone.movie.data.MovieContract.MovieEntry;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.MovieViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public FavoriteMovieAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public FavoriteMovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_favorite_movies, parent, false));
    }

    @Override
    public void onBindViewHolder(FavoriteMovieAdapter.MovieViewHolder holder, int position) {
        // The columns of product info that we will see in the list
        // Get indices for the _id, movie id, movie title, and movie poster image columns
        int idIndex = mCursor.getColumnIndex(MovieEntry._ID);
        int movieIdIndex = mCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID);
        int movieMovieIndex = mCursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH);
        int movieTitleIndex = mCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_TITLE);

        // Fetch the right location in the cursor
        mCursor.moveToPosition(position);

        final int rowId = mCursor.getInt(idIndex);
        final int movieId = mCursor.getInt(movieIdIndex);
        String moviePoster = mCursor.getString(movieMovieIndex);
        String movieTitle = mCursor.getString(movieTitleIndex);

        holder.itemView.setTag(rowId);

        String mPosterUrl = "";
        if (moviePoster != null) {
            mPosterUrl = ApiInterface.BACKDROP_POSTER_URL + moviePoster;
            Picasso.with(mContext)
                    .load(mPosterUrl)
                    .into(holder.mPoster);
        }

        holder.mTitle.setText(movieTitle);
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.favorite_movie_poster)
        ImageView mPoster;
        @BindView(R.id.favorite_movie_title)
        TextView mTitle;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}