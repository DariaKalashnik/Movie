package com.project.stageone.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.stageone.movie.R;
import com.project.stageone.movie.api.ApiInterface;
import com.project.stageone.movie.models.Movie;
import com.project.stageone.movie.models.MovieResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private OnClickHandler mHandler;
    private Context mContext;
    private MovieResponse mResponse;
    private List<Movie> mModel;

    /**
     * @param handler for a particular item in the list clicked
     */
    public MovieAdapter(OnClickHandler handler) {
        this.mHandler = handler;
        mModel = new ArrayList<>();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Movie movie = mModel.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return (mResponse == null) ? 0 : mResponse.getResults().size();
    }

    /**
     * Method is used to avoid creating a new MovieAdapter
     */
    public void setMovieResponse(MovieResponse movies) {
        this.mResponse = movies;
        mModel = mResponse.getResults();
        notifyDataSetChanged();
    }

    /**
     * Interface to hold onMovieClick (OnClick) method
     */
    public interface OnClickHandler {
        void onMovieClick(Movie movie);
        void onNetworkConnectionChanged(boolean isConnected);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_title)
        TextView movieTitle;
        @BindView(R.id.poster)
        ImageView moviePoster;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            Picasso.with(mContext)
                    .load(ApiInterface.POSTER_URL + movie.getPosterPath())
                    .placeholder(moviePoster.getDrawable())
                    .into(moviePoster);
            movieTitle.setText(movie.getTitle());
        }

        @Override
        public void onClick(View v) {
            mHandler.onMovieClick(mResponse.getResults().get(getAdapterPosition()));
        }
    }
}