package com.example.kenne.countries.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.kenne.countries.Object.ScoreItem;

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
        int percentage = scoreItem.getPercentage();
        int total = scoreItem.getTotal();
        ArrayList<String> regions = scoreItem.getRegions();
        ArrayList<String> correct = scoreItem.getCorrect();
        ArrayList<String> incorrect = scoreItem.getIncorrect();

//        JSONArray jsonRegionsArray = new JSONArray();
//        for(String r : regions) { jsonRegionsArray.put(r); }
//        JSONArray jsonCorrectArray = new JSONArray();
//        for(Country c : correct) { jsonCorrectArray.put(c.getName()); }
//        JSONArray jsonIncorrectArray = new JSONArray();
//        for(Country c : incorrect) { jsonIncorrectArray.put(c.getName()); }

        // Putting the variables in the parameters
        params.put("name", name);
        params.put("score", String.valueOf(sc));
        params.put("percentage", String.valueOf(percentage));
        params.put("total", String.valueOf(total));
        params.put("regions", regions+"");
        params.put("correct", correct+"");
        params.put("incorrect",incorrect+"");

        return params;
    }
}
