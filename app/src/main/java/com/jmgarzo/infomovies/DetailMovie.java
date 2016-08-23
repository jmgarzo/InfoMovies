package com.jmgarzo.infomovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jmgarzo.infomovies.data.MoviesContract;

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


            Cursor cursorFavorite = getContentResolver().query(MoviesContract.FavoriteEntry.buildFavoriteWithWebId(webMovieId), null, null, null, null);
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

                    Cursor cursorMovie = getContentResolver().query(MoviesContract.MoviesEntry.buildMoviesWithIdUri(Long.parseLong(idMovie)), null, null, null, null);
                    if (cursorMovie.moveToFirst()) {
                        int index = cursorMovie.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_WEB_ID);
                        String webMovieId = cursorMovie.getString(index);


                        Cursor cursorFavorite = getContentResolver().query(MoviesContract.FavoriteEntry.buildFavoriteWithWebId(webMovieId), null, null, null, null);
                        if (!cursorFavorite.moveToFirst()) {
                            ContentValues cv = getFavoriteValues();
                            if (cv != null) {
                                getContentResolver().insert(MoviesContract.FavoriteEntry.CONTENT_URI, cv);
                                fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                            }
                        } else {
                            int indx = cursorFavorite.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_WEB_ID);
                            String webMovieIdFavorite = cursorFavorite.getString(index);
                            getContentResolver().delete(MoviesContract.FavoriteEntry.CONTENT_URI, MoviesContract.FavoriteEntry.MOVIE_WEB_ID + " = ? ", new String[]{webMovieIdFavorite});
                            fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                        }
                    }
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

    ContentValues getFavoriteValues() {
        Cursor cursor = getContentResolver().query(MoviesContract.MoviesEntry.buildMoviesWithIdUri(Long.parseLong(idMovie)), DetailMovieFragment.MOVIE_COLUMNS, null, null, null);

        ContentValues cv = null;
        if (cursor.moveToFirst()) {
            cv = new ContentValues();
            cv.put(MoviesContract.FavoriteEntry.POSTER_PATH, cursor.getString(DetailMovieFragment.COL_POSTER_PATH));
            cv.put(MoviesContract.FavoriteEntry.ADULT, cursor.getString(DetailMovieFragment.COL_ADULT));
            cv.put(MoviesContract.FavoriteEntry.OVERVIEW, cursor.getString(DetailMovieFragment.COL_OVERVIEW));
            cv.put(MoviesContract.FavoriteEntry.RELEASE_DATE, cursor.getString(DetailMovieFragment.COL_RELEASE_DATE));
            cv.put(MoviesContract.FavoriteEntry.MOVIE_WEB_ID, cursor.getString(DetailMovieFragment.COL_MOVIE_WEB_ID));
            cv.put(MoviesContract.FavoriteEntry.ORIGINAL_TITLE, cursor.getString(DetailMovieFragment.COL_ORIGINAL_TITLE));
            cv.put(MoviesContract.FavoriteEntry.ORIGINAL_LANGUAGE, cursor.getString(DetailMovieFragment.COL_ORIGINAL_LANGUAGE));

            cv.put(MoviesContract.FavoriteEntry.TITLE, cursor.getString(DetailMovieFragment.COL_TITLE));

            cv.put(MoviesContract.FavoriteEntry.BACKDROP_PATH, cursor.getString(DetailMovieFragment.COL_BACKDROP_PATH));

            cv.put(MoviesContract.FavoriteEntry.POPULARITY, Double.parseDouble(cursor.getString(DetailMovieFragment.COL_POPULARITY)));

            cv.put(MoviesContract.FavoriteEntry.VOTE_COUNT, Integer.parseInt(cursor.getString(DetailMovieFragment.COL_VOTE_COUNT)));

            cv.put(MoviesContract.FavoriteEntry.VIDEO, cursor.getString(DetailMovieFragment.COL_VIDEO));
            cv.put(MoviesContract.FavoriteEntry.VOTE_AVERAGE, Double.parseDouble(cursor.getString(DetailMovieFragment.COL_VOTE_AVERAGE)));
            cv.put(MoviesContract.FavoriteEntry.RELEASE_DATE, cursor.getString(DetailMovieFragment.COL_RELEASE_DATE));
            cv.put(MoviesContract.FavoriteEntry.ADD_DATE, Utility.formatDate(System.currentTimeMillis()));
            cursor.close();
        }
        return cv;
    }
}
