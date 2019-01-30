package com.example.kenne.countries.Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Country implements Serializable {
    private String name, capital, iso, region, subregion, flag;
    private int area, population;
    private Double lat, lng;
    private ArrayList<String> languages;

//    private static ArrayList<String> instances = new ArrayList();
//    private Integer area;

    public Country(String name, String capital, String iso, String region, String subregion,
                   int area, int population, String flag, ArrayList<String> languages, Double lat,
                   Double lng){
        this.name = name;
        this.capital = capital;
        this.iso = iso;
        this.region = region;
        this.subregion = subregion;
        this.area = area;
        this.population = population;
        this.flag = flag;
        this.languages = languages;
        this.lat = lat;
        this.lng = lng;
    }

//    This override function causes it to show 'Zimbabwe' instead of 'com.example.kenne.countries.Object.Country@127c0a'
//    when they are put into an ArrayList. This is a must for the autocompletion function!
    @Override
    public String toString() {
        return name;
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

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
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