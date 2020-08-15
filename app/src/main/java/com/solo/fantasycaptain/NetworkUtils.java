package com.solo.fantasycaptain;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG =
            NetworkUtils.class.getSimpleName();
    // Base URL for Books API.
    //private static final String TEAM_BASE_URL =  "https://api-football-v1.p.rapidapi.com/v2/teams/search?";
    private static final String TEAM_BASE_URL =  "https://v2.api-football.com/topscorers?";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "printType";

    private static final String KEY = "X-RapidAPI-Key";
    //private static final String KEY = "0369dbcbf6fcb537eb1ff5bae1e33030";

    static String getTeamInfo(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String teamJSONString = null;
        //queryString="2";

        try {
            Uri builtURI = Uri.parse(TEAM_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, "2")//.appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(KEY, "0369dbcbf6fcb537eb1ff5bae1e33030")
                    .build();
            //convert the built URI to URL
            Log.d("The string url is ",builtURI.toString());
            URL requestURL = new URL(builtURI.toString());
            //Open the URL connection and make the request
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Create a buffered reader from that input stream.
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // Use a StringBuilder to hold the incoming response.
            StringBuilder builder = new StringBuilder();

            //Read the input line-by-line into the string while there is still input:
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                // Since it's JSON, adding a newline isn't necessary (it won't
                // affect parsing) but it does make debugging a *lot* easier
                // if you print out the completed buffer for debugging.
                builder.append("\n");
            }

            if (builder.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }

            teamJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, teamJSONString);

        return teamJSONString;

    }


}
