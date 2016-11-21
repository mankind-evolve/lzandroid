package com.lazy.android.z_sample.xutils.protocol;

import android.content.Context;

import com.lazy.android.basefunc.LZLogger.Logger;
import com.lazy.android.baseprotocol.LZHttpIProtocolCallback;
import com.lazy.android.baseprotocol.LZHttpProtocolHandlerBase;
import com.lazy.android.baseprotocol.LZHttpRequestInfo;
import com.lazy.android.config.ConfigProtocolType;
import com.lazy.android.config.ConfigStaticType;

/**
 * Created by chenglei on 16/1/22.
 */
public class SampleUploadProtocol extends LZHttpProtocolHandlerBase {
	public String json;

	public SampleUploadProtocol(Context context, LZHttpIProtocolCallback callBack, String img) {
		super(context, callBack);
		mSubUrl = mTextUrl;
		mProtocolType = ConfigProtocolType.SAMPLE_UPLOAD;
		mRequestInfo = new LZHttpRequestInfo(mSubUrl, LZHttpRequestInfo.RequestType.UPLOAD);
		mRequestInfo.addFormFieldParam("img",img);
		this.sendRequest();
	}

	@Override
	public void afterParse() {

	}


	@Override
	public boolean parse() {
		boolean result = false;
		try {
			if (mProtocolStatus == ConfigStaticType.ProtocolStatus.RESULT_SUCCESS) {
				if (!mResponeVO.isNull("data")) {
					json = mJson;
				}
			}
		} catch (Exception ex) {
			Logger.getInstance(TAG).debug("接收到的数据包:<" + mJson + ">", ex);
		}
		return result;
	}



	public String getJson() {
		return json;
	}
}
