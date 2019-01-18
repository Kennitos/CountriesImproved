package com.example.kenne.countries;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostScoreRequest extends StringRequest {

    ScoreItem scoreItem;

    // Constructor
    public PostScoreRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, ScoreItem scoreItem) {
        super(method, url, listener, errorListener);
        this.scoreItem = scoreItem;
    }

    // Method to supply parameters to the request
    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();

        // Naming the variables
        String name = scoreItem.getPlayer_name();
        int sc = scoreItem.getScore();
        ArrayList<String> regions = scoreItem.getRegions();
        ArrayList<Country> correct = scoreItem.getCorrect();
        ArrayList<Country> incorrect = scoreItem.getIncorrect();


        // Putting the variables in the parameters
        params.put("name", name);
        params.put("score", String.valueOf(sc));
        for(int i = 0; i < regions.size(); i++){
            params.put("regions", regions.get(i));
        }
        for(int i = 0; i < correct.size(); i++){
            Log.d("check_request",""+correct.get(i).getName());
            params.put("correct", correct.get(i).getName());
        }
        for(int i = 0; i < incorrect.size(); i++){
            params.put("incorrect", incorrect.get(i).getName());
        }

        return params;
    }
}
