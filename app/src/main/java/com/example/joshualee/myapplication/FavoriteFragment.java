package com.example.joshualee.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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

//        Place place0 = new Place();
//        place0.setDistance("1000");
//        place0.setName("Michael's House");
//
//        Place place1 = new Place();
//        place1.setDistance("5000");
//        place1.setName("Fariz's House");
//
//        Place place2 = new Place();
//        place2.setDistance("10000");
//        place2.setName("Josh's House");

        ArrayList<Place> aListplaces = new ArrayList<Place>();

        Place[] places = new Place[aListplaces.size()];

        aListplaces = sharedPreference.getFavorites(getActivity());

        places = aListplaces.toArray(places);

        PlaceAdapter pAdapter = new PlaceAdapter(getActivity(), places);
        setListAdapter(pAdapter);

        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

}
