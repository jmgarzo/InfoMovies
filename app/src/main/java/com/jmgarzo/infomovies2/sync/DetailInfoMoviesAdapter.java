//package com.jmgarzo.infomovies.sync;
//
//import android.accounts.Account;
//import android.accounts.AccountManager;
//import android.content.AbstractThreadedSyncAdapter;
//import android.content.ContentProviderClient;
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.SyncResult;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.jmgarzo.infomovies.BuildConfig;
//import com.jmgarzo.infomovies.R;
//import com.jmgarzo.infomovies.data.MoviesContract;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Vector;
//
///**
// * Created by jmgarzo on 14/08/16.
// */
//public class DetailInfoMoviesAdapter extends AbstractThreadedSyncAdapter {
//
//    private String LOG_TAG = DetailInfoMoviesAdapter.class.getSimpleName();
//
//    public DetailInfoMoviesAdapter(Context context, boolean autoInitialize) {
//        super(context, autoInitialize);
//
//    }
//
//        @Override
//    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
//
//            getContext().getContentResolver().delete(MoviesContract.ReviewEntry.CONTENT_URI, null, null);
//            getContext().getContentResolver().delete(MoviesContract.VideoEntry.CONTENT_URI, null, null);
//
//            Cursor cursor = getContext().getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI, null, null, null, null);
//
//            ArrayList<String> id_web_movies = null;
//            if (cursor.moveToFirst()) {
//                id_web_movies = new ArrayList<String>();
//
//                do {
//
//                    int ind_id = cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_WEB_ID);
//                    String id_movie = cursor.getString(ind_id);
//                    id_web_movies.add(id_movie);
//
//                } while (cursor.moveToNext());
//            }
//
//            if (id_web_movies != null && id_web_movies.size() != 0) {
//
//                for (String id_web_movie : id_web_movies) {
//
//                    String jsonStrVideo = getStrFromAPIWebURL(buildVideoURL(id_web_movie));
//                    try {
//
//                        if (jsonStrVideo != null && !jsonStrVideo.isEmpty()) {
//                            insertVideosToDB(jsonStrVideo, id_web_movie);
//                        }
//                    } catch (JSONException e) {
//                        Log.e(LOG_TAG, e.toString());
//                    }
//
//                    String jsonStrReview = getStrFromAPIWebURL(buildReviewURL(id_web_movie));
//                    try {
//
//                        if (jsonStrReview != null && !jsonStrReview.isEmpty()) {
//                            insertReviewToDB(jsonStrReview, id_web_movie);
//                        }
//                    } catch (JSONException e) {
//                        Log.e(LOG_TAG, e.toString());
//                    }
//                }
//
//            }
//
//
//            return;
//
//    }
//
//
//
//    public static Account getSyncAccount(Context context) {
//        // Get an instance of the Android account manager
//        AccountManager accountManager =
//                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
//
//        // Create the account type and default account
//        Account newAccount = new Account(
//                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));
//
//        // If the password doesn't exist, the account doesn't exist
//        if ( null == accountManager.getPassword(newAccount) ) {
//
//        /*
//         * Add the account and account type, no password or user data
//         * If successful, return the Account object, otherwise report an error.
//         */
//            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
//                return null;
//            }
//            /*
//             * If you don't set android:syncable="true" in
//             * in your <provider> element in the manifest,
//             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
//             * here.
//             */
//
//        }
//        return newAccount;
//    }
//
//    public static void syncImmediately(Context context) {
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
//        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
//        ContentResolver.requestSync(getSyncAccount(context),
//                context.getString(R.string.content_authority), bundle);
//    }
//
//    private URL buildVideoURL(String id_web_movie) {
//        final String VIDEOS_DB_BASE_URL = "http://api.themoviedb.org/3/movie/";
//        final String VIDEO_PATH = "/videos";
//        final String API_KEY_PARAM = "api_key";
//
//
//        Uri buildUriVideo = Uri.parse(VIDEOS_DB_BASE_URL + id_web_movie + VIDEO_PATH).buildUpon()
//                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY).build();
//
//
//        URL url = null;
//        try {
//            url = new URL(buildUriVideo.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }
//
//
//    private URL buildReviewURL(String id_web_movie) {
//        final String REVIEW_DB_BASE_URL = "http://api.themoviedb.org/3/movie/";
//        final String REVIEW_PATH = "/reviews";
//        final String API_KEY_PARAM = "api_key";
//
//
//        Uri buildUriReview = Uri.parse(REVIEW_DB_BASE_URL + id_web_movie + REVIEW_PATH).buildUpon()
//                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY).build();
//
//
//        URL url = null;
//        try {
//            url = new URL(buildUriReview.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }
//
//
//    private String getStrFromAPIWebURL(URL url) {
//
//        String jsonStr = null;
//        HttpURLConnection urlConnection = null;
//        BufferedReader reader = null;
//        try {
////            URL url = new URL(buildUriReviews.toString());
//            Log.v(LOG_TAG, "Build URI " + url.toString());
//
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
//
//            InputStream inputStream = urlConnection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//            if (inputStream == null) {
//            }
//
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                buffer.append((line + "\n"));
//            }
//            if (buffer.length() == 0) {
//            }
//
//            jsonStr = buffer.toString();
//            Log.v(LOG_TAG, jsonStr);
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    Log.e(LOG_TAG, "Error closing stream", e);
//                }
//            }
//        }
//        if (jsonStr == null) {
//            Log.v(LOG_TAG, "asdfa");
//        }
//        return jsonStr;
//
//    }
//
//
//    private void insertVideosToDB(String videosJsonStr, String id_web_movie) throws JSONException {
//
//        JSONObject videosJson = new JSONObject(videosJsonStr);
//        JSONArray videosArray = videosJson.getJSONArray(getContext().getString(R.string.video_results_key));
//        if (null != videosArray && videosArray.length() > 0) {
//            Vector<ContentValues> cVVector = new Vector<ContentValues>(videosArray.length());
//
//            for (int i = 0; i < videosArray.length(); i++) {
//                JSONObject jsonVideo = videosArray.getJSONObject(i);
//
//                String id = jsonVideo.getString(getContext().getString(R.string.video_id_key));
//                String iso_639_1 = jsonVideo.getString(getContext().getString(R.string.video_iso_639_1_key));
//                String iso_3166_1 = jsonVideo.getString(getContext().getString(R.string.video_iso_3166_1_key));
//                String key = jsonVideo.getString(getContext().getString(R.string.video_key_key));
//                String name = jsonVideo.getString(getContext().getString(R.string.video_name_key));
//                String site = jsonVideo.getString(getContext().getString(R.string.video_site_key));
//                String size = jsonVideo.getString(getContext().getString(R.string.video_size_key));
//                String type = jsonVideo.getString(getContext().getString(R.string.video_type_key));
//
//                ContentValues videoValues = new ContentValues();
//
//                Cursor cursor = getContext().getContentResolver().query(MoviesContract.MoviesEntry.buildMovieWithWebId(id_web_movie), null, null, null, null);
//                String id_movie = "";
//                if (cursor.moveToFirst()) {
//                    int id_index = cursor.getColumnIndex(MoviesContract.MoviesEntry._ID);
//                    id_movie = cursor.getString(id_index);
//                }
//                videoValues.put(MoviesContract.VideoEntry.MOVIE_KEY, id_movie);
//                videoValues.put(MoviesContract.VideoEntry.ID, checkNull(id));
//                videoValues.put(MoviesContract.VideoEntry.ISO_639_1, checkNull(iso_639_1));
//                videoValues.put(MoviesContract.VideoEntry.ISO_3166_1, checkNull(iso_3166_1));
//                videoValues.put(MoviesContract.VideoEntry.KEY, checkNull(key));
//                videoValues.put(MoviesContract.VideoEntry.NAME, checkNull(name));
//                videoValues.put(MoviesContract.VideoEntry.SITE, checkNull(site));
//                videoValues.put(MoviesContract.VideoEntry.SIZE, checkNull(size));
//                videoValues.put(MoviesContract.VideoEntry.TYPE, checkNull(type));
//
//                cVVector.add(videoValues);
//
//                cursor.close();
//
//            }
//
//            if (cVVector.size() > 0) {
//                ContentValues[] cvArray = new ContentValues[cVVector.size()];
//                cVVector.toArray(cvArray);
//                getContext().getContentResolver().bulkInsert(MoviesContract.VideoEntry.CONTENT_URI, cvArray);
//            }
//        }
//
//
//    }
//
//
//    private void insertReviewToDB(String reviewJsonStr, String id_web_movie) throws JSONException {
//
//        JSONObject reviewJson = new JSONObject(reviewJsonStr);
//        JSONArray reviewArray = reviewJson.getJSONArray(getContext().getString(R.string.review_results_key));
//        if (reviewArray.length() > 0) {
//            Vector<ContentValues> cVVector = new Vector<ContentValues>(reviewArray.length());
//
//            for (int i = 0; i < reviewArray.length(); i++) {
//                JSONObject jsonVideo = reviewArray.getJSONObject(i);
//
//                String id = jsonVideo.getString(getContext().getString(R.string.review_id_key));
//                String author = jsonVideo.getString(getContext().getString(R.string.review_author_key));
//                String content = jsonVideo.getString(getContext().getString(R.string.review_content_key));
//                String url = jsonVideo.getString(getContext().getString(R.string.review_url_key));
//
//                ContentValues reviewValues = new ContentValues();
//
//                Cursor cursor = getContext().getContentResolver().query(MoviesContract.MoviesEntry.buildMovieWithWebId(id_web_movie), null, null, null, null);
//                String id_movie = "";
//                if (cursor.moveToFirst()) {
//                    int id_index = cursor.getColumnIndex(MoviesContract.MoviesEntry._ID);
//                    id_movie = cursor.getString(id_index);
//                }
//                reviewValues.put(MoviesContract.ReviewEntry.MOVIE_KEY, id_movie);
//                reviewValues.put(MoviesContract.ReviewEntry.ID, checkNull(id));
//                reviewValues.put(MoviesContract.ReviewEntry.AUTHOR, checkNull(author));
//                reviewValues.put(MoviesContract.ReviewEntry.CONTENT, checkNull(content));
//                reviewValues.put(MoviesContract.ReviewEntry.URL, checkNull(url));
//
//
//                cVVector.add(reviewValues);
//
//                cursor.close();
//
//            }
//
//            if (cVVector.size() > 0) {
//                ContentValues[] cvArray = new ContentValues[cVVector.size()];
//                cVVector.toArray(cvArray);
//
//                getContext().getContentResolver().bulkInsert(MoviesContract.ReviewEntry.CONTENT_URI, cvArray);
//            }
//        }
//
//
//    }
//
//
//    private String checkNull(String value) {
//        if (null == value) {
//            return "";
//        }
//        return value;
//
//
//    }
//}
