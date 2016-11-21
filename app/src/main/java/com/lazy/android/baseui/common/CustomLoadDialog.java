package com.lazy.android.baseui.common;

import com.lazy.android.R;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

/**
 * 自定义进度对话框
 * 
 * @ClassName: CustomLoadDialog
 * @Description: TODO
 * @author
 * @date
 */
public class CustomLoadDialog extends Dialog {

	private Context mContext;
	private View mContentView;

	private TextView mTitleText;
	private TextView mContentText;

	public CustomLoadDialog(Context context) {
		super(context, R.style.Custom_dialog_style);
		this.mContext = context;
		mContentView = View.inflate(mContext, R.layout.lz_custom_load_dialog, null);
		mTitleText = (TextView) mContentView
				.findViewById(R.id.txt_loadDialog_title);
		mContentText = (TextView) mContentView
				.findViewById(R.id.txt_loadDialog_content);
		LayoutParams localLayoutParams = getWindow().getAttributes();
		onWindowAttributesChanged(localLayoutParams);
		setCanceledOnTouchOutside(false);
		setCancelable(true);
		mContentView.setMinimumWidth(10000);
		localLayoutParams.gravity = Gravity.CENTER;
		setContentView(mContentView);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		if (TextUtils.isEmpty(title)) {
			mTitleText.setVisibility(View.GONE);
		} else {
			mTitleText.setText(title);
			mTitleText.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setContent(String content) {
		mContentText.setText(content);
	}

}
