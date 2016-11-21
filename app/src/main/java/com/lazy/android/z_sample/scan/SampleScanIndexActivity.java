package com.lazy.android.z_sample.scan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lazy.android.R;
import com.lazy.android.basefunc.LZScan.ScanCommonActivity;
import com.lazy.android.basefunc.LZScan.ScanConstant;
import com.lazy.android.basefunc.LZScan.ScanCreateActivity;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by chenglei on 16/6/26.
 */
@ContentView(R.layout.lzscan_index_activity)
public class SampleScanIndexActivity extends LZBaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();
	}


//	创建二维码
	@Event(R.id.lzscan_create)
	private void lzscan_create_Event(View view){
		startActivity(new Intent(this, ScanCreateActivity.class));
	}

	//	扫描二维码
	@Event(R.id.lzscan_common1)
	private void lzscan_common1_Event(View view){
		Intent intent=new Intent(this,ScanCommonActivity.class);
		intent.putExtra(ScanConstant.REQUEST_SCAN_MODE,ScanConstant.REQUEST_SCAN_MODE_QRCODE_MODE);
		startActivity(intent);
	}


	//	扫描条形码
	@Event(R.id.lzscan_common2)
	private void lzscan_common2_Event(View view){
		Intent intent=new Intent(this,ScanCommonActivity.class);
		intent.putExtra(ScanConstant.REQUEST_SCAN_MODE,ScanConstant.REQUEST_SCAN_MODE_BARCODE_MODE);
		startActivity(intent);
	}


	//	扫描二维码和条形码
	@Event(R.id.lzscan_common3)
	private void lzscan_common3_Event(View view){
		Intent intent=new Intent(this,ScanCommonActivity.class);
		intent.putExtra(ScanConstant.REQUEST_SCAN_MODE,ScanConstant.REQUEST_SCAN_MODE_ALL_MODE);
		startActivity(intent);
	}


	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(true);
		mCommonHead.setMiddleTitle("二维码扫描与生成");
	}

}
