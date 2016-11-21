package com.lazy.android.z_sample.divview.orderlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;
import com.lazy.android.baseui.common.CommonPagerAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by chenglei on 16/10/4.
 */
@ContentView(R.layout.lz_divview_activity_orderlist)
public class orderlistActivity extends LZBaseActivity {

	@ViewInject(R.id.pagerSlidingTabStrip) private PagerSlidingTabStrip pagerSlidingTabStrip;
	@ViewInject(R.id.viewpager) private ViewPager viewpager;

	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	private CommonPagerAdapter pagerAdapter;
	private String[] TITLES = {"全部", "待付款", "待发货", "待收货", "待评价","已经完成","已取消","已签收","正在运输"};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();
		initPager();

	}

//	初始化页面
	private void initPager() {

		for(int i = 0 ; i < TITLES.length ; i++){
			fragments.add(commonOrderFragment.newInstance(TITLES[i]));
		}
		pagerAdapter = new CommonPagerAdapter(getSupportFragmentManager(),fragments,TITLES);
		viewpager.setAdapter(pagerAdapter);
		pagerSlidingTabStrip.setViewPager(viewpager);

	}


	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(true);
		mCommonHead.setMiddleTitle("订单列表页面");
	}

}
