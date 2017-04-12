package com.ramonlence.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ramon on 11/4/17.
 */

public class FavoriteMovieContract {

    public static final String CONTENT_AUTHORITY = "com.ramonlence.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVORITES = "favorites";

    public static final class FavoriteMovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITES)
                .build();

        public static final String TABLE_NAME = "favoritemovies";
        public static final String COLUMN_MOVIE_ID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTERPATH = "posterpath";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_AVERAGE = "vote_average";
        public static final String COLUMN_RELEASEDATE = "release_date";
    }

}
