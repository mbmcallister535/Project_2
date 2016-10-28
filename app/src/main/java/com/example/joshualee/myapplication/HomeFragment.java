package com.example.joshualee.myapplication;

import android.content.Context;
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

        Place place0 = new Place();
        place0.setDistance("1000");
        place0.setName("Michael's House");

        Place place1 = new Place();
        place1.setDistance("5000");
        place1.setName("Fariz's House");

        Place place2 = new Place();
        place2.setDistance("10000");
        place2.setName("Josh's House");

        final Place[] places = new Place[3];
        places[0] = place0;
        places[1] = place1;
        places[2] = place2;



        View view = inflater.inflate(R.layout.fragment_home, container, false);

        placeListView = (ListView) view.findViewById(android.R.id.list);

        PlaceAdapter pAdapter = new PlaceAdapter(getActivity(), places);
        setListAdapter(pAdapter);

        placeListView.setAdapter(pAdapter);



        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
