package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView origin;
    private TextView description;
    private TextView ingredients;
    private TextView alsoKnown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        origin = findViewById(R.id.origin_tv);
        description = findViewById(R.id.description_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        alsoKnown = findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Set the place of origin and the description texts
        if ( !TextUtils.isEmpty(sandwich.getPlaceOfOrigin()) ) {
            origin.setText(sandwich.getPlaceOfOrigin());
        }
        if ( !TextUtils.isEmpty(sandwich.getDescription()) ) {
            description.setText(sandwich.getDescription());
        }

        //Set the alsoKnown text
        List<String> alsoKnownArray = sandwich.getAlsoKnownAs();
        StringBuilder alsoKnownString = new StringBuilder();
        for (int i = 0; i < alsoKnownArray.size(); i++){
            alsoKnownString.append(alsoKnownArray.get(i));
            if (i < alsoKnownArray.size() - 1){
                alsoKnownString.append(", ");
            }

        }
        if (alsoKnownArray.size() != 0) {
            alsoKnown.setText(alsoKnownString);
        }

        //Set the ingredients text
        List<String> ingredientsArray = sandwich.getIngredients();
        StringBuilder ingredientsString = new StringBuilder();
        for (int i = 0; i < ingredientsArray.size(); i++){
            ingredientsString.append(ingredientsArray.get(i));
            if (i < ingredientsArray.size() - 1){
                ingredientsString.append(", ");
            }
        }
        if (ingredientsArray.size() != 0) {
            ingredients.setText(ingredientsString);
        }

    }
}
