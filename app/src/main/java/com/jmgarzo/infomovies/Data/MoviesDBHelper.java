package com.jmgarzo.infomovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jmgarzo on 15/07/2016.
 */
public class MoviesDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "movie.db";

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MoviesContract.MoviesEntry.TABLE_NAME + " ( " +
                        MoviesContract.MoviesEntry._ID + " INTEGER PRIMARY KEY, " +
                        MoviesContract.MoviesEntry.POSTER_PATH + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.ADULT + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.OVERVIEW + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.RELEASE_DATE + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.MOVIE_WEB_ID + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.ORIGINAL_TITLE + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.TITLE + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.BACKDROP_PATH + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.POPULARITY + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.VOTE_COUNT + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.VIDEO + " TEXT NOT NULL, " +
                        MoviesContract.MoviesEntry.VOTE_AVERAGE + " TEXT NOT NULL " +
                        " );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);

    }
}
