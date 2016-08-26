package com.jmgarzo.infomovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jmgarzo.infomovies.data.MoviesContract;

import java.util.Vector;

public class DetailMovie extends AppCompatActivity {
    private String idMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerview);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.movie_detail_container, new DetailMovieFragment())
//                    .commit();
//        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();
            idMovie = getIntent().getStringExtra(MoviesContract.MoviesEntry._ID);
            arguments.putParcelable(DetailMovieFragment.DETAIL_URI, getIntent().getData());
            arguments.putString(MoviesContract.MoviesEntry._ID, idMovie);
            DetailMovieFragment fragment = new DetailMovieFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Cursor cursorMovie = getContentResolver().query(MoviesContract.MoviesEntry.buildMoviesWithIdUri(Long.parseLong(idMovie)), null, null, null, null);
        if (cursorMovie.moveToFirst()) {
            int index = cursorMovie.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_WEB_ID);
            String webMovieId = cursorMovie.getString(index);


            Cursor cursorFavorite = getContentResolver().query(MoviesContract.FavoriteMovieEntry.buildFavoriteWithWebId(webMovieId), null, null, null, null);
            if (cursorFavorite.moveToFirst()) {
                fab.setImageResource(R.drawable.ic_favorite_white_24dp);
            } else {
                fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            }
        }


        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //                        .setAction("Action", null).show();

                    copyToFavorites(fab);

                }
            });
        }
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ContentValues getFavoriteMovieValues() {
        Cursor cursor = getContentResolver().query(MoviesContract.MoviesEntry.buildMoviesWithIdUri(Long.parseLong(idMovie)), DetailMovieFragment.MOVIE_COLUMNS, null, null, null);

        ContentValues cv = null;
        if (cursor.moveToFirst()) {
            cv = new ContentValues();
            cv.put(MoviesContract.FavoriteMovieEntry.POSTER_PATH, cursor.getString(DetailMovieFragment.COL_POSTER_PATH));
            cv.put(MoviesContract.FavoriteMovieEntry.ADULT, cursor.getString(DetailMovieFragment.COL_ADULT));
            cv.put(MoviesContract.FavoriteMovieEntry.OVERVIEW, cursor.getString(DetailMovieFragment.COL_OVERVIEW));
            cv.put(MoviesContract.FavoriteMovieEntry.RELEASE_DATE, cursor.getString(DetailMovieFragment.COL_RELEASE_DATE));
            cv.put(MoviesContract.FavoriteMovieEntry.MOVIE_WEB_ID, cursor.getString(DetailMovieFragment.COL_MOVIE_WEB_ID));
            cv.put(MoviesContract.FavoriteMovieEntry.ORIGINAL_TITLE, cursor.getString(DetailMovieFragment.COL_ORIGINAL_TITLE));
            cv.put(MoviesContract.FavoriteMovieEntry.ORIGINAL_LANGUAGE, cursor.getString(DetailMovieFragment.COL_ORIGINAL_LANGUAGE));

            cv.put(MoviesContract.FavoriteMovieEntry.TITLE, cursor.getString(DetailMovieFragment.COL_TITLE));

            cv.put(MoviesContract.FavoriteMovieEntry.BACKDROP_PATH, cursor.getString(DetailMovieFragment.COL_BACKDROP_PATH));

            cv.put(MoviesContract.FavoriteMovieEntry.POPULARITY, Double.parseDouble(cursor.getString(DetailMovieFragment.COL_POPULARITY)));

            cv.put(MoviesContract.FavoriteMovieEntry.VOTE_COUNT, Integer.parseInt(cursor.getString(DetailMovieFragment.COL_VOTE_COUNT)));

            cv.put(MoviesContract.FavoriteMovieEntry.VIDEO, cursor.getString(DetailMovieFragment.COL_VIDEO));
            cv.put(MoviesContract.FavoriteMovieEntry.VOTE_AVERAGE, Double.parseDouble(cursor.getString(DetailMovieFragment.COL_VOTE_AVERAGE)));
            cv.put(MoviesContract.FavoriteMovieEntry.RELEASE_DATE, cursor.getString(DetailMovieFragment.COL_RELEASE_DATE));
            cv.put(MoviesContract.FavoriteMovieEntry.ADD_DATE, Utility.formatDate(System.currentTimeMillis()));
            cursor.close();
        }
        return cv;
    }

    void copyToFavorites(FloatingActionButton fab) {
        Cursor cursorMovie = getContentResolver().query(MoviesContract.MoviesEntry.buildMoviesWithIdUri(Long.parseLong(idMovie)), null, null, null, null);
        if (cursorMovie.moveToFirst()) {
            int index = cursorMovie.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_WEB_ID);
            String webMovieId = cursorMovie.getString(index);


            Cursor cursorFavorite = getContentResolver().query(MoviesContract.FavoriteMovieEntry.buildFavoriteWithWebId(webMovieId), null, null, null, null);
            if (!cursorFavorite.moveToFirst()) {
                ContentValues cv = getFavoriteMovieValues();
                if (cv != null) {
                    getContentResolver().insert(MoviesContract.FavoriteMovieEntry.CONTENT_URI, cv);
                    fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                    addVideo();
                    addReview();
                }
            } else {
                index = cursorFavorite.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_WEB_ID);
                String webMovieIdFavorite = cursorFavorite.getString(index);
                getContentResolver().delete(MoviesContract.FavoriteMovieEntry.CONTENT_URI, MoviesContract.FavoriteMovieEntry.MOVIE_WEB_ID + " = ? ", new String[]{webMovieIdFavorite});
                fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            }
        }

    }

    void addVideo() {
        Cursor cursor = getContentResolver().query(MoviesContract.VideoEntry.buildVideoWithMovieId(idMovie),
                DetailMovieFragment.VIDEO_COLUMNS,
                null,
                null,
                null);

        Vector<ContentValues> vVContentValues = getFavoriteVideoValues(cursor);
        if (vVContentValues != null) {
            if (vVContentValues.size() > 0) {
                ContentValues[] cvArray = new ContentValues[vVContentValues.size()];
                vVContentValues.toArray(cvArray);

                getContentResolver().bulkInsert(MoviesContract.FavoriteVideoEntry.CONTENT_URI, cvArray);
            }

        }
    }


    private Vector<ContentValues> getFavoriteVideoValues(Cursor cursor) {
        Vector vectorVideoValues = null;
        ContentValues cv = null;
        if (cursor.moveToFirst()) {
            vectorVideoValues = new Vector<ContentValues>();
            do {
                cv = new ContentValues();

                cv.put(MoviesContract.FavoriteVideoEntry.MOVIE_KEY, cursor.getString(DetailMovieFragment.COL_VIDEO_MOVIE_KEY));
                cv.put(MoviesContract.FavoriteVideoEntry.ID, cursor.getString(DetailMovieFragment.COL_VIDEO_WEB_ID));
                cv.put(MoviesContract.FavoriteVideoEntry.ISO_639_1, cursor.getString(DetailMovieFragment.COL_VIDEO_ISO_639_1));
                cv.put(MoviesContract.FavoriteVideoEntry.ISO_3166_1, cursor.getString(DetailMovieFragment.COL_VIDEO_ISO_3166_1));
                cv.put(MoviesContract.FavoriteVideoEntry.KEY, cursor.getString(DetailMovieFragment.COL_VIDEO_KEY));
                cv.put(MoviesContract.FavoriteVideoEntry.NAME, cursor.getString(DetailMovieFragment.COL_VIDEO_NAME));
                cv.put(MoviesContract.FavoriteVideoEntry.SITE, cursor.getString(DetailMovieFragment.COL_VIDEO_SITE));
                cv.put(MoviesContract.FavoriteVideoEntry.SIZE, cursor.getString(DetailMovieFragment.COL_VIDEO_SIZE));
                cv.put(MoviesContract.FavoriteVideoEntry.TYPE, cursor.getString(DetailMovieFragment.COL_VIDEO_TYPE));

                vectorVideoValues.add(cv);


            } while (cursor.moveToNext());
        }
        return vectorVideoValues;
    }

    void addReview() {
        Cursor cursor = getContentResolver().query(MoviesContract.ReviewEntry.buildReviewWithMovieId(idMovie),
                DetailMovieFragment.REVIEW_COLUMNS,
                null,
                null,
                null);

        Vector<ContentValues> vVContentValues = getFavoriteReviewValues(cursor);
        if (vVContentValues != null) {
            if (vVContentValues.size() > 0) {
                ContentValues[] cvArray = new ContentValues[vVContentValues.size()];
                vVContentValues.toArray(cvArray);

                getContentResolver().bulkInsert(MoviesContract.FavoriteReviewEntry.CONTENT_URI, cvArray);
            }

        }
    }

    private Vector<ContentValues> getFavoriteReviewValues(Cursor cursor) {
        Vector vectorReviewValues = null;
        ContentValues cv = null;
        if (cursor.moveToFirst()) {
            vectorReviewValues = new Vector<ContentValues>();
            do {
                cv = new ContentValues();

                cv.put(MoviesContract.FavoriteReviewEntry.MOVIE_KEY, cursor.getString(DetailMovieFragment.COL_REVIEW_MOVIE_KEY));
                cv.put(MoviesContract.FavoriteReviewEntry.ID, cursor.getString(DetailMovieFragment.COL_REVIEW_WEB_ID_KEY));
                cv.put(MoviesContract.FavoriteReviewEntry.AUTHOR, cursor.getString(DetailMovieFragment.COL_REVIEW_AUTHOR));
                cv.put(MoviesContract.FavoriteReviewEntry.CONTENT, cursor.getString(DetailMovieFragment.COL_REVIEW_CONTENT));
                cv.put(MoviesContract.FavoriteReviewEntry.URL, cursor.getString(DetailMovieFragment.COL_REVIEW_URL));

                vectorReviewValues.add(cv);

            } while (cursor.moveToNext());
        }
        return vectorReviewValues;
    }


}
