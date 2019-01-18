package com.example.kenne.countries;

import java.io.Serializable;
import java.util.ArrayList;

public class ScoreItem implements Serializable {

    private String player_name;
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
}
