package com.lazy.android.baseui.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lazy.android.baseprotocol.LZHttpIProtocolCallback;
import com.lazy.android.baseprotocol.LZHttpProtocolHandlerBase;
import com.lazy.android.config.ConfigDatebase;
import com.lazy.android.baseui.common.CommonViewUtils;
import com.lazy.android.baseui.common.CustomLoadDialog;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Fragment的基类。产品里的Fragment必须从这个类继承
 * 
 * @author gavin
 * 
 */
public class LZBaseFragment extends Fragment implements LZHttpIProtocolCallback {
	protected LZBaseActivity parentActivity;
    private static final String TAG = "BaseActivity";
    public final static String ACTIVITY_EXTRA = "activity_extra";
    public final static String ACTIVITY_EXTRA2 = "activity_extra2";
    public final static String ACTIVITY_BUNDLE = "bundle";
    public final static String UID_EXTRA = "uid";

    protected boolean mEnableResumeLoadImage = true;

    protected long mActivityId = 0;
    protected long mEndActivityId = 0;

    private CustomLoadDialog mWaitDlg = null;
//    protected DbManager mDbManager = null;
//	protected DbManager.DaoConfig daoConfig = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentActivity = getBaseActivity();
	}
	
	public LZBaseActivity getBaseActivity() {
		return (LZBaseActivity) getActivity();
	}


	@Override
	public void onHttpStart(LZHttpProtocolHandlerBase protocol) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onHttpSuccess(LZHttpProtocolHandlerBase protocol) {
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
    public void onResume() {
        super.onResume();
    }
    
    public void startWaitingDialog(String title, String text) {
        if (isDialogShowing()) {
            return;
        }
        mWaitDlg = CommonViewUtils.getWaitDlg(getBaseActivity(), title, text);
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

    public boolean isDialogShowing() {
        return mWaitDlg != null && mWaitDlg.isShowing();
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


    //	获得操作sqlite数据库的管理对象
    private DbManager dbManager;
    public DbManager getDbManager(){
        if(dbManager == null){
            return x.getDb(ConfigDatebase.daoConfig);
        }else{
            return dbManager;
        }
    }


    //	xutils注解代码开始
    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }
//	xutils注解代码结束




    //	为了统一管理页面跳转，所有调用都通过BaseFragment里面的方法
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



}
