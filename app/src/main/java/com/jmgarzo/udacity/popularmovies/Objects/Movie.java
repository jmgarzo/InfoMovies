package com.jmgarzo.udacity.popularmovies.Objects;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;
import com.jmgarzo.udacity.popularmovies.utilities.DataBaseUtils;

/**
 * Created by jmgarzo on 13/02/17.
 */

public class Movie implements Parcelable {

    private String LOG_TAG = Movie.class.getSimpleName();


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

    public Movie() {
    }

    /**
     * Construct a new Movie from a cursor first row.
     *
     * @param cursor
     */
    public Movie(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getCount() > 1) {
                Log.w(LOG_TAG, "Cursor have more than one rows");
            }
            cursorToMovie(cursor);
        }
    }

    public Movie(Cursor cursor, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            cursorToMovie(cursor);
        }
    }

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
     * @return a movie without _id and timestamp to make an insert
     */
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(PopularMovieContract.MovieEntry.POSTER_PATH, getPosterPath());
        contentValues.put(PopularMovieContract.MovieEntry.ADULT, Boolean.toString(isAdult()));
        contentValues.put(PopularMovieContract.MovieEntry.OVERVIEW, getOverview());
        contentValues.put(PopularMovieContract.MovieEntry.RELEASE_DATE, getReleaseDate());
        contentValues.put(PopularMovieContract.MovieEntry.MOVIE_WEB_ID, getMovieWebId());
        contentValues.put(PopularMovieContract.MovieEntry.ORIGINAL_TITLE, getOriginalTitle());
        contentValues.put(PopularMovieContract.MovieEntry.ORIGINAL_LANGUAGE, getOriginalLanguage());
        contentValues.put(PopularMovieContract.MovieEntry.TITLE, getTitle());
        contentValues.put(PopularMovieContract.MovieEntry.BACKDROP_PATH, getBackdropPath());
        contentValues.put(PopularMovieContract.MovieEntry.POPULARITY, getPopularity());
        contentValues.put(PopularMovieContract.MovieEntry.VOTE_COUNT, getVoteCount());
        contentValues.put(PopularMovieContract.MovieEntry.VIDEO, Boolean.toString(isVideo()));
        contentValues.put(PopularMovieContract.MovieEntry.VOTE_AVERAGE, getVoteAverage());
        contentValues.put(PopularMovieContract.MovieEntry.REGISTRY_TYPE, getRegistryType());

        return contentValues;
    }

    private void cursorToMovie(Cursor cursor) {
        id = cursor.getInt(DataBaseUtils.COL_MOVIE_ID);
        posterPath = cursor.getString(DataBaseUtils.COL_MOVIE_POSTER_PATH);
        adult = Boolean.valueOf(cursor.getString(DataBaseUtils.COL_MOVIE_ADULT));
        overview = cursor.getString(DataBaseUtils.COL_MOVIE_OVERVIEW);
        releaseDate = cursor.getString(DataBaseUtils.COL_MOVIE_RELEASE_DATE);
        movieWebId = cursor.getString(DataBaseUtils.COL_MOVIE_MOVIE_WEB_ID);
        originalTitle = cursor.getString(DataBaseUtils.COL_MOVIE_ORIGINAL_TITLE);
        originalLanguage = cursor.getString(DataBaseUtils.COL_MOVIE_ORIGINAL_LANGUAGE);
        title = cursor.getString(DataBaseUtils.COL_MOVIE_TITLE);
        backdropPath = cursor.getString(DataBaseUtils.COL_MOVIE_BACKDROP_PATH);
        popularity = cursor.getDouble(DataBaseUtils.COL_MOVIE_POPULARITY);
        voteCount = cursor.getInt(DataBaseUtils.COL_MOVIE_VOTE_COUNT);
        video = Boolean.valueOf(cursor.getString(DataBaseUtils.COL_MOVIE_VIDEO));
        voteAverage = cursor.getDouble(DataBaseUtils.COL_MOVIE_VOTE_AVERAGE);
        registryType = cursor.getString(DataBaseUtils.COL_MOVIE_REGISTRY_TYPE);
        timestamp = cursor.getString(DataBaseUtils.COL_MOVIE_TIMESTAMP);
    }


    //Parcelable methods

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(posterPath);
        //there is not write method for booleans,
        dest.writeValue(adult);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(movieWebId);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeString(title);
        dest.writeString(backdropPath);
        dest.writeDouble(popularity);
        dest.writeInt(voteCount);
        dest.writeValue(video);
        dest.writeDouble(voteAverage);
        dest.writeString(registryType);
        dest.writeString(timestamp);

    }

    //constructor used for parcel
    public Movie(Parcel parcel) {
        id = parcel.readInt();
        posterPath = parcel.readString();
        //This is the way to read a boolean value
        adult = (boolean) parcel.readValue(null);
        overview = parcel.readString();
        releaseDate = parcel.readString();
        movieWebId = parcel.readString();
        originalTitle = parcel.readString();
        originalLanguage = parcel.readString();
        title = parcel.readString();
        backdropPath = parcel.readString();
        popularity = parcel.readDouble();
        voteCount = parcel.readInt();
        video = (boolean) parcel.readValue(null);
        voteAverage = parcel.readDouble();
        registryType = parcel.readString();
        timestamp = parcel.readString();
    }

    //creator - used when un-parceling our parcel (creating the object)
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }
}
