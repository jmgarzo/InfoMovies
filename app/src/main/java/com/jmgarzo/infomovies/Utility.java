package com.jmgarzo.infomovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jmgarzo on 19/07/2016.
 */
public class Utility {

    private final String LOG_TAG = Utility.class.getSimpleName();
    public static String getPreferredSortBy(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String defaultValue = context.getResources().getString(R.string.pref_sort_by_defautl);
        String result = sharedPref.getString(context.getString(R.string.pref_sort_by_key), defaultValue);

//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
//        String result = pref.getString(context.getString(R.string.pref_sort_by_key),context.getString(R.string.pref_sort_by_defautl));
        return result;

    }

    public static void setPrererenceShortByFavorite(Context context){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.pref_sort_by_key), context.getString(R.string.pref_sort_by_favorite) );
        editor.commit();

    }

    public static void setPrererenceShortByTopRate(Context context){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.pref_sort_by_key), context.getString(R.string.pref_sort_by_top_rate) );
        editor.commit();

    }

    public static void setPrererenceShortByMostPopular(Context context){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.pref_sort_by_key), context.getString(R.string.pref_sort_by_most_popular) );
        editor.commit();

    }

    public static boolean isPreferenceSortByFavorite(Context context){
        if(getPreferredSortBy(context).equalsIgnoreCase(context.getString(R.string.pref_sort_by_favorite))){
            return true;
        }
        return false;
    }

    public static boolean isPreferenceSortByMostPopular(Context context){
        if(getPreferredSortBy(context).equalsIgnoreCase(context.getString(R.string.pref_sort_by_most_popular))){
            return true;
        }
        return false;
    }

    public static boolean isPreferenceSortByTopRate(Context context){
        if(getPreferredSortBy(context).equalsIgnoreCase(context.getString(R.string.pref_sort_by_top_rate))){
            return true;
        }
        return false;
    }

    static String formatDate(long dateInMilliseconds) {
        Date date = new Date(dateInMilliseconds);
        return DateFormat.getDateInstance().format(date);
    }

    static String getMonthAndYear (String dateStr)  {

//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
//        try {
//            cal.setTime(sdf.parse(date));// all done
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//         cal.getTime()


        DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
        //fromFormat.setLenient(false);
        DateFormat toFormat = new SimpleDateFormat("MM-yyyy");
        toFormat.setLenient(false);
        Date date = null;
        try {
            date = fromFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        System.out.println(toFormat.format(date));

        return toFormat.format(date);
    }








}
