package com.project.stageone.movie.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.stageone.movie.data.MovieContract.MovieEntry;

public class DbHelper extends SQLiteOpenHelper {


    // Name of the database file
    private static final String DATABASE_NAME = "mMovies.db";

    // Database version
    private static final int DATABASE_VERSION = 4;


    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the products table
        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " ("
                + MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, "
                + MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "
                + MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
