//package com.jmgarzo.infomovies;
//
//import android.app.Activity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
///**
// * Created by jmgarzo on 12/07/2016.
// */
//public class AndroidMovieAdapter extends ArrayAdapter<Movie> {
//
//    public AndroidMovieAdapter (Activity context, List<Movie> androidMovies){
//        super(context,0,androidMovies);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        Movie movie = getItem(position);
//
//        if(convertView == null){
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie,parent,false);
//        }
//
//        ImageView posterView = (ImageView) convertView.findViewById(R.id.list_item_image);
//        Picasso.with(getContext()).load(movie.getPosterPath()).into(posterView);
//        return convertView;    }
//}
