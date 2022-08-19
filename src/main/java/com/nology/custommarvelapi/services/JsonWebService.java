package com.nology.custommarvelapi.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JsonWebService {
    static JsonObject getJsonObjectFromUrl(String uri) throws IOException {
        URL url = new URL(uri);
        URLConnection request = url.openConnection();
        request.connect();
        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
        return root.getAsJsonObject();
    }

    static JsonArray getArrayObjectFromUrl(String uri) throws IOException {
        URL url = new URL(uri);
        URLConnection request = url.openConnection();
        request.connect();
        JsonElement root =JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
        return root.getAsJsonArray();
    }
}
