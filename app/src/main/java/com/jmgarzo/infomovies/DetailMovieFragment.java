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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private static final int DETAIL_LOADER = 0;
    private static final int DETAIL_TRAILER_LOADER = 1;

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


    private ListView listViewTrailers;
    private TrailerAdapter mTrailerAdapter;
    private String idMovie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        mTrailerAdapter = new TrailerAdapter(getActivity(), null, 0);

        listViewTrailers = (ListView) rootView.findViewById(R.id.listview_trailers);
        listViewTrailers.setAdapter(mTrailerAdapter);




//
//        Intent intent = getActivity().getIntent();
//        String movieID = intent.getStringExtra(MoviesContract.MoviesEntry._ID);
//        Cursor cursorMovies = getActivity().getContentResolver().query(
//                MoviesContract.VideoEntry.buildVideoWithMovieId(movieID),
//                null,
//                null,
//                null,
//                null);
//
//        if (cursorMovies.moveToFirst()) {
//            int i = 0;
//            do {
//                i++;
//                String id = cursorMovies.getString(COL_VIDEO_ID);
//                String name = cursorMovies.getString(COL_VIDEO_NAME);
//                Log.v(LOG_TAG, id + "-" + name + i);
//            } while (cursorMovies.moveToNext());
//        }

//        Intent intent = getActivity().getIntent();
//        //Movie movie = intent.getExtras().getParcelable("movie");
//        String webMovieId = intent.getExtras().getString(getString(R.string.mdb_movie_web_id_key));
//
//        Uri MovieWithWebIdUri = MoviesContract.MoviesEntry.buildMovieWithWebId(webMovieId);
//        Cursor cursorMovies = getActivity().getContentResolver().query(
//                MovieWithWebIdUri ,
//                null,
//                MoviesContract.MoviesEntry.MOVIE_WEB_ID + " = ?",
//                new String[]{webMovieId},
//                null);
//
//        int _id=0;
//        String title="";
//        String posterPath="";
//        String overview="";
//        String releaseDate="";
//        String voteAverage="";
//        if (cursorMovies.moveToFirst()) {
//
//            int _idMovie = cursorMovies.getColumnIndex(MoviesContract.MoviesEntry._ID);
//            _id = cursorMovies.getInt(_idMovie);
//
//            int titleIndex = cursorMovies.getColumnIndex(MoviesContract.MoviesEntry.TITLE);
//            title = cursorMovies.getString(titleIndex);
//
//            int posterPathIndex = cursorMovies.getColumnIndex(MoviesContract.MoviesEntry.POSTER_PATH);
//            posterPath = cursorMovies.getString(posterPathIndex);
//
//            int overviewIndex = cursorMovies.getColumnIndex(MoviesContract.MoviesEntry.OVERVIEW);
//            overview = cursorMovies.getString(overviewIndex);
//
//            int releaseDateIndex = cursorMovies.getColumnIndex(MoviesContract.MoviesEntry.RELEASE_DATE);
//            releaseDate = cursorMovies.getString(releaseDateIndex);
//
//            int voteAverageIndex = cursorMovies.getColumnIndex(MoviesContract.MoviesEntry.VOTE_AVERAGE);
//            voteAverage = cursorMovies.getString(voteAverageIndex);
//
//        }
//
//
//        String key="";
//
//        Uri VideosWithMovieIdUri = MoviesContract.VideoEntry.buildVideoWithMovieId(Integer.valueOf(_id).toString());
//        Cursor cursorVideos = getActivity().getContentResolver().query(
//                VideosWithMovieIdUri ,
//                null,
//                MoviesContract.VideoEntry._ID + " = ?",
//                new String[]{Integer.valueOf(_id).toString()},
//                null);
//
//        if(cursorVideos.moveToFirst()){
//            int keyIndex = cursorVideos.getColumnIndex(MoviesContract.VideoEntry.KEY);
//            key = cursorVideos.getString(keyIndex);
//        }
//
//
//        getActivity().setTitle(title);
//
//        ImageView imageViewPoster = (ImageView) rootView.findViewById(R.id.img_poster);
//        Picasso.with(getContext()).load(posterPath).into(imageViewPoster);
//
//        TextView txtOverview = (TextView) rootView.findViewById(R.id.txt_overview);
//        txtOverview.setText(overview);
//
//        TextView txtReleaseDate = (TextView) rootView.findViewById(R.id.txt_release_date);
//        txtReleaseDate.setText(releaseDate);
//
//        TextView txtVoteAverage = (TextView) rootView.findViewById(R.id.txt_vote_average);
//        txtVoteAverage.setText(voteAverage + "/ 10");


            return rootView;
        }

        @Override
        public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public void onActivityCreated (@Nullable Bundle savedInstanceState){
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
            getLoaderManager().initLoader(DETAIL_TRAILER_LOADER, null, this);
            super.onActivityCreated(savedInstanceState);
        }


        @Override
        public Loader<Cursor> onCreateLoader ( int id, Bundle args){
            Log.v(LOG_TAG, "In onCreateLoader");
            Intent intent = getActivity().getIntent();
            if (intent == null) {
                return null;
            }

            CursorLoader cursorLoader = null;
            switch (id) {
                case DETAIL_LOADER: {
                    Uri uri = intent.getData();

                    return new CursorLoader(
                            getActivity(),
                            intent.getData(),
                            MOVIE_COLUMNS,
                            null,
                            null,
                            null
                    );


                }


                case DETAIL_TRAILER_LOADER: {
                    String idMovie = intent.getStringExtra(MoviesContract.MoviesEntry._ID);
                    return new CursorLoader(
                            getActivity(),
                            MoviesContract.VideoEntry.buildVideoWithMovieId(idMovie),
                            VIDEO_COLUNMS,
                            null,
                            null,
                            null
                    );

                }

            }
            return null;

        }

        @Override
        public void onLoadFinished (Loader < Cursor > loader, Cursor data){
            Log.v(LOG_TAG, "In onLoadFinished");
            if (!data.moveToFirst()) {
                return;
            }

            switch (loader.getId()) {
                case DETAIL_LOADER: {


                    getActivity().setTitle(data.getString(COL_TITLE));

                    ImageView imageViewPoster = (ImageView) getView().findViewById(R.id.img_poster);
                    Picasso.with(getContext()).load(data.getString(COL_POSTER_PATH)).into(imageViewPoster);

                    TextView txtOverview = (TextView) getView().findViewById(R.id.txt_overview);
                    txtOverview.setText(data.getString(COL_OVERVIEW));

                    TextView txtReleaseDate = (TextView) getView().findViewById(R.id.txt_release_date);
                    txtReleaseDate.setText(data.getString(COL_RELEASE_DATE));

                    TextView txtVoteAverage = (TextView) getView().findViewById(R.id.txt_vote_average);
                    txtVoteAverage.setText(data.getString(COL_VOTE_AVERAGE) + "/ 10");

                    break;
                }
                case DETAIL_TRAILER_LOADER: {
                    mTrailerAdapter.swapCursor(data);
                    break;
                }
            }


        }

        @Override
        public void onLoaderReset (Loader < Cursor > loader) {

            switch (loader.getId()) {
                case DETAIL_TRAILER_LOADER: {
                    mTrailerAdapter.swapCursor(null);

                }
            }
        }


    }
