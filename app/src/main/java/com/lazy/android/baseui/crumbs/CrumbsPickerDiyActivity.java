package com.lazy.android.baseui.crumbs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;
import com.lazy.android.baseui.common.WheelView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.ArrayList;

/**
 * Created by chenglei on 16/4/15. 使用方法
 * 传两个参数  一个是标题 一个是数据源，返回选择的position
 * Intent intent = new Intent(this, CrumbsPickerDiyActivity.class);
 * intent.putExtra("data",pettypename);
 * intent.putExtra("title","选择品种");
 * startActivityForResult(intent, CrumbsPickerDiyActivity.RESULT_CODE);
 * 在onActivityResult方法中处理
 * if(resultCode == CrumbsPickerDiyActivity.RESULT_CODE){
 * int position = data.getIntExtra(CrumbsPickerDiyActivity.INTENT_KEY,0);
 * }
 */

@ContentView(R.layout.lz_crumbs_picker_sex_activity)
public class CrumbsPickerDiyActivity extends LZBaseActivity {

	public final static int RESULT_CODE = 10;
	public final static String INTENT_KEY = "result";


	private ArrayList<String> PLANETS;
	private int selecteid = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
//    获得数据源和标题
		PLANETS = (ArrayList<String>) intent.getSerializableExtra("data");
		String title = intent.getStringExtra("title");

		TextView titleTextView = (TextView) findViewById(R.id.crumbs_picker_title);
		titleTextView.setText(title);

		WheelView crumbs_picker_wheel = (WheelView) findViewById(R.id.crumbs_picker_wheel);
		crumbs_picker_wheel.setOffset(1);
		crumbs_picker_wheel.setItems(PLANETS);
		crumbs_picker_wheel.setSeletion(0);
		crumbs_picker_wheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
			@Override
			public void onSelected(int selectedIndex, String item) {
				selecteid = selectedIndex - 1;
			}
		});


	}


	@Event(R.id.crumbs_pickersex_button)
	private void crumbs_pickersex_button_Event(View view) {

		Intent intent = new Intent();
		intent.putExtra(INTENT_KEY, selecteid);
		setResult(RESULT_CODE, intent);
		this.finish();

	}


}

