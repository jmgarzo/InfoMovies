package com.jmgarzo.infomovies2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jmgarzo on 21/07/2016.
 */
public class TrailerAdapter extends CursorAdapter {

    public TrailerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_trailer, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        ImageView imageView = (ImageView) view.findViewById(R.id.trailer_icon_image);
        imageView.setImageResource(R.drawable.ic_play_circle_filled_black_48dp);

        TextView textViewTitle = (TextView) view.findViewById(R.id.trailer_title_textview);
        String text = cursor.getString(DetailMovieFragment.COL_VIDEO_NAME);
        textViewTitle.setText(text);


        TextView textViewType = (TextView) view.findViewById(R.id.trailer_type_textview);
        textViewType.setText(cursor.getString(DetailMovieFragment.COL_VIDEO_TYPE)+ " / ");

        TextView textViewSize = (TextView) view.findViewById(R.id.trailer_size_textview);
        textViewSize.setText(cursor.getString(DetailMovieFragment.COL_VIDEO_SIZE) + "p");

//        TextView textViewIso_639 = (TextView) view.findViewById(R.id.trailer_iso_639_1_textview);
//        textViewIso_639.setText(cursor.getString(DetailMovieFragment.COL_VIDEO_ISO_639_1) + "-");
//
//        TextView textViewIso_3166 = (TextView) view.findViewById(R.id.trailer_iso_3166_1_textview);
//        textViewIso_3166.setText(cursor.getString(DetailMovieFragment.COL_VIDEO_ISO_3166_1));

    }


}
