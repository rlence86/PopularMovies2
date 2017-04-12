package com.ramonlence.popularmovies.utilities;

import com.ramonlence.popularmovies.data.FavoriteMovieContract;
import com.ramonlence.popularmovies.entities.Movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

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

    public ArrayList<Movie> getAllFavoriteMovies(Context context){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        Cursor cursor = context.getContentResolver().query(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        try {
            while (cursor.moveToNext()) {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID)));
                movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_TITLE)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_POSTERPATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW)));
                movie.setVote_average(cursor.getDouble(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_AVERAGE)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_RELEASEDATE)));
                movies.add(movie);
            }
        } finally {
            cursor.close();
        }
        return movies;
    }
}
