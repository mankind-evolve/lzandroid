package com.lazy.android.z_sample.divview.HorizontalScrollView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;
import com.lazy.android.baseui.common.mHorizontalScrollView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenglei on 16/8/30.
 */

@ContentView(R.layout.lz_hscroll_activity)
public class Activity_hscroll extends LZBaseActivity {

	@ViewInject(R.id.buy_horizontalScrollView) private mHorizontalScrollView buy_m_horizontalScrollView;
	@ViewInject(R.id.hscroll_image) private ImageView hscroll_image;


	private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
		R.drawable.lz_hscroll_a,R.drawable.lz_hscroll_b,R.drawable.lz_hscroll_c,
		R.drawable.lz_hscroll_d,R.drawable.lz_hscroll_e,R.drawable.lz_hscroll_f,
		R.drawable.lz_hscroll_g,R.drawable.lz_hscroll_l));

	private Adapter_hscroll_data mAdapter;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();
		mAdapter = new Adapter_hscroll_data(this,R.layout.lz_hscroll_item,mDatas);

		//添加滚动回调
//		buy_m_horizontalScrollView
//			.setCurrentImageChangeListener(new mHorizontalScrollView.CurrentImageChangeListener()
//			{
//				@Override
//				public void onCurrentImgChanged(int position,
//												View viewIndicator)
//				{
//					hscroll_image.setImageResource(mDatas.get(position));
//
//					viewIndicator.setBackgroundColor(Color.parseColor("#AA024DA4"));
//				}
//			});

		//添加点击回调
		buy_m_horizontalScrollView.setOnItemClickListener(new mHorizontalScrollView.OnItemClickListener()
		{

			@Override
			public void onClick(View view, int position)
			{
				hscroll_image.setImageResource(mDatas.get(position));
				view.setBackgroundColor(Color.parseColor("#AA024DA4"));
			}
		});
		//设置适配器
		buy_m_horizontalScrollView.initDatas(mAdapter);

	}



	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(true);
		mCommonHead.setMiddleTitle("横向滚动相册");
	}
}
