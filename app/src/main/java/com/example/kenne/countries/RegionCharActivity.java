package com.example.kenne.countries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class RegionCharActivity extends AppCompatActivity {

    ArrayList<Country> COUNTRIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_char);

        Intent intent = getIntent();
        COUNTRIES = (ArrayList) intent.getStringArrayListExtra("countries");
    }

    public void goToChar(View view){
        CheckBox africa = findViewById(R.id.checkAfrica);
        CheckBox asia = findViewById(R.id.checkAsia);
        CheckBox europe = findViewById(R.id.checkEurope);
        CheckBox oceania = findViewById(R.id.checkOceania);
        CheckBox namerica = findViewById(R.id.checkNorthAmerica);
        CheckBox samerica = findViewById(R.id.checkSouthAmerica);

        ArrayList<String> regions = new ArrayList<>();
        if(africa.isChecked()){
            regions.add("Africa");
        }
        if(asia.isChecked()){
            regions.add("Asia");
        }
        if(europe.isChecked()){
            regions.add("Europe");
        }
        if(oceania.isChecked()){
            regions.add("Oceania");
        }
        if(namerica.isChecked()){
            regions.add("North America");
        }
        if(samerica.isChecked()){
            regions.add("South America");
        }

        CheckBox name = findViewById(R.id.checkName);
        CheckBox capital = findViewById(R.id.checkCapital);
        CheckBox population = findViewById(R.id.checkPopulation);
        CheckBox area = findViewById(R.id.checkArea);
        CheckBox language = findViewById(R.id.checkLanguage);
        CheckBox weather = findViewById(R.id.checkWeather);
        CheckBox flag = findViewById(R.id.checkFlag);

        ArrayList<String> characteristics = new ArrayList<>();
        if(name.isChecked()){
            characteristics.add("name");
        }
        if(capital.isChecked()){
            characteristics.add("capital");
        }
        if(population.isChecked()){
            characteristics.add("population");
        }
        if(area.isChecked()){
            characteristics.add("area");
        }
        if(language.isChecked()){
            characteristics.add("language");
        }
        if(weather.isChecked()){
            characteristics.add("weather");
        }
        if(flag.isChecked()){
            characteristics.add("flag");
        }

        if((name.isChecked() || capital.isChecked() || population.isChecked() || language.isChecked() || weather.isChecked() || flag.isChecked()) &
                (africa.isChecked() || asia.isChecked() || europe.isChecked() || oceania.isChecked() || namerica.isChecked() || samerica.isChecked())){
            Toast.makeText(this,"Arrays: "+regions+characteristics,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DifficultyActivity.class);
            intent.putExtra("regions",regions);
            intent.putExtra("characteristics",characteristics);
            intent.putExtra("countries",COUNTRIES);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this,"Choose ",Toast.LENGTH_LONG).show();
        }

    }
}
