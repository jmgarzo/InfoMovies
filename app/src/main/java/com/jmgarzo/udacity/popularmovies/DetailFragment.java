package com.jmgarzo.udacity.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private TextView releaseDate;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        releaseDate = (TextView) view.findViewById(R.id.tv_release_date_detail);
        Intent intent = getActivity().getIntent();
        if(null != intent){
            releaseDate.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
        }

        return view;

    }

    public class LoadDataFromUrl extends AsyncTask<String,Void,String[]>{
        private final String LOG_TAG = LoadDataFromUrl.class.getSimpleName();


        @Override
        protected String[] doInBackground(String... strings) {
            if(strings.length==0){
                return null;
            }


            return new String[0];
        }
    }
}
