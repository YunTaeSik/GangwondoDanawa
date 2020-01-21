package com.example.sky87.gangwon.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sky87 on 2016-06-25.
 */
public class JsonManager_Web {
    HashMap<String, String> mHashMap;
    ArrayList mArrHashMap;
    String TAG;

    public ArrayList getJSONParser(String Url, String flag, String[] Tags) {
        Log.e("JsonManager_Web", "Url : " + Url);
        mArrHashMap = new ArrayList();
        TAG = new String();
        try {
            JSONObject jsonObj = new JSONObject(getStringFromUrl(Url));
            JSONArray jsonArr = new JSONObject(jsonObj.getString(flag)).getJSONArray("row");
            int ArrLength = jsonArr.length();
            int TagLength = Tags.length;
            for (int i = 0; i < ArrLength; i++) {
                mHashMap = new HashMap<>();
                jsonObj = jsonArr.getJSONObject(i);
                for (int j = 0; j < TagLength; j++) {
                    TAG = Tags[j];
                    mHashMap.put(TAG, jsonObj.getString(TAG));
                    Log.i("JsonManager_Web", "TAGValue : " + j + " : " + TAG + " : " + mHashMap.get(TAG));
                }
                mArrHashMap.add(i, mHashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mArrHashMap;
    }

    public String getStringFromUrl(String pUrl) {
        BufferedReader bufreader = null;
        HttpURLConnection urlConnection = null;
        StringBuffer page = new StringBuffer();
        try {
            URL url = new URL(pUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream contentStream = urlConnection.getInputStream();
            bufreader = new BufferedReader(new InputStreamReader(contentStream, "UTF-8"));
            String line = null;
            while ((line = bufreader.readLine()) != null) {
                page.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufreader != null)
                    bufreader.close();
                if (urlConnection != null)
                    urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return page.toString();
    }
}