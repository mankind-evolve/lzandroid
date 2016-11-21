package com.lazy.android.basefunc.LZLogger;

import android.text.TextUtils;
import android.util.Log;

import com.lazy.android.config.ConfigSystem;


public class Logger implements ILogger {
	private static Logger instance = null;
	private String mTag = "Logger";

	// 阻止直接创建实例
	private Logger() {

	}

	/**
	 * @Title: getInstance
	 * @Description: 创建Logger唯一实例
	 * @param tag
	 * @return Logger
	 */
	public static Logger getInstance(String tag) {
		if (instance == null) {
			instance = new Logger();
		}
		instance.mTag = tag;
		return instance;
	}

	/**
	 * 
	 */
	@Override
	public void info(String msg) {
		doLog(Log.INFO, msg);
	}

	/**
	 * 
	 */
	@Override
	public void warn(String msg) {
		doLog(Log.WARN, msg);
	}

	/**
	 * 
	 */
	@Override
	public void error(String msg) {
		doLog(Log.ERROR, msg);
	}

	/**
	 * 
	 */
	@Override
	public void debug(String msg) {
		doLog(Log.DEBUG, msg);
	}

	private void doLog(int logType, String msg) {
		if (ConfigSystem.DEBUG) {
			if (TextUtils.isEmpty(msg)) {
				return;
			}
			switch (logType) {
			case Log.INFO:
				Log.i(mTag, msg);
				break;
			case Log.WARN:
				Log.w(mTag, msg);
				break;
			case Log.ERROR:
				Log.e(mTag, msg);
				break;
			case Log.DEBUG:
				Log.d(mTag, msg);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void info(String msg, Exception e) {
		doLog(Log.INFO, msg, e);
	}

	/**
	 * 
	 */
	@Override
	public void warn(String msg, Exception e) {
		doLog(Log.WARN, msg, e);
	}

	/**
	 * 
	 */
	@Override
	public void error(String msg, Exception e) {
		doLog(Log.ERROR, msg, e);
	}



	/**
	 * 
	 */
	@Override
	public void debug(String msg, Exception e) {
		doLog(Log.DEBUG, msg, e);
	}

	private void doLog(int logType, String msg, Exception e) {
		if (ConfigSystem.DEBUG) {
			if (TextUtils.isEmpty(msg)) {
				return;
			}
			switch (logType) {
			case Log.INFO:
				Log.i(mTag, msg, e);
				break;
			case Log.WARN:
				Log.w(mTag, msg, e);
				break;
			case Log.ERROR:
				Log.e(mTag, msg, e);
				break;
			case Log.DEBUG:
				Log.d(mTag, msg, e);
				break;
			default:
				break;
			}
		}
	}

}
