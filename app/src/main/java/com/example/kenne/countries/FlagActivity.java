package com.example.kenne.countries;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlagActivity extends AppCompatActivity {

    JSONArray allQuestions;
    JSONObject current_object;
    String object_region, object_correct, object_question;
    String complete_url, img_url;
    int score, questionIndex, correct_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("continue_quiz");
        score = intent.getIntExtra("score",0);
        questionIndex = intent.getIntExtra("index",0);
        complete_url = intent.getStringExtra("map1");
        img_url = intent.getStringExtra("map2");

        try {
            allQuestions = new JSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView score_view = findViewById(R.id.scoreFlagView);
        TextView remaining_view = findViewById(R.id.remainingFlagView);
        TextView question_view = findViewById(R.id.questionFlagView);
        TextView region_view = findViewById(R.id.regionFlagView);

        score_view.setText(String.valueOf(score));
        remaining_view.setText(String.valueOf(questionIndex+1+"/"+allQuestions.length()));

        ImageButton button_a = findViewById(R.id.imageButtonA);
        ImageButton button_b = findViewById(R.id.imageButtonB);
        ImageButton button_c = findViewById(R.id.imageButtonC);
        ImageButton button_d = findViewById(R.id.imageButtonD);

        try {
            current_object = (JSONObject) allQuestions.get(questionIndex);
            object_region = current_object.getString("region");
            object_correct = current_object.getString("correct");
            object_question = current_object.getString("question");

            question_view.setText(object_question);
            region_view.setText(object_region);

            Picasso.get().load(current_object.getJSONArray("answers").get(0).toString())
                    .resize(400, 400)
                    .into(button_a);
            Picasso.get().load(current_object.getJSONArray("answers").get(1).toString())
                    .resize(400, 400)
                    .into(button_b);
            Picasso.get().load(current_object.getJSONArray("answers").get(2).toString())
                    .resize(400, 400)
                    .into(button_c);
            Picasso.get().load(current_object.getJSONArray("answers").get(3).toString())
                    .resize(400, 400)
                    .into(button_d);

            for(int i = 0; i < current_object.getJSONArray("answers").length(); i++){
                if(object_correct.equals(current_object.getJSONArray("answers").get(i))){
                    correct_index = i;
                }
            }
            String[] buttonNames = {"A","B","C","D"};
            List<String> buttonList = Arrays.asList( buttonNames );
            String correct_a = buttonList.get(correct_index);

            for (int i = 0; i < buttonList.size(); i++) {
                ImageButton button = findViewById(getResources().getIdentifier("imageButton"+buttonList.get(i), "id",
                        getApplicationContext().getPackageName()));
                if(buttonList.get(i).equals(correct_a)){
                    button.setTag(1);
                } else{
                    button.setTag(0);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView imageView = findViewById(R.id.countryFlagView);
        Picasso.get().load(complete_url).resize(650, 650).into(imageView, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                ImageView imageView = findViewById(R.id.countryFlagView);
                Picasso.get().load(img_url).centerCrop().resize(512, 512).into(imageView);
            }
        });
    }

    public void questionAfterFlag(View view){
        ImageButton answerButton = (ImageButton) view;
        Log.d("check_tag","tag:"+answerButton.getTag());
        if(answerButton.getTag().equals(1)){
            Log.d("check_tag","correct!");
        } else{
            Log.d("check_tag","not correct");
        }
    }
}
