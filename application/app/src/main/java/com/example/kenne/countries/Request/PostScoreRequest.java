/*
PostScoreRequest.java

This PostScoreRequest will create request to post the score using a Map<String, String> combination.
It was not possible to post a <String, JSONArray> combination. Therefore the ArrayLists regions,
correct and incorrect will be posted as strings of an ArrayList.

@ author        Kennet Botan
*/

package com.example.kenne.countries.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.kenne.countries.Object.ScoreItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostScoreRequest extends StringRequest {

    // Create the variables that will be used through the whole activity;
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
        int percentage = scoreItem.getPercentage();
        int total = scoreItem.getTotal();
        ArrayList<String> regions = scoreItem.getRegions();
        ArrayList<String> correct = scoreItem.getCorrect();
        ArrayList<String> incorrect = scoreItem.getIncorrect();

        // Putting the variables in the parameters
        params.put("name", name);
        params.put("score", String.valueOf(sc));
        params.put("percentage", String.valueOf(percentage));
        params.put("total", String.valueOf(total));
        params.put("regions", regions+"");
        params.put("correct", correct+"");
        params.put("incorrect",incorrect+"");

        // return the params with all the values into it
        return params;
    }
}
