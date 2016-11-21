package com.lazy.android.config;

import android.widget.ImageView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

/**
 * Created by chenglei on 16/9/17.
 */
public class ConfigImage {
	public final static ImageOptions imageOptions90_90 = new ImageOptions.Builder()
		.setSize(DensityUtil.dip2px(90), DensityUtil.dip2px(90))
		.setRadius(DensityUtil.dip2px(5))
		// 如果ImageView的大小不是定义为wrap_content, 不要crop.
		.setCrop(true)
		// 加载中或错误图片的ScaleType
		//.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
//			.setLoadingDrawableId(R.mipmap.ic_launcher)
//			.setFailureDrawableId(R.mipmap.ic_launcher)
		.build();


	public static ImageOptions  getImageOptions(int w,int h){
		return new ImageOptions.Builder()
			.setSize(DensityUtil.dip2px(w), DensityUtil.dip2px(h))
			.setRadius(DensityUtil.dip2px(5))
			// 如果ImageView的大小不是定义为wrap_content, 不要crop.
			.setCrop(true)
			// 加载中或错误图片的ScaleType
			//.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
			.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
//			.setLoadingDrawableId(R.mipmap.ic_launcher)
//			.setFailureDrawableId(R.mipmap.ic_launcher)
			.build();
	}

}
