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
    public Place(String n, double d,double lat,double lon)
    {
        latitude = lat;
        longitude = lon;
        name = n;
        distance = d;
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
    public void printPlace()
    {
        System.out.println(name);
        System.out.println(distance);
    }
    public String getName()
    {
        return this.name;
    }
}
