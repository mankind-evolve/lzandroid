package com.lazy.android.basefunc.LZUtils;

import java.io.DataOutputStream;

import com.lazy.android.config.ConfigSystem;


import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;

/**
 * 手机平台（如Android、iPhone 软件版本 手机IMEI 手机IMSI 手机产品ID（手机型号）
 * 国家ID（mcc中国 60 运营商ID（MNC中国移动0,02 (GSM),07 (TD)，联通是01
 * 地区ID（位置区域码，它是唯一地识别我国数字PLMN中每个位置区的，是一字节16进制的BCD码） 当前基站ID
 * 
 */
public class UtilsSystemInfo {
	
	public static String getPlatform() {
		return ConfigSystem.PLATFORM;
	}
	
	public static String getPublishChannel(Context context) {
		String code = getMetaData(context, ConfigSystem.CHANNEL_KEY);
		if (!TextUtils.isEmpty(code)) {
			return code;
		}
		return "consame";
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

	public static String getIMEI(Context context) {
		String imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		if (TextUtils.isEmpty(imei)) {
			imei = "";
		}
		return imei;
	}

	public static String getIMSI(Context context) {
		String imsi = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
		if (TextUtils.isEmpty(imsi)) {
			imsi = "";
		}
		return imsi;
	}
	/**
	* @Title: getDeviceId
	* @Description: 得到设备的id
	* @param context
	* @return
	* String
	*/
	public static String getDeviceId(Context context) {
		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}	
	public static String getProduct() {
		return Build.PRODUCT;
	}
	/**
	* @Title: getProductId
	* @Description: 型号
	* @return
	* String
	*/
	public static String getProductModel() {
		return Build.MODEL;
	}
	/**
	* @Title: getManufacturer
	* @Description: 生产厂家。
	* @return
	* String
	*/
	public static String getManufacturer() {
		return Build.MANUFACTURER;
	}
	public static String getSDKVersion() {
		return Build.VERSION.SDK;
	}
	public static String getSDKLevel() {
		return Build.VERSION.RELEASE;
	}
	
	/**
	* @Title: getMCC
	* @Description: country code
	* @param context
	* @return
	* String
	*/
	public static String getMCC(Context context) {
		String MCCAndMNC = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperator();
		if (MCCAndMNC == null || MCCAndMNC.length() < 3) {
			return null;
		} else {
			return MCCAndMNC.substring(0, 3);
		}
	}

	/**
	* @Title: getMNC
	* @Description: network code
	* @param context
	* @return
	* String
	*/
	public static String getMNC(Context context) {
		String MCCAndMNC = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
				.getNetworkOperator();
		if (MCCAndMNC == null || MCCAndMNC.length() < 3)
			return null;
		else
			return MCCAndMNC.substring(3);
	}

	/**
	* @Title: getLac
	* @Description: Location code
	* @param context
	* @return
	* String
	*/
	public static String getLac(Context context) {
		String ret = null;
		CellLocation loc = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
		if (loc instanceof GsmCellLocation) {
			GsmCellLocation gsmLoc = (GsmCellLocation) loc;
			ret = String.valueOf(gsmLoc.getLac());
		} else if (loc instanceof CdmaCellLocation) {
			CdmaCellLocation cdmaLoc = (CdmaCellLocation) loc;
			ret = String.valueOf(cdmaLoc.getBaseStationLatitude());
		}
		return ret;
	}
	/**
	* @Title: getCellId
	* @Description: CellId
	* @param context
	* @return
	* String
	*/
	public static String getCellId(Context context) {
		String ret = null;
		CellLocation loc = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
		if (loc instanceof GsmCellLocation) {
			GsmCellLocation gsmLoc = (GsmCellLocation) loc;
			ret = String.valueOf(gsmLoc.getCid());
		} else if (loc instanceof CdmaCellLocation) {
			CdmaCellLocation cdmaLoc = (CdmaCellLocation) loc;
			ret = String.valueOf(cdmaLoc.getBaseStationId());
		}
		return ret;
	}
	/**
	* @Title: getWlanMac
	* @Description: 无线mac地址
	* @param c
	* @return
	* String
	*/
	public static String getWlanMac(Context c) {
		WifiManager mWifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
		return mWifiManager.getConnectionInfo().getBSSID();
	}
	/**
	* @Title: getBTName
	* @Description: 蓝牙节点名
	* @return
	* String
	*/
	public final static String getBTName() {
        try {
            BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
            if (bt != null) {
                String name = bt.getName();
                if (name != null)
                    return name;
                else
                    return "";
            } else
                return "";
        } catch (Exception e) {
            return "";
        }
    }
	/**
	* @Title: isRoot
	* @Description: 手机是否root过
	* @return
	* boolean
	*/
	public static boolean isRoot() {
		String command = "a";
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {

			}
		}
		return true;
	}

	public static String getCountryISO(Context context) {
		String isoString = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
				.getNetworkCountryIso();
		return isoString;
	}
	/**
	* @Title: isScreenLocked
	* @Description: 是否锁屏
	* @param c
	* @return
	* boolean
	*/
	public static boolean isScreenLocked(Context c) {
        android.app.KeyguardManager mKeyguardManager = (KeyguardManager) c.getSystemService(Context.KEYGUARD_SERVICE);
        return !mKeyguardManager.inKeyguardRestrictedInputMode();
    }

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}
}
