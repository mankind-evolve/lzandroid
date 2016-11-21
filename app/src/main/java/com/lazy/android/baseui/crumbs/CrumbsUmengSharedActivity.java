package com.lazy.android.baseui.crumbs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.lazy.android.config.ConfigUmeng;
import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by chenglei on 16/4/14.
 */
@ContentView(R.layout.lz_crumbs_umengshared_activity)
public class CrumbsUmengSharedActivity extends LZBaseActivity {
	private String title;
	private String content;
	private String img;
	private String url;
	@ViewInject(R.id.crumbs_takephoto_layout)
	RelativeLayout crumbs_takephoto_layout;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		crumbs_takephoto_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});

//		解决左右两边有空隙
		getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
		title = getIntent().getStringExtra("title");
		content = getIntent().getStringExtra("content");
		img = getIntent().getStringExtra("img");
		url = getIntent().getStringExtra("url");
//		友盟初始化
		ConfigUmeng.initUmeng();
	}

	//	分享到微信朋友圈
	@Event(R.id.umengshare_wxpeng)
	private void umengshare_wxpeng_Event(View v){

		ToastShow("请配置APPKEY");
//		new ShareAction(this)
//			.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
//			.setCallback(umShareListener)
//			.withText(title)
//			.withTargetUrl(url)
//			.withMedia(new UMImage(CrumbsUmengSharedActivity.this,img))
//			.share();
	}

	//	分享到微信
	@Event(R.id.umengshare_wx)
	private void umengshare_wx_Event(View v){

		ToastShow("请配置APPKEY");
//		new ShareAction(this)
//			.setPlatform(SHARE_MEDIA.WEIXIN)
//			.setCallback(umShareListener)
//			.withText(title)
//			.withTargetUrl(url)
//			.withMedia(new UMImage(CrumbsUmengSharedActivity.this,img))
//			.share();
	}

	//	分享到QQ
	@Event(R.id.umengshare_qq)
	private void umengshare_qq_Event(View v){
		ToastShow("请配置APPKEY");
//		new ShareAction(this)
//			.setPlatform(SHARE_MEDIA.QQ)
//			.setCallback(umShareListener)
//			.withText(title)
//			.withTargetUrl(url)
//			.withMedia(new UMImage(CrumbsUmengSharedActivity.this,img))
//			.share();
	}

	//	分享到QZONE
	@Event(R.id.umengshare_qzone)
	private void umengshare_qzone_Event(View v){
		ToastShow("请配置APPKEY");
//		new ShareAction(this)
//			.setPlatform(SHARE_MEDIA.QZONE)
//			.setCallback(umShareListener)
//			.withText(title)
//			.withTargetUrl(url)
//			.withMedia(new UMImage(CrumbsUmengSharedActivity.this,img))
//			.share();
	}

	//	分享到微博
	@Event(R.id.umengshare_weibo)
	private void umengshare_weibo_Event(View v){
		ToastShow("请配置APPKEY");
//		new ShareAction(this)
//			.setPlatform(SHARE_MEDIA.SINA)
//			.setCallback(umShareListener)
//			.withText(title)
//			.withTargetUrl(url)
//			.withMedia(new UMImage(CrumbsUmengSharedActivity.this,img))
//			.share();
	}


	private UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {
			Toast.makeText(CrumbsUmengSharedActivity.this,platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
			CrumbsUmengSharedActivity.this.finish();
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(CrumbsUmengSharedActivity.this,platform + " 分享失败啦" + t.getMessage() + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(CrumbsUmengSharedActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
		}
	};


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		UMShareAPI.get( this ).onActivityResult( requestCode, resultCode, data);
	}



}




