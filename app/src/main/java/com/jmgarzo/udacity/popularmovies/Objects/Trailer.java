package com.jmgarzo.udacity.popularmovies.Objects;

import android.content.ContentValues;

import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;

/**
 * Created by jmgarzo on 08/03/17.
 */

public class Trailer {


    int id;
    int movieKey;
    String webTrailerId;
    String iso_639_1;
    String iso_3166_1;
    String key;
    String name;
    String site;
    String size;
    String type;
    String registryType;

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
}
