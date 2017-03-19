package com.jmgarzo.udacity.popularmovies.utilities;

import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;

/**
 * Created by jmgarzo on 19/03/17.
 */

public class DataBaseUtils {

    public static final String[] MOVIE_COLUMS = {
            PopularMovieContract.MovieEntry._ID,
            PopularMovieContract.MovieEntry.POSTER_PATH,
            PopularMovieContract.MovieEntry.ADULT,
            PopularMovieContract.MovieEntry.OVERVIEW,
            PopularMovieContract.MovieEntry.RELEASE_DATE,
            PopularMovieContract.MovieEntry.MOVIE_WEB_ID,
            PopularMovieContract.MovieEntry.ORIGINAL_TITLE,
            PopularMovieContract.MovieEntry.ORIGINAL_LANGUAGE,
            PopularMovieContract.MovieEntry.TITLE,
            PopularMovieContract.MovieEntry.BACKDROP_PATH,
            PopularMovieContract.MovieEntry.POPULARITY,
            PopularMovieContract.MovieEntry.VOTE_COUNT,
            PopularMovieContract.MovieEntry.VIDEO,
            PopularMovieContract.MovieEntry.VOTE_AVERAGE,
            PopularMovieContract.MovieEntry.REGISTRY_TYPE,
            PopularMovieContract.MovieEntry.TIMESTAMP
    };

    public static final int COL_MOVIE_ID = 0;
    public static final int COL_MOVIE_POSTER_PATH = 1;
    public static final int COL_MOVIE_ADULT = 2;
    public static final int COL_MOVIE_OVERVIEW = 3;
    public static final int COL_MOVIE_RELEASE_DATE = 4;
    public static final int COL_MOVIE_MOVIE_WEB_ID = 5;
    public static final int COL_MOVIE_ORIGINAL_TITLE = 6;
    public static final int COL_MOVIE_ORIGINAL_LANGUAGE = 7;
    public static final int COL_MOVIE_TITLE = 8;
    public  static final int COL_MOVIE_BACKDROP_PATH = 9;
    public static final int COL_MOVIE_POPULARITY = 10;
    public static final int COL_MOVIE_VOTE_COUNT = 11;
    public static final int COL_MOVIE_VIDEO = 12;
    public static final int COL_MOVIE_VOTE_AVERAGE = 13;
    public static final int COL_MOVIE_REGISTRY_TYPE = 14;
    public static final int COL_MOVIE_TIMESTAMP = 15;

}

