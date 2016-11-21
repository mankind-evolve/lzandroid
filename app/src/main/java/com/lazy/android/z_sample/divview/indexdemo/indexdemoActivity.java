package com.lazy.android.z_sample.divview.indexdemo;

import android.os.Bundle;
import android.view.View;

import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;

/**
 * Created by chenglei on 16/9/28.
 */
@ContentView(R.layout.lz_indexdemo_activity)
public class indexdemoActivity extends LZBaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonFoot();
	}


	@Override
	public void initCommonFoot() {
		super.initCommonFoot();
		mCommonFood.setmenuclick(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommonFood.menuselected(1);
			}
		}, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommonFood.menuselected(2);
			}
		}, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommonFood.menuselected(3);
			}
		}, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommonFood.menuselected(4);
			}
		}, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommonFood.menuselected(5);
			}
		});
	}
}
