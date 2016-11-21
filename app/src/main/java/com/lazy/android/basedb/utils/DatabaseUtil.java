package com.lazy.android.basedb.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.lazy.android.basefunc.LZLogger.Logger;

/**
* @ClassName: DatabaseUtil
* @Description: 数据库表数据检索、更新、插入工具类
* @author
* @date
*
*/
public class DatabaseUtil {
	public final static String TAG = "DatabaseUtil";
	
	// 查询
	public final static Cursor query(Context c, Uri uri, final String[] column,final String where, final String[] value, final String order) {
		Cursor cursor = null;
		try {
			cursor = c.getContentResolver().query(uri, column, where, value, order);
		} catch (Exception e) {
			Logger.getInstance(TAG).debug(e.getMessage());
		}
		return cursor;
	}
	// 插入
	public final static Uri insert(Context c, Uri uri, ContentValues values) {
		Uri insertUri = null;
		try {
			insertUri = c.getContentResolver().insert(uri, values);
		} catch (Exception e) {
			Logger.getInstance(TAG).debug(e.getMessage());
		}
		return insertUri;
	}
	// 更新
	public final static int update(Context c, Uri uri, ContentValues values, final String where, final String[] params) {
		int count = 0;
		try {
			count = c.getContentResolver().update(uri, values, where, params);
		} catch (Exception e) {
			Logger.getInstance(TAG).debug(e.getMessage());
		}
		return count;
	}
	// 删除
	public final static int delete(Context c, Uri uri, final String where, final String[] params) {
		int count = 0;
		try {
			count = c.getContentResolver().delete(uri, where, params);
		} catch (Exception e) {
			Logger.getInstance(TAG).debug(e.getMessage());
		}
		return count;
	}
}
