package com.lazy.android.basedb.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.lazy.android.basefunc.LZLogger.Logger;
import com.lazy.android.basedb.provider.LZAndroidDatabase.CMBaseTable;
import com.lazy.android.basedb.provider.LZAndroidDatabase.ServiceCategoryTable;
import com.lazy.android.basedb.utils.DatabaseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */
public class LZAndroidProvider extends ContentProvider {
	private static final String TAG = "LZAndroidProvider";
	private static final String DATABASE_NAME = "lzandroid.db";
	private static final int DATABASE_VERSION = 2;

	private static final UriMatcher uriMatcher;

	private static final ArrayList<CMBaseTable> mDataTableList;

	private static LZAndroidProvider mInstance;

	private Context mContext;


	static {
		mInstance = null;
		mDataTableList = new ArrayList<CMBaseTable>();
		mDataTableList.add(new LZAndroidDatabase.ServiceCategoryTable());//创建项目分类的字典数据
		mDataTableList.add(new LZAndroidDatabase.ServiceRegionTable());//创建地区分类的字典数据
		mDataTableList.add(new LZAndroidDatabase.ShopCarItemTable());//创建购物车项目字典数据
		mDataTableList.add(new LZAndroidDatabase.ShopCarCouponTable());//创建购物车优惠券字典数据
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		for (int index = 0, count = mDataTableList.size(); index < count; ++index) {
			uriMatcher.addURI(LZAndroidDatabase.AUTHORITY, mDataTableList.get(index).getTableName(), index + 1);
		}
	}

	public static void deleteAll(Context context) {
		for (CMBaseTable table : mDataTableList) {
			DatabaseUtil.delete(context, table.getContentUri(), null, null);
		}
	}

	@Override
	public boolean onCreate() {
		mContext = getContext();
		mDatabaseHelper = new DatabaseHelper(mContext);
		return true;
	}

	public static LZAndroidProvider getInstance() {
		if (mInstance == null) {
			mInstance = new LZAndroidProvider();
			mInstance.onCreate();
		}
		return mInstance;
	}

