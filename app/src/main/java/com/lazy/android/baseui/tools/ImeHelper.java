package com.lazy.android.baseui.tools;

import android.app.Activity;
import android.content.Context;
import android.os.ResultReceiver;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * @ClassName: ImeHelper
 * @Description: 软键盘显示、隐藏控制
 * @author
 * @date
 * 
 */
public class ImeHelper {
	/**
	 * @Title: setNoTitleAndInput
	 * @Description: 无标题activity且隐藏键盘
	 * @param
	 * @return void
	 */
	public static final void setNoTitleAndInput(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	/**
	 * @Title: setSoftInputMode
	 * @Description: 设置软键盘状态
	 * @param activity
	 * @param alwaysVisible
	 * @return void
	 */
	public static final void setSoftInputMode(Activity activity,
			boolean alwaysVisible) {
		if (alwaysVisible) {
			activity.getWindow()
					.setSoftInputMode(
							WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
									| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		} else {
			activity.getWindow()
					.setSoftInputMode(
							WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
									| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		}
	}

	public final static void toggleIME(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, 0);
	}

	/**
	 * @Title: hideIME
	 * @Description: 隐藏键盘
	 * @param activity
	 * @return void
	 */
	public static final void hideIME(Activity activity) {
		activity.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	/**
	 * @Title: hideIME
	 * @Description: 隐藏键盘
	 * @param v
	 * @return void
	 */
	public final static void hideIME(View v) {
		if (v == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	/**
	 * @Title: showIME
	 * @Description: 显示键盘
	 * @param v
	 * @return void
	 */
	public final static void showIME(View v) {
		v.requestFocus();
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(v, 0);
	}

	/**
	 * @Title: showIME
	 * @Description: 显示键盘
	 * @param v
	 * @param receiver
	 * @return void
	 */
	public final static void showIME(View v, ResultReceiver receiver) {
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(v, 0, receiver);
	}

	public static boolean getInputState(Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		return inputMethodManager.isActive();
	}
}
