package com.example.sky87.gangwon.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sky87.gangwon.R;
import com.example.sky87.gangwon.db.DBManager;
import com.example.sky87.gangwon.json.JsonManager_DB;
import com.example.sky87.gangwon.map.MapViewing;
import com.example.sky87.gangwon.map.MyLocation;
import com.example.sky87.gangwon.util.Contact;
import com.example.sky87.gangwon.util.SharedPrefsUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by sky87 on 2016-06-25.
 */
public class TwoFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    private MapView mapView;
    private int position;
    private ViewGroup issuelayout;

    // 현재 GPS 사용유무
    boolean isGPSEnabled = false;
    // 네트워크 사용유무
    // GPS 상태값
    double lat; // 위도
    double lon; // 경도
    DBManager dbManager;

    LinearLayout MapLayout;
    MyLocation myLocation;


    Cursor cursor_restaurant, cursor_wifi, cursor_parking, cursor_pension, cursor_hotel;

    JsonManager_DB jsonManagerDB;
    AssetManager assetManager;
    MapViewing mMapViewing;

    public static TwoFragment newInstance(int position) {
        TwoFragment f = new TwoFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        position = bundle.getInt(ARG_POSITION);
        myLocation = new MyLocation();
        jsonManagerDB = new JsonManager_DB();

        dbManager = new DBManager(getContext(), "gangwon.db", null, 1);
        mMapViewing = new MapViewing();
        assetManager = getResources().getAssets();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        issuelayout = (ViewGroup) inflater.inflate(R.layout.activity_map, container, false);
        MapLayout = (LinearLayout) issuelayout.findViewById(R.id.MapLayout);
        mapView = (MapView) inflater.inflate(R.layout.testmap, container, false).findViewById(R.id.testmap);

        mapView.removeAllViews();
        MapLayout.addView(mapView);

        mapView.onCreate(savedInstanceState);
        mMapViewing.setGoogleMap(mapView.getMap());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        mMapViewing.getGoogleMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMapViewing.getGoogleMap().getUiSettings().setZoomControlsEnabled(true);
        mMapViewing.getGoogleMap().getUiSettings().setCompassEnabled(true);
        mMapViewing.getGoogleMap().getUiSettings().setMyLocationButtonEnabled(true);
        mMapViewing.getGoogleMap().getUiSettings().setAllGesturesEnabled(true);
        mMapViewing.getGoogleMap().setMyLocationEnabled(true);

        myLocation.getLocation(getContext(), locationResult);
        new JsonLoadingTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return issuelayout;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onStart() {
        ;
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //getContext().unregisterReceiver(mRecevier);
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    //현재 위치 표시하고 위경도 저장
    public MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
        @Override
        public void gotLocation(Location location) {
            try {
                Log.d("zzzzzlocationResult", "start");
                mMapViewing.drawMarkerLonLat(location, 14);
                lat = location.getLatitude();
                lon = location.getLongitude();
                getCursor();
                setAllMark();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    };

    private class JsonLoadingTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog asyncDialog = new ProgressDialog(getContext(), ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        private int progresscount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
/*            asyncDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            asyncDialog.setCanceledOnTouchOutside(false);
            asyncDialog.setMessage("최초 1회 강원공공데이터 DB를 받는중입니다. 잠시만 기다려주세요!");
            asyncDialog.setMax(8767);
            asyncDialog.show();*/
        }

        @Override
        protected String doInBackground(Void... ints) {
            int folder_length = Contact.getFolder.length;
            int city_length = Contact.getCity.length;
            for (int findex = 1; findex < folder_length; findex++) {  //폴더
                for (int cindex = 0; cindex < city_length; cindex++) {    //도시
                    ArrayList ChildArrayList = jsonManagerDB.getJSONParser(assetManager, Contact.getFolder[findex], Contact.getCity[cindex], Contact.TAG[findex]);
                    for (int index = 0; index < ChildArrayList.size(); index++) {
                        HashMap data = (HashMap) ChildArrayList.get(index);
                        String name = (String) data.get(Contact.getUsingtagName[findex]);
                        String address = (String) data.get(Contact.getUsingtagaddress[findex]);
                        String tel = (String) data.get(Contact.getUsingtagtel[findex]);
                        String describe = (String) data.get(Contact.getUsingtagetc[findex]);
                        Double latitude = Double.parseDouble((String) data.get("LAT"));
                        Double longitude = Double.parseDouble((String) data.get("LNG"));
                        dbManager.insert("insert into " + Contact.Flag_list[findex] + " values(null, '" + name + "', '" + address + "', '" + tel + "', '" + describe + "', '" + latitude + "', '" + longitude + "');");
                    }
                }
            }
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.


        @Override
        protected void onPostExecute(String result) {
            SharedPrefsUtils.setStringPreference(getContext(), Contact.DB_FLAG, "FINISH");
            getCursor();
            //setAllMark();
            asyncDialog.dismiss();
        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    }

    void getCursor() {
        Log.d("zzzzz", "getCursor");
        try {
            cursor_restaurant = dbManager.getCursor(Contact.Flag_list[1]);
            cursor_wifi = dbManager.getCursor(Contact.Flag_list[2]);
            cursor_parking = dbManager.getCursor(Contact.Flag_list[3]);
            cursor_pension = dbManager.getCursor(Contact.Flag_list[4]);
            cursor_hotel = dbManager.getCursor(Contact.Flag_list[5]);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    void setMark(Cursor cursor, int num) {
        Log.d("zzzzz", "setMark" + "now lat" + lat + "now lon" + lon);
        try {
            while (cursor.moveToNext()) {
                //Log.d("zzzzz", "setMark" + " moveToNext()");
                if (calDistance(cursor.getDouble(5), cursor.getDouble(6), lat, lon) < 10000) {  //1번 이름 2번 주소 3번 전화번호 4번 lat 5번 lon
                    mMapViewing.Allmark(cursor.getDouble(5), cursor.getDouble(6), cursor.getString(1), cursor.getString(2), cursor.getString(3), num, getContext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            /*
			if(cursor.isClosed()){
				Log.d("zzzzz", "setMark" + " cursor is Closed()");
			}
			*/
        }
    }

    void setAllMark() {
        setMark(cursor_restaurant, 1);
        setMark(cursor_wifi, 2);
        setMark(cursor_parking, 3);
        setMark(cursor_pension, 4);
        setMark(cursor_hotel, 5);
    }

    double calDistance(double D_lat, double D_lon, double H_lat, double H_lon) {
        Double distance;
        Location locationA = new Location("point A");
        locationA.setLatitude(D_lat);
        locationA.setLongitude(D_lon);
        Location locationB = new Location("point B");
        locationB.setLatitude(H_lat);
        locationB.setLongitude(H_lon);

        distance = (double) locationB.distanceTo(locationA);
        return distance;
    }

}