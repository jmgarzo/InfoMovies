package com.jmgarzo.udacity.popularmovies;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;
import com.jmgarzo.udacity.popularmovies.utilities.DataBaseUtils;
import com.jmgarzo.udacity.popularmovies.utilities.SettingsUtils;


public class MainActivityFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        MovieGridViewAdapter.MovieGridViewAdapterOnClickHandler {

    private static final String TAG_LOG = MainActivityFragment.class.getSimpleName();

    private static final int ID_MOVIES_LOADER = 14;


    private RecyclerView mRecyclerView;
    private MovieGridViewAdapter mGridViewAdapter;

    private TextView mErrorMenssageDisplay;
    private ProgressBar mLoadingIndicator;

    Movie mMovie;


    public interface Callback {
        public void OnItemSelected(Movie movie);
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

        mGridViewAdapter = new MovieGridViewAdapter(getContext(), this);

        mRecyclerView.setAdapter(mGridViewAdapter);

        mLoadingIndicator = (ProgressBar) viewRoot.findViewById(R.id.pb_loading_indicator);
        loadMovieThumbs();

        getActivity().getSupportLoaderManager().initLoader(ID_MOVIES_LOADER, null, this);


        return viewRoot;
    }

    private void loadMovieThumbs() {
        // new FetchDataMovies().execute(getActivity());
    }

    @Override
    public void onClick(Movie movie) {
        ((Callback) getActivity()).OnItemSelected(movie);


    }

    private void showMovieThumbs() {
        mErrorMenssageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMenssageDisplay.setVisibility(View.VISIBLE);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_MOVIES_LOADER: {

                String selectionArg = "";
                if (SettingsUtils.isPreferenceSortByMostPopular(getContext())) {
                    selectionArg = PopularMovieContract.MOST_POPULAR_REGISTRY_TYPE;
                } else if (SettingsUtils.isPreferenceSortByTopRated(getContext())){
                    selectionArg = PopularMovieContract.TOP_RATE_REGISTRY_TYPE;
                } else {
                    selectionArg = PopularMovieContract.FAVORITE_REGISTRY_TYPE;
                }

                return new CursorLoader(
                        getContext(),
                        PopularMovieContract.MovieEntry.CONTENT_URI,
                        DataBaseUtils.MOVIE_COLUMS,
                        PopularMovieContract.MovieEntry.REGISTRY_TYPE + " = ?",
                        new String[]{selectionArg},
                        null);
            }

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mGridViewAdapter.swapCursor(data);

        //TODO:Quedan cosas por hacer para ajusar pantalla
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mGridViewAdapter.swapCursor(null);
    }


}
