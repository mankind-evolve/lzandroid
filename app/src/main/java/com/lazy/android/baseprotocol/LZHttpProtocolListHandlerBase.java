package com.lazy.android.baseprotocol;

import android.content.Context;


import com.lazy.android.basemodel.LZBaseData;
import com.lazy.android.basemodel.LZRefreshListData;
import com.lazy.android.config.ConfigSystem;

import java.util.ArrayList;

/**
 * @author
 * @ClassName: ListProtocolHandlerBase
 * @Description: 列表数据协议基类。支持分页
 * @date
 */
public abstract class LZHttpProtocolListHandlerBase<T extends LZBaseData> extends LZHttpProtocolHandlerBase {
	/**
	 * 传入的参数。
	 */
	protected LZRefreshListData<T> mDataList;
	/**
	 * 上次请求最后一条数据的id。(控制分页需要)
	 */
	protected long mLastId = -1L;
	/**
	 * 当前页第一项的index值，算式为：(页数-1)*分页大小。默认值为0
	 */
//	protected int mStartIndex = 0;
	protected int mStartIndex = 1;
	protected int mPageNum = 1;
	/**
	 * 是否刷新。如果是刷新，那么需要清空mDataList中原来的数据
	 */
	protected boolean mRefreshed = false;
	/**
	 * 暂存解析出的数据
	 */
	protected ArrayList<T> mReceiveDataList = new ArrayList<T>();
	/**
	 * 页大小
	 */
	protected int mPageSize = ConfigSystem.PAGE_SIZE;

	public LZHttpProtocolListHandlerBase(Context context, boolean refreshed, LZRefreshListData<T> data, LZHttpIProtocolCallback callBack) {
		super(context, callBack);
		mRefreshed = refreshed;
		mDataList = data;
		mDataList.setLastPage(false);
		mPageSize = mDataList.getPageSize();
		if (!refreshed) {
			mLastId = mDataList.getCursor();
			mStartIndex = (data.getPageNum() - 1) * mPageSize;
			mPageNum = data.getPageNum();
			if (mStartIndex < 1) {
				mStartIndex = 1;
			}
		} else {
			mDataList.clear();
		}
	}

	public LZRefreshListData<T> getDataList() {
		return mDataList;
	}

	/**
	 * 支持分页的默认处理函数
	 */
	@Override
	public void afterParse() {

		if (mReceiveDataList != null && !mReceiveDataList.isEmpty()
			&& mDataList != null) {
			if (mRefreshed) {
				mDataList.clear();
			}


			mDataList.setDataList(mReceiveDataList, null);

			if (mReceiveDataList.size() < mPageSize) {
				mDataList.setLastPage(true);
			}
		}
	}

	/**
	 * 获得解析后的数据
	 */
    public ArrayList<T> getReceiveDataList(){
        return  mReceiveDataList;
    }

}
