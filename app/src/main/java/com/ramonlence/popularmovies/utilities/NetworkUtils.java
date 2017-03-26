package com.ramonlence.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ramon on 24/1/17.
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

        private static final String MOVIES_BASE_URL =
            "http://api.themoviedb.org/3/movie/";

    private static final String MOVIES_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    private static final String PRIVATE_AUTH_KEY = "yourapikeyhere";


    public static URL buildUrl(String queryPath) {
        if(queryPath != "") {
            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendPath(queryPath)
                    .appendQueryParameter("api_key", PRIVATE_AUTH_KEY)
                    .build();

            URL url = null;
            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Log.v(TAG, "Built URI " + url);

            return url;
        } else {
            return null;
        }
    }

    public static URL buildImageUrl(String imagePath, String size){
        if(imagePath != "" && size !="") {
            String imageFolder = "";
            if("normal"==size){
                imageFolder = "w780";
            } else if("small"==size){
                imageFolder = "w342";
            }
            Uri builtUri = Uri.parse(MOVIES_IMAGE_BASE_URL).buildUpon()
                    .appendPath(imageFolder)
                    .appendEncodedPath(imagePath)
                    .build();

            URL url = null;
            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Log.v(TAG, "Built URI " + url);

            return url;
        } else {
            return null;
        }
    }

    public static URL buildExtraUrl(int movieId, String extraType){
        if(movieId > 0){
            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendPath(""+movieId)
                    .appendPath(extraType)
                    .appendQueryParameter("api_key", PRIVATE_AUTH_KEY)
                    .build();
            URL url = null;
            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return url;
        } else {
            return null;
        }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
