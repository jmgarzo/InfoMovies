package com.jmgarzo.udacity.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jmgarzo.udacity.popularmovies.utilities.NetworksUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jmgarzo on 09/02/17.
 */


public class FetchDataMovies extends AsyncTask<Context, Void, String[]> {

    private final String LOG_TAG = FetchDataMovies.class.getSimpleName();

    @Override
    protected String[] doInBackground(Context... contexts) {

        if (contexts.length == 0) {
            return null;
        }

        URL moviesURL = NetworksUtils.buildURL(contexts[0]);

        try {
            String jsonMoviesResponse = NetworksUtils.getResponseFromHttpUrl(moviesURL);
            ArrayList<String> posterPathList = getPostersFromJson(jsonMoviesResponse);

        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
        }


        return new String[0];
    }

    private ArrayList<String> getPostersFromJson(String moviesJsonStr) {
        final String MOVIE_RESULTS = "results";
        final String MOVIE_POSTER_PATH = "poster_path";
        ArrayList<String> posterPathList=null;

        JSONObject moviesJson = null;
        try {
            moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(MOVIE_RESULTS);
            String posterPath = "";
            posterPathList = new ArrayList<>();
            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject jsonMovie = moviesArray.getJSONObject(i);
                posterPath = jsonMovie.getString(MOVIE_POSTER_PATH);
                posterPathList.add(posterPath);

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.toString());
        }

        return posterPathList;
    }


}
