package com.example.kenne.countries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private static ArrayList<String> allNames;
//    private static String[] COUNTRIES = new String[]{};
//    private static String[] COUNTRIES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        ArrayList<Country> countriesArray = (ArrayList) intent.getStringArrayListExtra("countries");
        Log.d("connect_test","all countries:"+countriesArray.get(0).getName());

        for(int i = 0; i < countriesArray.size(); i++){
            allNames.add(countriesArray.get(i).getName());
        }
    }

    public void goToCountryDetail(View view){
        Intent intent = new Intent(this, CountryDetailActivity.class);
        startActivity(intent);
    }
}
