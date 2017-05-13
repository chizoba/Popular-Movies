package com.github.chizoba.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.github.chizoba.popularmovies.Constants;
import com.github.chizoba.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3";

    static String SORT_BY;

    final static String PARAM_QUERY = "api_key";

    /**
     * Builds the URL used to query themoviedb.
     *
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(int id) {
        if (id == R.id.sort_by_popular) {
            SORT_BY = "popular";
        }
        if (id == R.id.sort_by_top_rated){
            SORT_BY = "top_rated";
        }

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(SORT_BY)
                .appendQueryParameter(PARAM_QUERY, Constants.API_KEY)
                .build();

//        Uri builtUri = new Uri.Builder().scheme("http")
//                .authority("api.themoviedb.org")
//                .appendPath("3")
//                .appendPath(SORT_BY)
//                .appendQueryParameter(PARAM_QUERY, Constants.API_KEY)
//                .build();
//
//                Uri.parse(MOVIE_DB_BASE_URL+SORT_BY).buildUpon()
//                .appendQueryParameter(PARAM_QUERY, Constants.API_KEY)
//                .build();
        Log.d("BUILDURL", builder.toString());
        URL url = null;
        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildUrl(String id, String type) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(id)
                .appendPath(type)
                .appendQueryParameter(PARAM_QUERY, Constants.API_KEY)
                .build();

        Log.d("BUILDURL", builder.toString());
        URL url = null;
        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
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