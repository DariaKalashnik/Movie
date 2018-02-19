package com.project.stageone.movie.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Trailers> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailers> getResults() {
        return results;
    }

    public void setResults(List<Trailers> results) {
        this.results = results;
    }
}
