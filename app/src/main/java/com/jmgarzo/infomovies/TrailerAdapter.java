package com.jmgarzo.infomovies;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmgarzo.infomovies.data.MoviesContract;

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
            imageView.setImageResource(R.mipmap.ic_launcher);

            TextView textViewTitle = (TextView) view.findViewById(R.id.trailer_title_text);
            int indexName = cursor.getColumnIndex(MoviesContract.VideoEntry.NAME);
            String title = cursor.getString(indexName);
            textViewTitle.setText(title);

            TextView textViewSite = (TextView) view.findViewById(R.id.trailer_site_text);
            int indexSite = cursor.getColumnIndex(MoviesContract.VideoEntry.SITE);
            String site = cursor.getString(indexSite);
            textViewSite.setText(site);

        }


}
