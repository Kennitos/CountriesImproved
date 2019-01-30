/*
ScoreRequest.java

This ScoreRequest class will get all the countries from the rester server (https://ide50-kennitos.
-legacy.cs50.io:8080/list). It will create an ArrayList<ScoreItem> of all the scores. With a Callback
this request is called from within another activity.

@ author        Kennet Botan
*/

package com.example.kenne.countries.Request;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenne.countries.Object.ScoreItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener{

    // Create the variables that will be used through the whole activity;
    Context context;
    Callback activity;

    public interface Callback {
        void gotScores(ArrayList<ScoreItem> scoreArray);
        void gotScoresError(String message);
    }

    // Constructor
    public ScoreRequest(Context context){
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onResponse(JSONArray response) {
        // Create an ArrayList in which all the scores will go
        ArrayList<ScoreItem> ScoreItems = new ArrayList();
        try {
            for (int i = 0; i < response.length(); i++) {
                // Loop through the JSONObject within the JSONArray and create a variable scoreItem
                // each time
                JSONObject scoreObject = response.getJSONObject(i);

                String name = scoreObject.getString("name");
                int score = scoreObject.getInt("score");
                int id = scoreObject.getInt("id");
                int percentage = scoreObject.getInt("percentage");
                int total = scoreObject.getInt("total");
                String regions = scoreObject.getString("regions");
                String correct = scoreObject.getString("correct");
                String incorrect = scoreObject.getString("incorrect");

                ScoreItem scoreItem = new ScoreItem(name, score, regions, correct, incorrect, percentage, total);
                ScoreItems.add(scoreItem);
            }
            // Put the ArrayList<ScoreItem> in the activity
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
