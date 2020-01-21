package com.example.sky87.gangwon.map;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sky87.gangwon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

/**
 * Created by wayne on 2016-06-29.
 */
public class MapViewing {

    public GoogleMap googlemap;
    public Geocoder geocoder;
    public Address AddrAddress;
    Marker mMarker, Allmark;

    public void drawMarkerLonLat(Location location, int ZoomLevel) {
        //기존 마커 지우기
        try {
            if (mMarker != null) {
                mMarker.remove();
                mMarker = null;
            }
            googlemap.clear();
            LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
            //currentPosition 위치로 카메라 중심을 옮기고 화면 줌을 조정한다. 줌범위는 2~21, 숫자클수록 확대
            googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, ZoomLevel));
            googlemap.animateCamera(CameraUpdateFactory.zoomTo(ZoomLevel), 2000, null);

            //마커 추가
            mMarker = googlemap.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .snippet("Lat:" + location.getLatitude() + "Lng:" + location.getLongitude())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
                    .title("현재위치"));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


    public void Allmark(double lat, double lon, String title, String address, String tel, int num, final Context mContext) {
        Log.d("zzzzzAllmark", "lat\t" + lat + "lon\t" + lon + "title\t" + title + "address\t" + address + "tel\t" + tel + "num\t" + num);
        switch (num) {
            case 1:        //레스토랑
                Allmark = googlemap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(title)
                        .snippet("주소 : " + address + "\n" + "전화번호 : " + tel + "\n" + "주메뉴" + "\n")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_mark)));
                googlemap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        LinearLayout info = new LinearLayout(mContext);
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(mContext);
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(mContext);
                        snippet.setTextColor(Color.GRAY);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });
                break;
            case 2:        //펜션
                Allmark = googlemap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(title)
                        .snippet("Address : " + address + "tel : " + tel + "\n")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_mark)));
                break;
            case 3:
                Allmark = googlemap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(title)
                        .snippet("Address : " + address + "tel : " + tel + "\n")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pension_mark)));
                break;
            case 4:
                Allmark = googlemap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(title)
                        .snippet("Address : " + address + "tel : " + tel + "\n")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_mark)));
                break;
            case 5:
                Allmark = googlemap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(title)
                        .snippet("Address : " + address + "tel : " + tel + "\n")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.wifi_mark)));
                break;
        }
    }


    public void drawMarkerAddress(Context context, String Address) {
        geocoder = new Geocoder(context);
        Location location = new Location("dummy");
        try {
            AddrAddress = geocoder.getFromLocationName(Address, 5).get(0);
            location.setLatitude(AddrAddress.getLatitude());
            location.setLongitude(AddrAddress.getLongitude());
            //Location location = new Location();
            Log.e("OneFragment : ", "drawMarkerAddress" + AddrAddress.getLatitude() + " " + AddrAddress.getLongitude());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        drawMarkerLonLat(location, 14);
    }

    public void setGoogleMap(GoogleMap map) {
        googlemap = map;
    }

    public GoogleMap getGoogleMap() {
        return googlemap;
    }
}
