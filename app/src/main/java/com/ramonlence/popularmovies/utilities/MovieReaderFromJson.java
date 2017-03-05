package com.ramonlence.popularmovies.utilities;

import com.ramonlence.popularmovies.entities.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ramon on 25/1/17.
 */

public class MovieReaderFromJson {
    public static ArrayList<Movie> readMovies(String jsonResponse){
        ArrayList<Movie> result = new ArrayList<Movie>();

        try {
            JSONObject obj = new JSONObject(jsonResponse);
            JSONArray resultsArray = obj.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++)
            {
                Movie movie = new Movie();
                String originalTitle = resultsArray.getJSONObject(i).getString("original_title");
                movie.setOriginal_title(originalTitle);
                String posterPath = resultsArray.getJSONObject(i).getString("poster_path");
                movie.setPoster_path(posterPath);
                String overview = resultsArray.getJSONObject(i).getString("overview");
                movie.setOverview(overview);
                Double voteAverage = resultsArray.getJSONObject(i).getDouble("vote_average");
                movie.setVote_average(voteAverage);
                String releaseDate = resultsArray.getJSONObject(i).getString("release_date");
                movie.setRelease_date(releaseDate);
                result.add(movie);
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return result;
    }
}
