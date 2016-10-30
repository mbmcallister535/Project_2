package com.example.joshualee.myapplication;

import android.util.Log;

/**
 * Created by joshualee on 10/12/16.
 */
public class Place {
    private String name;
    private double distance;
    private double latitude;
    private double longitude;
    private boolean wifi = true;
    private String dining = "both";
    private String price;
    private String seating = "both";
    private String noise = "quiet";
    private String url = "www.placeholder.com";
    private boolean favorited = false;

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public Place(String n, double d,double lat,double lon,String p)
    {
        latitude = lat;
        longitude = lon;
        name = n;
        distance = d;
        if(p!="")
        {
            price = p;
        }
        else
        {
            price = "$$";
        }
    }
    public double getDistance() {
        String str_distance = Double.toString(distance);
        Log.v("Distance",str_distance);
        return this.distance;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }
    public void setDistance(double d)
    {
        this.distance = d;
    }
    public void setName(String n)
    {
        this.name = n;
    }
    public boolean getWifi()
    {
        return this.wifi;
    }
    public String getDining()
    {
        return this.dining;
    }
    public String getUrl()
    {
        return this.url;
    }
    public void printPlace()
    {
        System.out.println(name);
        System.out.println(distance);
    }
    public String getPrice()
    {
        return this.price;
    }
    public String getSeating()
    {
        return this.seating;
    }
    public String getNoise()
    {
        return this.noise;
    }
    public String getName()
    {
        return this.name;
    }
}
