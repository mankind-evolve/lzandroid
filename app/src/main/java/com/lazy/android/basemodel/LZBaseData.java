package com.lazy.android.basemodel;

/**
 * @ClassName: BaseData
 * @Description: 模型基类
 * @author
 * 
 */

public abstract class LZBaseData extends LZBaseSqliteBean {
	protected long mCursorId = -1L;

	public long getCursorId() {
		if (mCursorId == -1) {
			return getId();
		}
		return mCursorId;
	}

	public abstract long getId();





}
