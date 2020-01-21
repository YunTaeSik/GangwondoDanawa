package com.example.sky87.gangwon.intro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.VideoView;

import com.example.sky87.gangwon.R;
import com.example.sky87.gangwon.kakao.KaKaoLogin;

/**
 * Created by sky87 on 2016-06-30.
 */
public class IntroActivity extends Activity {
    Handler h;//핸들러 선언
    private VideoView intro_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //인트로화면이므로 타이틀바를 없앤다
        setContentView(R.layout.activity_intro);
        intro_video = (VideoView) findViewById(R.id.intro_video);
        intro_video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro));
        intro_video.start();
        h = new Handler(); //딜래이를 주기 위해 핸들러 생성
        h.postDelayed(mrun, 3000); // 딜레이 ( 런어블 객체는 mrun, 시간 2초)


    }

    Runnable mrun = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(IntroActivity.this, KaKaoLogin.class); //인텐트 생성(현 액티비티, 새로 실행할 액티비티)
            startActivity(i);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        h.removeCallbacks(mrun);
    }


}
