package com.lazy.android.basefunc.storage;

import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.lazy.android.config.ConfigFilePath;
import com.lazy.android.basefunc.LZLogger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName: FileHelper
 * @Description: 文件辅助类
 * @author
 * 
 */
public class FileHelper {
	private final static String TAG = "FileHelper";
	private static int BUFFER_SIZE = 1024;
	private static Map<String, String> mimeMap;
	
	static {
		mimeMap = new HashMap<String, String>();
		mimeMap.put("gz", "application/x-gzip");
		mimeMap.put("z", "application/x-compress");
		mimeMap.put("zip", "application/x-zip-compressed");
		
		mimeMap.put("txt", "text/plain");
		mimeMap.put("pdf", "application/pdf");
		
		mimeMap.put("jpg", "image/jpeg");
		mimeMap.put("jpeg", "image/jpeg");
		mimeMap.put("jpe", "image/jpeg");
		mimeMap.put("png", "image/png");
		mimeMap.put("gif", "image/gif");
		
		mimeMap.put("mp2", "audio/x-mpeg");
		mimeMap.put("mp3", "audio/mpeg");
		mimeMap.put("wav", "audio/x-wav");
		mimeMap.put("aac", "audio/aac");
		mimeMap.put("m4a", "audio/m4a");
		
		mimeMap.put("mpe", "video/mpeg");
		mimeMap.put("mpeg", "video/mpeg");
		mimeMap.put("mpg", "video/mpeg");
		mimeMap.put("mp4", "video/mp4");
		mimeMap.put("ogv", "video/ogv");
		mimeMap.put("3gp", "video/3gp");
		mimeMap.put("webm", "video/webm");
	}
	/**
	 * @Title: createFolder
	 * @Description: 新建文件夹
	 * @param folderPath
	 * @return boolean
	 */
	public static boolean createFolder(String folderPath) {
//		if (!sdCardExist()) {
//			return false;
//		}
		if (!TextUtils.isEmpty(folderPath)) {
			File dir = new File(folderPath);
			if (dir != null && !dir.exists()) {
				return dir.mkdirs();
			}
		}
		return true;
	}
	/**
	 * @Title: deleteFile
	 * @Description: 删除单个文件
	 * @param file
	 * @return void
	 */
	public static void deleteFile(String file) {
		if (!sdCardExist()) {
			return;
		}
		File f = new File(file);
		if (f != null && f.exists()) {
			f.delete();
		}
	}
	/**
	 * @Title: deleteFolder
	 * @Description: 删除文件夹(递归删除此文件下所有文件)
	 * @param rootFolderPath
	 * @return void
	 */
	public static void deleteFolder(String rootFolderPath) {
		if (!sdCardExist()) {
			return;
		}
		File f = new File(rootFolderPath);
		if (f == null || !f.exists()) {
			return;
		}
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			for (int index = 0; index < fs.length; index++) {
				if (fs[index].isDirectory()) {
					deleteFolder(fs[index].getAbsolutePath());
				} else {
					fs[index].delete();
				}
			}
			f.delete();
		} else {
			f.delete();
		}
	}
	/**
	 * 文件是否存在
	 * @param fileFullName
	 * @return
	 */
	public static boolean fileExists(String fileFullName) {
		if (!sdCardExist()) {
			return false;
		}
		File f = new File(fileFullName);
		return f != null && f.exists();
	}
	/**
	 * @Title: getRootDir
	 * @Description: app文件根目录
	 * @return String
	 */
	public static String getRootDir() {
		if (sdCardExist()) {
			String root = null;
			File sdDir = null;
			sdDir = Environment.getExternalStorageDirectory();
			root = sdDir.toString() + ConfigFilePath.FILE_ROOT;
			boolean ret = createFolder(root);
			return ret ? root : null;
		} else {
			String root = ConfigFilePath.FILE_ROOT;
			if(createFolder(root)) {
				return root;
			} else {
				return null;
			}
		}
	}
	/**
	 * @Title: getCacheFileRoot
	 * @Description: app缓存文件根目录
	 * @return String
	 */
	public static String getCacheFileRoot() {
		String root = getRootDir();
		if (!TextUtils.isEmpty(root)) {
			root += ConfigFilePath.FILE_ROOT;
			File f = new File(root);
			if (!f.exists()) {
				f.mkdirs();
			}
		}
		return root;
	}
	/**
	 * 获取媒体库根目录
	 * @return
	 */
	public static String getSysDcmiRoot() {		
		if (sdCardExist()) {
			String root = null;
			File sdDir = null;
			sdDir = Environment.getExternalStorageDirectory();
			root = sdDir.toString() + File.separator + ConfigFilePath.FILE_MEDIA;
			boolean ret = createFolder(root);
			return ret ? root : null;
		} else {
			String root = ConfigFilePath.FILE_MEDIA;
			if(createFolder(root)) {
				return root;
			} else {
				return null;
			}
		}
	}	
	/**
	 * @Title: getSysDcmiFullPath
	 * @Description: 媒体库文件的全路径名称
	 * @param fileName
	 * @return String
	 */
	public static String getSysDcmiFullPath(String fileName) {
		String rootPath = getSysDcmiRoot();
		if (!TextUtils.isEmpty(rootPath)) {
			return rootPath + File.separator +fileName;
		} else {
			return null;
		}
	}
	/**
	 * @Title: getCacheFileFullPath
	 * @Description: 缓存文件名
	 * @param fileName
	 * @return String
	 */
	public static String getCacheFileFullPath(String fileName) {
		String rootPath = getCacheFileRoot();
		if (!TextUtils.isEmpty(rootPath)) {
			return rootPath + File.separator +fileName;
		} else {
			return null;
		}
	}
	/**
	 * @Title: getFileName
	 * @Description: 从全路径中得到文件名
	 * @param fullPath
	 * @return String
	 */
	public static String getFileName(String fullPath) {
		if (TextUtils.isEmpty(fullPath)) {
			return fullPath;
		}
		String fileName = "";
		int lastSeperator = fullPath.lastIndexOf('/');
		if (lastSeperator >= 0 && fullPath.length() > lastSeperator + 1) {
			fileName = fullPath.substring(lastSeperator + 1);
		} else {
			fileName = fullPath;
		}
		return fileName;
	}
	/**
	 * @Title: getFileExtension
	 * @Description: 取文件扩展名
	 * @param fullPath
	 * @return String
	 */
	public static String getFileExtension(String fullPath) {
		if (TextUtils.isEmpty(fullPath)) {
			return fullPath;
		}
		int pos = fullPath.lastIndexOf('.');
		if ((pos > 0) && (pos < (fullPath.length() - 1))) {
			return fullPath.substring(pos + 1);
		}
		return fullPath;
	}
	/**
	 * @Title: sdCardExist
	 * @Description: 检测手机是否存在sd card
	 * @return boolean
	 */
	public static boolean sdCardExist() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		return sdCardExist;
	}
	/**
	 * @Title: getMIME
	 * @Description: 根据文件扩展名返回对应的MIME
	 * @param filePath
	 *            文件名
	 * @return String
	 */
	public static String getMIME(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return "application/octet-stream";
		}
		int pos = filePath.lastIndexOf(".");
		if (pos > 0 && pos <= (filePath.length() - 1)) {
			String extension = filePath.substring(pos + 1).toLowerCase();
			if (mimeMap.containsKey(extension)) {
				return mimeMap.get(extension);
			} else {
				return "application/octet-stream";
			}
		} else {
			return "application/octet-stream";
		}
	}
	/**
	 * @Title: isImageFile
	 * @Description: 是否是图片文件
	 * @param filePath
	 * @return boolean
	 */
	public static boolean isImageFile(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return false;
		}
		int pos = filePath.lastIndexOf(".");
		if (pos > 0 && pos <= (filePath.length() - 1)) {
			String extension = filePath.substring(pos + 1).toLowerCase();
			if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("jpe")) {
				return true;
			} else if (extension.equals("png")) {
				return true;
			} else if (extension.equals("gif")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	/**
	 * @Title: readStreamToByte
	 * @Description: 读文件
	 * @param is
	 * @return byte[]
	 */
	public static byte[] readStreamToByte(InputStream is) {
		byte[] result = null;
		try {
			result = new byte[is.available()];
			is.read(result);
		} catch (IOException e) {
			Logger.getInstance(TAG).debug(e.getMessage(), e);
			return null;
		}
		return result;
	}
	/**
	 * @Title: getFileBytes
	 * @Description: 读文件
	 * @param fileUri
	 * @return byte[]
	 */
	public static byte[] getFileBytes(Uri fileUri) {
		if (fileUri == null) {
			return null;
		}
		return getFileBytes(fileUri.getPath());
	}
	/**
	 * @Title: getFileBytes
	 * @Description: 读取文件字节流
	 * @param fullPath
	 * @return byte[]
	 */
	public static byte[] getFileBytes(String fullPath) {
		if (TextUtils.isEmpty(fullPath)) {
			return null;
		}
		byte[] result = null;
		try {
			InputStream is = new FileInputStream(new File(fullPath));
			result = readStreamToByte(is);
			is.close();
		} catch (FileNotFoundException e) {
			Logger.getInstance(TAG).debug(e.getMessage(), e);
		} catch (IOException e) {
			Logger.getInstance(TAG).debug(e.getMessage(), e);
		}
		return result;
	}
	/**
	 * @Title: writeFile
	 * @Description: 写文件
	 * @param fullPath
	 * @param bytes
	 * @return boolean
	 */
	public static boolean writeFile(String fullPath, byte[] bytes) {
		boolean result = false;
		try {
			FileOutputStream os = new FileOutputStream(fullPath);
			os.write(bytes, 0, bytes.length);
			os.close();
			result = true;
		} catch (Exception e) {
			result = false;
			Logger.getInstance(TAG).debug(e.getMessage(), e);
		}
		return result;
	}
	/**
	 * @Title: writeFile
	 * @Description: 写文件
	 * @param fullPath
	 * @param in
	 * @return boolean
	 */
	public static boolean writeFile(String fullPath, InputStream in) {
		boolean result = false;
		int len = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		try {
			FileOutputStream os = new FileOutputStream(fullPath);
			while ((len = in.read(buffer)) > 0) {
				os.write(buffer, 0, len);
			}
			os.close();
			result = true;
		} catch (Exception e) {
			result = false;
			Logger.getInstance(TAG).debug(e.getMessage(), e);
		}
		return result;
	}
	/**
	 * @Title: rename
	 * @Description: 修改文件名
	 * @param oldFullFilename
	 * @param newFilename
	 * @return
	 */
	public static boolean rename(String oldFullFilename, String newFilename) {
		File oldfile = new File(oldFullFilename);
		String rootPath = oldfile.getParent();
		File newFile = new File(rootPath + File.separator + newFilename);

		if (oldfile.renameTo(newFile)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @Title: copyFile
	 * @Description: 文件复制
	 * @param srcFilePath
	 * @param destFilePath
	 * @return true 成功，false 失败
	 */
	public static boolean copyFile(String srcFilePath, String destFilePath) {
		try {
			File srcFile = new File(srcFilePath);
			File destFile = new File(destFilePath);
			InputStream in = new FileInputStream(srcFile);
			OutputStream out = new FileOutputStream(destFile);

			byte[] buf = new byte[BUFFER_SIZE];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			Logger.getInstance(TAG).debug(e.getMessage(), e);
		} catch (IOException e) {
			Logger.getInstance(TAG).debug(e.getMessage(), e);
		}
		return false;
	}

	public static String convertStreamToString(InputStream is, String encoding) throws IOException {
		String line = null;
		if (is != null) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding));
				line = reader.readLine();
			} finally {
				is.close();
			}
		}
		return (line == null) ? "" : line;
	}
	
	public static String[] listFile(File file ) {
		File[] f = file.listFiles();
		String Path[] = new String[f.length];
		for (int i = 0; i < f.length; i++) {
			Path[i] = f[i].getPath();
		}
		return Path;
	}
}
