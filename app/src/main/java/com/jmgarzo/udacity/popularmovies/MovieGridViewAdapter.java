package com.jmgarzo.udacity.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.utilities.NetworksUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jmgarzo on 09/02/17.
 */

public class MovieGridViewAdapter extends RecyclerView.Adapter<MovieGridViewAdapter.MovieAdapterViewHolder> {

    private ArrayList<Movie> mMoviesList;
    private Context mContext;

    private final MovieGridViewAdapterOnClickHandler mClickHandler;


    public interface MovieGridViewAdapterOnClickHandler {

        void onClick(int movieId);
    }



    public MovieGridViewAdapter(MovieGridViewAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;

    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final ImageView mMovieThumb;

        public MovieAdapterViewHolder(View view){
            super(view);
            mMovieThumb = (ImageView) view.findViewById(R.id.iv_movie_thumb);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mMoviesList.get(adapterPosition).getId());


        }
    }




    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.grid_item_movie,parent,false);
        return new MovieAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        String posterUrl = NetworksUtils.buildPosterThumbnail(mMoviesList.get(position).getPosterPath());
        Picasso.with(mContext)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .tag(mContext)
                .into(holder.mMovieThumb);

    }

    @Override
    public int getItemCount() {
        if(null==mMoviesList) return 0;
        return mMoviesList.size();
    }

    public void setMovies(ArrayList<Movie>moviesList){
        mMoviesList = moviesList;
        notifyDataSetChanged();
    }









}
