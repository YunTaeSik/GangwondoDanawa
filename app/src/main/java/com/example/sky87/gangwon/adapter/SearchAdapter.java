package com.example.sky87.gangwon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sky87.gangwon.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sky87 on 2016-06-27.
 */
public class SearchAdapter extends BaseAdapter {
    private View search_layout;
    private ArrayList mList;
    private Map<String, String> mMap = new HashMap<String, String>();
    private Context context;
    private LinearLayout search_item_layout;
    private ImageView search_blog_image;
    private TextView search_blog_title;
    private TextView search_blog_name;

    private String image;


    public SearchAdapter(Context context, ArrayList list, Map<String, String> hashMap) {
        this.context = context;
        this.mList = list;
        this.mMap = hashMap;
    }

    @Override
    public int getCount() {
        if (mList.size() <= 10) {
            return mList.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        search_layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        try {
            String title = mMap.get("title" + position);
            image = mMap.get("image" + position).replace("type=s88", "type=w2");
            final String link = mMap.get("link" + position);
            String blogname = mMap.get("blogname" + position);
            String date = mMap.get("date" + position);

            search_item_layout = (LinearLayout) search_layout.findViewById(R.id.search_item_layout);
            search_blog_image = (ImageView) search_layout.findViewById(R.id.search_blog_image);
            search_blog_title = (TextView) search_layout.findViewById(R.id.search_blog_title);
            search_blog_name = (TextView) search_layout.findViewById(R.id.search_blog_name);
            Transformation transformation = new CircleTransform();
            //Picasso.with(context).load(image).transform(transformation).into(search_blog_image);  //라운드 뷰
            Picasso.with(context).load(image).into(search_blog_image);  //일반 사각형 뷰
            search_blog_title.setText(title);
            search_blog_name.setText(blogname);
            search_item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(link);
                    intent.setData(uri);
                    context.startActivity(intent);
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return search_layout;
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


}
