package com.jmgarzo.infomovies;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jmgarzo.infomovies.data.MoviesContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int MOVIE_LOADER = 0;
    private String TOP_RATE_PARAM = "top_rate";
    private String MOST_POPULAR = "most_popular";


    private MovieAdapter mMovieAdapter;

    private static final String[] MOVIE_COLUMNS = {
            MoviesContract.MoviesEntry.TABLE_NAME + "." + MoviesContract.MoviesEntry._ID,
            MoviesContract.MoviesEntry.POSTER_PATH,
            MoviesContract.MoviesEntry.ADULT,
            MoviesContract.MoviesEntry.OVERVIEW,
            MoviesContract.MoviesEntry.RELEASE_DATE,
            MoviesContract.MoviesEntry.MOVIE_WEB_ID,
            MoviesContract.MoviesEntry.ORIGINAL_TITLE,
            MoviesContract.MoviesEntry.ORIGINAL_LANGUAGE,
            MoviesContract.MoviesEntry.TITLE,
            MoviesContract.MoviesEntry.BACKDROP_PATH,
            MoviesContract.MoviesEntry.POPULARITY,
            MoviesContract.MoviesEntry.VOTE_COUNT,
            MoviesContract.MoviesEntry.VIDEO,
            MoviesContract.MoviesEntry.VOTE_AVERAGE
    };

    static final int COL_MOVIE_ID = 0;
    static final int COL_POSTER_PATH = 1;
    static final int COL_ADULT = 2;
    static final int COL_OVERVIEW = 3;
    static final int COL_RELEASE_DATE = 4;
    static final int COL_MOVIE_WEB_ID = 5;
    static final int COL_ORIGINAL_TITLE = 6;
    static final int COL_ORIGINAL_LANGUAGE = 7;
    static final int COL_TITLE = 8;
    static final int COL_BACKDROP_PATH = 9;
    static final int COL_POPULARITY = 10;
    static final int COL_VOTE_COUNT = 11;
    static final int COL_VIDEO = 12;
    static final int COL_VOTE_AVERAGE = 13;


    public MainActivityFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //TODO Think about how to store diference movies sort by
        String sortBy = Utility.getPreferredSortBy(getActivity());

        Uri moviesSortBy = MoviesContract.MoviesEntry.CONTENT_URI;

        mMovieAdapter = new MovieAdapter(getActivity(),null,0);
        //Cursor cur = getActivity().getContentResolver().query(moviesSortBy, null, null, null, null);


        //mMovieAdapter = new MovieAdapter(getActivity(), cur, 0);


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(mMovieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                                                if (cursor != null) {
                                                    Intent intent = new Intent(getActivity(), DetailMovie.class);
//                                                    intent.putExtra(getString(R.string.mdb_movie_web_id_key), cursor.getString(COL_MOVIE_WEB_ID));
                                                    intent.setData(MoviesContract.MoviesEntry.buildMovieWithWebId(cursor.getString(COL_MOVIE_WEB_ID)));
                                                    intent.putExtra(MoviesContract.MoviesEntry._ID,cursor.getString(COL_MOVIE_ID));
                                                    startActivity(intent);
                                                }
                                            }
                                        }

        );


//        androidMovieAdapter = new AndroidMovieAdapter(getActivity(), new ArrayList<Movie>());
//
//
//
//        final GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
//        gridView.setAdapter(androidMovieAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Movie movie = androidMovieAdapter.getItem(position);
//
//                Intent intent = new Intent(getActivity(), DetailMovie.class);
//                intent.putExtra(getString(R.string.mdb_movie_web_id_key), movie.getWebMovieId());
//                //intent.putExtra("movie",movie);
//                startActivity(intent);
//            }
//        });


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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private void updateMovies() {
        FetchTheMovieDBInfo fetchTheMovieDBInfo = new FetchTheMovieDBInfo(getActivity());
        String sortBy = Utility.getPreferredSortBy(getActivity());
        fetchTheMovieDBInfo.execute(sortBy);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String orderBy = Utility.getPreferredSortBy(getActivity());

        Uri moviesUri = MoviesContract.MoviesEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),
                moviesUri,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMovieAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieAdapter.swapCursor(null);
    }


