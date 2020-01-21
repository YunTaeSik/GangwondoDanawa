package com.example.sky87.gangwon.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sky87.gangwon.R;
import com.example.sky87.gangwon.kakao.KaKaoLogin;
import com.example.sky87.gangwon.util.Contact;
import com.example.sky87.gangwon.util.SharedPrefsUtils;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by sky87 on 2016-07-05.
 */
public class FourFragment extends Fragment implements View.OnClickListener {
    private ViewGroup profile_view;
    private ImageView profile_image;
    private TextView profile_name;
    private RelativeLayout logout_layout;
    private TextView profile_gender;
    private TextView profile_birthday;
    private TextView profile_email;
    private RelativeLayout setting_layout;
    private ImageView setting_image;
    private ImageView logout_image;

    private ViewGroup setting_view;
    private ImageView edit_image;
    private EditText edit_name;
    private EditText edit_gender;
    private EditText edit_birthday;
    private EditText edit_email;
    private RelativeLayout save_layout;
    private ImageView save_image;

    private String VIEWTAG = "PROFILE";

    public static FourFragment newInstance() {
        FourFragment f = new FourFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (VIEWTAG.equals("PROFILE")) {
            return ProfileView(inflater, container);
        } else if (VIEWTAG.equals("SETTING")) {
            return SettingView(inflater, container);
        } else {
            return ProfileView(inflater, container);
        }
    }

