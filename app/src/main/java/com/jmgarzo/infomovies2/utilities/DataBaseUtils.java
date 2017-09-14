package com.jmgarzo.infomovies2.utilities;

import com.jmgarzo.infomovies2.data.PopularMovieContract;

/**
 * Created by jmgarzo on 19/03/17.
 */

public class DataBaseUtils {

    public static final String[] MOVIE_COLUMNS = {
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
    public static final int COL_MOVIE_BACKDROP_PATH = 9;
    public static final int COL_MOVIE_POPULARITY = 10;
    public static final int COL_MOVIE_VOTE_COUNT = 11;
    public static final int COL_MOVIE_VIDEO = 12;
    public static final int COL_MOVIE_VOTE_AVERAGE = 13;
    public static final int COL_MOVIE_REGISTRY_TYPE = 14;
    public static final int COL_MOVIE_TIMESTAMP = 15;


    public static final String[] TRAILER_COLUMNS =

            {
                    PopularMovieContract.TrailerEntry._ID,
                    PopularMovieContract.TrailerEntry.MOVIE_KEY,
                    PopularMovieContract.TrailerEntry.WEB_TRAILER_ID,
                    PopularMovieContract.TrailerEntry.ISO_639_1,
                    PopularMovieContract.TrailerEntry.ISO_3166_1,
                    PopularMovieContract.TrailerEntry.KEY,
                    PopularMovieContract.TrailerEntry.NAME,
                    PopularMovieContract.TrailerEntry.SITE,
                    PopularMovieContract.TrailerEntry.SIZE,
                    PopularMovieContract.TrailerEntry.TYPE,
                    PopularMovieContract.TrailerEntry.REGISTRY_TYPE

            };

    public static final int COL_TRAILER_ID = 0;
    public static final int COL_TRAILER_MOVIE_KEY = 1;
    public static final int COL_TRAILER_WEB_TRAILER_ID = 2;
    public static final int COL_TRAILER_ISO_639_1 = 3;
    public static final int COL_TRAILER_ISO_3166_1 = 4;
    public static final int COL_TRAILER_KEY = 5;
    public static final int COL_TRAILER_NAME = 6;
    public static final int COL_TRAILER_SITE = 7;
    public static final int COL_TRAILER_SIZE = 8;
    public static final int COL_TRAILER_TYPE = 9;
    public static final int COL_TRAILER_REGISTRY_TYPE = 10;

    public static final String[] REVIEW_COLUMNS ={
            PopularMovieContract.ReviewEntry._ID,
            PopularMovieContract.ReviewEntry.MOVIE_KEY,
            PopularMovieContract.ReviewEntry.WEB_REVIEW_ID,
            PopularMovieContract.ReviewEntry.AUTHOR,
            PopularMovieContract.ReviewEntry.CONTENT,
            PopularMovieContract.ReviewEntry.URL,
            PopularMovieContract.ReviewEntry.REGISTRY_TYPE
    };

    public static final int COL_REVIEW_ID = 0;
    public static final int COL_REVIEW_MOVIE_KEY = 1;
    public static final int COL_REVIEW_WEB_REVIEW_ID = 2;
    public static final int COL_REVIEW_AUTHOR= 3;
    public static final int COL_REVIEW_CONTENT= 4;
    public static final int COL_REVIEW_URL= 5;
    public static final int COL_REVIEW_REGISTRY_TYPE= 6;







}

