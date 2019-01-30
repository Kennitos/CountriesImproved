package com.example.kenne.countries.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenne.countries.Object.ScoreItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherRequest implements Response.Listener<JSONObject>, Response.ErrorListener{

    Context context;
    Callback activity;

    public interface Callback {
        void gotWeather(ArrayList<String> weatherList);
        void gotWeatherError(String message);
    }

    public WeatherRequest(Context context){
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("check_response","error"+error);
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("check_response","onResponse runt!");
        ArrayList<String> weatherList = new ArrayList<>();
        try {
            JSONArray weatherArray = response.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String description = weatherObject.getString("description");

            JSONObject mainObject = response.getJSONObject("main");
            Double temp = mainObject.getDouble("temp");

            weatherList.add(description);
            weatherList.add(String.valueOf(temp+" °C"));

            activity.gotWeather(weatherList);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void getWeather(WeatherRequest.Callback activity,double lat, double lon){
        this.activity = activity;
        String url = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=metric&APPID=8680b7e935978eee010fd84e4d7e15ad";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,null,this,this);
        queue.add(jsonObjectRequest);
    }
}
