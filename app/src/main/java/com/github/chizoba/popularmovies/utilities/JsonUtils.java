/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.chizoba.popularmovies.utilities;

import android.content.ContentValues;
import android.content.Context;

import com.github.chizoba.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class JsonUtils {

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the movies in a database.
     * <p/>
     * Later on, we'll be parsing the JSON into structured data within the
     * getStringsFromJson function, leveraging the data we have stored in the JSON. For
     * now, we just convert the JSON into human-readable strings.
     *
     * @param jsonString JSON response from server
     * @return Array of Strings describing weather data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static ArrayList<Movie> getStringsFromJson(Context context, String jsonString)
            throws JSONException {

        /* Movies information. Each movie's info is an element of the "results" array */
        final String MOVIE_LIST = "results";

        /* A Movie info */
        final String MOVIE_POSTER_PATH = "poster_path";
        final String ORIGINAL_TITLE = "original_title";
        final String PLOT_SYNOPSIS = "overview";
        final String USER_RATING = "vote_average";
        final String RELEASE_DATE = "release_date";

        /* String array to hold each movie's details String */
        ArrayList<Movie> parsedUserData = null;

        JSONObject movieJson = new JSONObject(jsonString);

        JSONArray movieArray = movieJson.getJSONArray(MOVIE_LIST);

        parsedUserData = new ArrayList<>(movieArray.length());

        for (int i = 0; i < movieArray.length(); i++) {

            /* Get the JSON object representing the movie */
            JSONObject movie = movieArray.getJSONObject(i);

            /* These are the values that will be collected */
            String poster_path = movie.getString(MOVIE_POSTER_PATH);
            String original_title = movie.getString(ORIGINAL_TITLE);
            String plot_synopsis = movie.getString(PLOT_SYNOPSIS);
            String user_rating = movie.getString(USER_RATING);
            String release_date = movie.getString(RELEASE_DATE);

            parsedUserData.add(new Movie(poster_path, original_title, plot_synopsis, user_rating, release_date));

        }

        return parsedUserData;
    }

}