//    public class FetchTheMovieDBInfo extends AsyncTask<String, Void, Void> {
//
//        private String LOG_TAG = FetchTheMovieDBInfo.class.getSimpleName();
//
//        private final Context mContext;
//
//        public FetchTheMovieDBInfo(Context context) {
//            mContext = context;
//        }
//
//        @Override
//        protected Void doInBackground(String... params) {
//
//            if (params.length == 0) {
//                return null;
//            }
//
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//
//            String sortByPopularity = "popularity.desc";
//            String sortByVoteAverage = "vote_average.desc";
//            String certificationCountryUS = "US";
//            String certification = "R";
//
//
//            final String MOVIES_DB_BASE_URL = "https://api.themoviedb.org/3/";
//            final String MOST_POPULAR = "movie/popular";
//            final String TOP_RATE = "movie/top_rated";
////            final String SORT_BY_PARAM = "sort_by";
////            final String CERTIFICATION_COUNTRY_PARAM = "certification_country";
////            final String CERTIFICATION_PARAM = "certification";
//            final String API_KEY_PARAM = "api_key";
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
//            Uri buildUriMostPopular = Uri.parse(MOVIES_DB_BASE_URL + MOST_POPULAR).buildUpon()
//                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
//                    .build();
//
//            Uri buildUriTopRate = Uri.parse(MOVIES_DB_BASE_URL + TOP_RATE).buildUpon()
//                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
//                    .build();
//            // Uri build = Uri.parse("http://api.themoviedb.org/3/movie/popular?api_key=3890bbe3b27964c4c01fe8863a852df5").buildUpon().build();
//
//            String moviesJsonStr = null;
//            try {
//
//
//                URL url;
//                if (params[0].equalsIgnoreCase(MOST_POPULAR)) {
//                    url = new URL(buildUriMostPopular.toString());
//                } else if (params[0].equalsIgnoreCase(TOP_RATE_PARAM)) {
//                    url = new URL(buildUriTopRate.toString());
//                } else {
//                    url = new URL(buildUriMostPopular.toString());
//                }
//                Log.v(LOG_TAG, "Built URI " + buildUriTopRate.toString());
//                Log.v(LOG_TAG, "Built URI " + buildUriMostPopular.toString());
//
//
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    return null;
//                }
//
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line + "\n");
//                }
//
//                if (buffer.length() == 0) {
//                    return null;
//                }
//
//                moviesJsonStr = buffer.toString();
//
//                Log.v(LOG_TAG, moviesJsonStr);
//
//
//            } catch (MalformedURLException e) {
//                Log.e(LOG_TAG, e.getMessage(), e);
//            } catch (IOException e) {
//                Log.e(LOG_TAG, e.getMessage(), e);
//                return null;
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (IOException e) {
//                        Log.e(LOG_TAG, "Error closing stream", e);
//                    }
//                }
//            }
//
//
//            try {
//                getMoviesDataFromJson(moviesJsonStr, getActivity());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//
//        private void getMoviesDataFromJson(String moviesJsonStr, Context context) throws JSONException {
//
//            ArrayList<HashMap<String, String>> resultList = new ArrayList<>();
//
//            final String MDB_RESULTS = "results";
////            final String MBD_POSTER_PATH="poster_path";
////            final String MBD_TITLE = "original_title";
////            final String MBD_OVERVIEW = "overview";
////            final String MBD_VOTE_AVERAGE="vote_average";
////            final String MBD_RELEASE_DATE="release_date";
//
//            JSONObject moviesJson = new JSONObject(moviesJsonStr);
//            JSONArray moviesArray = moviesJson.getJSONArray(MDB_RESULTS);
//
//            Vector<ContentValues> cVVector = new Vector<ContentValues>(moviesArray.length());
//
//            for (int i = 0; i < moviesArray.length(); i++) {
//
//                String posterPath;
//                String adult;
//                String overview;
//                String releaseDate;
//                String movieWebId;
//                String originalTitle;
//                String originalLanguage;
//                String title;
//                String backdropPath;
//                String popularity;
//                String voteCount;
//                String video;
//                String voteAverage;
//
//
//                JSONObject jsonMovie = moviesArray.getJSONObject(i);
//
//
//                posterPath = jsonMovie.getString(getString(R.string.mdb_poster_path_key));
//
//
//                adult = jsonMovie.getString(getString(R.string.mdb_adult_key));
//                overview = jsonMovie.getString(getString(R.string.mdb_overview_key));
//                releaseDate = jsonMovie.getString(getString(R.string.mdb_release_date_key));
//                movieWebId = jsonMovie.getString(getString(R.string.mdb_movie_web_id_key));
//                originalTitle = jsonMovie.getString(getString(R.string.mdb_original_title_key));
//                originalLanguage = jsonMovie.getString(getString(R.string.mdb_original_language_key));
//                title = jsonMovie.getString((getString(R.string.mdb_title_key)));
//                backdropPath = jsonMovie.getString(getString(R.string.mdb_backdrop_path_key));
//                popularity = jsonMovie.getString(getString(R.string.mdb_popularity_key));
//                voteCount = jsonMovie.getString(getString(R.string.mdb_vote_count_key));
//                video = jsonMovie.getString(getString(R.string.mdb_video_key));
//                voteAverage = jsonMovie.getString(getString(R.string.mdb_vote_average_key));
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
//                ContentValues movieValues = new ContentValues();
//
//
//                URL url = pathPosterToURL(posterPath);
//                movieValues.put(MoviesContract.MoviesEntry.POSTER_PATH, checkNull(url.toString()));
//
//                movieValues.put(MoviesContract.MoviesEntry.ADULT, checkNull(adult));
//                movieValues.put(MoviesContract.MoviesEntry.OVERVIEW, checkNull(overview));
//                movieValues.put(MoviesContract.MoviesEntry.RELEASE_DATE, checkNull(releaseDate));
//                movieValues.put(MoviesContract.MoviesEntry.MOVIE_WEB_ID, checkNull(movieWebId));
//                movieValues.put(MoviesContract.MoviesEntry.ORIGINAL_TITLE, checkNull(originalTitle));
//                movieValues.put(MoviesContract.MoviesEntry.ORIGINAL_LANGUAGE, checkNull(originalLanguage));
//                movieValues.put(MoviesContract.MoviesEntry.TITLE, checkNull(title));
//                movieValues.put(MoviesContract.MoviesEntry.BACKDROP_PATH, checkNull(backdropPath));
//                movieValues.put(MoviesContract.MoviesEntry.POPULARITY, checkNull(popularity));
//                movieValues.put(MoviesContract.MoviesEntry.VOTE_COUNT, checkNull(voteCount));
//                movieValues.put(MoviesContract.MoviesEntry.VIDEO, checkNull(video));
//                movieValues.put(MoviesContract.MoviesEntry.VOTE_AVERAGE, checkNull(voteAverage));
//
////                imageToFile(url.toString(),movieWebId,context);
//
//                cVVector.add(movieValues);
//
//
//            }
//
//            //add to database
//            if (cVVector.size() > 0) {
//                ContentValues[] cvArray = new ContentValues[cVVector.size()];
//                cVVector.toArray(cvArray);
//                mContext.getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI, null, null);
//                mContext.getContentResolver().bulkInsert(MoviesContract.MoviesEntry.CONTENT_URI, cvArray);
//            }
//
//            // return resultList;
//        }
//
//        private String checkNull(String value) {
//            if (null == value) {
//                return "";
//            }
//            return value;
//        }
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
//        private URL pathPosterToURL(String path) {
//
//            final String IMAGE_URL_BASE = "http://image.tmdb.org/t/p/";
//            final String SIZE_w92 = "w92";
//            final String SIZE_w154 = "w154";
//            final String SIZE_w185 = "w185";
//            final String SIZE_w342 = "w342";
//            final String SIZE_w500 = "w500";
//            final String SIZE_w780 = "w780";
//
//            if (null == path || path.equalsIgnoreCase("")) {
//                return null;
//            }
////            Uri builtUri = Uri.parse(IMAGE_URL_BASE).buildUpon()
////                    .appendPath(SIZE_w185)
////                    .appendPath(path.toString()).build();
//            String uri = IMAGE_URL_BASE + SIZE_w500 + path;
//            URL url = null;
//            try {
//                url = new URL(uri);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            return url;
//
//        }
//
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
//    }


}
