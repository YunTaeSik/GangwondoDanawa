package com.example.sky87.gangwon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sky87.gangwon.fragment.FourFragment;
import com.example.sky87.gangwon.fragment.OneFragment;
import com.example.sky87.gangwon.fragment.ThreeFragment;
import com.example.sky87.gangwon.fragment.TwoFragment;

/**
 * Created by sky87 on 2016-06-25.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES = {"Service", "NearMap", "Bloging", "Profile"};

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return OneFragment.newInstance(position);
        } else if (position == 1) {
            return TwoFragment.newInstance(position);
        } else if (position == 2) {
            return ThreeFragment.newInstance1(position);
        } else if (position == 3) {
            return FourFragment.newInstance();
        } else {
            return FourFragment.newInstance();
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}