package com.lazy.android.baseui.common;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by chenglei on 16/8/5.
 */

public class CommonPagerAdapter extends FragmentStatePagerAdapter {
	private String[] TITLES;
	private ArrayList<Fragment> fragments;

	public CommonPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments,String[] TITLES) {
		super(fm);
		this.fragments = fragments;
		this.TITLES = TITLES;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}



}
