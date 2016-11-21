package com.lazy.android.baseui.common.pulltorefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lazy.android.R;


public class FooterLoadingLayout extends FrameLayout {
	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

	private final ImageView footerImage;
	private final ProgressBar footerProgress;
	private final TextView footerText;

	// 上拉时,刷新时,下滑时显示的文本
	private String pullLabel;
	private String refreshingLabel;
	private String releaseLabel;
	private String subLabel;
	private Context context;

	private final Animation rotateAnimation, resetRotateAnimation;

	public FooterLoadingLayout(Context context, final int mode) {
		this(context, mode, null, null, null, null);
		this.context = context;
	}

	public FooterLoadingLayout(Context context, final int mode, String releaseLabel, String pullLabel,
				   String refreshingLabel, String subLable) {
		super(context);
		this.context = context;

		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_vertical, this);
		footerText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
		footerImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
		footerProgress = (ProgressBar) header.findViewById(R.id.pull_to_refresh_progress);
		final Interpolator interpolator = new LinearInterpolator();
		// 动画放到Xml中
		rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
			0.5f);
		rotateAnimation.setInterpolator(interpolator);
		rotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		rotateAnimation.setFillAfter(true);

		resetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
			Animation.RELATIVE_TO_SELF, 0.5f);
		resetRotateAnimation.setInterpolator(interpolator);
		resetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		resetRotateAnimation.setFillAfter(true);

		if (releaseLabel == null) {
			releaseLabel = context.getString(R.string.pull_to_refresh_release_footer_label);
		}
		if (pullLabel == null) {
			pullLabel = context.getString(R.string.pull_to_refresh_pull_footer_label);
		}
		if (refreshingLabel == null) {
			refreshingLabel = context.getString(R.string.pull_to_refresh_refreshing_footer_label);
		}
		if (subLable == null) {
			subLable = context.getString(R.string.pull_to_refresh_sub_footer_label);
		}

		// 不同的模式,区分头或脚
		switch (mode) {
			case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
				footerText.setText(getResources().getString(R.string.pull_to_refresh_pull_footer_label));
				footerImage.setImageResource(R.drawable.pulltorefresh_up_arrow);
				break;
			case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
			default:
				footerImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
				footerText.setText(getResources().getString(R.string.pull_to_refresh_pull_header_label));
				break;
		}
		this.releaseLabel = releaseLabel;
		this.pullLabel = pullLabel;
		this.refreshingLabel = refreshingLabel;
		this.subLabel = subLable;
	}

	// ----------------------------
	//
	// ----------------------------
	public void releaseToRefresh() {
		footerText.setText(releaseLabel);
		footerImage.clearAnimation();
		footerImage.startAnimation(rotateAnimation);
	}

	public void pullToRefresh() {
		footerText.setText(pullLabel);
		footerImage.clearAnimation();
		footerImage.startAnimation(resetRotateAnimation);
	}

	// -----------------------------
	// 刷新
	// ----------------------------

	/**
	 * 刷新中
	 */
	public void refreshing() {
		footerText.setText(refreshingLabel);
		footerImage.clearAnimation();
		footerImage.setVisibility(View.INVISIBLE);
		footerProgress.setVisibility(View.VISIBLE);
	}

	public void setLastPage() {
		Toast.makeText(context,context.getResources().getString(R.string.pull_to_refresh_sub_footer_label),Toast.LENGTH_SHORT).show();
		footerText.setText(subLabel);
		footerImage.setVisibility(View.INVISIBLE);
		footerProgress.setVisibility(View.GONE);
	}

	public void reset() {
		footerText.setText(pullLabel);
		footerImage.setVisibility(View.VISIBLE);
		footerProgress.setVisibility(View.GONE);
	}

	// --------------------
	// 设置显示的文本
	// --------------------
	public void setRefreshingLabel(String refreshingLabel) {
		this.refreshingLabel = refreshingLabel;
	}

	public void setReleaseLabel(String releaseLabel) {
		this.releaseLabel = releaseLabel;
	}

	public void setPullLabel(String pullLabel) {
		this.pullLabel = pullLabel;
	}

	public void setSubLabel(String subLable) {
		this.subLabel = subLable;
	}

	public void setTextColor(int color) {
		footerText.setTextColor(color);
	}
}
