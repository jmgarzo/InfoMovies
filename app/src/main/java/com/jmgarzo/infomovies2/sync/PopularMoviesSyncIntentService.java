package com.jmgarzo.infomovies2.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by jmgarzo on 18/03/17.
 */

public class PopularMoviesSyncIntentService extends IntentService {
    public PopularMoviesSyncIntentService(){
        super("PopularMoviesSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        PopularMoviesSyncTask.syncMovies(this);
    }
}
