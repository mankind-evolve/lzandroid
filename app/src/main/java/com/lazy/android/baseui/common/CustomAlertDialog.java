package com.lazy.android.baseui.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomAlertDialog extends Dialog {

	/**
	 * 布局文件 *
	 */
	int layoutRes;
	/**
	 * 上下文对象 *
	 */
	Context context;
	/**
	 * 确定按钮 *
	 */
	private Button btnConfirm;
	/**
	 * 取消按钮 *
	 */
	private Button btnCancel;

	public CustomAlertDialog(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 自定义布局的构造方法
	 *
	 * @param context
	 * @param resLayout
	 */
	public CustomAlertDialog(Context context, int resLayout) {
		super(context);
		this.context = context;
		this.layoutRes = resLayout;
	}

	/**
	 * 自定义主题及布局的构造方法
	 *
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public CustomAlertDialog(Context context, int theme, int resLayout) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
		this.setContentView(layoutRes);
//		// 根据id在布局中找到控件对象
//		btnConfirm = (Button) this.findViewById(R.id.btn_confirm);
//		btnCancel = (Button) this.findViewById(R.id.btn_cancel);
//		// 设置按钮的文本颜色
//		btnConfirm.setTextColor(0xff1E90FF);
//		btnCancel.setTextColor(0xff1E90FF);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 指定布局
		/*this.setContentView(layoutRes);
		// 根据id在布局中找到控件对象
		btnConfirm = (Button) this.findViewById(R.id.btn_confirm);
		btnCancel = (Button) this.findViewById(R.id.btn_cancel);
		// 设置按钮的文本颜色
		btnConfirm.setTextColor(0xff1E90FF);
		btnCancel.setTextColor(0xff1E90FF);*/
	}

	public void setDescription(String description) {
//		TextView tv = (TextView) this.findViewById(R.id.tv_description);
//		tv.setSingleLine(false);
//		tv.setMaxLines(3);
//		tv.setText(description);
	}

	public View getCancel() {
		return btnCancel;
	}

	public View getConfirm() {
		return btnConfirm;
	}
}