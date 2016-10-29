package com.jmgarzo.infomovies;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIE_LOADER = 0;

    private MovieAdapter mMovieAdapter;
    private GridView mGridView;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMovieAdapter = new MovieAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        mGridView = (GridView) rootView.findViewById(R.id.favorite_gridview);
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
                                                     Utility.setPrererenceShortByFavorite(getActivity());

                                                     ((MainActivityFragment.Callback) getActivity())
                                                             .onItemSelected(MoviesContract.FavoriteMovieEntry
                                                                     .buildFavoriteWithWebId(cursor.getString(DetailMovieFragment.COL_FAVORITE_MOVIE_WEB_ID)), cursor.getString(DetailMovieFragment.COL_FAVORITE_MOVIE_ID));


                                                 }
                                             }
                                         }

        );

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                MoviesContract.FavoriteMovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
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
        Utility.setPrererenceShortByFavorite(getActivity());
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri, String movie_id );
    }
}
