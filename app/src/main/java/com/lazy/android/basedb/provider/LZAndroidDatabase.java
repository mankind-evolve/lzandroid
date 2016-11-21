package com.lazy.android.basedb.provider;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author
 * @ClassName:
 * @Description: 数据库表定义。约定：缺省值-1表示用户还没有填写此项值
 * @date
 */
public final class LZAndroidDatabase {

	public static final String AUTHORITY = "com.baicaijia";
	public static final String TABLE_ServiceCategory = "tcServiceCategory";
	public static final String TABLE_REGION = "region";
	public static final String TABLE_SHOPCARITEM = "shopcarItem";
	public static final String TABLE_SHOPCARCOUPON = "shopcarCoupon";

	public static abstract class CMBaseTable implements BaseColumns {

		public abstract String getTableName();

		public abstract String getBaseSqlString();

		public Uri getContentUri() {
			Uri uri = Uri.parse("content://" + AUTHORITY + "/" + getTableName());
			return uri;
		}

		public void createTable(SQLiteDatabase db) {
			db.execSQL(" CREATE TABLE" + " " + getTableName() + " (" + getBaseSqlString() + " );");
		}
	}


	// sqlite中0为false，1为true
	public static class BaseTable extends CMBaseTable {
		// id
		public static final String ID = "id";
		/**
		 * 父id
		 */
		public static final String PARENT_ID = "pid";
		/**
		 * 默认排序列
		 */
		public static final String SORT_ORDER = ID + " DESC";
		/**
		 * 升序排列
		 */
		public static final String SORT_ORDER_ASC = ID + " ASC";


		@Override
		public String getTableName() {
			return TABLE_REGION;
		}


		public String getBaseSqlString() {
			return BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," // 本地自增id
				+ BaseTable.ID + " INTEGER DEFAULT 0,"
				+ BaseTable.PARENT_ID + " INTEGER DEFAULT 0";
		}


	}

	//       项目分类数据库结构
	public static class ServiceCategoryTable extends BaseTable {
		/**
		 * 名称
		 */
		public final static String NAME = "name";
		public final static String LEVEL = "level";
		public final static String STATUS = "status";
		public final static String SERIALNUM = "serialnum";

		@Override
		public String getTableName() {
			return TABLE_ServiceCategory;
		}


		@Override
		public String getBaseSqlString() {
			String sql = super.getBaseSqlString() + ","
				+ ServiceCategoryTable.NAME + " " + " TEXT,"
				+ ServiceCategoryTable.LEVEL + " " + " INTEGER,"
				+ ServiceCategoryTable.STATUS + " " + " INTEGER,"
				+ ServiceCategoryTable.SERIALNUM + " " + " INTEGER";
			return sql;
		}
	}

	//    地区字典数据库结构
	public static class ServiceRegionTable extends BaseTable {
		/**
		 * 名称
		 */
		public final static String NAME = "name";
		public final static String STATUS = "status";


		@Override
		public String getTableName() {
			return TABLE_REGION;
		}

		@Override
		public String getBaseSqlString() {
			String sql = super.getBaseSqlString() + ","
				+ ServiceRegionTable.NAME + " " + "TEXT,"
				+ ServiceRegionTable.STATUS + " " + "INTEGER";
			return sql;
		}
	}

	//    购物车项目结构
	public static class ShopCarItemTable extends BaseTable {
		/**
		 * 名称
		 */
		public final static String NAME = "name";
		public final static String PRICE = "price";
		public final static String NUM = "num";
		public final static String IMAGE = "image";
		public final static String CLICK = "click";
		public final static String DELCLICK = "delclick";
		public final static String PAYITEM = "payitem";
		public final static String GROUPTIMES = "groupTimes";
		public final static String GROUPPRICE = "groupPrice";

		@Override
		public String getTableName() {
			return TABLE_SHOPCARITEM;
		}

		@Override
		public String getBaseSqlString() {
			String sql = super.getBaseSqlString() + ","
				+ ShopCarItemTable.NAME + " " + "TEXT,"
				+ ShopCarItemTable.PRICE + " " + "INTEGER,"
				+ ShopCarItemTable.NUM  + " " + "INTEGER,"
				+ ShopCarItemTable.IMAGE  + " " + "TEXT,"
				+ ShopCarItemTable.PAYITEM  + " " + "INTEGER DEFAULT 0,"
				+ ShopCarItemTable.CLICK  + " " + "INTEGER,"
				+ ShopCarItemTable.DELCLICK  + " " + "INTEGER DEFAULT 0,"
				+ ShopCarItemTable.GROUPTIMES  + " " + "INTEGER DEFAULT 0,"
				+ ShopCarItemTable.GROUPPRICE  + " " + "INTEGER DEFAULT 0";
			return sql;
		}
	}


	//    购物车优惠券结构
	public static class ShopCarCouponTable extends BaseTable {
		/**
		 * 名称
		 */
		public final static String NAME = "name";
		public final static String PRICE = "price";
		public final static String ENDTIME = "endtime";
		public final static String ONITEMID = "onitemid";
		public final static String CLICK = "click";


		@Override
		public String getTableName() {
			return TABLE_SHOPCARCOUPON;
		}

		@Override
		public String getBaseSqlString() {
			String sql = super.getBaseSqlString() + ","
				+ ShopCarCouponTable.NAME + " " + "TEXT,"
				+ ShopCarCouponTable.PRICE + " " + "INTEGER,"
				+ ShopCarCouponTable.ENDTIME + " " + "INTEGER,"
				+ ShopCarCouponTable.ONITEMID + " " + "INTEGER,"
				+ ShopCarCouponTable.CLICK + " " + "INTEGER";
			return sql;
		}
	}


}
