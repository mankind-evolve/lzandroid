package com.lazy.android.basefunc.LZPay;

import android.content.Context;
import android.widget.Toast;

import com.lazy.android.config.ConfigProtocolType;
import com.lazy.android.baseprotocol.LZHttpIProtocolCallback;
import com.lazy.android.baseprotocol.LZHttpProtocolHandlerBase;
import com.lazy.android.baseprotocol.LZHttpRequestInfo;

/**
 * Created by chenglei on 16/3/22.
 */
public class AlipayProtocol extends LZHttpProtocolHandlerBase {

	public String payInfo;


	public AlipayProtocol(Context context, LZHttpIProtocolCallback callBack, String orderid) {
		super(context, callBack);
//		传订单id到网络请求地址，返回加密后的字符串，根据发起后的加密字符串吊起支付
		mSubUrl = "http://www.baidu.com/" + orderid;
		mProtocolType = ConfigProtocolType.PAY_ALIPAY;
		LZHttpRequestInfo requestInfo = new LZHttpRequestInfo(mSubUrl, LZHttpRequestInfo.RequestType.GET);
		this.setmRequestInfo(requestInfo);
		this.sendRequest();

	}

	@Override
	public void afterParse() {
	}

	@Override
	public boolean parse() {
		payInfo = mJson;
		Toast.makeText(mContext, mJson, Toast.LENGTH_SHORT).show();
		return false;
	}


}
