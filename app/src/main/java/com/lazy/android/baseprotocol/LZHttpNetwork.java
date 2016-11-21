package com.lazy.android.baseprotocol;

import android.content.Context;
import android.util.Log;

import com.lazy.android.application.LzandroidApplication;
import com.lazy.android.config.ConfigStaticType;
import com.lazy.android.config.ConfigStaticType.CSHttpHeader;
import com.lazy.android.basefunc.LZUtils.UtilsShared;
import com.lazy.android.basefunc.error.ErrorManager;
import com.lazy.android.basefunc.LZUtils.UtilsNet;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author
 * @ClassName: HttpNetwork
 * @Description: http协议实现类
 */
public class LZHttpNetwork implements LZHttpINetwork {

	private final static String TAG = LZHttpNetwork.class.getSimpleName();
	/**
	 * 缓冲区大小
	 */
	private final static int BUFFERSIZE = 1024;
	/**
	 * 网络超时时间。毫秒
	 */
	private final static int TIMEOUT_CONNECTION = 90 * 1000;
	/**
	 * 最大重试次数
	 */
	protected static final int TRY_COUNT = 3;
	/**
	 * 本例单例对象
	 */
	private static LZHttpNetwork instance = null;
	/**
	 * 上下文对象
	 */
	private Context mContext;
	/**
	 * http请求头
	 */
	private RequestParams params;
	/**
	 * 默认content type
	 */
	private final static String CONTENT_TYPE = "CSContent-Type";

	public LZHttpNetwork() {
	}

	public LZHttpNetwork(Context context) {
		mContext = context;
	}


	public static LZHttpNetwork getInstance(Context context) {
		if (instance == null) {
			instance = new LZHttpNetwork(context);
		}
		return instance;
	}

	static {

	}

	/*
	 * (非 Javadoc) <p>Title: sendRequest</p> <p>Description: </p>
	 *
	 * @param info
	 */
	@Override
	public void sendRequest(LZHttpRequestInfo info) {
		if (!UtilsNet.isNetworkAvailable(mContext)) {
			info.setHttpResultCode(ErrorManager.NOT_NETWORK);
			if (info.getRequestCallback() != null) {
				info.getRequestCallback().onHttpFailure(new Exception(), ErrorManager.getErrorDescByCode(ErrorManager.NOT_NETWORK));
			}
			return;
		}
		LZHttpRequestInfo.RequestType requestType = info.getRequestType();

		params = new RequestParams(info.getUrl());
		params.addHeader("User-Agent", CSHttpHeader.UA_VALUE);
		params.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		params.addHeader("Accept-Charset", "GBK,GB2312,utf-8;q=0.7,*;q=0.7");
		params.addHeader("Accept", "*/*");
//		params.addHeader(CSHttpHeader.MOBILE_OS, ConfigSystem.PLATFORM);
//		params.addHeader(CSHttpHeader.MOBILE_OS_VERSION, UtilsSystemInfo.getSDKVersion());
//		params.addHeader(CSHttpHeader.APP_VERSION, LzandroidApplication.APP_VERSION);
		params.addHeader(CSHttpHeader.ACCESS_TOKEN, System.currentTimeMillis()/1000 + UtilsShared.getString(mContext, ConfigStaticType.SettingField.ACCESS_TOKEN,""));
		Log.i("token",System.currentTimeMillis()/1000 + "|" + UtilsShared.getString(mContext, ConfigStaticType.SettingField.ACCESS_TOKEN,""));
		String token = UtilsShared.getString(mContext, ConfigStaticType.SettingField.ACCESS_TOKEN,"");
//		token放到cookies里面
//		if(!token.equals("")){
//			params.addHeader(CSHttpHeader.COOKIE,"token=" + UtilsShared.getString(mContext, ConfigStaticType.SettingField.ACCESS_TOKEN,""));
//		}
//		params.addHeader(CSHttpHeader.USER_ID, UtilsShared.getInt(mContext, ConfigStaticType.SettingField.XB_UID,0)+"");
		params.setUseCookie(true);
//		params.addHeader(CSHttpHeader.REGIONID, LZConfig_System.getRegionId());

		info.setHttpResultCode(ErrorManager.NO_ERROR);
		switch (requestType) {
			case UPLOAD:
				doPostRequest(info,true);
				break;
			case DOWNLOAD:
				downloadFile(info);
				break;
			case GET:
				doGetRequest(info);
				break;
			case POST:
				doPostRequest(info,true);
				break;
			case DELETE:
				doDelRequest(info);
				break;
			case PUT:
				doPutRequest(info);
				break;
//		default:
//			doRequest(info);
//			break;
		}
	}

