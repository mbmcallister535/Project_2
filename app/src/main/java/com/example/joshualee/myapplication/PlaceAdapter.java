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

/**
 * Created by joshualee on 10/19/16.
 */
public class PlaceAdapter extends BaseAdapter{
    private Context myContext;
    private Place[] places;

    public PlaceAdapter(Context context, Place[] p)
    {
        places=p;
    }

    @Override
    public int getCount() {
        return places.length;
    }

    @Override
    public Object getItem(int position) {
        return places[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        Place place = places[position];

        if(convertView == null)
        {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(myContext).inflate(R.layout.list_item, null);
            holder.titleView = (TextView) convertView.findViewById(R.id.nameView);
            holder.distanceView = (TextView) convertView.findViewById(R.id.distanceText);
            holder.placeCell = (RelativeLayout) convertView.findViewById(R.id.placeCell);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        //set the certain text values for the corresponding files
        //4th then you can set the text in the view
        holder.titleView.setText(place.getName());
        holder.distanceView.setText(place.getDistance());

//        holder.userCell.setBackgroundColor(ContextCompat.getColor(myContext, R.color.whiteColor));

        return convertView;
    }

    private static class ViewHolder {
        TextView titleView;
        //2nd create another TextView dateView...etc
        TextView distanceView;
        RelativeLayout placeCell;
    }
}
