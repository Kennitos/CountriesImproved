package com.example.kenne.countries.Object;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DummyIntegers {

    private int input_int,num1,num2,num3;
    private String sort,correct_int;
    private ArrayList<String> answerList;

    public DummyIntegers(int input_int, String sort){
        this.input_int = input_int;
        this.sort = sort;
    }

    public void create(){
        int length = String.valueOf(input_int).length();

        double i1 = (input_int / (Math.pow(10, length-2)));
        double i2 = Math.round(i1);
        double i3 = i2*(Math.pow(10, length-2));
        int i4 = (int) i3;

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

        if(sort.equals("area")){
            correct_int = NumberFormat.getIntegerInstance().format(i4)+" km²";
        } else {
            correct_int = NumberFormat.getIntegerInstance().format(i4);
        }

        num.add(i4);
        num.add(num1);
        num.add(num2);
        num.add(num3);

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
