package com.lazy.android.basefunc.storage;



import com.lazy.android.basefunc.LZUtils.UtilsStringNum;

import java.io.File;
import java.io.InputStream;

/**
 * @ClassName: FileCache
 * @Description: 文件缓存类(从服务端下载的图片文件使用它存成文件)
 * @author
 * @date
 * 
 */
public class FileCache {
	private static final String TAG = "FileCache";
	private final static String TYPE_JPG = "_j";
	private final static String TYPE_PNG = "_p";
	private final static String EXT_JPG = ".jpg";
	private static final String EXT_PNG = ".png";

	private static FileCache mFileCache = null;

	private String mCachePath = null;
	private String mCacheEmojiPackPath = null;

	private FileCache() {
		mCachePath = FileHelper.getCacheFileRoot();
		FileHelper.createFolder(mCachePath + ".nomedia/");
		FileHelper.createFolder(mCacheEmojiPackPath + ".nomedia/");
	}

	public static FileCache getInstance() {
		if (mFileCache == null) {
			mFileCache = new FileCache();
		}
		return mFileCache;
	}

//	文件名  md5 32位加密
	private String makeFileName(String uri) {
		return UtilsStringNum.md5Hash(uri);
	}
	/**
	 * @Title: getCacheFile
	 * @Description: 得到缓存文件
	 * @param uri
	 * @return String 缓存文件路径，null：文件不存在
	 */
	public String getCacheFile(String uri) {
		if (FileHelper.sdCardExist()) {
			String fileName = mCachePath + makeFileName(uri);
			File file = new File(fileName);
			if (file != null && file.exists()) {
				file.setLastModified(System.currentTimeMillis());
				return fileName;
			}
		}
		return null;
	}

	/**
	 * @Title: writeCacheFile
	 * @Description: 生成缓存文件
	 * @param uri
	 * @param bytes
	 * @return boolean
	 */
	public boolean writeCacheFile(String uri, byte[] bytes) {
		if (bytes == null || !FileHelper.sdCardExist()) {
			return false;
		}
		String fileName = mCachePath + makeFileName(uri);
		boolean result = FileHelper.writeFile(fileName, bytes);
		return result;
	}	
	/**
	 * @Title: writeCacheFile
	 * @Description: 生成缓存文件
	 * @param uri 路径
	 * @param in 输入流
	 * @return boolean
	 */
	public boolean writeCacheFile(String uri, InputStream in) {
		if (in == null || !FileHelper.sdCardExist()) {
			return false;
		}
		String fileName = mCachePath + makeFileName(uri);
		return FileHelper.writeFile(fileName, in);
	}

	/**
	 * @Title: deleteCacheFile
	 * @Description: 删除缓存文件
	 * @param uri 要删除的文件uri
	 * @return void
	 */
	public void deleteCacheFile(String uri) {
		if (FileHelper.sdCardExist()) {
			String fileName = mCachePath + makeFileName(uri);
			FileHelper.deleteFile(fileName);
		}
	}
}
