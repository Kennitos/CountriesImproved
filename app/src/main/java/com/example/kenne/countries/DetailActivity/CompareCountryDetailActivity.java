package com.example.kenne.countries.DetailActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kenne.countries.Object.Country;
import com.example.kenne.countries.R;
import com.example.kenne.countries.Request.WeatherRequest;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CompareCountryDetailActivity extends AppCompatActivity implements WeatherRequest.Callback{

    ArrayList<ArrayList<String>> multi = new ArrayList<>();
    int State = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_country_detail);

        Intent intent = getIntent();
        Country selected1 = (Country) intent.getSerializableExtra("selected1");
        Country selected2 = (Country) intent.getSerializableExtra("selected2");

        TextView name1 = findViewById(R.id.nameView1);
        TextView region1 = findViewById(R.id.regionView1);
        TextView subregion1  = findViewById(R.id.subregionView1);
        TextView capital1 = findViewById(R.id.capitalView1);
        TextView population1 = findViewById(R.id.populationView1);
        TextView area1 = findViewById(R.id.areaView1);
        TextView language1 = findViewById(R.id.languageView1);
        ImageView map1 = findViewById(R.id.mapView1);

        TextView name2 = findViewById(R.id.nameView2);
        TextView region2 = findViewById(R.id.regionView2);
        TextView subregion2  = findViewById(R.id.subregionView2);
        TextView capital2 = findViewById(R.id.capitalView2);
        TextView population2 = findViewById(R.id.populationView2);
        TextView area2 = findViewById(R.id.areaView2);
        TextView language2 = findViewById(R.id.languageView2);
        ImageView map2 = findViewById(R.id.mapView2);


        // Fill in data for country 1
        name1.setText(selected1.getName());
        region1.setText(selected1.getRegion());
        subregion1.setText(selected1.getSubregion());
        capital1.setText(selected1.getCapital());
        language1.setText(selected1.getLanguages().toString());

        String img_url1 = "https://raw.githubusercontent.com/djaiss/mapsicon/master/all/"+selected1.getIso()+"/512.png";
        Picasso.get().load(img_url1).centerCrop().resize(512, 512).into(map1);
        if(selected1.getPopulation()==0){
            population1.setText("not available");
        } else {
            population1.setText(NumberFormat.getIntegerInstance().format(selected1.getPopulation()));
        }

        if(selected1.getArea()==0){
            area1.setText("not available");
        } else {
            String area = NumberFormat.getIntegerInstance().format(selected1.getArea())+" km²";
            area1.setText(area);
        }

        // Fill in data for country 2
        name2.setText(selected2.getName());
        region2.setText(selected2.getRegion());
        subregion2.setText(selected2.getSubregion());
        capital2.setText(selected2.getCapital());
        language2.setText(selected2.getLanguages().toString());
        String img_url2 = "https://raw.githubusercontent.com/djaiss/mapsicon/master/all/"+selected2.getIso()+"/512.png";
        Picasso.get().load(img_url2).centerCrop().resize(512, 512).into(map2);
        if(selected2.getPopulation()==0){
            population2.setText("not available");
        } else {
            population2.setText(NumberFormat.getIntegerInstance().format(selected2.getPopulation()));
        }

        if(selected2.getArea()==0){
            area2.setText("not available");
        } else {
            String area = NumberFormat.getIntegerInstance().format(selected2.getArea())+" km²";
            area2.setText(area);
        }

        WeatherRequest x = new WeatherRequest(this);
        x.getWeather(this,selected1.getLat(),selected1.getLng());

        WeatherRequest y = new WeatherRequest(this);
        y.getWeather(this,selected2.getLat(),selected2.getLng());

        Log.d("check_weather","list_1:"+multi+x);
    }

    @Override
    public void gotWeather(ArrayList<String> weatherList){

        Log.d("check_weather","list_x:"+weatherList);
        multi.add(weatherList);
        Log.d("check_weather","list_added:"+multi);
        if(State==0){
            TextView weather1View = findViewById(R.id.weatherView1);
            weather1View.setText(String.valueOf(weatherList.get(0)+", "+weatherList.get(1)));
        } else {
            TextView weather2View = findViewById(R.id.weatherView2);
            weather2View.setText(String.valueOf(weatherList.get(0)+", "+weatherList.get(1)));
        }
        State+=1;
//        TextView weatherView = findViewById(R.id.weatherOutputView);
//        weatherView.setText(String.valueOf(weatherList.get(0)+", "+weatherList.get(1)));
    }

    @Override
    public void gotWeatherError(String message) {
        Log.d("check_response","cd"+message);
    }


}
