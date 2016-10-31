package com.example.joshualee.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import java.io.InputStream;
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

    ArrayList<Place> places;
    Activity activity;
    ListView placeListView;
    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int location, wifi, dining, seating, price, noise;
        SharedPreferences filter = getActivity().getSharedPreferences("filterPrefs",0);
        location = filter.getInt("locationSeekBar", 0);
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
        places = j.return_filter();
        for(int i = 0; i < places.size(); i++)
        {
            Log.v("Hello from here",places.get(i).getName());
        }

        PlaceAdapter pAdapter = new PlaceAdapter(getActivity(), places);
        setListAdapter(pAdapter);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
       // DownloadImageTask down = new DownloadImageTask(j,inflater,container);
        //down.execute();



        return inflater.inflate(R.layout.fragment_home, container, false);
    }
   /* private class DownloadImageTask extends AsyncTask<String, Void, String> {
        ImageView bmImage;
        JReader j;
        LayoutInflater inflater;
        ViewGroup container;

        public DownloadImageTask(JReader a, LayoutInflater inflate,ViewGroup c) {
            j = a;
            inflater = inflate;
            container = c;
        }

        protected String doInBackground(String... urls) {

            for(int i = 0; i < j.return_filter_length(); i++)
            {
                Place temp = places.get(i);
                String photo_url = temp.getPhotoUrl();
                String urldisplay = photo_url;
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    System.out.println("InputStream " + in);
                    bitmapArray.add(BitmapFactory.decodeStream(in));

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
            return "true";
        }

        protected void onPostExecute() {
            //View view = inflater.inflate(R.layout.fragment_home, container, false);
            //placeListView = (ListView) view.findViewById(android.R.id.list);
            //PlaceAdapter pAdapter = new PlaceAdapter(getActivity(), places);
            //setListAdapter(pAdapter);

            //placeListView.setAdapter(pAdapter);
        }
    }*/
}
