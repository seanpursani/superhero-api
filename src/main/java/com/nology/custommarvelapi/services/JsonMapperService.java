package com.nology.custommarvelapi.services;

import com.google.gson.JsonObject;

public class JsonMapperService {
    static String getStringFromJson(JsonObject jsonObject, String propertyName) {
        if (jsonObject.has(propertyName) && !jsonObject.get(propertyName).isJsonNull()) {
            return jsonObject.get(propertyName).getAsString();
        } else {
            return "";
        }
    }

    static int getIntFromJson(JsonObject jsonObject, String propertyName) {
        if (jsonObject.has(propertyName) && !jsonObject.get(propertyName).isJsonNull()) {
            return jsonObject.get(propertyName).getAsInt();
        } else {
            return -1;
        }
    }
}
