package com.lazy.android.z_sample.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by chenglei on 16/6/27.
 */
@ContentView(R.layout.sample_baidumap_activity)
public class SampleBaidumapIndexActivity extends LZBaseActivity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		初始化百度地图
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext
		//注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		initCommonHead();
	}



//	地图初始化
	@Event(R.id.sample_baidumap_init)
	private void sample_baidumap_init_Event(View view){
		startActivity(new Intent(this,BaidumapInitActivity.class));
	}


//	地图检索
	@Event(R.id.sample_baidumap_search)
	private void sample_baidumap_search_Event(View view){
		startActivity(new Intent(this,BaidumapSearchActivity.class));
	}

//	地图线路检索
	@Event(R.id.sample_baidumap_line)
	private void sample_baidumap_line_Event(View view){
		startActivity(new Intent(this,BaidumapRoadSearchActivity.class));
	}

//	地图定位
	@Event(R.id.sample_baidumap_location)
	private void sample_baidumap_location_Event(View view){
		startActivity(new Intent(this,BaidumapLocationActivity.class));
	}

	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(false);
		mCommonHead.setMiddleTitle("百度地图");
	}
}
