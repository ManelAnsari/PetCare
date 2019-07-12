package com.example.camilorosales.petcare;

import java.io.Serializable;

public class Pet implements Serializable {
    public static final int CAT = 0;
    public static final int DOG = 1;

    private String mName;
    private int mTypeOfPet;
    private float mTemperature;
    private float mHeartRate;

    public Pet(String name, int typeOfPet){
        mName = name;
        mTypeOfPet = typeOfPet;
    }

    public Pet(String name, int typeOfPet, float temperature, float hearRate) {
        mName = name;
        mTypeOfPet = typeOfPet;
        mTemperature = temperature;
        mHeartRate = hearRate;
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
}
