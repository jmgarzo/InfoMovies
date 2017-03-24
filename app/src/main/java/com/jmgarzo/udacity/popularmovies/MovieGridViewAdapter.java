package com.jmgarzo.udacity.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;
import com.jmgarzo.udacity.popularmovies.utilities.NetworksUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by jmgarzo on 09/02/17.
 */

public class MovieGridViewAdapter extends RecyclerView.Adapter<MovieGridViewAdapter.MovieAdapterViewHolder> {

    private Context mContext;

    private final MovieGridViewAdapterOnClickHandler mClickHandler;


    public interface MovieGridViewAdapterOnClickHandler {

        void onClick(Movie movie);
    }

    private Cursor mCursor;

    public MovieGridViewAdapter(@NonNull Context context, MovieGridViewAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;

    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.grid_item_movie, parent, false);
        view.setFocusable(true);
        return new MovieAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        mCursor.moveToPosition(position);
        int index = mCursor.getColumnIndex(PopularMovieContract.MovieEntry.POSTER_PATH);
        String posterPath = mCursor.getString(index);

        String posterUrl = NetworksUtils.buildPosterThumbnail(posterPath);
        Picasso.with(mContext)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .tag(mContext)
                .into(holder.mMovieThumb);
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

//    public void setMovies(ArrayList<Movie>moviesList){
//        mMoviesList = moviesList;
//        notifyDataSetChanged();
//    }

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mMovieThumb;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieThumb = (ImageView) view.findViewById(R.id.iv_movie_thumb);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = new Movie(mCursor,adapterPosition);
//            mCursor.moveToPosition(adapterPosition);
//            int index = mCursor.getColumnIndex(PopularMovieContract.MovieEntry._ID);
//            int movieId = mCursor.getInt(index);
            mClickHandler.onClick(movie);


        }
    }


}
