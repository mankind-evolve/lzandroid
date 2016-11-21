package com.lazy.android.z_sample.xutils.fragment;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lazy.android.R;
import com.lazy.android.basefunc.LZUtils.UtilsDownload;
import com.lazy.android.basefunc.LZUtils.UtilsStringNum;
import com.lazy.android.baseprotocol.LZHttpProtocolHandlerBase;
import com.lazy.android.config.ConfigFilePath;
import com.lazy.android.config.ConfigProtocolType;
import com.lazy.android.baseui.base.LZBaseFragment;
import com.lazy.android.z_sample.xutils.protocol.SampleGetProtocol;
import com.lazy.android.z_sample.xutils.protocol.SamplePostProtocol;
import com.lazy.android.z_sample.xutils.protocol.SampleUploadProtocol;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by Administrator on 2016/1/15.
 */
@ContentView(R.layout.sample_fragment_httpfragment)
public class HttpIFragment extends LZBaseFragment {

	@ViewInject(R.id.xutils_http_get)  TextView xutils_http_get;
	@ViewInject(R.id.xutils_http_post)  TextView xutils_http_post;
	@ViewInject(R.id.xutils_http_result) TextView xutils_http_result;
	@ViewInject(R.id.xutils_http_down_ser) TextView xutils_http_down_ser;
	@ViewInject(R.id.xutils_http_down_sers) TextView xutils_http_down_sers;
	@ViewInject(R.id.xutils_http_down_http) TextView xutils_http_down_http;
	@ViewInject(R.id.xutils_http_upload) TextView xutils_http_upload;



	@Event(R.id.xutils_http_get)
	private void xutils_http_get_click(View v) {
		new SampleGetProtocol(getBaseActivity(), this);
	}

	@Event(R.id.xutils_http_post)
	private void xutils_http_post_click(View v) {
		new SamplePostProtocol(getBaseActivity(), this);
	}




