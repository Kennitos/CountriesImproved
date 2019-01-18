package com.example.kenne.countries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class CurrentScoreActivity extends AppCompatActivity implements ScoreRequest.Callback, Response.Listener, Response.ErrorListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_score);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score",0);
        ArrayList<String> regions = intent.getStringArrayListExtra("regions") ;
        ArrayList<Country> correct = (ArrayList) intent.getStringArrayListExtra("correct");
        ArrayList<Country> incorrect = (ArrayList) intent.getStringArrayListExtra("incorrect");

        TextView scoreView = findViewById(R.id.scoreInputView);
        scoreView.setText(String.valueOf(score));

        // Create a scoreItem and post it on 'http://ide50-kennitos.cs50.io:8080/list'
        ScoreItem currentScore = new ScoreItem("Kennet",score,regions,correct,incorrect);
        postScore(currentScore);
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
