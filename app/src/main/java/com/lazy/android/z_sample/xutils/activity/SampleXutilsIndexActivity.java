package com.lazy.android.z_sample.xutils.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;
import com.lazy.android.z_sample.xutils.adapter.FragmentAdapter;
import com.lazy.android.z_sample.xutils.fragment.BitmapFragment;
import com.lazy.android.z_sample.xutils.fragment.DbFragment;
//import com.lazy.android.sample.xutils.fragment.HttpFragment;
import com.lazy.android.z_sample.xutils.fragment.HttpIFragment;
import com.lazy.android.z_sample.xutils.fragment.ViewFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by chenglei on 16/1/15.
 */
@ContentView(R.layout.sample_xutils_activity_index)
public class SampleXutilsIndexActivity extends LZBaseActivity {

	@ViewInject(R.id.sample_index_viewpage) private ViewPager viewPager;
	@ViewInject(R.id.sample_index_pagetab) private PagerTabStrip pagerTabStrip;

	private ArrayList<Fragment> datas;
	private FragmentAdapter fragmentAdapter;
	private String[] mtitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initdata();
		initCommonHead();
		initView();
	}




	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setMiddleTitle("Xutils");
	}


	//	初始化数据
	private void initdata() {
		datas = new ArrayList<Fragment>();
		datas.add(new ViewFragment());
		datas.add(new HttpIFragment());
		datas.add(new DbFragment());
		datas.add(new BitmapFragment());

		mtitle = new String[]{"view(xutils)", "http(xutils)", "db(xutils)", "bitmap(xutils)"};
	}


	public void initView() {
		fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),datas,mtitle);
		viewPager.setAdapter(fragmentAdapter);
	}

}
