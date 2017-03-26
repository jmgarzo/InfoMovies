package com.jmgarzo.udacity.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.Objects.Trailer;
import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;
import com.jmgarzo.udacity.popularmovies.sync.AddFavoriteIntentService;
import com.jmgarzo.udacity.popularmovies.sync.DeleteFromFavoriteIntentService;
import com.jmgarzo.udacity.popularmovies.utilities.DataBaseUtils;
import com.jmgarzo.udacity.popularmovies.utilities.NetworksUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        TrailerAdapter.TrailerAdapterOnClickHandler {

    private final String LOG_TAG = DetailFragment.class.getSimpleName();

    public static final String FAVORITE_MOVIE_TAG = "favorite_movie_tag";
    public static final String MOVIE_TAG = "movie_tag";

    private static final int FAB_LOADER = 0;
    private static final int TRAILER_LOADER = 1;
    private static final int REVIEW_LOADER = 2;

    //private int movieId;
    private Movie mMovie;


    @BindView(R.id.pb_loading_indicator_detail) ProgressBar mProgressBar;
    @BindView(R.id.tv_error_message_detail) TextView mErrorMenssageDetail;
    @BindView(R.id.movie_detail) LinearLayout mContentLayout;// private LinearLayout mContentLayout;
    @BindView(R.id.iv_poster_image) ImageView postertImage;
    @BindView(R.id.tv_release_date) TextView releaseDate;
    @BindView(R.id.tv_vote_average) TextView voteAverage;
    @BindView(R.id.tv_overview_text) TextView overview;


    @BindView(R.id.fab) FloatingActionButton fab; //private FloatingActionButton fab;

    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;
    @BindView(R.id.recyclerview_trailer) RecyclerView  mTrailerRecyclerView;
    @BindView(R.id.recyclerview_review) RecyclerView  mReviewRecyclerView;

    private int mPosition = RecyclerView.NO_POSITION;

    private boolean isFavorite = false;

    public interface Callback {
        public void OnItemSelected(Trailer trailer);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this,view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    Intent deleteFavoriteIntent = new Intent(getContext(), DeleteFromFavoriteIntentService.class);
                    deleteFavoriteIntent.putExtra(FAVORITE_MOVIE_TAG, mMovie);
                    getActivity().startService(deleteFavoriteIntent);
                } else {
                    Intent addToFavoriteIntent = new Intent(getContext(), AddFavoriteIntentService.class);
                    addToFavoriteIntent.putExtra(FAVORITE_MOVIE_TAG, mMovie);
                    getActivity().startService(addToFavoriteIntent);

                }
            }
        });

        Intent intent = getActivity().getIntent();
        if (null != intent) {
            mMovie = intent.getParcelableExtra(MainActivity.MOVIE_INTENT_TAG);

        }



        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_indicator_detail);

        LinearLayoutManager trailerLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager reviewLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mTrailerRecyclerView.setLayoutManager(trailerLayoutManager);
        mTrailerRecyclerView.setHasFixedSize(true);
        mReviewRecyclerView.setLayoutManager(reviewLayoutManager);
        mReviewRecyclerView.setHasFixedSize(true);

        mTrailerAdapter = new TrailerAdapter(getContext(), this);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);

        mReviewAdapter = new ReviewAdapter(getContext());
        mReviewRecyclerView.setAdapter(mReviewAdapter);

        loadMovieData();

        getActivity().getSupportLoaderManager().initLoader(FAB_LOADER, null, this);
        getActivity().getSupportLoaderManager().initLoader(TRAILER_LOADER, null, this);
        getActivity().getSupportLoaderManager().initLoader(REVIEW_LOADER, null, this);


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
            case FAB_LOADER: {

                String selection = PopularMovieContract.MovieEntry.MOVIE_WEB_ID + "= ? AND "
                        + PopularMovieContract.MovieEntry.REGISTRY_TYPE + " = ? ";


                return new CursorLoader(getActivity(),
                        PopularMovieContract.MovieEntry.CONTENT_URI,
                        DataBaseUtils.MOVIE_COLUMNS,
                        selection,
                        new String[]{mMovie.getMovieWebId(), PopularMovieContract.FAVORITE_REGISTRY_TYPE},
                        null);

            }
            case TRAILER_LOADER: {

                String selection = PopularMovieContract.TrailerEntry.MOVIE_KEY + " = ?";
                return new CursorLoader(getActivity(),
                        PopularMovieContract.TrailerEntry.CONTENT_URI,
                        null,
                        selection,
                        new String[]{Integer.toString(mMovie.getId())},
                        null);
            }

            case REVIEW_LOADER: {
                String selection = PopularMovieContract.ReviewEntry.MOVIE_KEY + " = ?";
                return new CursorLoader(getActivity(),
                        PopularMovieContract.ReviewEntry.CONTENT_URI,
                        null,
                        selection,
                        new String[]{Integer.toString(mMovie.getId())},
                        null);
            }

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()) {
            case FAB_LOADER: {
                if (data.moveToFirst()) {
                    isFavorite = true;
                    fab.setImageResource(R.drawable.ic_favorite_white_24px);
                } else {
                    isFavorite = false;
                    fab.setImageResource(R.drawable.ic_favorite_border_white_24px);
                }
                break;

            }
            case TRAILER_LOADER: {
                mTrailerAdapter.swapCursor(data);
                break;
            }
            case REVIEW_LOADER: {
                mReviewAdapter.swapCursor(data);
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        switch (loader.getId()) {
            case TRAILER_LOADER: {
                mTrailerAdapter.swapCursor(null);
                break;
            }
            case REVIEW_LOADER: {
                mReviewAdapter.swapCursor(null);
                break;
            }
        }
    }


    @Override
    public void onClick(Trailer trailer) {

        ((Callback) getActivity()).OnItemSelected(trailer);


    }


    private void loadMovieData() {
        mProgressBar.setVisibility(View.INVISIBLE);
        showMovieData();
        getActivity().setTitle(mMovie.getTitle());
        releaseDate.setText(mMovie.getReleaseDate());
        Picasso.with(getActivity())
                .load(NetworksUtils.buildPosterDetail(mMovie.getPosterPath()))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.ic_broken_image_black_48px)
                .tag(getActivity())
                .into(postertImage);
        voteAverage.setText(Double.toString(mMovie.getVoteAverage()).concat("/10"));
        overview.setText(mMovie.getOverview());

    }

}