	private String getDatabaseTypeString(Uri uri) {
		int result = uriMatcher.match(uri) - 1;
		if (result >= 0 && result < mDataTableList.size()) {
			return mDataTableList.get(result).getTableName();
		} else {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	private Uri getDatabaseTypeUri(Uri uri) {
		int result = uriMatcher.match(uri) - 1;
		if (result >= 0 && result < mDataTableList.size()) {
			return mDataTableList.get(result).getContentUri();
		} else {
			throw new IllegalArgumentException("Unknown URI:" + uri);
		}
	}

	/**
	 * @param sql 要执行的sql语句
	 * @return void
	 * @Title: execSQL
	 * @Description: 执行sql语句
	 */
	public void execSQL(String sql) {
		if (TextUtils.isEmpty(sql)) {
			return;
		}
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		try {
			db.execSQL(sql);
		} catch (SQLException e) {
			Logger.getInstance(TAG).debug(e.getMessage(), e);
		}
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		int count = db.delete(getDatabaseTypeString(uri), where, whereArgs);
		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		long rowId = db.insert(getDatabaseTypeString(uri), BaseColumns._ID, values);
		Uri tableUri = getDatabaseTypeUri(uri);

		if (rowId > 0) {
			Uri newRowUri = ContentUris.withAppendedId(tableUri, rowId);
			getContext().getContentResolver().notifyChange(newRowUri, null);
			return newRowUri;
		}
		throw new SQLException("Failed to insert row into:" + uri);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			    String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getDatabaseTypeString(uri));
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = BaseColumns._ID;
		} else {
			orderBy = sortOrder;
		}
		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where,
			  String[] whereArgs) {
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		int count = db.update(getDatabaseTypeString(uri), values, where, whereArgs);

		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	private class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			try {
				getWritableDatabase();
			} catch (SQLiteException e) {
				Logger.getInstance(TAG).debug(e.getMessage(), e);
			}
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			try {
				for (CMBaseTable baseTable : mDataTableList) {
//					创建数据库
					baseTable.createTable(db);
				}
				initDb(db);
			} catch (Exception e) {
				Logger.getInstance(TAG).debug(e.getMessage(), e);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (oldVersion < 1) {
				for (CMBaseTable baseDb : mDataTableList) {
					db.execSQL("DROP TABLE IF EXISTS " + baseDb.getTableName());
				}
				onCreate(db);
				return;
			}
			if (oldVersion == 1) { // V1.3
				// 删除老的表
				//
				oldVersion++;
			}
			if (oldVersion != newVersion) {
				throw new IllegalStateException("error upgrading the database to version:" + newVersion);
			}
		}

		// 数据初始化
		private void initDb(SQLiteDatabase db) {
			//初始化项目分类的字典
			initServiceCategoryData(db);
			// TODO 需要初始化行政区域字典
			initAreaData(db);
		}

		//        初始化项目分类的字典数据
		private void initServiceCategoryData(SQLiteDatabase db) {
			//id, cityName, ename, parent_id, isOpen, areaCode
			final String sqlTemp = "INSERT INTO " + LZAndroidDatabase.TABLE_ServiceCategory + " ("
				+ LZAndroidDatabase.ServiceCategoryTable.ID + " , "
				+ ServiceCategoryTable.NAME + " , "
				+ ServiceCategoryTable.PARENT_ID + " , "
				+ ServiceCategoryTable.LEVEL + " , "
				+ ServiceCategoryTable.SERIALNUM + " , "
				+ ServiceCategoryTable.STATUS
				+ ") VALUES ";
			String value = "";
			InputStream is = null;
			String sql = null;
			try {
				is = mContext.getResources().getAssets().open("servicecategory.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				long beginTime = System.currentTimeMillis();
				Logger.getInstance(TAG).debug("开始导入项目分类的字典数据");
				//开始事务
				db.beginTransaction();
				while ((value = reader.readLine()) != null) {//"INSERT INTO tcCityNew(id,cityName,pyName,pid,isOpen,postcode) values "
					sql = sqlTemp + value.trim();
					db.execSQL(sql);
				}
				reader.close();
				// 设置事务处理成功
				db.setTransactionSuccessful();
				// 处理完成
				db.endTransaction();
				Logger.getInstance(TAG).debug("导入数据完成。耗时:" + (System.currentTimeMillis() - beginTime));
			} catch (IOException e) {
				Logger.getInstance(TAG).debug(e.getMessage(), e);
			} catch (Exception e) {
				Logger.getInstance(TAG).debug(e.getMessage(), e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						Logger.getInstance(TAG).debug(e.getMessage(), e);
					}
				}
			}
		}


		//        初始化地区的字典数据
		private void initAreaData(SQLiteDatabase db) {
			final String sqlTemp = "INSERT INTO " + LZAndroidDatabase.TABLE_REGION + " ("
				+ LZAndroidDatabase.ServiceCategoryTable.ID + " , "
				+ ServiceCategoryTable.NAME + " , "
				+ ServiceCategoryTable.PARENT_ID + " , "
				+ ServiceCategoryTable.STATUS
				+ ") VALUES ";
			String value = "";
			InputStream is = null;
			String sql = null;
			try {
				is = mContext.getResources().getAssets().open("initAreaData.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				long beginTime = System.currentTimeMillis();
				Logger.getInstance(TAG).debug("开始导入地区分类的字典数据");
				//开始事务
				db.beginTransaction();
				while ((value = reader.readLine()) != null) {//"INSERT INTO tcCityNew(id,cityName,pyName,pid,isOpen,postcode) values "
					sql = sqlTemp + value.trim();
					db.execSQL(sql);
				}
				reader.close();
				// 设置事务处理成功
				db.setTransactionSuccessful();
				// 处理完成
				db.endTransaction();
				Logger.getInstance(TAG).debug("导入数据完成。耗时:" + (System.currentTimeMillis() - beginTime));
			} catch (IOException e) {
				Logger.getInstance(TAG).debug(e.getMessage(), e);
			} catch (Exception e) {
				Logger.getInstance(TAG).debug(e.getMessage(), e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						Logger.getInstance(TAG).debug(e.getMessage(), e);
					}
				}
			}
		}

		private void initRegionData(SQLiteDatabase db) {

		}
	}

	private DatabaseHelper mDatabaseHelper = null;
}
