package com.jmgarzo.udacity.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.utilities.NetworksUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivityFragment extends Fragment implements MovieGridViewAdapter.MovieGridViewAdapterOnClickHandler {

    private static final String TAG_LOG = MainActivityFragment.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private MovieGridViewAdapter mGridViewAdapter;

    private TextView mErrorMenssageDisplay;
    private ProgressBar mLoadingIndicator;

    public interface Callback {
        public void OnItemSelected(int movieId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_main_activity, container, false);

        mRecyclerView = (RecyclerView) viewRoot.findViewById(R.id.recyclerview_movies);

        mErrorMenssageDisplay = (TextView) viewRoot.findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mGridViewAdapter = new MovieGridViewAdapter(this);

        mRecyclerView.setAdapter(mGridViewAdapter);

        mLoadingIndicator = (ProgressBar) viewRoot.findViewById(R.id.pb_loading_indicator);
        loadMovieThumbs();

        return viewRoot;
    }

    private void loadMovieThumbs() {
        new FetchDataMovies().execute(getActivity());
    }

    @Override
    public void onClick(int movieId) {
        ((Callback) getActivity()).OnItemSelected(movieId);


    }

    private void showMovieThumbs() {
        mErrorMenssageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMenssageDisplay.setVisibility(View.VISIBLE);
    }


    public class FetchDataMovies extends AsyncTask<Context, Void, ArrayList<Movie>> {

        private String LOG_TAG = FetchDataMovies.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Context... contexts) {

            if (contexts.length == 0) {
                return null;
            }

            URL moviesURL = NetworksUtils.buildURL(contexts[0]);

            ArrayList<Movie> moviesList = null;
            try {
                String jsonMoviesResponse = NetworksUtils.getResponseFromHttpUrl(moviesURL);
                moviesList = getMoviesFromJson(jsonMoviesResponse);

            } catch (IOException e) {
                Log.e(LOG_TAG, e.toString());
            }


            return moviesList;
        }

        private ArrayList<Movie> getMoviesFromJson(String moviesJsonStr) {
            final String MOVIE_RESULTS = "results";
            final String POSTER_PATH = "poster_path";
            final String ID = "id";

            ArrayList<Movie> moviesList = null;

            JSONObject moviesJson = null;
            try {
                moviesJson = new JSONObject(moviesJsonStr);
                JSONArray moviesArray = moviesJson.getJSONArray(MOVIE_RESULTS);
                String posterPath = "";
                Integer id = null;
                moviesList = new ArrayList<>();
                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject jsonMovie = moviesArray.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setId(jsonMovie.getInt(ID));
                    movie.setPosterPath(jsonMovie.getString(POSTER_PATH));

                    moviesList.add(movie);
                }

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.toString());
            }

            return moviesList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> moviesList) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (null != moviesList) {
                showMovieThumbs();
                mGridViewAdapter.setMovies(moviesList);
            } else {
                showErrorMessage();
            }
        }
    }


}
