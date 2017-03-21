package com.jmgarzo.udacity.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;

/**
 * Created by jmgarzo on 21/03/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {


    private Context mContext;

    private Cursor mCursor;

    public ReviewAdapter(@NonNull Context context){
        mContext = context;
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.list_item_review,parent,false);
        view.setFocusable(true);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {

        if (mCursor != null && mCursor.moveToPosition(position)) {
            int indexAuthor = mCursor.getColumnIndex(PopularMovieContract.ReviewEntry.AUTHOR);
            holder.tvAuthor.setText(mCursor.getString(indexAuthor));

            int reviewIndex = mCursor.getColumnIndex(PopularMovieContract.ReviewEntry.CONTENT);
            holder.tvReview.setText(mCursor.getString(reviewIndex));
        }
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvAuthor;
        public final TextView tvReview;

        public ReviewAdapterViewHolder (View view){
            super(view);
            tvAuthor = (TextView) view.findViewById(R.id.tv_review_author);
            tvReview = (TextView) view.findViewById(R.id.tv_review);

        }


    }
}
