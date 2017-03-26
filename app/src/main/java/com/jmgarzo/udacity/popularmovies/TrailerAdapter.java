package com.jmgarzo.udacity.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmgarzo.udacity.popularmovies.Objects.Trailer;
import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jmgarzo on 21/03/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private Context mContext;

    private final TrailerAdapterOnClickHandler mClickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    private Cursor mCursor;

    public TrailerAdapter(@NonNull Context context, TrailerAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }


    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.list_item_trailer, parent, false);
        view.setFocusable(true);

        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder holder, int position) {
        if (mCursor != null && mCursor.moveToPosition(position)) {

            int indexTitle = mCursor.getColumnIndex(PopularMovieContract.TrailerEntry.NAME);
            holder.tvTitle.setText(mCursor.getString(indexTitle));
            int indexSite = mCursor.getColumnIndex(PopularMovieContract.TrailerEntry.SITE);
            holder.tvTrailerInfo.setText(mCursor.getString(indexSite));
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


    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_trailer_info) TextView tvTrailerInfo;

        public TrailerAdapterViewHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Trailer trailer = new Trailer(mCursor,adapterPosition);
            mClickHandler.onClick(trailer);
        }
    }
}
