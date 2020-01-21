package com.example.sky87.gangwon.dialog;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.sky87.gangwon.R;

import java.util.List;

/**
 * Created by sky87 on 2016-07-08.
 */
public class PostingSucessDialog extends Activity implements View.OnClickListener {
    private Button ok_btn;
    private Button cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);

        setContentView(R.layout.activity_postingsucess);

        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                PackageManager pm = getApplicationContext().getPackageManager();
                List<ResolveInfo> installedApps = pm.queryIntentActivities(mainIntent, 0);
                for (ResolveInfo ai : installedApps) {
                    Log.e("tag", ai.activityInfo.packageName);
                }  //외부앱 패키지명 따기

                Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.kakao.story");
                getApplicationContext().startActivity(intent); //카카오스토리실행
                finish();
                break;
            case R.id.cancel_btn:
                finish();
                break;
        }
    }
}
