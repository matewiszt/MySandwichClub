package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        //If the JSON String is empty, return null
        if (json == null){
            return null;
        }

        try {
            //Create a JSONObject from the JSON String
            JSONObject jsonObject = new JSONObject(json);

            //Get the names
            JSONObject names = jsonObject.getJSONObject("name");
            String mainName = names.getString("mainName");
            JSONArray alsoKnownAsJson = names.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJson.length(); i++){
                alsoKnownAs.add(alsoKnownAsJson.getString(i));
            }

            //Get the place of origin, the description and the image path
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");

            //Get the ingredients
            JSONArray ingredientsJson = jsonObject.getJSONArray("ingredients");
            ArrayList<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJson.length(); i++){
                ingredients.add(ingredientsJson.getString(i));
            }

            //Create a new Sandwich object from the data and return it
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e){
            e.printStackTrace();

        }
        return null;
    }
}
