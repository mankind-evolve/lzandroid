package com.lazy.android.baseprotocol;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lazy.android.config.ConfigSystem;
import com.lazy.android.config.ConfigStaticType.ProtocolStatus;
import com.lazy.android.basefunc.error.ErrorManager;
import com.lazy.android.basefunc.LZLogger.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * 协议处理器基类
 *
 * @author gavin
 */
public abstract class LZHttpProtocolHandlerBase implements LZHttpIRequestCallback {
	protected static final String TAG = "LZHttpProtocolHandlerBase";
	// 请求服务器的根地址
	protected  static String mBaseUrl = ConfigSystem.SERVER_ROOT;
	//测试图片服务器字段
	protected  static String mImgUrl = ConfigSystem.UPLOAD_ROOT;
	//	返回请求内容详情的测试服地址
	protected final static String mTextUrl = "http://app.zhuli6.com/lzAndroid/httpreturn.php";
	//	返回请求内容详情的列表页面
	protected final static String mListUrl = "http://app.zhuli6.com/lzAndroid/netlist.php";


	//	项目一测试数据列表
	protected final static String mangUrl = "http://app.zhuli6.com/mangapi.php";
//	美昂API
	protected final static String mangAPI = "http://www.armyant.cc/";




	protected String mSubUrl = "";
	protected Context mContext = null;
	protected LZHttpRequestInfo mRequestInfo = null;
	protected LZHttpIProtocolCallback mProtocolCallback = null;
	/**
	 * 协议类型
	 */
	protected int mProtocolType;
	/**
	 * 服务器返回的json包
	 */
	protected String mJson;

	/**
	 * 协议处理状态
	 */
	protected int mProtocolStatus = ProtocolStatus.RESULT_SUCCESS;
	/**
	 * 网络请求状态码
	 */
	protected int mNetworkResultCode;
	protected JSONObject mResponeVO;
	protected CSResponseHeader mResponseHeader;
	protected String mProtocolErrorMessage;

	public LZHttpProtocolHandlerBase(Context context, LZHttpIProtocolCallback callBack) {
		mContext = context;
		mProtocolCallback = callBack;
		mNetworkResultCode = ErrorManager.NO_ERROR;

	}

	protected void sendRequest() {
		if (mRequestInfo != null) {
			mRequestInfo.setRequestCallback(this);
			LZHttpNetwork.getInstance(mContext).sendRequest(mRequestInfo);
		}
	}

	public LZHttpRequestInfo getmRequestInfo() {
		return mRequestInfo;
	}

	public void setmRequestInfo(LZHttpRequestInfo mRequestInfo) {
		this.mRequestInfo = mRequestInfo;
	}

	public int getProtocolType() {
		return mProtocolType;
	}

	public void setProtocolType(int protocolType) {
		this.mProtocolType = protocolType;
	}

	public int getNetworkResultCode() {
		return mNetworkResultCode;
	}

	public void setNetworkResultCode(int errorCode) {
		mNetworkResultCode = errorCode;
	}

	public Context getContext() {
		return mContext;
	}

	/**
	 * @return boolean
	 * @Title: isHttpSuccess
	 * @Description: 是否发生了网络错误
	 */
	public boolean isHttpSuccess() {
		return mRequestInfo == null ? false : mNetworkResultCode == ErrorManager.NO_ERROR;
	}

	/**
	 * @return int
	 * @Title: getProtocolStatus
	 * @Description: 协议处理状态
	 */
	public int getProtocolStatus() {
		return mProtocolStatus;
	}

	/**
	 * @param status
	 * @return void
	 * @Title: setProtocolStatus
	 * @Description: 设置协议处理状态
	 */
	public void setProtocolStatus(int status) {
		mProtocolStatus = status;
	}

