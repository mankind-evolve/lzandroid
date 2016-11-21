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

import com.lazy.android.R;

import static com.lazy.android.baseui.common.pulltorefresh.PullToRefreshBase.*;


public class HeaderLoadingLayout extends FrameLayout {
	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;
	private final ImageView headerImage;
	private final ProgressBar headerProgress;
	private final TextView headerText;

	// 上拉时,刷新时,下滑时显示的文本
	private String pullLabel;
	private String refreshingLabel;
	private String releaseLabel;
	private String subLabel;

	private final Animation rotateAnimation, resetRotateAnimation;

	public HeaderLoadingLayout(Context context, final int mode) {
		this(context, mode, null, null, null, null);
	}

	public HeaderLoadingLayout(Context context, final int mode, String releaseLabel, String pullLabel,
			String refreshingLabel, String subLable) {
		super(context);

		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_vertical,
				this);
		headerText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
		headerImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
		headerProgress = (ProgressBar) header.findViewById(R.id.pull_to_refresh_progress);
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
			releaseLabel = context.getString(R.string.pull_to_refresh_release_header_label);

		}
		if (pullLabel == null) {
			pullLabel = context.getString(R.string.pull_to_refresh_pull_header_label);
		}
		if (refreshingLabel == null) {
			refreshingLabel = context.getString(R.string.pull_to_refresh_refreshing_header_label);
		}
		if (subLable == null) {
			subLable = context.getString(R.string.pull_to_refresh_sub_header_label);
		}
		this.releaseLabel = releaseLabel;
		this.pullLabel = pullLabel;
		this.refreshingLabel = refreshingLabel;
		this.subLabel = subLable;
		// 不同的模式,区分头或脚
		switch (mode) {
		case MODE_PULL_UP_TO_REFRESH:
			headerImage.setImageResource(R.drawable.pulltorefresh_up_arrow);
			break;
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			headerImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
			break;
		}
	}

	// ----------------------------
	//
	// ----------------------------
	public void releaseToRefresh() {
		headerText.setText(releaseLabel);
		headerImage.clearAnimation();
		headerImage.startAnimation(rotateAnimation);
	}

	public void pullToRefresh() {
		headerText.setText(pullLabel);
		headerImage.clearAnimation();
		headerImage.startAnimation(resetRotateAnimation);
	}

	// -----------------------------
	// 刷新
	// ----------------------------
	/** 刷新中 */
	public void refreshing() {
		headerText.setText(refreshingLabel);
		headerImage.clearAnimation();
		headerImage.setVisibility(View.INVISIBLE);
		headerProgress.setVisibility(View.VISIBLE);
	}

	public void setLastPager() {
		headerText.setText(subLabel);
		headerImage.setVisibility(View.GONE);
		headerProgress.setVisibility(View.GONE);
	}

	public void reset() {
		headerText.setText(pullLabel);
		headerImage.setVisibility(View.VISIBLE);
		headerProgress.setVisibility(View.GONE);
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
		headerText.setTextColor(color);
	}
}
