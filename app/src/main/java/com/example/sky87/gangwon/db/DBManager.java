package com.example.sky87.gangwon.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sky87.gangwon.util.Contact;

/**
 * Created by sky87 on 2016-06-23.
 */
public class DBManager extends SQLiteOpenHelper {
    private SQLiteDatabase date;

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 1; i < Contact.Flag_list.length; i++) {    //home 은 table 생성이 필요없음.
            db.execSQL("CREATE TABLE IF NOT EXISTS " + Contact.Flag_list[i] + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT , Address TEXT , Tel TEXT, Des TEXT, Lat REAL not null, Lng REAL not null);");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(String _query) {
        //Log.d("zzzzz_query",_query);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }


    public String ReadData(String Section) {     //섹션은 rest 인지 wifi인지 등등 구분
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        Cursor cursor = db.rawQuery("select * from " + Section, null);
        cursor.getCount();
        Log.e("getcount", String.valueOf(cursor.getCount()));
        while (cursor.moveToNext()) {
            str += " : 이름 =" +
                    cursor.getString(1) + "\n"
                    + ",주소 ="
                    + cursor.getString(2) + "\n"
                    + ",전화번호 ="
                    + cursor.getString(3) + "\n"
                    + ",상세정보 ="
                    + cursor.getString(4) + "\n";
        }
        return str;
    }

    public String ReadLATLNGData(String Section) {     //섹션은 rest 인지 wifi인지 등등 구분
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        Cursor cursor = db.rawQuery("select Lat,Lng from " + Section, null);
        while (cursor.moveToNext()) {
        }
        return str;
    }


    public Cursor getCursor(String list) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + list, null);
            return cursor;
    }
}

