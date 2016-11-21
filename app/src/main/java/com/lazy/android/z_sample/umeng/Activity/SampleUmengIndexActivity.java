package com.lazy.android.z_sample.umeng.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lazy.android.config.ConfigUmeng;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;
import com.lazy.android.baseui.crumbs.CrumbsUmengSharedActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.Map;

/**
 * Created by chenglei on 16/1/17.
 */

@ContentView(R.layout.sample_umeng_activity)
public class SampleUmengIndexActivity extends LZBaseActivity {

	private UMShareAPI mShareAPI;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();
//		umeng初始化配置
		ConfigUmeng.initUmeng();
//		授权  首先获取UMShareAPI
		mShareAPI = UMShareAPI.get(this);
	}

	//	微信登录
	@Event(R.id.umeng_loginwx)
	private void umeng_loginwx_Event(View view) {

		ToastShow("请配置APPKEY");
//		if (mShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN)) {
//			mShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN, umAuthListener);
//		} else {
//			ToastShow("您还未安装微信");
//		}


	}

	//	QQ登录
	@Event(R.id.umeng_loginqq)
	private void umeng_loginqq_Event(View view) {


		ToastShow("请配置APPKEY");
//		if (mShareAPI.isInstall(this, SHARE_MEDIA.QQ)) {
//			mShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umAuthListener);
//		} else {
//			ToastShow("您还未安装QQ");
//		}

	}

	//	微博登录
	@Event(R.id.umeng_loginweibo)
	private void umeng_loginweibo_Event(View view) {

		ToastShow("请配置APPKEY");
//		if (mShareAPI.isInstall(this, SHARE_MEDIA.SINA)) {
//			mShareAPI.doOauthVerify(this, SHARE_MEDIA.SINA, umAuthListener);
//		} else {
//			ToastShow("您还未安装新浪");
//		}
	}


	//	返回回调
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mShareAPI.onActivityResult(requestCode, resultCode, data);
	}

	//	第三方登录回调
	private UMAuthListener umAuthListener = new UMAuthListener() {
		@Override
		public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

			String result = platform.name();
			for (String key : data.keySet()) {
				result += ("&" + key + "=" + data.get(key));
			}

			ToastShow(result);
		}

		@Override
		public void onError(SHARE_MEDIA platform, int action, Throwable t) {
			ToastShow("failaaaa");
			Log.i("chenglei", "fail");
//			Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform, int action) {
			ToastShow("cancelaaaa");
			Log.i("chenglei", "cancel");
//			Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
		}
	};


	//	点击打开分享面板
	@Event(R.id.umeng_share)
	private void umeng_share_Event(View view) {
		startActivity(new Intent(this, CrumbsUmengSharedActivity.class));
	}


	//	统计账号登录
	@Event(R.id.umeng_stat_login)
	private void umeng_stat_login_Event(View view) {
		MobclickAgent.onProfileSignIn("WB", "userID");
	}


	//	账号登出统计
	@Event(R.id.umeng_stat_loginout)
	private void umeng_stat_loginout_Event(View view) {
		MobclickAgent.onProfileSignOff();
	}


	//	埋点统计
	@Event(R.id.umeng_stat_diy)
	private void umeng_stat_diy_Event(View view) {
		MobclickAgent.onEvent(this, "1");
	}


	//	crash统计
	@Event(R.id.umeng_stat_crash)
	private void umeng_stat_crash_Event() {

		textView.setText("测试crash");

	}


	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(true);
		mCommonHead.setMiddleTitle("umeng");
	}

}
