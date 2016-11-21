package com.lazy.android.z_sample.jpush;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by chenglei on 16/6/24.
 */
@ContentView(R.layout.lz_base_lzhttp_activity)
public class JpushIndexActivity extends LZBaseActivity {

	@ViewInject(R.id.lzhttp_activity_webview) private WebView mWebView;
	final String id = JPushInterface.getRegistrationID(JpushIndexActivity.this);
	final String name = "name" + id;
	final String tag = "tag" + id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();
//		设置推送的别名
		initname();
		showWebView();

	}

	//		设置推送的别名
	private void initname() {

		Set<String> tags = new HashSet<String>();
		tags.add(tag);
		JPushInterface.setAliasAndTags(this, name, tags, new TagAliasCallback() {
			@Override
			public void gotResult(int i, String s, Set<String> set) {
				if(i==0){
					ToastShow("设置成功，别名=" + name + ";Tag=" + "tag" + id);
				}else{
					ToastShow("设置失败,错误代码="+i);
				}
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
		mCommonHead.setMiddleTitle("极光推送");
	}



	// webView与js交互代码
	private void showWebView(){
		try {
//			设置是否获得焦点
			mWebView.requestFocus();
//			WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
//			onCloseWindow(关闭WebView)
//			onCreateWindow()
//			onJsAlert (WebView上alert是弹不出来东西的，需要定制你的WebChromeClient处理弹出)
//			onJsPrompt
//			onJsConfirm
//			onProgressChanged
//    		onReceivedIcon
//			onReceivedTitle
			mWebView.setWebChromeClient(new WebChromeClient(){
				@Override
				public void onProgressChanged(WebView view, int progress){

//					Toast.makeText(LZHtmlActivity.this, "Loading....", Toast.LENGTH_SHORT).show();
//					if(progress >= 80) {
//						Toast.makeText(LZHtmlActivity.this, "JsAndroid Test", Toast.LENGTH_SHORT).show();
//					}
				}
			});


			//点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
//			mWebView.goBack();   //后退
//			mWebView.goForward();//前进
//			mWebView.reload();  //刷新
			mWebView.setOnKeyListener(new View.OnKeyListener() {        // webview can go back
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					//表示按返回键
					if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
						mWebView.goBack();
						return true;
					}
					return false;
				}
			});

//			webview设置
			WebSettings webSettings = mWebView.getSettings();
//			设置支持javascript
			webSettings.setJavaScriptEnabled(true);

//			如果网页是是中文，设置网页编码为utf-8，
			webSettings.setDefaultTextEncodingName("utf-8");
//			不保存状态
			mWebView.setSaveEnabled(false);

//			把原生对象注入到页面中命名为jsObj对象
//			注： compileSdkVersion版本号要用最高
//			对象中的每个方法都必须要加  @JavascriptInterface
			mWebView.addJavascriptInterface(getHtmlObject(), "native");
//			webview加载地址
			mWebView.loadUrl("http://app.zhuli6.com/lzAndroid/jpush/jpush.html");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private Object getHtmlObject(){
		Object insertObj = new Object(){

			@JavascriptInterface
			public void callToast(String content){
				ToastShow(content);
			}

			@JavascriptInterface
			public void pushbyid(){
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mWebView.loadUrl("javascript: idpush('"+ id +"')");
					}
				});
			}

			@JavascriptInterface
			public void pushbyname(){
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mWebView.loadUrl("javascript: namepush('"+ name +"')");
					}
				});
			}

			@JavascriptInterface
			public void pushbytag(){
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mWebView.loadUrl("javascript: tagpush('"+ tag +"')");
					}
				});
			}




		};
		return insertObj;
	}
}
