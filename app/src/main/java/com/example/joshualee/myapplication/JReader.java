package com.example.joshualee.myapplication;

/**
 * Created by miggle on 10/26/16.
 */
//import java.awt.List;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JReader {
    private ArrayList<Place> places = new ArrayList<Place>();
    private ArrayList<Place> filterPlaces = new ArrayList<Place>();
    private int length_list = 0;
    private double user_latitude = 41.600235;
    private double user_longitude = -93.6513452;
    public JReader(double u_lat, double u_long)
    {
        Log.v("Hi","THIS IS NEW");
        Log.v("hi", "Am I in here");

        URL url;
        String json_string = "";
        String json_data = "";
        try{
            url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=41.600235,-93.6513452&radius=50000&type=cafe&key=AIzaSyBhnE7KFYA_ASATz_B94xYIT3Ubof0ubwY");
            URLConnection yc = url.openConnection();
            Log.v("hi", "is it even here");
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                json_string += inputLine;
            }
            Log.v("hi","Got through here");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            JSONObject  jsonRootObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonRootObject.optJSONArray("results");
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.optString("name").toString();
                String rating = jsonObject.optString("rating").toString();
                String price_level = jsonObject.optString("price_level").toString();
                JSONObject geometry = jsonObject.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                double lat = Double.parseDouble(location.optString("lat").toString());
                double lng = Double.parseDouble(location.optString("lng").toString());
                String open_now;
                try{
                    JSONObject hours = jsonObject.getJSONObject("opening_hours");


                    open_now = hours.optString("open_now").toString();
                    //System.out.println(open_now);

                }
                catch(JSONException e){
                    open_now ="";
                }
                //String open_now = hours.optString("open_now").toString();
                json_data += "Node"+i+" : \n Name= "+ name +" \n lat= " +lat  + "\n lng= " + lng +"\n rating= " + rating +"\n open= "+open_now +"\n price= " + price_level + "\n";
                Place p = new Place(name,10,lat,lng);
                this.places.add(p);
                this.length_list++;
            }

        } catch (JSONException e) {
            System.out.println("UH OH");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.v("hi","end of constructor");
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    //Distance Formula found on http://www.geodatasource.com/developers/java
    public void print_data()
    {
        for(int i = 0; i < length_list; i++)
        {
            places.get(i).printPlace();
        }
    }
    public void set_distances()
    {
        for(int i = 0; i < length_list; i++)
        {
            Place p = places.get(i);
            double theta = user_longitude - p.getLongitude();
            double dist = Math.sin(deg2rad(user_latitude)) * Math.sin(deg2rad(p.getLatitude())) + Math.cos(deg2rad(user_latitude)) * Math.cos(deg2rad(p.getLatitude())) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            Place temp = places.get(i);
            temp.setDistance(dist);
            places.set(i, temp);
            String str = Double.toString(dist);
            Log.v("Hi", str);

        }
    }
    public ArrayList<Place> return_list()
    {
        return places;
    }

    //Bubble sort found on http://mathbits.com/MathBits/Java/arrays/Bubble.htm
    public void sort_list_by_distance()
    {
        int j;
        boolean flag = true;
        Place temp;
        while(flag)
        {
            flag = false;
            for(j=0; j< length_list-1;j++)
            {
                if(places.get(j).getDistance() > places.get(j+1).getDistance())
                {
                    temp = places.get(j);
                    places.set(j, places.get(j+1));
                    places.set(j+1, temp);
                    flag = true;
                }

            }

        }
    }
}
