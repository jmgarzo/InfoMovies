package com.jmgarzo.infomovies2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jmgarzo.infomovies2.Objects.Movie;
import com.jmgarzo.infomovies2.sync.PopularMoviesSyncUtils;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    public static final String MOVIE_INTENT_TAG = "movie_intent_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainActivityFragment())
                    .commit();
        }

        PopularMoviesSyncUtils.initialize(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:{
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void OnItemSelected(Movie movie) {
        Intent intent = new Intent(this,Detail.class);
        intent.putExtra(MOVIE_INTENT_TAG,movie);
        startActivity(intent);
    }
}
