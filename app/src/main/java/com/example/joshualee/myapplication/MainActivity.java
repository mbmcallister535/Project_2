package com.example.joshualee.myapplication;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import static java.sql.Types.INTEGER;

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    protected static final String TAG = "MainActivity";
    private ImageButton button1;
    private SeekBar seekbarLoc, seekbarWif, seekbarDin, seekbarSea, seekbarPri, seekbarNoi;
    protected GoogleApiClient mGoogleApiClient;
    private SharedPreferences filter;
    Location mLastLocation;
    int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION, k;
    double mLatitude, mLongitude;
    String[] filterPrefName = {"locationSeekBar", "wifiSeekBar",
            "diningSeekBar", "seatingSeekBar", "priceSeekBar", "noiseSeekBar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        buildGoogleApiClient();

        button1 = (ImageButton) findViewById(R.id.menu_button);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                popupWindowCreate();
                final SeekBar[] seekbarArray = {seekbarLoc, seekbarWif, seekbarDin, seekbarSea, seekbarPri, seekbarNoi};
                for (k=0; k < seekbarArray.length;k++){
                    SeekBar temp = seekbarArray[k];
                    filter = getSharedPreferences("filterPrefs",0);
                    int num = filter.getInt(filterPrefName[k], temp.getProgress());
                    temp.setProgress(num);
                    temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        int changedProgress = 0;

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            changedProgress = progress;
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            int counter;
                            //Loop to find the index for seekBar corresponding to filterPrefName at the bottom
                            for (counter = 0; counter < seekbarArray.length; counter++) {
                                if (seekBar == seekbarArray[counter])
                                    break;
                            }

                            SharedPreferences.Editor editor = filter.edit();
                            editor.putInt(filterPrefName[counter], changedProgress);
                            editor.apply();
                        }

                    });
                }
            }
        });

        final int[] icons = new int[] {
            R.drawable.ic_home,
            R.drawable.ic_recent,
            R.drawable.ic_favorite
        };

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int k=0; k < icons.length; k++) {
            tabLayout.addTab(tabLayout.newTab().setIcon(icons[k]));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
            Toast.makeText(this, "Location Detected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Location Detected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }


    public void popupWindowCreate(){
        LayoutInflater layoutInflater = getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.popup_filter, null);
        PopupWindow popupWindow = new PopupWindow(MainActivity.this);
        int popupWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        int popupHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        popupWindow.setHeight(popupHeight);
        popupWindow.setWidth(popupWidth);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(popupView);
        popupWindow.setOutsideTouchable(true);
        int xOffset = -5;
        int yOffset = 210;
        popupWindow.showAsDropDown(popupView, xOffset, yOffset);
        seekbarLoc = (SeekBar) popupView.findViewById(R.id.locationSeekBar);
        seekbarWif = (SeekBar) popupView.findViewById(R.id.wifiSeekBar);
        seekbarDin = (SeekBar) popupView.findViewById(R.id.diningSeekBar);
        seekbarSea = (SeekBar) popupView.findViewById(R.id.seatingSeekBar);
        seekbarPri = (SeekBar) popupView.findViewById(R.id.priceSeekBar);
        seekbarNoi = (SeekBar) popupView.findViewById(R.id.noiseSeekBar);
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }
    /*public String getName(SeekBar seekBar){
        int temp = seekBar.getId();
        switch (temp) {
            case
        }


    }*/
}