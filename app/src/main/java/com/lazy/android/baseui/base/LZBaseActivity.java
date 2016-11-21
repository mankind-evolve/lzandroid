package com.lazy.android.baseui.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.lazy.android.basefunc.LZUtils.BaiduMapHelper;
import com.lazy.android.basefunc.LZUtils.UtilsUmengStat;
import com.lazy.android.baseui.common.CommonFoot;
import com.lazy.android.config.ConfigStaticType;
import com.lazy.android.R;
import com.lazy.android.application.LzandroidApplication;

import com.lazy.android.basefunc.LZUtils.UtilsShared;
import com.lazy.android.basefunc.LZUtils.UtilsUniqueId;
import com.lazy.android.baseprotocol.LZHttpIProtocolCallback;
import com.lazy.android.baseprotocol.LZHttpProtocolHandlerBase;
import com.lazy.android.config.ConfigDatebase;
import com.lazy.android.baseui.common.CommonHead;
import com.lazy.android.baseui.common.CommonViewUtils;
import com.lazy.android.baseui.common.CustomLoadDialog;
import com.lazy.android.baseui.tools.ImeHelper;


import org.xutils.DbManager;
import org.xutils.x;

import java.util.List;

/**
 * @ClassName: BaseActivity
 * @Description: Activity基类
 * @author
 * 
 */
public class LZBaseActivity extends FragmentActivity implements LZHttpIProtocolCallback {
	private static final String TAG = "BaseActivity";
	public final static String ACTIVITY_EXTRA = "activity_extra";
	public final static String ACTIVITY_EXTRA2 = "activity_extra2";
	public final static String ACTIVITY_BUNDLE = "bundle";
	public final static String UID_EXTRA = "uid";

	protected boolean mEnableResumeLoadImage = true;

	protected long mActivityId = 0;
	protected long mEndActivityId = 0;

	protected CommonHead mCommonHead = null;
	protected CommonFoot mCommonFood = null;
	
	private CustomLoadDialog mWaitDlg = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImeHelper.setNoTitleAndInput(this);
		createActivityId();
		((LzandroidApplication) this.getApplication()).mActivitys.put(mActivityId, this);
//		HttpImageLoader.getInstance(this).setCurrentBelongId(mActivityId);
		x.view().inject(this);
//		初始化头部
//		initCommonHead();
	}


	/**
	 * 子类需要重写这个方法，初始化视图
	 */
	public void initView() {
	}


	/**
	 *
	 * 顶部视图定义
	 */
	public void  initCommonHead(){
		mCommonHead = this.getmCommonHead();
		mCommonHead.initCommonHead();
//		设置左侧按钮的点击事件
		LinearLayout common_headleft_leftimage_out = (LinearLayout) findViewById(R.id.common_headleft_leftimage_out);
		common_headleft_leftimage_out.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				CloseKeyboard();
			}
		});
	}

