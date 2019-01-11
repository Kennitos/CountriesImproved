package com.example.kenne.countries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
            }
        });

    }
}
