package com.ramonlence.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by ramon on 11/4/17.
 */

public class FavoriteMovieContract {

    public static final class FavoriteMovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favoritemovies";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTERPATH = "posterpath";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_AVERAGE = "vote_average";
        public static final String COLUMN_RELEASEDATE = "release_date";
    }

}
