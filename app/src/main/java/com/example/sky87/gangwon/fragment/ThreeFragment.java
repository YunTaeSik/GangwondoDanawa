package com.example.sky87.gangwon.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sky87.gangwon.R;
import com.example.sky87.gangwon.adapter.SearchAdapter;
import com.example.sky87.gangwon.adapter.ViewPagerAdapter;
import com.example.sky87.gangwon.map.MyLocation;
import com.example.sky87.gangwon.util.getAddress;
import com.squareup.picasso.Picasso;
import com.yalantis.phoenix.PullToRefreshView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sky87 on 2016-06-26.
 */
public class ThreeFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_POSITION = "position";
    private ViewGroup recommand_view;
    private ViewGroup search_view;
    private int position;
    private TextView text;
    private TextView marquee_text;
    private ImageView seach_image;
    private Animation alphaAni;
    private EditText seach_edit;
    private GridView search_grid;
    private ImageView back_btn;
    private TextView title_two;
    private ImageView search_image_two;
    private EditText search_edit_two;

    private PullToRefreshView mPullToRefreshView;
    private ArrayList restaurant_data = new ArrayList<>();
    private ArrayList hotel_data = new ArrayList<>();
    private ArrayList pension_data = new ArrayList<>();
    private ArrayList course_data = new ArrayList<>();
    private ArrayList search_data = new ArrayList<>();
    private Map<String, String> restaurant_map = new HashMap<String, String>();
    private Map<String, String> hotel_map = new HashMap<String, String>();
    private Map<String, String> pension_map = new HashMap<String, String>();
    private Map<String, String> course_map = new HashMap<String, String>();
    private Map<String, String> search_map = new HashMap<String, String>();

    private ViewPager restaurant_viewpager;
    private ViewPager hotel_viewpager;
    private ViewPager pension_viewpager;
    private ViewPager course_viewpager;

    private String local;

    private String search;
    private String tilteencode;

    private MyLocation myLocation;

    public static String Tag = "recommand";

    private double lat;
    private double lon;

    public static ThreeFragment newInstance1(int position) {
        ThreeFragment f = new ThreeFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        position = bundle.getInt(ARG_POSITION);
        getAddress getAddress = new getAddress();
        myLocation = new MyLocation();
        myLocation.getLocation(getContext(), locationResult);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("NOTIFICATION_three");
        intentFilter.addAction("ONEFRAGMENT_NOTI");
        getContext().registerReceiver(broadcastReceiver, intentFilter);


    }

    private MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
        @Override
        public void gotLocation(Location location) {
            try {
                Toast.makeText(getContext(), "Three " + location.getLatitude() + " : " + location.getLongitude(), Toast.LENGTH_SHORT);
                lat = location.getLatitude();
                lon = location.getLongitude();
                getAddress getAddress = new getAddress();
                local = getAddress.getAddressLocal(lat, lon, getContext());
                reloadView();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Tag.equals("recommand")) {
            return RecommandView(inflater, container);
        } else if (Tag.equals("search")) {
            return SearchView(inflater, container);
        } else {
            return RecommandView(inflater, container);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_image:
                alphaAni = AnimationUtils.loadAnimation(getContext(), R.anim.click_alpha);
                seach_image.startAnimation(alphaAni);
                Tag = "search";
                search = seach_edit.getText().toString();
                if (search.length() > 0) {
                    search_data.clear();
                    reloadView();
                } else {
                    Toast.makeText(getContext(), "검색명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.search_image_two:
                alphaAni = AnimationUtils.loadAnimation(getContext(), R.anim.click_alpha);
                search_image_two.startAnimation(alphaAni);
                Tag = "search";
                search = search_edit_two.getText().toString();
                if (search_edit_two.length() > 0) {
                    search_data.clear();
                    reloadView();
                } else {
                    Toast.makeText(getContext(), "검색명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back_btn:
                Tag = "recommand";
                reloadView();
                break;
        }

    }

    private class JsoupHtmlParsing extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            int count = 0;
            try {
                tilteencode = URLEncoder.encode(params[0]);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                org.jsoup.nodes.Document doc = Jsoup.connect("http://section.blog.naver.com/sub/SearchBlog.nhn?type=post&option.keyword=" + tilteencode).get();
                for (Element table : doc.select("li[class=add_img]")) {
                    Elements eLink = table.select("a[href]");
                    String link = eLink.attr("href");

                    Elements eTitle = table.select("h5");
                    String title = eTitle.text();

                    Elements eImage = table.select("img[src]");
                    String image = eImage.attr("src");

                    Elements eBlogname = table.select("div[class=list_data]").select("a[href]");
                    String blogname = eBlogname.text();

                    Elements eDate = table.select("span[class=date]");
                    String date = eDate.text();
                    if (params[0].equals(local + "맛집")) {
                        restaurant_map.put("image" + count, image);
                        restaurant_map.put("link" + count, link);
                        restaurant_map.put("title" + count, title);
                        restaurant_map.put("blogname" + count, blogname);
                        restaurant_map.put("date" + count, date);
                        restaurant_data.add(count);
                    } else if (params[0].equals(local + "호텔")) {
                        hotel_map.put("image" + count, image);
                        hotel_map.put("link" + count, link);
                        hotel_map.put("title" + count, title);
                        hotel_map.put("blogname" + count, blogname);
                        hotel_map.put("date" + count, date);
                        hotel_data.add(count);
                    } else if (params[0].equals(local + "펜션")) {
                        pension_map.put("image" + count, image);
                        pension_map.put("link" + count, link);
                        pension_map.put("title" + count, title);
                        pension_map.put("blogname" + count, blogname);
                        pension_map.put("date" + count, date);
                        pension_data.add(count);
                    } else if (params[0].equals(local + "여행코스")) {
                        course_map.put("image" + count, image);
                        course_map.put("link" + count, link);
                        course_map.put("title" + count, title);
                        course_map.put("blogname" + count, blogname);
                        course_map.put("date" + count, date);
                        course_data.add(count);
                    } else {
                        search_map.put("image" + count, image);
                        search_map.put("link" + count, link);
                        search_map.put("title" + count, title);
                        search_map.put("blogname" + count, blogname);
                        search_map.put("date" + count, date);
                        search_data.add(count);
                    }
                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result.equals(local + "맛집")) {
                    String tag = "RESTAURANT";
                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(), restaurant_data, restaurant_map, tag);
                    viewPagerAdapter.notifyDataSetChanged();
                    restaurant_viewpager.setPageMargin(30);
                    restaurant_viewpager.setAdapter(viewPagerAdapter);
                    viewPagerAdapter.notifyDataSetChanged();
                } else if (result.equals(local + "호텔")) {
                    String tag = "HOTEL";
                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(), hotel_data, hotel_map, tag);
                    viewPagerAdapter.notifyDataSetChanged();
                    hotel_viewpager.setPageMargin(30);
                    hotel_viewpager.setAdapter(viewPagerAdapter);
                } else if (result.equals(local + "펜션")) {
                    String tag = "PENSION";
                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(), pension_data, pension_map, tag);
                    viewPagerAdapter.notifyDataSetChanged();
                    pension_viewpager.setPageMargin(30);
                    pension_viewpager.setAdapter(viewPagerAdapter);
                } else if (result.equals(local + "여행코스")) {
                    String tag = "COURSE";
                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(), course_data, course_map, tag);
                    viewPagerAdapter.notifyDataSetChanged();
                    course_viewpager.setPageMargin(30);
                    course_viewpager.setAdapter(viewPagerAdapter);
                } else {
                    if (search_grid != null) {
                        SearchAdapter searchAdapter = new SearchAdapter(getContext(), search_data, search_map);
                        search_grid.setAdapter(searchAdapter);
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
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

    private ViewGroup RecommandView(LayoutInflater inflater, ViewGroup container) {
        try {
            recommand_view = (ViewGroup) inflater.inflate(R.layout.activity_threefragment, container, false);
           /* new JsoupHtmlParsing().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, local + "맛집");
            new JsoupHtmlParsing().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, local + "호텔");
            new JsoupHtmlParsing().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, local + "펜션");
            new JsoupHtmlParsing().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, local + "여행코스");*/
            new JsoupHtmlParsing().execute(local + "맛집");
            new JsoupHtmlParsing().execute(local + "호텔");
            new JsoupHtmlParsing().execute(local + "펜션");
            new JsoupHtmlParsing().execute(local + "여행코스");
            text = (TextView) recommand_view.findViewById(R.id.text);
            restaurant_viewpager = (ViewPager) recommand_view.findViewById(R.id.test_viewpager);
            hotel_viewpager = (ViewPager) recommand_view.findViewById(R.id.hotel_viewpager);
            pension_viewpager = (ViewPager) recommand_view.findViewById(R.id.pension_viewpager);
            course_viewpager = (ViewPager) recommand_view.findViewById(R.id.course_viewpager);
            marquee_text = (TextView) recommand_view.findViewById(R.id.marquee_text);
            seach_image = (ImageView) recommand_view.findViewById(R.id.search_image);
            seach_edit = (EditText) recommand_view.findViewById(R.id.search_edit);
            Picasso.with(getContext()).load(R.drawable.search_image_black).into(seach_image);
            seach_image.setOnClickListener(this);
            marquee_text.setSelected(true);
        /*refresh start*/
            mPullToRefreshView = (PullToRefreshView) recommand_view.findViewById(R.id.pull_to_refresh);
            mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mPullToRefreshView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullToRefreshView.setRefreshing(false);
                        }
                    }, 700);
                    new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
                        @Override
                        public void run() {
                            DataClear();
                            reloadView();
                        }
                    }, 1000);
                }
            });
        } catch (InflateException e) {
            e.printStackTrace();
        }
        /*refresh end*/

        return recommand_view;

    }

    private ViewGroup SearchView(LayoutInflater inflater, ViewGroup container) {
        search_view = (ViewGroup) inflater.inflate(R.layout.activity_searchfragment, container, false);
        new JsoupHtmlParsing().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, search);
        marquee_text = (TextView) search_view.findViewById(R.id.marquee_text);
        search_grid = (GridView) search_view.findViewById(R.id.search_grid);
        back_btn = (ImageView) search_view.findViewById(R.id.back_btn);
        title_two = (TextView) search_view.findViewById(R.id.title_two);
        search_edit_two = (EditText) search_view.findViewById(R.id.search_edit_two);
        search_image_two = (ImageView) search_view.findViewById(R.id.search_image_two);
        Picasso.with(getContext()).load(R.drawable.search_image_black).into(search_image_two);
        if (search != null) {
            title_two.setText("검색명 = " + search);
        }
        back_btn.setOnClickListener(this);
        search_image_two.setOnClickListener(this);
        return search_view;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("NOTIFICATION_three")) {
                reloadView();
            } else if (intent.getAction().equals("ONEFRAGMENT_NOTI")) {
                Tag = "search";
                search = intent.getExtras().getString("data");
                reloadView();

                Intent setcurrentpage = new Intent("CLICK_ONE_LIST");
                context.sendBroadcast(setcurrentpage);
            }
        }
    };

    private void DataClear() {
        restaurant_data.clear();
        hotel_data.clear();
        pension_data.clear();
        course_data.clear();
        search_data.clear();
    }
}