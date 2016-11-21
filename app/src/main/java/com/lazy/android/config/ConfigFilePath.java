package com.lazy.android.config;

import android.os.Environment;

/**
 * @ClassName: LZConfig_FilePath
 * @Description: 文件路径配置
 * @author
 * @date
 * 
 */
public class ConfigFilePath {
//	本地存储根目录
	public static final String FILE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/"+ConfigSystem.PACKAGE_NAME  ;
//	public static final String FILE_ROOT = "/sdcard/xiaobaiyangchong" ;
	/**媒体存储根目录*/
	public static final String FILE_MEDIA = FILE_ROOT + "/files/media";
	/**图片存储根目录*/
	public static final String FILE_IMAGE = FILE_ROOT + "/files/image";
	/**shared目录*/
	public static final String FILE_SHARED = FILE_ROOT +  "/files/shared_prefs";
	/**缓存文件临时路径*/
	public static final String FILE_CACHE = FILE_ROOT +  "/cache";
	/**数据库文件临时路径*/
	public static final String FILE_DATABASE = FILE_ROOT +  "/files/databases";
	/**crash信息文件名*/
	public static final String FILE_CRASH =  FILE_ROOT + "/files/crash/crash.txt";


}