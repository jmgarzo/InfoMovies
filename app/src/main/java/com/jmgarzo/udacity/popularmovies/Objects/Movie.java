package com.jmgarzo.udacity.popularmovies.Objects;

import java.util.ArrayList;

/**
 * Created by jmgarzo on 13/02/17.
 */

public class Movie {

//    public static final String _ID = "_id";
//
//    //Data from JSON
//    public static final String POSTER_PATH = "poster_path";
//    public static final String ADULT = "adult";
//    public static final String OVERVIEW = "overview";
//    public static final String RELEASE_DATE = "release_date";
//    //TODO:IF I have time store Genre_ids
//    //public static final String GENRE_IDS = "genre_ids";
//    public static final String MOVIE_WEB_ID = "movie_web_id";
//    public static final String ORIGINAL_TITLE = "original_title";
//    public static final String ORIGINAL_LANGUAGE = "original_language";
//    public static final String TITLE = "title";
//    public static final String BACKDROP_PATH = "backdrop_path";
//    public static final String POPULARITY = "popularity";
//    public static final String VOTE_COUNT = "vote_count";
//    public static final String VIDEO = "video";
//    public static final String VOTE_AVERAGE = "vote_average";
    private int id;
    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private int gnereIds;
    private String movieWebId;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String BackdropPath;
    private double popularity;
    private int voteCount;
    private boolean video;
    private double voteAverage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getGnereIds() {
        return gnereIds;
    }

    public void setGnereIds(int gnereIds) {
        this.gnereIds = gnereIds;
    }

    public String getMovieWebId() {
        return movieWebId;
    }

    public void setMovieWebId(String movieWebId) {
        this.movieWebId = movieWebId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return BackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        BackdropPath = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
