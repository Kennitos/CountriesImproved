/*
CurrentScoreActivity.java

This is an activity where the user can select two different countries to compare to each other. The
user will only go to the new activity (CompareDetailActivity) when to existing countries are chosen.

@ author        Kennet Botan
*/

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

public class CurrentScoreActivity extends AppCompatActivity implements  Response.Listener, Response.ErrorListener{

    // Create the variables that will be used through the whole activity;
    String regions, correct, incorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_score);

        // Get the intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("score",0);
        int percentage = intent.getIntExtra("percentage",0);
        int total = intent.getIntExtra("total_points",0);

        // Check if the key 'post' exist in the intent, if it does exist it means that this
        // activity was reached via the Quiz- or FlagActivity after completion of all question. This
        // means that we still need to post a ScoreItem.
        // note: 'regions' was a ArrayList<String>
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
                // If this is not the case, the activity was accessed via ScoresActivity, with the
                // OnItemClickListener on the ListView of ScoreItems. In that case take these values.
                // note: 'regions' is a String
                regions = intent.getStringExtra("regions");
                correct = intent.getStringExtra("correct");
                incorrect = intent.getStringExtra("incorrect");
            }
        }

        // Assign TextView's
        TextView scoreView = findViewById(R.id.scoreInputView);
        TextView totalView = findViewById(R.id.totalPointsView);
        TextView regionView = findViewById(R.id.regionInputView);
        TextView correctView = findViewById(R.id.correctInputView);
        TextView incorrectView = findViewById(R.id.incorrectInputView);

        // Set values for TextView's
        scoreView.setText(String.valueOf(score));
        String htmlString = "out of <b> "+total+"</b> points";
        totalView.setText(Html.fromHtml(htmlString));
        regionView.setText(regions);
        correctView.setText(correct);
        // In case the user does not have any question wrong // Bravo :) !
        if(incorrect.equals("[]")){
            String htmlIncorrectString = "<i>You got everything correct!</i>";
            incorrectView.setText(Html.fromHtml(htmlIncorrectString));
        } else{
            incorrectView.setText(incorrect);
        }
    }

    // The function to post a scoreItem of the class ScoreItem
    public void postScore(ScoreItem scoreItem){
        String url = "http://ide50-kennitos.cs50.io:8080/list";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        PostScoreRequest request = new PostScoreRequest(Request.Method.POST, url, this, this, scoreItem);
        queue.add(request);
    }

    // The onError and onResponse with statements to check whether it works accordingly.
    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onResponse(Object response) {
        Log.d("response", "onResponse: " + response.toString());
    }
}
