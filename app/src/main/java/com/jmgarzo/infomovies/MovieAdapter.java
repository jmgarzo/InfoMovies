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

    private final LayoutInflater inflater;

    public static class ViewHolder{
        public  ImageView imageView;

        public ViewHolder(View view){
            imageView = (ImageView) view.findViewById(R.id.list_item_image);
        }
    }

    public MovieAdapter(Context context, Cursor c, int flags){
        super(context,c,flags);
        inflater = LayoutInflater.from(context);

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View itemLayout = inflater.inflate(R.layout.list_item_movie, parent, false);

        ViewHolder holder = new ViewHolder(itemLayout);

        holder.imageView = (ImageView) itemLayout.findViewById(R.id.list_item_image);

        itemLayout.setTag(holder);

        return itemLayout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

//        View itemLayout = inflater.inflate(R.layout.list_item_movie, viewGroup, false);
//        ViewHolder holder = new ViewHolder();
//        holder. = (TextView) itemLayout.findViewById(android.R.id.text1);
//        holder.imageView = (ImageView) .findViewById(android.R.id.icon);
////        ImageView imageView = (ImageView) view.findViewById(R.id.list_item_image);
//        String posterPath = cursor.getString(MainActivityFragment.COL_POSTER_PATH);

        ViewHolder holder = (ViewHolder) view.getTag();

        if(Utility.isPreferenceSortByFavorite(context)){
            int indexPosterPath = cursor.getColumnIndex(MoviesContract.FavoriteMovieEntry.POSTER_PATH);
            String posterPath = cursor.getString(indexPosterPath);

            Picasso.with(context)
                    .load(posterPath)
                    .placeholder(R.drawable.placeholder)
                    .tag(context)
                    .into(holder.imageView);
        }else {

            String url = cursor.getString(DetailMovieFragment.COL_POSTER_PATH);
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .tag(context)
                    .into(holder.imageView);
        }









//

//       Picasso.with(context).load(posterPath).into(viewHolder.imageView);
//        imageView.setAdjustViewBounds(true);
//        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);



    }
}
