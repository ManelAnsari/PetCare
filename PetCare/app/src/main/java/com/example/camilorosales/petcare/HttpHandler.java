package com.example.camilorosales.petcare;

import android.util.Log;

import com.facebook.internal.Logger;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class HttpHandler {
    private static final String SERVER = "http://167.86.117.236:3001/";
    private static final String GET_PETS = "getPets";
    private static final String TAG = "HttpHandler";

    public static ArrayList<Pet> getPets() {
        String json = "";
        try {
            URL url = new URL(SERVER);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Scanner scanner = new Scanner(in).useDelimiter("\\A");
            json = scanner.hasNext() ? scanner.next() : "";
        } catch (MalformedURLException error) {
            Log.e(TAG, "Malformed URL");
            error.printStackTrace();
        } catch (IOException error) {
            Log.e(TAG, "IOException");
            error.printStackTrace();
        }

        return QueryUtils.parseJsonPets(json);
    }
}
