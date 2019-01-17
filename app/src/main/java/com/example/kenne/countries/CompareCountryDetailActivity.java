package com.example.kenne.countries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class CompareCountryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_country_detail);

        Intent intent = getIntent();
        Country selected1 = (Country) intent.getSerializableExtra("selected1");
        Country selected2 = (Country) intent.getSerializableExtra("selected2");

        TextView name1 = findViewById(R.id.nameView1);
        TextView capital1 = findViewById(R.id.capitalView1);
        TextView population1 = findViewById(R.id.populationView1);
        TextView area1 = findViewById(R.id.areaView1);
        ImageView map1 = findViewById(R.id.mapView1);

        TextView name2 = findViewById(R.id.nameView2);
        TextView capital2 = findViewById(R.id.capitalView2);
        TextView population2 = findViewById(R.id.populationView2);
        TextView area2 = findViewById(R.id.areaView2);
        ImageView map2 = findViewById(R.id.mapView2);


        // Fill in data for country 1
        name1.setText(selected1.getName());
        capital1.setText(selected1.getCapital());
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
        capital2.setText(selected2.getCapital());
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
    }


}
