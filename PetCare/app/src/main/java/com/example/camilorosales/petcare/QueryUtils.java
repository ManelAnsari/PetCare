package com.example.camilorosales.petcare;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public final class QueryUtils {
    public static Pet parseJson(String jsonString){
        Pet pet = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String petName = jsonObject.getString("name");
            int typeOfPet = jsonObject.getInt("typeOfPet");
            float temperature = (float) jsonObject.getDouble("temperature");
            float heartRate = (float) jsonObject.getDouble("heartRate");
            pet = new Pet(petName, typeOfPet, temperature, heartRate);
        } catch (JSONException e) {
            Log.e("QueryUtils", "Couldn't parse json");
            e.printStackTrace();
        }
        return pet;
    }
}
