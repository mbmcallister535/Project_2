package com.example.joshualee.myapplication;

/**
 * Created by miggle on 10/26/16.
 */
//import java.awt.List;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JReader {
    private ArrayList<Place> places = new ArrayList<Place>();
    private ArrayList<Place> filterPlaces = new ArrayList<Place>();
    private int length_list = 0;
    private int length_filter = 0;
    private double user_latitude;
    private double user_longitude;

    public JReader(double u_lat, double u_long)
    {


        user_latitude = u_lat;
        user_longitude = u_long;
        String str_lat = String.valueOf(u_lat);
        String str_long = String.valueOf(u_long);
        String photo_string = "";
        URL url;
        String id = "";
        String photo_url = "";
        String json_string = "";
        String json_data = "";
        String html= "";
        try{
            //String str_user_latitude = Double.toString(u_lat);
            //String str_user_longitude = Double.toString(u_long);
            url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+str_lat+","+str_long+"&radius=50000&type=cafe&key=AIzaSyB65gfarp2f8JQ5XykeRJdFTdw9DC6smrk");
            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                json_string += inputLine;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            JSONObject  jsonRootObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonRootObject.optJSONArray("results");
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.optString("name").toString();
                String rating = (jsonObject.optString("rating").toString());
                id = jsonObject.optString("id").toString();
                String price_level = jsonObject.optString("price_level").toString();
                JSONObject geometry = jsonObject.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");

                if (jsonObject.has("photos")) {
                    JSONArray photo = jsonObject.optJSONArray("photos");
                    String photo_reference = "";
                    int photo_size = photo.length();

                    if(photo_size > 0) {
                        JSONArray photoObject = jsonArray.optJSONArray(0);
                        html = photo.optJSONObject(0).optString("photo_reference").toString();
                        photo_url = "https://maps.googleapis.com/maps/api/place/photo?photoreference="+html+"&maxheight=400&key=AIzaSyBhnE7KFYA_ASATz_B94xYIT3Ubof0ubwY";
                        /*System.out.println(photo_url);
                        URL purl = new URL(photo_url);
                        URLConnection pc = purl.openConnection();
                        BufferedReader pin = new BufferedReader(new InputStreamReader(pc.getInputStream()));
                        String photoLine;
                        while ((photoLine = pin.readLine()) != null) {
                            photo_string += photoLine;

                        }*/
                        //System.out.println("123 please work: " + photo_string);
                    }
                }

        //String photo_reference = photo.optString("photo_reference").toString();
                double lat = Double.parseDouble(location.optString("lat").toString());
                double lng = Double.parseDouble(location.optString("lng").toString());
                String open_now;
                try{
                    JSONObject hours = jsonObject.getJSONObject("opening_hours");


                    open_now = hours.optString("open_now").toString();

                }
                catch(JSONException e){
                    open_now ="";
                }
                //String open_now = hours.optString("open_now").toString();
                json_data += "Node"+i+" : \n Name= "+ name +" \n lat= " +lat  + "\n lng= " + lng +"\n rating= " + rating +"\n open= "+open_now +"\n price= " + price_level + "\n";
                Place p = new Place(name,10,lat,lng,price_level,rating,photo_url,id);
                this.places.add(p);
                this.length_list++;
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
    public void set_filter_list(int l, int w, int d, int s, int p, int n )
    {
        for(int i = 0; i < length_list; i++)
        {
            Place temp = places.get(i);
            double dist = temp.getDistance();
            String checker = String.valueOf(dist);
            boolean wifi = temp.getWifi();
            String dining = temp.getDining();
            String seating = temp.getSeating();
            String price = temp.getPrice();
            String noise = temp.getNoise();
            String switch_clause = String.valueOf(l);
            int condition_counter = 0;
            switch (l){
                case 0:
                    if(dist <= 5)
                    {
                        condition_counter++;
                    }
                    break;
                case 1:
                    if(dist<=10){
                        condition_counter++;
                    }
                    break;
                case 2:
                    if(dist <= 15)
                    {
                        condition_counter++;
                    }
                    break;
            }
              switch(w)
            {
                case(0):
                    if(wifi == false)
                    {
                        condition_counter++;
                    }
                    break;
                case 1:
                    if(wifi == true)
                    {

                        condition_counter++;
                    }
                    break;
            }
            switch(d)
            {
            case 0:
                if(dining.equals("coffee"))
                {
                    condition_counter++;
                }
                break;
            case 1:
                if(dining.equals("food")) {
                    condition_counter++;
                }
                break;
            case 2:
                if(dining.equals("both"))
                {
                    condition_counter++;
                }
                break;

            }
            switch(s)
            {
                case 0:
                    if(dining.equals("indoor"))
                    {
                        condition_counter++;
                    }
                    break;
                case 1:
                    if(dining.equals("outdoor"))
                    {
                        condition_counter++;
                    }
                    break;
                case 2:
                    if(dining.equals("both"))
                    {
                        condition_counter++;
                    }
                    break;
            }
            switch(p)
            {
                case 0:
                    if(price.equals("$"))
                    {
                        condition_counter++;
                    }
                    break;
                case(1):
                    if(price.equals("$$"))
                    {
                        condition_counter++;
                    }
                    break;
                case(2):
                    if(price.equals("$$$"))
                    {
                        condition_counter++;
                    }
                    break;
            }
            switch(n)
            {
                case 0:
                    if(noise.equals ("quiet"))
                    {
                        condition_counter++;
                    }
                    break;
                case 1:
                    if(noise.equals("medium"))
                    {
                        condition_counter++;
                    }
                    break;
                case 2:
                    if(noise.equals("loud"))
                    {
                        condition_counter++;
                    }
                    break;
            }

            if(condition_counter == 6)
            {
                String con_counter = String.valueOf(condition_counter);
                filterPlaces.add(places.get(i));
                length_filter++;
            }
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
        }
    }
    public ArrayList<Place> return_list()
    {
        return places;
    }
    public ArrayList<Place> return_filter() {
        if (filterPlaces.size() == 0)
            return null;
        else
            return filterPlaces;
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
    public int return_filter_length()
    {
        return this.length_filter;
    }
    public void sort_filter_by_distance()
    {
        int j;
        boolean flag = true;
        Place temp;
        while(flag)
        {
            flag = false;
            for(j = 0; j < length_filter-1; j++)
            {
                if(filterPlaces.get(j).getDistance() > filterPlaces.get(j+1).getDistance())
                {
                    temp = filterPlaces.get(j);
                    filterPlaces.set(j,filterPlaces.get(j+1));
                    filterPlaces.set(j+1, temp);
                    flag = true;
                }
            }
        }
    }
}
