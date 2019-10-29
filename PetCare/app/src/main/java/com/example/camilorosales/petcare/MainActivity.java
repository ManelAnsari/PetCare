package com.example.camilorosales.petcare;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.Loader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Pet>> {

    private PetAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private PetViewModel mPetViewModel;
    private String mUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AccessToken.getCurrentAccessToken() == null){
            goLoginScreen();
        } else {
            // Get user email
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.v("LoginActivity", response.toString());
                    mUserEmail = object.optString("email");
                    mAdapter.setEmail(mUserEmail);
                    mPetViewModel = ViewModelProviders.of(
                            MainActivity.this, new PetViewModelFactory(getApplication(), mUserEmail)
                    ).get(PetViewModel.class);
                    getLoaderManager().initLoader(0, null, MainActivity.this);

                    mPetViewModel.getPet().observe(MainActivity.this, new Observer<Pet>() {
                        @Override
                        public void onChanged(@Nullable Pet pet) {
                            //update ui
                            Log.d("MainActivityViewModel", "pets changed");
                            Log.d("MainActivityViewModel", "Temperature: " + pet.getTemperature());
                            Log.d("MainActivityViewModel", "Heart rate: " + pet.getHeartRate());
                            mAdapter.update(pet);
                        }
                    });
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, name, email, gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }


        RecyclerView recyclerView = findViewById(R.id.recyler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new PetAdapter(this, new ArrayList<Pet>());


        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pet_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logout:
                // Add code for logging out
                Log.d(this.getLocalClassName(), "logging out");
                LoginManager.getInstance().logOut();
                goLoginScreen();

                return true;
        }

        return false;
    }

    private void goLoginScreen(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public Loader<ArrayList<Pet>> onCreateLoader(int id, Bundle args) {
        try {
            JSONObject bodyJson = new JSONObject();
            JSONObject ownerJson = new JSONObject();
            Log.d("MainActivity", "email: " + this.mUserEmail);
            ownerJson.put("email", this.mUserEmail);
            Log.d("MainActivity", ownerJson.toString());
            bodyJson.put("owner", ownerJson);
            String bodyString = bodyJson.toString();
            Log.d("MainActivity", bodyString);
            return new PetLoader(MainActivity.this, "http://167.86.117.236:3001/api/getPets", bodyJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<Pet>> loader, ArrayList<Pet> pets) {
        for (Pet pet : pets) {
            this.mAdapter.update(pet);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<Pet>> loader) {

    }
}
