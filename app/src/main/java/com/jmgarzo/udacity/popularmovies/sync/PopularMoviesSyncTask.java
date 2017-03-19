package com.jmgarzo.udacity.popularmovies.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.Objects.Review;
import com.jmgarzo.udacity.popularmovies.Objects.Trailer;
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
                for (int i = 0; i < moviesList.size(); i++) {
                    Movie movie = moviesList.get(i);
                    contentValues[i] = movie.getContentValues();
                }

                ContentResolver contentResolver = context.getContentResolver();
                contentResolver.delete(
                        PopularMovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null);

                contentResolver.bulkInsert(PopularMovieContract.MovieEntry.CONTENT_URI,
                        contentValues);

                syncTrailersAndReviews(context);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void syncTrailersAndReviews(Context context) {

        try {


            //We update all movies trailers in DB, favorite's trailers too.
            String[] projection = {PopularMovieContract.MovieEntry._ID,
                    PopularMovieContract.MovieEntry.MOVIE_WEB_ID,
                    PopularMovieContract.MovieEntry.REGISTRY_TYPE};

            Cursor moviesCursor = context.getContentResolver().query(
                    PopularMovieContract.MovieEntry.CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);

            if (moviesCursor != null && moviesCursor.moveToFirst()) {
                context.getContentResolver().delete(
                        PopularMovieContract.TrailerEntry.CONTENT_URI,
                        null,
                        null);

                insertNewTrailers(context, moviesCursor);
                moviesCursor.moveToFirst();
                insertNewReviews(context,moviesCursor);
                moviesCursor.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertNewTrailers(Context context, Cursor moviesCursor) {

        do {
            int indexId = moviesCursor.getColumnIndex(PopularMovieContract.MovieEntry._ID);
            int _id= moviesCursor.getInt(indexId);
            int indexMovieId = moviesCursor.getColumnIndex(PopularMovieContract.MovieEntry.MOVIE_WEB_ID);
            String movieId = moviesCursor.getString(indexMovieId);
            int indexRegistryType = moviesCursor.getColumnIndex(PopularMovieContract.MovieEntry.REGISTRY_TYPE);
            String registryType = moviesCursor.getString(indexRegistryType);

            ArrayList<Trailer> trailersList = NetworksUtils.getTrailers(movieId);
            if (trailersList != null && trailersList.size() > 0) {
                ContentValues[] trailersContentValues = new ContentValues[trailersList.size()];
                for (int i = 0; i < trailersList.size(); i++) {
                    trailersList.get(i).setMovieKey(_id);
                    trailersList.get(i).setRegistryType(registryType);
                    trailersContentValues[i] = trailersList.get(i).getContentValues();
                }

                int insercciones = context.getContentResolver().bulkInsert(PopularMovieContract.TrailerEntry.CONTENT_URI,
                        trailersContentValues);
                insercciones++;
            }
        } while (moviesCursor.moveToNext());
    }


    private static void insertNewReviews(Context context, Cursor moviesCursor) {

        do {
            int indexId = moviesCursor.getColumnIndex(PopularMovieContract.MovieEntry._ID);
            int _id= moviesCursor.getInt(indexId);
            int indexMovieId = moviesCursor.getColumnIndex(PopularMovieContract.MovieEntry.MOVIE_WEB_ID);
            String movieId = moviesCursor.getString(indexMovieId);
            int indexRegistryType = moviesCursor.getColumnIndex(PopularMovieContract.MovieEntry.REGISTRY_TYPE);
            String registryType = moviesCursor.getString(indexRegistryType);

            ArrayList<Review> reviewsList = NetworksUtils.getReviews(movieId);
            if (reviewsList != null && reviewsList.size() > 0) {
                ContentValues[] trailersContentValues = new ContentValues[reviewsList.size()];
                for (int i = 0; i < reviewsList.size(); i++) {
                    reviewsList.get(i).setMovieKey(_id);
                    reviewsList.get(i).setRegistryType(registryType);
                    trailersContentValues[i] = reviewsList.get(i).getContentValues();
                }

                int insercciones = context.getContentResolver().bulkInsert(PopularMovieContract.ReviewEntry.CONTENT_URI,
                        trailersContentValues);
                insercciones++;
            }
        } while (moviesCursor.moveToNext());
    }



}
