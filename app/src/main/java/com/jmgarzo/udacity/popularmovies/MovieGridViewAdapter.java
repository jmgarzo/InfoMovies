package com.jmgarzo.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jmgarzo.udacity.popularmovies.Objects.Movie;
import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;
import com.jmgarzo.udacity.popularmovies.sync.AddFavoriteIntentService;
import com.jmgarzo.udacity.popularmovies.sync.AddTrailerAndReviewIntentService;
import com.jmgarzo.udacity.popularmovies.utilities.DataBaseUtils;
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
        String posterPath = mCursor.getString(DataBaseUtils.COL_MOVIE_POSTER_PATH);

        String posterUrl = NetworksUtils.buildPosterThumbnail(posterPath);
        Picasso.with(mContext)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.ic_broken_image_black_48px)
                .tag(mContext)
                .into(holder.mMovieThumb);

        Intent addTrailersAndReviewsIntent = new Intent(mContext, AddTrailerAndReviewIntentService.class);
        addTrailersAndReviewsIntent.putExtra(DetailFragment.MOVIE_TAG, new Movie(mCursor, position));
        mContext.startService(addTrailersAndReviewsIntent);


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
            Movie movie = new Movie(mCursor, adapterPosition);

            mClickHandler.onClick(movie);


        }
    }


}
