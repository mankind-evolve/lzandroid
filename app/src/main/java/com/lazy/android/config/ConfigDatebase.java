package com.lazy.android.config;

import org.xutils.DbManager;

import java.io.File;

/**
 * Created by Administrator on 2016/1/29.
 * Email: 187228131@qq.com
 */
public class ConfigDatebase {
//	数据库文件名
	public static final String DATEBASE_NAME = "LZAndroid.db";


//	数据库存储路径
	public static final String DATEBASE_PATH = ConfigFilePath.FILE_DATABASE;
//	数据库版本号
	public static final int DATEBASE_VERSION = 1;

//	数据库配置对象
	public static final DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
			.setDbName(DATEBASE_NAME)
			.setDbDir(new File(DATEBASE_PATH))
			.setDbVersion(DATEBASE_VERSION)
			.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
				@Override
				public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
					// TODO: ...
					// db.addColumn(...);
					// db.dropTable(...);
					// ...
				}
			});
}

