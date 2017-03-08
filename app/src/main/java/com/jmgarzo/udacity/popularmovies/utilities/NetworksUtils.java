package com.jmgarzo.udacity.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.jmgarzo.udacity.popularmovies.BuildConfig;
import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.Objects.Trailer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by jmgarzo on 09/02/17.
 */

public class NetworksUtils {

    private static final String LOG_TAG = NetworksUtils.class.getSimpleName();

    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";


    private static final String MOST_POPULAR = "movie/popular";
    private static final String TOP_RATE = "movie/top_rated";


    private static final String API_KEY_PARAM = "api_key";
    private static final String API_MOVIE_PARAM = "movie";
    private static final String API_VIDEO_PARAM = "videos";


    private static final String SIZE_w92 = "w92";
    private static final String SIZE_w154 = "w154";
    private static final String SIZE_w185 = "w185";
    private static final String SIZE_w342 = "w342";
    private static final String SIZE_w500 = "w500";
    private static final String SIZE_w780 = "w780";


    /**
     * This method returns the URL for MOST_POPULAR or TOP_RATE query depending on Preferences
     *
     * @param context
     * @return URL
     */
    public static URL buildURL(Context context) {
        String sortBy = "";
        if (SettingsUtils.isPreferenceSortByMostPopular(context)) {
            sortBy = MOST_POPULAR;
        } else if (SettingsUtils.isPreferenceSortByTopRated(context)) {
            sortBy = TOP_RATE;
        }

        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendEncodedPath(sortBy)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns poster URL
     *
     * @param posterPath
     * @param size
     * @return
     */
    public static URL buildPosterUrl(String posterPath, String size) {

        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendEncodedPath(size)
                .appendEncodedPath(posterPath)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.toString());
        }

        return url;
    }


    public static String buildPosterThumbnail(String posterPath) {
        return buildPosterUrl(posterPath, SIZE_w185).toString();
    }

    public static String buildPosterDetail(String posterPath) {
        return buildPosterUrl(posterPath, SIZE_w342).toString();
    }


    public static URL buildMovieUrl(String idMovie) {
        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendEncodedPath(API_MOVIE_PARAM)
                .appendEncodedPath(idMovie)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.toString());
        }
        return url;
    }


    public static URL buildTrailerUrl(String idMovie) {
        //  /movie/{id}/videos

        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendEncodedPath(API_MOVIE_PARAM)
                .appendEncodedPath(idMovie)
                .appendEncodedPath(API_VIDEO_PARAM)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.toString());
        }
        return url;

    }

    public static Trailer getTrailerFromJson(URL Url) {
        String trailerJsonStr = null;
        return null;

    }


    public static Movie getMovieFromJson(String idMovie) {

        if (idMovie == null | idMovie == "") {
            return null;
        }

        String movieJsonStr = null;
        URL movieUrl = NetworksUtils.buildMovieUrl(idMovie);

        try {
            movieJsonStr = getResponseFromHttpUrl(movieUrl);
        } catch (IOException e){
            Log.e(LOG_TAG + " movie url: " + movieUrl, e.toString());
            e.printStackTrace();
        }

        final String POSTER_PATH = "poster_path";
        final String ID = "id";
        final String RELEASE_DATE = "release_date";
        final String TITLE = "title";
        final String VOTE_AVERAGE = "vote_average";
        final String OVERVIEW = "overview";

        if (null == movieJsonStr || movieJsonStr.equals(""))

        {
            return null;
        }

        Movie movie = new Movie();


        JSONObject movieJson = null;

        try

        {
            movieJson = new JSONObject(movieJsonStr);

            movie.setId(movieJson.getInt(ID));
            movie.setPosterPath(movieJson.getString(POSTER_PATH));
            movie.setReleaseDate(movieJson.getString(RELEASE_DATE));
            movie.setTitle(movieJson.getString(TITLE));
            movie.setVoteAverage(movieJson.getDouble(VOTE_AVERAGE));
            movie.setOverview(movieJson.getString(OVERVIEW));
        } catch (
                JSONException e)

        {
            e.printStackTrace();
        }
        return movie;


    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
