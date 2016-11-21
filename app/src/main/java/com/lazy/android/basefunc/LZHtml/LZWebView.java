package com.lazy.android.basefunc.LZHtml;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lazy.android.config.ConfigFilePath;
import com.lazy.android.config.ConfigStaticType;
import com.lazy.android.config.ConfigSystem;
import com.lazy.android.basefunc.LZUtils.UtilsShared;

/**
 * Created by chenglei on 16/3/17.
 */
@SuppressWarnings("deprecation")
public class LZWebView extends WebView {

	private ProgressBar progressbar;
	private Context context;
	private String loadurl;

	public LZWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 3, 0, 0));
//		addView(progressbar);
		setWebViewClient(new LZWebViewClient());
		setWebChromeClient(new WebChromeClient());
	}


	//	webview需要处理的Javascript的对话框、网站图标、网站title、加载进度等
	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}
	}


	//	帮助WebView处理各种通知、请求事件的
	public class LZWebViewClient extends WebViewClient {
		//		判断什么样的请求会交给webview来处理
		@Override
		public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//			Intent i=new Intent(context, CommonWebViewActivity.class);
//			context.startActivity(i);
			return null;
		}

		//	页面开始加载的时候回调
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
//		//判断字符串中是否含有特定字符串cutstring
//		String reg = ".*"+ cutString +".*";
//		if(url.matches(reg)){
//			String webviewURL = url.split(cutString)[0];
//			String title = URLDecoder.decode(url.split(cutString)[1]);
//
//			WebviewIntentData webviewIntentData = new WebviewIntentData(title,webviewURL);
//			Intent intent = new Intent(context,CommonWebViewActivity.class);
//			intent.putExtra("intentdata",webviewIntentData);
//			((Activity)context).startActivity(intent);
//		}else{
//		}
		}

		//	页面加载的时候回调
		@Override
		public void onLoadResource(WebView view, String url) {
			super.onLoadResource(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			view.stopLoading();
			view.clearView();
			String data = "Page NO FOUND！";
			//这里加上断网提示图片
			view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
			//Message msg=handler.obtainMessage();//发送通知，加入线程
			//msg.what=1;//通知加载自定义404页面
			//handler.sendMessage(msg);//通知发送！
		}
	}


	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}


	// webView与js交互代码
	public void showWebView(String url) {
		try {
//			设置是否获得焦点
			this.requestFocus();
//			WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
//			onCloseWindow(关闭WebView)
//			onCreateWindow()
//			onJsAlert (WebView上alert是弹不出来东西的，需要定制你的WebChromeClient处理弹出)
//			onJsPrompt
//			onJsConfirm
//			onProgressChanged
//    		onReceivedIcon
//			onReceivedTitle
//			this.setWebChromeClient(new android.webkit.WebChromeClient() {
//				@Override
//				public void onProgressChanged(WebView view, int progress) {
//					Toast.makeText(context, "Loading....", Toast.LENGTH_SHORT).show();
//					if (progress >= 80) {
//						Toast.makeText(context, "JsAndroid Test", Toast.LENGTH_SHORT).show();
//					}
//				}
//			});


			//点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
//			mWebView.goBack();   //后退
//			mWebView.goForward();//前进
//			mWebView.reload();  //刷新
			this.setOnKeyListener(new OnKeyListener() {        // webview can go back
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					//表示按返回键
					if (keyCode == KeyEvent.KEYCODE_BACK && LZWebView.this.canGoBack()) {
						LZWebView.this.goBack();
						return true;
					}
					return false;
				}
			});

//			webview设置
			WebSettings webSettings = this.getSettings();
//			设置支持javascript
			webSettings.setJavaScriptEnabled(true);
//			如果网页是是中文，设置网页编码为utf-8，
			webSettings.setDefaultTextEncodingName("utf-8");
//			支持视频播放
			webSettings.setPluginState(WebSettings.PluginState.ON);
//			开启缓存,设置缓存路径和缓存目录，用于支持html5离线显示
//			webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
			webSettings.setAppCacheEnabled(true);
			webSettings.setAppCachePath(ConfigFilePath.FILE_CACHE);
			webSettings.setAppCacheMaxSize(5 * 1024 * 1024);


//			开启localstorage
			webSettings.setDomStorageEnabled(true);
			webSettings.setAllowFileAccess(true);
//			webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
//			String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
//			webSettings.setAppCachePath(appCachePath);
//			webSettings.setAppCacheEnabled(true);
//			不保存状态
			this.setSaveEnabled(false);


//			把原生对象注入到页面中命名为jsObj对象
//			注： compileSdkVersion版本号要用最高
//			对象中的每个方法都必须要加  @JavascriptInterface
//			this.addJavascriptInterface(new CommonWebViewObject(context, this), "native");
//			this.addJavascriptInterface(getHtmlObject(), "toJsObj");

//			webview加载地址

			if (url.contains("?")) {
//				url += "&userid=" + UtilsShared.getInt(context, ConfigStaticType.SettingField.XB_UID, 0) + "&token=" + UtilsShared.getString(context, ConfigStaticType.SettingField.XB_TOKEN, "0");
			} else {
//				url += "?userid=" + UtilsShared.getInt(context, ConfigStaticType.SettingField.XB_UID, 0) + "&token=" + UtilsShared.getString(context, ConfigStaticType.SettingField.XB_TOKEN, "0");
			}

			String murl = ConfigSystem.SERVER_ROOT;
			if (url.contains("http")) {
				loadurl = url;
			} else {
				loadurl = murl + "app/" + url;
			}
			this.loadUrl(loadurl);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	////	刷新网页
	public void refresh() {
//		Toast.makeText(context, "LZweb refresh", Toast.LENGTH_SHORT).show();
//		loadurl = loadurl + "&" + System.currentTimeMillis();
//		Toast.makeText(context, loadurl, Toast.LENGTH_SHORT).show();
//		this.loadUrl(loadurl);
		this.reload();
	}


	private Object getHtmlObject() {

		Object insertObj = new Object() {

			@JavascriptInterface
			public String HtmlcallJava() {
				return "Html call Java";
			}

			@JavascriptInterface
			public String HtmlcallJava2(final String param) {
				return "Html call Java : " + param;
			}


			@JavascriptInterface
			public void JavacallHtml() {
				((Activity) context).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						LZWebView.this.loadUrl("javascript: showFromHtml()");
					}
				});
			}

			@JavascriptInterface
			public void JavacallHtml2() {
				((Activity) context).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						LZWebView.this.loadUrl("javascript: showFromHtml2('IT-homer blog')");
					}
				});
			}
		};

		return insertObj;
	}

}

