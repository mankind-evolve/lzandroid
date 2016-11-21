package com.lazy.android.baseprotocol;

public interface LZHttpIRequestCallback {
	/**
	 * @Title: onStart
	 * @Description: 发送http请求前回调
	 * @param
	 * @return void
	 */
	public abstract void onHttpStart();

	/**
	 * @Title: onSuccess
	 * @Description: 接收服务端http返回数据后回调
	 * @param info
	 * @return void
	 */
	public abstract void onHttpSuccess(LZHttpRequestInfo info);

	/**
	 * 
	 * @Title onHttpProgress
	 * @Description 下载或者上传的进度百分百
	 * @param isUploading
	 */
	public abstract void onHttpProgress(long total, long current,boolean isUploading);

	/**
	 * 
	 * @Title onHttpfailure
	 * @Description 请求失败
	 */
	public abstract void onHttpFailure(Exception except, String msg);
}
