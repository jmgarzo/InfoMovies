package com.jmgarzo.infomovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jmgarzo on 15/07/2016.
 */
public class MoviesDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "movie.db";

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MoviesContract.MoviesEntry.TABLE_NAME + " ( " +
                        MoviesContract.MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        MoviesContract.MoviesEntry.POSTER_PATH + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.ADULT + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.OVERVIEW + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.RELEASE_DATE + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.MOVIE_WEB_ID + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.ORIGINAL_TITLE + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.TITLE + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.BACKDROP_PATH + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.POPULARITY + " REAL NOT NULL, " +
                        MoviesContract.MoviesEntry.VOTE_COUNT + " INTEGER NOT NULL, " +
                        MoviesContract.MoviesEntry.VIDEO + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.VOTE_AVERAGE + " REAL NOT NULL, " +
                        MoviesContract.MoviesEntry.MOST_POPULAR + " INTEGER NOT NULL , " +
                        MoviesContract.MoviesEntry.TOP_RATE + " INTEGER NOT NULL  " +
                                " );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);


        final String SQL_CREATE_VIDEO_TABLE =
                "CREATE TABLE " + MoviesContract.VideoEntry.TABLE_NAME + " ( " +
                        MoviesContract.VideoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        MoviesContract.VideoEntry.MOVIE_KEY + " INTEGER NOT NULL , " +
                        MoviesContract.VideoEntry.ID + " TEXT NOT NULL UNIQUE, " +
                        MoviesContract.VideoEntry.ISO_639_1 + " TEXT NOT NULL, " +
                        MoviesContract.VideoEntry.ISO_3166_1 + " TEXT NOT NULL, " +
                        MoviesContract.VideoEntry.KEY + " TEXT NOT NULL, " +
                        MoviesContract.VideoEntry.NAME + " TEXT NOT NULL, " +
                        MoviesContract.VideoEntry.SITE + " TEXT NOT NULL, " +
                        MoviesContract.VideoEntry.SIZE + " TEXT NOT NULL, " +
                        MoviesContract.VideoEntry.TYPE + " TEXT NOT NULL, " +
                        " FOREIGN KEY (" + MoviesContract.VideoEntry.MOVIE_KEY + ") REFERENCES " +
                        MoviesContract.MoviesEntry.TABLE_NAME + " (" + MoviesContract.MoviesEntry._ID + ") " +
                        ");";

        db.execSQL(SQL_CREATE_VIDEO_TABLE);


        final String SQL_CREATE_REVIEW_TABLE =
                "CREATE TABLE " + MoviesContract.ReviewEntry.TABLE_NAME + " ( " +
                        MoviesContract.ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                        MoviesContract.ReviewEntry.MOVIE_KEY + " INTEGER NOT NULL, " +
                        MoviesContract.ReviewEntry.ID + " TEXT NOT NULL UNIQUE, " +
                        MoviesContract.ReviewEntry.AUTHOR + " TEXT NOT NULL, " +
                        MoviesContract.ReviewEntry.CONTENT + " TEXT NOT NULL, "+
                        MoviesContract.ReviewEntry.URL + " TEXT NOT NULL, " +
                        " FOREIGN KEY (" + MoviesContract.ReviewEntry.MOVIE_KEY + ") REFERENCES " +
                        MoviesContract.MoviesEntry.TABLE_NAME + " (" + MoviesContract.MoviesEntry._ID + " ) " +
                        ");";
        db.execSQL(SQL_CREATE_REVIEW_TABLE);


        //FAVORITE

        final String SQL_CREATE_FAVORITE_TABLE =
                "CREATE TABLE " + MoviesContract.FavoriteMovieEntry.TABLE_NAME + " ( " +
                        MoviesContract.FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        MoviesContract.FavoriteMovieEntry.POSTER_PATH + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.ADULT + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.OVERVIEW + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.RELEASE_DATE + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.MOVIE_WEB_ID + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.ORIGINAL_TITLE + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.TITLE + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.BACKDROP_PATH + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.POPULARITY + " REAL NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.VOTE_COUNT + " INTEGER NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.VIDEO + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.VOTE_AVERAGE + " REAL NOT NULL, " +
                        MoviesContract.FavoriteMovieEntry.ADD_DATE + " TEXT NOT NULL " +
                        " );";

        db.execSQL(SQL_CREATE_FAVORITE_TABLE);

        final String SQL_CREATE_FAVORITE_VIDEO_TABLE =
                "CREATE TABLE " + MoviesContract.FavoriteVideoEntry.TABLE_NAME + " ( " +
                        MoviesContract.FavoriteVideoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        MoviesContract.FavoriteVideoEntry.MOVIE_KEY + " INTEGER NOT NULL , " +
                        MoviesContract.FavoriteVideoEntry.ID + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteVideoEntry.ISO_639_1 + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteVideoEntry.ISO_3166_1 + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteVideoEntry.KEY + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteVideoEntry.NAME + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteVideoEntry.SITE + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteVideoEntry.SIZE + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteVideoEntry.TYPE + " TEXT NOT NULL, " +
                        " FOREIGN KEY (" + MoviesContract.FavoriteVideoEntry.MOVIE_KEY + ") REFERENCES " +
                        MoviesContract.FavoriteMovieEntry.TABLE_NAME + " (" + MoviesContract.FavoriteMovieEntry._ID + ") " +
                        ");";

        db.execSQL(SQL_CREATE_FAVORITE_VIDEO_TABLE);

        final String SQL_CREATE_FACORITE_REVIEW_TABLE =
                "CREATE TABLE " + MoviesContract.FavoriteReviewEntry.TABLE_NAME + " ( " +
                        MoviesContract.FavoriteReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                        MoviesContract.FavoriteReviewEntry.MOVIE_KEY + " INTEGER NOT NULL, " +
                        MoviesContract.FavoriteReviewEntry.ID + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteReviewEntry.AUTHOR + " TEXT NOT NULL, " +
                        MoviesContract.FavoriteReviewEntry.CONTENT + " TEXT NOT NULL, "+
                        MoviesContract.FavoriteReviewEntry.URL + " TEXT NOT NULL, " +
                        " FOREIGN KEY (" + MoviesContract.FavoriteReviewEntry.MOVIE_KEY + ") REFERENCES " +
                        MoviesContract.FavoriteMovieEntry.TABLE_NAME + " (" + MoviesContract.FavoriteMovieEntry._ID + " ) " +
                        ");";
        db.execSQL(SQL_CREATE_FACORITE_REVIEW_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.VideoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.ReviewEntry.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.FavoriteMovieEntry.TABLE_NAME);

    }


}
