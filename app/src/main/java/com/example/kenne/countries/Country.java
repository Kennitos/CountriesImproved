package com.example.kenne.countries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Country implements Serializable {
    private String name, capital, iso, region, subregion, flag;
    private int area, population;

//    private static ArrayList<String> instances = new ArrayList();
//    private Integer area;

    public Country(String name, String capital, String iso, String region, String subregion, int area, int population, String flag){
        this.name = name;
        this.capital = capital;
        this.iso = iso;
        this.region = region;
        this.subregion = subregion;
        this.area = area;
        this.population = population;
        this.flag = flag;

//        instances.add(name);
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getIso() {
        return iso;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public String getFlag() {
        return flag;
    }

    public int getArea() {
        return area;
    }

    public int getPopulation() {
        return population;
    }
}

//
//    String name = testObject.getString("name");
//    String capital = testObject.getString("capital");
//    String iso = testObject.getString("alpha2Code");
//    String region = testObject.getString("region");
//    String subregion = testObject.getString("subregion");
//    String flag = testObject.getString("flag");
//    int population = testObject.getInt("population");
//    int area =