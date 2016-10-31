package com.example.joshualee.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

public class ActivityItem extends AppCompatActivity {
    Button directions;
    TextView name;
    TextView distance;
    RatingBar ratings;
    TextView money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        String activityPlaces = getIntent().getStringExtra("Place");
        name = (TextView) findViewById(R.id.nameView);
        distance = (TextView) findViewById(R.id.distanceText);
        ratings = (RatingBar) findViewById(R.id.ratingBar);
        money = (TextView) findViewById(R.id.moneyView);
        final Place place = new Gson().fromJson(activityPlaces,Place.class);
        directions = (Button) findViewById(R.id.button);
        distance.setText(String.valueOf(place.getDistance()));
        name.setText(place.getName());
        money.setText(place.getPrice());
        float d_rating = Float.parseFloat(place.getRating());

        ratings.setRating(d_rating);
        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String directions_url = "http://maps.google.com/maps?daddr="+place.getLatitude()+","+place.getLongitude();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(directions_url));
                startActivity(intent);
            }
        });
    }
}