	//	发送put网络请求
	private void doPutRequest(LZHttpRequestInfo info) {

	}

	//	发送delete网络请求
	private void doDelRequest(LZHttpRequestInfo info) {

	}

	//	发送post网络请求和上传文件请求
	private void doPostRequest(final LZHttpRequestInfo info,Boolean asfile) {

		final LZHttpIRequestCallback callback = info.getRequestCallback();


//		组装请求谓词
		if(info.getmQueryString()!=null){
			Log.i("post","================= POST 参数列表 =================");
			HashMap map = info.getmQueryString();
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				if(entry.getValue().toString() != null){
					params.addBodyParameter(entry.getKey().toString(), entry.getValue().toString());
					Log.i("post",entry.getKey().toString() + "  -->  " + entry.getValue().toString());
				}
			}
		}



//		上传文件请求绑定文件
		if(asfile){
			params.setMultipart(true);
			if(info.getFormFieldParams() != null ){
				Log.i("post","================= 文件 参数列表 =================");
				HashMap<String,String> map = (HashMap) info.getFormFieldParams();
					for(Map.Entry<String,String> entry : map.entrySet()){
						if(!entry.getValue().equals("")){
							params.addBodyParameter(entry.getKey().toString(),new File(entry.getValue().toString()));
							Log.i("post",entry.getKey().toString() + "  -->  " + entry.getValue().toString());
						}
					}
			}
		}

		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				info.setRecieveData(result);
				info.setHttpResultCode(ErrorManager.NO_ERROR);
				if (callback != null) {
					callback.onHttpSuccess(info);
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if (ex instanceof org.xutils.ex.HttpException) { // 网络错误
					org.xutils.ex.HttpException httpEx = (org.xutils.ex.HttpException) ex;
					info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
					if (callback != null) {
						callback.onHttpFailure(httpEx, httpEx.getMessage());
					}
				} else { // 其他错误

				}
			}

			@Override
			public void onCancelled(CancelledException cex) {

			}

