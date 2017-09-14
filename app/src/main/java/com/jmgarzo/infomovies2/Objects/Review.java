package com.jmgarzo.infomovies2.Objects;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.jmgarzo.infomovies2.utilities.DataBaseUtils;
import com.jmgarzo.infomovies2.data.PopularMovieContract;

/**
 * Created by jmgarzo on 12/03/17.
 */

public class Review {
    private String LOG_TAG = Review.class.getSimpleName();


    private int id;
    private int movieKey;
    private String webReviewId;
    private String author;
    private String content;
    private String url;
    private String registryType;

    public Review(){}

    public Review(Cursor cursor){
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getCount() > 1) {
                Log.w(LOG_TAG, "Cursor have more than one rows");
            }
            cursorToReview(cursor);
        }
    }

    public Review(Cursor cursor, int position){
        if(cursor != null && cursor.moveToPosition(position)){
            cursorToReview(cursor);
        }
    }


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
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        contentValues.put(PopularMovieContract.ReviewEntry.URL, getUrl());
        contentValues.put(PopularMovieContract.ReviewEntry.REGISTRY_TYPE,getRegistryType());

        return contentValues;
    }

    private void cursorToReview(Cursor cursor){

        id = cursor.getInt(DataBaseUtils.COL_REVIEW_ID);
        movieKey = cursor.getInt(DataBaseUtils.COL_REVIEW_MOVIE_KEY);
        webReviewId = cursor.getString(DataBaseUtils.COL_REVIEW_WEB_REVIEW_ID);
        author = cursor.getString(DataBaseUtils.COL_REVIEW_AUTHOR);
        content = cursor.getString(DataBaseUtils.COL_REVIEW_CONTENT);
        url = cursor.getString(DataBaseUtils.COL_REVIEW_URL);
        registryType = cursor.getString(DataBaseUtils.COL_REVIEW_REGISTRY_TYPE);
    }
}
