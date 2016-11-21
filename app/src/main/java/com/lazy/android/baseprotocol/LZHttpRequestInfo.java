package com.lazy.android.baseprotocol;

import android.text.TextUtils;

import com.lazy.android.basefunc.error.ErrorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LZHttpRequestInfo {

	public static String isnull;

	public static enum RequestType {
		/**
		 * 带文件附件的http post
		 */
		UPLOAD,
		/**
		 * 下载文件
		 */
		DOWNLOAD,
		/**
		 * http get 方式获取数据
		 */
		GET,
		/**
		 * HTTP POST方式发送数据
		 */
		POST,
		/**
		 * 删除数据
		 */
		DELETE,
		/**
		 * 修改状态
		 */
		PUT
	}

	/**
	 * 缺省字符编码
	 */
	public final static String DEFAULT_CHARSET = "utf-8";
	/**
	 * 缺省content-type
	 */
	public final static String DEFAULT_CONTENTTYPE = "application/json";
	/**
	 * json数据field名称，用户带文件附件的请求
	 */
	public final static String JSON_FIELD = "JSON";
	/**
	 * 字符编码
	 */
	private String mCharset = DEFAULT_CHARSET;
	/**
	 * 请求的url地址
	 */
	private String mUrl;
	/**
	 * post请求谓词
	 */
	private HashMap<String,String> mQueryString;
	/**
	 * 结果回调
	 */
	private LZHttpIRequestCallback mRequestCallback = null;
	/**
	 * 要发送的http body数据包
	 */
	private String mBody;
	/**
	 * HTTP Status 结果状态码
	 */
	private int mHttpResultCode;
	/**
	 * Http form field 参数对
	 */
	private Map<String, String> mFormFieldParams;
	/**
	 * 要上传的文件列表
	 */
	private ArrayList<String> mUploadFilePathList;
	/**
     * req数据的content-type
     */
	private String mContentType = DEFAULT_CONTENTTYPE;
	/**
     * response数据的content-type
     */
	private String mResponseContentType = DEFAULT_CONTENTTYPE;
	/**
	 * 请求类型
	 */
	private RequestType mRequestType;
	/**
	 * response数据
	 */
	private String mRecieveData;
	/**
	 * 下载的文件保存的文件名
	 */
	private String mSavedFilepath;

	/**服务器返回的异常信息*/
	private Exception mException;

	public LZHttpRequestInfo(String url, RequestType requestType) {
		mUrl = url;
		mCharset = DEFAULT_CHARSET;
		mContentType = DEFAULT_CONTENTTYPE;
		mRecieveData = "";
		mSavedFilepath = "";
		mRequestType = requestType;
		mHttpResultCode = ErrorManager.NO_ERROR;
		mException = null;
	}

	public LZHttpRequestInfo(String url, RequestType requestType, String body) {
		this(url, requestType);
		mBody = body;
	}

	public void setUrl(String url) {
		this.mUrl = url;
	}

	public String getUrl() {
        if (!TextUtils.isEmpty(mUrl)) {
            mUrl = mUrl.trim();
        }
		return mUrl;
	}

	public void addQueryString(String key,String value){
		if(mQueryString == null ){
			mQueryString = new HashMap<String,String>();
		}
			mQueryString.put(key,value);
	}

	public HashMap<String, String> getmQueryString() {
		return mQueryString;
	}

	public void setmQueryString(HashMap<String, String> mQueryString) {
		this.mQueryString = mQueryString;
	}

	public void setCharset(String charset) {
		this.mCharset = charset;
	}

	public String getCharset() {
		return mCharset;
	}
	
	public void setContentType(String contentType) {
		this.mContentType = contentType;
	}

	public String getContentType() {
		return mContentType;
	}
	
	public void setResponseContentType(String contentType) {
		this.mResponseContentType = contentType;
	}

	public String getResponseContentType() {
		return mResponseContentType;
	}
	
	public void setRequestCallback(LZHttpIRequestCallback callback) {
		this.mRequestCallback = callback;
	}

	public LZHttpIRequestCallback getRequestCallback() {
		return mRequestCallback;
	}

	public void setBody(String jsonBody) {
		this.mBody = jsonBody;
	}

	public String getBody() {
		return mBody;
	}

	public void setHttpResultCode(int code) {
		this.mHttpResultCode = code;
	}

	public int getHttpResultCode() {
		return mHttpResultCode;
	}

	public boolean isSuccess() {
		return mHttpResultCode == ErrorManager.NO_ERROR;
	}

	public void setRecieveData(String recieveData) {
		this.mRecieveData = recieveData;
	}

	public String getRecieveData() {
		return mRecieveData == null ? "" : mRecieveData;
	}
	
	public void setSavedFilePath(String path) {
		this.mSavedFilepath = path;
	}

	public String getSavedFilePath() {
		return mSavedFilepath == null ? "" : mSavedFilepath;
	}
	
	public void setException(Exception e) {
		this.mException = e;
	}

	public Exception getException() {
		return mException;
	}

	public void addFormFieldParam(String fieldName, String fieldValue) {
		if (mFormFieldParams == null) {
			mFormFieldParams = new HashMap<String, String>();
		}
		mFormFieldParams.put(fieldName, fieldValue);
	}

	public void setFormFieldParams(Map<String, String> params) {
		this.mFormFieldParams = params;
	}

	public Map<String, String> getFormFieldParams() {
		return mFormFieldParams;
	}

	public void addUploadFilePath(String path) {
		if (mUploadFilePathList == null) {
			mUploadFilePathList = new ArrayList<String>();
		}
		mUploadFilePathList.add(path);
	}

	public void setUploadFilePath(ArrayList<String> path) {
		this.mUploadFilePathList = path;
	}

	public ArrayList<String> getUploadFilePath() {
		return mUploadFilePathList;
	}

	public void setRequestType(RequestType requestType) {
		this.mRequestType = requestType;
	}

	public RequestType getRequestType() {
		return mRequestType;
	}
}
