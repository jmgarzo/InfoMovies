package com.jmgarzo.udacity.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.jmgarzo.udacity.popularmovies.BuildConfig;
import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.Objects.Review;
import com.jmgarzo.udacity.popularmovies.Objects.Trailer;
import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;

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


    public static final String MOST_POPULAR = "movie/popular";
    public static final String TOP_RATE = "movie/top_rated";


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

    private static final String POSTER_PATH = "poster_path";
    private static final String ADULT = "adult";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String WEB_MOVIE_ID = "id";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String ORIGINAL_LANGUAGE = "original_language";
    private static final String TITLE = "title";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String POPULARITY = "popularity";
    private static final String VOTE_COUNT = "vote_count";
    private static final String VIDEO = "video" ;
    private static final String VOTE_AVERAGE = "vote_average" ;













    /**
     * This method returns the URL for MOST_POPULAR or TOP_RATE query depending on Preferences
     *
     * @param context
     * @return URL
     */
    public static URL buildMainURL(Context context,String registryType) {
//        String sortBy = "";
//        if (SettingsUtils.isPreferenceSortByMostPopular(context)) {
//            sortBy = MOST_POPULAR;
//        } else if (SettingsUtils.isPreferenceSortByTopRated(context)) {
//            sortBy = TOP_RATE;
//        }

        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendEncodedPath(registryType)
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

    /**
     * This method returns Top rate, and most popular movies from API
     *
     * @param context
     * @return ArrayList<Movie>
     */
    public static ArrayList<Movie> getMovies(Context context){

        URL moviesURLMostPopular = NetworksUtils.buildMainURL(context,MOST_POPULAR);
        URL moviesURLTopRate = NetworksUtils.buildMainURL(context,TOP_RATE);

        ArrayList<Movie> moviesList = null;
        try {
            String jsonMoviesResponse = NetworksUtils.getResponseFromHttpUrl(moviesURLMostPopular);
            moviesList = getMoviesFromJson(jsonMoviesResponse,PopularMovieContract.MOST_POPULAR_REGISTRY_TYPE);

            jsonMoviesResponse=NetworksUtils.getResponseFromHttpUrl(moviesURLTopRate);
            moviesList.addAll(getMoviesFromJson(jsonMoviesResponse,PopularMovieContract.TOP_RATE_REGISTRY_TYPE));
        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
        }

        return moviesList;
    }

    private static ArrayList<Movie> getMoviesFromJson(String moviesJsonStr,String registryType) {
        final String MOVIE_RESULTS = "results";


        ArrayList<Movie> moviesList = null;

        JSONObject moviesJson = null;
        try {
            moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(MOVIE_RESULTS);
            moviesList = new ArrayList<>();
            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject jsonMovie = moviesArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.setPosterPath(jsonMovie.getString(POSTER_PATH));
                movie.setAdult(Boolean.valueOf(jsonMovie.getString(ADULT)));
                movie.setOverview(jsonMovie.getString(OVERVIEW));
                movie.setReleaseDate(jsonMovie.getString(RELEASE_DATE));
                movie.setMovieWebId(jsonMovie.getString(WEB_MOVIE_ID));
                movie.setOriginalTitle(jsonMovie.getString(ORIGINAL_TITLE));
                movie.setOriginalLanguage(jsonMovie.getString(ORIGINAL_LANGUAGE));
                movie.setTitle(jsonMovie.getString(TITLE));
                movie.setBackdropPath(jsonMovie.getString(BACKDROP_PATH));
                movie.setPopularity(jsonMovie.getDouble(POPULARITY));
                movie.setVoteCount(jsonMovie.getInt(VOTE_COUNT));
                movie.setVideo(Boolean.valueOf(jsonMovie.getString(VIDEO)));
                movie.setVoteAverage(jsonMovie.getDouble(VOTE_AVERAGE));
                movie.setRegistryType(registryType);
                moviesList.add(movie);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.toString());
        }

        return moviesList;
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
            movie.setPosterPath(movieJson.getString(PopularMovieContract.MovieEntry.POSTER_PATH));
            movie.setAdult(isAdult(movieJson.getString(PopularMovieContract.MovieEntry.ADULT)));
            movie.setOverview(movieJson.getString(PopularMovieContract.MovieEntry.OVERVIEW));
            movie.setReleaseDate(movieJson.getString(PopularMovieContract.MovieEntry.RELEASE_DATE));
            movie.setMovieWebId(movieJson.getString(PopularMovieContract.MovieEntry.MOVIE_WEB_ID));
            movie.setOriginalTitle(movieJson.getString(PopularMovieContract.MovieEntry.ORIGINAL_TITLE));
            movie.setOriginalLanguage(movieJson.getString(PopularMovieContract.MovieEntry.ORIGINAL_LANGUAGE));
            movie.setTitle(movieJson.getString(PopularMovieContract.MovieEntry.TITLE));
            movie.setBackdropPath(movieJson.getString(PopularMovieContract.MovieEntry.BACKDROP_PATH));
            movie.setPopularity(Double.valueOf(movieJson.getString(PopularMovieContract.MovieEntry.POPULARITY)));
            movie.setVoteCount(Integer.valueOf(movieJson.getString(PopularMovieContract.MovieEntry.VOTE_COUNT)));
            movie.setVideo(isVideo(movieJson.getString(PopularMovieContract.MovieEntry.VIDEO)));
            movie.setVoteAverage(Double.valueOf(movieJson.getString(PopularMovieContract.MovieEntry.VOTE_AVERAGE)));

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


    public static ArrayList<Trailer> getTrailers(String idMovie) {

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

                    trailer.setWebTrailerId(jsonTrailer.getString(PopularMovieContract.TrailerEntry.WEB_TRAILER_ID));
                    trailer.setIso_639_1(jsonTrailer.getString(PopularMovieContract.TrailerEntry.ISO_639_1));
                    trailer.setIso_3166_1(jsonTrailer.getString(PopularMovieContract.TrailerEntry.ISO_3166_1));
                    trailer.setKey(jsonTrailer.getString(PopularMovieContract.TrailerEntry.KEY));
                    trailer.setName(jsonTrailer.getString(PopularMovieContract.TrailerEntry.NAME));
                    trailer.setSite(jsonTrailer.getString(PopularMovieContract.TrailerEntry.SITE));
                    trailer.setSize(jsonTrailer.getString(PopularMovieContract.TrailerEntry.SIZE));
                    trailer.setType(jsonTrailer.getString(PopularMovieContract.TrailerEntry.TYPE));

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

    public static ArrayList<Review> getReviews(String idMovie) {

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

                    review.setWebReviewId(jsonReview.getString(PopularMovieContract.ReviewEntry.WEB_REVIEW_ID));
                    review.setAuthor(jsonReview.getString(PopularMovieContract.ReviewEntry.AUTHOR));
                    review.setContent(jsonReview.getString(PopularMovieContract.ReviewEntry.CONTENT));
                    review.setUrl(jsonReview.getString(PopularMovieContract.ReviewEntry.URL));

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
