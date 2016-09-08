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
    private boolean selectedToDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
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
            arguments.putParcelable(DetailMovieFragment.DETAIL_URI, getIntent().getData());
            DetailMovieFragment fragment = new DetailMovieFragment();

            if (Utility.isPreferenceSortByFavorite(this)) {
                idMovie = getIntent().getStringExtra(MoviesContract.FavoriteMovieEntry._ID);
                arguments.putString(MoviesContract.FavoriteMovieEntry._ID, idMovie);

            } else {
                idMovie = getIntent().getStringExtra(MoviesContract.MoviesEntry._ID);
                arguments.putString(MoviesContract.MoviesEntry._ID, idMovie);

            }

            toolbar.setTitle(getTitle(idMovie));
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (isInFavorites(idMovie)) {
            fab.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }


        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //                        .setAction("Action", null).show();

                    manageFab(fab);

                }
            });
        }
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean isInFavorites(String idMovie) {
        boolean result;
        if (Utility.isPreferenceSortByFavorite(this)) {
            Cursor favoritesCursor = getContentResolver().query(
                    MoviesContract.FavoriteMovieEntry.buildFavoriteUri(Long.parseLong(idMovie)),
                    null,
                    null,
                    null,
                    null
            );
            if (favoritesCursor.moveToFirst()) {
                return true;
            }
        } else {
            Cursor cursorMovie = getContentResolver().query(MoviesContract.MoviesEntry.buildMoviesWithIdUri(Long.parseLong(idMovie)), null, null, null, null);
            if (cursorMovie.moveToFirst()) {
                int index = cursorMovie.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_WEB_ID);
                String webMovieId = cursorMovie.getString(index);

                Cursor cursorFavorite = getContentResolver().query(MoviesContract.FavoriteMovieEntry.buildFavoriteWithWebId(webMovieId), null, null, null, null);
                if (cursorFavorite.moveToFirst()) {
                    return true;
                }
            }
        }
        return false;
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

    String getTitle(String idMovie) {
        String result = "";
        if (Utility.isPreferenceSortByFavorite(this)) {
            Cursor cursor = getContentResolver().query(MoviesContract.FavoriteMovieEntry.buildFavoriteUri(Long.parseLong(idMovie)),
                    DetailMovieFragment.FAVORITE_MOVIE_COLUMNS,
                    null,
                    null,
                    null);
            if (cursor.moveToFirst()) {
                result = cursor.getString(DetailMovieFragment.COL_FAVORITE_TITLE);
            }

        } else {
            Cursor cursor = getContentResolver().query(MoviesContract.MoviesEntry.buildMoviesWithIdUri(Long.parseLong(idMovie)),
                    DetailMovieFragment.MOVIE_COLUMNS,
                    null,
                    null,
                    null);
            if (cursor.moveToFirst()) {
                result = cursor.getString(DetailMovieFragment.COL_TITLE);

            }
        }
        return result;

    }


        ContentValues getFavoriteMovieValues () {
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

    void manageFab(FloatingActionButton fab) {
        if (Utility.isPreferenceSortByFavorite(this)) {
            //delelte
            if (selectedToDelete) {
                selectedToDelete = false;
                fab.setImageResource(R.drawable.ic_favorite_white_24dp);

            } else {
                if (isInFavorites(idMovie)) {
                    selectedToDelete = true;
                    fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);

                }

            }

        } else {


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
                        addVideo(webMovieId);
                        addReview(webMovieId);
                    }
                } else {
                    index = cursorFavorite.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_WEB_ID);
                    String webMovieIdFavorite = cursorFavorite.getString(index);
                    getContentResolver().delete(MoviesContract.FavoriteMovieEntry.CONTENT_URI, MoviesContract.FavoriteMovieEntry.MOVIE_WEB_ID + " = ? ", new String[]{webMovieIdFavorite});
                    fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                }
            }
        }

    }

    void addVideo(String webMovieid) {


        String favoriteMovieId = getFavoriteMovieId(webMovieid);


        Cursor cursor = getContentResolver().query(MoviesContract.VideoEntry.buildVideoWithMovieId(idMovie),
                DetailMovieFragment.VIDEO_COLUMNS,
                null,
                null,
                null);

        Vector<ContentValues> vVContentValues = getFavoriteVideoValues(cursor, favoriteMovieId);
        if (vVContentValues != null) {
            if (vVContentValues.size() > 0) {
                ContentValues[] cvArray = new ContentValues[vVContentValues.size()];
                vVContentValues.toArray(cvArray);

                getContentResolver().bulkInsert(MoviesContract.FavoriteVideoEntry.CONTENT_URI, cvArray);
            }

        }
    }


    private Vector<ContentValues> getFavoriteVideoValues(Cursor cursor, String favoriteMovieId) {
        Vector vectorVideoValues = null;
        ContentValues cv = null;
        if (cursor.moveToFirst()) {
            vectorVideoValues = new Vector<ContentValues>();
            do {
                cv = new ContentValues();

                cv.put(MoviesContract.FavoriteVideoEntry.MOVIE_KEY, favoriteMovieId);
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

    void addReview(String webMovieid) {
        String favoriteMovieId = getFavoriteMovieId(webMovieid);

        Cursor cursor = getContentResolver().query(MoviesContract.ReviewEntry.buildReviewWithMovieId(idMovie),
                DetailMovieFragment.REVIEW_COLUMNS,
                null,
                null,
                null);

        Vector<ContentValues> vVContentValues = getFavoriteReviewValues(cursor, favoriteMovieId);
        if (vVContentValues != null) {
            if (vVContentValues.size() > 0) {
                ContentValues[] cvArray = new ContentValues[vVContentValues.size()];
                vVContentValues.toArray(cvArray);

                getContentResolver().bulkInsert(MoviesContract.FavoriteReviewEntry.CONTENT_URI, cvArray);
            }

        }
    }

    private Vector<ContentValues> getFavoriteReviewValues(Cursor cursor, String favoriteMovieId) {
        Vector vectorReviewValues = null;
        ContentValues cv = null;
        if (cursor.moveToFirst()) {
            vectorReviewValues = new Vector<ContentValues>();
            do {
                cv = new ContentValues();

                cv.put(MoviesContract.FavoriteReviewEntry.MOVIE_KEY, favoriteMovieId);
                cv.put(MoviesContract.FavoriteReviewEntry.ID, cursor.getString(DetailMovieFragment.COL_REVIEW_WEB_ID_KEY));
                cv.put(MoviesContract.FavoriteReviewEntry.AUTHOR, cursor.getString(DetailMovieFragment.COL_REVIEW_AUTHOR));
                cv.put(MoviesContract.FavoriteReviewEntry.CONTENT, cursor.getString(DetailMovieFragment.COL_REVIEW_CONTENT));
                cv.put(MoviesContract.FavoriteReviewEntry.URL, cursor.getString(DetailMovieFragment.COL_REVIEW_URL));

                vectorReviewValues.add(cv);

            } while (cursor.moveToNext());
        }
        return vectorReviewValues;
    }

    private String getFavoriteMovieId(String webMovieId) {
        Cursor favoriteCursor = getContentResolver().query(MoviesContract.FavoriteMovieEntry.buildFavoriteWithWebId(webMovieId),
                DetailMovieFragment.FAVORITE_MOVIE_COLUMNS,
                null,
                null,
                null);
        String favoriteMovieId = "";
        if (favoriteCursor.moveToFirst()) {
            favoriteMovieId = favoriteCursor.getString(DetailMovieFragment.COL_FAVORITE_MOVIE_ID);
        }
        return favoriteMovieId;
    }

    @Override
    protected void onStop() {
        if (selectedToDelete) {
            getContentResolver().delete(MoviesContract.FavoriteMovieEntry.CONTENT_URI, MoviesContract.FavoriteMovieEntry._ID + " = ? ", new String[]{idMovie});
        }
        super.onStop();

    }
}
