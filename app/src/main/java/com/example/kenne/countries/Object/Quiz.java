/*
Quiz.java

This class Quiz will be used to create the actual Quiz. There is a .selectCountries() that will
create an ArrayList<Countries> of countries that are selected based on the chosen region(s). The
.selectComplete(i) will create the questions based on the chosen characteristics, it will return a
JSONArray format.

@ author        Kennet Botan
*/

package com.example.kenne.countries.Object;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Quiz implements Serializable {
    private ArrayList<String> regions, characteristics;
    private ArrayList<Country> countries;
    private ArrayList<Country> quiz_countries;
    private String difficulty;

    // Constructor
    public Quiz(ArrayList<String> regions, ArrayList<String> characteristics, String difficulty, ArrayList<Country> countries){
        this.regions = regions;
        this.characteristics = characteristics;
        this.difficulty = difficulty;
        this.countries = countries;
    }

    // Select all the countries based on the chosen region(s) (let's say chosenA)
    public ArrayList<Country> selectCountries(){
        quiz_countries = new ArrayList<>();
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

    // Create 'dummy' countries for a country based on the difficulty of the quiz.
    // Easy - random countries of the same region.
    // Medium - random countries on the same subregion
    // Hard - random neighbouring countries (use characteristic 'borders')
    // If no borders (probably an Island) - other random countries with no borders in region/subregion

    public int selectDifficulty(){
        int k = 0;
        if(difficulty.equals("easy")){
            k = 1;
        }
        return k;
    }

    // Select all countries of based on their characteristic of the already selected countries prior
    // with .selectCountries. We basically "trim" down the selection.
    public JSONArray selectComplete(int x){
        ArrayList<Country> random_countries = (ArrayList<Country>)quiz_countries.clone();
        Collections.shuffle(random_countries);

        ArrayList<Country> random_order = new ArrayList<>();
        random_order.addAll(random_countries);
        Collections.shuffle(random_order);

        // Create a JSONArray and add a JSONObject for each characteristic (if the characteristic was chosen)
        // for each country
        JSONArray allQuestionsArray = new JSONArray();
        int y = 1;
        for(int i = 0; i < x; i++){
            Country random = random_countries.get(i);
            // If the characteristic 'name' was chosen, create a JSONObject and add it to the JSONArray
            // ,if not skip this characteristic and check the following characteristic (so on, so on)
            // This continues looping all the countries, creating the JSONArray
            if(characteristics.contains("name")){
                String question = "What is the name of this country?";
                String correct = random.getName();

                // Create 'dummy' answers (incorrect answers) and add them in an ArrayList with the
                // correct answer
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
                QuestionEntry questionEntry = new QuestionEntry(y,random.getName(),random.getRegion(),random.getSubregion()
                        ,random.getIso2(),random.getIso3(),"string",question, correct,new JSONArray(answerList));
                allQuestionsArray.put(questionEntry);
                Log.d("check_array",""+allQuestionsArray);
//                JSONObject entry = new JSONObject();
//                try {
//                    entry.put("id",y);
//                    entry.put("name",random.getName());
//                    entry.put("region",random.getRegion());
//                    entry.put("subregion",random.getSubregion());
//                    entry.put("iso",random.getIso2());
//                    entry.put("type","string");
//                    entry.put("question",question);
//                    entry.put("correct",correct);
//                    entry.put("answers", new JSONArray(answerList));
//                    allQuestionsArray.put(entry);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                y += 1;
            }
            // Repeat this process for the other characteristics, it has the same principal but differs
            // slightly
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
                    entry.put("iso",random.getIso2());
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

                // Use te created class DummyIntegers to create the dummy integers
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
                    entry.put("iso",random.getIso2());
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
                    entry.put("iso",random.getIso2());
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
                String correct_url = "https://www.countryflags.io/"+random.getIso2()+"/flat/64.png";

                Collections.shuffle(random_order);
                ArrayList<String> answerList = new ArrayList<>();
                answerList.add(correct_url);
                for (int c = 0; c < 6; c++) {
                    String answer_url = "https://www.countryflags.io/"+random_order.get(c).getIso2()+"/flat/64.png";
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
                    entry.put("iso",random.getIso2());
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
        // When done, return the created JSONArray with all the questions/answers of the Quiz
        return allQuestionsArray;
    }
}
