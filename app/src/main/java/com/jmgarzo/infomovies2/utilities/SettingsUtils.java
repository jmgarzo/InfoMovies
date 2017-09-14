package com.jmgarzo.infomovies2.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jmgarzo.infomovies2.R;


/**
 * Created by jmgarzo on 09/02/17.
 */

public class SettingsUtils {

    private final String LOG_TAG = SettingsUtils.class.getSimpleName();

    public static String getPreferredSortBy(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(context.getString(R.string.pref_sort_by_key),context.getString(R.string.pref_sort_by_default));
    }

    public static boolean isPreferenceSortByMostPopular(Context context){
        if(getPreferredSortBy(context).equalsIgnoreCase(context.getString(R.string.pref_sort_by_value_most_popular))){
            return true;
        }
        return false;
    }

    public static boolean isPreferenceSortByTopRated(Context context){
        if(getPreferredSortBy(context).equalsIgnoreCase(context.getString(R.string.pref_sort_by_value_top_rated))){
            return true;
        }
        return false;
    }

    public static boolean isPreferenceSortByFavorite(Context context){
        if(getPreferredSortBy(context).equalsIgnoreCase(context.getString(R.string.pref_sort_by_value_favorite))){
            return true;
        }
        return false;
    }

}
