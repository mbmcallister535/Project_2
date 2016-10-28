package com.example.joshualee.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

//import com.androidopentutorials.spfavorites.R;
//import com.androidopentutorials.spfavorites.adapter.ProductListAdapter;
//import com.androidopentutorials.spfavorites.beans.Product;
//import com.androidopentutorials.spfavorites.utils.SharedPreference;

public class HomeFragment extends ListFragment {

    List<Place> places;
    Activity activity;
    ListView placeListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int location, wifi, dining, seating, price, noise;
       /* Place place0 = new Place("m",10.0,10.0,10.0);
        place0.setDistance(1000);
        place0.setName("Michael's House");

        Place place1 = new Place("m",10.0,10.0,10.0);
        place1.setDistance(5000);
        place1.setName("Fariz's House");

        Place place2 = new Place("m",10.0,10.0,10.0);
        place2.setDistance(10000);
        place2.setName("Josh's House");

        final Place[] places = new Place[3];
        places[0] = place0;
        places[1] = place1;
        places[2] = place2;
        */
        SharedPreferences filter = getActivity().getSharedPreferences("filterPrefs",0);
        location = filter.getInt("locationSeekBar", 0);
        //Log.v("Location", Integer.toString(location));
        wifi = filter.getInt("wifiSeekBar", 0);
        dining = filter.getInt("diningSeekBar", 0);
        seating = filter.getInt("seatingSeekBar", 0);
        price = filter.getInt("priceSeekBar", 0);
        noise = filter.getInt("noiseSeekBar", 0);

        double user_latitude = ((MainActivity)getActivity()).getmLatitude();
        double user_longitude = ((MainActivity)getActivity()).getmLongitude();
        JReader j = new JReader(user_latitude,user_longitude);
        j.set_distances();
        j.sort_list_by_distance();
        j.set_filter_list(location,wifi,dining,seating,price,noise);
        j.sort_filter_by_distance();
        ArrayList<Place> places = j.return_list();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        placeListView = (ListView) view.findViewById(android.R.id.list);
        PlaceAdapter pAdapter = new PlaceAdapter(getActivity(), places);
        setListAdapter(pAdapter);

        placeListView.setAdapter(pAdapter);



        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
