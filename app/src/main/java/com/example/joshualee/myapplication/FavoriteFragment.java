package com.example.joshualee.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FavoriteFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreference sharedPreference;
        sharedPreference = new SharedPreference();

        ArrayList<Place> places = new ArrayList<Place>();

        places = sharedPreference.getFavorites(getActivity());

        if (places == null)
            return inflater.inflate(R.layout.favorite_error, container,false);

        PlaceAdapter pAdapter = new PlaceAdapter(getActivity(), places);
        setListAdapter(pAdapter);

        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

}
