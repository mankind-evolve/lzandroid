package com.lazy.android.basefunc.LZUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.lazy.android.basefunc.LZLogger.Logger;


/**
 * @ClassName: CompressHelper
 * @Description: zip压缩与解压缩
 * @author
 * @date
 * 
 */
public final class UtilsCompress {
	public final static String TAG = "CompressHelper";
	private final static int BUFFER_SIZE = 1024;
	private final static String DEFAULT_CHARSET = "utf-8";

	public static byte[] compress(byte[] input) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		BufferedOutputStream bufos = null;
		byte[] retval = null;
		try {
			bufos = new BufferedOutputStream(new GZIPOutputStream(bos));
			bufos.write(input);
			retval = bos.toByteArray();
		} catch (IOException e) {
			Logger.getInstance(TAG).debug(e.getMessage(), e);
		} finally {
			if (bufos != null) {
				try {
					bufos.close();
				} catch (IOException e1) {
					Logger.getInstance(TAG).debug(e1.getMessage(), e1);
				}
			}
			try {
				bos.close();
			} catch (IOException e1) {
				Logger.getInstance(TAG).debug(e1.getMessage(), e1);
			}
		}
		return retval;
	}

	public static byte[] compress(String str) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		BufferedOutputStream bufos = null;
		byte[] retval = null;
		try {
			bufos = new BufferedOutputStream(new GZIPOutputStream(bos));
			bufos.write(str.getBytes());
			retval = bos.toByteArray();
		} catch (IOException e) {
			Logger.getInstance(TAG).debug(e.getMessage(), e);
		} finally {
			if (bufos != null) {
				try {
					bufos.close();
				} catch (IOException e) {
					Logger.getInstance(TAG).debug(e.getMessage(), e);
				}
			}
			try {
				bos.close();
			} catch (IOException e) {
				Logger.getInstance(TAG).debug(e.getMessage(), e);
			}
		}
		return retval;
	}	

	public static String unCompress2String(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		BufferedInputStream bufis = new BufferedInputStream(new GZIPInputStream(bis));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[BUFFER_SIZE];
		int len;
		while ((len = bufis.read(buf)) > 0) {
			bos.write(buf, 0, len);
		}

		String retval = bos.toString();
		bis.close();
		bufis.close();
		bos.close();
		return retval;
	}

	public static byte[] unCompress(byte[] bytes) throws IOException {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			BufferedInputStream bufis = new BufferedInputStream(
					new GZIPInputStream(bis));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[BUFFER_SIZE];
			int len;
			while ((len = bufis.read(buf)) > 0) {
				bos.write(buf, 0, len);
			}
			return bos.toByteArray();
		} catch (java.lang.OutOfMemoryError e) {
			Logger.getInstance(TAG).debug(e.getMessage());
			return null;
		}
	}	
	
	/**
	 * @Title: compress2HexStr
	 * @Description: 压缩成16进制字符串
	 * @param str
	 * @return String
	 */
	public static String compress2HexStr(String str) {
		byte[] bytes = compress(str);
		return UtilsStringNum.byte2HexStr(bytes);
	}

	/**
	 * @Title: unCompress2String
	 * @Description: 解压成字符串
	 * @param hexStr 16进制字串
	 * @return String
	 * @throws IOException
	 */
	public static String unCompressFromHexStr(String hexStr) throws IOException {		
		byte[] bytes = UtilsStringNum.hexStr2Bytes(hexStr);
		return unCompress2String(bytes);
	}
	
	/**
	 * 解压缩存成独立的文件。解压文件是个耗时操作，最好放到线程里调用
	 * @param zipFile 压缩文件名
	 * @param destDir 解压后文件存放路径(文件夹)
	 * @throws IOException
	 */
	public static void unZipFile(String zipFile, String destDir) throws IOException {
		FileInputStream fis = new FileInputStream(zipFile);
		doUnZipFile(fis, destDir);
	}
	
	/**
	 * 解压缩存成独立的文件。解压文件是个耗时操作，最好放到线程里调用
	 * @param bytes 压缩文件流
	 * @param destDir 解压后文件存放路径(文件夹)
	 * @throws IOException
	 */
	public static void unZipFile(byte[] bytes, String destDir) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		doUnZipFile(bis, destDir);
	}
	
	private static void doUnZipFile(InputStream is, String destDir) throws FileNotFoundException, IOException {
		ZipEntry  entry = null;
		ZipInputStream zis = new ZipInputStream(is);
		while ((entry = zis.getNextEntry()) != null) {
			String fileName = entry.getName();
			if (entry.isDirectory()) {
				fileName = fileName.substring(0, fileName.length() - 1);
                File folder = new File(destDir + File.separator + fileName);
                folder.mkdirs();
            } else {
                File file = new File(destDir + File.separator + fileName);
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((len = zis.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
		}
		is.close();
		zis.close();
	}
}
