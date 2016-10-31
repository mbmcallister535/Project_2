package com.example.joshualee.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HomeFragment tab1 = new HomeFragment();
                return tab1;
            case 1:
                RecentFragment tab2 = new RecentFragment();
                return tab2;
            case 2:
                FavoriteFragment tab3 = new FavoriteFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}