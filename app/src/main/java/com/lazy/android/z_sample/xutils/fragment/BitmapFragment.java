package com.lazy.android.z_sample.xutils.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lazy.android.R;
import com.lazy.android.basefunc.LZUtils.UtilsHttpImageLoader;
import com.lazy.android.baseui.base.LZBaseFragment;
import com.lazy.android.baseui.crumbs.CrumbsCityPick.BaseActivity;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/1/15.
 */
@ContentView(R.layout.sample_fragment_bitmapfragment)
public class BitmapFragment extends LZBaseFragment {
	@ViewInject(R.id.sample_bitmap_url) private ImageView sample_bitmap_url;
	private ImageOptions imageOptions;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initImageConfig();
		initdata();



	}



	private void initImageConfig() {
		imageOptions = new ImageOptions.Builder()
			.setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
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



	//	初始化数据源
	private void initdata() {

		x.image().bind(sample_bitmap_url,
			"http://m.yanyr.com/logo_3_fci.png",
			imageOptions,
			new CustomBitmapLoadCallBack());
	}


//	图片加载回调
	public class CustomBitmapLoadCallBack implements Callback.ProgressCallback<Drawable> {

		public CustomBitmapLoadCallBack() {
		}

		@Override
		public void onWaiting() {

		}

		@Override
		public void onStarted() {

		}

		@Override
		public void onLoading(long total, long current, boolean isDownloading) {
		}

		@Override
		public void onSuccess(Drawable result) {

		}

		@Override
		public void onError(Throwable ex, boolean isOnCallback) {
		}

		@Override
		public void onCancelled(CancelledException cex) {

		}

		@Override
		public void onFinished() {

		}
	}



	@Event(R.id.sample_bitmap_url)
	private void sample_bitmap_url_click(View view){

	}

}
