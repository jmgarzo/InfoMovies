package com.jmgarzo.infomovies;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
    private String TOP_RATE_PARAM = "top_rate";
    private String MOST_POPULAR = "most_popular";


    private MovieAdapter mMovieAdapter;
    private GridView mGridView;

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

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri,String movie_id );
    }


    public MainActivityFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMovieAdapter = new MovieAdapter(getActivity(),null,0);
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

                                                    ((Callback) getActivity())
                                                            .onItemSelected(MoviesContract.MoviesEntry.buildMovieWithWebId(cursor.getString(COL_MOVIE_WEB_ID)),cursor.getString(COL_MOVIE_ID));

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
        updateMovies();
    }



    private void updateSortBy() {
        updateMovies();
    }

    void onSortChanged() {
        updateSortBy();
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private void updateMovies() {

        if(!Utility.isPreferenceSortByFavorite(getActivity())); {
            MainInfoMoviesSyncAdapter.syncImmediately(getActivity());
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if(Utility.isPreferenceSortByFavorite(getActivity())){
            return new CursorLoader(getActivity(),
                    MoviesContract.FavoriteMovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

        }else {

            return new CursorLoader(getActivity(),
                    MoviesContract.MoviesEntry.CONTENT_URI,
                    DetailMovieFragment.MOVIE_COLUMNS,
                    null,
                    null,
                    null);
        }
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
}
