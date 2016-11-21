package com.lazy.android.basefunc.LZUtils;

import android.widget.ImageView;

import com.lazy.android.R;
import com.lazy.android.config.ConfigSystem;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by chenglei on 16/9/17.
 */
public class UtilsImages {

	//	xutils 图片大小配置类
	public static ImageOptions getImageOptions(int w, int h) {
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


	//	绑定图片的控件
	public static void bind(String url, ImageView imageview, int w, int h) {
		ImageOptions imageOptions = new ImageOptions.Builder()
			.setSize(DensityUtil.dip2px(w), DensityUtil.dip2px(h))
			.setRadius(DensityUtil.dip2px(5))
			// 如果ImageView的大小不是定义为wrap_content, 不要crop.
			.setCrop(true)
			// 加载中或错误图片的ScaleType
			//.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
			.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
			//设置加载过程中的图片
//			.setLoadingDrawableId(R.drawable.ic_launcher)
			//设置加载失败后的图片
//			.setFailureDrawableId(R.drawable.ic_launcher)
			//设置使用缓存
//			.setUseMemCache(true)
			//设置支持gif
//			.setIgnoreGif(false)
			//设置显示圆形图片
//      .setCircular(false)
			.build();

		if (url.equals("http")) {
			x.image().bind(imageview, url, imageOptions);
		} else {
			x.image().bind(imageview, url, imageOptions);
		}
	}


}
