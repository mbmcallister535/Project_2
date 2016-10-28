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

        /*Place place0 = new Place("m",10.0,10.0,10.0);
        place0.setDistance(1000);
        place0.setName("Michael's House");

        Place place1 = new Place("m",10.0,10.0,10.0);
        place1.setDistance(5000);
        place1.setName("Fariz's House");

        Place place2 = new Place("m",10.0,10.0,10.0);
        place2.setDistance(10000);
        place2.setName("Josh's House");

        Place[] places = new Place[3];
        places[0] = place0;
        places[1] = place1;
        places[2] = place2;
        */
        double user_latitude = ((MainActivity)getActivity()).getmLatitude();
        double user_longitude = ((MainActivity)getActivity()).getmLongitude();
        JReader j = new JReader(user_latitude,user_longitude);
        j.set_distances();
        j.sort_list_by_distance();
        ArrayList<Place> places = j.return_list();
        PlaceAdapter pAdapter = new PlaceAdapter(getActivity(), places);
        setListAdapter(pAdapter);

        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

}
