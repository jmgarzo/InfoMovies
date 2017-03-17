package com.jmgarzo.udacity.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.jmgarzo.udacity.popularmovies.data.PopularMovieContract;

import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by jmgarzo on 14/03/17.
 */

public class TestUtilities {

    public static final int BULK_INSERT_MOVIE_RECORDS_TO_INSERT = 10;
    public static final int BULK_INSERT_TRAILER_RECORDS_TO_INSERT = 10;
    public static final int BULK_INSERT_REVIEW_RECORDS_TO_INSERT = 10;



    static ContentValues createTestMovieContentValues() {
        ContentValues testMovieValues = new ContentValues();

        testMovieValues.put(PopularMovieContract.MovieEntry.POSTER_PATH, "/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg");
        testMovieValues.put(PopularMovieContract.MovieEntry.ADULT, "false");
        testMovieValues.put(PopularMovieContract.MovieEntry.OVERVIEW, "Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.");
        testMovieValues.put(PopularMovieContract.MovieEntry.RELEASE_DATE, "2015-06-09");
        testMovieValues.put(PopularMovieContract.MovieEntry.MOVIE_WEB_ID, "135397");
        testMovieValues.put(PopularMovieContract.MovieEntry.ORIGINAL_TITLE, "Jurassic World");
        testMovieValues.put(PopularMovieContract.MovieEntry.ORIGINAL_LANGUAGE, "en");
        testMovieValues.put(PopularMovieContract.MovieEntry.TITLE, "Jurassic World");
        testMovieValues.put(PopularMovieContract.MovieEntry.BACKDROP_PATH, "/dkMD5qlogeRMiEixC4YNPUvax2T.jpg");
        testMovieValues.put(PopularMovieContract.MovieEntry.TITLE, "Jurassic World");
        //TODO: There is a problem with double values precision https://code.google.com/p/android/issues/detail?id=22219
        //To this tests we use doubles values with less precision
        testMovieValues.put(PopularMovieContract.MovieEntry.POPULARITY, 68.465); //68.465163
        testMovieValues.put(PopularMovieContract.MovieEntry.VOTE_COUNT, 6456);
        testMovieValues.put(PopularMovieContract.MovieEntry.VIDEO, "false");
        testMovieValues.put(PopularMovieContract.MovieEntry.VOTE_AVERAGE, 6.5);
        testMovieValues.put(PopularMovieContract.MovieEntry.REGISTRY_TYPE, PopularMovieContract.MOST_POPULAR_REGISTRY_TYPE);

        return testMovieValues;
    }


    static ContentValues createTestTrailerContentValues(int movieId) {
        ContentValues testTrailerValues = new ContentValues();

        testTrailerValues.put(PopularMovieContract.TrailerEntry.MOVIE_KEY, movieId);
        testTrailerValues.put(PopularMovieContract.TrailerEntry.WEB_TRAILER_ID, "58bdec35c3a368664d0523fe");
        testTrailerValues.put(PopularMovieContract.TrailerEntry.ISO_639_1, "en");
        testTrailerValues.put(PopularMovieContract.TrailerEntry.ISO_3166_1, "US");
        testTrailerValues.put(PopularMovieContract.TrailerEntry.KEY, "FId2fr_XP9k");
        testTrailerValues.put(PopularMovieContract.TrailerEntry.NAME, "Teaser Trailer Preview");
        testTrailerValues.put(PopularMovieContract.TrailerEntry.SITE, "YouTube");
        testTrailerValues.put(PopularMovieContract.TrailerEntry.SIZE, "1080,");
        testTrailerValues.put(PopularMovieContract.TrailerEntry.TYPE, "Teaser");
        testTrailerValues.put(PopularMovieContract.TrailerEntry.REGISTRY_TYPE,
                PopularMovieContract.MOST_POPULAR_REGISTRY_TYPE);

        return testTrailerValues;
    }

