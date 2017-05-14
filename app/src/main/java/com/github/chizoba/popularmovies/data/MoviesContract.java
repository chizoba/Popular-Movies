package com.github.chizoba.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Chizoba on 5/9/2017.
 */

public class MoviesContract {

    public static final String AUTHORITY = "com.github.chizoba.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "movies";


    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_POSTER = "moviePoster";
        public static final String COLUMN_ORIGINAL_TITLE = "movieTitle";
        public static final String COLUMN_PLOT_SYNOPSIS = "plotSynopsis";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_USER_RATING = "userRating";
        public static final String COLUMN_MOVIE_ID = "movieId";
    }
}
