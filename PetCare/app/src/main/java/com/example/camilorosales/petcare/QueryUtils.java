package com.example.camilorosales.petcare;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

public final class QueryUtils {
    private static final String TAG = "QueryUtils";

    public static Pet parseJsonPet(String jsonString) {
        Pet pet = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String petName = jsonObject.getString("name");
            int typeOfPet = jsonObject.getInt("typeOfPet");
            float temperature = (float) jsonObject.getDouble("temperature");
            float heartRate = (float) jsonObject.getDouble("heartRate");
            Log.d(TAG + "/parseJsonPet", "Temperature: " + temperature);
            Log.d(TAG + "/parseJsonPet", "Heart rate: " + heartRate);
            pet = new Pet(petName, typeOfPet, temperature, heartRate);
        } catch (JSONException e) {
            Log.e(TAG, "Couldn't parse json");
            e.printStackTrace();
        }
        return pet;
    }

    public static ArrayList<Pet> parseJsonPets(String jsonString) {
        ArrayList<Pet> pets = new ArrayList<Pet>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String petName = jsonObject.getString("name");
                int typeOfPet = jsonObject.getInt("typeOfPet");
                pets.add(new Pet(petName, typeOfPet));
            }
        } catch(JSONException error) {
            Log.e(TAG, "Couldn't parse json");
            error.printStackTrace();
        }
        return pets;
    }

    public static ArrayList<Pet> parseJsonPetInfo (String jsonString) {
        ArrayList<Pet> petInfo = new ArrayList<Pet>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject jsonPetObject = jsonObject.getJSONObject("pet");
                String name = jsonPetObject.getString("name");
                int typeOfPet = jsonPetObject.getInt("typeOfPet");
                float temperature = (float) jsonObject.getDouble("temperature");
                float heartBeat = (float) jsonObject.getDouble("heartRate");
                long time = jsonObject.getLong("date");
                petInfo.add(new Pet(name, typeOfPet, temperature, heartBeat, time));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Couldn't parse json");
            e.printStackTrace();
        }
        return petInfo;
    }

    public static String postRequest(String stringUrl, JSONObject body) {
        String jsonResponse = "";
        HttpURLConnection urlConnection= null;
        InputStream inputStream = null;
        Log.d("QueryUtils", "Connecting to: " + stringUrl);
        try {
            Log.d("QueryUtils", "Creating url");
            URL url = new URL(stringUrl);
            Log.d("QueryUtils", "Created url");
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.d("QueryUtils", "It's not url.openConnection()");
            urlConnection.setReadTimeout(20000);
            urlConnection.setConnectTimeout(20000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            //urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            // Writing body into the http request
            Log.d("QueryUtils", "Writing: " + body.toString());
            Log.d("QueryUtils", "" + body.toString().getBytes("utf-8"));
            OutputStream os = urlConnection.getOutputStream();
            byte[] input = body.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d("QueryUtils", "Response code OK");
                InputStream is = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                jsonResponse = response.toString();

                Log.d("QueryUtils", "It's not readFromStream()");
                Log.d("QueryUtils", "Response: " + jsonResponse);
                urlConnection.disconnect();
                inputStream.close();
                Log.d("QueryUtils", "It's not inputStream.close()");
                Log.d("QueryUtils", "Post request done successfully");
            }
            else {
                Log.d("QueryUtils", "Response code INTERNAL SERVER ERROR");
            }
        } catch (ProtocolException e) {
            Log.e("QueryUtils", "Protocol exception");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            Log.e("QueryUtils", "Malformed url exception");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("QueryUtils", "IOException");
            //Log.e("QueryUtils", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("QueryUtils", "Encode parameters error");
            e.printStackTrace();
        }

        Log.d("QueryUtils", "Json response:");
        Log.d("QueryUtils", jsonResponse);

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws  IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static String encodeParams(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }

}
