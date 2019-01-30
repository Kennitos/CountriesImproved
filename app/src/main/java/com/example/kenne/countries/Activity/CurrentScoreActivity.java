package com.example.kenne.countries.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.kenne.countries.Object.ScoreItem;
import com.example.kenne.countries.R;
import com.example.kenne.countries.Request.PostScoreRequest;
import com.example.kenne.countries.Request.ScoreRequest;

import java.util.ArrayList;

public class CurrentScoreActivity extends AppCompatActivity implements ScoreRequest.Callback, Response.Listener, Response.ErrorListener{

    String regions, correct, incorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_score);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score",0);
        int percentage = intent.getIntExtra("percentage",0);
        int total = intent.getIntExtra("total_points",0);

        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("post")) {
                ArrayList<String> regions_array = intent.getStringArrayListExtra("regions") ;
                ArrayList<String> correct_array = intent.getStringArrayListExtra("correct");
                ArrayList<String> incorrect_array = intent.getStringArrayListExtra("incorrect");

                regions = regions_array.toString();
                correct = correct_array.toString();
                incorrect = incorrect_array.toString();

                // Create a scoreItem and post it on 'http://ide50-kennitos.cs50.io:8080/list'
                ScoreItem currentScore = new ScoreItem("Kennet",score,regions_array,correct_array,incorrect_array,percentage,total);
                postScore(currentScore);
            }
            else{
                regions = intent.getStringExtra("regions");
                correct = intent.getStringExtra("correct");
                incorrect = intent.getStringExtra("incorrect");
//                Log.d("check_string",""+Integer.parseInt("55,543,533"));
            }
        }

        TextView scoreView = findViewById(R.id.scoreInputView);
        TextView totalView = findViewById(R.id.totalPointsView);
        TextView regionView = findViewById(R.id.regionInputView);
        TextView correctView = findViewById(R.id.correctInputView);
        TextView incorrectView = findViewById(R.id.incorrectInputView);

        scoreView.setText(String.valueOf(score));
        String htmlString = "out of <b> "+total+"</b> points";
        totalView.setText(Html.fromHtml(htmlString));
        regionView.setText(regions);
        correctView.setText(correct);
        if(incorrect.equals("[]")){
            String htmlIncorrectString = "<i>You got everything correct!</i>";
            incorrectView.setText(Html.fromHtml(htmlIncorrectString));
        } else{
            incorrectView.setText(incorrect);
        }
    }

    public void postScore(ScoreItem scoreItem){
        String url = "http://ide50-kennitos.cs50.io:8080/list";
         RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
         PostScoreRequest request = new PostScoreRequest(Request.Method.POST, url, this, this, scoreItem);
         queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onResponse(Object response) {
        Log.d("response", "onResponse: " + response.toString());
    }

    @Override
    public void gotScores(ArrayList scores) {
        Log.d("check_response","bc"+scores);
    }

    @Override
    public void gotScoresError(String message) {
        Log.d("check_response","cd"+message);
    }
}
