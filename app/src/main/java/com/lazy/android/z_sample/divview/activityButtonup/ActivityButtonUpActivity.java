package com.lazy.android.z_sample.divview.activityButtonup;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by chenglei on 16/4/14.
 * 使用方法，正常写布局
 * 注册activity的时候指定主题 android:theme="@style/lz_activity_buttonup"
 * 添加  解决左右两边有空隙 和 点击其他的地方关闭页面 的解决代码
 */
@ContentView(R.layout.sample_divview_activity_buttonup_activity)
public class ActivityButtonUpActivity extends LZBaseActivity {

	@ViewInject(R.id.activity_button_up) private RelativeLayout activity_button_up;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		解决左右两边有空隙
		getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

//		点击其他的地方关闭页面
		activity_button_up.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				finish();
				return true;
			}
		});
	}



	@Event(value = R.id.activity_button_up_take)
	private void activity_button_up_take_Event(View view){
		ToastShow("拍照");
	}


	@Event(value = R.id.activity_button_up_select)
	private void activity_button_up_select_Event(View view){
		ToastShow("选择相册");
	}


	@Event(value = R.id.activity_button_up_cancle)
	private void activity_button_up_cancle_Event(View view){
		ToastShow("取消");
	}




}
