package com.example.kenne.countries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements CountriesRequest.Callback{

    ArrayList<Country> allCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CountriesRequest x = new CountriesRequest(this);
        x.getCountries(this);
        Toast.makeText(this,"started",Toast.LENGTH_LONG).show();
    }

    @Override
    public void gotCountries(ArrayList<Country> countries) {
        Log.d("connect_test","Runt gotCountries");
        try {
            Log.d("connect_test","Everything loaded in! -name: "+countries.get(249)+' '
                    +countries.get(249).getArea());
            Toast.makeText(this,"All countries loaded in!",Toast.LENGTH_LONG).show();
            allCountries = countries;

        } catch (Error e){
            Log.d("connect_test","Empty");
        }
    }

    @Override
    public void gotCountriesError(String message){
        Log.d("connect_test","Runt response error");
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

//    FUNCTIONS FOR THE FOUR BUTTONS
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

//    public void goToScoresActivity(View view){
//        Intent intent = new Intent(this, ScoresActivity.class);
//        intent.putExtra("countries",allCountries);
//        startActivity(intent);
//    }
}
