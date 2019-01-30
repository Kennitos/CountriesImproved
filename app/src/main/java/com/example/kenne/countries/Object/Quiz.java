package com.example.kenne.countries.Object;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Quiz implements Serializable {
    private String question_type, difficulty;
    private int score, num1, num2, num3;
    private ArrayList<String> regions, characteristics;
    private ArrayList<Country> countries;
    private ArrayList<Country> quiz_countries;

    {
        // instance initializer; runs before any constructor
        score = 0;
        quiz_countries = new ArrayList<>();
        clear();
    }

    public Quiz(String question_type, String difficulty, ArrayList<String> regions, ArrayList<String> characteristics, ArrayList<Country> countries){
        this.question_type = question_type;
        this.difficulty = difficulty;
        this.regions = regions;
        this.characteristics = characteristics;
        this.countries = countries;
    }

    public void clear(){
        score = 0;
//        ...
//        ...
    }

    public ArrayList<Country> selectCountries(){
        for(int i = 0; i < countries.size(); i++){
            Country item = countries.get(i);
            if(regions.contains(item.getRegion())){
                quiz_countries.add(item);
            }
            if(regions.contains(item.getSubregion())){
                quiz_countries.add(item);
            }
            if(regions.contains("North America")&(item.getSubregion().equals("Northern America")|| item.getSubregion().equals("Central America")
                    || item.getSubregion().equals("Caribbean"))){
                quiz_countries.add(item);
            }
        }
        return  quiz_countries;
    }

    public ArrayList<Country> select(int x){
        ArrayList<Country> random_countries = (ArrayList<Country>)quiz_countries.clone();
        Collections.shuffle(random_countries);
//        KAN DIT NIET IN 1 REGEL??
        ArrayList<Country> few_countries = new ArrayList<>();
        for(int i = 0; i <x; i++){
            few_countries.add(random_countries.get(i));
        }
//        Arrays.copyOf(random_countries,10);
//        ArrayList[] ab = Arrays.copyOf(new ArrayList[]{random_countries},10);
//        random_countries.slice(0, 10);
//        ArrayList<Country> random_countries;
//        random_countries = Arrays.copyOf(new ArrayList<Country>[]{quiz_countries}, 10);
        return few_countries;
    }

    public JSONArray selectComplete(int x){
        ArrayList<Country> random_countries = (ArrayList<Country>)quiz_countries.clone();
        Collections.shuffle(random_countries);

        ArrayList<Country> random_order = new ArrayList<>();
        random_order.addAll(random_countries);
        Collections.shuffle(random_order);


        JSONArray allQuestionsArray = new JSONArray();
        int y = 1;
        for(int i = 0; i < x; i++){
            Country random = random_countries.get(i);
            if(characteristics.contains("name")){
                String question = "What is the name of this country?";
                String correct = random.getName();

                Collections.shuffle(random_order);
                ArrayList<String> answerList = new ArrayList<>();
                answerList.add(correct);
                for (int c = 0; c < 6; c++) {
                    if (!answerList.contains(random_order.get(c).getName())) {
                        if(answerList.size()<4) {
                            answerList.add(random_order.get(c).getName());
                        }
                    }
                }
                Collections.shuffle(answerList);

                JSONObject entry = new JSONObject();
                try {
                    entry.put("id",y);
                    entry.put("name",random.getName());
                    entry.put("region",random.getRegion());
                    entry.put("subregion",random.getSubregion());
                    entry.put("iso",random.getIso());
                    entry.put("type","string");
                    entry.put("question",question);
                    entry.put("correct",correct);
                    entry.put("answers", new JSONArray(answerList));
                    allQuestionsArray.put(entry);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                y += 1;
            }
            if(characteristics.contains("capital")){
                String question = "What is the capital of "+random.getName()+"?";
                String correct = random.getCapital();

                Collections.shuffle(random_order);
                ArrayList<String> answerList = new ArrayList<>();
                answerList.add(correct);
                for (int c = 0; c < 6; c++) {
                    if (!answerList.contains(random_order.get(c).getCapital())) {
                        if(answerList.size()<4) {
                            answerList.add(random_order.get(c).getCapital());
                        }
                    }
                }
                Collections.shuffle(answerList);

                JSONObject entry = new JSONObject();
                try {
                    entry.put("id",y);
                    entry.put("name",random.getName());
                    entry.put("region",random.getRegion());
                    entry.put("subregion",random.getSubregion());
                    entry.put("iso",random.getIso());
                    entry.put("type","string");
                    entry.put("question",question);
                    entry.put("correct",correct);
                    entry.put("answers",new JSONArray(answerList));
                    allQuestionsArray.put(entry);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                y += 1;
            }
            if(characteristics.contains("population")){
                String question = "What is the population of "+random.getName()+"?";

                DummyIntegers dummyIntegers = new DummyIntegers(random.getPopulation(),"pop");
                dummyIntegers.create();
                String correct_int = dummyIntegers.getCorrect_int();
                ArrayList<String> answerList = dummyIntegers.getAnswerList();

                JSONObject entry = new JSONObject();
                try {
                    entry.put("id",y);
                    entry.put("name",random.getName());
                    entry.put("region",random.getRegion());
                    entry.put("subregion",random.getSubregion());
                    entry.put("iso",random.getIso());
                    entry.put("type","pop");
                    entry.put("question",question);
                    entry.put("correct",correct_int);
                    entry.put("answers",new JSONArray(answerList));
                    allQuestionsArray.put(entry);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                y += 1;
            }
            if(characteristics.contains("area")){
                String question = "What is the area of "+random.getName()+"?";

                DummyIntegers dummyIntegers = new DummyIntegers(random.getArea(),"area");
                dummyIntegers.create();
                String correct_int = dummyIntegers.getCorrect_int();
                ArrayList<String> answerList = dummyIntegers.getAnswerList();

                JSONObject entry = new JSONObject();
                try {
                    entry.put("id",y);
                    entry.put("name",random.getName());
                    entry.put("region",random.getRegion());
                    entry.put("subregion",random.getSubregion());
                    entry.put("iso",random.getIso());
                    entry.put("type","area");
                    entry.put("question",question);
                    entry.put("correct",correct_int);
                    entry.put("answers",new JSONArray(answerList));
                    allQuestionsArray.put(entry);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                y += 1;
            }
            if(characteristics.contains("flag")){
                String question = "What is the the flag of "+random.getName()+"?";
                String correct_url = "https://www.countryflags.io/"+random.getIso()+"/flat/64.png";

                Collections.shuffle(random_order);
                ArrayList<String> answerList = new ArrayList<>();
                answerList.add(correct_url);
                for (int c = 0; c < 6; c++) {
                    String answer_url = "https://www.countryflags.io/"+random_order.get(c).getIso()+"/flat/64.png";
                    if (!answerList.contains(answer_url)) {
                        if(answerList.size()<4) {
                            answerList.add(answer_url);
                        }
                    }
                }
                Collections.shuffle(answerList);

                JSONObject entry = new JSONObject();
                try {
                    entry.put("id",y);
                    entry.put("name",random.getName());
                    entry.put("region",random.getRegion());
                    entry.put("subregion",random.getSubregion());
                    entry.put("iso",random.getIso());
                    entry.put("type","img");
                    entry.put("question",question);
                    entry.put("correct",correct_url);
                    entry.put("answers", new JSONArray(answerList));
                    allQuestionsArray.put(entry);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                y += 1;
            }
        }
        Log.d("jsonarray",""+allQuestionsArray);
        return allQuestionsArray;
    }
}