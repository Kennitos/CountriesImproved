package com.example.kenne.countries;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Quiz {
    private String question_type, difficulty;
    private int score;
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
//            Log.d("select_countries","region: "+item.getRegion()+regions);
            if(regions.contains(item.getRegion())){
                Log.d("select_countries","country: "+item.getName()+" "+item.getRegion());
                quiz_countries.add(item);
            }
        }
        return  quiz_countries;
    }

    public ArrayList<Country> select10(){
        ArrayList<Country> random_countries = (ArrayList<Country>)quiz_countries.clone();
        Collections.shuffle(random_countries);
//        KAN DIT NIET IN 1 REGEL??
        ArrayList<Country> few_countries = new ArrayList<>();
        for(int i = 0; i <10; i++){
            few_countries.add(random_countries.get(i));
        }
//        Arrays.copyOf(random_countries,10);
//        ArrayList[] ab = Arrays.copyOf(new ArrayList[]{random_countries},10);
//        random_countries.slice(0, 10);
//        ArrayList<Country> random_countries;
//        random_countries = Arrays.copyOf(new ArrayList<Country>[]{quiz_countries}, 10);
        return few_countries;
    }
}
