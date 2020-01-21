package com.example.sky87.gangwon.kakao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sky87.gangwon.R;
import com.example.sky87.gangwon.dialog.NonMemberDialog;
import com.example.sky87.gangwon.gangmain.MainActivity;
import com.example.sky87.gangwon.util.Contact;
import com.example.sky87.gangwon.util.SharedPrefsUtils;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.helper.Utility.getPackageInfo;

/**
 * Created by sky87 on 2016-07-05.
 */
public class KaKaoLogin extends Activity {
    private SessionCallback callback;
    private LoginButton com_kakao_login;
    private TextView login_text;
    private Button no_kakao_login;

    /**
     * 로그인 버튼을 클릭 했을시 access token을 요청하도록 설정한다.
     *
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao);

        com_kakao_login = (LoginButton) findViewById(R.id.com_kakao_login);
        login_text = (TextView) findViewById(R.id.login_text);
        no_kakao_login = (Button) findViewById(R.id.no_kakao_login);
        com_kakao_login.setVisibility(View.VISIBLE);
        login_text.setVisibility(View.GONE);
        getKeyHash(getApplicationContext());


        Log.e("keyhash", getKeyHash(getApplicationContext()));
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        no_kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NonMemberDialog.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            com_kakao_login.setVisibility(View.GONE);
            no_kakao_login.setVisibility(View.GONE);
            login_text.setVisibility(View.VISIBLE);
            Log.e("onActivityResult", "onActivityResult");
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Toast.makeText(getApplicationContext(), "로그인 실패입니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            com_kakao_login.setVisibility(View.VISIBLE);
            no_kakao_login.setVisibility(View.VISIBLE);
            login_text.setVisibility(View.GONE);
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

    protected void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Logger.d("UserProfile : " + userProfile);
                Log.e("user", userProfile.toString());
                SharedPrefsUtils.setStringPreference(getApplicationContext(), Contact.UserName, userProfile.getNickname());  //카카오 이름
                SharedPrefsUtils.setStringPreference(getApplicationContext(), Contact.ProFileImage, userProfile.getProfileImagePath()); //카카오 프로필 이미지 url
            }

            @Override
            public void onNotSignedUp() {
            }
        });
    }


    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w("", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }
}