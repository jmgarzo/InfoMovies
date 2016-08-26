package com.jmgarzo.infomovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by jmgarzo on 19/07/2016.
 */
public class Utility {

    private final String LOG_TAG = Utility.class.getSimpleName();
    public static String getPreferredSortBy(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(context.getString(R.string.pref_sort_by_key),context.getString(R.string.pref_sort_by_defautl));
    }

    public static boolean isPreferenceSortByFavorite(Context context){
        if(getPreferredSortBy(context).equalsIgnoreCase(context.getString(R.string.pref_sort_by_favorite))){
            return true;
        }
        return false;
    }

    static String formatDate(long dateInMilliseconds) {
        Date date = new Date(dateInMilliseconds);
        return DateFormat.getDateInstance().format(date);
    }








}
