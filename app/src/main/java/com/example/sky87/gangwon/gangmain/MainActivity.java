package com.example.sky87.gangwon.gangmain;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.example.sky87.gangwon.R;
import com.example.sky87.gangwon.adapter.MyPagerAdapter;
import com.example.sky87.gangwon.fragment.OneFragment;
import com.example.sky87.gangwon.map.MyLocation;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        new MyLocation().chkGpsService(MainActivity.this);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CLICK_ONE_LIST");
        registerReceiver(broadcastReceiver, intentFilter);

    }

    @Override
    public void onBackPressed() {
        if (OneFragment.flag.equals("home")) {
            super.onBackPressed();
        } else {
            OneFragment.flag = "home";
            Intent noti = new Intent("NOTIFICATION");
            getApplicationContext().sendBroadcast(noti);
        }
    }

    @Override
    public void onClick(View v) {
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("CLICK_ONE_LIST")) {
                pager.setCurrentItem(2);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}