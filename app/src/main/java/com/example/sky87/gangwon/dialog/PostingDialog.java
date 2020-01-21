package com.example.sky87.gangwon.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sky87.gangwon.R;
import com.kakao.kakaostory.KakaoStoryService;
import com.kakao.kakaostory.callback.StoryResponseCallback;
import com.kakao.kakaostory.request.PostRequest;
import com.kakao.kakaostory.response.model.MyStoryInfo;
import com.kakao.network.ErrorResult;
import com.kakao.util.KakaoParameterException;
import com.kakao.util.helper.log.Logger;

/**
 * Created by sky87 on 2016-07-08.
 */
public class PostingDialog extends Activity implements View.OnClickListener {

    private EditText posting_text;
    private Button ok_btn;
    private Button cancel_btn;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);

        setContentView(R.layout.activity_posting);
        link = getIntent().getStringExtra("link");
        posting_text = (EditText) findViewById(R.id.posting_text);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                try {
                    requestPostLink(link, posting_text.getText().toString());
                } catch (KakaoParameterException e) {
                    Toast.makeText(getApplicationContext(), "카카오스토리 이용자가 아니거나 잠시후 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            case R.id.cancel_btn:
                finish();
                break;
        }
    }

    private void requestPostLink(String url, String content) throws KakaoParameterException {
        PostRequest.StoryPermission permission = PostRequest.StoryPermission.PUBLIC;
        KakaoStoryService.requestPostLink(new KakaoStoryResponseCallback<MyStoryInfo>() {
            @Override
            public void onSuccess(MyStoryInfo result) {
                Logger.d(result.toString());
                Intent intent = new Intent(getApplicationContext(), PostingSucessDialog.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        }, url, content, permission, true, "NONE", "NONE", "NONE", "NONE");
    }

    private abstract class KakaoStoryResponseCallback<T> extends StoryResponseCallback<T> {

        @Override
        public void onNotKakaoStoryUser() {
            Logger.d("not KakaoStory user");
            Toast.makeText(getApplicationContext(), "카카오스토리 이용자가 아닙니다. 가입 후 사용해주세요!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(ErrorResult errorResult) {
            Toast.makeText(getApplicationContext(), "실패하였습니다. 잠시후 시도해주세요.", Toast.LENGTH_SHORT).show();
            Logger.e("KakaoStoryResponseCallback : failure : " + errorResult);
        }

        @Override
        public void onSessionClosed(ErrorResult errorResult) {
        }

        @Override
        public void onNotSignedUp() {
        }
    }
}
