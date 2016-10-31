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
    private String rating;
    private String id;
    private String photo_url;
    private String url = "www.placeholder.com";
    public Place(String n, double d,double lat,double lon,String p,String r,String photo, String identification)
    {
        id = identification;
        latitude = lat;
        longitude = lon;
        name = n;
        photo_url = photo;
        if(r.length() > 0)
        {
            rating = r;
        }
        else
        {
            rating = "3.0";
        }
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

        return (double) Math.round(this.distance * 100.0)/100.0;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public String getId() { return this.id; }
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
    public String getPhotoUrl()
    {
        return this.photo_url;
    }
    public String getRating ()
    {
        return this.rating;
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
