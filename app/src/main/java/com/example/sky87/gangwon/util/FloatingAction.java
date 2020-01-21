package com.example.sky87.gangwon.util;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.sky87.gangwon.R;

/**
 * Created by sky87 on 2016-07-13.
 */
public class FloatingAction {

    public static void Floating(Context context, boolean b, Button btn, final LinearLayout btn_under) {
        Animation fab_open = AnimationUtils.loadAnimation(context, R.anim.fab_open);
        Animation fab_close = AnimationUtils.loadAnimation(context, R.anim.fab_close);
        Animation fab_under_open = AnimationUtils.loadAnimation(context, R.anim.fab_under_open);
        Animation fab_under_close = AnimationUtils.loadAnimation(context, R.anim.fab_under_close);
      /*  btn.setBackgroundColor(Color.parseColor("#153155"));
        btn_under.setBackgroundColor(Color.parseColor("#153155"));*/
        if (b) {
            btn.startAnimation(fab_open);
            btn_under.startAnimation(fab_under_open);
            btn_under.setVisibility(View.VISIBLE);
            btn.setText("닫기");
        } else {
            btn.startAnimation(fab_close);
            btn_under.startAnimation(fab_under_close);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_under.setVisibility(View.GONE);
                }
            }, 300);

            btn.setText("지역 선택");
        }
    }
}
