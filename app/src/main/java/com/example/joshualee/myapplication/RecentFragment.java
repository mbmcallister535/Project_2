package com.example.joshualee.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class RecentFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        double user_latitude = ((MainActivity)getActivity()).getmLatitude();
        double user_longitude = ((MainActivity)getActivity()).getmLongitude();
        JReader j = new JReader(user_latitude,user_longitude);
        j.set_distances();
        j.sort_list_by_distance();
        ArrayList<Place> places = j.return_list();

        if (places == null){
            return inflater.inflate(R.layout.favorite_error, container,false);
        }

        PlaceAdapter pAdapter = new PlaceAdapter(getActivity(), places);
        setListAdapter(pAdapter);

        return inflater.inflate(R.layout.fragment_recent, container, false);
    }

}
