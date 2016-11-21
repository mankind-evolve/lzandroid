package com.lazy.android.basefunc.LZUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.lazy.android.basefunc.LZLogger.Logger;

/**
 * @ClassName: Base64Helper
 * @Description: Base64编码与解码
 * @author
 * @date
 * 
 */
public class UtilsBase64 {
	public final static String TAG = "Base64Helper";
	private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			.toCharArray();
	/**
	* @Title: encode
	* @Description: 字节数组编码成base64字符串
	* @param bytes
	* @return
	* String
	*/
	public static String encode(byte[] bytes) {
		int start = 0;
		int len = bytes.length;
		StringBuffer buffer = new StringBuffer(bytes.length * 3 / 2);

		int end = len - 3;
		int index = start;
		int num = 0;

		while (index <= end) {
			int data = ((((int) bytes[index]) & 0x0ff) << 16)
					| ((((int) bytes[index + 1]) & 0x0ff) << 8)
					| (((int) bytes[index + 2]) & 0x0ff);

			buffer.append(legalChars[(data >> 18) & 63]);
			buffer.append(legalChars[(data >> 12) & 63]);
			buffer.append(legalChars[(data >> 6) & 63]);
			buffer.append(legalChars[data & 63]);

			index += 3;
			if (num++ >= 14) {
				num = 0;
				buffer.append(" ");
			}
		}

		if (index == start + len - 2) {
			int data = ((((int) bytes[index]) & 0x0ff) << 16)
					| ((((int) bytes[index + 1]) & 255) << 8);

			buffer.append(legalChars[(data >> 18) & 63]);
			buffer.append(legalChars[(data >> 12) & 63]);
			buffer.append(legalChars[(data >> 6) & 63]);
			buffer.append("=");
		} else if (index == start + len - 1) {
			int data = (((int) bytes[index]) & 0x0ff) << 16;

			buffer.append(legalChars[(data >> 18) & 63]);
			buffer.append(legalChars[(data >> 12) & 63]);
			buffer.append("==");
		}

		return buffer.toString();
	}	
	
	/**
	* @Title: decode
	* @Description: base64字符串解码成字节数组
	* @param str
	* @return byte[]
	*/
	public static byte[] decode(String str) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			decode(str, bos);
		} catch (IOException e) {
			Logger.getInstance(TAG).debug(e.getMessage());
			throw new RuntimeException();
		}
		byte[] decodedBytes = bos.toByteArray();
		try {
			bos.close();
			bos = null;
		} catch (IOException e) {
			Logger.getInstance(TAG).debug(e.getMessage());
		}
		return decodedBytes;
	}
	
	private static int decode(char ch) {
		if (ch >= 'A' && ch <= 'Z') {
			return ((int) ch) - 65;
		} else if (ch >= 'a' && ch <= 'z') {
			return ((int) ch) - 97 + 26;
		} else if (ch >= '0' && ch <= '9') {
			return ((int) ch) - 48 + 26 + 26;
		} else {
			switch (ch) {
			case '+':
				return 62;
			case '/':
				return 63;
			case '=':
				return 0;
			default:
				throw new RuntimeException("unexpected code: " + ch);
			}
		}
	}
	
	private static void decode(String str, OutputStream os) throws IOException {
		int index = 0;
		int len = str.length();

		while (true) {
			while (index < len && str.charAt(index) <= ' ') {
				index++;
			}
			if (index == len) {
				break;
			}
			int tri = (decode(str.charAt(index)) << 18)
					+ (decode(str.charAt(index + 1)) << 12)
					+ (decode(str.charAt(index + 2)) << 6)
					+ (decode(str.charAt(index + 3)));

			os.write((tri >> 16) & 255);
			if (str.charAt(index + 2) == '=') {
				break;
			}
			os.write((tri >> 8) & 255);
			if (str.charAt(index + 3) == '=') {
				break;
			}
			os.write(tri & 255);
			index += 4;
		}
	}
}
