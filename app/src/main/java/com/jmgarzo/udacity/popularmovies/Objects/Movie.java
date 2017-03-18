package com.jmgarzo.udacity.popularmovies.Objects;

import android.content.ContentValues;

import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;

/**
 * Created by jmgarzo on 13/02/17.
 */

public class Movie {


    private int id;
    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private String movieWebId;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private double popularity;
    private int voteCount;
    private boolean video;
    private double voteAverage;
    private String registryType;
    private String timestamp;

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
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
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

    public String getRegistryType() {
        return registryType;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return a movie without _id and timestamp to make an insert
     */
    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PopularMovieContract.MovieEntry.POSTER_PATH,getPosterPath());
        contentValues.put(PopularMovieContract.MovieEntry.ADULT,Boolean.toString(isAdult()));
        contentValues.put(PopularMovieContract.MovieEntry.OVERVIEW,getOverview());
        contentValues.put(PopularMovieContract.MovieEntry.RELEASE_DATE,getReleaseDate());
        contentValues.put(PopularMovieContract.MovieEntry.MOVIE_WEB_ID,getMovieWebId());
        contentValues.put(PopularMovieContract.MovieEntry.ORIGINAL_TITLE,getOriginalTitle());
        contentValues.put(PopularMovieContract.MovieEntry.ORIGINAL_LANGUAGE,getOriginalLanguage());
        contentValues.put(PopularMovieContract.MovieEntry.TITLE,getTitle());
        contentValues.put(PopularMovieContract.MovieEntry.BACKDROP_PATH,getBackdropPath());
        contentValues.put(PopularMovieContract.MovieEntry.POPULARITY,getPopularity());
        contentValues.put(PopularMovieContract.MovieEntry.VOTE_COUNT,getVoteCount());
        contentValues.put(PopularMovieContract.MovieEntry.VIDEO,Boolean.toString(isVideo()));
        contentValues.put(PopularMovieContract.MovieEntry.VOTE_AVERAGE,getVoteAverage());
        contentValues.put(PopularMovieContract.MovieEntry.REGISTRY_TYPE,getRegistryType());

        return contentValues;
    }
}
