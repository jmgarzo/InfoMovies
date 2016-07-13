package com.jmgarzo.infomovies;

import android.content.Intent;
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
        Movie movie = intent.getExtras().getParcelable("movie");

        getActivity().setTitle(movie.getTitle());

        ImageView imageViewPoster = (ImageView) rootView.findViewById(R.id.img_poster);
        Picasso.with(getContext()).load(movie.getPosterPath()).into(imageViewPoster);

        TextView txtOverview = (TextView) rootView.findViewById(R.id.txt_overview);
        txtOverview.setText(movie.getOverview());

        TextView txtReleaseDate = (TextView) rootView.findViewById(R.id.txt_release_date);
        txtReleaseDate.setText(movie.getReleaseDate());

        TextView txtVoteAverage = (TextView) rootView.findViewById(R.id.txt_vote_average);
        txtVoteAverage.setText(movie.getVoteAverage()+ "/ 10");









        movie.getPosterPath();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

}
