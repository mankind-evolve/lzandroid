package com.lazy.android.basefunc.LZUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lazy.android.basefunc.LZLogger.Logger;

import android.text.TextUtils;

/**
 * @ClassName: StringHelper
 * @Description: 字符串相关的工具类
 * @author
 * 
 */
public class UtilsStringNum {
	public final static String TAG = "StringHelper";
	private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * @Title: jsonNullToEmpty
	 * @Description: json解析结果的null转成空字符
	 * @param str
	 * @return String
	 */
	public static String jsonNullToEmpty(String str) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		if ("null".equalsIgnoreCase(str)) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * @Title: isEmail
	 * @Description: 是否是有效的email地址
	 * @param email
	 * @return boolean
	 */
	public static boolean isEmail(String email) {
		Pattern p = Pattern
				.compile("^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * @Title: isMobilePhone
	 * @Description: 是否是有效的手机号码

	 * @return boolean
	 */
	public static final boolean isMobilePhone(String mobile) {
		if (TextUtils.isEmpty(mobile)) {
			return false;
		}
		if (mobile.startsWith("+86")) {
			mobile = mobile.substring(3);

		} else if (mobile.startsWith("+86")) {
			mobile = mobile.substring(2);
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * 检查是否是全数字
	 * 
	 * @Title: isMobilePhone
	 * @Description: TODO
	 * @param mobile
	 * @return
	 */
	public static final boolean isNumber(String mobile) {
		if (TextUtils.isEmpty(mobile)) {
			return false;
		}
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * 检查是否是 6~20位数字、字母或下划线
	 * 
	 * @Title: isMobilePhone
	 * @Description: TODO
	 * @param mobile
	 * @return
	 */
	public static final boolean isWeixin(String mobile) {
		if (TextUtils.isEmpty(mobile)) {
			return false;
		}
		Pattern p = Pattern.compile("[a-zA-Z0-9_]{6,20}");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * @Title: isUrl
	 * @Description: 是否是有效的网址
	 * @param url
	 * @return boolean
	 */
	public static final boolean isUrl(String url) {
		// ^(http(s)?://)?([\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$
		// ^http\://[a-zA-Z0-9\-\.]+\.[a-zA-Z]{2,3}(/\S*)?$
		// ^(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$
		Pattern p = Pattern
				.compile("^(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$");
		Matcher m = p.matcher(url);
		return m.matches();
	}

	/**
	 * @Title: byte2HexStr
	 * @Description: 字节数组转成16进制串
	 * @param bytes
	 * @return String
	 */
	public static String byte2HexStr(byte[] bytes) {
		if (bytes == null) {
			return "";
		}
		int num = bytes.length;
		char[] chars = new char[num * 2];
		int ch;
		for (int index = 0; index < num; ++index) {
			ch = bytes[index];
			// chars[index * 2] = hexDigits[ch >> 4];
			// chars[index * 2 + 1] = hexDigits[ch & 0xF];
			chars[2 * index] = hexDigits[(ch >> 4) & 0x0f];
			chars[2 * index + 1] = hexDigits[ch & 0x0f];
		}
		return new String(chars);
	}

	/**
	 * @Title: str2HexStr
	 * @Description: 字符串转成16进制字符串
	 * @param str
	 * @return String
	 */
	public static String str2HexStr(String str) {
		if (TextUtils.isEmpty(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder("");
		byte[] bytes = str.getBytes();
		int bit;
		for (int index = 0; index < bytes.length; ++index) {
			bit = (bytes[index] & 0x0f0) >> 4;
			sb.append(hexDigits[bit]);
			bit = bytes[index] & 0x0f;
			sb.append(hexDigits[bit]);
		}
		return sb.toString();
	}

	/**
	 * @Title: hexStr2Bytes
	 * @Description: 16进制字符串转成字节数组
	 * @param hexStr
	 * @return byte[]
	 */
	public static byte[] hexStr2Bytes(String hexStr) {
		if (TextUtils.isEmpty(hexStr)) {
			return null;
		}
		if (hexStr.length() % 2 != 0) {
			return null;
		}
		hexStr = hexStr.toLowerCase();
		int length = hexStr.length() / 2;
		char[] hexChars = hexStr.toCharArray();
		byte[] bytes = new byte[length];
		for (int index = 0; index < length; ++index) {
			int pos = index * 2;
			bytes[index] = (byte) (toByte(hexChars[pos]) << 4 | toByte(hexChars[pos + 1]));
		}
		return bytes;
	}

	/**
	 * @Title: md5Hash
	 * @Description: md5 32位加密
	 * @param str
	 * @return String
	 */
	public static String md5Hash(String str) {
		String hashValue = str;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(str.getBytes());
			byte[] messageDigest = digest.digest();
			hashValue = byte2HexStr(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			Logger.getInstance(TAG).debug(e.getMessage());
		}
		return hashValue;
	}


	/**
	 * @Title: md5Hash
	 * @Description: md5 16位加密
	 * @param str
	 * @return String
	 */
	public static String md5Hash16(String str) {
		String hashValue = str;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(str.getBytes());
			byte[] messageDigest = digest.digest();
			hashValue = byte2HexStr(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			Logger.getInstance(TAG).debug(e.getMessage());
		}
		return  hashValue.toString().substring(8, 24);
	}



	private static byte toByte(char ch) {
		return (byte) "0123456789abcdef".indexOf(ch);
	}

	/**
	 * 将字符串里边的半角字符转为全角 ,解决textview参差不齐的情况
	 * 
	 * @Title: toDBC
	 * @Description: TODO
	 * @param input
	 * @return
	 */
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * @Title: containsNumber
	 * @Description: 是否包含数字【0-9】
	 * @param paramString
	 * @return boolean
	 */
	public static boolean containsNumber(String paramString) {
		// TODO 测试
		String matchStr = ".*\\d+.*";
		return Pattern.compile(matchStr).matcher(paramString).matches();
	}


	/**
	 * 两个数字转换成百分比
	 */
	public static String percentage(long total , long current){
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		String result = numberFormat.format((float) current / (float) total * 100);
		return result;
	}



	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			// 取出每一个字符
			char c = string.charAt(i);
			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}



	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);
			// 追加成string
			string.append((char) data);
		}
		return string.toString();
	}


//	字符串翻转
	public static String reverse4(String s) {
		return new StringBuffer(s).reverse().toString();
	}

}
