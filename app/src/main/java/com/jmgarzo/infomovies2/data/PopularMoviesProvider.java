package com.jmgarzo.infomovies2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by jmgarzo on 13/03/17.
 */

public class PopularMoviesProvider extends ContentProvider {

    private final String LOG_TAG = PopularMoviesProvider.class.getSimpleName();

    static final int MOVIE = 100;
    static final int MOVIE_WITH_ID = 101;

    static final int TRAILER = 200;
    static final int TRAILER_WITH_ID = 201;

    static final int REVIEW = 300;
    static final int REVIEW_WITH_ID = 301;

    private PopularMoviesDBHelper mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(PopularMovieContract.CONTENT_AUTHORITY, PopularMovieContract.PATH_MOVIE, MOVIE);
        matcher.addURI(PopularMovieContract.CONTENT_AUTHORITY, PopularMovieContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);

        matcher.addURI(PopularMovieContract.CONTENT_AUTHORITY, PopularMovieContract.PATH_TRAILER, TRAILER);
        matcher.addURI(PopularMovieContract.CONTENT_AUTHORITY, PopularMovieContract.PATH_TRAILER + "/#", TRAILER_WITH_ID);

        matcher.addURI(PopularMovieContract.CONTENT_AUTHORITY, PopularMovieContract.PATH_REVIEW, REVIEW);
        matcher.addURI(PopularMovieContract.CONTENT_AUTHORITY, PopularMovieContract.PATH_REVIEW + "/#", REVIEW_WITH_ID);
        return matcher;

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new PopularMoviesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor returnCursor;

        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                returnCursor = mOpenHelper.getReadableDatabase().query(
                        PopularMovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MOVIE_WITH_ID: {
                returnCursor = mOpenHelper.getReadableDatabase().query(
                        PopularMovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        PopularMovieContract.MovieEntry._ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case TRAILER: {
                returnCursor = mOpenHelper.getReadableDatabase().query(
                        PopularMovieContract.TrailerEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TRAILER_WITH_ID: {
                returnCursor = mOpenHelper.getReadableDatabase().query(
                        PopularMovieContract.TrailerEntry.TABLE_NAME,
                        projection,
                        PopularMovieContract.TrailerEntry._ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case REVIEW: {
                returnCursor = mOpenHelper.getReadableDatabase().query(
                        PopularMovieContract.ReviewEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case REVIEW_WITH_ID: {
                returnCursor = mOpenHelper.getReadableDatabase().query(
                        PopularMovieContract.ReviewEntry.TABLE_NAME,
                        projection,
                        PopularMovieContract.ReviewEntry._ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                long id = db.insert(PopularMovieContract.MovieEntry.TABLE_NAME,
                        null,
                        values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(PopularMovieContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            case TRAILER: {
                long id = db.insert(PopularMovieContract.TrailerEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(PopularMovieContract.TrailerEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            case REVIEW: {
                long id = db.insert(PopularMovieContract.ReviewEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(PopularMovieContract.ReviewEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numDeleted = 0;

        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                numDeleted = db.delete(
                        PopularMovieContract.MovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            case MOVIE_WITH_ID: {
                numDeleted = db.delete(PopularMovieContract.MovieEntry.TABLE_NAME,
                        PopularMovieContract.MovieEntry._ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)});
                break;
            }

            case TRAILER: {
                numDeleted = db.delete(
                        PopularMovieContract.TrailerEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            case TRAILER_WITH_ID: {
                numDeleted = db.delete(PopularMovieContract.TrailerEntry.TABLE_NAME,
                        PopularMovieContract.TrailerEntry._ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)});
                break;
            }

            case REVIEW: {
                numDeleted = db.delete(
                        PopularMovieContract.ReviewEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            case REVIEW_WITH_ID: {
                numDeleted = db.delete(PopularMovieContract.ReviewEntry.TABLE_NAME,
                        PopularMovieContract.ReviewEntry._ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)});
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return numDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (values == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }
        int numUpdates = 0;

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                numUpdates = db.update(PopularMovieContract.MovieEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case MOVIE_WITH_ID: {
                numUpdates = db.update(PopularMovieContract.MovieEntry.TABLE_NAME,
                        values,
                        PopularMovieContract.MovieEntry._ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)});
                break;
            }
            case TRAILER: {
                numUpdates = db.update(PopularMovieContract.TrailerEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case TRAILER_WITH_ID: {
                numUpdates = db.update(PopularMovieContract.TrailerEntry.TABLE_NAME,
                        values,
                        PopularMovieContract.TrailerEntry._ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)});
                break;
            }
            case REVIEW: {
                numUpdates = db.update(PopularMovieContract.ReviewEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case REVIEW_WITH_ID: {
                numUpdates = db.update(PopularMovieContract.ReviewEntry.TABLE_NAME,
                        values,
                        PopularMovieContract.ReviewEntry._ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)});
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return numUpdates;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                return PopularMovieContract.MovieEntry.CONTENT_DIR_TYPE;
            case MOVIE_WITH_ID:
                return PopularMovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            case TRAILER:
                return PopularMovieContract.TrailerEntry.CONTENT_DIR_TYPE;
            case TRAILER_WITH_ID:
                return PopularMovieContract.TrailerEntry.CONTENT_ITEM_TYPE;
            case REVIEW:
                return PopularMovieContract.ReviewEntry.CONTENT_DIR_TYPE;
            case REVIEW_WITH_ID:
                return PopularMovieContract.ReviewEntry.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numInserted = 0;

        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long id = db.insert(PopularMovieContract.MovieEntry.TABLE_NAME, null, value);
                        if (id != -1) {
                            numInserted++;
                        }
                    }
                    db.setTransactionSuccessful();

                } finally {
                    db.endTransaction();
                }

                if (numInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            }

            case TRAILER: {
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long id = db.insert(PopularMovieContract.TrailerEntry.TABLE_NAME, null, value);
                        if (id != -1) {
                            numInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                break;
            }

            case REVIEW: {
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long id = db.insert(PopularMovieContract.ReviewEntry.TABLE_NAME, null, value);
                        if (id != -1) {
                            numInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                break;
            }


            default:
                return super.bulkInsert(uri, values);

        }

        if (numInserted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numInserted;
    }
}
