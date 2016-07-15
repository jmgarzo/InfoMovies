package com.jmgarzo.infomovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private String TOP_RATE_PARAM = "top_rate";
    private String MOST_POPULAR = "most_popular";

    private AndroidMovieAdapter androidMovieAdapter;


    public MainActivityFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        updateMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        androidMovieAdapter = new AndroidMovieAdapter(getActivity(), new ArrayList<Movie>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(androidMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = androidMovieAdapter.getItem(position);

                Intent intent = new Intent(getActivity(),DetailMovie.class);
                intent.putExtra("movie",movie);
                startActivity(intent);
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        //super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_fragment_main, menu);
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }



    private void updateMovies() {
        FetchTheMovieDBInfo fetchTheMovieDBInfo = new FetchTheMovieDBInfo();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String location = sharedPreferences.getString(getString(R.string.pref_sort_by_key), getString(R.string.pref_sort_by_defautl));
        String sortBy = sharedPreferences.getString(getString(R.string.pref_sort_by_key),getString(R.string.pref_sort_by_defautl));
        fetchTheMovieDBInfo.execute(sortBy);
    }


    public class FetchTheMovieDBInfo extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

        private String LOG_TAG = FetchTheMovieDBInfo.class.getSimpleName();

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

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
                if (params[0].equalsIgnoreCase(MOST_POPULAR)) {
                    url = new URL(buildUriMostPopular.toString());
                } else if (params[0].equalsIgnoreCase(TOP_RATE_PARAM)) {
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
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));


                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                moviesJsonStr = buffer.toString();

                Log.v(LOG_TAG, moviesJsonStr);


            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                return null;
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
                return getMoviesDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        private ArrayList<HashMap<String, String>> getMoviesDataFromJson(String moviesJsonStr) throws JSONException {

            ArrayList<HashMap<String, String>> resultList = new ArrayList<>();

            final String MDB_RESULTS = "results";
//            final String MBD_POSTER_PATH="poster_path";
//            final String MBD_TITLE = "original_title";
//            final String MBD_OVERVIEW = "overview";
//            final String MBD_VOTE_AVERAGE="vote_average";
//            final String MBD_RELEASE_DATE="release_date";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(MDB_RESULTS);


            for (int i = 0; i < moviesArray.length(); i++) {

                HashMap<String, String> map = new HashMap<String, String>();
                String posterPath;
                String title;
                String overview;
                String voteAverage;
                String releaseDate;


                JSONObject jsonMovie = moviesArray.getJSONObject(i);

                posterPath = jsonMovie.getString(getString(R.string.mdb_poster_path_key));
                title = jsonMovie.getString((getString(R.string.mdb_title_key)));
                overview = jsonMovie.getString(getString(R.string.mdb_overview_key));
                voteAverage = jsonMovie.getString(getString(R.string.mdb_vote_average_key));
                releaseDate = jsonMovie.getString(getString(R.string.mdb_release_date_key));


                map.put(getString(R.string.mdb_poster_path_key), posterPath);
                map.put(getString(R.string.mdb_title_key), title);
                map.put(getString(R.string.mdb_overview_key), overview);
                map.put(getString(R.string.mdb_vote_average_key), voteAverage);
                map.put(getString(R.string.mdb_release_date_key), releaseDate);

                resultList.add(map);


            }

            return resultList;
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
            String uri = IMAGE_URL_BASE + SIZE_w500 +path;
            URL url = null;
            try {
                url = new URL(uri);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;

        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> hashMaps) {
            if (hashMaps != null) {
                androidMovieAdapter.clear();
                for (HashMap map : hashMaps) {

                    URL url = pathPosterToURL(map.get(getString(R.string.mdb_poster_path_key)).toString());

                    Movie movie = new Movie(
                            url.toString(),
                            map.get(getString(R.string.mdb_title_key)).toString(),
                            map.get(getString(R.string.mdb_overview_key)).toString(),
                            map.get(getString(R.string.mdb_vote_average_key)).toString(),
                            map.get(getString(R.string.mdb_release_date_key)).toString());
                    androidMovieAdapter.add(movie);

                }
            }
        }
    }


}
