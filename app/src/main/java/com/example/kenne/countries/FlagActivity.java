/*
FlagActivity.java

This activity handle all the questions of the quiz concerning the flag questions. When a quiz is started
in QuizActivity, during the quiz the app will look ahead one question and checks if it is a question
concerning flags. If that is the case, an intent is started and the user will be redirected from the
QuizActivity to the FlagActivity. For exapmle if a quiz has questions about the country name, capital
and flag, it will cycle back and forth between QuizActivity and FlagActivity. During that cycling back
and forth the values will be passed on with intents each time.

@ author        Kennet Botan
*/

package com.example.kenne.countries;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenne.countries.Activity.CurrentScoreActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlagActivity extends AppCompatActivity {

    // Create the variables that will be used through the whole activity;
    JSONArray allQuestions;
    JSONObject current_object;
    ArrayList<String> regions, correct_str, incorrect_str;
    String object_name, object_region, object_subregion, object_correct, object_question;
    String complete_url, img_url;
    int score, questionIndex, correct_index;
    CountDownTimer countdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);

        // Get intent and its values (extra's)
        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("continue_quiz");
        score = intent.getIntExtra("score", 0);
        questionIndex = intent.getIntExtra("index", 0);
        complete_url = intent.getStringExtra("map1");
        img_url = intent.getStringExtra("map2");
        regions = intent.getStringArrayListExtra("regions");
        correct_str = intent.getStringArrayListExtra("correct");
        incorrect_str = intent.getStringArrayListExtra("incorrect");
        try {
            allQuestions = new JSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Load the question based on the questionIndex that is passed through the intent.
        loadAfterFlag(questionIndex);
    }


    // This function (checkFlagNext) checks whether all questions are asked and whether what kind of question the next
    // one is (questionIndex+1). If the next question is a question about flags, the users stays
    // on this (FlagActivity) activity. It will load another question with loadAfterFlag.
    // If the next question is not a question about flags, the users get redirected to the QuizActivity,
    // all the values will be sent with an intent.

    public void checkFlagNext(){
        // Check next question, if the last question: start intent to go to CurrentScoreActivity
        if (questionIndex == allQuestions.length() - 1) {
            int percentage = score/(allQuestions.length());
            Toast.makeText(getApplicationContext(), "All questions done!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), CurrentScoreActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("regions",regions);
            intent.putExtra("correct",correct_str);
            intent.putExtra("incorrect", incorrect_str);
            intent.putExtra("percentage",percentage);
            intent.putExtra("total_points",allQuestions.length()*100);
            intent.putExtra("post","");

            startActivity(intent);
            finish();
        } else {
            try {
                JSONObject next_object = (JSONObject) allQuestions.get(questionIndex + 1);
                String type = next_object.getString("type");
                // If the next type of question is img (flag), stay on this activity and load new
                // question with loadAfterFlag
                if (type.equals("img")) {
                    questionIndex += 1;
                    loadAfterFlag(questionIndex);
                } else {
                    // Else go back to QuizActivity
                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    intent.putExtra("continue_quiz", allQuestions.toString());
                    intent.putExtra("score", score);
                    intent.putExtra("index", questionIndex + 1);
                    intent.putExtra("map1", complete_url);
                    intent.putExtra("map2", img_url);
                    intent.putExtra("regions",regions);
                    intent.putExtra("correct",correct_str);
                    intent.putExtra("incorrect", incorrect_str);

                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // This function will load the activity with the all the values of the question
    // For example the TextView for the String of the question will set, etc.
    public void loadAfterFlag(int index) {
        // Assign the TextViews
        TextView score_view = findViewById(R.id.scoreFlagView);
        TextView remaining_view = findViewById(R.id.remainingFlagView);
        TextView question_view = findViewById(R.id.questionFlagView);
        TextView region_view = findViewById(R.id.regionFlagView);
        final TextView time_view = findViewById(R.id.timeFlagView);

        // Set Textviews with values
        score_view.setText(String.valueOf(score));
        remaining_view.setText(String.valueOf(index + 1 + "/" + allQuestions.length()));

        // Assign ImageButtons
        final ImageButton button_a = findViewById(R.id.imageButtonA);
        final ImageButton button_b = findViewById(R.id.imageButtonB);
        final ImageButton button_c = findViewById(R.id.imageButtonC);
        final ImageButton button_d = findViewById(R.id.imageButtonD);

        // Make them transparent again, only the flag is visible (not the button)
        button_a.setBackgroundColor(Color.TRANSPARENT);
        button_b.setBackgroundColor(Color.TRANSPARENT);
        button_c.setBackgroundColor(Color.TRANSPARENT);
        button_d.setBackgroundColor(Color.TRANSPARENT);

        //
        button_a.setEnabled(true);
        button_b.setEnabled(true);
        button_c.setEnabled(true);
        button_d.setEnabled(true);

        // Set TextViews and the images of the flags for the ImageButtons
        try {
            current_object = (JSONObject) allQuestions.get(index);
            object_name = current_object.getString("name");
            object_region = current_object.getString("region");
            object_subregion = current_object.getString("subregion");
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

            for (int i = 0; i < current_object.getJSONArray("answers").length(); i++) {
                if (object_correct.equals(current_object.getJSONArray("answers").get(i))) {
                    correct_index = i;
                }
            }
            String[] buttonNames = {"A", "B", "C", "D"};
            List<String> buttonList = Arrays.asList(buttonNames);
            String correct_a = buttonList.get(correct_index);

            // Give the correct ImageButton a Tag of '1'. Later on we use this tag to check which
            // ImageButton is the correct answer
            for (int i = 0; i < buttonList.size(); i++) {
                ImageButton button = findViewById(getResources().getIdentifier("imageButton" + buttonList.get(i), "id",
                        getApplicationContext().getPackageName()));
                if (buttonList.get(i).equals(correct_a)) {
                    button.setTag(1);
                } else {
                    button.setTag(0);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a countdown that will countdown from 10 to 0, where during the last second of the
        // countdown the buttons will become disable, so that you cant press them anymore
        countdown = new CountDownTimer(11000, 1000) {
            public void onTick(long millisUntilFinished) {
                time_view.setText(String.valueOf(millisUntilFinished / 1000));
                if (millisUntilFinished < 1001) {
                    incorrect_str.add("Flag of "+object_name);
                    String[] buttonNames = {"A", "B", "C", "D"};
                    List<String> buttonList = Arrays.asList(buttonNames);
                    String correct_a = buttonList.get(correct_index);

                    for (int i = 0; i < buttonList.size(); i++) {
                        ImageButton button = findViewById(getResources().getIdentifier("imageButton" + buttonList.get(i), "id",
                                getApplicationContext().getPackageName()));
                        if (buttonList.get(i).equals(correct_a)) {
                            button.setBackgroundColor(Color.GREEN);
                        }
                        button.setEnabled(false);
                    }
                }
            }

            @Override
            public void onFinish() {
                // Display to the user that he was to slow with his answer and load the new checkFlagNext
                time_view.setText(R.string.slow);
                checkFlagNext();
            }
        };

        ImageView imageView = findViewById(R.id.countryFlagView);
        Log.d("check_object", "object: " + object_name + object_region + object_subregion);
        EncryptionMD5 createString = new EncryptionMD5(object_name, object_region, object_subregion);
        complete_url = createString.CreateEncryption();
        Picasso.get().load(complete_url).resize(650, 650).into(imageView, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                // Make buttons enabled again
                button_a.setEnabled(true);
                button_b.setEnabled(true);
                button_c.setEnabled(true);
                button_d.setEnabled(true);
                countdown.start();
            }

            // If the image of the map of the country of the code above doesn't load, then load
            // this backup image of map of the country
            @Override
            public void onError(Exception e) {
                super.onError(e);
                ImageView imageView = findViewById(R.id.countryFlagView);
                Picasso.get().load(img_url).centerCrop().resize(512, 512).into(imageView);
                // Make buttons enabled again
                button_a.setEnabled(true);
                button_b.setEnabled(true);
                button_c.setEnabled(true);
                button_d.setEnabled(true);
                countdown.start();
            }
        });
    }

    // This function will run after a ImageButton is pressed
    public void questionAfterFlag(View view) {
        // Check which button is pressed
        ImageButton answerButton = (ImageButton) view;

        TextView time_view = findViewById(R.id.timeFlagView);
        int time = Integer.parseInt(time_view.getText().toString());
        try {
            countdown.cancel();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // Check if the button is the correct answer with the Tag of '1' created in line.202
        if (answerButton.getTag().equals(1)) {
            // If this is the correct answer, add the answer to the list of correct answers
            // and make the answer GREEN for the period of 1.0 second
            correct_str.add("Flag of "+object_name);
            score += time * 10;

            final Toast score_toast = Toast.makeText(this, "+" + time * 10 + " points", Toast.LENGTH_SHORT);
            score_toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    score_toast.cancel();
                }
            }, 750);
            answerButton.setBackgroundColor(Color.GREEN);
            Log.d("check_tag", "correct!");
        } else {
            // If this is not the correct answer, add the answer to the list of incorrect answers
            // and make the button RED for the period of 1.0 second
            incorrect_str.add("Flag of "+object_name);
            answerButton.setBackgroundColor(Color.RED);

            String[] buttonNames = {"A", "B", "C", "D"};
            List<String> buttonList = Arrays.asList(buttonNames);
            String correct_a = buttonList.get(correct_index);

            for (int i = 0; i < buttonList.size(); i++) {
                ImageButton button = findViewById(getResources().getIdentifier("imageButton" + buttonList.get(i), "id",
                        getApplicationContext().getPackageName()));
                if (buttonList.get(i).equals(correct_a)) {
                    button.setBackgroundColor(Color.GREEN);
                }
                button.setEnabled(false);
            }
        }
        // Repeat the the whole process until all questions are done,// start intent to go new activity
        CountDownTimer short_countdown = new CountDownTimer(1000, 1000) {
            ImageButton button_a = findViewById(R.id.imageButtonA);
            ImageButton button_b = findViewById(R.id.imageButtonB);
            ImageButton button_c = findViewById(R.id.imageButtonC);
            ImageButton button_d = findViewById(R.id.imageButtonD);

            public void onTick(long millisUntilFinished) {
                button_a.setEnabled(false);
                button_b.setEnabled(false);
                button_c.setEnabled(false);
                button_d.setEnabled(false);
            }

            public void onFinish() {
                checkFlagNext();
            }
        }.start();
    }
}

