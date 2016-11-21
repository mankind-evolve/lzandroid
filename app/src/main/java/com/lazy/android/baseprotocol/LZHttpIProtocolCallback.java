package com.lazy.android.baseprotocol;

public interface LZHttpIProtocolCallback {
	/**
	 * @Title: onStart
	 * @Description: 发送http请求前回调
	 * @param protocol
	 * @return void
	 */
	public abstract void onHttpStart(LZHttpProtocolHandlerBase protocol);

	/**
	 * @Title: onSuccess
	 * @Description: 接收服务端http返回数据后回调
	 * @param protocol
	 * @return void
	 */
	public abstract void onHttpSuccess(LZHttpProtocolHandlerBase protocol);

	/**
	 * 
	 * @param total
	 * @param current
	 * @param isUploading
	 */
	public abstract void onHttpProgress(long total, long current,boolean isUploading);

	/**
	 * 
	 * @param except
	 * @param msg
	 */
	public abstract void onHttpFailure(Exception except, String msg);
}
