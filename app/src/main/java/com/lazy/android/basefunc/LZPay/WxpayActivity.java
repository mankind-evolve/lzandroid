package com.lazy.android.basefunc.LZPay;

import android.os.Bundle;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.lazy.android.config.ConfigStaticType;
import com.lazy.android.baseprotocol.LZHttpProtocolHandlerBase;
import com.lazy.android.basefunc.LZUtils.UtilsShared;
import com.lazy.android.baseui.base.LZBaseActivity;

/**
 * Created by chenglei on 16/5/4.
 */
public class WxpayActivity extends LZBaseActivity {



	/***
	 * 微信支付需要对订单信息进行签名加密，签名不能放在客户端，可以把订单信息信息发送给服务器，
	 * 服务器进行加密，返回加密后的订单信息，吊起支付
	 *
	 * 微信签名机制：
	 * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3
	 *
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String orderid = getIntent().getStringExtra("orderid");
//		UtilsShared.setString(this, ConfigStaticType.SettingField.XB_ORDERID, orderid);
		new WxpayProtocol(this,this,orderid);
	}


	@Override
	public void onHttpStart(LZHttpProtocolHandlerBase protocol) {
		super.onHttpStart(protocol);
	}

	@Override
	public void onHttpProgress(long total, long current, boolean isUploading) {
		super.onHttpProgress(total, current, isUploading);
	}

	@Override
	public void onHttpFailure(Exception except, String msg) {
		super.onHttpFailure(except, msg);
	}

	@Override
	public void onHttpSuccess(LZHttpProtocolHandlerBase protocol) {
		super.onHttpSuccess(protocol);
		PayReq data = ((WxpayProtocol)protocol).req;
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
		msgApi.registerApp(data.appId);
		msgApi.sendReq(data);
		this.finish();
	}
}
