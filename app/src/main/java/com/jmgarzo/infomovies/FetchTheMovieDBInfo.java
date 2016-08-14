//package com.jmgarzo.infomovies;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Environment;
//import android.util.Log;
//
//import com.jmgarzo.infomovies.data.MoviesContract;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Vector;
//
///**
// * Created by jmgarzo on 20/07/2016.
// */
//public class FetchTheMovieDBInfo extends AsyncTask<String, Void, Void> {
//    private String LOG_TAG = FetchTheMovieDBInfo.class.getSimpleName();
//
//    private final Context mContext;
//    private String TOP_RATE_PARAM = "top_rate";
//    Cursor cursorMoviesIds;
//    public FetchTheMovieDBInfo(Context context) {
//        mContext = context;
//    }
//
//    @Override
//    protected Void doInBackground(String... params) {
//
//
//        cursorMoviesIds = mContext.getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,new String[]{MoviesContract.MoviesEntry.MOVIE_WEB_ID},null,null,null);
//
//        if (params.length == 0) {
//            return null;
//        }
//
//        HttpURLConnection urlConnection = null;
//        BufferedReader reader = null;
//
//        String sortByPopularity = "popularity.desc";
//        String sortByVoteAverage = "vote_average.desc";
//        String certificationCountryUS = "US";
//        String certification = "R";
//
//
//        final String MOVIES_DB_BASE_URL = "https://api.themoviedb.org/3/";
//        final String MOST_POPULAR = "movie/popular";
//        final String TOP_RATE = "movie/top_rated";
////            final String SORT_BY_PARAM = "sort_by";
////            final String CERTIFICATION_COUNTRY_PARAM = "certification_country";
////            final String CERTIFICATION_PARAM = "certification";
//        final String API_KEY_PARAM = "api_key";
////
////            final String QUERY_URL_MOST_POPULAR = "/discover/movie?sort_by=popularity.desc";
////            final String QUERY_URL_TOP_RATE = "/discover/movie/?certification_country=US&certification=R&sort_by=vote_average.desc";
//
////
////            Uri buildUriMostPopular = Uri.parse(MOVIES_DB_BASE_URL).buildUpon()
////                    .appendQueryParameter(SORT_BY_PARAM, sortByPopularity)
////                    .appendQueryParameter(API_KEY_PARAM, THE_MOVIE_DB_API_KEY)
////                    .build();
////
////            Uri buildUriTopRate = Uri.parse(MOVIES_DB_BASE_URL).buildUpon()
////                    .appendQueryParameter(CERTIFICATION_COUNTRY_PARAM, certificationCountryUS)
////                    .appendQueryParameter(CERTIFICATION_PARAM, certification)
////                    .appendQueryParameter(SORT_BY_PARAM, sortByVoteAverage)
////                    .appendQueryParameter(API_KEY_PARAM, THE_MOVIE_DB_API_KEY)
////                    .build();
//
//        Uri buildUriMostPopular = Uri.parse(MOVIES_DB_BASE_URL + MOST_POPULAR).buildUpon()
//                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
//                .build();
//
//        Uri buildUriTopRate = Uri.parse(MOVIES_DB_BASE_URL + TOP_RATE).buildUpon()
//                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
//                .build();
//
//        // Uri build = Uri.parse("http://api.themoviedb.org/3/movie/popular?api_key=3890bbe3b27964c4c01fe8863a852df5").buildUpon().build();
//
//        String moviesJsonStr = null;
//        try {
//
//
//            URL url;
//            if (params[0].equalsIgnoreCase(MOST_POPULAR)) {
//                url = new URL(buildUriMostPopular.toString());
//            } else if (params[0].equalsIgnoreCase(TOP_RATE_PARAM)) {
//                url = new URL(buildUriTopRate.toString());
//            } else {
//                url = new URL(buildUriMostPopular.toString());
//            }
//            Log.v(LOG_TAG, "Built URI " + buildUriTopRate.toString());
//            Log.v(LOG_TAG, "Built URI " + buildUriMostPopular.toString());
//
//
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
//
//            InputStream inputStream = urlConnection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//            if (inputStream == null) {
//                return null;
//            }
//
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                buffer.append(line + "\n");
//            }
//
//            if (buffer.length() == 0) {
//                return null;
//            }
//
//            moviesJsonStr = buffer.toString();
//
//            Log.v(LOG_TAG, moviesJsonStr);
//
//
//        } catch (MalformedURLException e) {
//            Log.e(LOG_TAG, e.getMessage(), e);
//        } catch (IOException e) {
//            Log.e(LOG_TAG, e.getMessage(), e);
//            return null;
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
//
//
//        try {
//            getMoviesDataFromJson(moviesJsonStr, mContext);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//
//    private void getMoviesDataFromJson(String moviesJsonStr, Context context) throws JSONException {
//
//        ArrayList<HashMap<String, String>> resultList = new ArrayList<>();
//
//        final String MDB_RESULTS = "results";
////            final String MBD_POSTER_PATH="poster_path";
////            final String MBD_TITLE = "original_title";
////            final String MBD_OVERVIEW = "overview";
////            final String MBD_VOTE_AVERAGE="vote_average";
////            final String MBD_RELEASE_DATE="release_date";
//
//        JSONObject moviesJson = new JSONObject(moviesJsonStr);
//        JSONArray moviesArray = moviesJson.getJSONArray(MDB_RESULTS);
//
//        Vector<ContentValues> cVVector = new Vector<ContentValues>(moviesArray.length());
//
//        for (int i = 0; i < moviesArray.length(); i++) {
//
//            String posterPath;
//            String adult;
//            String overview;
//            String releaseDate;
//            String movieWebId;
//            String originalTitle;
//            String originalLanguage;
//            String title;
//            String backdropPath;
//            Double popularity;
//            Integer voteCount;
//            String video;
//            Double voteAverage;
//
//
//            JSONObject jsonMovie = moviesArray.getJSONObject(i);
//
//
//            posterPath = jsonMovie.getString(mContext.getString(R.string.mdb_poster_path_key));
//            adult = jsonMovie.getString(mContext.getString(R.string.mdb_adult_key));
//            overview = jsonMovie.getString(mContext.getString(R.string.mdb_overview_key));
//            releaseDate = jsonMovie.getString(mContext.getString(R.string.mdb_release_date_key));
//            movieWebId = jsonMovie.getString(mContext.getString(R.string.mdb_movie_web_id_key));
//            originalTitle = jsonMovie.getString(mContext.getString(R.string.mdb_original_title_key));
//            originalLanguage = jsonMovie.getString(mContext.getString(R.string.mdb_original_language_key));
//            title = jsonMovie.getString((mContext.getString(R.string.mdb_title_key)));
//            backdropPath = jsonMovie.getString(mContext.getString(R.string.mdb_backdrop_path_key));
//            popularity = jsonMovie.getDouble(mContext.getString(R.string.mdb_popularity_key));
//            voteCount = jsonMovie.getInt(mContext.getString(R.string.mdb_vote_count_key));
//            video = jsonMovie.getString(mContext.getString(R.string.mdb_video_key));
//            voteAverage = jsonMovie.getDouble(mContext.getString(R.string.mdb_vote_average_key));
//
////                map.put(getString(R.string.mdb_movie_web_id_key), movieWebId);
////                map.put(getString(R.string.mdb_poster_path_key), posterPath);
////                map.put(getString(R.string.mdb_title_key), title);
////                map.put(getString(R.string.mdb_overview_key), overview);
////                map.put(getString(R.string.mdb_vote_average_key), voteAverage.toString());
////                map.put(getString(R.string.mdb_release_date_key), releaseDate);
//
////                resultList.add(map);
//
//            ContentValues movieValues = new ContentValues();
//
//
//            URL url = pathPosterToURL(posterPath);
//            movieValues.put(MoviesContract.MoviesEntry.POSTER_PATH, checkNull(url.toString()));
//            movieValues.put(MoviesContract.MoviesEntry.ADULT, checkNull(adult));
//            movieValues.put(MoviesContract.MoviesEntry.OVERVIEW, checkNull(overview));
//            movieValues.put(MoviesContract.MoviesEntry.RELEASE_DATE, checkNull(releaseDate));
//            movieValues.put(MoviesContract.MoviesEntry.MOVIE_WEB_ID, checkNull(movieWebId));
//            movieValues.put(MoviesContract.MoviesEntry.ORIGINAL_TITLE, checkNull(originalTitle));
//            movieValues.put(MoviesContract.MoviesEntry.ORIGINAL_LANGUAGE, checkNull(originalLanguage));
//            movieValues.put(MoviesContract.MoviesEntry.TITLE, checkNull(title));
//            movieValues.put(MoviesContract.MoviesEntry.BACKDROP_PATH, checkNull(backdropPath));
//            movieValues.put(MoviesContract.MoviesEntry.POPULARITY, checkNull(popularity));
//            movieValues.put(MoviesContract.MoviesEntry.VOTE_COUNT, checkNull(voteCount));
//            movieValues.put(MoviesContract.MoviesEntry.VIDEO, checkNull(video));
//            movieValues.put(MoviesContract.MoviesEntry.VOTE_AVERAGE, checkNull(voteAverage));
//
////                imageToFile(url.toString(),movieWebId,context);
//
//            cVVector.add(movieValues);
//        }
//
//        //add to database
//        if (cVVector.size() > 0) {
//            ContentValues[] cvArray = new ContentValues[cVVector.size()];
//            cVVector.toArray(cvArray);
//            mContext.getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI, null, null);
//            mContext.getContentResolver().bulkInsert(MoviesContract.MoviesEntry.CONTENT_URI, cvArray);
//
//
//        }
//
//
//        // return resultList;
//    }
//
//    private String checkNull(String value) {
//        if (null == value) {
//            return "";
//        }
//        return value;
//    }
//    private double checkNull(Double value){
//        if (null==value){
//            return 0;
//        }
//        return value;
//     }
//
//    private double checkNull(Integer value){
//        if (null==value){
//            return 0;
//        }
//        return value;
//    }
//
////        private String imageToFile(String posterPath,String name,Context context){
////
////            URL urlImage = pathPosterToURL(posterPath);
////            File file = new File(context.getFilesDir(), name);
////            Picasso.with(context).load(new File(...)).into(imageView3);
////
////            return null;
////        }
//
//    private URL pathPosterToURL(String path) {
//
//        final String IMAGE_URL_BASE = "http://image.tmdb.org/t/p/";
//        final String SIZE_w92 = "w92";
//        final String SIZE_w154 = "w154";
//        final String SIZE_w185 = "w185";
//        final String SIZE_w342 = "w342";
//        final String SIZE_w500 = "w500";
//        final String SIZE_w780 = "w780";
//
//        if (null == path || path.equalsIgnoreCase("")) {
//            return null;
//        }
////            Uri builtUri = Uri.parse(IMAGE_URL_BASE).buildUpon()
////                    .appendPath(SIZE_w185)
////                    .appendPath(path.toString()).build();
//        String uri = IMAGE_URL_BASE + SIZE_w500 + path;
//        URL url = null;
//        try {
//            url = new URL(uri);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return url;
//
//    }
//
//    private void saveImage(URL url,String name) {
//
//       // URL url = new URL("file://some/path/anImage.png");
//        InputStream input = null;
//        try {
//            input = url.openStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            //The sdcard directory e.g. '/sdcard' can be used directly, or
//            //more safely abstracted with getExternalStorageDirectory()
//            File storagePath = Environment.getDataDirectory();
//            OutputStream output = new FileOutputStream(new File(storagePath, name));
//            try {
//                byte[] buffer = new byte[1024];
//                int bytesRead = 0;
//                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
//                    output.write(buffer, 0, bytesRead);
//                }
//            } finally {
//                output.close();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                input.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
////        @Override
////        protected void onPostExecute(ArrayList<HashMap<String, String>> hashMaps) {
////            if (hashMaps != null) {
////                androidMovieAdapter.clear();
////                for (HashMap map : hashMaps) {
////
////                    URL url = pathPosterToURL(map.get(getString(R.string.mdb_poster_path_key)).toString());
////
////                    Movie movie = new Movie(
////                            url.toString(),
////                            map.get(getString(R.string.mdb_title_key)).toString(),
////                            map.get(getString(R.string.mdb_overview_key)).toString(),
////                            map.get(getString(R.string.mdb_vote_average_key)).toString(),
////                            map.get(getString(R.string.mdb_release_date_key)).toString());
////                    androidMovieAdapter.add(movie);
////
////                }
////            }
////        }
//}
