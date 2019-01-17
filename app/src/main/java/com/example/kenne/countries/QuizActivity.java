package com.example.kenne.countries;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuizActivity extends AppCompatActivity {

    ArrayList<Country> COUNTRIES;
    ArrayList<Country> selected_countries;
    List<String> europeList;
    Country current;
    CountDownTimer countdown;
    String end_url, complete_url, accurate, accurate2;
    private int questionIndex, score, testNr;


    // removeLastChar will transform 'Moldova ' to 'Moldova' and 'Isle of Man ' to 'Isle of Man'
    private static String removeLastChar(String str) {
        Character lastChar = str.charAt(str.length()-1);
        if(Character.isWhitespace(lastChar)){
            return str.substring(0, str.length() - 1);
        }
        else { return str; }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        testNr = 20;

        Intent intent = getIntent();
        ArrayList<String> regions = intent.getStringArrayListExtra("regions");
        ArrayList<String> characteristics = intent.getStringArrayListExtra("characteristics");
        String type = intent.getStringArrayListExtra("difficulty").get(0);
        String difficulty = intent.getStringArrayListExtra("difficulty").get(1);
        COUNTRIES = (ArrayList) intent.getStringArrayListExtra("countries");

        Quiz testQuiz = new Quiz(type,difficulty,regions,characteristics,COUNTRIES);
        ArrayList<Country> testcountries = testQuiz.selectCountries();
        selected_countries = testQuiz.select(testNr);

        europeList = new ArrayList<>();
        for(int i = 0; i < selected_countries.size(); i++){
            europeList.add(selected_countries.get(i).getName());
        }
        Collections.shuffle(europeList);
        Log.d("check_random",""+europeList);
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

        String[] split_name = current.getName().split("[(\\)]");
        String lastCharSpace = removeLastChar(split_name[0]);
        if(current.getRegion().equals("Europe")){
            end_url = lastCharSpace.replaceAll(" ", "_")+"_in_Europe.svg";
            if(current.getName().equals("United Kingdom of Great Britain and Northern Ireland")){
                end_url = "United_Kingdom_in_Europe.svg";
            }
        }
        else if(current.getRegion().equals("Africa")){
            end_url = lastCharSpace.replaceAll(" ", "_")+"_in_Africa.svg";
        }
        else if(current.getSubregion().equals("South America")){
            end_url = lastCharSpace.replaceAll(" ", "_")+"_in_South_America.svg";
        } else{
            end_url = lastCharSpace.replaceAll(" ", "_")+"_in_its_region.svg";
        }
        Log.d("check_current",""+end_url);
        try {
            // bron r.113-118 - https://stackoverflow.com/questions/3934331/how-to-hash-a-string-in-android
            // tijmen has the same
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(end_url.getBytes(Charset.forName("US-ASCII")),0,end_url.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hashString = String.format("%0" + (magnitude.length << 1) + "x", bi);


//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(end_url.getBytes(), 0, end_url.length());
//            String hashString = new BigInteger(1, md.digest()).toString(16);

//            BigInteger bigInt = new BigInteger(1, md.digest());
//            accurate = String.format("%0"+(magnitude.byteArray ), bigInt);
//            accurate2 = String.format("%032x",bigInt);
//            formatter.format("%02x", b)

            String first = Character.toString(hashString.charAt(0));
            String first_two = first+Character.toString(hashString.charAt(1));


            // Hardcode some important countries of which the generated md5 hash an accurate md5
            // Bulgaria:
//            if(current.getName().equals("Bulgaria")){
//                first= "0";
//                first_two= "0e";
//            }

            complete_url = "https://upload.wikimedia.org/wikipedia/commons/thumb/"+first+"/"+first_two+"/"+end_url+"/1051px-"+end_url+".png";
            // https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Netherlands_in_Europe.svg/1051px-Netherlands_in_Europe.svg.png


             Log.d("check_current",""+first+" "+first_two+" "+hashString);
             Log.d("check_current",""+accurate);
             Log.d("check_current",""+accurate2);
             Log.d("check_current",""+complete_url);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

//        StackOverflow link
//        https://stackoverflow.com/questions/25749055/how-to-test-if-an-image-is-fully-loaded-with-picasso
        Picasso.get().load(complete_url).resize(650, 650).into(imageView);
        // removed .centerCrop()

        final AtomicBoolean loaded = new AtomicBoolean();
        Picasso.get().load(complete_url).resize(650, 650).into(imageView, new Callback.EmptyCallback() {
            @Override public void onSuccess() {
                loaded.set(true);
                countdown = new CountDownTimer(11000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        timeView.setText(String.valueOf(millisUntilFinished / 1000));
                        if(millisUntilFinished<1001){
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
                countdown.start();
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

        ArrayList<String> answerList = new ArrayList<>();
//        Collections.shuffle(europe);
//        answerList.add(current.getName());
//        answerList.add("option");
//        answerList.add("option");
//        answerList.add("option");
        answerList.add(current.getName());
        Collections.shuffle(europeList);
        for (int i = 0; i < 6; i++) {
            if (!answerList.contains(europeList.get(i))) {
                if(answerList.size()<4) {
                    answerList.add(europeList.get(i));
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
