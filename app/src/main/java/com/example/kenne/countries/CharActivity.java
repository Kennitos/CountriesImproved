package com.example.kenne.countries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CharActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char);
    }

    public void goToDifficulty (View view){
        Intent intent = new Intent(this , DifficultyActivity.class);
        startActivity(intent);
        finish();
    }
}
