package com.jmgarzo.udacity.popularmovies;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;
import com.jmgarzo.udacity.popularmovies.data.PopularMoviesDBHelper;
import com.jmgarzo.udacity.popularmovies.data.PopularMoviesProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Created by jmgarzo on 14/03/17.
 */

@RunWith(AndroidJUnit4.class)
public class TestPopularMoviesProvider {

    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void setUp() {

        deleteAllRecordsFromWeatherTable();
    }

    @Test
    public void testProviderRegistry() {

        String packageName = mContext.getPackageName();
        String PopularMoviesProviderClassName = PopularMoviesProvider.class.getName();
        ComponentName componentName = new ComponentName(packageName, PopularMoviesProviderClassName);

        try {

            PackageManager pm = mContext.getPackageManager();

            /* The ProviderInfo will contain the authority, which is what we want to test */
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            String actualAuthority = providerInfo.authority;
            String expectedAuthority = PopularMovieContract.CONTENT_AUTHORITY;

            /* Make sure that the registered authority matches the authority from the Contract */
            String incorrectAuthority =
                    "Error: PopularMoviesProvider registered with authority: " + actualAuthority +
                            " instead of expected authority: " + expectedAuthority;
            assertEquals(incorrectAuthority,
                    actualAuthority,
                    expectedAuthority);

        } catch (PackageManager.NameNotFoundException e) {
            String providerNotRegisteredAtAll =
                    "Error: WeatherProvider not registered at " + mContext.getPackageName();
            fail(providerNotRegisteredAtAll);
        }


    }

    @Test
    public void testBasicMovieQuery(){
        PopularMoviesDBHelper dbHelper = new PopularMoviesDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


    }


    private void deleteAllRecordsFromWeatherTable() {
        PopularMoviesDBHelper helper = new PopularMoviesDBHelper(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase database = helper.getWritableDatabase();

        database.delete(PopularMovieContract.MovieEntry.TABLE_NAME, null, null);
        database.delete(PopularMovieContract.TrailerEntry.TABLE_NAME, null, null);
        database.delete(PopularMovieContract.ReviewEntry.TABLE_NAME, null, null);

        database.close();
    }
}
