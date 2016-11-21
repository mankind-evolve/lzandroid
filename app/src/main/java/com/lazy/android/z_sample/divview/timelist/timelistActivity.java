package com.lazy.android.z_sample.divview.timelist;

import android.os.Bundle;
import android.view.View;

import com.lazy.android.R;
import com.lazy.android.basefunc.LZUtils.UtilsDate;
import com.lazy.android.baseui.base.LZBaseActivity;
import com.lazy.android.baseui.common.CommonAdapterTextonly;
import com.lazy.android.baseui.common.mHorizontalScrollView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by chenglei on/28.
 */
@ContentView(R.layout.sample_divview_timelist_activity)
public class timelistActivity extends LZBaseActivity {

	//	日期列表元素
	private ArrayList<Long> data = new ArrayList<>();
	private ArrayList<String> datastr = new ArrayList<>();
	private CommonAdapterTextonly adapter;
	@ViewInject(R.id.mhorizontalScrollView) private mHorizontalScrollView mhorizontalScrollView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();
		initDateData();
		initpage();

	}


	private void initpage() {
		adapter = new CommonAdapterTextonly(this,R.layout.lz_item_textonly,datastr);
		//添加滚动回调
//		buyhistory_horizontalScrollView
//			.setCurrentImageChangeListener(new mHorizontalScrollView.CurrentImageChangeListener()
//			{
//				@Override
//				public void onCurrentImgChanged(int position,View viewIndicator){
////					hscroll_image.setImageResource(mDatas.get(position));
//					viewIndicator.setBackgroundColor(getResources().getColor(R.color.m_main_bg));
//				}
//			});
		//添加点击回调
		mhorizontalScrollView.setOnItemClickListener(new mHorizontalScrollView.OnItemClickListener()
		{

			@Override
			public void onClick(View view, int position)
			{
				view.setBackgroundColor(getResources().getColor(R.color.m_white));
				ToastShow(" 系统时间戳 = " + data.get(position) );

			}
		});
		//设置适配器
		mhorizontalScrollView.initDatas(adapter);
	}


	//	初始化显示的时间,可根据需求自定制
	public void initDateData(){
		//系统时间
		long  systemTime = System.currentTimeMillis();
		for (int index = 0;index < 15; index++) {
			systemTime = systemTime - 60 * 60 * 24 * 1000;
			String weekStr = UtilsDate.judgeWeek(systemTime);
			String systemDate = UtilsDate.getMd(systemTime);
			data.add(systemTime);
			datastr.add(systemDate + "\n" + weekStr);
		}
	}


	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(true);
		mCommonHead.setMiddleTitle("滚动时间列表");
	}



}
