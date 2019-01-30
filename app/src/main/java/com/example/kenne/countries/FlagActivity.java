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

//        correct_str = new ArrayList<>();
//        incorrect_str = new ArrayList<>();

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("continue_quiz");
        score = intent.getIntExtra("score", 0);
        questionIndex = intent.getIntExtra("index", 0);
        complete_url = intent.getStringExtra("map1");
        img_url = intent.getStringExtra("map2");
        regions = intent.getStringArrayListExtra("regions");
        correct_str = intent.getStringArrayListExtra("correct");
        incorrect_str = intent.getStringArrayListExtra("incorrect");
        Log.d("check_correct_array",""+correct_str+incorrect_str);

        try {
            allQuestions = new JSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loadAfterFlag(questionIndex);
    }

    public void checkFlagNext(){
        // check next
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
                // If the next type of question is img (flag), go to the stay on this activity
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

    public void loadAfterFlag(int index) {
        TextView score_view = findViewById(R.id.scoreFlagView);
        TextView remaining_view = findViewById(R.id.remainingFlagView);
        TextView question_view = findViewById(R.id.questionFlagView);
        TextView region_view = findViewById(R.id.regionFlagView);
        final TextView time_view = findViewById(R.id.timeFlagView);

        score_view.setText(String.valueOf(score));
        remaining_view.setText(String.valueOf(index + 1 + "/" + allQuestions.length()));

        ImageButton button_a = findViewById(R.id.imageButtonA);
        ImageButton button_b = findViewById(R.id.imageButtonB);
        ImageButton button_c = findViewById(R.id.imageButtonC);
        ImageButton button_d = findViewById(R.id.imageButtonD);

        button_a.setBackgroundColor(Color.TRANSPARENT);
        button_b.setBackgroundColor(Color.TRANSPARENT);
        button_c.setBackgroundColor(Color.TRANSPARENT);
        button_d.setBackgroundColor(Color.TRANSPARENT);

        button_a.setEnabled(true);
        button_b.setEnabled(true);
        button_c.setEnabled(true);
        button_d.setEnabled(true);

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
                time_view.setText(R.string.slow);
                // check next
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
                countdown.start();
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                ImageView imageView = findViewById(R.id.countryFlagView);
                Picasso.get().load(img_url).centerCrop().resize(512, 512).into(imageView);
                countdown.start();
            }
        });
    }

    public void questionAfterFlag(View view) {
        ImageButton answerButton = (ImageButton) view;

        TextView time_view = findViewById(R.id.timeFlagView);
        int time = Integer.parseInt(time_view.getText().toString());
        try {
            countdown.cancel();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Log.d("check_tag", "tag:" + answerButton.getTag());

        if (answerButton.getTag().equals(1)) {
            correct_str.add("Flag of "+object_name);
            score += time * 10;
            //final Toast score_toast = Toast.makeText(this,"+"+time * 10+" points",Toast.LENGTH_SHORT);
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
            incorrect_str.add("Flag of "+object_name);
            Log.d("check_tag", "not correct");
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
                // check next
                checkFlagNext();
            }

        }.start();
    }
}

