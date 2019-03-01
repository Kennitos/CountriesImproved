/*
RegionCharActivity.java

This activity will determine which regions and characteristics the quiz will include. It will send
those variables to the next activity (QuizActivity) and create the Quiz in that activity.

@ author        Kennet Botan
*/

package com.example.kenne.countries.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.kenne.countries.Object.Country;
import com.example.kenne.countries.QuizActivity;
import com.example.kenne.countries.R;

import java.util.ArrayList;

public class RegionCharActivity extends AppCompatActivity {

    // Create the variables that will be used through the whole activity;
    ArrayList<Country> COUNTRIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_char);

        // Get the intent from the HomeActivity
        Intent intent = getIntent();
        COUNTRIES = (ArrayList) intent.getStringArrayListExtra("countries");
    }

    // This function will check all checkboxes and add them to either one of the two ArrayList's.
    public void goToQuiz(View view){
        // Name the diffrenct checkboxes (for regions)
        CheckBox africa = findViewById(R.id.checkAfrica);
        CheckBox asia = findViewById(R.id.checkAsia);
        CheckBox europe = findViewById(R.id.checkEurope);
        CheckBox oceania = findViewById(R.id.checkOceania);
        CheckBox namerica = findViewById(R.id.checkNorthAmerica);
        CheckBox samerica = findViewById(R.id.checkSouthAmerica);

        // Add variable String's to a new ArrayList.
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

        // Name the different checkboxes (for characteristics)
        CheckBox name = findViewById(R.id.checkName);
        CheckBox capital = findViewById(R.id.checkCapital);
        CheckBox population = findViewById(R.id.checkPopulation);
        CheckBox area = findViewById(R.id.checkArea);
        CheckBox flag = findViewById(R.id.checkFlag);

        // Add variable String's to a new ArrayList.
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
        if(flag.isChecked()){
            characteristics.add("flag");
        }

        // Check if if one or more characteristics and!! one or more regions are checked. If that is
        // the case, the ArrayList's can be sent to the new activity (QuizActivity) in an intent. If
        // is not the case, print a toast explaining to the user that she (not sexist) needs to make
        // a choice :P
        if((name.isChecked() || capital.isChecked() || population.isChecked() || area.isChecked() || flag.isChecked())  &
                (africa.isChecked() || asia.isChecked() || europe.isChecked() || oceania.isChecked() || namerica.isChecked() || samerica.isChecked())){
            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra("regions",regions);
            intent.putExtra("characteristics",characteristics);
            intent.putExtra("countries",COUNTRIES);
            startActivity(intent);
            finish();
        } else {
            // Print the toast if the user has not properly selected the regions/characteristics
            Toast.makeText(this,"Choose at least a characteristic and region  ",Toast.LENGTH_LONG).show();
        }

    }
}
