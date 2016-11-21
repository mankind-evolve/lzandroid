package com.lazy.android.basefunc.storage;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * @ClassName: MediaStoreHelper
 * @Description: 媒体库操作类
 * @author liubing
 * 
 */
public class MediaStoreHelper {
	public static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

	/**
	 * @Title: insertImage
	 * @Description: 将图片文件插入到媒体库中
	 * @param cr
	 * @param name
	 *            图片标题
	 * @param displayName
	 *            显示名称
	 * @param dateTaken
	 *            时间戳
	 * @param mimeType
	 *            图片文件MIME
	 * @param filePath
	 *            图片文件路径
	 * @return Uri 返回图片对应的Uri
	 */
	public static Uri insertImage(ContentResolver cr, String name, String displayName, long dateTaken, String mimeType,
			String filePath) {
		ContentValues values = new ContentValues(7);
		values.put(MediaStore.Images.Media.TITLE, name);
		values.put(MediaStore.Images.Media.DISPLAY_NAME, displayName);
		values.put(MediaStore.Images.Media.DATE_TAKEN, dateTaken);
		values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);// "image/jpeg"
		values.put(MediaStore.Images.Media.DATA, filePath);
		return cr.insert(IMAGE_URI, values);
		// Uri uri = null;
		// try {
		// uri =
		// Uri.parse(MediaStore.Images.Media.insertImage(cr,filePath,name,displayName));
		// } catch (FileNotFoundException e) {
		// }
		// return uri;
	}
	/**
	 * @Title: insertImage
	 * @Description: 将图片文件插入到媒体库中
	 * @param cr
	 * @param name
	 *            图片标题
	 * @param displayName
	 *            显示名称
	 * @param fileName
	 *            文件名
	 * @param mimeType
	 *            图片文件MIME
	 * @param filePath
	 *            图片文件路径
	 * @return Uri 返回图片对应的Uri
	 */
	public static Uri insertImage(ContentResolver cr, String name, String displayName, String fileName, String mimeType, String filePath) {
		ContentValues values = new ContentValues(7);
		values.put(MediaStore.Images.Media.TITLE, name);
		values.put(MediaStore.Images.Media.DISPLAY_NAME, displayName);
		values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, fileName);
		values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);// "image/jpeg"
		values.put(MediaStore.Images.Media.DATA, filePath);
		return cr.insert(IMAGE_URI, values);
		// Uri uri = null;
		// try {
		// uri =
		// Uri.parse(MediaStore.Images.Media.insertImage(cr,filePath,name,displayName));
		// } catch (FileNotFoundException e) {
		// }
		// return uri;
	}
	/**
	 * @Title: getAllImage
	 * @Description: 得到手机媒体库中的所有图片文件
	 * @param cr
	 * @return ArrayList<Uri> 图片文件的uri列表(结果按照图片入库的时间倒序排列)
	 */
	/*
	 * public static ArrayList<Uri> getAllImage(ContentResolver cr) { String[]
	 * projection = { MediaStore.Images.Media.DATA }; String sortOrder =
	 * MediaStore.Images.Media._ID + " desc"; Cursor cur = cr.query(IMAGE_URI,
	 * projection, null, null, sortOrder); ArrayList<Uri> uriList = null; if
	 * (cur != null) { uriList = new ArrayList<Uri>(); while (cur.moveToNext())
	 * { String fileName = cur.getString(0); uriList.add(Uri.parse(fileName)); }
	 * cur.close(); } return uriList; }
	 */
	/**
	 * @Title: getAllImage
	 * @Description: 得到手机媒体库中的所有图片文件
	 * @param cr
	 * @return ArrayList<String> 图片文件的pathName列表(结果按照图片入库的时间倒序排列)
	 */

	public static ArrayList<String> getAllImage(ContentResolver cr) {
		String[] projection = { MediaStore.Images.Media.DATA };
		String sortOrder = MediaStore.Images.Media._ID + " desc";
		Cursor cur = cr.query(IMAGE_URI, projection, null, null, sortOrder);
		ArrayList<String> pathNameList = new ArrayList<String>();
		if (cur != null) {
			pathNameList = new ArrayList<String>();
			while (cur.moveToNext()) {
				String fileName = cur.getString(0);
				pathNameList.add(fileName);
			}
			cur.close();
		}
		return pathNameList;
	}

	/**
	 * @Title: getImagePath
	 * @Description: 通过媒体库Uri得到真实路径
	 * @param cr
	 * @param uri
	 *            媒体库Uri
	 * @return String uri的真实路径
	 */
	public static String getImagePath(ContentResolver cr, Uri uri) {
		String result = null;
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cur = cr.query(uri, projection, null, null, null);
		if (cur != null) {
			int columnIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cur.moveToFirst();
			result = cur.getString(columnIndex);
			cur.close();
		}
		return result;
	}

	/**
	* @Title: getIntentImagePath
	* @Description:通过intent获取图片的uri。搜索顺序为：媒体数据库，文件，最后是uri.getPath()返回的路径
	* @param cr 
	* @param uri
	* @return
	* String
	 */
	public static String getIntentImagePath(ContentResolver cr, Uri uri) {
		// 获取图片地址：由于有第三方媒体库所以返回的Data，有两种结果 1:真是路径 2:媒体库数据库Uri 优先判断是否为数据库Uri
		if (uri == null) {
			return null;
		}
		String u = MediaStoreHelper.getImagePath(cr, uri);
		if (u != null) {
			if (FileHelper.fileExists(u)) {
				return u;
			}
		}
		return uri.getPath();
	}
}
