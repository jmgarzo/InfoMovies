package com.jmgarzo.udacity.popularmovies;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;
import com.jmgarzo.udacity.popularmovies.data.PopularMoviesDBHelper;
import com.jmgarzo.udacity.popularmovies.data.PopularMoviesProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by jmgarzo on 14/03/17.
 */

@RunWith(AndroidJUnit4.class)
public class TestPopularMoviesProvider {

    private final Context mContext = InstrumentationRegistry.getTargetContext();
    private int movieId;

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

        ContentValues testMovieValues = TestUtilities.createTestMovieContentValues();

        long movieRowId = db.insert(PopularMovieContract.MovieEntry.TABLE_NAME,null,testMovieValues);

        String insertFailed = "Unable to insert into the database";
        assertTrue(insertFailed,movieRowId != -1);

        db.close();

        Cursor movieCursor = mContext.getContentResolver().query(
                PopularMovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        TestUtilities.validateThenCloseCursor("testBasicMovieQuery",
                movieCursor,
                testMovieValues);

        movieId = movieCursor.getColumnIndex(PopularMovieContract.MovieEntry._ID);



    }


    @Test
    public void testBasicTrailerQuery(){
        PopularMoviesDBHelper dbHelper = new PopularMoviesDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testTrailerValues = TestUtilities.createTestTrailerContentValues(movieId);

        long movieRowId = db.insert(PopularMovieContract.TrailerEntry.TABLE_NAME,null,testTrailerValues);

        String insertFailed = "Unable to insert into the database";
        assertTrue(insertFailed,movieRowId != -1);

        db.close();

        Cursor movieCursor = mContext.getContentResolver().query(
                PopularMovieContract.TrailerEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        TestUtilities.validateThenCloseCursor("testBasicMovieQuery",
                movieCursor,
                testTrailerValues);
    }


    @Test
    public void testBasicReviewQuery(){
        PopularMoviesDBHelper dbHelper = new PopularMoviesDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testReviewValues = TestUtilities.createTestReviewContentValues(movieId);

        long movieRowId = db.insert(PopularMovieContract.ReviewEntry.TABLE_NAME,null,testReviewValues);

        String insertFailed = "Unable to insert into the database";
        assertTrue(insertFailed,movieRowId != -1);

        db.close();

        Cursor movieCursor = mContext.getContentResolver().query(
                PopularMovieContract.ReviewEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        TestUtilities.validateThenCloseCursor("testBasicMovieQuery",
                movieCursor,
                testReviewValues);
    }


    @Test
    public void testBulkMovieInsert(){
        ContentValues[] bulkInsertContentValues = TestUtilities.createBulkInsertTestMovieValues();

        int insertCount = mContext.getContentResolver().bulkInsert(
                PopularMovieContract.MovieEntry.CONTENT_URI,
                bulkInsertContentValues);

        String expectedAndActualInsertedRecordCountDoNotMatch =
                "Number of expected records inserted does not match actual inserted record count";
        assertEquals(expectedAndActualInsertedRecordCountDoNotMatch,
                insertCount,
                TestUtilities.BULK_INSERT_MOVIE_RECORDS_TO_INSERT);


        Cursor cursor = mContext.getContentResolver().query(
                PopularMovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                PopularMovieContract.MovieEntry._ID + " ASC");

        assertEquals(cursor.getCount(), TestUtilities.BULK_INSERT_MOVIE_RECORDS_TO_INSERT);

        cursor.moveToFirst();
        for (int i = 0; i < TestUtilities.BULK_INSERT_MOVIE_RECORDS_TO_INSERT; i++, cursor.moveToNext()) {
            TestUtilities.validateCurrentRecord(
                    "testBulkInsert. Error validating MovieEntry " + i,
                    cursor,
                    bulkInsertContentValues[i]);
        }

        /* Always close the Cursor! */
        cursor.close();

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
