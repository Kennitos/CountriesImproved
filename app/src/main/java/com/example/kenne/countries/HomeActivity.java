package com.example.kenne.countries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void goToRegion(View view){
        Intent intent = new Intent(this, RegionActivity.class);
        startActivity(intent);
    }

    public void goToSearchActivity(View view){
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }


}
