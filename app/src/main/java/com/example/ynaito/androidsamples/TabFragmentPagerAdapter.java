package com.example.ynaito.androidsamples;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private String TAG = TabFragmentPagerAdapter.class.getSimpleName();
    private CharSequence[] tabTitles = {"タブ1", "タブ2"};


    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount");
        return tabTitles.length;
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "getItemId");
        return super.getItemId(position);
    }

    @Override
    public Fragment getItem(int i) {
        Log.d(TAG, "getItem");
        switch (i) {
            case 0:
                return new Main1Fragment();
            case 1:
                return new Main2Fragment();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(TAG, "getPageTitle");
        return tabTitles[position];
    }
}
