package com.jmgarzo.infomovies2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jmgarzo.infomovies2.Objects.Trailer;

public class Detail extends AppCompatActivity implements DetailFragment.Callback {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }



    @Override
    public void OnItemSelected(Trailer trailer) {
        startActivity(new Intent(Intent.ACTION_VIEW, trailer.getTrailerUri()));
    }
}
