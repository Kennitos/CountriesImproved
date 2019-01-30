/*
CountryDetailActivity.java

This activity will display a country and characteristics side by side. On this activity
the weather of that country will be queried with the WeatherRequest. On this activity there is also
the option to compare the displayed country with another country. The user will be redirected to the
CompareActivity, but the displayed country will already be filled in the first editText.

@ author        Kennet Botan
*/

package com.example.kenne.countries.DetailActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenne.countries.Activity.CompareActivity;
import com.example.kenne.countries.Object.Country;
import com.example.kenne.countries.R;
import com.example.kenne.countries.Request.ScoreRequest;
import com.example.kenne.countries.Request.WeatherRequest;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CountryDetailActivity extends AppCompatActivity implements WeatherRequest.Callback{

    // Create the variables that will be used through the whole activity;
    Country selected;
    ArrayList<Country> COUNTRIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        // Get intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("country_name");

        // Assign the 'selected' Country whose characteristics is going to be displayed
        selected= (Country) intent.getSerializableExtra("selected_country");
        COUNTRIES =  (ArrayList) intent.getStringArrayListExtra("countries");

        // Assign the TextView's
        TextView nameView = findViewById(R.id.countryNameView);
        TextView capitalView = findViewById(R.id.capitalOutputView);
        TextView populationView = findViewById(R.id.populationOutputView);
        TextView areaView = findViewById(R.id.areaOutputView);
        TextView languagesView = findViewById(R.id.languageOutputView);
        TextView regionView = findViewById(R.id.regionOutputView);
        TextView subregionView = findViewById(R.id.subregionOutputView);
        ImageView image_view = findViewById(R.id.imageView);

        // Fill in the data
        nameView.setText(selected.getName());
        capitalView.setText(selected.getCapital());
        languagesView.setText(selected.getLanguages().toString());
        regionView.setText(selected.getRegion());
        subregionView.setText(selected.getSubregion());
        if(selected.getPopulation()==0){
            // If the population is 0, display "not available"
            populationView.setText("not available");
        } else {
            // NumberFormat.getIntegerInstance().format() will format digits to digits with separators
            // for units of thousands, example below:
            // 29.649.304 if your phone settings are European country (except UK)
            // 29,649,304 if your phone settings are in US, UK, Australia
            populationView.setText(NumberFormat.getIntegerInstance().format(selected.getPopulation()));
        }

        if(selected.getArea()==0){
            areaView.setText("not available");
        } else {
            String area = NumberFormat.getIntegerInstance().format(selected.getArea())+" km²";
            areaView.setText(area);
        }

        // Load the image of the selected country
        String img_url = "https://raw.githubusercontent.com/djaiss/mapsicon/master/all/"+selected.getIso()+"/512.png";
        Picasso.get().load(img_url).centerCrop().resize(512, 512).into(image_view);


        // Run the WeatherRequest for the selected country
        WeatherRequest x = new WeatherRequest(this);
        x.getWeather(this,selected.getLat(),selected.getLng());
    }


    @Override
    public void gotWeather(ArrayList<String> weatherList){
        // set the value of the weather for the country in the TextView
        TextView weatherView = findViewById(R.id.weatherOutputView);
        weatherView.setText(String.valueOf(weatherList.get(0)+", "+weatherList.get(1)));
    }

    @Override
    public void gotWeatherError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void compareSingleCountry(View view){
        // Create a new intent to go to the CompareActivity to compare the selected country with
        // another country (in that activity
        Intent intent = new Intent(this, CompareActivity.class);
        intent.putExtra("selected", selected);
        intent.putExtra("countries",COUNTRIES);
        startActivity(intent);
        finish();
    }
}
