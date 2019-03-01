/*
CountriesRequest.java

This CountriesRequest class will get all the countries from the REST API (https://restcountries.eu/rest/v2/all).
It will create an ArrayList<Countries> of all the countries. With a Callback this request is called
from within another activity.

@ author        Kennet Botan
*/

package com.example.kenne.countries.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenne.countries.Object.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class CountriesRequest implements Response.Listener<JSONArray>, Response.ErrorListener{

    // Create the variables that will be used through the whole activity;
    Context context;
    Callback activity;


    public interface Callback {
        void gotCountries(ArrayList<Country> countries);
        void gotCountriesError(String message);
    }

    // Constructor
    public CountriesRequest(Context context_input) {
        context = context_input;
    }

    @Override
    public void onResponse(JSONArray response) {

        // Create the ArrayList where all countries will go into
        ArrayList<Country> CountriesArray = new ArrayList();
        try {
            for (int i = 0; i < response.length(); i++) {
                // Get the JSONObject and get all characteristics from it
                JSONObject countryObject = response.getJSONObject(i);
                String name = countryObject.getString("name");
                String capital = countryObject.getString("capital");
                if(capital.equals("")){
                    capital = "It has no capital";
                }
                String iso2 = countryObject.getString("alpha2Code").toLowerCase();
                String iso3 = countryObject.getString("alpha3Code").toLowerCase();
                String region = countryObject.getString("region");
                String subregion = countryObject.getString("subregion");
                String flag = countryObject.getString("flag");
                int population = countryObject.getInt("population");
                // optInt turns null values into zero's
                int area = countryObject.optInt("area");

                JSONArray coor = countryObject.getJSONArray("latlng");
                double[] coor_numbers = new double[coor.length()];

                // Extract coordinate numbers from JSON array.
                for (int c = 0; c < coor.length(); ++c) {
                    coor_numbers[c] = coor.optDouble(c);
                }
                Double lat = coor.optDouble(0);
                Double lng = coor.optDouble(1);

                ArrayList<String> languages = new ArrayList<>();
                JSONArray languageJson = countryObject.getJSONArray("languages");
                for(int lan = 0; lan < languageJson.length(); lan++){
                    JSONObject languageObject = languageJson.getJSONObject(lan);
                    String languageName = languageObject.getString("name");
                    languages.add(languageName);
                }

                // Create a variable country and put it into the ArrayList<Country>
                Country countryInput = new Country(name, capital, iso2, iso3, region, subregion, area, population, flag, languages, lat, lng);
                CountriesArray.add(countryInput);
            }
            // Put the ArrayList<Country> in the activity
            activity.gotCountries(CountriesArray);
        } catch (JSONException e) {
            e.printStackTrace();
            activity.gotCountriesError("gotCountriesError, something goes wrong");
        }
    }

    @Override
    public void onErrorResponse(VolleyError error){
        error.printStackTrace();
    }

    public void getCountries(Callback activity){
        this.activity = activity;
        String url = "https://restcountries.eu/rest/v2/all";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, this, this);
        queue.add(jsonArrayRequest);
    }
}
