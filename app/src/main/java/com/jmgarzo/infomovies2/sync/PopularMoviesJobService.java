package com.jmgarzo.infomovies2.sync;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by jmgarzo on 17/03/17.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PopularMoviesJobService extends JobService {

    private AsyncTask<Void, Void, Void> mFetchMoviesTask;

    @Override
    public boolean onStartJob(final JobParameters params) {

        mFetchMoviesTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Context context = getApplicationContext();
                PopularMoviesSyncTask.syncMovies(context);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(params, false);
            }
        };
        mFetchMoviesTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