//	底部按钮定义
	public void initCommonFoot(){
		mCommonFood = (CommonFoot) findViewById(R.id.lz_common_foot);
		mCommonFood.initCommonFoot();
		mCommonFood.menuselected(1);
	}




	/**
	 * 获得顶端的mCommonHead
	 * @return
	 */
	public CommonHead getmCommonHead(){
		return (CommonHead) findViewById(R.id.common_head);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
//		umeng统计
		UtilsUmengStat.onResume(this);
		if (mEnableResumeLoadImage) {
//			HttpImageLoader.getInstance(this).resumeBelongId(mActivityId);
		}
	}

	@Override
	public void onPause() {
		//		umeng统计
		UtilsUmengStat.onPause(this);
		if (mWaitDlg != null) {
			mWaitDlg.dismiss();
			mWaitDlg = null;
		}
		if (mEnableResumeLoadImage) {
//			HttpImageLoader.getInstance(this).recyclePauseByBelongId(mActivityId);
		}
		System.gc();
		overridePendingTransition(R.anim.lz_alpha_in, R.anim.lz_alpha_out);
		super.onPause();
	}

	public void close() {

	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		((LzandroidApplication) getApplication()).mActivitys.remove(mActivityId);
//		HttpImageLoader.getInstance(this).recycle(mActivityId);
		System.gc();
		close();
		super.onDestroy();
	}

	public boolean onSuperBackPressed() {
		return false;
	}

	@Override
	public void onBackPressed() {
		boolean result = false;
		super.onBackPressed();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public long createActivityId() {
		mActivityId = UtilsUniqueId.getInstance().genericId();
		return mActivityId;
	}

	public long getActivityId() {
		return mActivityId;
	}

	public LZBaseActivity getActivity() {
		return this;
	}

	@Override
	public void onHttpStart(LZHttpProtocolHandlerBase protocol) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onHttpProgress(long total, long current, boolean isUploading) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onHttpFailure(Exception except, String msg) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onHttpSuccess(LZHttpProtocolHandlerBase protocol) {
		// TODO Auto-generated method stub
	}
	
	public void startWaitingDialog(String title, String text) {
		if (isDialogShowing()) {
			return;
		}
		mWaitDlg = CommonViewUtils.getWaitDlg(this, title, text);
		mWaitDlg.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface arg0) {

			}
		});
		mWaitDlg.show();
	}

	public void startWaitingDialog(int title, int text) {
		if (title == 0) {
			startWaitingDialog("", getString(text));
		} else {
			startWaitingDialog(getString(title), getString(text));
		}
	}
	
	public void startWaitingDialog(int text) {
		startWaitingDialog("", getString(text));
	}
	
	/**
	 * @Title: dismissWaitingDialog
	 * @Description: 关闭等待Dialog
	 * @return void
	 */
	public void dismissWaitingDialog() {
		if (mWaitDlg != null && mWaitDlg.isShowing()) {
			mWaitDlg.dismiss();
		}
	}

	public boolean isDialogShowing() {
		return mWaitDlg != null && mWaitDlg.isShowing();
	}	

//	提示信息
	public void ToastShow(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}


    //  获得当前的Activity，并写入xml
    public  void getTopActivityString(){
        ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if(runningTaskInfos != null){
            UtilsShared.setString(this, ConfigStaticType.SettingField.LOGIN_ACTIVITY, (runningTaskInfos.get(0).topActivity).toString());
        }else{

        }
    }


    //百度定位位置,定位5秒
    public void BaiduMapLocation(final Boolean setin) {
        new BaiduMapHelper(getApplicationContext(),new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
//                SettingHelper.setString(LZBaseActivity.this, SettingHelper.SettingField.LONGITUDE,bdLocation.getLongitude()+"");
//                SettingHelper.setString(LZBaseActivity.this, SettingHelper.SettingField.LATITUDE, bdLocation.getLatitude() + "");
                //写入城市的id
                String cityname = bdLocation.getCity();
				ToastShow(cityname);
//                int cityid = new RegionDbController(getActivity()).getcityid(cityname);
                //                如果定位不成功，默认的城市为北京
//                if (cityid == 0) {
//                    SettingHelper.setInt(LZBaseActivity.this, SettingHelper.SettingField.REGIONID,LZConfig_System.DEFAULT_CITY);
//                }else{
//                    if(setin){
//                        SettingHelper.setInt(LZBaseActivity.this,SettingHelper.SettingField.REGIONID,cityid);
//                    }
//                }
            }
        },5);
    }


//    关闭软键盘
    public void CloseKeyboard(){
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

//判断网络连接是否正常
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


	//	获得操作sqlite数据库的管理对象
	public DbManager getDbManager(){
		return x.getDb(ConfigDatebase.daoConfig);
	}


//	判断是否登录
	public Boolean isLogin(){
		if(!UtilsShared.getBoolean(this, ConfigStaticType.SettingField.LOGIN_SUCCESS,false)){
//			startActivity(new Intent(this, LoginActivity.class));
			return true;
		}

		return false;
	}



}