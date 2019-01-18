package com.example.kenne.countries;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener{

    Context context;
    Callback activity;

    public interface Callback {
//        void gotScores(ArrayList<HighscoreItem> HighscoreItems);
        void gotScores(ArrayList scores);
        void gotScoresError(String message);
    }

    public ScoreRequest(Context context){
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONArray response) {
//        ArrayList<HighscoreItem> HighscoreItems = new ArrayList();
        try {
//            JSONObject scoreObject = response.getJSONObject("highscore")
//            JSONArray scoresArray = response.getJSONArray("highscore");
            for (int i = 0; i < response.length(); i++) {
                JSONObject scoreObject = response.getJSONObject(i);
                Log.d("check_response","ab"+scoreObject);
//                String name = scoreObject.getString("name");
//                int highscore = scoreObject.getInt("highscore");
//                int id = scoreObject.getInt("id");
//                Log.d("highscores_test",' '+highscore+name+id);
//
//                HighscoreItem highscoreItem = new HighscoreItem(name,highscore,id);
//                HighscoreItems.add(highscoreItem);
            }
//            activity.gotHighscores(HighscoreItems);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
