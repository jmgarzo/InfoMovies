package com.jmgarzo.udacity.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by jmgarzo on 02/03/17.
 */

public class MovieContract {

    public static final class MovieEntry implements BaseColumns{



        public static final String TABLE_NAME = "movie";
        public static final String _ID = "_id";

        //Data from JSON
        public static final String POSTER_PATH = "poster_path";
        public static final String ADULT = "adult";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        //TODO:IF I have time store Genre_ids
        //public static final String GENRE_IDS = "genre_ids";
        public static final String MOVIE_WEB_ID = "movie_web_id";
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



    }

    public static final class TrailerEntry implements BaseColumns{

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
    }
}
