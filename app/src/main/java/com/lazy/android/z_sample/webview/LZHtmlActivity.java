package com.lazy.android.z_sample.webview;


import android.os.Bundle;
import android.telephony.TelephonyManager;
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

/**
 * Created by Administrator on 2016/2/29.
 */
@ContentView(R.layout.lz_base_lzhttp_activity)
public class LZHtmlActivity extends LZBaseActivity {

	@ViewInject(R.id.lzhttp_activity_webview) private WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();
		showWebView();
	}

	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(true);
		mCommonHead.setMiddleTitle("原生APP和html交互");
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
			mWebView.loadUrl("http://app.zhuli6.com/lzAndroid/js.html");

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
			public void callJs(){
				TelephonyManager mTm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
				String imei = mTm.getDeviceId();
				String imsi = mTm.getSubscriberId();
				String mtype = android.os.Build.MODEL; // 手机型号
				String numer = mTm.getLine1Number(); // 手机号码，有的可得，有的不可得
				final String  mobileInfo = mtype ;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mWebView.loadUrl("javascript: callAlert('"+mobileInfo+"')");
					}
				});
			}

		};

		return insertObj;
	}

	}




//	html页面定义的方法
//	<script type="text/javascript" language="javascript">
//
//	function showHtmlcallJava(){
//		var str = window.jsObj.HtmlcallJava();
//		alert(str);
//	}
//
//	function showHtmlcallJava2(){
//		var str = window.jsObj.HtmlcallJava2("IT-homer blog");
//		alert(str);
//	}
//
//	function showFromHtml(){
//		document.getElementById("id_input").value = "Java call Html";
//	}
//
//	function showFromHtml2( param ){
//		document.getElementById("id_input2").value = "Java call Html : " + param;
//	}
//	</script>


//	html页面按钮的代码
//	<input type="button" value="HtmlcallJava" onclick="showHtmlcallJava()" />
//	<input type="button" value="HtmlcallJava2" onclick="showHtmlcallJava2()" />
//	<input type="button" value="JavacallHtml" onclick="window.jsObj.JavacallHtml()" />
//	<input type="button" value="JavacallHtml2" onclick="window.jsObj.JavacallHtml2()" />