	/**
	 * 协议状态
	 *
	 * @return
	 */
	public boolean isProtocolSuccess() {
		return isHttpSuccess() && (mProtocolStatus == ProtocolStatus.RESULT_SUCCESS || mProtocolStatus == ProtocolStatus.RESULT_SUCCESS_EMPTY);
	}

	/**
	 * @return String
	 * @Title: getProtocolErrorMessage
	 * @Description: 服务端处理协议后的结果描述
	 */
	public String getProtocolErrorMessage() {
		return mProtocolErrorMessage;
	}

	private void parseData(String result) {
		mJson = result;
		// 解析服务器返回的json数据包
		try {

//			Log信息过长不显示，打TOAST
			Logger.getInstance(TAG).debug("url:<" + this.mRequestInfo.getUrl() + ">数据包:<" + mJson + ">");
			Toast.makeText(mContext, "url:<" + this.mRequestInfo.getUrl() + ">数据包:<" + mJson + ">", Toast.LENGTH_SHORT).show();

			JSONTokener jsonParser = new JSONTokener(mJson);
			mResponeVO = (JSONObject) jsonParser.nextValue();
//			解析数据获得相应头
			mResponseHeader = CSResponseHeader.parse(mResponeVO);
			mProtocolStatus = mResponseHeader.getCode();
			mProtocolErrorMessage = mResponseHeader.getMessage();
			if(mProtocolStatus == ProtocolStatus.RESULT_ERROR_UNAUTH){
				Toast.makeText(mContext, "用户登录过期，请重新登录", Toast.LENGTH_SHORT).show();
//				mContext.startActivity(new Intent(mContext,m_login_login_Activity.class));
			}
		} catch (JSONException ex) {
			mProtocolStatus = ProtocolStatus.RESULT_ERROR;
		} catch (Exception ex) {
			ex.printStackTrace();
			mProtocolStatus = ProtocolStatus.RESULT_ERROR;
		}


		if(mProtocolStatus == ProtocolStatus.RESULT_SUCCESS){
			if (parse()) {
				afterParse();
			}
		}else{
			//TODO 请求不成功返回错误的信息
			Toast.makeText(mContext, mResponeVO.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 子类重写这个方法。在解析完数据后做些额外工作
	 *
	 * @return
	 */
	public abstract void afterParse();

	/**
	 * 子类重写这个方法，解析数据包
	 *
	 * @return
	 */
	public abstract boolean parse();

	/**
	 * @return boolean
	 * @Title: getBooleanValue
	 * @Description: 返回bool值的函数。需要返回bool值的协议重载这个函数
	 */
	public boolean getBooleanValue() {
		return false;
	}

	/**
	 * @return long
	 * @Title: getLongValue
	 * @Description: 返回整数值的函数。需要返回整数值的协议重载这个函数
	 */
	public long getLongValue() {
		return 0;
	}

	/**
	 * @return String
	 * @Title: getStringValue
	 * @Description: 返回String值的函数。需要返回String值的协议重载这个函数
	 */
	public String getStringValue() {
		return "";
	}

	@Override
	public void onHttpStart() {
		if (mProtocolCallback != null) {
			mProtocolCallback.onHttpStart(this);
		}
	}

	@Override
	public void onHttpSuccess(LZHttpRequestInfo info) {
		if (mProtocolCallback != null) {
			this.setmRequestInfo(info);
			if (info.getHttpResultCode() == ErrorManager.NO_ERROR) {
				parseData(info.getRecieveData());
			}
			mProtocolCallback.onHttpSuccess(this);
		}
	}

	@Override
	public void onHttpProgress(long total, long current, boolean isUploading) {
		if (mProtocolCallback != null) {
			mProtocolCallback.onHttpProgress(total, current, isUploading);
		}
	}

	@Override
	public void onHttpFailure(Exception except, String msg) {
		mNetworkResultCode = mRequestInfo.getHttpResultCode();
		if (mProtocolCallback != null) {
			mProtocolCallback.onHttpFailure(except, msg);
		}
	}


}
