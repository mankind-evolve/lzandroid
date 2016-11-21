package com.lazy.android.baseprotocol;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import com.lazy.android.config.ConfigStaticType.ProtocolStatus;
import com.lazy.android.basefunc.LZLogger.Logger;

public class CSResponseHeader {
	private final static String TAG = "CSResponseHeader";
	/**
	 * 处理状态
	 */
	private int mCode = ProtocolStatus.RESULT_ERROR;
	/**
	 * 状态对应的文本信息
	 */
	private String mMessage = "";
	/**
	 * 时间戳
	 */
	private long mTimestamp = 0;

	/**
	 * @return status
	 */
	public int getCode() {
		return mCode;
	}

	/**
	 *
	 * 要设置的 status
	 */
	public void setCode(int code) {
		this.mCode = code;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return mMessage;
	}

	/**
	 * @param message
	 *            要设置的 message
	 */
	public void setMessage(String message) {
		this.mMessage = message;
	}

	/**
	 * @return mTimestamp
	 */
	public long getTimestamp() {
		return mTimestamp;
	}

	/**
	 * @param timestamp
	 *            要设置的 mTimestamp
	 */
	public void setTimestamp(long timestamp) {
		this.mTimestamp = timestamp;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("code:" + mCode + "\n");
		sb.append("message:" + mMessage + "\n");
		sb.append("timestamp:" + mTimestamp + "\n");
		return sb.toString();
	}

	/**
	 * @Title: parse
	 * @Description: response头
	 * @param jsonObject
	 * @return ZMResponseHeader
	 */
	public static CSResponseHeader parse(JSONObject jsonObject) {
		CSResponseHeader resp = new CSResponseHeader();
		try {
			resp.setCode(jsonObject.getInt("code"));
			if (!jsonObject.isNull("message")) {
				resp.setMessage(jsonObject.getString("message"));
			}
		} catch (JSONException e) {
			Logger.getInstance(TAG).debug(e.getMessage(), e);
			return null;
		}
		return resp;
	}
}
