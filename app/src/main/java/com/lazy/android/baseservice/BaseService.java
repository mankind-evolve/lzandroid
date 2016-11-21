package com.lazy.android.baseservice;

import com.lazy.android.application.LzandroidApplication;

import android.content.Context;

/**
 * @ClassName: BaseService
 * @Description: 基类
 * @author
 * @date
 * 
 */
public abstract class BaseService {
	protected Context mContext;
	
	public BaseService(Context context) {
		mContext = LzandroidApplication.getContext();
	}
	/**
	 * 创建时做一些初始化操作
	 */
	public abstract void onCreate();
	/**
	* @Title: clear
	* @Description: 清空缓存数据。子类可以在此把内存里缓存的数据清空
	* @return void
	*/
	public abstract void clear();
	/**
	 * 内部调用clear()清空数据
	 */
	public void onDestroy() {
		clear();
	}
}
