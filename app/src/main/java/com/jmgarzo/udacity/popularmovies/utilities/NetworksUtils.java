package com.jmgarzo.udacity.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.jmgarzo.udacity.popularmovies.BuildConfig;
import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.Objects.Review;
import com.jmgarzo.udacity.popularmovies.Objects.Trailer;
import com.jmgarzo.udacity.popularmovies.data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
    private static final String API_REVIEW_PARAM = "reviews";

    private static final String API_RESULTS_PARAM = "results";


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
    public static URL buildMainURL(Context context) {
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


    /**
     * This method returns the URL to get data about the movie
     *
     * @param idMovie
     * @return URL
     */
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

    public static Movie getMovieFromJson(String idMovie) {

        if (idMovie == null | idMovie == "") {
            return null;
        }

        String movieJsonStr = null;
        URL movieUrl = NetworksUtils.buildMovieUrl(idMovie);

        try {
            movieJsonStr = getResponseFromHttpUrl(movieUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG + " movie url: " + movieUrl, e.toString());
            e.printStackTrace();
        }

        if (null == movieJsonStr || movieJsonStr.equals("")) {
            return null;
        }
        Movie movie = new Movie();
        JSONObject movieJson = null;
        try {
            movieJson = new JSONObject(movieJsonStr);
            movie.setPosterPath(movieJson.getString(MovieContract.MovieEntry.POSTER_PATH));
            movie.setAdult(isAdult(movieJson.getString(MovieContract.MovieEntry.ADULT)));
            movie.setOverview(movieJson.getString(MovieContract.MovieEntry.OVERVIEW));
            movie.setReleaseDate(movieJson.getString(MovieContract.MovieEntry.RELEASE_DATE));
            movie.setMovieWebId(movieJson.getString(MovieContract.MovieEntry.MOVIE_WEB_ID));
            movie.setOriginalTitle(movieJson.getString(MovieContract.MovieEntry.ORIGINAL_TITLE));
            movie.setOriginalLanguage(movieJson.getString(MovieContract.MovieEntry.ORIGINAL_LANGUAGE));
            movie.setTitle(movieJson.getString(MovieContract.MovieEntry.TITLE));
            movie.setBackdropPath(movieJson.getString(MovieContract.MovieEntry.BACKDROP_PATH));
            movie.setPopularity(Double.valueOf(movieJson.getString(MovieContract.MovieEntry.POPULARITY)));
            movie.setVoteCount(Integer.valueOf(movieJson.getString(MovieContract.MovieEntry.VOTE_COUNT)));
            movie.setVideo(isVideo(movieJson.getString(MovieContract.MovieEntry.VIDEO)));
            movie.setVoteAverage(Double.valueOf(movieJson.getString(MovieContract.MovieEntry.VOTE_AVERAGE)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }

    private static boolean isAdult(String adult) {
        if (adult.equalsIgnoreCase("false")) {
            return false;
        } else if (adult.equalsIgnoreCase("true")) {
            return true;
        }
        return false;
    }

    private static boolean isVideo(String video) {
        if (video.equalsIgnoreCase("false")) {
            return false;
        } else if (video.equalsIgnoreCase("true")) {
            return true;
        }
        return false;
    }


    public static ArrayList<Trailer> getTrailersFromJson(String idMovie) {

        if (idMovie == null | idMovie.equals("")) {
            return null;
        }

        ArrayList<Trailer> arrayListTrailers = null;

        String trailerJsonStr = null;
        URL trailerUrl = NetworksUtils.buildTrailerUrl(idMovie);

        try {
            trailerJsonStr = getResponseFromHttpUrl(trailerUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG + " trailer url: " + trailerUrl, e.toString());
            e.printStackTrace();
        }

        if (null == trailerJsonStr || trailerJsonStr.equals("")) {
            return null;
        }

        JSONObject trailerJson = null;
        try {
            trailerJson = new JSONObject(trailerJsonStr);
            JSONArray trailersArray = trailerJson.getJSONArray(API_RESULTS_PARAM);
            if (null != trailersArray && trailersArray.length() > 0) {
                arrayListTrailers = new ArrayList<>();

                Trailer trailer = null;

                for (int i = 0; i < trailersArray.length(); i++) {

                    trailer = new Trailer();

                    JSONObject jsonTrailer = trailersArray.getJSONObject(i);

                    trailer.setWebTrailerId(jsonTrailer.getString(MovieContract.TrailerEntry.WEB_TRAILER_ID));
                    trailer.setIso_639_1(jsonTrailer.getString(MovieContract.TrailerEntry.ISO_639_1));
                    trailer.setIso_3166_1(jsonTrailer.getString(MovieContract.TrailerEntry.ISO_3166_1));
                    trailer.setKey(jsonTrailer.getString(MovieContract.TrailerEntry.KEY));
                    trailer.setName(jsonTrailer.getString(MovieContract.TrailerEntry.NAME));
                    trailer.setSize(jsonTrailer.getString(MovieContract.TrailerEntry.SIZE));
                    trailer.setType(jsonTrailer.getString(MovieContract.TrailerEntry.TYPE));

                    arrayListTrailers.add(trailer);

                }
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.toString());
            e.printStackTrace();
        }
        return arrayListTrailers;
    }




//  ***** Reviews's Utils  *****

    public static URL buildReviewUrl(String idMovie) {

        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendEncodedPath(API_MOVIE_PARAM)
                .appendEncodedPath(idMovie)
                .appendEncodedPath(API_REVIEW_PARAM)
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

    public static ArrayList<Review> getReviewsFromJson(String idMovie) {

        if (idMovie == null | idMovie.equals("")) {
            return null;
        }

        ArrayList<Review> arrayListReviews = null;

        String reviewJsonStr = null;
        URL reviewUrl = NetworksUtils.buildReviewUrl(idMovie);

        try {
            reviewJsonStr = getResponseFromHttpUrl(reviewUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG + " trailer url: " + reviewUrl, e.toString());
            e.printStackTrace();
        }

        if (null == reviewJsonStr || reviewJsonStr.equals("")) {
            return null;
        }

        JSONObject reviewJson = null;
        try {
            reviewJson = new JSONObject(reviewJsonStr);
            JSONArray reviewArray = reviewJson.getJSONArray(API_RESULTS_PARAM);
            if (null != reviewArray && reviewArray.length() > 0) {
                arrayListReviews = new ArrayList<>();

                Review review = null;

                for (int i = 0; i < reviewArray.length(); i++) {

                    review = new Review();

                    JSONObject jsonReview = reviewArray.getJSONObject(i);

                    review.setWebReviewId(jsonReview.getString(MovieContract.ReviewEntry.WEB_REVIEW_ID));
                    review.setAuthor(jsonReview.getString(MovieContract.ReviewEntry.AUTHOR));
                    review.setContent(jsonReview.getString(MovieContract.ReviewEntry.CONTENT));
                    review.setUrl(jsonReview.getString(MovieContract.ReviewEntry.URL));

                    arrayListReviews.add(review);

                }
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.toString());
            e.printStackTrace();
        }
        return arrayListReviews;
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
