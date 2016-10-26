package com.example.joshualee.myapplication;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import static android.R.id.button1;


public class MainActivity extends AppCompatActivity {

    private ImageButton button1;
    private SeekBar seekbarLoc, seekbarWif, seekbarDin, seekbarSea, seekbarPri, seekbarNoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setIcon(R.drawable.mycornerlogo_01_24px_2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        button1 = (ImageButton) findViewById(R.id.menu_button);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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
                SeekBar[] seekbarArray = {seekbarLoc, seekbarWif, seekbarDin, seekbarSea, seekbarPri, seekbarNoi};

                for (int k=0; k < seekbarArray.length; k++){
                    SeekBar temp = seekbarArray[k];
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
                            Toast.makeText(MainActivity.this, "progress is updated", Toast.LENGTH_SHORT).show();
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

    // menu button using app bar support design
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter, menu);
        return true;
    }*/
}