package com.example.kenne.countries;


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
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayList<String> COUNTRIES_STRING = new ArrayList<>();
    ArrayList<Country> COUNTRIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        COUNTRIES = (ArrayList) intent.getStringArrayListExtra("countries");
        Log.d("all_names","ArrayList"+COUNTRIES);

//        Create a second ArrayList besides COUNTRIES, but with strings as type instead of objects
        for(int i = 0; i < COUNTRIES.size(); i++){
            String name = COUNTRIES.get(i).getName();
            COUNTRIES_STRING.add(name);
        }

        AutoCompleteTextView editText = findViewById(R.id.autoCompleteCountry);
        ArrayAdapter<Country> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, COUNTRIES);
        editText.setAdapter(adapter);
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Country selected = (Country) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(getApplicationContext(), CountryDetailActivity.class);
                intent.putExtra("selected_country",selected);
                intent.putExtra("countries",COUNTRIES);

                View v = getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                startActivity(intent);
                finish();
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

//        View v = getCurrentFocus();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    @Override
    protected void onResume() {
//      clear the text, because of this when you return to this page the EditText will be empty
//      instead of being filled with the previous country
//      Log.d("clear_test","edit now clear");
        super.onResume();
        AutoCompleteTextView editText = findViewById(R.id.autoCompleteCountry);
        editText.setText("");
    }


    public void goToCountryDetail(View view){
        EditText edit = findViewById(R.id.autoCompleteCountry);
        String countryInput = edit.getText().toString();
        Log.d("all_names",""+countryInput+' '+ COUNTRIES_STRING.contains(countryInput));

        // check if countryInput is in the ArrayList
        if(COUNTRIES_STRING.contains(countryInput)){
            Intent intent = new Intent(this, CountryDetailActivity.class);
            intent.putExtra("country_name",countryInput);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"This country doesn't exist",Toast.LENGTH_LONG).show();
            Toast.makeText(this,"Try again",Toast.LENGTH_LONG).show();
        }
    }

}
