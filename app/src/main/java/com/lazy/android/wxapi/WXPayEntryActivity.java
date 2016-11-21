package com.lazy.android.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.lazy.android.config.ConfigStaticType;
import com.lazy.android.config.ConfigSystem;
import com.lazy.android.basefunc.LZUtils.UtilsShared;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, ConfigSystem.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {


//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("R.string.app_tip");
//			builder.setMessage(String.valueOf(resp.errCode));
//			builder.show();
//		}

		if(resp.errCode == 0 ){
//			UtilsShared.setBoolean(this, ConfigStaticType.SettingField.XB_ISPAYSUCCESS,true);
			String title = "支付结果";
//			String orderid = UtilsShared.getString(this, ConfigStaticType.SettingField.XB_ORDERID,"0");
			String orderid = "";
			String url = ConfigSystem.SERVER_ROOT + "app/paysuccess.html?ordersn=" + orderid;
			String refresh = "true";
//			WebviewIntentData webviewIntentData = new WebviewIntentData(title,url,refresh);
//			Intent intent = new Intent(this,CommonWebViewActivity.class);
//			intent.putExtra("intentdata",webviewIntentData);
//			startActivity(intent);
			Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
			this.finish();
		}else if(resp.errCode == -2){
			Toast.makeText(WXPayEntryActivity.this, "付款已取消", Toast.LENGTH_SHORT).show();
			this.finish();
		}

	}
}