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

    private Context context;
    private Callback activity; // option to remove private


    public interface Callback {
        //void gotCountries(ArrayList<Country> Countries);
        void gotCountries(ArrayList<Country> countries);
        void gotCountriesError(String message);
    }

    public CountriesRequest(Context context_input) {
        context = context_input;
    }

    @Override
    public void onResponse(JSONArray response) {
        Log.d("connect_test","Runt onResponse");
        Log.d("connect_test","Length of response: "+response.length());
        ArrayList<Country> CountriesArray = new ArrayList();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject countryObject = response.getJSONObject(i);
                String name = countryObject.getString("name");
                String capital = countryObject.getString("capital");
                if(capital.equals("")){
                    capital = "It has no capital";
                }
                String iso = countryObject.getString("alpha2Code").toLowerCase();
                String region = countryObject.getString("region");
                String subregion = countryObject.getString("subregion");
                String flag = countryObject.getString("flag");
                int population = countryObject.getInt("population");
                // optInt turns null values into zero's
                int area = countryObject.optInt("area");

                JSONArray coor = countryObject.getJSONArray("latlng");
                double[] coor_numbers = new double[coor.length()];

                // Extract numbers from JSON array.
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

                Country countryInput = new Country(name, capital, iso, region, subregion, area, population, flag, languages, lat, lng);
                Log.d("connect_test",""+i+' '+countryInput.getName()+coor+coor_numbers);
                CountriesArray.add(countryInput);
            }
            activity.gotCountries(CountriesArray);
        } catch (JSONException e) {
            e.printStackTrace();
            activity.gotCountriesError("gotCountriesError, something goes wrong");
        }
    }

    @Override
    public void onErrorResponse(VolleyError error){
        Log.d("connect_test","Runt onErrorResponse");
    }


    public void getCountries(Callback activity){
        Log.d("connect_test","Runt getCountries");
        this.activity = activity;
        String url = "https://restcountries.eu/rest/v2/all";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, this, this);
        queue.add(jsonArrayRequest);
    }
}
