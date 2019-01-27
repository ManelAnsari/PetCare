package com.example.camilorosales.petcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PetDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Pet pet = (Pet) bundle.getSerializable("pet");

        TextView name = (TextView) findViewById(R.id.pet_details_name);
        name.setText(pet.getName());
    }
}
