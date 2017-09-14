package com.jmgarzo.infomovies2.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.jmgarzo.infomovies2.DetailFragment;
import com.jmgarzo.infomovies2.Objects.Movie;

/**
 * Created by jmgarzo on 22/03/17.
 */

/**
 * This Intent Service add a movie to favorites.
 */
public class AddFavoriteIntentService extends IntentService {

    public AddFavoriteIntentService() {
        super("AddFavoriteIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Movie movie = intent.getParcelableExtra(DetailFragment.FAVORITE_MOVIE_TAG);
        PopularMoviesSyncTask.addFavorite(this,movie);
    }
}
