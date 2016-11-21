package com.lazy.android.baseprotocol;

/**
 * @ClassName: INetwork
 * @Description: 网络处理接口
 * @author liubing
 * 
 */
public interface LZHttpINetwork {
	/**
	 * @Title: sendGetRequest
	 * @Description: http请求处理
	 * @param info
	 *            void
	 */
	public abstract void sendRequest(LZHttpRequestInfo info);

	/**
	 * @Title: downloadFile
	 * @Description: 下载文件
	 * @param url
	 * @return byte[] 文件字节流
	 */
	public abstract byte[] downloadFile(LZHttpRequestInfo info);

	/**
	 * @Title: uploadFile
	 * @Description: 上传文件
	 * @return void
	 */
	public abstract void uploadFile(LZHttpRequestInfo info);

}
