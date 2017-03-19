package com.jmgarzo.udacity.popularmovies.Objects;

import android.content.ContentValues;

import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;

/**
 * Created by jmgarzo on 12/03/17.
 */

public class Review {
    private int id;
    private int movieKey;
    private String webReviewId;
    private String author;
    private String content;
    private String Url;
    private String registryType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieKey() {
        return movieKey;
    }

    public void setMovieKey(int movieKey) {
        this.movieKey = movieKey;
    }

    public String getWebReviewId() {
        return webReviewId;
    }

    public void setWebReviewId(String webReviewId) {
        this.webReviewId = webReviewId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getRegistryType() {
        return registryType;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();

        contentValues.put(PopularMovieContract.ReviewEntry.MOVIE_KEY,getMovieKey());
        contentValues.put(PopularMovieContract.ReviewEntry.WEB_REVIEW_ID,getWebReviewId());
        contentValues.put(PopularMovieContract.ReviewEntry.AUTHOR,getAuthor());
        contentValues.put(PopularMovieContract.ReviewEntry.CONTENT,getContent());
        contentValues.put(PopularMovieContract.ReviewEntry.URL,getUrl());
        contentValues.put(PopularMovieContract.ReviewEntry.REGISTRY_TYPE,getRegistryType());

        return contentValues;
    }
}
