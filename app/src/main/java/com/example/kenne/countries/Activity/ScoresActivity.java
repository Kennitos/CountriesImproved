package com.example.kenne.countries.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kenne.countries.Object.ScoreItem;
import com.example.kenne.countries.R;
import com.example.kenne.countries.Request.ScoreRequest;
import com.example.kenne.countries.Adapter.ScoreAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
            Collections.sort(scoreArray, new Comparator<ScoreItem>() {
                @Override
                public int compare(ScoreItem scoreItem1, ScoreItem scoreItem2) {
                    return scoreItem2.getPercentage()-scoreItem1.getPercentage();
                }
            });
//            Collections.sort(scoreArray, Collections.reverseOrder());


            ScoreAdapter adapter = new ScoreAdapter(this, R.layout.score_items, scoreArray);
            ListView scoresView = findViewById(R.id.listScoreView);
            scoresView.setAdapter(adapter);

            scoresView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String category = (String) adapterView.getItemAtPosition(i);
                    ScoreItem scoreItem = (ScoreItem) adapterView.getItemAtPosition(i);


                    Intent intent = new Intent(getApplicationContext(), CurrentScoreActivity.class);
                    intent.putExtra("score", scoreItem.getScore());
                    intent.putExtra("regions",scoreItem.getRegions_str());
                    intent.putExtra("correct",scoreItem.getCorrect_str());
                    intent.putExtra("incorrect", scoreItem.getIncorrect_str());
                    intent.putExtra("percentage",scoreItem.getScore());
                    intent.putExtra("total_points",scoreItem.getTotal());

                    startActivity(intent);
                }
            });


        }
        catch (Error e){
            Log.d("highscores_test","Empyt arraylist highscores");
        }
    }

    @Override
    public void gotScoresError(String message) {
        Log.d("check_response","cd"+message);
    }


}
