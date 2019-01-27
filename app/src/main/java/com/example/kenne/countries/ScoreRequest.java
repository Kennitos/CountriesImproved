package com.example.kenne.countries;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener{

    Context context;
    Callback activity;

    public interface Callback {
//        void gotScores(ArrayList<HighscoreItem> HighscoreItems);
        void gotScores(ArrayList<ScoreItem> scoreArray);
        void gotScoresError(String message);
    }

    public ScoreRequest(Context context){
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.d("check_response","error"+error);
    }

    @Override
    public void onResponse(JSONArray response) {
        Log.d("check_response","onResponse runt!");
        ArrayList<ScoreItem> ScoreItems = new ArrayList();
//        ArrayList<ArrayList> responseArray = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
//                ArrayList individualArray = new ArrayList();
                JSONObject scoreObject = response.getJSONObject(i);

                String name = scoreObject.getString("name");
                int score = scoreObject.getInt("score");
                int id = scoreObject.getInt("id");
                String regions = scoreObject.getString("regions");
                String correct = scoreObject.getString("correct");
                String incorrect = scoreObject.getString("incorrect");

                ScoreItem scoreItem = new ScoreItem(name, score, regions, correct, incorrect);
                ScoreItems.add(scoreItem);
//                individualArray.add(id);
//                individualArray.add(name);
//                individualArray.add(score);
//                individualArray.add(regions);
//                individualArray.add(correct);
//                individualArray.add(incorrect);

//                HighscoreItem highscoreItem = new HighscoreItem(name,highscore,id);
//                HighscoreItems.add(highscoreItem);
//                responseArray.add(individualArray);
            }
            Log.d("check_response",""+ScoreItems);
            activity.gotScores(ScoreItems);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getScore(Callback activity){
        this.activity = activity;
        String url = "https://ide50-kennitos.legacy.cs50.io:8080/list";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,this,this);
        queue.add(jsonArrayRequest);
    }
}