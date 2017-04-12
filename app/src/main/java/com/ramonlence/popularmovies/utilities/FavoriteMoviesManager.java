package com.ramonlence.popularmovies.utilities;

import com.ramonlence.popularmovies.data.FavoriteMovieContract;
import com.ramonlence.popularmovies.entities.Movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by ramon on 12/4/17.
 */

public class FavoriteMoviesManager {

    public boolean isMovieInFavorites(Movie movie, Context context) {
        String mSelectionClause = FavoriteMovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID + " = ?";
        String[] mSelectionArgs = {"" + movie.getId()};
        Cursor cursor = context.getContentResolver().query(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI,
                null,
                mSelectionClause,
                mSelectionArgs,
                null);
        cursor.moveToNext();
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addMovieToFavorites(Movie movie, Context context){
        ContentValues movieValues = new ContentValues();
        movieValues.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID, movie.getId());
        movieValues.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_TITLE, movie.getOriginal_title());
        movieValues.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_POSTERPATH, movie.getPoster_path());
        movieValues.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        movieValues.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_AVERAGE, movie.getVote_average());
        movieValues.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_RELEASEDATE, movie.getRelease_date());
        Uri result = context.getContentResolver().insert(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI, movieValues);
        if(result != null){
            return true;
        } else {
            return false;
        }
    }

    public boolean removeMovieFromFavorites(Movie movie, Context context){
        String mSelectionClause = FavoriteMovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID + " = ?";
        String[] mSelectionArgs = {"" + movie.getId()};
        int deletedRows = context.getContentResolver().delete(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI,
                mSelectionClause,
                mSelectionArgs);
        if(deletedRows > 0){
            return true;
        } else {
            return false;
        }
    }
}
