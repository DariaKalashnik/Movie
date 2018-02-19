package com.project.stageone.movie.api;

import com.project.stageone.movie.models.Movie;
import com.project.stageone.movie.models.MovieResponse;
import com.project.stageone.movie.models.ReviewResponse;
import com.project.stageone.movie.models.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "http://api.themoviedb.org/3/";

    String POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    String BACKDROP_POSTER_URL = "http://image.tmdb.org/t/p/w500/";

    String YOUTUBE_URL = "http://www.youtube.com/watch?v=%1$s";

    // API key Should be individual
    String API_KEY = "f02cf33a7058d2f7a40e8f9a154835d2";

    /**
     * @param id     - of a Movie in the database
     * @param apiKey - is a unique API key
     * @return details about Movie requested
     */
    @GET("movie/{id}")
    Call<Movie> getMoviesInfo(@Path("id") int id, @Query("api_key") String apiKey);

    /**
     * @param preference - sort parameter (popular or top rated)
     * @param apiKey     - is a unique API key
     * @return list of movies sorted by preference
     */
    @GET("movie/{preference}")
    Call<MovieResponse> getMovie(@Path("preference") String preference, @Query("api_key") String apiKey);

    /**
     * @param id
     * @param apiKey - is a unique API key
     * @return list of movie trailer/trailers
     */
    @GET("movie/{id}/videos")
    Call<TrailerResponse> getMovieTrailers(@Path("id") String id, @Query("api_key") String apiKey);

    /**
     * @param id
     * @param apiKey - is a unique API key
     * @return list of movie reviews
     */
    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getMovieReviews(@Path("id") String id, @Query("api_key") String apiKey);
}


