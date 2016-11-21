package com.lazy.android.z_sample.divview.circleimageview;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.lazy.android.R;
import com.lazy.android.basefunc.LZUtils.UtilsDownload;
import com.lazy.android.baseui.base.LZBaseActivity;
import com.lazy.android.config.ConfigFilePath;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenglei on 16/3/8.
 */
@ContentView(R.layout.sample_divview_circleimageview)
public class CircleImageViewActivity extends LZBaseActivity {

	@ViewInject(R.id.roundheadimg)  private  CircleImageView roundheadimg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();

		String pathurl = ConfigFilePath.FILE_CACHE + "logo.jpg";
		UtilsDownload.xutilDownload("roundheadimg", null, new Callback.ProgressCallback<File>() {
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
			public void onSuccess(File result) {
				ToastShow(result.getAbsolutePath());
				roundheadimg.setImageBitmap(BitmapFactory.decodeFile(result.getAbsolutePath()));
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
		});
	}

	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(true);
		mCommonHead.setMiddleTitle("circleimageview");
	}



}
