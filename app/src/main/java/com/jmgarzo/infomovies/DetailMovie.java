package com.jmgarzo.infomovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jmgarzo.infomovies.data.MoviesContract;

public class DetailMovie extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
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
            arguments.putString(MoviesContract.MoviesEntry._ID,getIntent().getStringExtra(MoviesContract.MoviesEntry._ID));
            DetailMovieFragment fragment = new DetailMovieFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
