package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String JSON_KEY_NAME = "name";
    private static final String JSON_KEY_MAIN_NAME = "mainName";
    private static final String JSON_KEY_ALSO_KNOWN = "alsoKnownAs";
    private static final String JSON_KEY_ORIGIN = "placeOfOrigin";
    private static final String JSON_KEY_DESCRIPTION = "description";
    private static final String JSON_KEY_IMAGE = "image";
    private static final String JSON_KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        //If the JSON String is empty, return null
        if (json == null){
            return null;
        }

        try {
            //Create a JSONObject from the JSON String
            JSONObject jsonObject = new JSONObject(json);
            JSONObject names;
            String mainName = null;
            ArrayList<String> alsoKnownAs = null;
            String placeOfOrigin = null;
            String description = null;
            String image = null;
            ArrayList<String> ingredients = null;

            //Get the names
            if (jsonObject.has(JSON_KEY_NAME)){
                names = jsonObject.getJSONObject(JSON_KEY_NAME);
                if (names.has(JSON_KEY_MAIN_NAME)){
                    mainName = names.getString(JSON_KEY_MAIN_NAME);
                }
                if (names.has(JSON_KEY_ALSO_KNOWN)){
                    JSONArray alsoKnownAsJson = names.getJSONArray(JSON_KEY_ALSO_KNOWN);
                    alsoKnownAs = new ArrayList<>();
                    for (int i = 0; i < alsoKnownAsJson.length(); i++){
                        alsoKnownAs.add(alsoKnownAsJson.getString(i));
                    }
                }
            }

            //Get the place of origin, the description and the image path
            if (jsonObject.has(JSON_KEY_ORIGIN)){
                placeOfOrigin = jsonObject.getString(JSON_KEY_ORIGIN);
            }

            if (jsonObject.has(JSON_KEY_DESCRIPTION)){
                description = jsonObject.getString(JSON_KEY_DESCRIPTION);
            }

            if(jsonObject.has(JSON_KEY_IMAGE)){
                image = jsonObject.getString(JSON_KEY_IMAGE);
            }

            //Get the ingredients
            if (jsonObject.has(JSON_KEY_INGREDIENTS)){
                JSONArray ingredientsJson = jsonObject.getJSONArray(JSON_KEY_INGREDIENTS);
                ingredients = new ArrayList<>();
                for (int i = 0; i < ingredientsJson.length(); i++){
                    ingredients.add(ingredientsJson.getString(i));
                }
            }

            //Create a new Sandwich object from the data and return it
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e){
            e.printStackTrace();

        }
        return null;
    }
}
