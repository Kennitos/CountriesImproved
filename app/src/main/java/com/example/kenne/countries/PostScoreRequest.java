package com.example.kenne.countries;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostScoreRequest extends StringRequest {

    ArrayList array_input;

    // Constructor
    public PostScoreRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, ArrayList array_input) {
        super(method, url, listener, errorListener);
        this.array_input = array_input;
    }

    // Method to supply parameters to the request
    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();

        // Naming the variables
        int score = (int) array_input.get(0);
        String name = String.valueOf(array_input.get(1));

        // Putting the variables in the parameters
        params.put("name", name);
        params.put("highscore", String.valueOf(score));

        return params;
    }
}
