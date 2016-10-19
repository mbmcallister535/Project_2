package com.example.joshualee.myapplication;

import android.widget.BaseAdapter;
import android.content.Context;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import static java.lang.System.out;
import java.util.ArrayList;

/**
 * Created by joshualee on 10/18/16.
 */
public class PlaceAdapter extends BaseAdapter {
    private Context myContext;

    public PlaceAdapter(Context context, Place[] places)
    {
        myContext = context;

    }


    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        Place place = places[position];

        if(convertView == null)
        {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(myContext).inflate(R.layout.list_item, null);
            holder.distanceView = (TextView) convertView.findViewById(R.id.textView2);
            holder.starTextView = (TextView) convertView.findViewById(R.id.textView4);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        //set the certain text values for the corresponding files
        //4th then you can set the text in the view

        System.out.println(event.getTitle());
        holder.titleView.setText(place.getName());
        holder.distanceView.setText(place.getDistance());
        holder.userCell.setBackgroundColor(ContextCompat.getColor(myContext, R.color.whiteColor));
        }


//        if(event.getColor().toString().equals("Green"))
//        {
//          holder.userCell.setBackgroundColor(ContextCompat.getColor(myContext, R.color.greenColor));
//        }
//
//        if(event.getColor().toString().equals("Blue"))
//        {
//            holder.userCell.setBackgroundColor(ContextCompat.getColor(myContext, R.color.blueColor));
//        }
//        if(event.getColor().toString().equals("Red")) {
//            holder.userCell.setBackgroundColor(ContextCompat.getColor(myContext, R.color.redColor));
//        }
//        if(event.getColor().toString().equals("White")) {
//            holder.userCell.setBackgroundColor(ContextCompat.getColor(myContext, R.color.whiteColor));
//        }

        return convertView;
    }

    private static class ViewHolder {
        //2nd create another TextView dateView...etc
        TextView titleView;
        TextView distanceView;
        TextView starTextView;
        RelativeLayout placeCell;
    }
}
