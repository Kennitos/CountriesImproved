/*
SearchActivity.java

This activity lets the user search for a particular country. This user is helped by this task with a
autocompletion, therefore the user won't have to type the whole literal string (more user friendly).
When the user taps on the one of the autocompletion suggestions, he will be automatically be directed
to the next activity (CountryDetailActivity).

@ author        Kennet Botan
*/

package com.example.kenne.countries.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kenne.countries.DetailActivity.CountryDetailActivity;
import com.example.kenne.countries.Object.Country;
import com.example.kenne.countries.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    // Create the variables that will be used through the whole activity;
    ArrayList<Country> COUNTRIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get the intent
        Intent intent = getIntent();
        COUNTRIES = (ArrayList) intent.getStringArrayListExtra("countries");

        // Create an adapter for the autocompletion
        AutoCompleteTextView editText = findViewById(R.id.autoCompleteCountry);
        ArrayAdapter<Country> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, COUNTRIES);
        editText.setAdapter(adapter);
        // Set an OnItemClickListener, this will save the country that was clicked and redirect the
        // user to a new activity (CountryDetailActivity) with that country in the intent.
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Country selected = (Country) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(getApplicationContext(), CountryDetailActivity.class);
                intent.putExtra("selected_country",selected);
                intent.putExtra("countries",COUNTRIES);

                // Close the keyboard if a country is selected and on the verge of going to the new
                // activity
                View v = getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                startActivity(intent);
                finish();
            }
        });

        // Open the keyboard when this activity is created
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    @Override
    protected void onResume() {
        // clear the text, because of this when you return to this page the EditText will be empty
        // instead of being filled with the previous country
        super.onResume();
        AutoCompleteTextView editText = findViewById(R.id.autoCompleteCountry);
        editText.setText("");
    }


    public void goToCountryDetail(View view){
        // When pressing on the button 'search', this Toast will always be showed. The user must
        // tap on one of the suggestions. Unfortunately this must be forced on the user, since this
        // will guarantee the a Country is selected instead of a String.
        Toast.makeText(this,"Tap on a country from the suggestions",Toast.LENGTH_LONG).show();
    }

}
