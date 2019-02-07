/*
QuizActivity.java

This activity handle all the questions of the quiz concerning the non flag questions. When a quiz is started
in QuizActivity, during the quiz the app will look ahead one question and checks if it is a question
concerning flags. If that is the case, the user will an intent is started and the user will be redirected from the
QuizActivity to the FlagActivity. Otherwise the user stays in the QuizActivity.For exapmle if a quiz
has questions about the country name, capital and flag, it will cycle back and forth between QuizActivity
and FlagActivity. During that cycling back and forth the values will be passed on with intents each time.

Note: the comments on QuizActivity and FlagActivity are almost identical, since they both have the same
layout, with the difference that QuizActivity uses Buttons and FlagActivity uses ImageButtons

@ author        Kennet Botan
*/

package com.example.kenne.countries;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kenne.countries.Activity.CurrentScoreActivity;
import com.example.kenne.countries.Object.Country;
import com.example.kenne.countries.Object.Quiz;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class QuizActivity extends AppCompatActivity {

    // Create the variables that will be used through the whole activity;
    ArrayList<Country> COUNTRIES;
    ArrayList<String> regions, correct_str, incorrect_str;
    CountDownTimer countdown;
    String img_url, complete_url;
    String object_name, object_region, object_subregion, object_iso, object_correct, object_question;
    JSONArray allQuestions;
    JSONObject current_object;
    int questionIndex, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        correct_str = new ArrayList<>();
        incorrect_str = new ArrayList<>();

        // Get intent and its values (extra's)
        Intent intent = getIntent();

        // check if intent 'xxx' exist
        // if it exist, do plan a (continue with existing quiz)
        // if it does not exist, do plan b (create a new quiz)
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("continue_quiz")) {
                String jsonArray = intent.getStringExtra("continue_quiz");
                score = intent.getIntExtra("score",0);
                questionIndex = intent.getIntExtra("index",0);
                complete_url = intent.getStringExtra("map1");
                img_url = intent.getStringExtra("map2");
                correct_str = intent.getStringArrayListExtra("correct");
                incorrect_str = intent.getStringArrayListExtra("incorrect");
                regions = intent.getStringArrayListExtra("regions");
                try {
                    allQuestions = new JSONArray(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadQuestions(questionIndex);
            }
            else {
                regions = intent.getStringArrayListExtra("regions");

                ArrayList<String> characteristics = intent.getStringArrayListExtra("characteristics");
                COUNTRIES = (ArrayList) intent.getStringArrayListExtra("countries");

                Quiz testQuiz = new Quiz(regions,characteristics,COUNTRIES);
                testQuiz.selectCountries();
                allQuestions = testQuiz.selectComplete(10);

                score = 0;
                questionIndex = 0;

                try {
                    JSONObject current_object = (JSONObject) allQuestions.get(questionIndex);
                    String question_type = current_object.getString("type");
                    if(question_type.equals("img")){
                        object_name = current_object.getString("name");
                        object_region = current_object.getString("region");
                        object_subregion = current_object.getString("subregion");
                        object_iso = current_object.getString("iso");


                        EncryptionMD5 createString = new EncryptionMD5(object_name, object_region, object_subregion);
                        complete_url = createString.CreateEncryption();
                        img_url = "https://raw.githubusercontent.com/djaiss/mapsicon/master/all/"+object_iso+"/512.png";

                        Intent new_intent = new Intent(getApplicationContext(),FlagActivity.class);
                        new_intent.putExtra("continue_quiz",allQuestions.toString());
                        new_intent.putExtra("score",score);
                        new_intent.putExtra("regions", regions);
                        new_intent.putExtra("index",questionIndex);
                        new_intent.putExtra("correct",correct_str);
                        new_intent.putExtra("incorrect", incorrect_str);
                        new_intent.putExtra("map1",complete_url);
                        new_intent.putExtra("map2",img_url);
                        startActivity(new_intent);
                        finish();
                    } else {
                        loadQuestions(questionIndex);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }


    // This function (checkNext) checks whether all questions are asked and whether what kind of question the next
    // one is (questionIndex+1). If the next question is a question not about flags, the users stays
    // on this (QuizActivity) activity. It will load another question with LoadQuestions.
    // If the next question is a question about flags, the users get redirected to the FlagActivity,
    // all the values will be sent with an intent.

    public void checkNext(){
        // Check what the next type of question is
        try {
            JSONObject next_object = (JSONObject) allQuestions.get(questionIndex+1);
            String type = next_object.getString("type");
            // If the next type of question is img (flag), go to the FlagActivity
            if(type.equals("img")){
                Intent intent = new Intent(getApplicationContext(),FlagActivity.class);
                intent.putExtra("continue_quiz",allQuestions.toString());
                intent.putExtra("score",score);
                intent.putExtra("regions",regions);
                intent.putExtra("index",questionIndex+1);
                intent.putExtra("correct",correct_str);
                intent.putExtra("incorrect", incorrect_str);
                intent.putExtra("map1",complete_url);
                intent.putExtra("map2",img_url);
                startActivity(intent);
                finish();
            } else{
                questionIndex += 1;
                loadQuestions(questionIndex);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // This function will stop the CountDownTimer (if it has ben started), preventing the countdouwn
    // to continue after the user pressed the back button.
    @Override
    public void onStop() {
        super.onStop();
        try{
            countdown.cancel();
        } catch (Error e){
            e.printStackTrace();
        }
        this.finish();
    }

    // This function will load the activity with the all the values of the question
    // For example the TextView for the String of the question will set, etc.
    public void loadQuestions(int index){
        // Assign the TextViews
        TextView regionView = findViewById(R.id.regionView);
        TextView questionView = findViewById(R.id.questionView);
        TextView remainingView = findViewById(R.id.remainingView);
        TextView scoreView = findViewById(R.id.scoreView);
        final TextView timeView = findViewById(R.id.timeView);

        // Assign Buttons, make them final so they can be accessed from within inner classes
        final Button button_a = findViewById(R.id.aButton);
        final Button button_b = findViewById(R.id.bButton);
        final Button button_c = findViewById(R.id.cButton);
        final Button button_d = findViewById(R.id.dButton);

        button_a.setBackgroundColor(Color.parseColor("#D100574B"));
        button_b.setBackgroundColor(Color.parseColor("#D100574B"));
        button_c.setBackgroundColor(Color.parseColor("#D100574B"));
        button_d.setBackgroundColor(Color.parseColor("#D100574B"));

        try {
            current_object = (JSONObject) allQuestions.get(index);
            object_name = current_object.getString("name");
            object_region = current_object.getString("region");
            object_subregion = current_object.getString("subregion");
            object_iso = current_object.getString("iso");
            object_correct = current_object.getString("correct");
            object_question = current_object.getString("question");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set TextViews
        regionView.setText(object_region);
        remainingView.setText(String.valueOf(questionIndex+1+"/"+allQuestions.length()));
        scoreView.setText(String.valueOf(score));
        questionView.setText(object_question);

        // Set Text of the buttons (it's already randomized)
        try {
            button_a.setText((String) current_object.getJSONArray("answers").get(0));
            button_b.setText((String) current_object.getJSONArray("answers").get(1));
            button_c.setText((String) current_object.getJSONArray("answers").get(2));
            button_d.setText((String) current_object.getJSONArray("answers").get(3));
        } catch (JSONException e){
            e.printStackTrace();
        }

        EncryptionMD5 createString = new EncryptionMD5(object_name, object_region, object_subregion);
        complete_url = createString.CreateEncryption();
        // https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Netherlands_in_Europe.svg/1051px-Netherlands_in_Europe.svg.png

        // Create a countdown that will countdown from 10 to 0, where during the last second of the
        // countdown the buttons will become disable, so that you cant press them anymore
        countdown = new CountDownTimer(11000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeView.setText(String.valueOf(millisUntilFinished / 1000));
                if(millisUntilFinished<1001){
                    incorrect_str.add(object_correct);
                    String[] buttonNames = {"a","b","c","d"};
                    List<String> buttonList = Arrays.asList( buttonNames );

                    for (int i = 0; i < buttonList.size(); i++) {
                        Button button = findViewById(getResources().getIdentifier(buttonList.get(i)+"Button", "id",
                                getApplicationContext().getPackageName()));
                        String buttonText = button.getText().toString();
                        if(buttonText.equals(object_correct)){
                            button.setBackgroundColor(Color.GREEN);
                        }
                        button.setEnabled(false);
                    }
                }
            }
            public void onFinish() {
                // Display to the user that he was to slow with his answer and check what the next type of question is
                timeView.setText(R.string.slow);
                checkNext();
            }
        };

        // bron - 'https://stackoverflow.com/questions/25749055/how-to-test-if-an-image-is-fully-loaded-with-picasso'
        ImageView imageView = findViewById(R.id.countryView);
        Picasso.get().load(complete_url).resize(650, 650).into(imageView, new Callback.EmptyCallback() {
            @Override public void onSuccess() {
                super.onSuccess();
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
                ImageView imageView = findViewById(R.id.countryView);
                img_url = "https://raw.githubusercontent.com/djaiss/mapsicon/master/all/"+object_iso+"/512.png";
                Picasso.get().load(img_url).centerCrop().resize(512, 512).into(imageView);
                Toast.makeText(getApplicationContext(),"substitution image",Toast.LENGTH_SHORT).show();
                button_a.setEnabled(true);
                button_b.setEnabled(true);
                button_c.setEnabled(true);
                button_d.setEnabled(true);
                countdown.start();
            }
        });
    }

    // This function will run after a Button
    public void nextQuestion(View view) {
        Button answerButton = (Button) view;
        try{
            countdown.cancel();
        } catch (NullPointerException | Error e){
            e.printStackTrace();
        }
        String chosen_answer = answerButton.getText().toString();
        TextView timeView = findViewById(R.id.timeView);
        int time = Integer.parseInt(timeView.getText().toString());
        // Check if the chosen answer is the correct one
        if (chosen_answer.equals(object_correct)) {
            correct_str.add(object_correct);
            score += time * 10;
            answerButton.setBackgroundColor(Color.GREEN);

            // bronvermelding - http://...
            final Toast score_toast = Toast.makeText(this,"+"+time * 10+" points",Toast.LENGTH_SHORT);
            score_toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    score_toast.cancel();
                }
            }, 750);
        }
        if (!chosen_answer.equals(object_correct)) {
            incorrect_str.add(object_correct);
            answerButton.setBackgroundColor(Color.RED);

            String[] buttonNames = {"a","b","c","d"};
            List<String> buttonList = Arrays.asList( buttonNames );

            for (int i = 0; i < buttonList.size(); i++) {
                Button button = findViewById(getResources().getIdentifier(buttonList.get(i)+"Button", "id",
                        this.getPackageName()));
                String buttonText = button.getText().toString();
                if(buttonText.equals(object_correct)){
                    button.setBackgroundColor(Color.GREEN);
                }
            }
        }


        // Repeat the the whole process until all questions are done, start intent to go new activity
        if (questionIndex == allQuestions.length()-1) {
            int percentage = score/(allQuestions.length());
            Toast.makeText(this, "All questions done!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CurrentScoreActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("regions",regions);
            intent.putExtra("correct",correct_str);
            intent.putExtra("incorrect", incorrect_str);
            intent.putExtra("percentage", percentage);
            intent.putExtra("total_points",allQuestions.length()*100);
            intent.putExtra("post","");
            // Use finish() to make it not possible to go back to this page from the highscore activity
            startActivity(intent);
            finish();
        }
        else {
            CountDownTimer short_countdown = new CountDownTimer(1000, 1000) {
                Button button_a = findViewById(R.id.aButton);
                Button button_b = findViewById(R.id.bButton);
                Button button_c = findViewById(R.id.cButton);
                Button button_d = findViewById(R.id.dButton);
                public void onTick(long millisUntilFinished) {
                    button_a.setEnabled(false);
                    button_b.setEnabled(false);
                    button_c.setEnabled(false);
                    button_d.setEnabled(false);
                }
                public void onFinish() {
                    // Check what the next type of question is
                    checkNext();
                }
            }.start();
      }
    }
}