package com.lazy.android.baseprotocol;

import android.content.Context;

import com.lazy.android.config.ConfigProtocolType;

/**
 * Created by chenglei on 16/3/22.
 */
public class LZTestProtocol extends LZHttpProtocolHandlerBase {


	public LZTestProtocol(Context context, LZHttpIProtocolCallback callBack) {
		super(context, callBack);
		mSubUrl = mTextUrl;
		mProtocolType = ConfigProtocolType.TEST;
		LZHttpRequestInfo requestInfo = new LZHttpRequestInfo(mSubUrl, LZHttpRequestInfo.RequestType.UPLOAD);
		this.setmRequestInfo(requestInfo);
		this.sendRequest();

	}

	@Override
	public void afterParse() {

	}

	@Override
	public boolean parse() {
		return false;
	}



}
