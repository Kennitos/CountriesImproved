package com.example.kenne.countries;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    ArrayList<Country> COUNTRIES;
    ArrayList<Country> selected_countries;
    Country current;
    private int questionIndex, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        ArrayList<String> regions = intent.getStringArrayListExtra("regions");
        ArrayList<String> characteristics = intent.getStringArrayListExtra("characteristics");
        String type = intent.getStringArrayListExtra("difficulty").get(0);
        String difficulty = intent.getStringArrayListExtra("difficulty").get(1);
        COUNTRIES = (ArrayList) intent.getStringArrayListExtra("countries");

        Quiz testQuiz = new Quiz(type,difficulty,regions,characteristics,COUNTRIES);
        ArrayList<Country> testcountries = testQuiz.selectCountries();
        selected_countries = testQuiz.select10();
        score = 0;
        questionIndex = 0;

        //        Toast.makeText(this,""+testcountries,Toast.LENGTH_LONG).show();
        Toast.makeText(this,""+selected_countries,Toast.LENGTH_LONG).show();
        loadQuestions(questionIndex);

    }

    public void loadQuestions(int index){
        current = selected_countries.get(index);

        TextView regionView = findViewById(R.id.regionView);
        TextView questionView = findViewById(R.id.questionView);
        TextView remainingView = findViewById(R.id.remainingView);
        TextView scoreView = findViewById(R.id.scoreView);
        ImageView imageView = findViewById(R.id.countryView);

        regionView.setText(current.getRegion());
        remainingView.setText(questionIndex+"/10");
        scoreView.setText(String.valueOf(score));
        String question = "What country is this?";
        questionView.setText(question);
        String img_url = "https://raw.githubusercontent.com/djaiss/mapsicon/master/all/"+current.getIso()+"/512.png";
        Picasso.get().load(img_url).centerCrop().resize(512, 512).into(imageView);

        Button button_a = findViewById(R.id.aButton);
        Button button_b = findViewById(R.id.bButton);
        Button button_c = findViewById(R.id.cButton);
        Button button_d = findViewById(R.id.dButton);

        ArrayList<String> answerList = new ArrayList();
        answerList.add(current.getName());
        answerList.add("option");
        answerList.add("option");
        answerList.add("option");

        Collections.shuffle(answerList);

        button_a.setText(answerList.get(0));
        button_b.setText(answerList.get(1));
        button_c.setText(answerList.get(2));
        button_d.setText(answerList.get(3));
    }

    public void nextQuestion(View view){
        Button answerButton = (Button) view;
        String chosen_answer = answerButton.getText().toString();
        // Check if the chosen answer is the correct one
        if(chosen_answer==current.getName()){
            score+=10;
            Toast.makeText(this, "Correct! You're score: "+score, Toast.LENGTH_SHORT).show();
        }
        // Repeat the the whole process until all questions are done, start intent to go new activity
        if(questionIndex==9){
            Log.d("abc",' '+String.valueOf(score));
            Toast.makeText(this, "All questions done!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CurrentScoreActivity.class);
//            intent.putExtra("highscore",highscore);
//            intent.putExtra("player_name",player_name);

//            ArrayList combine_array = new ArrayList<>();
//            combine_array.add(highscore);
//            combine_array.add(player_name);
//
//            postHighscore(combine_array);
            // Use finish() to make it not possible to go back to this page from the highscore activity
            startActivity(intent);
            finish();
        }
        else {
            questionIndex += 1;
            loadQuestions(questionIndex);

        }
    }

    public void goToCurrentScoreActivity(View view){
        Intent intent = new Intent(this, CurrentScoreActivity.class);
        startActivity(intent);
    }
}
