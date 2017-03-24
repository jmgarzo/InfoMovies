package com.jmgarzo.udacity.popularmovies.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;

/**
 * Created by jmgarzo on 18/03/17.
 */

public class PopularMoviesSyncUtils {

    private static boolean sInitialized;

//    private static final int SYNC_INTERVAL_HOURS =3;
//    private static final int SYNC_INTERVAL_SECONDS = (int) java.util.concurrent.TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
//    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;


    private static final int SYNC_INTERVAL_SECONDS = 60;
    private static final int SYNC_FLEXTIME_SECONDS =SYNC_INTERVAL_SECONDS/3 ;

    private static final String MOVIES_SYNC_TAG="movies-sync";

    static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context){
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncMoviesJob = dispatcher.newJobBuilder()
                .setService(PopularMoviesJobService.class)
                .setTag(MOVIES_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncMoviesJob);
    }


    synchronized public static void initialize(@NonNull final Context context){
        if(sInitialized) return;
        sInitialized= true;
        scheduleFirebaseJobDispatcherSync(context);

        Thread checkForEmpty = new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = context.getContentResolver().query(
                        PopularMovieContract.MovieEntry.CONTENT_URI,
                        new String[]{PopularMovieContract.MovieEntry._ID},
                        null,
                        null,
                        null
                );

                if(cursor == null || cursor.getCount() == 0){
                    startImmediateSync(context);
                }
                cursor.close();
            }
        });
        checkForEmpty.start();
    }

    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, PopularMoviesSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}
