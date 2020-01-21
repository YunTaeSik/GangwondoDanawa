package com.example.sky87.gangwon.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 2016-07-01.
 */
public class getAddress {

    //주소 가져오는 메소드
    public String getAddress(double lat, double lng, Context context) {
        String str = null;
        Geocoder geocoder = new Geocoder(context, Locale.KOREA);

        List<Address> address;
        try {
            if (geocoder != null) {
                address = geocoder.getFromLocation(lat, lng, 1);
                if (address != null && address.size() > 0) {
                    str = address.get(0).getAddressLine(0).toString();
                    address.get(0).getCountryName().toString();
                    Log.e("test", address.get(0).getLocality().toString());
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "주소를 찾지 못하였습니다.");
            e.printStackTrace();
        }
        return str;
    }

    public String getAddressLocal(double lat, double lng, Context context) {
        String str = null;
        Geocoder geocoder = new Geocoder(context, Locale.KOREA);

        List<Address> address;
        try {
            if (geocoder != null) {
                address = geocoder.getFromLocation(lat, lng, 1);
                if (address != null && address.size() > 0) {
                    str = address.get(0).getLocality().toString();
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "주소를 찾지 못하였습니다.");
            e.printStackTrace();
        }
        return str;
    }
}