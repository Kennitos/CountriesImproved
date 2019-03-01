/*
CompareActivity.java

This is an activity where the user can select two different countries to compare to each other. The
user will only go to the new activity (CompareDetailActivity) when to existing countries are chosen.

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
import android.widget.Toast;
import com.example.kenne.countries.DetailActivity.CompareCountryDetailActivity;
import com.example.kenne.countries.Object.Country;
import com.example.kenne.countries.R;
import java.util.ArrayList;

public class CompareActivity extends AppCompatActivity {

    // Create the variables that will be used through the whole activity;
    Country selected1;
    Country selected2;
    ArrayList<Country> COUNTRIES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        // Get the intent
        Intent intent = getIntent();
        COUNTRIES = (ArrayList) intent.getStringArrayListExtra("countries");
        Log.d("check_intent",""+COUNTRIES);

        AutoCompleteTextView editText1 = findViewById(R.id.autoCompleteCountry1);
        AutoCompleteTextView editText2 = findViewById(R.id.autoCompleteCountry2);

        // Check if the key 'selected' exist in the intent, if it does exist it means that this
        // activity was reached via the CountryDetailActivity with the 'compare' button (if not,
        // this activity was reached via HomeActivity).
        // If 'selected' exist name the variable selected1 with its value.
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("selected")) {
                selected1 = (Country) extras.getSerializable("selected");//extras.getBoolean("isNewItem", false);
                editText1.setText(selected1.getName());
                // Put the focus on the next editText,
                editText2.requestFocus();
            }
        }
        // After the focus is on the editText, open the keyboard.
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);



        // SEARCH 1
        // Create an adapter for the autocompletion
        ArrayAdapter<Country> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, COUNTRIES);
        editText1.setAdapter(adapter1);
        // Set an OnItemClickListener, this will save the country the user pressed on. Check if both
        // selected1 and selected2 are not null values (both must be assigned), if that is the case
        // go to the CompareCountryDetailActivity, if not return a toast to the user.
        editText1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView1, View view1, int i1, long l1) {
                selected1 = (Country) adapterView1.getAdapter().getItem(i1);
                if(selected1!=null & selected2!=null){
                    Intent intent = new Intent(getApplicationContext(), CompareCountryDetailActivity.class);
                    intent.putExtra("selected1",selected1);
                    intent.putExtra("selected2",selected2);

                    View v = getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                    startActivity(intent);
                } else {
                    // The user just selected a country for 'selected1', but 'selected2' is still
                    // a null value. Therefore put the focus on the editText for 'selected2'
                    AutoCompleteTextView editText2 = findViewById(R.id.autoCompleteCountry2);
                    editText2.requestFocus();
                }
            }
        });


        // SEARCH 2
        // The same principal as with SEARCH 1
        ArrayAdapter<Country> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, COUNTRIES);
        editText2.setAdapter(adapter2);
        editText2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView2, View view2, int i2, long l2) {
                selected2 = (Country) adapterView2.getAdapter().getItem(i2);
                if(selected1!=null & selected2!=null){
                    Intent intent = new Intent(getApplicationContext(), CompareCountryDetailActivity.class);
                    intent.putExtra("selected1",selected1);
                    intent.putExtra("selected2",selected2);

                    View v = getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                    startActivity(intent);
                } else {
                    AutoCompleteTextView editText1 = findViewById(R.id.autoCompleteCountry1);
                    editText1.requestFocus();
                }
            }
        });
    }

    // If the user returns to this Activity, all values must be cleared. The onStop will stop the
    // activity, so when the user returns all values are empty.
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    // The button for comparing both countries, the OnItemClickListener will do this actually
    // automatically, but the option is still there for the user.
    public void goToCompareCountryDetailActivity(View view){
        Log.d("check_intent",""+selected1+selected2+COUNTRIES);
        if(selected1!=null & selected2!=null){
            Intent intent = new Intent(this, CompareCountryDetailActivity.class);
            intent.putExtra("selected1",selected1);
            intent.putExtra("selected2",selected2);
            startActivity(intent);
        } if(selected1==null & selected2==null) {
            Toast.makeText(this,"Choose two existing countries",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Choose a second existing countries",Toast.LENGTH_LONG).show();
        }
    }
}
