/*
MD%Encryption.java

This class MD5Encryption will be used to create encryption of strings used for the wikipedia urls.
It uses MD5 encrytion for the string "{county_name}_in_{region}.svg" and uses the first two letters
of that encryption:

https://upload.wikimedia.org/wikipedia/commons/thumb/{first}/{first_two}/"{country_name}_in_{region}
.svg/1051px-{country_name}_in_{region}.svg.png

The class uses the country name, region, subregion as input and it will return the complete url with
.CreateEncryption. However there are some example in which I need to hardcode the MD5 solution.

@ author        Kennet Botan
*/

package com.example.kenne.countries;

import android.util.Log;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionMD5 implements Serializable{
    // Create the variables that will be used through the whole activity;
    private String name;
    private String region;
    private String subregion;
    private String complete_url;

    // Constructor
    public EncryptionMD5(String name, String region, String subregion){
        this.name = name;
        this.region = region;
        this.subregion = subregion;
    }

    // Removes whitespace at the end of a string
    private static String removeLastChar(String str) {
        Character lastChar = str.charAt(str.length()-1);
        if(Character.isWhitespace(lastChar)){
            return str.substring(0, str.length() - 1);
        }
        else { return str; }
    }

    // The method to create the complete_url
    String CreateEncryption() {
        // Split the name and remove whitespace at the end of the string
        String[] split_name = name.split("[(\\)]");
        String lastCharSpace = removeLastChar(split_name[0]);
        String end_url;

        // Hardcode for important countries
        if (region.equals("Europe")) {
            end_url = lastCharSpace.replaceAll(" ", "_") + "_in_Europe.svg";
            if (name.equals("United Kingdom of Great Britain and Northern Ireland")) {
                end_url = "United_Kingdom_in_Europe.svg";
            }
            if (name.equals("Russian Federation")) {
                end_url = "Russia_in_Europe.svg";
            }
            if (name.equals("Republic of Kosovo")) {
                end_url = "Kosovo_in_Europe_(de-facto).svg";
            }
            if (name.equals("United States of America")) {
                end_url = "United_States_in_North_America.svg";
            }
        } else if (region.equals("Africa")) {
            end_url = lastCharSpace.replaceAll(" ", "_") + "_in_Africa.svg";
        } else if (subregion.equals("South America")) {
            end_url = lastCharSpace.replaceAll(" ", "_") + "_in_South_America.svg";
        } else if (subregion.equals("Northern America") || subregion.equals("Central America")) {
            end_url = lastCharSpace.replaceAll(" ", "_") + "_in_North_America.svg";
        } else if (subregion.equals("Caribbean")) {
            end_url = lastCharSpace.replaceAll(" ", "_") + "_in_North_America.svg";
        } else if (region.equals("Oceania")) {
            end_url = lastCharSpace.replaceAll(" ", "_") + "_in_Oceania.svg";
        } else {
            end_url = lastCharSpace.replaceAll(" ", "_") + "_in_its_region.svg";
        }
        try {
            // Encrypt the end_url
            // bron r.65-69 - https://stackoverflow.com/questions/3934331/how-to-hash-a-string-in-android
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(end_url.getBytes(Charset.forName("US-ASCII")), 0, end_url.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hashString = String.format("%0" + (magnitude.length << 1) + "x", bi);

            // Get the first and first two characters of the encryption
            String first = Character.toString(hashString.charAt(0));
            String first_two = first + Character.toString(hashString.charAt(1));

            complete_url = "https://upload.wikimedia.org/wikipedia/commons/thumb/" + first + "/" + first_two + "/" + end_url + "/700px-" + end_url + ".png";
            // Example of possible complete_url
            // https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Netherlands_in_Europe.svg/1051px-Netherlands_in_Europe.svg.png

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // return the url
        return complete_url;
    }
}


