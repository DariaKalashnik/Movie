package com.project.stageone.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.project.stageone.movie.R;
import com.project.stageone.movie.models.Reviews;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    Context mContext;
    @BindView(R.id.no_reviews)
    LinearLayout noReview;
    private List<Reviews> mModel;

    public ReviewAdapter(Context mContext) {
        this.mContext = mContext;
        mModel = new ArrayList<>();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review, parent, false));
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(mModel.get(position).getAuthor(), mModel.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return (mModel != null) ? mModel.size():0;
    }

    /**
     * Method is used to avoid creating a new ReviewAdapter
     */
    public void setReviewResponse(List<Reviews> mModel) {
        this.mModel = mModel;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.expand_text_view)
        ExpandableTextView mExpandableTextView;
        @BindView(R.id.review_author)
        TextView mReviewAuthor;

        ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(String author, String content) {
            mExpandableTextView.setText(content);
            mReviewAuthor.setText(author);
        }
    }

}
