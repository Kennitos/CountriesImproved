/*
Country.java

This class Country will be used to created instances of countries. It uses the following characteristics:
country name, capital, iso-code, region, sub-region, area, population, url of flag, languages, latitude
and its longitude.
note: I used an Override toString, so that the instances will be shown with a string, this is needed
for the autocompletion in Search- and CompareActivity

@ author        Kennet Botan
*/

package com.example.kenne.countries.Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Country implements Serializable {
    private String name, capital, iso2, iso3, region, subregion, flag;
    private int area, population;
    private Double lat, lng;
    private ArrayList<String> languages;


    // Constructor
    public Country(String name, String capital, String iso2, String iso3, String region, String subregion,
                   int area, int population, String flag, ArrayList<String> languages, Double lat,
                   Double lng){
        this.name = name;
        this.capital = capital;
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.region = region;
        this.subregion = subregion;
        this.area = area;
        this.population = population;
        this.flag = flag;
        this.languages = languages;
        this.lat = lat;
        this.lng = lng;
    }

    // This override function causes it to show 'Zimbabwe' instead of 'com.example.kenne.countries.Object.Country@127c0a'
    // when they are put into an ArrayList. This is a must for the autocompletion function!
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

    public String getIso2() {
        return iso2;
    }

    public String getIso3() { return iso3; }

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