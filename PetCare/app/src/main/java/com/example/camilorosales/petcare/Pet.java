package com.example.camilorosales.petcare;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Pet implements Serializable {
    public static final int CAT = 0;
    public static final int DOG = 1;

    private String mName;
    private int mTypeOfPet;
    private float mTemperature;
    private float mHeartRate;
    private long mTime; // Time of the last sample taken

    public Pet(String name, int typeOfPet){
        mName = name;
        mTypeOfPet = typeOfPet;
        mTime = -1;
    }

    public Pet(String name, int typeOfPet, float temperature, float hearRate) {
        mName = name;
        mTypeOfPet = typeOfPet;
        mTemperature = temperature;
        mHeartRate = hearRate;
    }

    public Pet(String name, int typeOfPet, float temperature, float hearRate, long time) {
        mName = name;
        mTypeOfPet = typeOfPet;
        mTemperature = temperature;
        mHeartRate = hearRate;
        mTime = time;
    }

    public String getName(){
        return mName;
    }

    public int getTypeOfPet(){
        return mTypeOfPet;
    }

    public void setName(String name){
        mName = name;
    }

    public float getTemperature() {
        return mTemperature;
    }

    public void setTemperature(float mTemperature) {
        this.mTemperature = mTemperature;
    }

    public float getHeartRate() {
        return mHeartRate;
    }

    public void setHeartRate(float mHeartRate) {
        this.mHeartRate = mHeartRate;
    }

    public long getTime() {
        return this.mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("name", this.mName);
        json.put("typeOfPet", this.mTypeOfPet);
        return json;
    }
}
