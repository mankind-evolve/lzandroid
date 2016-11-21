package com.lazy.android.config;

public final class ConfigProtocolType {

//	获得http协议内容的请求
	public static final int TEST = 1000;

//	sample网络请求开始
	/**
	 * SAMPLE GET请求
	 */
	public static final int SAMPLE_GET = 0;
	/**
	 * SAMPLE UPLOAD请求
	 */
	public static final int SAMPLE_UPLOAD = SAMPLE_GET + 1;
	/**
	 *  SAMPLE POST请求
	 */
	public static final int SAMPLE_POST = SAMPLE_UPLOAD + 1;
	/**
	 *  SAMPLE LIST请求
	 */
	public static final int SAMPLE_LIST = SAMPLE_POST + 1;
	/**
	 *  SAMPLE DELETE请求
	 */
	public static final int SAMPLE_DELETE = SAMPLE_LIST + 1;
	/**
	 *  签名微信的订单
	 */
	public static final int WXPAY_GETSIGN = SAMPLE_DELETE + 1;
	/**
	 *  微信订单的预下
	 */
	public static final int WXPAY_ORDERINIT = WXPAY_GETSIGN + 1;







	/**
	 * 支付宝支付获取调起字符串
	 */
	public static final int PAY_ALIPAY = WXPAY_ORDERINIT + 1;

	/**
	 * 微信支付获取调起字符串
	 */
	public static final int PAY_WXPAY = PAY_ALIPAY + 1;


}
