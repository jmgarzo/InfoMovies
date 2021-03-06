package com.jmgarzo.infomovies2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by jmgarzo on 23/07/2016.
 */
public class ReviewAdapter extends CursorAdapter {


    public ReviewAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_review,parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

//        ImageView imageView = (ImageView) view.findViewById(R.id.icon_review);
//        imageView.setImageResource(R.drawable.ic_comment_black_48dp );

        TextView authorTextView = (TextView) view.findViewById(R.id.author_review_textview);
        if(Utility.isPreferenceSortByFavorite(context)){
            authorTextView.setText(cursor.getString(DetailMovieFragment.COL_FAVORITE_REVIEW_AUTHOR));

        }else {
            authorTextView.setText(cursor.getString(DetailMovieFragment.COL_REVIEW_AUTHOR));
        }

        TextView contentTextView = (TextView) view.findViewById(R.id.content_review_textview);
        if(Utility.isPreferenceSortByFavorite(context)) {
            contentTextView.setText(cursor.getString(DetailMovieFragment.COL_FAVORITE_REVIEW_CONTENT));
        }else{
            contentTextView.setText(cursor.getString(DetailMovieFragment.COL_REVIEW_CONTENT));

        }

//        Log.v("Trailer----------->", cursor.getString(DetailMovieFragment.COL_MOVIE_WEB_ID));


    }


}
