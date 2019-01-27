package com.example.camilorosales.petcare;

import java.io.Serializable;

public class Pet implements Serializable {
    public static final int CAT = 0;
    public static final int DOG = 1;

    private String mName;
    private int mTypeOfPet;

    public Pet(String name, int typeOfPet){
        mName = name;
        mTypeOfPet = typeOfPet;
    }

    public String getName(){
        return mName;
    }

    public int getTypeOfPet(){
        return mTypeOfPet;
    }
}
