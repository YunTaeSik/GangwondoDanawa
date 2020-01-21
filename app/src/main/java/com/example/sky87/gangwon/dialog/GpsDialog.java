package com.example.sky87.gangwon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.sky87.gangwon.R;

/**
 * Created by User on 2016-06-29.
 */
public class GpsDialog extends Dialog {
    private Button ok_btn;
    private Button cancel_btn;
    LayoutInflater li;
    TextView tv;
    String title;

    public GpsDialog() {
        super(null);
    }

    public GpsDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    public GpsDialog(Context context, String title) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.activity_gpsdialog);

        tv = (TextView) findViewById(R.id.Gps_Text);
        tv.setText(title);

        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);

        ok_btn.setOnClickListener(Ok_Listener);
        cancel_btn.setOnClickListener(Cancle_Listener);
    }

    View.OnClickListener Ok_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            getContext().startActivity(intent);
            Intent intent1 = new Intent("GPS_NOTI");
            getContext().sendBroadcast(intent1);
            dismiss();
        }
    };
    View.OnClickListener Cancle_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
}