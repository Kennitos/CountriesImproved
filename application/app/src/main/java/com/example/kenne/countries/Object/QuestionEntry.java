package com.example.kenne.countries.Object;

import org.json.JSONArray;

import java.util.ArrayList;

public class QuestionEntry {
    private int id;
    private String name, region, subregion, iso2, iso3, type, question, correct;
    private JSONArray answers;

    public QuestionEntry(int id, String name, String region, String subregion, String iso2, String iso3,
                         String type, String question, String correct, JSONArray answers){
        this.id = id;
        this.name = name;
        this.region = region;
        this.subregion = subregion;
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.type = type;
        this.question = question;
        this.correct = correct;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public String getIso2() {
        return iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrect() {
        return correct;
    }

    public JSONArray getAnswers() {
        return answers;
    }
}
