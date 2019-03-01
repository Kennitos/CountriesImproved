/*
DummyVariable.java

This class DummyVariables can create random integers that are needed for the quiz to create incorrect
integers for the Area and Population questions. It will create integers with a interval of 4*10^n.
For example the population is 16.459.221, it can return [12.000.000,16.000.000,20.000.000] or
[4.000.000,8.000.000,12.000.000,16.000.000]. This is randomized with restrictions, preventing the
creation of negative integers. (a population of -5.000.000 would raise some eyebrows)

@ author        Kennet Botan
*/

package com.example.kenne.countries.Object;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DummyIntegers {

    private int input_int,num1,num2,num3;
    private String sort,correct_int;
    private ArrayList<String> answerList;

    // Constructor
    public DummyIntegers(int input_int, String sort){
        this.input_int = input_int;
        this.sort = sort;
    }

    public void create(){
        int length = String.valueOf(input_int).length();
        // Round the integer on its first to digits, 45.789 will become 46.000
        // i4 will be the 'rounded' integer
        double i1 = (input_int / (Math.pow(10, length-2)));
        double i2 = Math.round(i1);
        double i3 = i2*(Math.pow(10, length-2));
        int i4 = (int) i3;

        // Randomize choosing the 'dummy' integers (the incorrect answers)
        Random rand = new Random();
        int rand_int;
        if(i2<5){
            rand_int = 0;
        } else if(i2<9){
            rand_int = rand.nextInt(2);
        } else if(i2<13){
            rand_int = rand.nextInt(3);
        } else {
            rand_int = rand.nextInt(4);
        }
        ArrayList<Integer> num = new ArrayList<>();
        if(rand_int==0){
            num1 = i4 + 4*(int)Math.pow(10, length-2);
            num2 = i4 + 8*(int)Math.pow(10, length-2);
            num3 = i4 + 12*(int)Math.pow(10, length-2);
        } if(rand_int==1){
            num1 = i4 + 4*(int)Math.pow(10, length-2);
            num2 = i4 + 8*(int)Math.pow(10, length-2);
            num3 = i4 - 4*(int)Math.pow(10, length-2);
        } if(rand_int==2){
            num1 = i4 + 4*(int)Math.pow(10, length-2);
            num2 = i4 - 4*(int)Math.pow(10, length-2);
            num3 = i4 - 8*(int)Math.pow(10, length-2);
        } if(rand_int==3){
            num1 = i4 - 4*(int)Math.pow(10, length-2);
            num2 = i4 - 8*(int)Math.pow(10, length-2);
            num3 = i4 - 12*(int)Math.pow(10, length-2);
        }

        // if the integer is made for the area question add the km2 unit at the end
        if(sort.equals("area")){
            correct_int = NumberFormat.getIntegerInstance().format(i4)+" km²";
        } else {
            correct_int = NumberFormat.getIntegerInstance().format(i4);
        }

        num.add(i4);
        num.add(num1);
        num.add(num2);
        num.add(num3);

        // sort the list integers ascending
        Collections.sort(num);

        answerList = new ArrayList<>();
        for(int index = 0; index < num.size(); index++) {
            if(sort.equals("area")){
                answerList.add(NumberFormat.getIntegerInstance().format(num.get(index))+" km²");
            } else {
                answerList.add(NumberFormat.getIntegerInstance().format(num.get(index)));
            }
        }
    }

    public String getCorrect_int() {
        return correct_int;
    }

    public ArrayList<String> getAnswerList() {
        return answerList;
    }
}
