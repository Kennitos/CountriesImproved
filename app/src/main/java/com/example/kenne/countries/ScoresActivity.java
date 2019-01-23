package com.example.kenne.countries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity implements ScoreRequest.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        ScoreRequest x = new ScoreRequest(this);
        x.getScore(this);
    }

    @Override
    public void gotScores(ArrayList<ScoreItem> scoreArray) {
        Log.d("check_response","bc"+scoreArray);
        try {
            ScoreAdapter adapter = new ScoreAdapter(this, R.layout.score_items, scoreArray);
            ListView listView = findViewById(R.id.listScoreView);
            listView.setAdapter(adapter);
        }
        catch (Error e){
            Log.d("highscores_test","Empyt arraylist highscores");
        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, categories);
//        ListView listView = findViewById(R.id.listScoreView);
//        listView.setAdapter(adapter);
    }

    @Override
    public void gotScoresError(String message) {
        Log.d("check_response","cd"+message);
    }
}
