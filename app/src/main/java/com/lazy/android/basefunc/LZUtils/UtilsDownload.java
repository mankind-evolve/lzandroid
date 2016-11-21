package com.lazy.android.basefunc.LZUtils;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lazy.android.R;
import com.lazy.android.basefunc.LZDownLoad.DownloadViewHolder;
import com.lazy.android.basefunc.LZInterface.CallbackInterFace;
import com.lazy.android.config.ConfigFilePath;
import com.lazy.android.basefunc.LZDownLoad.DownloadService;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.http.body.ProgressBody;
import org.xutils.x;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by chenglei on 16/4/13.
 */
public class UtilsDownload {

	//	绑定圆形的图片
	public static String bindHeadImage(String url) {
//		1、拆分出url后缀名
		String[] urlarr = url.split("\\/");
		String name = urlarr[urlarr.length - 1];
//		1、根据随机数生成文件名
//		String name = UUID.randomUUID()+"";
//		2、组成新的路径
		String local_url = ConfigFilePath.FILE_IMAGE + "/" + name;
//		3、检查新路径有没有文件存在，存在删除，不存在下载
//		File file = new File(local_url);
//		if(file.exists()){
////			file.delete();
//		}
//		4、下载完成返回新路径
		try {
			DownloadService.getDownloadManager().startDownload(
				url, name,
				local_url, true, false, null);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return local_url;
	}


	//	绑定圆形的图片
	public static String bindHeadImage(String url, DownloadViewHolder callback) {
//		1、拆分出url后缀名
		String[] urlarr = url.split("\\/");
		String name = urlarr[urlarr.length - 1];
//		1、根据随机数生成文件名
//		String name = UUID.randomUUID()+"";
//		2、组成新的路径
		String local_url = ConfigFilePath.FILE_IMAGE + "/" + name;
//		3、检查新路径有没有文件存在，存在删除，不存在下载
//		File file = new File(local_url);
//		if(file.exists()){
////			file.delete();
//		}
//		4、下载完成返回新路径
		try {
			DownloadService.getDownloadManager().startDownload(
				url, name,
				local_url, true, false, callback);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return local_url;
	}


	//	基于xutils的下载
	public static void xutilDownload(String url, String path, Callback.ProgressCallback<File> callback) {
		RequestParams requestParams = new RequestParams(url);
		requestParams.setSaveFilePath(path);
		x.http().get(requestParams, callback);
	}


	/**
	 * 带通知栏下载工具类
	 *
	 * @param url
	 * @param path
	 */
	public static void DownLoadWithNotification(String url, final String path, final Context context) {


		final NotificationManager mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setContentTitle("版本更新")
			.setContentText("正在下载...")
			.setContentInfo("0%")
			.setSmallIcon(R.drawable.logo);

		RequestParams params = new RequestParams(url);
		//设置断点续传
		params.setAutoResume(true);
		params.setSaveFilePath(path);

		x.http().get(params, new Callback.ProgressCallback<File>() {


			@Override
			public void onWaiting() {

			}

			@Override
			public void onStarted() {
				Toast.makeText(x.app(), "开始下载", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onLoading(long total, long current, boolean isDownloading) {
				BigDecimal b = new BigDecimal((float) current / (float) total);
				float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
				mBuilder.setProgress(100, (int) (f1 * 100), false);
				mBuilder.setContentInfo((int) (f1 * 100) + "%");
				mNotifyManager.notify(1, mBuilder.build());
			}

			@Override
			public void onSuccess(File result) {
				mBuilder.setContentText("正在下载...")
					// Removes the progress bar
					.setProgress(0, 0, false);
				mNotifyManager.notify(1, mBuilder.build());
				mNotifyManager.cancel(1);
				Toast.makeText(x.app(), "下载成功", Toast.LENGTH_LONG).show();
//				installApp(context, path);
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


	//	下载后自动安装
	public static void installApp(Context context, String filePath) {
		File _file = new File(filePath);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(_file),
			"application/vnd.android.package-archive");
		context.startActivity(intent);
	}



//	带下载进度的botton
	public static void DownLoadWithProgress(Context context, final ProgressBar bar, final TextView textview , String url, String path, final CallbackInterFace callback) {


		final Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
					case 1:
						bar.setProgress(msg.arg1);
						break;
				}
			};
		};
		bar.setVisibility(View.GONE);
		textview.setVisibility(View.VISIBLE);

		RequestParams params = new RequestParams(url);
		//设置断点续传
		params.setAutoResume(true);
		params.setSaveFilePath(path);
		x.http().get(params, new Callback.ProgressCallback<File>() {
			@Override
			public void onWaiting() {

			}
			@Override
			public void onStarted() {
				bar.setVisibility(View.VISIBLE);
				textview.setVisibility(View.GONE);
				Log.i("chenglei","onstart");
			}
			@Override
			public void onLoading(long total, long current, boolean isDownloading) {
				Message msg = new Message();
				msg.what = 1;
				BigDecimal b = new BigDecimal((float) current / (float) total);
				float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
				msg.arg1 = Integer.valueOf((int) (f1 * 100) + "");
				handler.sendMessage(msg);
			}

			@Override
			public void onSuccess(File result) {
				bar.setVisibility(View.GONE);
				textview.setVisibility(View.VISIBLE);
				textview.setText("重新下载");
				Log.i("chenglei","success");
				callback.OnSuccess();

			}


			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				callback.OnFailed();
			}

			@Override
			public void onCancelled(CancelledException cex) {

			}

			@Override
			public void onFinished() {

			}
		});


	}

}


