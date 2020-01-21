package com.example.sky87.gangwon.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sky87.gangwon.R;
import com.example.sky87.gangwon.adapter.MyListAdapter;
import com.example.sky87.gangwon.dialog.LoadingDailog;
import com.example.sky87.gangwon.json.JsonManager_DB;
import com.example.sky87.gangwon.json.JsonManager_Web;
import com.example.sky87.gangwon.map.MapViewing;
import com.example.sky87.gangwon.map.MyLocation;
import com.example.sky87.gangwon.util.Contact;
import com.example.sky87.gangwon.util.FloatingAction;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sky87 on 2016-06-25.
 */
public class OneFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_POSITION = "position";

    private int position;
    private ViewGroup issuelayout;
    private LinearLayout restaurant_layout;
    private LinearLayout wifi_layout;
    private LinearLayout parking_layout;
    private LinearLayout pension_layout;
    private LinearLayout hotel_layout;
    public static String flag = "home";
    private JsonManager_Web jsonManagerWeb;
    private JsonManager_DB jsonManagerDB;


    LinearLayout MainLayout;
    LinearLayout MapLayout;
    LinearLayout MovingLayout;
    int viewHeight;
    DisplayMetrics dm;
    ListView mListView;
    MyListAdapter mMyListAdapter;

    ArrayList DataArrList;
    int widthPixels;

    MyLocation myLocation;

    int DeliMinHeight;
    int DeliMaxHeight;
    int firstHeight;
    int TabHeight;
    int TitleHeight;
    private MapView mapView;
    MapViewing mMapViewing;

    Location location;
    double lat; // 위도
    double lon; // 경도

    AssetManager assetManager;
    private View include_layout;
    private Button select_btn;
    private LinearLayout floating_under_layout;
    private Button[] floating_under_btn = new Button[18];
    private boolean FloatingBtn_Flag = true;

    public static OneFragment newInstance(int position) {
        OneFragment fragment = new OneFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        jsonManagerWeb = new JsonManager_Web();
        jsonManagerDB = new JsonManager_DB();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("NOTIFICATION");
        getContext().registerReceiver(mRecevier, intentFilter);
        dm = getContext().getResources().getDisplayMetrics();
        widthPixels = dm.widthPixels;
        TabHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, dm);
        TitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, dm);
        viewHeight = dm.heightPixels - TabHeight - TitleHeight;
        DeliMinHeight = (int) (viewHeight * 0.05);
        DeliMaxHeight = (int) (viewHeight * 0.40);
        myLocation = new MyLocation();
        mMapViewing = new MapViewing();
        mMyListAdapter = new MyListAdapter(mMapViewing);
        location = new Location("OneFragment");

        assetManager = getResources().getAssets();
    }

    ViewGroup temp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int i = 0;
        if (Contact.Flag_list[i++].equals(flag)) {
            return HomeView(inflater, container);
        } else {
            for (i = 0; i < Contact.Flag_list.length; i++) {
                if (Contact.Flag_list[i].equals(flag)) {
                    temp = mView(inflater, container, savedInstanceState, Contact.Flag_layout[i], Contact.Flag_list[i]);
                    String[] tag = {Contact.Flag_list[i], Contact.getCity[0]};
                    new JsonLoadingTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, tag);
                    break;
                }
            }
        }
        return temp;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.restaurant_layout:
            case R.id.wifi_layout:
            case R.id.parking_layout:
            case R.id.pension_layout:
            case R.id.hotel_layout:
                for (int i = 0; i < Contact.Flag_list.length; i++) {
                    if (v.getId() == Contact.Flag_Click[i]) {
                        flag = Contact.Flag_list[i];
                        break;
                    }
                }
                startActivity(new Intent(getContext(), LoadingDailog.class));  //로딩시작
                reloadView();
                break;
            case R.id.select_btn:
                if (FloatingBtn_Flag) {
                    FloatingAction.Floating(getContext(), true, select_btn, floating_under_layout);
                    FloatingBtn_Flag = false;
                } else if (!FloatingBtn_Flag) {
                    FloatingAction.Floating(getContext(), FloatingBtn_Flag, select_btn, floating_under_layout);
                    FloatingBtn_Flag = true;
                }
                break;
        }
    }

    private void reloadView() {
        try {
            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.detach(this);
            fragTransaction.attach(this);
            fragTransaction.commit();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private ViewGroup HomeView(LayoutInflater inflater, ViewGroup container) {
        issuelayout = (ViewGroup) inflater.inflate(R.layout.activity_onefragment, container, false);
        restaurant_layout = (LinearLayout) issuelayout.findViewById(R.id.restaurant_layout);
        wifi_layout = (LinearLayout) issuelayout.findViewById(R.id.wifi_layout);
        parking_layout = (LinearLayout) issuelayout.findViewById(R.id.parking_layout);
        pension_layout = (LinearLayout) issuelayout.findViewById(R.id.pension_layout);
        hotel_layout = (LinearLayout) issuelayout.findViewById(R.id.hotel_layout);

        restaurant_layout.setOnClickListener(this);
        wifi_layout.setOnClickListener(this);
        parking_layout.setOnClickListener(this);
        pension_layout.setOnClickListener(this);
        hotel_layout.setOnClickListener(this);
        return issuelayout;
    }

    LinearLayout movingBar;
    String CityTag = null;

    private ViewGroup mView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int resource, final String BuildingTag) {
        try {
            issuelayout = (ViewGroup) inflater.inflate(resource, container, false);
            MainLayout = (LinearLayout) issuelayout.findViewById(R.id.MainLayout);
            MapLayout = (LinearLayout) issuelayout.findViewById(R.id.MapLayout);
            MapLayout.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, DeliMaxHeight));
            MovingLayout = (LinearLayout) issuelayout.findViewById(R.id.MovingLayout);
            MovingLayout.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, DeliMinHeight / 2));
            mListView = (ListView) issuelayout.findViewById(R.id.ListView);

            include_layout = (View) issuelayout.findViewById(R.id.include_layout);
            select_btn = (Button) include_layout.findViewById(R.id.select_btn);
            floating_under_layout = (LinearLayout) include_layout.findViewById(R.id.floating_under_layout);

            for (int i = 0; i < floating_under_btn.length; i++) {
                floating_under_btn[i] = (Button) include_layout.findViewById(Contact.floating_under_btn_id[i]);
                floating_under_btn[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), String.valueOf(v.getTag()), Toast.LENGTH_SHORT).show();
                        CityTag = String.valueOf(v.getTag());
                        String[] tag = {BuildingTag, CityTag};
                        new JsonLoadingTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, tag);
                        if (FloatingBtn_Flag) {
                            FloatingAction.Floating(getContext(), true, select_btn, floating_under_layout);
                            FloatingBtn_Flag = false;
                        } else if (!FloatingBtn_Flag) {
                            FloatingAction.Floating(getContext(), FloatingBtn_Flag, select_btn, floating_under_layout);
                            FloatingBtn_Flag = true;
                        }
                    }
                });
            }
            select_btn.setOnClickListener(this);

            if (BuildingTag.equals("restaurant")) {
                floating_under_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.floating_background_restaurant));
                select_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_btn_restaurant));
            } else if (BuildingTag.equals("wifi")) {
                floating_under_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.floating_background_wifi));
                select_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_btn_wifi));
            } else if (BuildingTag.equals("parking")) {
                floating_under_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.floating_background_parking));
                select_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_btn_parking));
            } else if (BuildingTag.equals("pension")) {
                floating_under_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.floating_background_pension));
                select_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_btn_pension));
            } else if (BuildingTag.equals("hotel")) {
                floating_under_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.floating_background_hotel));
                select_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_btn_hotel));
            }


            mapView = (MapView) inflater.inflate(R.layout.testmap, container, false).findViewById(R.id.testmap);
            movingBar = (LinearLayout) inflater.inflate(R.layout.moving_bar, container, false).findViewById(R.id.MovingBar);
            setMoveBarBackground(movingBar, BuildingTag);
            MovingLayout.addView(movingBar);
            MapLayout.addView(mapView);
            mapView.onCreate(savedInstanceState);
            mMapViewing.setGoogleMap(mapView.getMap());
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }


            mMapViewing.getGoogleMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMapViewing.getGoogleMap().getUiSettings().setZoomControlsEnabled(true);
            mMapViewing.getGoogleMap().getUiSettings().setCompassEnabled(true);
            mMapViewing.getGoogleMap().getUiSettings().setMyLocationButtonEnabled(true);
            mMapViewing.getGoogleMap().getUiSettings().setAllGesturesEnabled(true);
            mMapViewing.getGoogleMap().setMyLocationEnabled(true);

            myLocation.getLocation(getContext(), locationResult);
            MainLayout.setOnTouchListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issuelayout;
    }

    private BroadcastReceiver mRecevier = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("NOTIFICATION")) {
                reloadView();
            } else if (intent.getAction().equals("MAPADDRESS")) {

            }
        }
    };

    @Override
    public void onResume() {
        if (mapView != null) {
            try {
                mapView.onResume();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            try {
                mapView.onLowMemory();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(mRecevier);
        if (mapView != null) {
            mapView.onDestroy();
        }
        super.onDestroy();
    }

    //Json 데이터 분류 -> 리스트뷰 띄움
    private class JsonLoadingTask extends AsyncTask<String, Integer, String[][]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[][] doInBackground(String... strs) {
            Log.d("zzzzzdoInBackground", "Join");
            mMyListAdapter.arrayInit();
            for (int i = 0; i < Contact.Flag_list.length; i++) {
                if (Contact.Flag_list[i].equals(strs[0])) {
                    //DataArrList = jsonManagerWeb.getJSONParser(Contact.URL[i] + Contact.valuetest1to5, Contact.FLAG[i], Contact.TAG[i]);
                    DataArrList = jsonManagerDB.getJSONParser(assetManager, Contact.getFolder[i], strs[1], Contact.TAG[i]);
                    setConnList(DataArrList, Contact.UsingTAG[i], Contact.ListXML[i], Contact.Flag_list[i]);
                    break;
                }
            }
            Log.d("zzzzzdoInBackground", "Join");
            return null;
        }

        @Override
        protected void onPostExecute(String[][] result) {
            mListView.setAdapter(mMyListAdapter);
            Intent noti = new Intent("ENDLOADING");
            getContext().sendBroadcast(noti);  //로딩종료
        }
    }

    //아이템과 리스트를 연결해서 리스트뷰 띄움
    void setConnList(ArrayList DataArrList, String[] UsingTag, int resource, String LayoutTag) {
        int mArrSize = DataArrList.size();
        HashMap mHashMap;
        String[] getValue;
        int length = UsingTag.length;
        for (int i = 0; i < mArrSize; i++) {
            mHashMap = (HashMap) DataArrList.get(i);
            getValue = new String[length];
            for (int j = 0; j < length/* 뽑아낼 데이터 개수*/; j++) {
                getValue[j] = (String) mHashMap.get(UsingTag[j]);
                Log.d("OneFragment", "getValue : " + getValue[j]);
            }
            mMyListAdapter.add((getValue));
        }
        mMyListAdapter.setListSize(length);
        mMyListAdapter.setLayoutResource(resource);
        mMyListAdapter.setLayoutFlag(LayoutTag);
    }

    MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
        @Override
        public void gotLocation(Location location) {
            mMapViewing.drawMarkerLonLat(location, 16);
        }
    };

    View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            int mHeight = (int) event.getY();
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    if (mHeight < DeliMinHeight) {
                        mHeight = DeliMinHeight;
                    } else if (DeliMaxHeight < mHeight) {
                        mHeight = DeliMaxHeight;
                    }
                    MapLayout.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, mHeight));
                    MovingLayout.setY(MapLayout.getHeight());
                    return true;
                case MotionEvent.ACTION_DOWN:
                    return true;
                case MotionEvent.ACTION_UP:
                    return false;
            }
            return false;
        }
    };

    private void setMoveBarBackground(LinearLayout movingBar, String Tag) {
        if (Tag.equals("restaurant")) {
            movingBar.setBackgroundColor(Color.parseColor("#153155"));
        } else if (Tag.equals("wifi")) {
            movingBar.setBackgroundColor(Color.parseColor("#1fccb5"));
        } else if (Tag.equals("parking")) {
            movingBar.setBackgroundColor(Color.parseColor("#d69122"));
        } else if (Tag.equals("pension")) {
            movingBar.setBackgroundColor(Color.parseColor("#a0e696"));
        } else if (Tag.equals("hotel")) {
            movingBar.setBackgroundColor(Color.parseColor("#827bea"));
        }
    }
}