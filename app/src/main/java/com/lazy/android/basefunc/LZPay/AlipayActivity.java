package com.lazy.android.basefunc.LZPay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lazy.android.basefunc.LZPay.include.PayResult;
import com.lazy.android.config.ConfigProtocolType;
import com.lazy.android.config.ConfigStaticType;
import com.lazy.android.config.ConfigSystem;
import com.lazy.android.baseprotocol.LZHttpProtocolHandlerBase;
import com.lazy.android.basefunc.LZUtils.UtilsShared;
import com.lazy.android.baseui.base.LZBaseActivity;

/**
 * Created by chenglei on 16/5/4.
 */
public class AlipayActivity extends LZBaseActivity {
	private static final int SDK_PAY_FLAG = 1;

	private String orderid;


	/***
	 * 支付宝支付需要对订单信息进行签名加密，签名不能放在客户端，可以把订单id发送给服务器，
	 * 服务器进行订单信息加密，返回加密后的订单信息，吊起支付
	 *
	 * 支付宝签名机制：
	 * https://doc.open.alipay.com/doc2/detail?treeId=59&articleId=103927&docType=1
	 *
	 */

	//	支付回调
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					PayResult payResult = new PayResult((String) msg.obj);
					/**
					 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
					 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
					 * docType=1) 建议商户依赖异步通知
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						UtilsShared.setBoolean(AlipayActivity.this, ConfigStaticType.SettingField.ISPAYSUCCESS,true);
						String title = "支付结果";
						String url = ConfigSystem.SERVER_ROOT + "app/paysuccess.html?ordersn=" + orderid;
						String refresh = "true";
//						WebviewIntentData webviewIntentData = new WebviewIntentData(title,url,refresh);
//						Intent intent = new Intent(AlipayActivity.this,CommonWebViewActivity.class);
//						intent.putExtra("intentdata",webviewIntentData);
//						startActivity(intent);
					} else {
						// 判断resultStatus 为非"9000"则代表可能支付失败
						// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(AlipayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
						}else{
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							Toast.makeText(AlipayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
						}
					}
					AlipayActivity.this.finish();
					break;
				}
				default:
					break;
			}
		};
	};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		orderid = getIntent().getStringExtra("orderid");
//		UtilsShared.setString(this, ConfigStaticType.SettingField.XB_ORDERID, orderid);
		new AlipayProtocol(this,this,orderid);

	}

//	调起ali支付
	private void callAlipay(final String payInfo) {

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
//		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
//		Toast.makeText(AlipayActivity.this, "payInfo=" + payInfo, Toast.LENGTH_SHORT).show();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(AlipayActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);
				System.out.println("result="+result);
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
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
		if(protocol.getProtocolType() == ConfigProtocolType.PAY_ALIPAY){
				AlipayProtocol alipayProtocol = (AlipayProtocol) protocol;
				callAlipay(alipayProtocol.payInfo);
			}
	}
}
