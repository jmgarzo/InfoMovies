package com.jmgarzo.udacity.popularmovies.Objects;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;
import com.jmgarzo.udacity.popularmovies.utilities.DataBaseUtils;

/**
 * Created by jmgarzo on 08/03/17.
 */

public class Trailer {

    private String LOG_TAG = Trailer.class.getSimpleName();



    private int id;
    private int movieKey;
    private String webTrailerId;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private String size;
    private String type;
    private String registryType;

    public Trailer (){};

    public Trailer(Cursor cursor){
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getCount() > 1) {
                Log.w(LOG_TAG, "Cursor have more than one rows");
            }
            cursorToTrailer(cursor);
        }
    }

    public Trailer(Cursor cursor, int position){
        if(cursor != null && cursor.moveToPosition(position)){
            cursorToTrailer(cursor);
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

    public String getWebTrailerId() {
        return webTrailerId;
    }

    public void setWebTrailerId(String webTrailerId) {
        this.webTrailerId = webTrailerId;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegistryType() {
        return registryType;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }


    public Uri getTrailerUri() {

        final String BASE_YOUTUBE_URI = "http://www.youtube.com/watch";
        final String VIDEO_PARAM = "v";
        return Uri.parse(BASE_YOUTUBE_URI).buildUpon().appendQueryParameter(VIDEO_PARAM, getKey()).build();
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();

        contentValues.put(PopularMovieContract.TrailerEntry.MOVIE_KEY,getMovieKey());
        contentValues.put(PopularMovieContract.TrailerEntry.WEB_TRAILER_ID,getWebTrailerId());
        contentValues.put(PopularMovieContract.TrailerEntry.ISO_639_1,getIso_639_1());
        contentValues.put(PopularMovieContract.TrailerEntry.ISO_3166_1,getIso_3166_1());
        contentValues.put(PopularMovieContract.TrailerEntry.KEY,getKey());
        contentValues.put(PopularMovieContract.TrailerEntry.NAME,getName());
        contentValues.put(PopularMovieContract.TrailerEntry.SITE,getSite());
        contentValues.put(PopularMovieContract.TrailerEntry.SIZE,getSize());
        contentValues.put(PopularMovieContract.TrailerEntry.TYPE,getType());
        contentValues.put(PopularMovieContract.TrailerEntry.REGISTRY_TYPE,getRegistryType());

        return contentValues;
    }

    private void cursorToTrailer(Cursor cursor){

        id = cursor.getInt(DataBaseUtils.COL_TRAILER_ID);
        movieKey = cursor.getInt(DataBaseUtils.COL_TRAILER_MOVIE_KEY);
        webTrailerId = cursor.getString(DataBaseUtils.COL_TRAILER_WEB_TRAILER_ID);
        iso_639_1 = cursor.getString(DataBaseUtils.COL_TRAILER_ISO_639_1);
        iso_3166_1= cursor.getString(DataBaseUtils.COL_TRAILER_ISO_3166_1);
        key = cursor.getString(DataBaseUtils.COL_TRAILER_KEY);
        name = cursor.getString(DataBaseUtils.COL_TRAILER_NAME);
        site = cursor.getString(DataBaseUtils.COL_TRAILER_SITE);
        size = cursor.getString(DataBaseUtils.COL_TRAILER_SIZE);
        type = cursor.getString(DataBaseUtils.COL_TRAILER_TYPE);
        registryType = cursor.getString(DataBaseUtils.COL_TRAILER_REGISTRY_TYPE);

    }
}
