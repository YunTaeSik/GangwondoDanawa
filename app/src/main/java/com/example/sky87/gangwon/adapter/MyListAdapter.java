package com.example.sky87.gangwon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sky87.gangwon.R;
import com.example.sky87.gangwon.dialog.DetailDialog;
import com.example.sky87.gangwon.map.MapViewing;

import java.util.ArrayList;

/**
 * Created by wayne on 2016-06-27.
 */
public class MyListAdapter extends BaseAdapter {
    private ArrayList<String[]> mArrayList;

    TextView[] text;
    LayoutInflater inflater;
    Context context;
    int pos;
    int ListSize;
    int resource;
    private String Tag;
    MapViewing mMapViewing;
    private TextView detail_text;
    private TextView location_text;
    private ImageView blog_search;
    private LinearLayout btn_layout;

    public MyListAdapter(MapViewing mMapViewing) {
        mArrayList = new ArrayList<String[]>();
        this.mMapViewing = mMapViewing;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 출력 될 아이템 관리
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //pos = position;
        context = parent.getContext();
        StringBuffer sb = new StringBuffer();
        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if (convertView == null) {
            text = new TextView[4];
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
            Log.e("tag", Tag);

            text[0] = (TextView) convertView.findViewById(R.id.nameView);
            text[1] = (TextView) convertView.findViewById(R.id.addressView);
            text[2] = (TextView) convertView.findViewById(R.id.phoneView);
            detail_text = (TextView) convertView.findViewById(R.id.detail_text);
            location_text = (TextView) convertView.findViewById(R.id.location_text);
            blog_search = (ImageView) convertView.findViewById(R.id.blog_search);
            btn_layout = (LinearLayout) convertView.findViewById(R.id.btn_layout);

            SetBottomBackground(btn_layout, Tag, text[0]);

            for (int i = 0; i < ListSize; i++) {
                if (i < 3)
                    text[i].setText(mArrayList.get(position)[i]);
                sb.append(mArrayList.get(position)[i]).append("\n");
            }
            final String infoText = sb.toString();
            location_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMapViewing.drawMarkerAddress(context, mArrayList.get(position)[1]);
                }
            });
            detail_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detail = new Intent(context, DetailDialog.class);
                    try {
                        detail.putExtra("title", mArrayList.get(position)[0]);
                        detail.putExtra("location", mArrayList.get(position)[1]);
                        detail.putExtra("tele", mArrayList.get(position)[2]);
                        detail.putExtra("menu", mArrayList.get(position)[3]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    context.startActivity(detail);
                }
            });
            blog_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent clIck = new Intent("ONEFRAGMENT_NOTI");
                        clIck.putExtra("data", mArrayList.get(position)[0]);
                        context.sendBroadcast(clIck);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        return convertView;
    }

    private void SetBottomBackground(LinearLayout btn_layout, String Tag, TextView NameText) {
        if (Tag.equals("restaurant")) {
            btn_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bottom_box_background_restaurant));
            NameText.setTextColor(Color.parseColor("#153155"));
        } else if (Tag.equals("wifi")) {
            btn_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bottom_box_background_wifi));
            NameText.setTextColor(Color.parseColor("#1fccb5"));
        } else if (Tag.equals("parking")) {
            btn_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bottom_box_background_parking));
            NameText.setTextColor(Color.parseColor("#d69122"));
        } else if (Tag.equals("pension")) {
            btn_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bottom_box_background_pension));
            NameText.setTextColor(Color.parseColor("#a0e696"));
        } else if (Tag.equals("hotel")) {
            btn_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bottom_box_background_hotel));
            NameText.setTextColor(Color.parseColor("#827bea"));
        }

    }

    public void add(String[] _msg) {
        mArrayList.add(_msg);
    }

    public void setListSize(int size) {
        ListSize = size;
    }

    public void setLayoutResource(int res) {
        resource = res;
    }

    public void setLayoutFlag(String flag) {
        Tag = flag;
    }


    public void remove(int _position) {
        mArrayList.remove(_position);
    }

    public void arrayInit() {
        mArrayList = new ArrayList<>();
    }

}