package com.example.joshualee.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Place place0 = new Place();
        place0.setDistance("1000");
        place0.setName("Michael's House");

        Place place1 = new Place();
        place1.setDistance("5000");
        place1.setName("Fariz's House");

        Place place2 = new Place();
        place2.setDistance("10000");
        place2.setName("Josh's House");

        Place[] places = new Place[3];
        places[0] = place0;
        places[1] = place1;
        places[2] = place2;

        PlaceAdapter pAdapter = new PlaceAdapter(getActivity(), places);
        setListAdapter(pAdapter);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