    static ContentValues createTestReviewContentValues(int movieId) {
        ContentValues testTrailerValues = new ContentValues();

        testTrailerValues.put(PopularMovieContract.ReviewEntry.MOVIE_KEY, movieId);
        testTrailerValues.put(PopularMovieContract.ReviewEntry.WEB_REVIEW_ID, "583d5becc3a36835a200eb7d");
        testTrailerValues.put(PopularMovieContract.ReviewEntry.AUTHOR, "Gimly");
        testTrailerValues.put(PopularMovieContract.ReviewEntry.CONTENT, "Struggles desperately to be three movies at once: One about Pokemon, one about proto-Voldemort and one (uncharacteristically dark story) about child abuse. But none of these three movies are bad movies so _Fantastic Beasts_ gets a pass from me. \r\n\r\nI was particularly fond of the degree to which it tied into the Harry Potter world at large. There were moments were I went “Oh Harry’s used that same spell before!” or characters that fitted naturally into the narrative being mentioned, as opposed to getting all _Agents of SHIELD_ season 1 on us, and awkwardly name-dropping something from the other films every 5 minutes, just in case we forgot, which was what I was afraid it might do.\r\n\r\nEnd result, _Fantastic Beasts_ is a flawed film that I was still very happy to have watched, and exceeded my expectations.\r\n\r\n_Final rating:★★★ - I personally recommend you give it a go._");
        testTrailerValues.put(PopularMovieContract.ReviewEntry.URL, "https://www.themoviedb.org/review/583d5becc3a36835a200eb7d");
        testTrailerValues.put(PopularMovieContract.ReviewEntry.REGISTRY_TYPE, PopularMovieContract.MOST_POPULAR_REGISTRY_TYPE);

        return testTrailerValues;
    }

    static void validateThenCloseCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertNotNull("This cursor is null. Did you make sure to register your ContentProvider in the manifest?",
                valueCursor);
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }


    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int index = valueCursor.getColumnIndex(columnName);

            /* Test to see if the column is contained within the cursor */
            String columnNotFoundError = "Column '" + columnName + "' not found. " + error;
            assertFalse(columnNotFoundError, index == -1);

            /* Test to see if the expected value equals the actual value (from the Cursor) */
            String expectedValue = entry.getValue().toString();
            String actualValue = valueCursor.getString(index);


            String valuesDontMatchError = "Actual value '" + actualValue
                    + "' did not match the expected value '" + expectedValue + "'. "
                    + error;

            assertEquals(valuesDontMatchError,
                    expectedValue,
                    actualValue);
        }
    }


    static ContentValues[] createBulkInsertTestMovieValues(){

        ContentValues[] bulkTestMovieValues = new ContentValues[BULK_INSERT_MOVIE_RECORDS_TO_INSERT];

        for (int i=0; i<BULK_INSERT_MOVIE_RECORDS_TO_INSERT;i++ ){

            ContentValues testMovieValues = new ContentValues();

            testMovieValues.put(PopularMovieContract.MovieEntry.POSTER_PATH, "/jjBgi2r5cRt36xF6iNUEhzscEcb" + i +".jpg");
            testMovieValues.put(PopularMovieContract.MovieEntry.ADULT, "false");
            testMovieValues.put(PopularMovieContract.MovieEntry.OVERVIEW, i +"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.");
            testMovieValues.put(PopularMovieContract.MovieEntry.RELEASE_DATE, "2015-06-09");
            testMovieValues.put(PopularMovieContract.MovieEntry.MOVIE_WEB_ID, i+"_135397");
            testMovieValues.put(PopularMovieContract.MovieEntry.ORIGINAL_TITLE, i + "_Jurassic World");
            testMovieValues.put(PopularMovieContract.MovieEntry.ORIGINAL_LANGUAGE, i+"_en");
            testMovieValues.put(PopularMovieContract.MovieEntry.TITLE, i+"_Jurassic World");
            testMovieValues.put(PopularMovieContract.MovieEntry.BACKDROP_PATH, i+"_/dkMD5qlogeRMiEixC4YNPUvax2T.jpg");
            testMovieValues.put(PopularMovieContract.MovieEntry.TITLE, i+"_Jurassic World");
            testMovieValues.put(PopularMovieContract.MovieEntry.POPULARITY, 68.465 + i); //68.465163
            testMovieValues.put(PopularMovieContract.MovieEntry.VOTE_COUNT, 6456 +i);
            testMovieValues.put(PopularMovieContract.MovieEntry.VIDEO, "false");
            testMovieValues.put(PopularMovieContract.MovieEntry.VOTE_AVERAGE, 6.5 + i);
            testMovieValues.put(PopularMovieContract.MovieEntry.REGISTRY_TYPE, PopularMovieContract.MOST_POPULAR_REGISTRY_TYPE);

            bulkTestMovieValues[i] = testMovieValues;
        }

        return bulkTestMovieValues;
    }



}
