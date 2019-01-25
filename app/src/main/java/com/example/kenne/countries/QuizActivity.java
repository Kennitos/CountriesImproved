package com.example.kenne.countries;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class QuizActivity extends AppCompatActivity {

    ArrayList<Country> COUNTRIES, selected_countries;
    ArrayList<Country> correct, incorrect;
    List<String> quizList;
    ArrayList<String> regions;
    Country current;
    CountDownTimer countdown;
    String end_url, complete_url;
    int questionIndex, score, testNr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        testNr = 10;

        correct = new ArrayList<>();
        incorrect = new ArrayList<>();

        Intent intent = getIntent();
        regions = intent.getStringArrayListExtra("regions");
        ArrayList<String> characteristics = intent.getStringArrayListExtra("characteristics");
        String type = intent.getStringArrayListExtra("difficulty").get(0);
        String difficulty = intent.getStringArrayListExtra("difficulty").get(1);
        COUNTRIES = (ArrayList) intent.getStringArrayListExtra("countries");

        Quiz testQuiz = new Quiz(type,difficulty,regions,characteristics,COUNTRIES);
        ArrayList<Country> testcountries = testQuiz.selectCountries();
        selected_countries = testQuiz.select(testNr);
        testQuiz.selectComplete(10);

        quizList = new ArrayList<>();
        for(int i = 0; i < selected_countries.size(); i++){
            quizList.add(selected_countries.get(i).getName());
        }
        Collections.shuffle(quizList);
        Log.d("check_random",""+quizList);
        score = 0;
        questionIndex = 0;
        //        Toast.makeText(this,""+testcountries,Toast.LENGTH_LONG).show();
        //        Toast.makeText(this,""+selected_countries,Toast.LENGTH_SHORT).show();
        loadQuestions(questionIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        countdown.cancel();
        this.finish();
    }

    public void loadQuestions(int index){
        current = selected_countries.get(index);

        TextView regionView = findViewById(R.id.regionView);
        TextView questionView = findViewById(R.id.questionView);
        TextView remainingView = findViewById(R.id.remainingView);
        TextView scoreView = findViewById(R.id.scoreView);
        final TextView timeView = findViewById(R.id.timeView);
        ImageView imageView = findViewById(R.id.countryView);

        EncryptionMD5 createString = new EncryptionMD5(current.getName(),current.getRegion(),current.getSubregion());
        complete_url = createString.CreateEncryption();
        // https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Netherlands_in_Europe.svg/1051px-Netherlands_in_Europe.svg.png
         Log.d("check_current",""+complete_url);


//        StackOverflow link
//        https://stackoverflow.com/questions/25749055/how-to-test-if-an-image-is-fully-loaded-with-picasso
        if(current.getSubregion().equals("South America")){
            Picasso.get().load(complete_url).into(imageView);
        } else {
            Picasso.get().load(complete_url).resize(650, 650).into(imageView);
        }
//        Picasso.get().load(complete_url).resize(650, 650).into(imageView);
        // removed .centerCrop()

        // bron - 'https://stackoverflow.com/questions/25749055/how-to-test-if-an-image-is-fully-loaded-with-picasso'
        final AtomicBoolean loaded = new AtomicBoolean();
        Picasso.get().load(complete_url).resize(650, 650).into(imageView, new Callback.EmptyCallback() {
            @Override public void onSuccess() {
                loaded.set(true);
                countdown = new CountDownTimer(11000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        timeView.setText(String.valueOf(millisUntilFinished / 1000));
                        if(millisUntilFinished<1001){
                            incorrect.add(current);
                            String[] buttonNames = {"a","b","c","d"};
                            List<String> buttonList = Arrays.asList( buttonNames );

                            for (int i = 0; i < buttonList.size(); i++) {
                                Button button = findViewById(getResources().getIdentifier(buttonList.get(i)+"Button", "id",
                                        getApplicationContext().getPackageName()));
                                String buttonText = button.getText().toString();
                                if(buttonText.equals(current.getName())){
                                    button.setBackgroundColor(Color.GREEN);
                                }
                            }
                            Button button_a = findViewById(R.id.aButton);
                            Button button_b = findViewById(R.id.bButton);
                            Button button_c = findViewById(R.id.cButton);
                            Button button_d = findViewById(R.id.dButton);

                            button_a.setEnabled(false);
                            button_b.setEnabled(false);
                            button_c.setEnabled(false);
                            button_d.setEnabled(false);
                        }
                    }
                    public void onFinish() {
                        timeView.setText("To Slow!");
                        if(questionIndex==testNr-1) {
                            Toast.makeText(getApplicationContext(), "All questions done!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), CurrentScoreActivity.class);
                            intent.putExtra("score",score);
                            intent.putExtra("regions",regions);
                            intent.putExtra("correct",correct);
                            intent.putExtra("incorrect", incorrect);
                            startActivity(intent);
                            finish();
                        } else {
                            questionIndex += 1;
                            loadQuestions(questionIndex);
                        }
                    }
                }.start();
            }
            @Override
            public void onError(Exception e) {
                try{
                    countdown.start();
                } catch (NullPointerException e1){
                    e1.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"can't load image",Toast.LENGTH_SHORT).show();
            }
        });
        if (loaded.get()) {
            // The image was immediately available.
            Toast.makeText(this,"instant",Toast.LENGTH_SHORT).show();
        }




        regionView.setText(current.getRegion());
        remainingView.setText(questionIndex+1+"/"+testNr);
        scoreView.setText(String.valueOf(score));
        String question = "What country is this?";
        questionView.setText(question);
        String img_url = "https://raw.githubusercontent.com/djaiss/mapsicon/master/all/"+current.getIso()+"/512.png";
//        Picasso.get().load(img_url).centerCrop().resize(512, 512).into(imageView);

        Button button_a = findViewById(R.id.aButton);
        Button button_b = findViewById(R.id.bButton);
        Button button_c = findViewById(R.id.cButton);
        Button button_d = findViewById(R.id.dButton);

//        URL url = null;
//        try {
//            url = new URL("http://upload.wikimedia.org/wikipedia/commons/e/e8/Svg_example3.svg");
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = urlConnection.getInputStream();
//            SVG svg = SVGParser. getSVGFromInputStream(inputStream);
//            Drawable drawable = svg.createPictureDrawable();
//        } catch (Error e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        ImageButton button_test = findViewById(R.id.imageButton);
        Picasso.get().load("https://restcountries.eu/data/nld.svg").into(button_test);

        ArrayList<String> answerList = new ArrayList<>();
//        Collections.shuffle(europe);
//        answerList.add(current.getName());
//        answerList.add("option");
//        answerList.add("option");
//        answerList.add("option");
        answerList.add(current.getName());
        Collections.shuffle(quizList);
        for (int i = 0; i < 6; i++) {
            if (!answerList.contains(quizList.get(i))) {
                if(answerList.size()<4) {
                    answerList.add(quizList.get(i));
                }
            }
        }
        Collections.shuffle(answerList);

        button_a.setBackgroundColor(Color.CYAN);
        button_b.setBackgroundColor(Color.CYAN);
        button_c.setBackgroundColor(Color.CYAN);
        button_d.setBackgroundColor(Color.CYAN);

        button_a.setEnabled(true);
        button_b.setEnabled(true);
        button_c.setEnabled(true);
        button_d.setEnabled(true);

        button_a.setText(answerList.get(0));
        button_b.setText(answerList.get(1));
        button_c.setText(answerList.get(2));
        button_d.setText(answerList.get(3));
    }

    public void nextQuestion(View view) {
        Button answerButton = (Button) view;
        try{
            countdown.cancel();
        } catch (Error e){
            e.printStackTrace();
//            countdown.start();
        }
        String chosen_answer = answerButton.getText().toString();
        TextView timeView = findViewById(R.id.timeView);
        int time = Integer.parseInt(timeView.getText().toString());
        // Check if the chosen answer is the correct one
        if (chosen_answer.equals(current.getName())) {
            correct.add(current);
            score += time * 10;
            answerButton.setBackgroundColor(Color.GREEN);
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
        if (!chosen_answer.equals(current.getName())) {
            incorrect.add(current);
            answerButton.setBackgroundColor(Color.RED);

            String[] buttonNames = {"a","b","c","d"};
            List<String> buttonList = Arrays.asList( buttonNames );

            Log.d("check_button",""+buttonNames);
            Log.d("check_button",""+buttonList);
            for (int i = 0; i < buttonList.size(); i++) {
                Log.d("check_button",""+buttonList.get(i));
                Button button = findViewById(getResources().getIdentifier(buttonList.get(i)+"Button", "id",
                        this.getPackageName()));
                String buttonText = button.getText().toString();
                if(buttonText.equals(current.getName())){
                    button.setBackgroundColor(Color.GREEN);
                }
            }
        }
        // Repeat the the whole process until all questions are done, start intent to go new activity
        if (questionIndex == testNr-1) {
            Toast.makeText(this, "All questions done!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CurrentScoreActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("regions",regions);
            intent.putExtra("correct",correct);
            intent.putExtra("incorrect", incorrect);
            // Use finish() to make it not possible to go back to this page from the highscore activity
            startActivity(intent);
            finish();
        }
        else {
            questionIndex += 1;
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
                    loadQuestions(questionIndex);
                }
            }.start();
      }
    }
}
