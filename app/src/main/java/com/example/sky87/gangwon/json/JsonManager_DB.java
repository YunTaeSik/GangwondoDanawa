package com.example.sky87.gangwon.json;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.sky87.gangwon.util.Contact;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wayne on 2016-07-12.
 */
public class JsonManager_DB {
	HashMap<String, String> mHashMap;
	String TAG;
	AssetManager assetManager;
	String location;

	public ArrayList getJSONParser(AssetManager asset, String folder, String city, String[] Tags) {
		ArrayList mArrHashMap = new ArrayList();
		assetManager = asset;
		TAG = new String();
		if (folder.equals("Hotel")) {
			location = folder + "/NormalHotel/" + city + "_" + folder + ".json";
			getAssetsData(mArrHashMap, Contact.TAG[5]);
			location = folder + "/TourismHotel/" + city + "_" + folder + ".json";
			getAssetsData(mArrHashMap, Contact.TAG[6]);
		} else {
			location = folder + "/" + city + "_" + folder + ".json";
			getAssetsData(mArrHashMap, Tags);
		}

		return mArrHashMap;
	}

	void getAssetsData(ArrayList arrayList, String[] Tags) {
		Log.d("테스트",""+location);
		try {
			JSONObject jsonObj = new JSONObject(loadJSONFromAsset(location));
			JSONArray jsonArr = (JSONArray) jsonObj.get("DATA");
			int ArrLength = jsonArr.length();
			int TagLength = Tags.length;
			for (int i = 0; i < ArrLength; i++) {
				mHashMap = new HashMap<>();
				jsonObj = jsonArr.getJSONObject(i);
				for (int j = 0; j < TagLength; j++) {
					TAG = Tags[j];
					mHashMap.put(TAG, jsonObj.getString(TAG));
					//Log.i("JsonManager_db", "TAGValue : " + j + " : " + TAG + " : " + mHashMap.get(TAG));
				}
				arrayList.add(i, mHashMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String loadJSONFromAsset(String location) {
		String json;
		try {
			InputStream is = assetManager.open(location);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
			/*
			Log.d("zzzzz", "json : " + json);
			Log.d("zzzzz", "size : " + size);
			Log.d("zzzzz", "location : " + location);
			*/
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return json;
	}
}
