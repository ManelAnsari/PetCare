package com.example.camilorosales.petcare;

import android.content.Context;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;

import org.json.JSONObject;

import java.util.ArrayList;

public class PetLoader extends AsyncTaskLoader<ArrayList<Pet>> {
    private String mUrl;
    private JSONObject mBody;
    private Context context;


    public PetLoader(Context context, String url, JSONObject body) {
        super(context);
        this.mUrl = url;
        this.mBody = body;
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        this.forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Pet> loadInBackground() {
        if (mUrl == null) {
            return new ArrayList<Pet>();
        }
        String json = QueryUtils.postRequest(this.mUrl, this.mBody);
        return this.context instanceof MainActivity ? QueryUtils.parseJsonPets(json) : QueryUtils.parseJsonPetInfo(json);
    }
}