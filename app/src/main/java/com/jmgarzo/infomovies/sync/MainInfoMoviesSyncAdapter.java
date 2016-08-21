package com.jmgarzo.infomovies.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.jmgarzo.infomovies.BuildConfig;
import com.jmgarzo.infomovies.FetchVideoInfo2;
import com.jmgarzo.infomovies.R;
import com.jmgarzo.infomovies.Utility;
import com.jmgarzo.infomovies.data.MoviesContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by jmgarzo on 14/08/2016.
 */
public class MainInfoMoviesSyncAdapter extends AbstractThreadedSyncAdapter {

    Cursor cursorMoviesIds;
    private String TOP_RATE_PARAM = "top_rate";
    private String LOG_TAG = MainInfoMoviesSyncAdapter.class.getSimpleName();

    public MainInfoMoviesSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

        }
        return newAccount;
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        {


            cursorMoviesIds = getContext().getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI, new String[]{MoviesContract.MoviesEntry.MOVIE_WEB_ID}, null, null, null);


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String sortByPopularity = "popularity.desc";
            String sortByVoteAverage = "vote_average.desc";
            String certificationCountryUS = "US";
            String certification = "R";


            final String MOVIES_DB_BASE_URL = "https://api.themoviedb.org/3/";
            final String MOST_POPULAR = "movie/popular";
            final String TOP_RATE = "movie/top_rated";
//            final String SORT_BY_PARAM = "sort_by";
//            final String CERTIFICATION_COUNTRY_PARAM = "certification_country";
//            final String CERTIFICATION_PARAM = "certification";
            final String API_KEY_PARAM = "api_key";
//
//            final String QUERY_URL_MOST_POPULAR = "/discover/movie?sort_by=popularity.desc";
//            final String QUERY_URL_TOP_RATE = "/discover/movie/?certification_country=US&certification=R&sort_by=vote_average.desc";

