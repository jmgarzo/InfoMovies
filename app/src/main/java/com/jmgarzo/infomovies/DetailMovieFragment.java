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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jmgarzo.infomovies.data.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

//    private ImageView imageViewPoster = null;
//    private TextView txtSynopsis = null;

//    public DetailMovieFragment() {
//    }

    private static final String LOG_TAG = DetailMovieFragment.class.getSimpleName();

    static final String DETAIL_URI = "URI";
    static final String TWO_PANELS = "TWO_PANELS";


    private static final int DETAIL_LOADER = 0;
    private static final int DETAIL_TRAILER_LOADER = 1;
    private static final int DETAIL_REVIEW_LOADER = 2;

    public static final String[] MOVIE_COLUMNS = {
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.TABLE_NAME + "." + com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry._ID,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.POSTER_PATH,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.ADULT,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.OVERVIEW,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.RELEASE_DATE,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.MOVIE_WEB_ID,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.ORIGINAL_TITLE,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.ORIGINAL_LANGUAGE,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.TITLE,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.BACKDROP_PATH,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.POPULARITY,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.VOTE_COUNT,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.VIDEO,
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.VOTE_AVERAGE,
            MoviesContract.MoviesEntry.MOST_POPULAR,
            MoviesContract.MoviesEntry.TOP_RATE

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
    static final int COL_MOST_POPULAR = 14;
    static final int COL_TOP_RATE = 15;




    public static final String[] VIDEO_COLUMNS =
            {
                    com.jmgarzo.infomovies.data.MoviesContract.VideoEntry.TABLE_NAME + "." + com.jmgarzo.infomovies.data.MoviesContract.VideoEntry._ID,
                    com.jmgarzo.infomovies.data.MoviesContract.VideoEntry.MOVIE_KEY,
                    com.jmgarzo.infomovies.data.MoviesContract.VideoEntry.ID,
                    com.jmgarzo.infomovies.data.MoviesContract.VideoEntry.ISO_639_1,
                    com.jmgarzo.infomovies.data.MoviesContract.VideoEntry.ISO_3166_1,
                    com.jmgarzo.infomovies.data.MoviesContract.VideoEntry.KEY,
                    com.jmgarzo.infomovies.data.MoviesContract.VideoEntry.NAME,
                    com.jmgarzo.infomovies.data.MoviesContract.VideoEntry.SITE,
                    com.jmgarzo.infomovies.data.MoviesContract.VideoEntry.SIZE,
                    com.jmgarzo.infomovies.data.MoviesContract.VideoEntry.TYPE
            };

    static final int COL_VIDEO_ID = 0;
    static final int COL_VIDEO_MOVIE_KEY = 1;
    static final int COL_VIDEO_WEB_ID = 2;
    static final int COL_VIDEO_ISO_639_1 = 3;
    static final int COL_VIDEO_ISO_3166_1 = 4;
    static final int COL_VIDEO_KEY = 5;
    static final int COL_VIDEO_NAME = 6;
    static final int COL_VIDEO_SITE = 7;
    static final int COL_VIDEO_SIZE = 8;
    static final int COL_VIDEO_TYPE = 9;


    public static final String[] REVIEW_COLUMNS =
            {
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.TABLE_NAME + "." + com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry._ID,
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.MOVIE_KEY,
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.ID,
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.AUTHOR,
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.CONTENT,
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.URL
            };

    static final int COL_REVIEW_ID = 0;
    static final int COL_REVIEW_MOVIE_KEY = 1;
    static final int COL_REVIEW_WEB_ID_KEY = 2;
    static final int COL_REVIEW_AUTHOR = 3;
    static final int COL_REVIEW_CONTENT = 4;
    static final int COL_REVIEW_URL = 5;





    public static final String[] FAVORITE_MOVIE_COLUMNS = {
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.TABLE_NAME + "." + com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry._ID,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.POSTER_PATH,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.ADULT,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.OVERVIEW,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.RELEASE_DATE,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.MOVIE_WEB_ID,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.ORIGINAL_TITLE,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.ORIGINAL_LANGUAGE,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.TITLE,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.BACKDROP_PATH,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.POPULARITY,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.VOTE_COUNT,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.VIDEO,
            com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry.VOTE_AVERAGE
    };

    static final int COL_FAVORITE_MOVIE_ID = 0;
    static final int COL_FAVORITE_POSTER_PATH = 1;
    static final int COL_FAVORITE_ADULT = 2;
    static final int COL_FAVORITE_OVERVIEW = 3;
    static final int COL_FAVORITE_RELEASE_DATE = 4;
    static final int COL_FAVORITE_MOVIE_WEB_ID = 5;
    static final int COL_FAVORITE_ORIGINAL_TITLE = 6;
    static final int COL_FAVORITE_ORIGINAL_LANGUAGE = 7;
    static final int COL_FAVORITE_TITLE = 8;
    static final int COL_FAVORITE_BACKDROP_PATH = 9;
    static final int COL_FAVORITE_POPULARITY = 10;
    static final int COL_FAVORITE_VOTE_COUNT = 11;
    static final int COL_FAVORITE_VIDEO = 12;
    static final int COL_FAVORITE_VOTE_AVERAGE = 13;



    public static final String[] FAVORITE_VIDEO_COLUMNS =
            {
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry.TABLE_NAME + "." + com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry._ID,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry.MOVIE_KEY,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry.ID,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry.ISO_639_1,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry.ISO_3166_1,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry.KEY,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry.NAME,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry.SITE,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry.SIZE,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry.TYPE
            };

    static final int COL_FAVORITE_VIDEO_ID = 0;
    static final int COL_FAVORITE_VIDEO_MOVIE_KEY = 1;
    static final int COL_FAVORITE_VIDEO_WEB_ID = 2;
    static final int COL_FAVORITE_VIDEO_ISO_639_1 = 3;
    static final int COL_FAVORITE_VIDEO_ISO_3166_1 = 4;
    static final int COL_FAVORITE_VIDEO_KEY = 5;
    static final int COL_FAVORITE_VIDEO_NAME = 6;
    static final int COL_FAVORITE_VIDEO_SITE = 7;
    static final int COL_FAVORITE_VIDEO_SIZE = 8;
    static final int COL_FAVORITE_VIDEO_TYPE = 9;


    public static final String[] FAVORITE_REVIEW_COLUMNS =
            {
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteReviewEntry.TABLE_NAME + "." + com.jmgarzo.infomovies.data.MoviesContract.FavoriteReviewEntry._ID,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteReviewEntry.MOVIE_KEY,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteReviewEntry.ID,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteReviewEntry.AUTHOR,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteReviewEntry.CONTENT,
                    com.jmgarzo.infomovies.data.MoviesContract.FavoriteReviewEntry.URL
            };

    static final int COL_FAVORITE_REVIEW_ID = 0;
    static final int COL_FAVORITE_REVIEW_MOVIE_KEY = 1;
    static final int COL_FAVORITE_REVIEW_WEB_ID_KEY = 2;
    static final int COL_FAVORITE_REVIEW_AUTHOR = 3;
    static final int COL_FAVORITE_REVIEW_CONTENT = 4;
    static final int COL_FAVORITE_REVIEW_URL = 5;



    private ListView listViewTrailers;
    private ListView listViewReview;
    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;
    private Uri mUri;
    private String mIdMovie;
    private boolean mTwoPanels;

    Toolbar mToolbar;

    public DetailMovieFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mTwoPanels = false;
        Bundle argument = getArguments();

        if (argument != null) {
            mUri = argument.getParcelable(DetailMovieFragment.DETAIL_URI);
            if(Utility.isPreferenceSortByFavorite(getContext())){
                mIdMovie = argument.getString(com.jmgarzo.infomovies.data.MoviesContract.FavoriteMovieEntry._ID);
            }else {
                mIdMovie = argument.getString(com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry._ID);

            }
            mTwoPanels = argument.getBoolean(TWO_PANELS);

            mToolbar= (Toolbar) getActivity().findViewById(R.id.toolbar_detail);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }


        View rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        hasOptionsMenu();


        mTrailerAdapter = new TrailerAdapter(getActivity(), null, 0);


        listViewTrailers = (ListView) rootView.findViewById(R.id.listview_trailers);
        listViewTrailers.setAdapter(mTrailerAdapter);

        listViewTrailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    String key = cursor.getString(COL_VIDEO_KEY);
                    startActivity(new Intent(Intent.ACTION_VIEW, buildTrailerURL(key)));

                }
            }
        });

        mReviewAdapter = new ReviewAdapter(getActivity(), null, 0);
        listViewReview = (ListView) rootView.findViewById(R.id.listview_review);
        listViewReview.setAdapter(mReviewAdapter);

        listViewReview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        return rootView;
    }

    private Uri buildTrailerURL(String trailerKey) {

        final String BASE_YOUTUBE_URI = "http://www.youtube.com/watch";
        final String VIDEO_PARAM = "v";
        return Uri.parse(BASE_YOUTUBE_URI).buildUpon().appendQueryParameter(VIDEO_PARAM, trailerKey).build();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        getLoaderManager().initLoader(DETAIL_TRAILER_LOADER, null, this);
        getLoaderManager().initLoader(DETAIL_REVIEW_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    void onSortChanged() {
        // replace the uri, since the sort by  has changed
        Uri uri = mUri;
        if (null != uri) {
            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "In onCreateLoader");
//            Intent intent = getActivity().getIntent();
//            if (intent == null || intent.getData() == null) {
//                return null;
//            }
        if (mUri == null || mIdMovie == null) {
            return null;
        }

        try {
            if(Utility.isPreferenceSortByFavorite(getContext())){
                switch (id) {
                    case DETAIL_LOADER: {
                        return new CursorLoader(
                                getActivity(),
                                MoviesContract.FavoriteMovieEntry.buildFavoriteUri(Long.parseLong(mIdMovie)),
                                FAVORITE_MOVIE_COLUMNS,
                                null,
                                null,
                                null
                        );
                    }
                    case DETAIL_TRAILER_LOADER: {
                        return new CursorLoader(
                                getActivity(),
                                com.jmgarzo.infomovies.data.MoviesContract.FavoriteVideoEntry.buildFavoriteVideoWithMovieId(mIdMovie),
                                FAVORITE_VIDEO_COLUMNS,
                                null,
                                null,
                                null
                        );

                    }
                    case DETAIL_REVIEW_LOADER: {
                        return new CursorLoader(
                                getActivity(),
                                com.jmgarzo.infomovies.data.MoviesContract.FavoriteReviewEntry.buildFavoriteReviewWithMovieId(mIdMovie),
                                FAVORITE_REVIEW_COLUMNS,
                                null,
                                null,
                                null
                        );
                    }
                }
            }else {
                switch (id) {
                    case DETAIL_LOADER: {


                        return new CursorLoader(
                                getActivity(),
                                MoviesContract.MoviesEntry.buildMoviesWithIdUri(Long.parseLong(mIdMovie)),
                                MOVIE_COLUMNS,
                                null,
                                null,
                                null
                        );
                    }

                    case DETAIL_TRAILER_LOADER: {
                        return new CursorLoader(
                                getActivity(),
                                com.jmgarzo.infomovies.data.MoviesContract.VideoEntry.buildVideoWithMovieId(mIdMovie),
                                VIDEO_COLUMNS,
                                null,
                                null,
                                null
                        );

                    }

                    case DETAIL_REVIEW_LOADER: {
                        return new CursorLoader(
                                getActivity(),
                                com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.buildReviewWithMovieId(mIdMovie),
                                REVIEW_COLUMNS,
                                null,
                                null,
                                null
                        );


                    }
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }


        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "In onLoadFinished");
        if (!data.moveToFirst()) {
            return;
        }

        switch (loader.getId()) {
            case DETAIL_LOADER: {



                    ImageView imageViewPoster = (ImageView) getView().findViewById(R.id.poster_image);
                    if(Utility.isPreferenceSortByFavorite(getContext())){
                        Picasso.with(getContext()).load(data.getString(COL_FAVORITE_POSTER_PATH)).into(imageViewPoster);
                    }else {
                        Picasso.with(getContext()).load(data.getString(COL_POSTER_PATH)).into(imageViewPoster);
                    }





                TextView txtOverview = (TextView) getView().findViewById(R.id.overview_text);



                TextView txtReleaseDate = (TextView) getView().findViewById(R.id.txt_release_date);
                TextView txtVoteAverage = (TextView) getView().findViewById(R.id.txt_vote_average);


                if(Utility.isPreferenceSortByFavorite(getContext())){
                    txtOverview.setText(data.getString(COL_FAVORITE_OVERVIEW));
                    txtReleaseDate.setText(Utility.getMonthAndYear(data.getString(COL_FAVORITE_RELEASE_DATE)));
                    txtVoteAverage.setText(data.getString(COL_FAVORITE_VOTE_AVERAGE));
                }else {
                    txtOverview.setText(data.getString(COL_OVERVIEW));
                    txtReleaseDate.setText(Utility.getMonthAndYear(data.getString(COL_FAVORITE_RELEASE_DATE)));
                    txtVoteAverage.setText(data.getString(COL_VOTE_AVERAGE));
                }

                if (txtOverview != null && txtOverview.getText() != "") {
                    TextView overviewLabel = (TextView) getView().findViewById(R.id.overview_label);
                    overviewLabel.setText(getString(R.string.overview_label));
                    overviewLabel.setVisibility(View.VISIBLE);
                }

//                TextView trailerLabel = (TextView) getView().findViewById(R.id.trailer_label);
//                trailerLabel.setText(R.string.trailer_label);
//                trailerLabel.setVisibility(View.INVISIBLE);
//
//                TextView reviewLabel = (TextView) getView().findViewById(R.id.review_label);
//                reviewLabel.setText(R.string.review_label);
//                reviewLabel.setVisibility(View.INVISIBLE);
                break;
            }
            case DETAIL_TRAILER_LOADER: {
                if (data.moveToFirst())

                    mTrailerAdapter.swapCursor(data);


                if (data.moveToFirst()) {
                    TextView trailerLabel = (TextView) getView().findViewById(R.id.trailer_label);
                    trailerLabel.setText(getString(R.string.trailer_label));
                    trailerLabel.setVisibility(View.VISIBLE);
                }
                setListViewHeightBasedOnChildren2(listViewTrailers);
                break;

            }

            case DETAIL_REVIEW_LOADER: {
                mReviewAdapter.swapCursor(data);
                if (data.moveToFirst()) {
                    TextView reviewLabel = (TextView) getView().findViewById(R.id.review_label);
                    reviewLabel.setText(getString(R.string.review_label));
                    reviewLabel.setVisibility(View.VISIBLE);
                }
                setListViewHeightBasedOnChildren2(listViewReview);
                break;
            }
        }


    }

    public void setListViewHeightBasedOnChildren2(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) return;
        if (listAdapter.getCount() <= 1) return;


        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int totalHeight = 0;

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = 0;
        listView.setLayoutParams(params);
        listView.requestLayout();

        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount())) + 8;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        switch (loader.getId()) {
            case DETAIL_TRAILER_LOADER: {

                mTrailerAdapter.swapCursor(null);
                getLoaderManager().restartLoader(DETAIL_TRAILER_LOADER, null, this);

                break;
            }
            case DETAIL_REVIEW_LOADER: {
                mReviewAdapter.swapCursor(null);
                getLoaderManager().restartLoader(DETAIL_REVIEW_LOADER, null, this);

                break;
            }
        }
    }

    @Override
    public void onPause() {

        mReviewAdapter.swapCursor(null);
        mTrailerAdapter.swapCursor(null);
        super.onPause();
    }

    @Override
    public void onResume() {
        if (null != getLoaderManager().getLoader(DETAIL_TRAILER_LOADER) &&
                null != getLoaderManager().getLoader(DETAIL_REVIEW_LOADER)) {
            getLoaderManager().restartLoader(DETAIL_REVIEW_LOADER, null, this);
            getLoaderManager().restartLoader(DETAIL_TRAILER_LOADER, null, this);
        }
        super.onResume();

    }




}
