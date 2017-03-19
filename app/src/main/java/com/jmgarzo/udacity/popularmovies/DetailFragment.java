package com.jmgarzo.udacity.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;
import com.jmgarzo.udacity.popularmovies.utilities.DataBaseUtils;
import com.jmgarzo.udacity.popularmovies.utilities.NetworksUtils;
import com.jmgarzo.udacity.popularmovies.utilities.SettingsUtils;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DETAIL_LOADER = 0;
    private int movieId;


    private Activity mActivity;
    private ProgressBar mProgressBar;
    private TextView mErrorMenssageDetail;
    private LinearLayout mContentLayout;
    private ImageView postertImage;
    private TextView releaseDate;
    private TextView voteAverage;
    private TextView overview;


    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mActivity = getActivity();
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_indicator_detail);
        mContentLayout = (LinearLayout) view.findViewById(R.id.content_layout);
        mErrorMenssageDetail = (TextView) view.findViewById(R.id.tv_error_message_detail);
        releaseDate = (TextView) view.findViewById(R.id.tv_release_date);
        postertImage = (ImageView) view.findViewById(R.id.iv_poster_image);
        voteAverage = (TextView) view.findViewById(R.id.tv_vote_average);
        overview = (TextView) view.findViewById(R.id.tv_overview_text);

        Intent intent = getActivity().getIntent();
        if (null != intent) {
            String intentMovieId = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (null != intentMovieId && !"".equals(intentMovieId)) {
                movieId = Integer.parseInt(intentMovieId);
            }
        }

        getLoaderManager().initLoader(DETAIL_LOADER, null, this);


        return view;

    }

    private void showMovieData() {
        mErrorMenssageDetail.setVisibility(View.INVISIBLE);
        mContentLayout.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mContentLayout.setVisibility(View.INVISIBLE);
        mErrorMenssageDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {
            case DETAIL_LOADER: {

                String sortByPreference = SettingsUtils.getPreferredSortBy(getContext());
                String selection = PopularMovieContract.MovieEntry._ID + "= ? AND "
                        + PopularMovieContract.MovieEntry.REGISTRY_TYPE + " = ? ";


                return new CursorLoader(getActivity(),
                        PopularMovieContract.MovieEntry.CONTENT_URI,
                        DataBaseUtils.MOVIE_COLUMS,
                        selection,
                        new String[]{Integer.toString(movieId),sortByPreference},
                        null);

            }
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()) {
            case DETAIL_LOADER: {
                mProgressBar.setVisibility(View.INVISIBLE);
                if (null != data && data.moveToFirst()) {
                    showMovieData();
                    mActivity.setTitle(data.getString(DataBaseUtils.COL_MOVIE_TITLE));
                    releaseDate.setText(data.getString(DataBaseUtils.COL_MOVIE_RELEASE_DATE));
                    Picasso.with(mActivity)
                            .load(NetworksUtils.buildPosterDetail(data.getString(DataBaseUtils.COL_MOVIE_POSTER_PATH)))
                            .placeholder(R.drawable.placeholder)
                            .tag(mActivity)
                            .into(postertImage);
                    voteAverage.setText(data.getString(DataBaseUtils.COL_MOVIE_VOTE_AVERAGE).concat("/10"));
                    overview.setText(data.getString(DataBaseUtils.COL_MOVIE_OVERVIEW));
                } else {
                    showErrorMessage();


                }


            }
        }
    }

            @Override
            public void onLoaderReset (Loader < Cursor > loader) {

            }


        }
