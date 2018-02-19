package com.project.stageone.movie.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.project.stageone.movie.R;
import com.project.stageone.movie.adapter.ReviewAdapter;
import com.project.stageone.movie.api.ApiClient;
import com.project.stageone.movie.api.ApiInterface;
import com.project.stageone.movie.databinding.ActivityReviewBinding;
import com.project.stageone.movie.models.Movie;
import com.project.stageone.movie.models.ReviewResponse;
import com.project.stageone.movie.models.Reviews;
import com.project.stageone.movie.rest.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {
    private static final Movie mCurrentMovie = DetailActivity.mMovie;
    private final Context mContext = ReviewActivity.this;
    RecyclerView mRecycle;
    ActivityReviewBinding mBinding;
    LinearLayoutManager mLayoutManager;
    ApiInterface apiInterface;
    private ReviewAdapter mAdapter;
    private List<Reviews> mModel;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_review);
        mRecycle = findViewById(R.id.recycle);
        mModel = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mAdapter = new ReviewAdapter(this);
        mRecycle.setLayoutManager(mLayoutManager);
        mRecycle.setAdapter(mAdapter);

        mToolbar = findViewById(R.id.toolbar);
        Utils.setToolbar(this, mToolbar);
        getMovieReview(String.valueOf(mCurrentMovie.getId()));
    }

    /**
     * Method to display review
     */
    public void getMovieReview(String id) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        final Call<ReviewResponse> callResponse = apiInterface.getMovieReviews(id, ApiInterface.API_KEY);
        callResponse.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResults().size() != 0) {
                        mModel = response.body().getResults();
                        mAdapter.setReviewResponse(mModel);
                    }
                } else {
                    Utils.showToast(mContext, getResources().getString(R.string.toast_error_message));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable throwable) {
                // Toast message if request is failed
                Utils.showToast(mContext, getResources().getString(R.string.toast_on_failure));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}