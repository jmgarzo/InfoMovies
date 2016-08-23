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
    static final String TWO_PANELS = "TO_PANNELS";


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
            com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry.VOTE_AVERAGE
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


    private static final String[] VIDEO_COLUNMS =
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


    private static final String[] REVIEW_COLUNMS =
            {
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.TABLE_NAME + "." + com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry._ID,
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.MOVIE_KEY,
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.ID,
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.AUTHOR,
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.CONTENT,
                    com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.URL,
            };

    static final int COL_REVIEW_ID = 0;
    static final int COL_REVIEW_MOVIE_KEY = 1;
    static final int COL_REVIEW_WEB_ID_KEY = 2;
    static final int COL_REVIEW_AUTHOR = 3;
    static final int COL_REVIEW_CONTENT = 4;
    static final int COL_REVIEW_URL = 5;


    private ListView listViewTrailers;
    private ListView listViewReview;
    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;
    private Uri mUri;
    private String mIdMovie;
    private boolean mTwoPanels;

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
            mIdMovie = argument.getString(com.jmgarzo.infomovies.data.MoviesContract.MoviesEntry._ID);
            mTwoPanels = argument.getBoolean(TWO_PANELS);

            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_detail);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            if (!mTwoPanels) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }
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
            ;
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

        switch (id) {
            case DETAIL_LOADER: {

                return new CursorLoader(
                        getActivity(),
                        mUri,
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
                        VIDEO_COLUNMS,
                        null,
                        null,
                        null
                );

            }

            case DETAIL_REVIEW_LOADER: {
                Log.v(LOG_TAG, "IdMovie " + mIdMovie);

                return new CursorLoader(
                        getActivity(),
                        com.jmgarzo.infomovies.data.MoviesContract.ReviewEntry.buildReviewWithMovieId(mIdMovie),
                        REVIEW_COLUNMS,
                        null,
                        null,
                        null
                );


            }
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


                if (!mTwoPanels) {
                    ImageView imageViewPoster = (ImageView) getView().findViewById(R.id.poster_image);
                    Picasso.with(getContext()).load(data.getString(COL_POSTER_PATH)).into(imageViewPoster);

                } else {
                    TextView titleTexView = (TextView) getView().findViewById(R.id.txt_title);
                    titleTexView.setText(data.getString(COL_TITLE));
                }


                TextView txtOverview = (TextView) getView().findViewById(R.id.overview_text);
                txtOverview.setText(data.getString(COL_OVERVIEW));

                if (txtOverview != null && txtOverview.getText() != "") {
                    TextView overviewLabel = (TextView) getView().findViewById(R.id.overview_label);
                    overviewLabel.setText(getString(R.string.overview_label));
                    overviewLabel.setVisibility(View.VISIBLE);
                }

                TextView txtReleaseDate = (TextView) getView().findViewById(R.id.txt_release_date);
                txtReleaseDate.setText(data.getString(COL_RELEASE_DATE));

                TextView txtVoteAverage = (TextView) getView().findViewById(R.id.txt_vote_average);
                txtVoteAverage.setText(data.getString(COL_VOTE_AVERAGE) + " / 10");

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


                //sv.getMeasuredHeight();


//                ListAdapter listAdapter = listViewReview.getAdapter();

//                ViewGroup.LayoutParams params = listViewReview.getLayoutParams();
//                params.height = mReviewAdapter.getHeight();
//
//                listViewReview.setLayoutParams(params);
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


    //   private static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null)
//            return;
//
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
//        int totalHeight = 0;
//        View view = null;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            view = listAdapter.getView(i, view, listView);
//            if (i == 0)
//                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewPager.LayoutParams.WRAP_CONTENT));
//
//            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            int paddingBottom = view.getPaddingBottom();
//            int paddingTop = view.getPaddingTop();
//            totalHeight += view.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//
//        listView.setLayoutParams(params);
//
//    }
//
//
//    private static void setListViewHeightBasedOnChildrenReview(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null)
//            return;
//
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
//        int totalHeight = 0;
//        int totalHeightViews = 0;
//        View view = null;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            view = listAdapter.getView(i, view, listView);
//            if (i == 0)
//                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewPager.LayoutParams.WRAP_CONTENT));
//            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//
//
//            totalHeight += view.getMeasuredHeightAndState();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//
//        //params.height = totalHeight+  (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//
//
//        listView.setLayoutParams(params);
//
//    }
//
//
//    public static void getTotalHeightofListView(ListView listView) {
//
//        ListAdapter mAdapter = listView.getAdapter();
//
//        int totalHeight = 0;
//
//        for (int i = 0; i < mAdapter.getCount(); i++) {
//            View mView = mAdapter.getView(i, null, listView);
//
//            mView.measure(
//                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//
//                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//
//            totalHeight += mView.getMeasuredHeight();
//            Log.w("HEIGHT" + i, String.valueOf(totalHeight));
//
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight
//                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//
//    }

}
