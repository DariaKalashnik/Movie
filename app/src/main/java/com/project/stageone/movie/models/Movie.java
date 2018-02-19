package com.project.stageone.movie.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_language")
    private String originalLang;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("vote_average")
    private double voteAverage;

    // Empty constructor
    public Movie() {
    }

    public Movie(int id, String title, String posterPath, String originalLang, String releaseDate, String backdropPath, double voteAverage) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.originalLang = originalLang;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        originalLang = in.readString();
        releaseDate = in.readString();
        backdropPath = in.readString();
        voteAverage = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(originalLang);
        dest.writeString(releaseDate);
        dest.writeString(backdropPath);
        dest.writeDouble(voteAverage);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOriginalLang() {
        return originalLang;
    }

    public void setOriginalLang(String originalLang) {
        this.originalLang = originalLang;
    }

}
