package com.project.stageone.movie.data;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.project.stageone.movie.data.MovieContract.MovieEntry;

@SuppressLint("Registered")
public class MovieContentProvider extends ContentProvider {

    // Tag for Log messages
    public static final String LOG_TAG = MovieContentProvider.class.getSimpleName();

    // URI matcher code for the content URI for the movies table
    private static final int MOVIES = 100;
    // URI matcher code for the content URI for a single item in the movies table
    private static final int MOVIE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE, MOVIES);
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE + "/#", MOVIE_ID);
    }

    // Database helper object
    private DbHelper mDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get readable database
        sqLiteDatabase = mDbHelper.getReadableDatabase();

        // Cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case MOVIE_ID:
                selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return MovieEntry.CONTENT_LIST_TYPE;
            case MOVIE_ID:
                return MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri mUri;
        long longId;
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                // Insert the new row, returning the primary key value of the new row
                longId = database.insert(MovieEntry.TABLE_NAME, null, values);
                if (longId == -1) {
                    return null;
                } else
                    mUri = ContentUris.withAppendedId(MovieEntry.CONTENT_URI, longId);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported Uri " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return mUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get writable database
        sqLiteDatabase = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                // Delete all rows that match the selection and selection args
                rowsDeleted = sqLiteDatabase.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_ID:
                // Delete a single row given by the ID in the URI
                selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = sqLiteDatabase.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        sqLiteDatabase = mDbHelper.getWritableDatabase();

        // Track the number of rows that were updated
        int rowsUpdated;
        switch (match) {
            case MOVIE_ID:
                selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = sqLiteDatabase.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported URI " + uri);
        }

        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}