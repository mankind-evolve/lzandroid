package com.lazy.android.basefunc.LZPay;

import android.content.Context;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.lazy.android.config.ConfigProtocolType;
import com.lazy.android.baseprotocol.LZHttpIProtocolCallback;
import com.lazy.android.baseprotocol.LZHttpProtocolHandlerBase;
import com.lazy.android.baseprotocol.LZHttpRequestInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenglei on 16/3/22.
 */
public class WxpayProtocol extends LZHttpProtocolHandlerBase {

	public PayReq req;

	public WxpayProtocol(Context context, LZHttpIProtocolCallback callBack, String orderid) {
		super(context, callBack);
		//		传订单id到网络请求地址，返回加密后的字符串，根据发起后的加密字符串吊起支付
		mSubUrl = "http://www.baidu.com/" + orderid;
		mProtocolType = ConfigProtocolType.PAY_WXPAY;
		LZHttpRequestInfo requestInfo = new LZHttpRequestInfo(mSubUrl, LZHttpRequestInfo.RequestType.GET);
		this.setmRequestInfo(requestInfo);
		this.sendRequest();
	}

	@Override
	public void afterParse() {
	}

	@Override
	public boolean parse() {
		try {
			JSONObject data = mResponeVO.getJSONObject("data");
			req = new PayReq();
			req.appId = data.getString("appid");
			req.partnerId = data.getString("partnerid");
			req.prepayId = data.getString("prepayid");
			req.packageValue = data.getString("package");
			req.nonceStr = data.getString("noncestr");
			req.timeStamp = data.getString("timestamp");
			req.sign = data.getString("sign");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}


}
