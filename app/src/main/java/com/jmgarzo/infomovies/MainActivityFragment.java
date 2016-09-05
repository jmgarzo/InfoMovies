package com.jmgarzo.infomovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.SharedPreferencesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jmgarzo.infomovies.data.MoviesContract;
import com.jmgarzo.infomovies.sync.MainInfoMoviesSyncAdapter;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int MOVIE_LOADER = 0;
    //    private String TOP_RATE_PARAM = "top_rate";
//    private String MOST_POPULAR = "most_popular";
    private static final String ARG_SECTION_NUMBER = "section_number";


    private MovieAdapter mMovieAdapter;
    private GridView mGridView;
    private int selectionTab;


//    private static final String[] MOVIE_COLUMNS = {
//            MoviesContract.MoviesEntry.TABLE_NAME + "." + MoviesContract.MoviesEntry._ID,
//            MoviesContract.MoviesEntry.POSTER_PATH,
//            MoviesContract.MoviesEntry.ADULT,
//            MoviesContract.MoviesEntry.OVERVIEW,
//            MoviesContract.MoviesEntry.RELEASE_DATE,
//            MoviesContract.MoviesEntry.MOVIE_WEB_ID,
//            MoviesContract.MoviesEntry.ORIGINAL_TITLE,
//            MoviesContract.MoviesEntry.ORIGINAL_LANGUAGE,
//            MoviesContract.MoviesEntry.TITLE,
//            MoviesContract.MoviesEntry.BACKDROP_PATH,
//            MoviesContract.MoviesEntry.POPULARITY,
//            MoviesContract.MoviesEntry.VOTE_COUNT,
//            MoviesContract.MoviesEntry.VIDEO,
//            MoviesContract.MoviesEntry.VOTE_AVERAGE,
//            MoviesContract.MoviesEntry.MOST_POPULAR,
//            MoviesContract.MoviesEntry.TOP_RATE
//
//    };
//
//    static final int COL_MOVIE_ID = 0;
//    static final int COL_POSTER_PATH = 1;
//    static final int COL_ADULT = 2;
//    static final int COL_OVERVIEW = 3;
//    static final int COL_RELEASE_DATE = 4;
//    static final int COL_MOVIE_WEB_ID = 5;
//    static final int COL_ORIGINAL_TITLE = 6;
//    static final int COL_ORIGINAL_LANGUAGE = 7;
//    static final int COL_TITLE = 8;
//    static final int COL_BACKDROP_PATH = 9;
//    static final int COL_POPULARITY = 10;
//    static final int COL_VOTE_COUNT = 11;
//    static final int COL_VIDEO = 12;
//    static final int COL_VOTE_AVERAGE = 13;
//    static final int COL_MOST_POPULAR = 14;
//    static final int COL_TOP_RATE = 15;


    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri, String movie_id);
    }


    public MainActivityFragment() {
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateMovies();
        selectionTab = 1;
        //setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        selectionTab = getArguments().getInt(ARG_SECTION_NUMBER);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        switch (selectionTab) {
            case 1: {
                pref.edit().putString(getActivity().getString(R.string.pref_sort_by_key), getActivity().getString(R.string.pref_sort_by_most_popular)).apply();
                getLoaderManager().restartLoader(MOVIE_LOADER, null, this);

                break;
            }
            case 2: {
                pref.edit().putString(getActivity().getString(R.string.pref_sort_by_key), getActivity().getString(R.string.pref_sort_by_top_rate)).apply();
                getLoaderManager().restartLoader(MOVIE_LOADER, null, this);


                break;
            }
            case 3: {
                pref.edit().putString(getActivity().getString(R.string.pref_sort_by_key), getActivity().getString(R.string.pref_sort_by_favorite)).apply();
                getLoaderManager().restartLoader(MOVIE_LOADER, null, this);


                break;
            }
        }

        mMovieAdapter = new MovieAdapter(getActivity(), null, 0);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

//        View view = inflater.inflate(R.layout.list_item_movie,container,false);
//        mImageView = (ImageView) view.findViewById(R.id.list_item_image);


        mGridView = (GridView) rootView.findViewById(R.id.gridview);

        mGridView.setAdapter(mMovieAdapter);


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                 Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                                                 if (cursor != null) {
//                                                    Intent intent = new Intent(getActivity(), DetailMovie.class);
////                                                    intent.putExtra(getString(R.string.mdb_movie_web_id_key), cursor.getString(COL_MOVIE_WEB_ID));
//                                                    intent.setData(MoviesContract.MoviesEntry.buildMovieWithWebId(cursor.getString(COL_MOVIE_WEB_ID)));
//                                                    intent.putExtra(MoviesContract.MoviesEntry._ID,cursor.getString(COL_MOVIE_ID));
//                                                    startActivity(intent);

                                                     int height = mGridView.getMeasuredHeight();
                                                     mGridView.smoothScrollToPositionFromTop(position, ((height / 2)));

                                                     if (Utility.isPreferenceSortByFavorite(getContext())) {
                                                         ((Callback) getActivity())
                                                                 .onItemSelected(MoviesContract.FavoriteMovieEntry
                                                                         .buildFavoriteWithWebId(cursor.getString(DetailMovieFragment.COL_FAVORITE_MOVIE_WEB_ID)), cursor.getString(DetailMovieFragment.COL_FAVORITE_MOVIE_ID));
                                                     } else {
                                                         ((Callback) getActivity())
                                                                 .onItemSelected(MoviesContract.MoviesEntry
                                                                         .buildMovieWithWebId(cursor.getString(DetailMovieFragment.COL_MOVIE_WEB_ID)), cursor.getString(DetailMovieFragment.COL_MOVIE_ID));
                                                     }

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

    }


//    private void updateSortBy() {
////        updateMovies();
//        g
//    }

    void onSortChanged() {
        //updateSortBy();
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private void updateMovies() {

        if (!Utility.isPreferenceSortByFavorite(getActivity())) ;
        {
            MainInfoMoviesSyncAdapter.syncImmediately(getActivity());
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (Utility.isPreferenceSortByFavorite(getActivity())) {
            return new CursorLoader(getActivity(),
                    MoviesContract.FavoriteMovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

        } else if (Utility.isPreferenceSortByMostPopular(getContext())) {
            return new CursorLoader(getActivity(),
                    MoviesContract.MoviesEntry.buildMovieWithMostPopular("1"),
                    DetailMovieFragment.MOVIE_COLUMNS,
                    null,
                    null,
                    null);

        } else if (Utility.isPreferenceSortByTopRate(getContext())) {
            return new CursorLoader(getActivity(),
                    MoviesContract.MoviesEntry.buildMovieWithTopRate("1"),
                    DetailMovieFragment.MOVIE_COLUMNS,
                    null,
                    null,
                    null);
        }
//        else {
//
//            return new CursorLoader(getActivity(),
//                    MoviesContract.MoviesEntry.CONTENT_URI,
//                    DetailMovieFragment.MOVIE_COLUMNS,
//                    null,
//                    null,
//                    null);
//        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMovieAdapter.swapCursor(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieAdapter.swapCursor(null);
    }


    @Override
    public void onResume() {
        super.onResume();
        //getLoaderManager().restartLoader(MOVIE_LOADER,null, this);
    }


    public static MainActivityFragment newInstance(int sectionNumber) {


        MainActivityFragment fragment = new MainActivityFragment();

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;

    }
}
