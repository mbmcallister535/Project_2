package com.example.joshualee.myapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
//import com.androidopentutorials.spfavorites.beans.Product;
import com.google.gson.Gson;

/**
 * Created by joshualee on 10/26/16.
 */

public class SharedPreference {
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<Place> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Place place) {
        List<Place> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Place>();
        favorites.add(place);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Place place) {
        ArrayList<Place> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(place);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Place> getFavorites(Context context) {
        SharedPreferences settings;
        List<Place> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Place[] favoriteItems = gson.fromJson(jsonFavorites,
                    Place[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Place>(favorites);
        } else
            return null;

        return (ArrayList<Place>) favorites;
    }
}
