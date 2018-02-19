package com.project.stageone.movie.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {

    static final String CONTENT_AUTHORITY = "com.project.stageone.movie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    static final String PATH_MOVIE = "movies";


    // Empty Constructor
    MovieContract(){
        throw new AssertionError("No MovieContract instances for you!");
    }

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIE);

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";

        static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
    }
}