			@Override
			public void onFinished() {

			}
		});


	}


	//	发送get网络请求
	private void doGetRequest(final LZHttpRequestInfo info) {
		Log.i("get","url --> " + info.getUrl());
		final LZHttpIRequestCallback callback = info.getRequestCallback();
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				info.setRecieveData(result);
				if (callback != null) {
					callback.onHttpSuccess(info);
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if (ex instanceof org.xutils.ex.HttpException) { // 网络错误
					org.xutils.ex.HttpException httpEx = (org.xutils.ex.HttpException) ex;
					info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
					if (callback != null) {
						callback.onHttpFailure(httpEx, httpEx.getMessage());
					}
				} else { // 其他错误

				}
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
	 * @param info
	 * @return void
	 * @Title: uploadFile
	 * @Description: 下载文件附件的请求
	 */
	@Override
	public byte[] downloadFile(LZHttpRequestInfo info) {
		return new byte[0];
	}

	/**
	 * @return void
	 * @Title: uploadFile
	 * @Description: 上传文件
	 */
	@Override
	public void uploadFile(LZHttpRequestInfo info) {



	}


//	//	处理不同的http请求
//	@Override
//	public void doRequest(LZHttpRequestInfo info) {
//
//		RequestParams params = new RequestParams(info.getUrl());
//		Callback.Callable = x.http().request(HttpMethod., params, new Callback.CommonCallback<RequestParams>() {
//			@Override
//			public void onSuccess(RequestParams result) {
//
//			}
//
//			@Override
//			public void onError(Throwable ex, boolean isOnCallback) {
//
//			}
//
//			@Override
//			public void onCancelled(CancelledException cex) {
//
//			}
//
//			@Override
//			public void onFinished() {
//
//			}
//		});


//		final LZHttpRequestCallback callback = info.getRequestCallback();
//		HttpUtils http = new HttpUtils();
//        //设置当前请求的缓存时间
//        http.configCurrentHttpCacheExpiry(0);
//        //设置默认请求的缓存时间
//        http.configDefaultHttpCacheExpiry(0);
//        if (isDownFile) {
//			HttpHandler<File> handler = http.download(info.getUrl(), info.getSavedFilePath(), mHeaderParams, true, true,
//					new RequestCallBack<File>() {
//						@Override
//						public void onLoading(long total, long current, boolean isUploading) {
//							if (callback != null) {
//								callback.onHttpProgress(total, current, isUploading);
//							}
//						}
//
//						@Override
//						public void onSuccess(ResponseInfo<File> responseInfo) {
//							info.setSavedFilePath(responseInfo.result.getPath());
//							if (responseInfo.contentType != null) {
//								info.setResponseContentType(responseInfo.contentType.getValue().trim());
//							}
//							callback.onHttpSuccess(info);
//						}
//
//						@Override
//						public void onStart() {
//							callback.onHttpStart();
//						}
//
//						@Override
//						public void onFailure(HttpException except, String msg) {
//							info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
//							if (callback != null) {
//								callback.onHttpFailure(except, msg);
//							}
//						}
//					});
//	};


////
////	/**
////	 * @Title: uploadFile
////	 * @Description: 带文件附件的请求
////	 * @param info
////	 * @return void
////	 */
////	private void uploadFile(final LZHttpRequestInfo info) {
////		// 上传文件
////		if (info.getFormFieldParams() == null
////				&& info.getUploadFilePath() == null) {
////			info.setHttpResultCode(ErrorManager.DATAFORMAT_ERROR);
////			if (info.getRequestCallback() != null) {
////				info.getRequestCallback().onHttpFailure(new Exception(), ErrorManager.getErrorDescByCode(ErrorManager.DATAFORMAT_ERROR));
////			}
////			return;
////		}
////		RequestParams fileParams = new RequestParams();
////		List<BaseParams.Header> headers = mHeaderParams.getHeaders();
////		for(BaseParams.Header item : headers){
////			fileParams.addHeader(item.key,item.getValueStr());
////		}
////		MultipartEntity bodyEntity = new MultipartEntity();
////		// 添加文本部分
////		if (info.getFormFieldParams() != null
////				&& !info.getFormFieldParams().isEmpty()) {
////			Set<Map.Entry<String, String>> entrySet = info.getFormFieldParams().entrySet();
////			for (Map.Entry<String, String> entry : entrySet) {
////				try {
//////					StringBody jsonBody = new StringBody(entry.getKey(), entry.getValue(), Charset.forName(info.getCharset()));
////
////
////					ContentBody jsonBody = new ContentBody() {
////						@Override
////						public String getFilename() {
////							return null;
////						}
////
////						@Override
////						public void writeTo(OutputStream outputStream) throws IOException {
////
////						}
////
////						@Override
////						public String getMimeType() {
////							return null;
////						}
////
////						@Override
////						public String getMediaType() {
////							return null;
////						}
////
////						@Override
////						public String getSubType() {
////							return null;
////						}
////
////						@Override
////						public String getCharset() {
////							return null;
////						}
////
////						@Override
////						public String getTransferEncoding() {
////							return null;
////						}
////
////						@Override
////						public long getContentLength() {
////							return 0;
////						}
////					}
////					bodyEntity.addPart(entry.getKey(), jsonBody);
////				} catch (UnsupportedEncodingException e) {
////					Logger.getInstance(TAG).debug(e.getMessage(), e);
////					info.setHttpResultCode(ErrorManager.DATAFORMAT_ERROR);
////					if (info.getRequestCallback() != null) {
////						info.getRequestCallback().onHttpFailure(new Exception(), ErrorManager.getErrorDescByCode(ErrorManager.DATAFORMAT_ERROR));
////					}
////					return;
////				}
////			}
////		}
////		// 添加文件部分
////		if (info.getUploadFilePath() != null
////				&& !info.getUploadFilePath().isEmpty()) {
////			for (String filePath : info.getUploadFilePath()) {
////				String mime = FileHelper.getMIME(filePath);
////				FileBody fileBody = new FileBody(new File(filePath), mime, RequestInfo.DEFAULT_CHARSET);
////				String fileField = StringHelper.md5Hash(filePath);
////				bodyEntity.addPart(fileField, fileBody);
////			}
////		}
////		fileParams.setBodyEntity(bodyEntity);
////		HttpUtils http = new HttpUtils();
////		final LZHttpRequestCallback callback = info.getRequestCallback();
////
////
////		http.send(HttpMethod.POST, info.getUrl(), fileParams,
////				new RequestCallBack<String>() {
////					@Override
////					public void onLoading(long total, long current, boolean isUploading) {
////						if (callback != null) {
////							callback.onHttpProgress(total, current, isUploading);
////						}
////					}
////
////					@Override
////					public void onSuccess(ResponseInfo<String> responseInfo) {
////						info.setRecieveData(responseInfo.result);
////						if (callback != null) {
////							if (responseInfo.contentType != null) {
////								info.setResponseContentType(responseInfo.contentType.getValue().trim());
////							}
////							callback.onHttpSuccess(info);
////						}
////					}
////
////					@Override
////					public void onStart() {
////						if (callback != null) {
////							callback.onHttpStart();
////						}
////					}
////
////					@Override
////					public void onFailure(HttpException error, String msg) {
////						info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
////						if (callback != null) {
////							callback.onHttpFailure(error, msg);
////						}
////					}
////				});
////	}
////
////	/**
////	 * @Title: sendPostRequest
////	 * @Description: HTTP POST方式向服务器发送数据
////	 * @param info
////	 * @return void
////	 */
////	private void sendPostRequest(final LZHttpRequestInfo info) {
////		doPostPutRequest(info, HttpMethod.POST);
////	}
////
////	/**
////	 * @Title: sendPutRequest
////	 * @Description: http put 操作
////	 * @param info
////	 * @return void
////	 */
////	private void sendPutRequest(LZHttpRequestInfo info) {
////		doPostPutRequest(info, HttpMethod.PUT);
////	}
////
////	/**
////	 * @Title: sendGetRequest
////	 * @Description: HTTP GET方式向服务器请求数据
////	 * @param info
////	 * @return void
////	 */
////	private void sendGetRequest(LZHttpRequestInfo info) {
////		doGetRequest(info, false);
////	}
////	/**
////	 * @Title: downloadFile
////	 * @Description: 下载文件
////	 * @param info
////	 * @return void
////	 */
////	private void doDownloadFile(LZHttpRequestInfo info) {
////		doGetRequest(info, true);
////	}
////	/**
////	 * @Title: sendDeleteRequest
////	 * @Description: http delete 操作
////	 * @param info
////	 * @return void
////	 */
////	private void sendDeleteRequest(final LZHttpRequestInfo info) {
////		RequestParams fileParams = new RequestParams();
////		List<HeaderItem> headers = mHeaderParams.getHeaders();
////		for (HeaderItem item : headers) {
////			fileParams.addHeader(item.header);
////		}
////		RequestParams jsonParams = new RequestParams();
////		for (HeaderItem item : headers) {
////			jsonParams.addHeader(item.header);
////		}
////		jsonParams.setHeader("Content-Type", RequestInfo.DEFAULT_CONTENTTYPE
////				+ ";charset=UTF-8");
////		jsonParams.setHeader("Accept", RequestInfo.DEFAULT_CONTENTTYPE);
////		final LZHttpRequestCallback callback = info.getRequestCallback();
////		HttpUtils http = new HttpUtils();
////		http.send(HttpMethod.DELETE, info.getUrl(), jsonParams,
////				new RequestCallBack<String>() {
////					@Override
////					public void onLoading(long total, long current, boolean isUploading) {
////						if (callback != null) {
////							callback.onHttpProgress(total, current, isUploading);
////						}
////					}
////
////					@Override
////					public void onSuccess(ResponseInfo<String> responseInfo) {
////						info.setRecieveData(responseInfo.result);
////						if (callback != null) {
////							if (responseInfo.contentType != null) {
////								info.setResponseContentType(responseInfo.contentType.getValue().trim());
////							}
////							callback.onHttpSuccess(info);
////						}
////					}
////
////					@Override
////					public void onStart() {
////						if (callback != null) {
////							callback.onHttpStart();
////						}
////					}
////
////					@Override
////					public void onFailure(HttpException except, String msg) {
////						info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
////						if (callback != null) {
////							callback.onHttpFailure(except, msg);
////						}
////					}
////				});
////	}
////
////	private void doPostPutRequest(final LZHttpRequestInfo info, HttpMethod method) {
////		List<HeaderItem> headers = mHeaderParams.getHeaders();
////		RequestParams jsonParams = new RequestParams();
////		for (HeaderItem item : headers) {
////            jsonParams.addHeader(item.header);
////		}
////		final LZHttpRequestCallback callback = info.getRequestCallback();
////		HttpUtils http = new HttpUtils();
////        //设置当前请求的缓存时间
////        http.configCurrentHttpCacheExpiry(0);
////        //设置默认请求的缓存时间
////        http.configDefaultHttpCacheExpiry(0);
////
////		// 带文件附件
////		if (info.getUploadFilePath() != null
////				&& !info.getUploadFilePath().isEmpty()) {
////			MultipartEntity bodyEntity = new MultipartEntity();
////			// 添加文本部分
////			if (info.getFormFieldParams() != null
////					&& !info.getFormFieldParams().isEmpty()) {
////				Set<Map.Entry<String, String>> entrySet = info.getFormFieldParams().entrySet();
////				for (Map.Entry<String, String> entry : entrySet) {
////					try {
////						StringBody jsonBody = new StringBody(entry.getKey(), entry.getValue(), Charset.forName(info.getCharset()));
////						bodyEntity.addPart(entry.getKey(), jsonBody);
////					} catch (UnsupportedEncodingException e) {
////						Logger.getInstance(TAG).debug(e.getMessage(), e);
////						info.setHttpResultCode(ErrorManager.DATAFORMAT_ERROR);
////						if (info.getRequestCallback() != null) {
////							info.getRequestCallback().onHttpFailure(new Exception(), ErrorManager.getErrorDescByCode(ErrorManager.DATAFORMAT_ERROR));
////						}
////						return;
////					}
////				}
////			}
////			// 添加文件
////			for (String filePath : info.getUploadFilePath()) {
////                String mime = FileHelper.getMIME(filePath);
////				FileBody fileBody = new FileBody(new File(filePath), mime, RequestInfo.DEFAULT_CHARSET);
////                String fileField = StringHelper.md5Hash(filePath);
//////                bodyEntity.addPart(fileField, fileBody);    //原来的代码
////                bodyEntity.addPart("file", fileBody);
////			}
////			jsonParams.setBodyEntity(bodyEntity);
////		} else {
////			// 不带文件附件
////			jsonParams.setHeader("Content-Type", RequestInfo.DEFAULT_CONTENTTYPE);
////
////			try {
////				if(!TextUtils.isEmpty(info.getBody())){
////					StringEntity entity = new StringEntity(info.getBody(), info.getCharset());
////					entity.setContentType("application/json");
////					jsonParams.setBodyEntity(entity);
////				}
////			} catch (UnsupportedEncodingException e) {
////				Logger.getInstance(TAG).debug(e.getMessage(), e);
////				info.setHttpResultCode(ErrorManager.DATAFORMAT_ERROR);
////				if (info.getRequestCallback() != null) {
////					info.getRequestCallback().onHttpFailure(new Exception(), ErrorManager.getErrorDescByCode(ErrorManager.DATAFORMAT_ERROR));
////				}
////				return;
////			}
////		}
////
////		http.send(method, info.getUrl(), jsonParams,
////				new RequestCallBack<String>() {
////					@Override
////					public void onLoading(long total, long current, boolean isUploading) {
////						if (callback != null) {
////							callback.onHttpProgress(total, current, isUploading);
////						}
////					}
////
////					@Override
////					public void onSuccess(ResponseInfo<String> responseInfo) {
////						info.setRecieveData(responseInfo.result);
////						if (callback != null) {
////							if (responseInfo.contentType != null) {
////								info.setResponseContentType(responseInfo.contentType.getValue().trim());
////							}
////							callback.onHttpSuccess(info);
////						}
////					}
////
////					@Override
////					public void onStart() {
////						if (callback != null) {
////							callback.onHttpStart();
////						}
////					}
////
////					@Override
////					public void onFailure(HttpException error, String msg) {
////						info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
////						if (callback != null) {
////							callback.onHttpFailure(error, msg);
////						}
////					}
////				});
////	}
////
////	private void doGetRequest(final LZHttpRequestInfo info, boolean isDownFile) {
////		final LZHttpRequestCallback callback = info.getRequestCallback();
////		HttpUtils http = new HttpUtils();
////        //设置当前请求的缓存时间
////        http.configCurrentHttpCacheExpiry(0);
////        //设置默认请求的缓存时间
////        http.configDefaultHttpCacheExpiry(0);
////        if (isDownFile) {
////			HttpHandler<File> handler = http.download(info.getUrl(), info.getSavedFilePath(), mHeaderParams, true, true,
////					new RequestCallBack<File>() {
////						@Override
////						public void onLoading(long total, long current, boolean isUploading) {
////							if (callback != null) {
////								callback.onHttpProgress(total, current, isUploading);
////							}
////						}
////
////						@Override
////						public void onSuccess(ResponseInfo<File> responseInfo) {
////							info.setSavedFilePath(responseInfo.result.getPath());
////							if (responseInfo.contentType != null) {
////								info.setResponseContentType(responseInfo.contentType.getValue().trim());
////							}
////							callback.onHttpSuccess(info);
////						}
////
////						@Override
////						public void onStart() {
////							callback.onHttpStart();
////						}
////
////						@Override
////						public void onFailure(HttpException except, String msg) {
////							info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
////							if (callback != null) {
////								callback.onHttpFailure(except, msg);
////							}
////						}
////					});
////		} else {
////			RequestParams jsonParams = new RequestParams();
////			List<HeaderItem> headers = mHeaderParams.getHeaders();
////			for (HeaderItem item : headers) {
////                jsonParams.addHeader(item.header);
////			}
////			jsonParams.setHeader("Content-Type", RequestInfo.DEFAULT_CONTENTTYPE + ";charset=UTF-8");
////
////			http.send(HttpMethod.GET, info.getUrl(), jsonParams,
////					new RequestCallBack<String>() {
////						@Override
////						public void onLoading(long total, long current, boolean isUploading) {
////							if (callback != null) {
////								callback.onHttpProgress(total, current, isUploading);
////							}
////						}
////
////						@Override
////						public void onSuccess(ResponseInfo<String> responseInfo) {
////							info.setRecieveData(responseInfo.result);
////							if (callback != null) {
////								if (responseInfo.contentType != null) {
////									info.setResponseContentType(responseInfo.contentType.getValue().trim());
////								}
////								callback.onHttpSuccess(info);
////							}
////						}
////
////						@Override
////						public void onStart() {
////							if (callback != null) {
////								callback.onHttpStart();
////							}
////						}
////
////						@Override
////						public void onFailure(HttpException except, String msg) {
////							info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
////							if (callback != null) {
////								callback.onHttpFailure(except, msg);
////							}
////						}
////					});
////		}
////	}
////
//
//
//	@Override
//	public byte[] downloadFile(LZHttpRequestInfo info) {
//		DefaultHttpClient httpClient = getHttpClient();
//		if (httpClient == null) {
//			info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
//			if (info.getRequestCallback() != null) {
//				info.getRequestCallback().onHttpFailure(new Exception(), ErrorManager.getErrorDescByCode(ErrorManager.NETWORK_FAILURE));
//			}
//			return null;
//		} else {
//			httpClient.setHttpRequestRetryHandler(requestRetryHandler);
//			httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
//					info.getCharset() == null ? RequestInfo.DEFAULT_CHARSET : info.getCharset());
//		}
//		HttpGet httpGet = null;
//		try {
//			httpGet = new HttpGet(info.getUrl());
//		} catch (IllegalArgumentException e) {
//			info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
//			if (info.getRequestCallback() != null) {
//				info.getRequestCallback().onHttpFailure(new Exception(), ErrorManager.getErrorDescByCode(ErrorManager.NETWORK_FAILURE));
//			}
//			Logger.getInstance(TAG).debug(info.getUrl(), e);
//			return null;
//		}
//        mHeaderParams = new RequestParams();
//        mHeaderParams.addHeader("User-Agent", CSHttpHeader.UA_VALUE);
//        mHeaderParams.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
//        mHeaderParams.addHeader("Accept-Charset", "GBK,GB2312,utf-8;q=0.7,*;q=0.7");
//        mHeaderParams.addHeader("Accept", "*/*");
//        mHeaderParams.addHeader(CSHttpHeader.MOBILE_OS, LZConfig_System.PLATFORM);
//        mHeaderParams.addHeader(CSHttpHeader.MOBILE_OS_VERSION,SystemInfo.getSDKVersion());
//        mHeaderParams.addHeader(CSHttpHeader.APP_VERSION, BaicaijiaApplication.APP_VERSION);
//        mHeaderParams.addHeader(CSHttpHeader.USER_ID,LZConfig_System.getUserId());
//        mHeaderParams.addHeader(CSHttpHeader.REGIONID,LZConfig_System.getRegionId());
//		List<HeaderItem> headerItems = mHeaderParams.getHeaders();
//		for (HeaderItem item : headerItems) {
//            httpGet.addHeader(item.header.getName(), item.header.getValue());
//		}
//		byte[] buffer = null;
//		try {
//			HttpResponse httpResponse = httpClient.execute(httpGet);
//			int responseCode = httpResponse.getStatusLine().getStatusCode();
//			if (responseCode == HttpStatus.SC_OK) {
//				HttpEntity httpEntity = httpResponse.getEntity();
//				buffer = EntityUtils.toByteArray(httpEntity);
//				Header[] headers = httpResponse.getAllHeaders();
//				if (headers != null && headers.length > 0) {
//					for (Header h : headers) {
//						if (CONTENT_TYPE.equalsIgnoreCase(h.getName())) {
//							info.setResponseContentType(h.getValue().trim());
//							break;
//						}
//					}
//				}
//				info.setHttpResultCode(ErrorManager.NO_ERROR);
//			} else {
//				Logger.getInstance(TAG).debug("resp url<" + info.getUrl() + "> http status:<" + responseCode + "> data<" + info.getBody() + ">");
//				info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
//				HttpEntity httpEntity = httpResponse.getEntity();
//				String str = EntityUtils.toString(httpEntity, info.getCharset());
//				info.setRecieveData(str);
//				if (info.getRequestCallback() != null) {
//					info.getRequestCallback().onHttpFailure(new Exception(), ErrorManager.getErrorDescByCode(ErrorManager.NETWORK_FAILURE));
//				}
//			}
//		} catch (ClientProtocolException e) {
//			info.setHttpResultCode(ErrorManager.PROTOCOL_ERROR);
//			if (info.getRequestCallback() != null) {
//				info.getRequestCallback().onHttpFailure(e, ErrorManager.getErrorDescByCode(ErrorManager.PROTOCOL_ERROR));
//			}
//			Logger.getInstance(TAG).debug(e.getMessage(), e);
//		} catch (FileNotFoundException e) {
//			info.setHttpResultCode(ErrorManager.FILENOTEXISTS_ERROR);
//			if (info.getRequestCallback() != null) {
//				info.getRequestCallback().onHttpFailure(e, ErrorManager.getErrorDescByCode(ErrorManager.FILENOTEXISTS_ERROR));
//			}
//			Logger.getInstance(TAG).debug(e.getMessage(), e);
//		} catch (IOException e) {
//			info.setHttpResultCode(ErrorManager.IO_ERROR);
//			if (info.getRequestCallback() != null) {
//				info.getRequestCallback().onHttpFailure(e, ErrorManager.getErrorDescByCode(ErrorManager.IO_ERROR));
//			}
//			Logger.getInstance(TAG).debug(e.getMessage(), e);
//		} catch (Exception e) {
//			info.setHttpResultCode(ErrorManager.NETWORK_FAILURE);
//			if (info.getRequestCallback() != null) {
//				info.getRequestCallback().onHttpFailure(e, ErrorManager.getErrorDescByCode(ErrorManager.NETWORK_FAILURE));
//			}
//			Logger.getInstance(TAG).debug(e.getMessage(), e);
//		} finally {
//			abortRequest(httpGet);
//			shutdown(httpClient);
//		}
//		return buffer;
//	}
//
//	/**
//	 * 异常自动恢复处理 使用HttpRequestRetryHandler接口实现请求的异常恢复
//	 */
//	private HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
//		// 自定义的恢复策略
//		@Override
//		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
//			// 设置恢复策略，在发生异常时候将自动重试
//			if (executionCount > TRY_COUNT) {
//				// 超过最大次数则不需要重试
//				return false;
//			}
//			if (exception instanceof NoHttpResponseException) {
//				// 服务停掉则重新尝试连接
//				return true;
//			}
//			if (exception instanceof SSLHandshakeException) {
//				// SSL异常不需要重试
//				return false;
//			}
//			HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
//			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
//			if (!idempotent) {
//				// 请求内容相同则重试
//				return true;
//			}
//			return false;
//		}
//	};
//
//	private DefaultHttpClient getHttpClient() {
//		HttpParams httpParams = new BasicHttpParams();
//		// 代理设置
//		HttpHost proxyHost = LZHttpProxySettings.getProxyHost();
//		if (proxyHost != null) {
//			httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxyHost);
//		}
//		// 设置http头信息
//		httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//		httpParams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT_CONNECTION);
//		httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, TIMEOUT_CONNECTION * 2);
//
//		HttpProtocolParams.setUseExpectContinue(httpParams, true);
//		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_CONNECTION);
//		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_CONNECTION);
//
//		// 设置我们的HttpClient支持HTTP和HTTPS两种模式
//		SchemeRegistry schReg = new SchemeRegistry();
//		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//		// ssl
//		SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
//		sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//		schReg.register(new Scheme("https", sslSocketFactory, 443));
//		// 使用线程安全的连接管理来创建HttpClient
//		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(httpParams, schReg);
//		return new DefaultHttpClient(conMgr, httpParams);
//	}
//
//	//
//	private void abortRequest(final HttpRequestBase request) {
//		if (request != null) {
//			request.abort();
//		}
//	}
//
//	/**
//	 * @param httpclient
//	 * @return void
//	 * @Title: shutdown
//	 * @Description: 关闭http连接
//	 */
//	private void shutdown(final HttpClient httpclient) {
//		if (httpclient != null) {
//			httpclient.getConnectionManager().shutdown();
//		}
//	}
}
