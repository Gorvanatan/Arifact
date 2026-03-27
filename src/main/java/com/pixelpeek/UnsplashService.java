package com.pixelpeek;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class UnsplashService {

    // Replace this with your free Unsplash Access Key from https://unsplash.com/developers
    private static final String ACCESS_KEY = "klT49diqKn8qM6PKCgIZNdZEGptR48WvVZsJjPhJm30";
    private static final String BASE_URL = "https://api.unsplash.com/search/photos";

    public static PhotoData search(String query) throws Exception {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = BASE_URL + "?query=" + encodedQuery + "&per_page=30&client_id=" + ACCESS_KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 401) {
            throw new Exception("Invalid API key. Check your Unsplash Access Key in UnsplashService.java");
        }
        if (response.statusCode() != 200) {
            throw new Exception("API error: HTTP " + response.statusCode());
        }

        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray results = root.getAsJsonArray("results");

        if (results == null || results.size() == 0) {
            throw new Exception("No photos found for \"" + query + "\". Try a different word.");
        }

        JsonObject photo = results.get(new Random().nextInt(results.size())).getAsJsonObject();

        String imageUrl = photo.getAsJsonObject("urls").get("regular").getAsString();
        int width = photo.get("width").getAsInt();
        int height = photo.get("height").getAsInt();
        String color = photo.get("color").getAsString();

        // Description fallback chain
        String description = "No description available.";
        if (!photo.get("description").isJsonNull()) {
            description = photo.get("description").getAsString();
        } else if (!photo.get("alt_description").isJsonNull()) {
            description = photo.get("alt_description").getAsString();
        }

        String photographerName = photo.getAsJsonObject("user").get("name").getAsString();
        String photographerUrl = photo.getAsJsonObject("user")
                .getAsJsonObject("links").get("html").getAsString();

        return new PhotoData(imageUrl, width, height, color,
                description, photographerName, photographerUrl, query);
    }
}