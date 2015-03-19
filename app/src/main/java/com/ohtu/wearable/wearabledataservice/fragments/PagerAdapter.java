package com.ohtu.wearable.wearabledataservice.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Adapter class to populate views with fragments.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Returns fragment associated with given position.
     * @param arg0 Position of the item.
     * @return Fragment.
     */
    @Override
    public Fragment getItem(int arg0) {

        switch (arg0) {
            case 0:
                return new FragmentThree();
            case 1:
                return new FragmentOne();
            case 2:
                return new FragmentTwo();
            default:
                break;
        }
        return null;
    }

    /**
     * Returns the number of views available.
     * @return Number of fragments.
     */
    @Override
    public int getCount() {
        return 3;
    }


}
