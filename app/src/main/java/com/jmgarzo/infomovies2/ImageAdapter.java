package com.jmgarzo.infomovies2;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jmgarzo on 25/07/2016.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private Cursor mData;
    ArrayList<String> mListPaths =null;


    public ImageAdapter(Context c, Cursor data) {
        mContext = c;
        mData = data;
        if(mData.moveToFirst()){
            mListPaths = new ArrayList<String>();
            do{
                mListPaths.add(mData.getString(DetailMovieFragment.COL_POSTER_PATH));
            }while (mData.moveToNext());

        }
    }
    @Override
    public int getCount() {
        return mListPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return mListPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(mContext);

        }
        else{
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(mListPaths.get(position))
                .fit()
                .into(imageView);

        return imageView;




    }









}
