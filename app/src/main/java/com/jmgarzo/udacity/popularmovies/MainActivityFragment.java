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

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivityFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        MovieGridViewAdapter.MovieGridViewAdapterOnClickHandler {

    private static final int LANDSCAPE_AND_600 = 4;
    private static final int LANDSCAPE_AND_PHONE = 3;
    private static final int PORTRAIT_AND_600=3;
    private static final int DEFAULT = 2;


    private static final String TAG_LOG = MainActivityFragment.class.getSimpleName();

    private static final int ID_MOVIES_LOADER = 14;


    @BindView(R.id.recyclerview_movies) RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message_display) TextView mErrorMenssageDisplay;
    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;

    private MovieGridViewAdapter mGridViewAdapter;


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

        ButterKnife.bind(this,viewRoot);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), getSpanCount(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mGridViewAdapter = new MovieGridViewAdapter(getContext(), this);
        mRecyclerView.setAdapter(mGridViewAdapter);

        getActivity().getSupportLoaderManager().initLoader(ID_MOVIES_LOADER, null, this);

        return viewRoot;
    }

    private int getSpanCount(){


        boolean landscape = getResources().getBoolean(R.bool.isLandscape);
        boolean width600 = getResources().getBoolean(R.bool.is600);
        if(landscape && width600){
            return LANDSCAPE_AND_600;
        }else if(landscape){
            return LANDSCAPE_AND_PHONE;
        }else if(width600){
            return PORTRAIT_AND_600;
        }
        return DEFAULT;
    }



    @Override
    public void onClick(Movie movie) {
        ((Callback) getActivity()).OnItemSelected(movie);


    }

    private void showMovieThumbs() {
        mErrorMenssageDisplay.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
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
                        DataBaseUtils.MOVIE_COLUMNS,
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

        if(data.getCount()==0){
            if(SettingsUtils.isPreferenceSortByFavorite(getContext())){
                mErrorMenssageDisplay.setText(getString(R.string.error_message_favorite));
            }
            showErrorMessage();
        }else{
            showMovieThumbs();
        }

;

        mGridViewAdapter.swapCursor(data);

        //TODO:Quedan cosas por hacer para ajustar pantalla
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mGridViewAdapter.swapCursor(null);
    }





}