	@Event(R.id.xutils_http_down_http)
	private void  xutils_http_down_http_click(View view){
		String url = "http://dx5.xiazaiba.com/Soft/W/winrar_5.3.exe";
		String saveurl = ConfigFilePath.FILE_IMAGE + "test.png";
		UtilsDownload.xutilDownload(url,saveurl,new Callback.ProgressCallback<File>(){
			@Override
			public void onSuccess(File result) {
				Log.i("lzandroid","onSuccess");
				Log.i("lzandroid","file=" + result.getPath());
				xutils_http_result.setText(result.getPath());
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				Log.i("lzandroid","onError");
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Log.i("lzandroid","onCancelled");
			}

			@Override
			public void onFinished() {
				Log.i("lzandroid","onFinished");
			}

			@Override
			public void onWaiting() {
				Log.i("lzandroid","onWaiting");
			}

			@Override
			public void onStarted() {
				Log.i("lzandroid","onStarted");
			}

			@Override
			public void onLoading(long total, long current, boolean isDownloading) {
				Log.i("lzandroid","onLoading" + "total=" + total + "current=" + current);
				xutils_http_result.setText(UtilsStringNum.percentage(total,current) + "%");
			}
		});

	}


//	上传文件
	@Event(R.id.xutils_http_upload)
	private void xutils_http_upload_click(View v){
		String url = "http://m.yanyr.com/logo_3_fci.png";
		String saveurl = ConfigFilePath.FILE_IMAGE + "testimg.png";
		UtilsDownload.xutilDownload(url,saveurl,new Callback.ProgressCallback<File>(){
			@Override
			public void onSuccess(File result) {
				Log.i("lzandroid","onSuccess");
				Log.i("lzandroid","file=" + result.getPath());
				xutils_http_result.setText(result.getPath());
				new SampleUploadProtocol(getActivity(),HttpIFragment.this,result.getPath());
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				Log.i("lzandroid","onError");
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Log.i("lzandroid","onCancelled");
			}

			@Override
			public void onFinished() {
				Log.i("lzandroid","onFinished");
			}

			@Override
			public void onWaiting() {
				Log.i("lzandroid","onWaiting");
			}

			@Override
			public void onStarted() {
				Log.i("lzandroid","onStarted");
			}

			@Override
			public void onLoading(long total, long current, boolean isDownloading) {
				Log.i("lzandroid","onLoading" + "total=" + total + "current=" + current);
				xutils_http_result.setText(UtilsStringNum.percentage(total,current) + "%");
			}
		});

	}



//	@Event(R.id.xutils_http_down_ser)
//	private void xutils_http_down_ser_click(View view){
//		String url = "http://dx5.xiazaiba.com/Soft/W/winrar_5.3.exe";
//		String saveurl = ConfigFilePath.FILE_IMAGE + "test.png";
//		String label = "lable";
//		try {
//			DownloadService.getDownloadManager().startDownload(
//				url, label,
//				saveurl, true, false, new DownloadViewHolder(null,null) {
//					@Override
//					public void onWaiting() {
//						Log.i("lzandroid","onWaiting");
//					}
//
//					@Override
//					public void onStarted() {
//						Log.i("lzandroid","onStarted");
//					}
//
//					@Override
//					public void onLoading(long total, long current) {
//
//					}
//
//					@Override
//					public void onSuccess(File result) {
//
//					}
//
//					@Override
//					public void onError(Throwable ex, boolean isOnCallback) {
//
//					}
//
//					@Override
//					public void onCancelled(Callback.CancelledException cex) {
//
//					}
//				});
//		} catch (DbException e) {
//			e.printStackTrace();
//		}
//	}



//	@Event(R.id.xutils_http_down_sers)
//	private void xutils_http_down_sers_click(View view){
//
//		String url = "http://m.yanyr.com/logo_3_fci.png";
//
//		String label = "xUtils_" + System.nanoTime();
//
//		try {
//			DownloadService.getDownloadManager().startDownload(url,
//				label,"/sdcard/xUtils/" + label + ".png",
//				true, false, null);
//		} catch (DbException e) {
//			e.printStackTrace();
//		}
//
////		new Callback.ProgressCallback
//	}


//
//	@Event(R.id.xutils_http_put)
//	private void xutils_http_put_click(View v) {
////		new SamplePutProtocol(getBaseActivity(),this);
//		xutils_http_result.setText("服务器不支持PUT请求");
//
//
//		try {
//			for (int i = 0; i < 5; i++) {
//				String url = "http://m.yanyr.com/logo_3_fci.png";
//				String label = i + "xUtils_" + System.nanoTime();
//				DownloadService.getDownloadManager().startDownload(
//					url, label,
//					"/sdcard/xUtils/" + label + ".png", true, false, null);
//			}
//		} catch (DbException e) {
//			e.printStackTrace();
//		}
//
//	}


//	@Event(R.id.xutils_http_delete)
//	private void xutils_http_delete_click(View v) {
////		new SampleDeleteProtocol(getBaseActivity(),this);
//		xutils_http_result.setText("服务器不支持DELETE请求");
//	}


	@Override
	public void onHttpStart(LZHttpProtocolHandlerBase protocol) {
		super.onHttpStart(protocol);
	}

	@Override
	public void onHttpSuccess(LZHttpProtocolHandlerBase protocol) {
		super.onHttpSuccess(protocol);
		String json = "";
		if (protocol.getProtocolType() == ConfigProtocolType.SAMPLE_GET) {
			json = ((SampleGetProtocol) protocol).getJson();
		} else if (protocol.getProtocolType() == ConfigProtocolType.SAMPLE_POST) {
			json = ((SamplePostProtocol) protocol).getJson();
		}else if(protocol.getProtocolType() == ConfigProtocolType.SAMPLE_UPLOAD){
			json = ((SampleUploadProtocol) protocol).getJson();
		}
		xutils_http_result.setText(Html.fromHtml(json));

	}

	@Override
	public void onHttpFailure(Exception except, String msg) {
		super.onHttpFailure(except, msg);
	}

	@Override
	public void onHttpProgress(long total, long current, boolean isUploading) {
		super.onHttpProgress(total, current, isUploading);
	}
}

