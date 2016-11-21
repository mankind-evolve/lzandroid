package com.lazy.android.basefunc.LZUtils;

import android.text.format.Time;

/**
 * @ClassName: UniqueIdGenerator
 * @Description: 生成唯一的长整数
 * @author
 * 
 */
public class UtilsUniqueId {
	private long mLastId = 0;
	private Time mTime;
	public static UtilsUniqueId mInstance = null;
	
	private UtilsUniqueId() {
		mTime = new Time();
	}
	public static UtilsUniqueId getInstance() {
		if (mInstance == null) {
			mInstance = new UtilsUniqueId();
		}
		return mInstance;
	}

	synchronized public long genericId() {
		mTime.setToNow();
		long id = mTime.toMillis(true);
		if (id <= mLastId) {
			id = mLastId + 1;
		}
		mLastId = id;
		return id;
	}
}