    private ViewGroup ProfileView(LayoutInflater inflater, ViewGroup container) {
        profile_view = (ViewGroup) inflater.inflate(R.layout.activity_fourfragment, container, false);

        profile_image = (ImageView) profile_view.findViewById(R.id.profile_image);
        profile_name = (TextView) profile_view.findViewById(R.id.profile_name);
        logout_layout = (RelativeLayout) profile_view.findViewById(R.id.logout_layout);
        profile_gender = (TextView) profile_view.findViewById(R.id.profile_gender);
        profile_birthday = (TextView) profile_view.findViewById(R.id.profile_birthday);
        profile_email = (TextView) profile_view.findViewById(R.id.profile_email);
        setting_layout = (RelativeLayout) profile_view.findViewById(R.id.setting_layout);
        setting_image = (ImageView) profile_view.findViewById(R.id.setting_image);
        logout_image = (ImageView) profile_view.findViewById(R.id.logout_image);
        logout_layout.setOnClickListener(this);
        setting_layout.setOnClickListener(this);
        Picasso.with(getContext()).load(R.drawable.setting_image).into(setting_image);
        Picasso.with(getContext()).load(R.drawable.logout_image).into(logout_image);
        if (SharedPrefsUtils.getStringPreference(getContext(), Contact.ProFileImage) != null) {
            try {
                Picasso.with(getContext()).load(SharedPrefsUtils.getStringPreference(getContext(), Contact.ProFileImage)).transform(new CircleTransform()).into(profile_image);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(getContext()).load(R.drawable.user_profile_mint).into(profile_image);
            // profile_image.setBackgroundDrawable(getResources().getDrawable(R.drawable.user_profile_mint));
        }
        try {
            profile_name.setText(SharedPrefsUtils.getStringPreference(getContext(), Contact.UserName));
            profile_gender.setText(SharedPrefsUtils.getStringPreference(getContext(), Contact.ProFileGender));
            profile_birthday.setText(SharedPrefsUtils.getStringPreference(getContext(), Contact.ProFileBirth));
            profile_email.setText(SharedPrefsUtils.getStringPreference(getContext(), Contact.ProFileEmail));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return profile_view;
    }

    private ViewGroup SettingView(LayoutInflater inflater, ViewGroup container) {
        setting_view = (ViewGroup) inflater.inflate(R.layout.activity_setting, container, false);
        edit_name = (EditText) setting_view.findViewById(R.id.edit_name);
        edit_gender = (EditText) setting_view.findViewById(R.id.edit_gender);
        edit_birthday = (EditText) setting_view.findViewById(R.id.edit_birthday);
        edit_email = (EditText) setting_view.findViewById(R.id.edit_email);
        edit_image = (ImageView) setting_view.findViewById(R.id.edit_image);
        save_layout = (RelativeLayout) setting_view.findViewById(R.id.save_layout);
        save_image = (ImageView) setting_view.findViewById(R.id.save_image);

        save_layout.setOnClickListener(this);
        Picasso.with(getContext()).load(R.drawable.save).into(save_image);
        if (SharedPrefsUtils.getStringPreference(getContext(), Contact.ProFileImage) != null) {
            Picasso.with(getContext()).load(SharedPrefsUtils.getStringPreference(getContext(), Contact.ProFileImage)).transform(new CircleTransform()).into(edit_image);
        } else {
            Picasso.with(getContext()).load(R.drawable.user_profile_mint).into(edit_image);
            //edit_image.setBackgroundDrawable(getResources().getDrawable(R.drawable.user_profile_mint));
        }
        try {
            edit_name.setText(SharedPrefsUtils.getStringPreference(getContext(), Contact.UserName));
            edit_gender.setText(SharedPrefsUtils.getStringPreference(getContext(), Contact.ProFileGender));
            edit_birthday.setText(SharedPrefsUtils.getStringPreference(getContext(), Contact.ProFileBirth));
            edit_email.setText(SharedPrefsUtils.getStringPreference(getContext(), Contact.ProFileEmail));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return setting_view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_layout:
                SharedPrefsUtils.setStringPreference(getContext(), Contact.UserName, null);
                SharedPrefsUtils.setStringPreference(getContext(), Contact.ProFileGender, null);
                SharedPrefsUtils.setStringPreference(getContext(), Contact.ProFileBirth, null);
                SharedPrefsUtils.setStringPreference(getContext(), Contact.ProFileEmail, null);
                SharedPrefsUtils.setStringPreference(getContext(), Contact.ProFileImage, null);
                Logout();
                break;
            case R.id.setting_layout:
                VIEWTAG = "SETTING";
                reloadView();
                break;
            case R.id.save_layout:
                SharedPrefsUtils.setStringPreference(getContext(), Contact.UserName, edit_name.getText().toString());
                SharedPrefsUtils.setStringPreference(getContext(), Contact.ProFileGender, edit_gender.getText().toString());
                SharedPrefsUtils.setStringPreference(getContext(), Contact.ProFileBirth, edit_birthday.getText().toString());
                SharedPrefsUtils.setStringPreference(getContext(), Contact.ProFileEmail, edit_email.getText().toString());
                VIEWTAG = "PROFILE";
                reloadView();
                break;
        }
    }

    private void reloadView() {
        try {
            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.detach(this);
            fragTransaction.attach(this);
            fragTransaction.commit();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    private void Logout() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Log.e("logout", "logout");
                Intent intent = new Intent(getContext(), KaKaoLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

   /* private void requestPostNote() throws KakaoParameterException {
        KakaoStoryService.requestPostNote(new KakaoStoryResponseCallback<MyStoryInfo>() {
            @Override
            public void onSuccess(MyStoryInfo result) {
                Logger.e(result.toString());
            }
        }, "ㅁㄴㅇqwdqwd", PostRequest.StoryPermission.PUBLIC, true, null, null, null, null);
    }

    private abstract class KakaoStoryResponseCallback<T> extends StoryResponseCallback<T> {

        @Override
        public void onNotKakaoStoryUser() {
            Logger.e("not KakaoStory user");
        }

        @Override
        public void onFailure(ErrorResult errorResult) {
            Logger.e("KakaoStoryResponseCallback : failure : " + errorResult);
        }

        @Override
        public void onSessionClosed(ErrorResult errorResult) {
        }

        @Override
        public void onNotSignedUp() {
        }
    }

    private void requestIsStoryUser() {
        KakaoStoryService.requestIsStoryUser(new KakaoStoryResponseCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                Logger.e("check story user : " + result);
                try {
                    requestPostNote();
                } catch (KakaoParameterException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/


}
