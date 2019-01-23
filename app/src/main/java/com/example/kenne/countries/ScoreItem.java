package com.example.kenne.countries;

import java.io.Serializable;
import java.util.ArrayList;

public class ScoreItem implements Serializable {

    private String player_name, regions_str, correct_str, incorrect_str;
    private int score;
    private ArrayList<String> regions;
    private ArrayList<Country> correct, incorrect;

    public ScoreItem(String player_name, int score, ArrayList<String> regions, ArrayList<Country> correct, ArrayList<Country> incorrect){
        this.player_name = player_name;
        this.score = score;
        this.regions =  regions;
        this.correct = correct;
        this.incorrect = incorrect;
    }

    public ScoreItem(String player_name, int score, String regions_str, String correct_str, String incorrect_str){
        this.player_name = player_name;
        this.score = score;
        this.regions_str = regions_str;
        this.correct_str = correct_str;
        this.incorrect_str = incorrect_str;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<String> getRegions() {
        return regions;
    }

    public ArrayList<Country> getCorrect() {
        return correct;
    }

    public ArrayList<Country> getIncorrect() {
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
