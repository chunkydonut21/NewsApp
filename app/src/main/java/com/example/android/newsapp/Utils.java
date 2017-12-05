package com.example.android.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.newsapp.MainActivity.LOG_TAG;

public class Utils {

    private static URL createUrl(String stringUrl) throws MalformedURLException {
        URL url = new URL(stringUrl);
        return url;
    }

    private static String makeHttpConnection(URL url) throws IOException {

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String response = "";
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                response = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFromJson(String responseJson) throws JSONException {

        List<News> news = new ArrayList<>();

        String section;
        String title;
        String url;

        JSONObject root = new JSONObject(responseJson);
        JSONObject responseObject = root.getJSONObject("response");
        JSONArray results = responseObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {

            if (results.getJSONObject(i).has("sectionName")) {
                section = results.getJSONObject(i).getString("sectionName");
            } else {
                section = "";
            }

            if (results.getJSONObject(i).has("webTitle")) {
                title = results.getJSONObject(i).getString("webTitle");
            } else {
                title = "";
            }
            if (results.getJSONObject(i).has("webUrl")) {
                url = results.getJSONObject(i).getString("webUrl");
            } else {
                url = "";
            }

            JSONObject fieldObject = results.getJSONObject(i).getJSONObject("fields");
            String icon = fieldObject.getString("thumbnail");
            News newsObject = new News(section, title, url, icon);
            news.add(newsObject);
        }
        return news;
    }

    public static List<News> fetchNewsData(String stringUrl) throws JSONException, IOException {

        URL url = createUrl(stringUrl);
        String jsonResponse = makeHttpConnection(url);
        List<News> newsList = extractFromJson(jsonResponse);

        return newsList;

    }
}
