package com.example.kenne.countries.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.kenne.countries.Object.Country;
import com.example.kenne.countries.QuizActivity;
import com.example.kenne.countries.R;

import java.util.ArrayList;

public class DifficultyActivity extends AppCompatActivity {

    ArrayList<String> regions = new ArrayList<>();
    ArrayList<String> characteristics = new ArrayList<>();
    ArrayList<Country> COUNTRIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        Intent intent = getIntent();
        regions = intent.getStringArrayListExtra("regions");
        characteristics = intent.getStringArrayListExtra("characteristics");
        COUNTRIES = (ArrayList) intent.getStringArrayListExtra("countries");
    }

    public void goToQuiz(View view){
        RadioButton open = findViewById(R.id.radioOpen);
        RadioButton multi = findViewById(R.id.radioMulti);
        RadioButton easy = findViewById(R.id.radioEasy);
        RadioButton medium = findViewById(R.id.radioMedium);
        RadioButton hard = findViewById(R.id.radioHard);

        ArrayList<String> difficulty = new ArrayList<>();
        if(open.isChecked()){
            difficulty.add("open");
        }
        if(multi.isChecked()){
            difficulty.add("multi");
        }
        if(easy.isChecked()){
            difficulty.add("easy");
        }
        if(medium.isChecked()){
            difficulty.add("medium");
        }
        if(hard.isChecked()){
            difficulty.add("hard");
        }

        if((open.isChecked() || multi.isChecked()) & (easy.isChecked() || medium.isChecked() || hard.isChecked())){
//            Toast.makeText(this,"Arrays: "+difficulty,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra("regions",regions);
            intent.putExtra("characteristics",characteristics);
            intent.putExtra("difficulty",difficulty);
            intent.putExtra("countries",COUNTRIES);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this,"Choose",Toast.LENGTH_SHORT).show();
        }
    }
}
