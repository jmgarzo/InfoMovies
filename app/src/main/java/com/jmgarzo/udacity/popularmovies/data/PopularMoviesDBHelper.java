package com.jmgarzo.udacity.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jmgarzo on 14/03/17.
 */

public class PopularMoviesDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "popular_movies.db";


    public PopularMoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + PopularMovieContract.MovieEntry.TABLE_NAME + " ( " +
                        PopularMovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        PopularMovieContract.MovieEntry.POSTER_PATH + " TEXT NOT NULL, " +
                        PopularMovieContract.MovieEntry.ADULT + " TEXT NOT NULL, " +
                        PopularMovieContract.MovieEntry.OVERVIEW + " TEXT NOT NULL, " +
                        PopularMovieContract.MovieEntry.RELEASE_DATE + " TEXT NOT NULL, " +
                        PopularMovieContract.MovieEntry.MOVIE_WEB_ID + " TEXT NOT NULL, " +
                        PopularMovieContract.MovieEntry.ORIGINAL_TITLE + " TEXT NOT NULL, " +
                        PopularMovieContract.MovieEntry.ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                        PopularMovieContract.MovieEntry.TITLE + " TEXT NOT NULL, " +
                        PopularMovieContract.MovieEntry.BACKDROP_PATH + " TEXT NOT NULL, " +
                        PopularMovieContract.MovieEntry.POPULARITY + " REAL NOT NULL, " +
                        PopularMovieContract.MovieEntry.VOTE_COUNT + " INTEGER NOT NULL, " +
                        PopularMovieContract.MovieEntry.VIDEO + " TEXT NOT NULL, " +
                        PopularMovieContract.MovieEntry.VOTE_AVERAGE + " REAL NOT NULL, " +
                        PopularMovieContract.MovieEntry.REGISTRY_TYPE + " TEXT NOT NULL , " +
                        PopularMovieContract.MovieEntry.TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                        " );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);

        final String SQL_CREATE_TRAILER_TABLE =
                "CREATE TABLE " + PopularMovieContract.TrailerEntry.TABLE_NAME + " ( " +
                        PopularMovieContract.TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        PopularMovieContract.TrailerEntry.MOVIE_KEY + " INTEGER NOT NULL , " +
                        PopularMovieContract.TrailerEntry.WEB_TRAILER_ID + " TEXT NOT NULL, " +
                        PopularMovieContract.TrailerEntry.ISO_639_1 + " TEXT NOT NULL, " +
                        PopularMovieContract.TrailerEntry.ISO_3166_1 + " TEXT NOT NULL, " +
                        PopularMovieContract.TrailerEntry.KEY + " TEXT NOT NULL, " +
                        PopularMovieContract.TrailerEntry.NAME + " TEXT NOT NULL, " +
                        PopularMovieContract.TrailerEntry.SITE + " TEXT NOT NULL, " +
                        PopularMovieContract.TrailerEntry.SIZE + " TEXT NOT NULL, " +
                        PopularMovieContract.TrailerEntry.TYPE + " TEXT NOT NULL, " +
                        PopularMovieContract.TrailerEntry.REGISTRY_TYPE + " TEXT NOT NULL, " +
                        " FOREIGN KEY (" + PopularMovieContract.TrailerEntry.MOVIE_KEY + ") REFERENCES " +
                        PopularMovieContract.MovieEntry.TABLE_NAME + " (" + PopularMovieContract.MovieEntry._ID + ") ON DELETE CASCADE " +
                        ");";

        db.execSQL(SQL_CREATE_TRAILER_TABLE);

        final String SQL_CREATE_REVIEW_TABLE =
                "CREATE TABLE " + PopularMovieContract.ReviewEntry.TABLE_NAME + " ( " +
                        PopularMovieContract.ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                        PopularMovieContract.ReviewEntry.MOVIE_KEY + " INTEGER NOT NULL, " +
                        PopularMovieContract.ReviewEntry.WEB_REVIEW_ID + " TEXT NOT NULL UNIQUE, " +
                        PopularMovieContract.ReviewEntry.AUTHOR + " TEXT NOT NULL, " +
                        PopularMovieContract.ReviewEntry.CONTENT + " TEXT NOT NULL, " +
                        PopularMovieContract.ReviewEntry.URL + " TEXT NOT NULL, " +
                        " FOREIGN KEY (" + PopularMovieContract.ReviewEntry.MOVIE_KEY + ") REFERENCES " +
                        PopularMovieContract.MovieEntry.TABLE_NAME + " (" + PopularMovieContract.MovieEntry._ID + ") ON DELETE CASCADE " +
                        ");";
        db.execSQL(SQL_CREATE_REVIEW_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + PopularMovieContract.MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PopularMovieContract.TrailerEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PopularMovieContract.ReviewEntry.TABLE_NAME);

    }
}