//
//            Uri buildUriMostPopular = Uri.parse(MOVIES_DB_BASE_URL).buildUpon()
//                    .appendQueryParameter(SORT_BY_PARAM, sortByPopularity)
//                    .appendQueryParameter(API_KEY_PARAM, THE_MOVIE_DB_API_KEY)
//                    .build();
//
//            Uri buildUriTopRate = Uri.parse(MOVIES_DB_BASE_URL).buildUpon()
//                    .appendQueryParameter(CERTIFICATION_COUNTRY_PARAM, certificationCountryUS)
//                    .appendQueryParameter(CERTIFICATION_PARAM, certification)
//                    .appendQueryParameter(SORT_BY_PARAM, sortByVoteAverage)
//                    .appendQueryParameter(API_KEY_PARAM, THE_MOVIE_DB_API_KEY)
//                    .build();

            Uri buildUriMostPopular = Uri.parse(MOVIES_DB_BASE_URL + MOST_POPULAR).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                    .build();

            Uri buildUriTopRate = Uri.parse(MOVIES_DB_BASE_URL + TOP_RATE).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                    .build();

            // Uri build = Uri.parse("http://api.themoviedb.org/3/movie/popular?api_key=3890bbe3b27964c4c01fe8863a852df5").buildUpon().build();

            String moviesJsonStr = null;
            try {


                URL url;
                String sortByPreference = Utility.getPreferredSortBy(getContext());
                if (sortByPreference.equalsIgnoreCase(MOST_POPULAR)) {
                    url = new URL(buildUriMostPopular.toString());
                } else if (sortByPreference.equalsIgnoreCase(TOP_RATE_PARAM)) {
                    url = new URL(buildUriTopRate.toString());
                } else {
                    url = new URL(buildUriMostPopular.toString());
                }
                Log.v(LOG_TAG, "Built URI " + buildUriTopRate.toString());
                Log.v(LOG_TAG, "Built URI " + buildUriMostPopular.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));


                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return;
                }

                moviesJsonStr = buffer.toString();

                Log.v(LOG_TAG, moviesJsonStr);


            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                return;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }


            try {
                getMoviesDataFromJson(moviesJsonStr, getContext());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            FetchVideoInfo2 fetchVideoInfo2 = new FetchVideoInfo2(getContext());
            fetchVideoInfo2.execute();
            return;
        }


    }

    private void getMoviesDataFromJson(String moviesJsonStr, Context context) throws JSONException {

        ArrayList<HashMap<String, String>> resultList = new ArrayList<>();

        final String MDB_RESULTS = "results";
//            final String MBD_POSTER_PATH="poster_path";
//            final String MBD_TITLE = "original_title";
//            final String MBD_OVERVIEW = "overview";
//            final String MBD_VOTE_AVERAGE="vote_average";
//            final String MBD_RELEASE_DATE="release_date";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(MDB_RESULTS);

        Vector<ContentValues> cVVector = new Vector<ContentValues>(moviesArray.length());

        for (int i = 0; i < moviesArray.length(); i++) {

            String posterPath;
            String adult;
            String overview;
            String releaseDate;
            String movieWebId;
            String originalTitle;
            String originalLanguage;
            String title;
            String backdropPath;
            Double popularity;
            Integer voteCount;
            String video;
            Double voteAverage;


            JSONObject jsonMovie = moviesArray.getJSONObject(i);


            posterPath = jsonMovie.getString(getContext().getString(R.string.mdb_poster_path_key));
            adult = jsonMovie.getString(getContext().getString(R.string.mdb_adult_key));
            overview = jsonMovie.getString(getContext().getString(R.string.mdb_overview_key));
            releaseDate = jsonMovie.getString(getContext().getString(R.string.mdb_release_date_key));
            movieWebId = jsonMovie.getString(getContext().getString(R.string.mdb_movie_web_id_key));
            originalTitle = jsonMovie.getString(getContext().getString(R.string.mdb_original_title_key));
            originalLanguage = jsonMovie.getString(getContext().getString(R.string.mdb_original_language_key));
            title = jsonMovie.getString((getContext().getString(R.string.mdb_title_key)));
            backdropPath = jsonMovie.getString(getContext().getString(R.string.mdb_backdrop_path_key));
            popularity = jsonMovie.getDouble(getContext().getString(R.string.mdb_popularity_key));
            voteCount = jsonMovie.getInt(getContext().getString(R.string.mdb_vote_count_key));
            video = jsonMovie.getString(getContext().getString(R.string.mdb_video_key));
            voteAverage = jsonMovie.getDouble(getContext().getString(R.string.mdb_vote_average_key));

//                map.put(getString(R.string.mdb_movie_web_id_key), movieWebId);
//                map.put(getString(R.string.mdb_poster_path_key), posterPath);
//                map.put(getString(R.string.mdb_title_key), title);
//                map.put(getString(R.string.mdb_overview_key), overview);
//                map.put(getString(R.string.mdb_vote_average_key), voteAverage.toString());
//                map.put(getString(R.string.mdb_release_date_key), releaseDate);

//                resultList.add(map);

            ContentValues movieValues = new ContentValues();


            URL url = pathPosterToURL(posterPath);
            movieValues.put(MoviesContract.MoviesEntry.POSTER_PATH, checkNull(url.toString()));
            movieValues.put(MoviesContract.MoviesEntry.ADULT, checkNull(adult));
            movieValues.put(MoviesContract.MoviesEntry.OVERVIEW, checkNull(overview));
            movieValues.put(MoviesContract.MoviesEntry.RELEASE_DATE, checkNull(releaseDate));
            movieValues.put(MoviesContract.MoviesEntry.MOVIE_WEB_ID, checkNull(movieWebId));
            movieValues.put(MoviesContract.MoviesEntry.ORIGINAL_TITLE, checkNull(originalTitle));
            movieValues.put(MoviesContract.MoviesEntry.ORIGINAL_LANGUAGE, checkNull(originalLanguage));
            movieValues.put(MoviesContract.MoviesEntry.TITLE, checkNull(title));
            movieValues.put(MoviesContract.MoviesEntry.BACKDROP_PATH, checkNull(backdropPath));
            movieValues.put(MoviesContract.MoviesEntry.POPULARITY, checkNull(popularity));
            movieValues.put(MoviesContract.MoviesEntry.VOTE_COUNT, checkNull(voteCount));
            movieValues.put(MoviesContract.MoviesEntry.VIDEO, checkNull(video));
            movieValues.put(MoviesContract.MoviesEntry.VOTE_AVERAGE, checkNull(voteAverage));

//                imageToFile(url.toString(),movieWebId,context);

            cVVector.add(movieValues);
        }

        //add to database
        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            getContext().getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI, null, null);
            getContext().getContentResolver().bulkInsert(MoviesContract.MoviesEntry.CONTENT_URI, cvArray);


        }


        // return resultList;
    }

    private String checkNull(String value) {
        if (null == value) {
            return "";
        }
        return value;
    }
    private double checkNull(Double value){
        if (null==value){
            return 0;
        }
        return value;
    }

    private double checkNull(Integer value){
        if (null==value){
            return 0;
        }
        return value;
    }

    private URL pathPosterToURL(String path) {

        final String IMAGE_URL_BASE = "http://image.tmdb.org/t/p/";
        final String SIZE_w92 = "w92";
        final String SIZE_w154 = "w154";
        final String SIZE_w185 = "w185";
        final String SIZE_w342 = "w342";
        final String SIZE_w500 = "w500";
        final String SIZE_w780 = "w780";

        if (null == path || path.equalsIgnoreCase("")) {
            return null;
        }
//            Uri builtUri = Uri.parse(IMAGE_URL_BASE).buildUpon()
//                    .appendPath(SIZE_w185)
//                    .appendPath(path.toString()).build();
        String uri = IMAGE_URL_BASE + SIZE_w185 + path;
        URL url = null;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

}
