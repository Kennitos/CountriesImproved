/*
HomeActivity.java

This is the first activity the user will see when opening the application. In this activity

@ author        Kennet Botan
*/

package com.example.kenne.countries.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kenne.countries.Object.Country;
import com.example.kenne.countries.R;
import com.example.kenne.countries.Request.CountriesRequest;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements CountriesRequest.Callback{

    // Create the variables that will be used through the whole activity;
    public static ArrayList<Country> allCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initiate the process to load all the countries from the API
        CountriesRequest x = new CountriesRequest(this);
        x.getCountries(this);
        Toast.makeText(this,"started",Toast.LENGTH_LONG).show();
    }

    // Load the countries
    @Override
    public void gotCountries(ArrayList<Country> countries) {
        try {
            Toast.makeText(this,"All countries loaded in!",Toast.LENGTH_LONG).show();
            // Assign the all the instances of the class Country to the variable
            allCountries = countries;
        } catch (Error e){
            e.printStackTrace();
            Log.d("connect_test","Empty");
        }
    }

    @Override
    public void gotCountriesError(String message){
        Log.d("connect_test","Runt response error");
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    // The four main buttons on the HomeActivity. Start a new intent for every button (if clicked)
    // and send all the assigned countries with it.
    public void goToSearchActivity(View view){
        Intent intent = new Intent(this,SearchActivity.class);
        intent.putExtra("countries",allCountries);
        startActivity(intent);
    }

    public void goToCompareActivity(View view){
        Intent intent = new Intent(this, CompareActivity.class);
        intent.putExtra("countries",allCountries);
        startActivity(intent);
    }

    public void goToRegionCharActivity(View view){
        Intent intent = new Intent(this, RegionCharActivity.class);
        intent.putExtra("countries",allCountries);
        startActivity(intent);
    }

    public void goToScoresActivity(View view){
        Intent intent = new Intent(this, ScoresActivity.class);
        intent.putExtra("countries",allCountries);
        startActivity(intent);
    }
}
