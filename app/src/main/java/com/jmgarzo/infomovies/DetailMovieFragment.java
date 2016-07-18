package com.jmgarzo.infomovies;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmgarzo.infomovies.data.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailMovieFragment extends Fragment {

//    private ImageView imageViewPoster = null;
//    private TextView txtSynopsis = null;

    public DetailMovieFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        Intent intent = getActivity().getIntent();
        //Movie movie = intent.getExtras().getParcelable("movie");
        String webMovieId = intent.getExtras().getString(getString(R.string.mdb_movie_web_id_key));

        Uri MovieWithWebIdUri = MoviesContract.MoviesEntry.buildMovieWithWebId(webMovieId);
        Cursor cursor = getActivity().getContentResolver().query(
                MovieWithWebIdUri ,
                null,
                MoviesContract.MoviesEntry.MOVIE_WEB_ID + " = ?",
                new String[]{webMovieId},
                null);

        String title="";
        String posterPath="";
        String overview="";
        String releaseDate="";
        String voteAverage="";
        if (cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.TITLE);
            title = cursor.getString(titleIndex);

            int posterPathIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.POSTER_PATH);
            posterPath = cursor.getString(posterPathIndex);

            int overviewIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.OVERVIEW);
            overview = cursor.getString(overviewIndex);

            int releaseDateIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.RELEASE_DATE);
            releaseDate = cursor.getString(releaseDateIndex);

            int voteAverageIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.VOTE_AVERAGE);
            voteAverage = cursor.getString(voteAverageIndex);



        }


        getActivity().setTitle(title);

        ImageView imageViewPoster = (ImageView) rootView.findViewById(R.id.img_poster);
        Picasso.with(getContext()).load(posterPath).into(imageViewPoster);

        TextView txtOverview = (TextView) rootView.findViewById(R.id.txt_overview);
        txtOverview.setText(overview);

        TextView txtReleaseDate = (TextView) rootView.findViewById(R.id.txt_release_date);
        txtReleaseDate.setText(releaseDate);

        TextView txtVoteAverage = (TextView) rootView.findViewById(R.id.txt_vote_average);
        txtVoteAverage.setText(voteAverage + "/ 10");



        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

}
