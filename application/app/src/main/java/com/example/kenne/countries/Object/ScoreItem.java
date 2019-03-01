/*
ScoreItem.java

This class ScoreItem is used for storing the results of the quiz. It uses the following
characteristics: player name, score, regions, correct and incorrect answers, percentage of achieved
score of maximum score possible and total (the maximum score possible). There are two option to create
the ScoreItem, with regions, correct, incorrect as ArrayList<string>'s or as Strings

@ author        Kennet Botan
*/

package com.example.kenne.countries.Object;

import java.io.Serializable;
import java.util.ArrayList;

public class ScoreItem implements Serializable {

    private String player_name, regions_str, correct_str, incorrect_str;
    private int score, percentage, total;
    private ArrayList<String> regions, correct, incorrect;

    // Constructor
    public ScoreItem(String player_name, int score, ArrayList<String> regions, ArrayList<String> correct, ArrayList<String> incorrect, int percentage, int total){
        this.player_name = player_name;
        this.score = score;
        this.regions =  regions;
        this.correct = correct;
        this.incorrect = incorrect;
        this.percentage = percentage;
        this.total = total;
    }

    // Constructor
    public ScoreItem(String player_name, int score, String regions_str, String correct_str, String incorrect_str, int percentage, int total){
        this.player_name = player_name;
        this.score = score;
        this.regions_str = regions_str;
        this.correct_str = correct_str;
        this.incorrect_str = incorrect_str;
        this.percentage = percentage;
        this.total = total;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getScore() {
        return score;
    }

    public int getTotal(){
        return total;
    }

    public ArrayList<String> getRegions() {
        return regions;
    }

    public ArrayList<String> getCorrect() {
        return correct;
    }

    public ArrayList<String> getIncorrect() {
        return incorrect;
    }

    public String getRegions_str() {
        return regions_str;
    }

    public String getCorrect_str() {
        return correct_str;
    }

    public String getIncorrect_str() {
        return incorrect_str;
    }
}
