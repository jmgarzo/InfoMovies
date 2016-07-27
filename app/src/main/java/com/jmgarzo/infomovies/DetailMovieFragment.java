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
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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


    private static final int DETAIL_LOADER = 0;
    private static final int DETAIL_TRAILER_LOADER = 1;
    private static final int DETAIL_REVIEW_LOADER = 2;

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


    private static final String[] VIDEO_COLUNMS =
            {
                    MoviesContract.VideoEntry.TABLE_NAME + "." + MoviesContract.VideoEntry._ID,
                    MoviesContract.VideoEntry.MOVIE_KEY,
                    MoviesContract.VideoEntry.ID,
                    MoviesContract.VideoEntry.ISO_639_1,
                    MoviesContract.VideoEntry.ISO_3166_1,
                    MoviesContract.VideoEntry.KEY,
                    MoviesContract.VideoEntry.NAME,
                    MoviesContract.VideoEntry.SITE,
                    MoviesContract.VideoEntry.SIZE,
                    MoviesContract.VideoEntry.TYPE
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
                    MoviesContract.ReviewEntry.TABLE_NAME + "." + MoviesContract.ReviewEntry._ID,
                    MoviesContract.ReviewEntry.MOVIE_KEY,
                    MoviesContract.ReviewEntry.ID,
                    MoviesContract.ReviewEntry.AUTHOR,
                    MoviesContract.ReviewEntry.CONTENT,
                    MoviesContract.ReviewEntry.URL,
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle argument = getArguments();
        if (argument != null) {
            mUri = argument.getParcelable(DetailMovieFragment.DETAIL_URI);
            mIdMovie = argument.getString(MoviesContract.MoviesEntry._ID);
        }

        View rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false);
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


    private static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewPager.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        getLoaderManager().initLoader(DETAIL_TRAILER_LOADER, null, this);
        getLoaderManager().initLoader(DETAIL_REVIEW_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    void onLocationChanged( String newLocation ) {
        // replace the uri, since the sort by  has changed
        Uri uri = mUri;
        if (null != uri) {
//            Uri updatedUri = MoviesContract.MoviesEntry.b(newLocation, date);
//            mUri = updatedUri;
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
                        MoviesContract.VideoEntry.buildVideoWithMovieId(mIdMovie),
                        VIDEO_COLUNMS,
                        null,
                        null,
                        null
                );

            }

            case DETAIL_REVIEW_LOADER: {
                return new CursorLoader(
                        getActivity(),
                        MoviesContract.ReviewEntry.buildReviewWithMovieId(mIdMovie),
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


                getActivity().setTitle(data.getString(COL_TITLE));

                ImageView imageViewPoster = (ImageView) getView().findViewById(R.id.img_poster);
                Picasso.with(getContext()).load(data.getString(COL_POSTER_PATH)).into(imageViewPoster);

                TextView overviewLabel = (TextView) getView().findViewById(R.id.overview_label);
                overviewLabel.setText(getString(R.string.overview_label));


                TextView txtOverview = (TextView) getView().findViewById(R.id.overview_text);
                txtOverview.setText(data.getString(COL_OVERVIEW));

                TextView txtReleaseDate = (TextView) getView().findViewById(R.id.txt_release_date);
                txtReleaseDate.setText(data.getString(COL_RELEASE_DATE));

                TextView txtVoteAverage = (TextView) getView().findViewById(R.id.txt_vote_average);
                txtVoteAverage.setText(data.getString(COL_VOTE_AVERAGE) + "/ 10");

                break;
            }
            case DETAIL_TRAILER_LOADER: {

                mTrailerAdapter.swapCursor(data);
                setListViewHeightBasedOnChildren(listViewTrailers);
                break;
            }

            case DETAIL_REVIEW_LOADER:{
                mReviewAdapter.swapCursor(data);
                setListViewHeightBasedOnChildren(listViewReview);
                break;
            }
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        switch (loader.getId()) {
            case DETAIL_TRAILER_LOADER: {
                mTrailerAdapter.swapCursor(null);
                break;
            }
            case DETAIL_REVIEW_LOADER: {
                mReviewAdapter.swapCursor(null);
                break;
            }
        }
    }


}
