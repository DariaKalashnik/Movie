package com.project.stageone.movie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.stageone.movie.R;
import com.project.stageone.movie.models.TrailerResponse;
import com.project.stageone.movie.models.Trailers;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private OnClickHandler mHandler;
    private TrailerResponse mResponse;
    private List<Trailers> mModel;

    /**
     * @param handler for a particular item in the list clicked
     */
    public TrailerAdapter(OnClickHandler handler) {
        this.mHandler = handler;
        mModel = new ArrayList<>();
    }

    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrailerAdapter.TrailerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trailers, parent, false));
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        final Trailers trailers = mModel.get(position);
        holder.bind(trailers);
    }

    @Override
    public int getItemCount() {
        return (mResponse == null) ? 0 : mResponse.getResults().size();
    }

    /**
     * Method is used to avoid creating a new TrailerAdapter
     */
    public void setMovieResponse(TrailerResponse trailers) {
        this.mResponse = trailers;
        mModel = mResponse.getResults();
        notifyDataSetChanged();
    }

    /**
     * Interface to hold onMovieClick(OnClick) method
     */
    public interface OnClickHandler {
        void onMovieClick(String key);
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailer_movie_title)
        TextView trailerTitle;

        TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Trailers movie) {
            trailerTitle.setText(movie.getName());
        }

        @Override
        public void onClick(View v) {
            mHandler.onMovieClick(mResponse.getResults().get(getAdapterPosition()).getKey());
        }
    }
}
