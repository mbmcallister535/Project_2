package com.example.joshualee.myapplication;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.Rating;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    protected static final String TAG = "MainActivity";
    private ImageButton button1;
    SeekBar seekbarLoc, seekbarWif, seekbarDin, seekbarSea, seekbarPri, seekbarNoi;
    protected GoogleApiClient mGoogleApiClient;
    private SharedPreferences filter;
    Location mLastLocation;
    int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION, k;
    double mLatitude = 41.600235;
    double mLongitude = -93.6513452;
    TabLayout tabLayout;
    PagerAdapter adapter;
    String[] filterPrefName = {"locationSeekBar", "wifiSeekBar",
            "diningSeekBar", "seatingSeekBar", "priceSeekBar", "noiseSeekBar"};
    TextView header;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int k=0; k < icons.length; k++) {
            tabLayout.addTab(tabLayout.newTab().setIcon(icons[k]));
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                header = (TextView) findViewById(R.id.header);
                switch (tab.getPosition()) {
                    case 0:
                        header.setText("HOME");
                        break;
                    case 1:
                        header.setText("RECENT");
                        break;
                    case 2:
                        header.setText("FAVORITES");
                }
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

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // used if user wants to see explanation for accessing location
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                // Get permission for accessing location
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            // This will get the latitude and longitude of the device. If location is null, the latitude
            // and longitude has been declared with set location.
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

    // Creates the popup window for the filter menu
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
        int xOffset = -40;
        int yOffset = 210;
        popupWindow.showAsDropDown(popupView, xOffset, yOffset);
        seekbarLoc = (SeekBar) popupView.findViewById(R.id.locationSeekBar);
        seekbarWif = (SeekBar) popupView.findViewById(R.id.wifiSeekBar);
        seekbarDin = (SeekBar) popupView.findViewById(R.id.diningSeekBar);
        seekbarSea = (SeekBar) popupView.findViewById(R.id.seatingSeekBar);
        seekbarPri = (SeekBar) popupView.findViewById(R.id.priceSeekBar);
        seekbarNoi = (SeekBar) popupView.findViewById(R.id.noiseSeekBar);

       popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener(){
            @Override
            public void onDismiss() {
                // Reload current fragment
                Fragment frg = null;
                frg = adapter.getItem(tabLayout.getSelectedTabPosition());
                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
                adapter.notifyDataSetChanged();

            }
        });
    }

    // accessor for user's latitude
    public double getmLatitude() {
        return mLatitude;
    }

    // accessor for user's longitude
    public double getmLongitude() {
        return mLongitude;
    }


}