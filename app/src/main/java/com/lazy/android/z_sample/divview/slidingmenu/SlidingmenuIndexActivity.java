package com.lazy.android.z_sample.divview.slidingmenu;


import android.os.Bundle;

import com.lazy.android.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

/**
 * Created by Administrator on 2016/3/4.
 */
public class SlidingmenuIndexActivity extends SlidingActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_divview_slidingmenu_index_activity);
		initslidingmenu();
	}


	//	初始化slidingmenu
	private void initslidingmenu() {
		setBehindContentView(R.layout.sample_divview_slidingmenu_leftlayout);
		SlidingMenu menu = getSlidingMenu();

		menu.setMode(SlidingMenu.LEFT_RIGHT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//		menu.setShadowWidthRes(R.dimen.shadow_width);
//		menu.setShadowDrawable(R.drawable.shadow);

		// 设置滑动菜单视图的宽度
		menu.setBehindOffsetRes(R.dimen.px_200);
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.01f);
		menu.setSecondaryMenu(R.layout.sample_divview_slidingmenu_leftlayout);
	}


}
