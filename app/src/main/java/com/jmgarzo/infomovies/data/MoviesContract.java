package com.jmgarzo.infomovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jmgarzo on 15/07/2016.
 */
public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.jmgarzo.infomovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";
    public static final String PATH_VIDEO = "video";
    public static final String PATH_REVIEW = "review";
    public static final String PATH_FAVORITE = "favorite";


    public static final class MoviesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();


        public static final String TABLE_NAME = "movie";
        public static final String _ID = "_id";
        public static final String POSTER_PATH = "poster_path";
        public static final String ADULT = "adult";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String MOVIE_WEB_ID = "movie_web_id";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String TITLE = "title";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VIDEO = "video";
        public static final String VOTE_AVERAGE = "vote_average";




        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static Uri buildMoviesWithIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieWithWebId(String MovieWebId) {
            return CONTENT_URI.buildUpon().appendPath(MovieWebId).build();
        }

    }


    public static final class VideoEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_VIDEO).build();

        public static final String TABLE_NAME = "video";
        public static final String MOVIE_KEY = "id_movie";
        public static final String ID = "id";
        public static final String ISO_639_1 = "iso_639_1";
        public static final String ISO_3166_1 = "iso_3166_1";
        public static final String KEY = "key";
        public static final String NAME = "name";
        public static final String SITE = "site";
        public static final String SIZE = "size";
        public static final String TYPE = "type";



        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static Uri buildVideosUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildVideoWithMovieId(String movieWebId) {
            return CONTENT_URI.buildUpon().appendPath(movieWebId).build();
        }


    }



    public static final class ReviewEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String TABLE_NAME = "review";
        public static final String MOVIE_KEY = "id_movie";
        public static final String ID = "id";
        public static final String AUTHOR= "author";
        public static final String CONTENT = "content";
        public static final String URL = "url";

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static Uri buildReviewUri (long id){
            return  ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static Uri buildReviewWithMovieId(String movieWebId){
            return  CONTENT_URI.buildUpon().appendPath(movieWebId).build();
        }
    }

    public static final class FavoriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String TABLE_NAME = "favorite";


        public static final String POSTER_PATH = "poster_path";
        public static final String ADULT = "adult";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String MOVIE_WEB_ID = "movie_web_id";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String TITLE = "title";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VIDEO = "video";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String ADD_DATE = "add_date";


        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static Uri buildFavoriteUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildFavoriteWithMovieId(String movieWebId) {
            return CONTENT_URI.buildUpon().appendPath(movieWebId).build();
        }

    }


}
