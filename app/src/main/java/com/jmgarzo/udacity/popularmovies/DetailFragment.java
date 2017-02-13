package com.jmgarzo.udacity.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.utilities.NetworksUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private Activity mActivity;
    private ProgressBar mProgressBar;
    private TextView mErrorMenssageDetail;
    private LinearLayout mContentLayout;
    private ImageView postertImage;
    private TextView releaseDate;
    private TextView voteAverage;
    private TextView overview;


    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mActivity=getActivity();
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_indicator_detail);
        mContentLayout = (LinearLayout) view.findViewById(R.id.content_layout);
        mErrorMenssageDetail = (TextView) view.findViewById(R.id.tv_error_message_detail);
        releaseDate = (TextView) view.findViewById(R.id.tv_release_date);
        postertImage = (ImageView) view.findViewById(R.id.iv_poster_image);
        voteAverage = (TextView) view.findViewById(R.id.tv_vote_average);
        overview = (TextView) view.findViewById(R.id.tv_overview_text);

        Intent intent = getActivity().getIntent();
        if(null != intent){
            String movieId = intent.getStringExtra(Intent.EXTRA_TEXT);
            if(null!=movieId && !"".equals(movieId)) {
                new LoadDataFromUrl().execute(movieId);
            }
        }

        return view;

    }

    private void showMovieData() {
        mErrorMenssageDetail.setVisibility(View.INVISIBLE);
        mContentLayout.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mContentLayout.setVisibility(View.INVISIBLE);
        mErrorMenssageDetail.setVisibility(View.VISIBLE);
    }

    public class LoadDataFromUrl extends AsyncTask<String,Void,Movie>{
        private final String LOG_TAG = LoadDataFromUrl.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie doInBackground(String... strings) {
            if(strings.length==0){
                return null;
            }

            URL movieUrl = NetworksUtils.buildMovieUrl(strings[0]);
            Movie movie = new Movie();
            String jsonMovieResponse="";
            try {
                jsonMovieResponse = NetworksUtils.getResponseFromHttpUrl(movieUrl);
                movie = getMovieFromJson(jsonMovieResponse);
            } catch (IOException e) {
                Log.e(LOG_TAG,e.toString());
            }

            jsonMovieResponse.concat("");
            return movie;
        }
        private Movie getMovieFromJson(String movieJsonStr){
            final String POSTER_PATH = "poster_path";
            final String ID = "id";
            final String RELEASE_DATE = "release_date";
            final String TITLE="title";
            final String VOTE_AVERAGE="vote_average";
            final String OVERVIEW = "overview";

            if(null==movieJsonStr || movieJsonStr.equals("")){
                return null;
            }
            Movie movie = new Movie();


            JSONObject movieJson = null;

            try {
                movieJson = new JSONObject(movieJsonStr);

                movie.setId(movieJson.getInt(ID));
                movie.setPosterPath(movieJson.getString(POSTER_PATH));
                movie.setReleaseDate(movieJson.getString(RELEASE_DATE));
                movie.setTitle(movieJson.getString(TITLE));
                movie.setVoteAverage(movieJson.getDouble(VOTE_AVERAGE));
                movie.setOverview(movieJson.getString(OVERVIEW));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return  movie;


        }

        @Override
        protected void onPostExecute(Movie movie) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if(null!=movie) {
                showMovieData();
                mActivity.setTitle(movie.getTitle());
                releaseDate.setText(movie.getReleaseDate());
                Picasso.with(mActivity)
                        .load(NetworksUtils.buildPosterDetail(movie.getPosterPath()).toString())
                        .placeholder(R.drawable.placeholder)
                        .tag(mActivity)
                        .into(postertImage);
                voteAverage.setText(Double.toString(movie.getVoteAverage()).concat("/10"));
                overview.setText(movie.getOverview());
            }else{
                showErrorMessage();
            }
        }
    }
}
