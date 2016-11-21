package com.lazy.android.basefunc.LZUtils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.lazy.android.application.LzandroidApplication;

public class UtilsNet {

	/**
	 * @Title: isNetworkAvailable
	 * @Description: 判断是否连接网络
	 * @param context
	 * @return true为以连接网络,false为未连接网络 boolean
	 */
	public static boolean isNetworkAvailable(Context context) {
		try {
			NetworkInfo info = LzandroidApplication.getConnectivityManager()
					.getActiveNetworkInfo();
			if (info != null && info.isAvailable()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * @Title: isWifi
	 * @Description: 判断是否wifi网络
	 * @param context
	 * @return boolean
	 */
	public static boolean isWifi(Context context) {
		NetworkInfo networkINfo = LzandroidApplication.getConnectivityManager()
				.getActiveNetworkInfo();
		if (networkINfo != null
				&& networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: isMobile
	 * @Description: 判断是否是移动网络
	 * @param context
	 * @return boolean
	 */
	public static boolean isMobile(Context context) {
		NetworkInfo networkINfo = LzandroidApplication.getConnectivityManager()
				.getActiveNetworkInfo();
		if (networkINfo != null
				&& networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: isWifiEnabled
	 * @Description: 判断WIFI是否打开
	 * @param context
	 * @return boolean
	 */
	public static boolean isWifiEnabled(Context context) {
		TelephonyManager mgrTel = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return ((LzandroidApplication.getConnectivityManager()
				.getActiveNetworkInfo() != null && LzandroidApplication
				.getConnectivityManager().getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
				.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}

	public static boolean isGpsEnabled(Context context) {
		LocationManager mLocationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
}
