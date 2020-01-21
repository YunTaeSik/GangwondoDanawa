package com.example.sky87.gangwon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sky87.gangwon.R;
import com.example.sky87.gangwon.dialog.PostingDialog;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sky87 on 2016-06-29.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private String tag;
    private View viewpager;
    private Context context;
    private ImageView icon_image;
    private TextView title_view;
    private TextView blogname_view;
    private TextView date_view;
    private ImageView restaurant_image;
    private LinearLayout restaurant_layout;
    private LinearLayout layout_background;
    private TextView icon_text;
    private ArrayList mList;
    private Map<String, String> mMap = new HashMap<String, String>();
    private Animation alphaAni;
    private LinearLayout kakao_layout;
    private LinearLayout link_layout;
    private LinearLayout posting_layout;
    private ImageView link_image;
    private ImageView post_image;

    public ViewPagerAdapter(Context context, ArrayList list, Map<String, String> hashMap, String tag) {
        super();
        this.mMap = hashMap;
        this.context = context;
        this.mList = list;
        this.tag = tag;
    }

    @Override
    public int getCount() {
        if (mList.size() <= 5) {
            return mList.size();
        } else {
            return 5;
        }
     /*   return mList.size();*/
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        viewpager = LayoutInflater.from(context).inflate(R.layout.viewpager_item, null);
        try {
            final String title = mMap.get("title" + position);
            final String image = mMap.get("image" + position).replace("type=s88", "type=w3");
            final String link = mMap.get("link" + position);
            String blogname = mMap.get("blogname" + position);
            String date = mMap.get("date" + position);

            icon_image = (ImageView) viewpager.findViewById(R.id.icon_image);
            title_view = (TextView) viewpager.findViewById(R.id.title);
            restaurant_image = (ImageView) viewpager.findViewById(R.id.restaurant_image);
            blogname_view = (TextView) viewpager.findViewById(R.id.blogname_view);
            date_view = (TextView) viewpager.findViewById(R.id.date_view);
            restaurant_layout = (LinearLayout) viewpager.findViewById(R.id.restaurant_layout);
            layout_background = (LinearLayout) viewpager.findViewById(R.id.layout_background);
            icon_text = (TextView) viewpager.findViewById(R.id.icon_text);
            kakao_layout = (LinearLayout) viewpager.findViewById(R.id.kakao_layout);
            link_layout = (LinearLayout) viewpager.findViewById(R.id.link_layout);
            posting_layout = (LinearLayout) viewpager.findViewById(R.id.posting_layout);
            link_image = (ImageView) viewpager.findViewById(R.id.link_image);
            post_image = (ImageView) viewpager.findViewById(R.id.post_image);

            Picasso.with(context).load(R.drawable.link_image).into(link_image);
            Picasso.with(context).load(R.drawable.post_image).into(post_image);

            link_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KakaoLink(context, image, title, link);
                }
            });
            posting_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PostingDialog.class);
                    intent.putExtra("link", link);
                    context.startActivity(intent);
                }
            });

            icon_text.setText(tag);
            title_view.setText(title);
            blogname_view.setText(blogname);
            date_view.setText(date + " (출처 = NAVER)");
            Picasso.with(context).load(image)
                    .into(restaurant_image);
            restaurant_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(link);
                    intent.setData(uri);
                    context.startActivity(intent);
                }
            });
            if (tag.equals("RESTAURANT")) {
                //icon_image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rest_image));
                Picasso.with(context).load(R.drawable.rest_image).into(icon_image);
                layout_background.setBackgroundColor(Color.parseColor("#1fccb5"));
                kakao_layout.setBackgroundColor(Color.parseColor("#1fccb5"));
            } else if (tag.equals("HOTEL")) {
                //   icon_image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.hotel_image));
                Picasso.with(context).load(R.drawable.hotel_image).into(icon_image);
                layout_background.setBackgroundColor(Color.parseColor("#827bea"));
                kakao_layout.setBackgroundColor(Color.parseColor("#827bea"));
            } else if (tag.equals("PENSION")) {
                //  icon_image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pension_image));
                Picasso.with(context).load(R.drawable.pension_image).into(icon_image);
                layout_background.setBackgroundColor(Color.parseColor("#a0e696"));
                kakao_layout.setBackgroundColor(Color.parseColor("#a0e696"));
            } else if (tag.equals("COURSE")) {
                //  icon_image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.travel_image));
                Picasso.with(context).load(R.drawable.travel_image).into(icon_image);
                layout_background.setBackgroundColor(Color.parseColor("#153155"));
                kakao_layout.setBackgroundColor(Color.parseColor("#153155"));
            }
            container.addView(viewpager);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return viewpager;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.85f;
    }

    private void KakaoLink(Context context, String url, String tilte, String link) {
        final KakaoLink kakaoLink;
        try {
            kakaoLink = KakaoLink.getKakaoLink(context);
            final KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
            String text = "강원 다나와!! 추천해주셨어요" + tilte + "\n" + link;
            kakaoTalkLinkMessageBuilder.addText(text).addImage(url, 100, 100).addAppButton("강원다나와");
            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, context);
        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
    }
}
