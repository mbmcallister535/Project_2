package com.example.joshualee.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.BaseAdapter;
import android.content.Context;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.google.gson.Gson;


/**
 * Created by joshualee on 10/19/16.
 */
public class PlaceAdapter extends BaseAdapter{
    private Context myContext;
    ArrayList<Place> places;
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

    public View getView(final int pos, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final String position = Integer.toString(pos);
        final Place place = places.get(pos);

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(myContext).inflate(R.layout.list_item, null);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        //LayoutInflater inflater = getLayoutInflater()
            holder.titleView = (TextView) convertView.findViewById(R.id.nameView);
            holder.distanceView = (TextView) convertView.findViewById(R.id.distanceText);
            holder.placeCell = (RelativeLayout) convertView.findViewById(R.id.placeCell);
            ImageView fav_view = (ImageView) convertView.findViewById(R.id.empty_fav);
            ImageView banner_image = (ImageView) convertView.findViewById(R.id.bannerView);
            RatingBar rate_bar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            Button directions = (Button) convertView.findViewById(R.id.button);
            banner_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ActivityItem.class);
                    intent.putExtra("Place", new Gson().toJson(place));
                    v.getContext().startActivity(intent);
                }
            });
            directions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String directions_url = "http://maps.google.com/maps?daddr="+place.getLatitude()+","+place.getLongitude();
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(directions_url));
                    myContext.startActivity(intent);
                }
            });
            String rating = place.getRating();
            float d_rating = Float.parseFloat(rating);
            rate_bar.setRating(d_rating);
            fav_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preferences = myContext.getSharedPreferences("PRODUCT_APP", 0);
                    ArrayList<Place> fav_places = new ArrayList<Place>();
                    fav_places = sharedPreference.getFavorites(myContext);
                    preferences.edit().clear().commit();
                    boolean infavorites = false;
                    if(fav_places!=null){
                        for(int i=0; i<fav_places.size(); i++){
                            if(fav_places.get(i).getId().equals(place.getId())) {
                                fav_places.remove(i);
                                infavorites = true;
                                break;
                            }
                        }
                    }
                    if(fav_places != null){
                        if(!infavorites){
                            fav_places.add(place);
                        }
                        for(int n=0; n<fav_places.size(); n++){
                            sharedPreference.addFavorite(myContext, fav_places.get(n));
                        }
                    }
                    else{
                        sharedPreference.addFavorite(myContext, place);
                    }

                }
            });

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
