package com.jmgarzo.udacity.popularmovies.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jmgarzo on 02/03/17.
 */

public class PopularMovieContract {
    public static final String CONTENT_AUTHORITY ="com.jmgarzo.udacity.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE="movie";
    public static final String PATH_TRAILER="trailer";
    public static final String PATH_REVIEW = "review";

    //This value must be the same that @string.xml pref_sort_by_label_top_rated
    public static final String TOP_RATE_REGISTRY_TYPE="top_rated";
    //This value must be the same that @string.xml pref_sort_by_value_most_popular
    public static final String MOST_POPULAR_REGISTRY_TYPE = "most_popular";
    public static final String FAVORITE_REGISTRY_TYPE = "favorite";




    public static final class MovieEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME = "movie";
        public static final String _ID = "_id";

        //Data from JSON
        public static final String POSTER_PATH = "poster_path";
        public static final String ADULT = "adult";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        //public static final String GENRE_IDS = "genre_ids";
        public static final String MOVIE_WEB_ID = "id";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String TITLE = "title";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VIDEO = "video";
        public static final String VOTE_AVERAGE = "vote_average";

        //to distinguish between most_popular, top_rate, and favorite
        public static final String REGISTRY_TYPE="registry_type";
        public static final String TIMESTAMP ="timestamp";

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;



    }

    public static final class TrailerEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        public static final String TABLE_NAME = "trailer";
        public static final String _ID = "_id";
        //Foreign key
        public static final String MOVIE_KEY = "id_movie";
        public static final String WEB_TRAILER_ID = "id";
        public static final String ISO_639_1 = "iso_639_1";
        public static final String ISO_3166_1 = "iso_3166_1";
        public static final String KEY = "key";
        public static final String NAME = "name";
        public static final String SITE = "site";
        public static final String SIZE = "size";
        public static final String TYPE = "type";
        public static final String REGISTRY_TYPE = "registry_type";

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }

    public static final class ReviewEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String TABLE_NAME = "review";
        public static final String _ID ="_id";
        //Foreign key
        public static final String MOVIE_KEY = "id_movie";
        public static final String WEB_REVIEW_ID = "id";
        public static final String AUTHOR = "author";
        public static final String CONTENT = "content";
        public static final String URL = "url";
        public static final String REGISTRY_TYPE = "registry_type";

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

    }
}
