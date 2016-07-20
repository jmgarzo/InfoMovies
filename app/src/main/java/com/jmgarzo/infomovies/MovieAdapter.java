package com.jmgarzo.infomovies;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.jmgarzo.infomovies.data.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by jmgarzo on 19/07/2016.
 */
public class MovieAdapter extends CursorAdapter {

    public MovieAdapter(Context context, Cursor c, int flags){
        super(context,c,flags);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_movie,parent,false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = (ImageView) view;
        int idx_poster_path = cursor.getColumnIndex(MoviesContract.MoviesEntry.POSTER_PATH);
        String path = cursor.getString(idx_poster_path);

        Picasso.with(context).load(path).into(imageView);



    }
}
