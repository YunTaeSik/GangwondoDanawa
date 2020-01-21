package com.example.sky87.gangwon.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sky87.gangwon.R;

/**
 * Created by wayne on 2016-07-07.
 */
public class DetailDialog extends Activity {
	private TextView title_text;
	private TextView location_text;
	private TextView tele_text;
	private TextView menu_text;
	private Button info_BtnOk;
	private LinearLayout menu_layout;
	private LinearLayout tele_layout;

	private String title, location, tele, menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		super.onCreate(savedInstanceState);
		setFinishOnTouchOutside(false);

		setContentView(R.layout.info_alert);

		title_text = (TextView) findViewById(R.id.title_text);
		location_text = (TextView) findViewById(R.id.location_text);
		tele_text = (TextView) findViewById(R.id.tele_text);
		menu_text = (TextView) findViewById(R.id.menu_text);
		info_BtnOk = (Button) findViewById(R.id.info_BtnOk);
		menu_layout = (LinearLayout) findViewById(R.id.menu_layout);
		tele_layout = (LinearLayout) findViewById(R.id.tele_layout);

		title = getIntent().getStringExtra("title");
		location = getIntent().getStringExtra("location");
		tele = getIntent().getStringExtra("tele");
		menu = getIntent().getStringExtra("menu");


		if (title != null) {
			title_text.setText(title);
		}
		if (location != null) {
			location_text.setText(location);
		}
		if (tele != null && tele.length() > 0) {
			tele_text.setText(tele);
		} else {
			tele_layout.setVisibility(View.GONE);
		}
		if (menu != null && menu.length() > 0) {
			menu_text.setText(menu);
		} else {
			menu_layout.setVisibility(View.GONE);
		}

		info_BtnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}
}
