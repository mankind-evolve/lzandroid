package com.lazy.android.z_sample.xutils.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/15.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> datas;
	private String[] titles;

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> datas, String[] titles) {
		super(fm);
		this.datas = datas;
		this.titles = titles;
	}

	@Override
	public Fragment getItem(int position) {
		return datas.get(position);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
}
