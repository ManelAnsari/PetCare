package com.example.camilorosales.petcare;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PetAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AccessToken.getCurrentAccessToken() == null){
            goLoginScreen();
        }

        RecyclerView recyclerView = findViewById(R.id.recyler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new PetAdapter(this, new ArrayList<Pet>());


        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        PetViewModel viewModel = ViewModelProviders.of(this).get(PetViewModel.class);
        viewModel.getPet().observe(this, new Observer<Pet>() {
            @Override
            public void onChanged(@Nullable Pet pet) {
                //update ui
                Log.d("MainActivityViewModel", "pets changed");
                mAdapter.update(pet);
            }
        });


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


}
