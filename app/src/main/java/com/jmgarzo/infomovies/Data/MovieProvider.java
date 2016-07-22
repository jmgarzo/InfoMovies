package com.jmgarzo.infomovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jmgarzo on 15/07/2016.
 */
public class MovieProvider extends ContentProvider {

    private static String LOG_TAG = MovieProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDBHelper mOpenHelper;

    static final int MOVIE = 100;
    static final int MOVIE_WITH_ID = 101;
    static final int PATH_POSTER = 102;
    static final int MOVIE_WITH_WEB_MOVIE_ID = 103;

    static final int VIDEO = 110;
    static final int VIDEO_WITH_ID = 111;
    static final int VIDEO_WITH_MOVIE_ID = 112;


    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MoviesContract.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, MoviesContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);
        matcher.addURI(authority, MoviesContract.PATH_MOVIE + "/#", MOVIE_WITH_WEB_MOVIE_ID);
        matcher.addURI(authority, MoviesContract.PATH_MOVIE + "*", PATH_POSTER);
        matcher.addURI(authority, MoviesContract.PATH_VIDEO, VIDEO);
        matcher.addURI(authority, MoviesContract.PATH_VIDEO + "/*", VIDEO_WITH_ID);
        matcher.addURI(authority, MoviesContract.PATH_VIDEO + "/*", VIDEO_WITH_MOVIE_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MoviesDBHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                SQLiteDatabase db = mOpenHelper.getReadableDatabase();
                retCursor = db.query(
                        MoviesContract.MoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            }
            case MOVIE_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MoviesContract.MoviesEntry.TABLE_NAME,
                        projection,
                        MoviesContract.MoviesEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            case MOVIE_WITH_WEB_MOVIE_ID:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MoviesContract.MoviesEntry.TABLE_NAME,
                        projection,
                        MoviesContract.MoviesEntry.MOVIE_WEB_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            case PATH_POSTER: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MoviesContract.MoviesEntry.TABLE_NAME,
                        projection,
                        MoviesContract.MoviesEntry._ID + " = ? ",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);

                break;
            }
            case VIDEO:{
                SQLiteDatabase db = mOpenHelper.getReadableDatabase();
                retCursor = db.query(
                        MoviesContract.VideoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case VIDEO_WITH_MOVIE_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MoviesContract.VideoEntry.TABLE_NAME,
                        projection,
                        MoviesContract.VideoEntry.MOVIE_KEY + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            default: {
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE:
                return MoviesContract.MoviesEntry.CONTENT_DIR_TYPE;
            case MOVIE_WITH_ID:
                return MoviesContract.MoviesEntry.CONTENT_DIR_TYPE;
            case PATH_POSTER:
                return MoviesContract.MoviesEntry.CONTENT_ITEM_TYPE;
            case MOVIE_WITH_WEB_MOVIE_ID:
                return MoviesContract.MoviesEntry.CONTENT_DIR_TYPE;
            case VIDEO:
                return MoviesContract.VideoEntry.CONTENT_DIR_TYPE;
            case VIDEO_WITH_ID:
                return MoviesContract.VideoEntry.CONTENT_DIR_TYPE;
            case VIDEO_WITH_MOVIE_ID:
                return MoviesContract.VideoEntry.CONTENT_DIR_TYPE;
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                long _id = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = MoviesContract.MoviesEntry.buildMoviesWithIdUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);

                }
                break;
            }
            case VIDEO:
                long _id = db.insert(MoviesContract.VideoEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = MoviesContract.VideoEntry.buildVideosUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch (match) {
            case MOVIE: {
                numDeleted = db.delete(MoviesContract.MoviesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case VIDEO:
                numDeleted = db.delete(MoviesContract.VideoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numUpdate = 0;
        if (values == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                numUpdate = db.update(
                        MoviesContract.MoviesEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case MOVIE_WITH_ID: {
                numUpdate = db.update(
                        MoviesContract.MoviesEntry.TABLE_NAME,
                        values,
                        MoviesContract.MoviesEntry._ID + " = ? ",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case VIDEO: {
                numUpdate = db.update(
                        MoviesContract.VideoEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case VIDEO_WITH_ID: {
                numUpdate = db.update(
                        MoviesContract.VideoEntry.TABLE_NAME,
                        values,
                        MoviesContract.VideoEntry._ID + " = ? ",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        if (numUpdate > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numUpdate;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numInserted = 0;

        switch (match) {
            case MOVIE:
                numInserted = 0;
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long _id = -1;
                        try {
                            _id = db.insertOrThrow(MoviesContract.MoviesEntry.TABLE_NAME,
                                    null, value);
                        } catch (SQLiteConstraintException e) {
                            Log.w(LOG_TAG, "Attempting to insert " +
                                    value.getAsString(
                                            MoviesContract.MoviesEntry.ORIGINAL_TITLE)
                                    + " but value is already in database.");
                        }
                        if (_id != -1) {
                            numInserted++;
                        }
                    }
                    if (numInserted > 0) {
                        // If no errors, declare a successful transaction.
                        // database will not populate if this is not called
                        db.setTransactionSuccessful();
                    }

                } finally {
                    // all transactions occur at once
                    db.endTransaction();
                }
                if (numInserted > 0) {
                    // if there was successful insertion, notify the content resolver that there
                    // was a change
                    getContext().getContentResolver().notifyChange(uri, null);

                }
                return numInserted;
            case VIDEO:
                db.beginTransaction();
                numInserted = 0;
                try {
                    for (ContentValues value : values) {
                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long _id = -1;
                        try {
                            _id = db.insertOrThrow(MoviesContract.VideoEntry.TABLE_NAME,
                                    null, value);
                        } catch (SQLiteConstraintException e) {
                            Log.w(LOG_TAG, "Attempting to insert " +
                                    value.getAsString(
                                            MoviesContract.VideoEntry.ID)
                                    + " but value is already in database.");
                        }
                        if (_id != -1) {
                            numInserted++;
                        }
                    }
                    if (numInserted > 0) {
                        // If no errors, declare a successful transaction.
                        // database will not populate if this is not called
                        db.setTransactionSuccessful();
                    }

                } finally {
                    // all transactions occur at once
                    db.endTransaction();
                }
                if (numInserted > 0) {
                    // if there was successful insertion, notify the content resolver that there
                    // was a change
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return numInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }


}
