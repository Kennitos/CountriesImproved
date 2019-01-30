/*
ScoresActivity.java

This activity will display all the achieved scores of the user in a ordered ListView. When the user
clicks on a scoreItem, the user will be directed to a other activity with more details of that
particular score.

@ author        Kennet Botan
*/

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

        // Initiate the process to load all the scores from the rester server
        ScoreRequest x = new ScoreRequest(this);
        x.getScore(this);
    }

    //
    @Override
    public void gotScores(ArrayList<ScoreItem> scoreArray) {
        try {
            // Sort the ListView filled with ScoreItems's descending on their percentage achieved
            // points of all possible points
            Collections.sort(scoreArray, new Comparator<ScoreItem>() {
                @Override
                public int compare(ScoreItem scoreItem1, ScoreItem scoreItem2) {
                    return scoreItem2.getPercentage()-scoreItem1.getPercentage();
                }
            });

            ScoreAdapter adapter = new ScoreAdapter(this, R.layout.score_items, scoreArray);
            ListView scoresView = findViewById(R.id.listScoreView);
            scoresView.setAdapter(adapter);

            // The OnItemClickListener will create a new intent with the scoreItem that was clicked
            // and its characteristics and sent it to the CurrentScoreActivity
            scoresView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ScoreItem scoreItem = (ScoreItem) adapterView.getItemAtPosition(i);
                    // create new intent
                    Intent intent = new Intent(getApplicationContext(), CurrentScoreActivity.class);
                    intent.putExtra("score", scoreItem.getScore());
                    intent.putExtra("regions",scoreItem.getRegions_str());
                    intent.putExtra("correct",scoreItem.getCorrect_str());
                    intent.putExtra("incorrect", scoreItem.getIncorrect_str());
                    intent.putExtra("percentage",scoreItem.getScore());
                    intent.putExtra("total_points",scoreItem.getTotal());
                    // Start the activity, but don't finish it. It must be possible for the user to
                    // back to the ScoresActivity to select another scoreItem instead of being send
                    // back to the HomeActivity.
                    startActivity(intent);
                }
            });
        }
        catch (Error e){
            e.printStackTrace();
            Log.d("highscores_test","Empyt arraylist highscores");
        }
    }

    @Override
    public void gotScoresError(String message) {
        Log.d("check_response","cd"+message);
    }
}
