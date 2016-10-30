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

        Log.v("Hi","THIS IS NEW");
        Log.v("hi", "Am I in here");
        user_latitude = u_lat;
        user_longitude = u_long;
        String str_lat = String.valueOf(u_lat);
        String str_long = String.valueOf(u_long);
        String photo_string = "";
        URL url;
        String photo_url = "";
        String json_string = "";
        String json_data = "";
        String html= "";
        try{
            //String str_user_latitude = Double.toString(u_lat);
            //String str_user_longitude = Double.toString(u_long);
            url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+str_lat+","+str_long+"&radius=50000&type=cafe&key=AIzaSyBhnE7KFYA_ASATz_B94xYIT3Ubof0ubwY");
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
                String rating = (jsonObject.optString("rating").toString());
                String price_level = jsonObject.optString("price_level").toString();
                JSONObject geometry = jsonObject.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                System.out.println(jsonObject);

                if (jsonObject.has("photos")) {
                    JSONArray photo = jsonObject.optJSONArray("photos");
                    System.out.println(photo);
                    String photo_reference = "";
                    int photo_size = photo.length();

                    System.out.println("123" + photo_size);
                    if(photo_size > 0) {
                        System.out.println("123 we are here");
                        System.out.println("123 "+jsonArray.optJSONObject(0));
                        System.out.println("123 photos: "+photo.optJSONObject(0));
                        JSONArray photoObject = jsonArray.optJSONArray(0);
                        System.out.println("Photo Object: " + photoObject);
                        html = photo.optJSONObject(0).optString("photo_reference").toString();
                        //System.out.println("123 html: " + html);
                        //Log.v("photo_ref",photo_reference);
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
                    //System.out.println(open_now);

                }
                catch(JSONException e){
                    open_now ="";
                }
                //String open_now = hours.optString("open_now").toString();
                json_data += "Node"+i+" : \n Name= "+ name +" \n lat= " +lat  + "\n lng= " + lng +"\n rating= " + rating +"\n open= "+open_now +"\n price= " + price_level + "\n";
                Place p = new Place(name,10,lat,lng,price_level,rating,photo_url);
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
            Log.v("location",switch_clause);
            switch (l){
                case 0:
                    if(dist <= 5)
                    {
                        Log.v("location","here");
                        condition_counter++;
                    }
                case 1:
                    if(dist<=10){
                        condition_counter++;
                    }
                case 2:
                    if(dist <= 15)
                    {
                        condition_counter++;
                    }
            }
            switch(w)
            {

                case(0):
                    if(wifi == false)
                    {
                        condition_counter++;
                    }

                case 1:
                    if(wifi == true)
                    {
                        condition_counter++;
                    }



            }
            switch(d)
            {
                case 0:
                    if(dining == "coffee")
                    {
                        condition_counter++;
                    }
                case 1:
                    if(dining == "food") {
                        condition_counter++;
                    }
                case 2:
                    if(dining == "both")
                    {
                        condition_counter++;
                    }

            }
            switch(s)
            {
                case 0:
                    if(dining == "indoor")
                    {
                        condition_counter++;
                    }
                case 1:
                    if(dining == "outdoor")
                    {
                        condition_counter++;
                    }
                case 2:
                    if(dining == "both")
                    {
                        condition_counter++;
                    }
            }
            switch(p)
            {
                case 0:
                    if(price == "$")
                    {
                        condition_counter++;
                    }
                case(1):
                    if(price == "$$")
                    {
                        condition_counter++;
                    }
                case(2):
                    if(price == "$$$")
                    {
                        condition_counter++;
                    }
            }
            switch(n)
            {
                case 0:
                    if(noise == "quiet")
                    {
                        condition_counter++;
                    }
                case 1:
                    if(noise == "medium")
                    {
                        condition_counter++;
                    }
                case 2:
                    if(noise == "loud")
                    {
                        condition_counter++;
                    }
            }
            if(condition_counter == 7)
            {
                String con_counter = String.valueOf(condition_counter);
                Log.v("location", "hereyyyy");
                Log.v("location",con_counter);
                Log.v("location",temp.getName());
                filterPlaces.add(temp);
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
            Log.v("Hi", str);

        }
    }
    public ArrayList<Place> return_list()
    {
        return places;
    }
    public ArrayList<Place> return_filter() {return filterPlaces; }
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
