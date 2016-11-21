package com.lazy.android.basefunc.LZUtils;

import android.content.Context;


import com.lazy.android.config.ConfigSystem;
import com.umeng.analytics.MobclickAgent;

public class UtilsUmengStat {

	public static void onResume(Context context) {
		if (ConfigSystem.STAT_ENABLE) {
			MobclickAgent.onPageStart(context.getClass().getName());
			MobclickAgent.onResume(context);
		}
	}

	public static void onPause(Context context) {
		if (ConfigSystem.STAT_ENABLE) {
			MobclickAgent.onPageEnd(context.getClass().getName());
			MobclickAgent.onPause(context);
		}
	}

	public static void onEvent(Context context, String eventId) {
		if (ConfigSystem.STAT_ENABLE) {
			MobclickAgent.onEvent(context, eventId);
		}
	}

	public static void exit(Context context) {
		if (ConfigSystem.STAT_ENABLE) {
			MobclickAgent.onKillProcess(context);
		}
	}

	public static void setDebugMode(boolean debugMode) {
		if (ConfigSystem.STAT_ENABLE) {
			MobclickAgent.setDebugMode(debugMode);
		}
	}

}
