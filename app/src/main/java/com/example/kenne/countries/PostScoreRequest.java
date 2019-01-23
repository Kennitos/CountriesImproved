package com.example.kenne.countries;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        JSONObject jsonParams = new JSONObject();

        // Naming the variables
        String name = scoreItem.getPlayer_name();
        int sc = scoreItem.getScore();
        ArrayList<String> regions = scoreItem.getRegions();
        ArrayList<Country> correct = scoreItem.getCorrect();
        ArrayList<Country> incorrect = scoreItem.getIncorrect();

//        JSONArray jsonRegionsArray = new JSONArray();
//        for(String r : regions) { jsonRegionsArray.put(r); }
//        JSONArray jsonCorrectArray = new JSONArray();
//        for(Country c : correct) { jsonCorrectArray.put(c.getName()); }
//        JSONArray jsonIncorrectArray = new JSONArray();
//        for(Country c : incorrect) { jsonIncorrectArray.put(c.getName()); }

        // Putting the variables in the parameters
        params.put("name", name);
        params.put("score", String.valueOf(sc));
        params.put("regions", regions+"");
        params.put("correct", correct+"");
        params.put("incorrect",incorrect+"");

        return params;
    }
}
