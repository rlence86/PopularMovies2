package com.ramonlence.popularmovies.entities;

import java.io.Serializable;

/**
 * Created by ramon on 25/1/17.
 */

public class Movie implements Serializable {
    private int id;
    private String original_title;
    private String poster_path;
    private String overview;
    private Double vote_average;
    private String release_date;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
