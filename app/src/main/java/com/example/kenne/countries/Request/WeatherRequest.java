/*
WeatherRequest.java

This WeatherRequest class will get all the countries from the OPEN Weather API
http://api.openweathermap.org/data/2.5/weather?lat="+ {variable} +"&lon="+ {variable} +"&units=metric&{user key}
It will create an ArrayList<String> of the weather, it will have a general description of the weahter
and the actual current temperature. For example [Snow, 2.0 °C].

@ author        Kennet Botan
*/

package com.example.kenne.countries.Request;

import android.content.Context;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherRequest implements Response.Listener<JSONObject>, Response.ErrorListener{

    // Create the variables that will be used through the whole activity;
    Context context;
    Callback activity;

    public interface Callback {
        void gotWeather(ArrayList<String> weatherList);
        void gotWeatherError(String message);
    }

    // Constructor
    public WeatherRequest(Context context){
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onResponse(JSONObject response) {
        // Create an ArrayList in which the description and temperature will go into
        ArrayList<String> weatherList = new ArrayList<>();
        try {
            // The JSONObject 'description' is nested in the JSONArray 'weather', therefore
            JSONArray weatherArray = response.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String description = weatherObject.getString("description");

            // The Double 'temp' is nested in the JSONObject 'main'
            JSONObject mainObject = response.getJSONObject("main");
            Double temp = mainObject.getDouble("temp");

            // Add both (description, temp) in the ArrayList<String>
            weatherList.add(description);
            weatherList.add(String.valueOf(temp+" °C"));

            // Put the ArrayList<String> in the activity
            activity.gotWeather(weatherList);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void getWeather(WeatherRequest.Callback activity,double lat, double lon){
        this.activity = activity;
        // Use the variable Double lat and Double lon to create a unique url each time
        String url = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=metric&APPID=8680b7e935978eee010fd84e4d7e15ad";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,null,this,this);
        queue.add(jsonObjectRequest);
    }
}
