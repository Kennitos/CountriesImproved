package com.example.kenne.countries.DetailActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    Country selected;
    ArrayList<Country> COUNTRIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        Intent intent = getIntent();
        String name = intent.getStringExtra("country_name");
        selected= (Country) intent.getSerializableExtra("selected_country");
        COUNTRIES =  (ArrayList) intent.getStringArrayListExtra("countries");

        TextView nameView = findViewById(R.id.countryNameView);
        TextView capitalView = findViewById(R.id.capitalOutputView);
        TextView populationView = findViewById(R.id.populationOutputView);
        TextView areaView = findViewById(R.id.areaOutputView);
        TextView languagesView = findViewById(R.id.languageOutputView);
        TextView regionView = findViewById(R.id.regionOutputView);
        TextView subregionView = findViewById(R.id.subregionOutputView);
        ImageView image_view = findViewById(R.id.imageView);

        nameView.setText(selected.getName());
        capitalView.setText(selected.getCapital());
        languagesView.setText(selected.getLanguages().toString());
        regionView.setText(selected.getRegion());
        subregionView.setText(selected.getSubregion());
        if(selected.getPopulation()==0){
            populationView.setText("not available");
        } else {
            populationView.setText(NumberFormat.getIntegerInstance().format(selected.getPopulation()));
        }

        if(selected.getArea()==0){
            areaView.setText("not available");
        } else {
            String area = NumberFormat.getIntegerInstance().format(selected.getArea())+" km²";
            areaView.setText(area);
        }
//        NumberFormat.getIntegerInstance().format() will format 29649304 to >
//        29.649.304 if your phone settings are European country (except UK)
//        29,649,304 if your phone settings are in US, UK, Australia


        String img_url = "https://raw.githubusercontent.com/djaiss/mapsicon/master/all/"+selected.getIso()+"/512.png";
        Picasso.get().load(img_url).centerCrop().resize(512, 512).into(image_view);
        //        Log.d("image_test","url: "+img_url);


        WeatherRequest x = new WeatherRequest(this);
        x.getWeather(this,selected.getLat(),selected.getLng());
    }

    @Override
    public void gotWeather(ArrayList<String> weatherList){
        Log.d("check_weather","list:"+weatherList);
        TextView weatherView = findViewById(R.id.weatherOutputView);
        weatherView.setText(String.valueOf(weatherList.get(0)+", "+weatherList.get(1)));
    }

    @Override
    public void gotWeatherError(String message) {
        Log.d("check_response","cd"+message);
    }

    public void compareSingleCountry(View view){
        Intent intent = new Intent(this, CompareActivity.class);
        intent.putExtra("selected", selected);
        intent.putExtra("countries",COUNTRIES);
        startActivity(intent);
        finish();
    }
}
