package com.project.stageone.movie.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.project.stageone.movie.R;
import com.project.stageone.movie.adapter.TrailerAdapter;
import com.project.stageone.movie.api.ApiClient;
import com.project.stageone.movie.api.ApiInterface;
import com.project.stageone.movie.databinding.ActivityTrailerBinding;
import com.project.stageone.movie.models.Movie;
import com.project.stageone.movie.models.TrailerResponse;
import com.project.stageone.movie.models.Trailers;
import com.project.stageone.movie.rest.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerActivity extends AppCompatActivity implements TrailerAdapter.OnClickHandler {

    private final Movie mCurrentMovie = DetailActivity.mMovie;
    private final Context mContext = TrailerActivity.this;
    ActivityTrailerBinding mBinding;
    TrailerAdapter mAdapter;
    ApiInterface apiInterface;
    List<Trailers> mModel;
    LinearLayoutManager mLayoutManager;
    private RecyclerView mRecycle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_trailer);

        mModel = new ArrayList<>();

        mRecycle = findViewById(R.id.recycle);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecycle.setLayoutManager(mLayoutManager);
        mAdapter = new TrailerAdapter(TrailerActivity.this);
        mRecycle.setAdapter(mAdapter);

        mToolbar = findViewById(R.id.toolbar);
        Utils.setToolbar(this, mToolbar);
        getMovieTrailer(String.valueOf(mCurrentMovie.getId()));
    }

    /**
     * Method to display trailers
     */
    public void getMovieTrailer(String id) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        final Call<TrailerResponse> callResponse = apiInterface.getMovieTrailers(id, ApiInterface.API_KEY);
        callResponse.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    mAdapter.setMovieResponse(response.body());
                    mAdapter.notifyDataSetChanged();
                    mModel = response.body().getResults();
                } else {
                    Utils.showToast(mContext, getResources().getString(R.string.toast_error_message));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable throwable) {
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

    @Override
    public void onMovieClick(String key) {
        String url = ApiInterface.YOUTUBE_URL + key;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
