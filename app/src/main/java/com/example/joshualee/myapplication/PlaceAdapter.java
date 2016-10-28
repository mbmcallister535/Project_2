package com.example.joshualee.myapplication;

import android.util.Log;
import android.widget.BaseAdapter;
import android.content.Context;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import static java.lang.System.out;

/**
 * Created by joshualee on 10/19/16.
 */
public class PlaceAdapter extends BaseAdapter{
    private Context myContext;
    private ArrayList<Place> places;
    SharedPreference sharedPreference;
    SharedPreferences preferences;


    public PlaceAdapter(Context context, ArrayList<Place> p)
    {
        myContext = context;
        places=p;
        sharedPreference = new SharedPreference();
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int position) {
        return places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        Place place = places.get(position);

        if(convertView == null)
        {
            holder = new ViewHolder();

             convertView = LayoutInflater.from(myContext).inflate(R.layout.list_item, null);
            //LayoutInflater inflater = getLayoutInflater()
            holder.titleView = (TextView) convertView.findViewById(R.id.nameView);
            holder.distanceView = (TextView) convertView.findViewById(R.id.distanceText);
            holder.placeCell = (RelativeLayout) convertView.findViewById(R.id.placeCell);

<<<<<<< HEAD
=======
            ImageView fav_view = (ImageView) convertView.findViewById(R.id.empty_fav);
            fav_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    preferences = myContext.getSharedPreferences("PRODUCT_APP", 0);
//                    preferences.edit().clear().commit();
                    sharedPreference.addFavorite(myContext, places.get(position));
                    String temp = String.valueOf(position);
                    Log.v("position", temp);
//                    Log.v("name", sharedPreference.getFavorites(myContext).get(position).getName());
                }
            });
>>>>>>> e2d88c44270e45a95ea1cf1bbb5531ff63bbe3a3

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        //set the certain text values for the corresponding files
        //4th then you can set the text in the view
        holder.titleView.setText(place.getName());
        holder.distanceView.setText(Double.toString(place.getDistance()));
        return convertView;
    }

    private static class ViewHolder {
        TextView titleView;
        //2nd create another TextView dateView...etc
        TextView distanceView;
        RelativeLayout placeCell;
    }
}
