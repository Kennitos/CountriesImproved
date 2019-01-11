package com.example.kenne.countries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CompareActivity extends AppCompatActivity {

    Country selected1;
    Country selected2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Intent intent = getIntent();
        ArrayList<Country> COUNTRIES = (ArrayList) intent.getStringArrayListExtra("countries");

//      SEARCH 1
        AutoCompleteTextView editText1 = findViewById(R.id.autoCompleteCountry1);
        editText1.setText("");
        ArrayAdapter<Country> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, COUNTRIES);
        editText1.setAdapter(adapter1);
        editText1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView1, View view1, int i1, long l1) {
                selected1 = (Country) adapterView1.getAdapter().getItem(i1);
                if(selected1!=null & selected2!=null){
                    Log.d("check_test",""+selected1+selected2);
                    Intent intent = new Intent(getApplicationContext(), CompareCountryDetailActivity.class);
                    intent.putExtra("selected1",selected1);
                    intent.putExtra("selected2",selected2);
                    startActivity(intent);
                } else {
                    AutoCompleteTextView editText2 = findViewById(R.id.autoCompleteCountry2);
                    editText2.requestFocus();
//                    Toast.makeText(this,"Choose two existing countries",Toast.LENGTH_LONG).show();
                }
            }
        });

//      SEARCH 2
        AutoCompleteTextView editText2 = findViewById(R.id.autoCompleteCountry2);
        editText2.setText("");
        ArrayAdapter<Country> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, COUNTRIES);
        editText2.setAdapter(adapter2);
        editText2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView2, View view2, int i2, long l2) {
                selected2 = (Country) adapterView2.getAdapter().getItem(i2);
                if(selected1!=null & selected2!=null){
                    Log.d("check_test",""+selected1+selected2);
                    Intent intent = new Intent(getApplicationContext(), CompareCountryDetailActivity.class);
                    intent.putExtra("selected1",selected1);
                    intent.putExtra("selected2",selected2);
                    startActivity(intent);
                } else {
                    AutoCompleteTextView editText1 = findViewById(R.id.autoCompleteCountry1);
                    editText1.requestFocus();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AutoCompleteTextView editText1 = findViewById(R.id.autoCompleteCountry1);
        AutoCompleteTextView editText2 = findViewById(R.id.autoCompleteCountry2);
        editText1.setText("");
        editText2.setText("");
        selected1 = null;
        selected2 = null;
    }

    public void goToCompareCountryDetailActivity(View view){
        if(selected1!=null & selected2!=null){
            Intent intent = new Intent(this, CompareCountryDetailActivity.class);
            intent.putExtra("selected1",selected1);
            intent.putExtra("selected2",selected2);
            startActivity(intent);
        } else {
            Toast.makeText(this,"Choose two existing countries",Toast.LENGTH_LONG).show();
        }
    }
}
