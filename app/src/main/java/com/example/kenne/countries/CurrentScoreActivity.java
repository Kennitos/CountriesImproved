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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

//        Map<String, String> params = new HashMap();
//        params.put("first_param", "1");
//        params.put("second_param", "2");
//
//        final JSONObject parameters = new JSONObject(params);
//        try {
//            parameters.put("work","ing?");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("Response","check_parameters"+parameters);
//        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, parameters,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // display response
//                        Log.d("check_response", response.toString());
//                        Log.d("check_response", ""+parameters);
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error.Response", "error:"+error);
//                    }
//                }
//        );
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        // add it to the RequestQueue
//        queue.add(postRequest);
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
