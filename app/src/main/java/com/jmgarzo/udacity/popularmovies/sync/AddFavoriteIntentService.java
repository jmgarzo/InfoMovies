package com.jmgarzo.udacity.popularmovies.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.jmgarzo.udacity.popularmovies.DetailFragment;
import com.jmgarzo.udacity.popularmovies.Objects.Movie;

/**
 * Created by jmgarzo on 22/03/17.
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
