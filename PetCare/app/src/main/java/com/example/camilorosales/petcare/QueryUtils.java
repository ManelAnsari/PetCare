package com.example.camilorosales.petcare;

import org.json.JSONException;
import org.json.JSONObject;

public final class QueryUtils {
    public static Pet parseJson(String jsonString){
        Pet pet = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String petName = jsonObject.getString("name");
            int typeOfPet = jsonObject.getInt("typeOfPet");
            pet = new Pet(petName, typeOfPet);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pet;
    }
}
