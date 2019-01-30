package com.example.kenne.countries.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kenne.countries.Object.ScoreItem;
import com.example.kenne.countries.R;

import java.util.ArrayList;

public class ScoreAdapter extends ArrayAdapter {

    private ArrayList<ScoreItem> scoreItems;

    public ScoreAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ScoreItem> objects) {
        super(context, resource, objects);
        scoreItems = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = scoreItems.get(position).getPlayer_name();
        int score = scoreItems.get(position).getScore();
        int percentage = scoreItems.get(position).getPercentage();
        String regions = scoreItems.get(position).getRegions_str();



        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.score_items, parent, false);
        }
        TextView name_view = convertView.findViewById(R.id.scoreNameView);
        name_view.setText(name);

        TextView score_view = convertView.findViewById(R.id.scoreScoreView);
        score_view.setText(String.valueOf(score));

        TextView percentage_view = convertView.findViewById(R.id.scorePercentageView);
        percentage_view.setText(String.valueOf(percentage));

        TextView regions_view = convertView.findViewById(R.id.scoreRegionView);
        regions_view.setText(String.valueOf(regions));

        return convertView;
    }
}