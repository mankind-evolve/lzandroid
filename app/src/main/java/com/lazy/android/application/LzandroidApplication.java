package com.lazy.android.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.view.WindowManager;


import com.baidu.mapapi.SDKInitializer;
import com.lazy.android.basefunc.LZLogger.Logger;
import com.lazy.android.R;
import com.lazy.android.basefunc.LZUtils.UtilsCrash;
import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import cn.jpush.android.api.JPushInterface;


public class LzandroidApplication extends Application {
    private final static String TAG = "LzandroidApplication";
    private static Context mContext = null;
    private static ConnectivityManager mConnectivityManager = null;

    private long mMainActivityId;

    public HashMap<Long, Activity> mActivitys;

    private UtilsCrash mCrashHandler;
    /**
     * app 版本
     */
    public static String APP_VERSION;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();

        initCrashHandler();


//        初始化xutils控件
        initXutils();

//        初始化umeng第三方
        initUmeng();

//        初始化极光推送
        initJpush();

//        初始化百度地图
        initBaidumap();

        mMainActivityId = 0;
        mActivitys = new HashMap<Long, Activity>();
        mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        GraphicUtils.mScreenWidth = wm.getDefaultDisplay().getWidth();
//        GraphicUtils.mScreenHeight = wm.getDefaultDisplay().getHeight();

        String pkName = mContext.getPackageName();
        String versionName = "";

        try {
            versionName = mContext.getPackageManager().getPackageInfo(pkName, PackageManager.GET_ACTIVITIES).versionName;
        } catch (NameNotFoundException e) {
            versionName = "";
        }
        if (TextUtils.isEmpty(versionName)) {
            versionName = mContext.getString(R.string.version);
        }
        APP_VERSION = versionName;
//        LZConfig_System.PUBLISH_CHANNEL = getMetaData(mContext, LZConfig_System.CHANNEL_KEY);
//        DataServiceManager.getInstance(mContext);
    }

//    初始化百度地图
    private void initBaidumap() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }


    //    极光推送初始化
    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    //    umeng初始化
    private void initUmeng() {
        MobclickAgent. startWithConfigure(new MobclickAgent.UMAnalyticsConfig(
            this,
            "5764c475e0f55acdc40027e7",
            "APP",
            MobclickAgent.EScenarioType.E_UM_NORMAL,
            true));
        MobclickAgent.setCatchUncaughtExceptions(true);
    }

    //    初始化xutils
    private void initXutils() {
//        xutils3初始化
        x.Ext.init(this);
        // 是否输出debug日志
        x.Ext.setDebug(false);
    }


    public static Context getContext() {
        return mContext;
    }

    public static ConnectivityManager getConnectivityManager() {
        return mConnectivityManager;
    }

    public long getMainActivityId() {
        return mMainActivityId;
    }

    public void setMainActivityId(long activityId) {
        mMainActivityId = activityId;
    }


    private static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
            //
        }
        return null;
    }

//    初始化crashhandler
    private void initCrashHandler() {
        mCrashHandler = UtilsCrash.getInstance();
        mCrashHandler.init(mContext);
    }

    public void popAllActivity() {
        Iterator<Entry<Long, Activity>> iterator = mActivitys.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Long, Activity> entry = iterator.next();
            Activity activity = entry.getValue();
            long activityId = entry.getKey();
            if (mMainActivityId == activityId) {
                continue;
            }
            activity.finish();
            iterator.remove();
        }
    }

    /**
     * @Title: exitApplication
     * @Description: 完全退出app
     * @param context
     *            void
     */
    public static void exitApplication(Context context) {
//        DataServiceManager.getInstance(context).onDestroy();
        System.exit(0);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Logger.getInstance(TAG).debug("app onLowMemory()");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.getInstance(TAG).debug("app onTerminate()");
    }


}
