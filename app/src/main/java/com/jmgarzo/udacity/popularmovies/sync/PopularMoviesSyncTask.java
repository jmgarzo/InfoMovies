package com.jmgarzo.udacity.popularmovies.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;
import com.jmgarzo.udacity.popularmovies.utilities.NetworksUtils;

import java.util.ArrayList;

/**
 * Created by jmgarzo on 17/03/17.
 */

public class PopularMoviesSyncTask {

    synchronized public static void syncMovies(Context context) {

        try {

            ArrayList<Movie> moviesList = NetworksUtils.getMovies(context);

            if (moviesList != null && moviesList.size() > 0) {
                ContentValues[] contentValues = new ContentValues[moviesList.size()];
                for (int i = 0; i< moviesList.size(); i++){
                    Movie movie = moviesList.get(i);
                    contentValues[i]= movie.getContentValues();
                }

                ContentResolver contentResolver = context.getContentResolver();
                contentResolver.delete(
                        PopularMovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null);

                contentResolver.bulkInsert(PopularMovieContract.MovieEntry.CONTENT_URI,
                        contentValues);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
