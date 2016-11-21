package com.lazy.android.basefunc.encrypt;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2015/4/23.
 */
public class MD5Helper {

//md5类的入口文件
    public static String encode(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            String  type = "32lower";
            if(type.equals("16upper")){
                return getEncode16upper(digest);
            }else if(type.equals("16lower")){
                return  getEncode16lower(digest);
            }else if(type.equals("32upper")){
                return  getEncode32upper(digest);
            }else if(type.equals("32lower")){
                return  getEncode32lower(digest);
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 32位加密大写
     *
     * @param digest
     * @return
     */
    private static String getEncode32upper(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        // return builder.toString().toLowerCase(); // 小写
        return builder.toString().toUpperCase(); // 大写
        // java.lang.String.toUpperCase(Locale locale)
        // 方法将在此字符串中的所有字符为大写的规则给定的Locale.
        // return builder.toString().toUpperCase(Locale.getDefault()); // 大写
//        return builder.toString();
    }




    /**
     * 32位加密大写
     *
     * @param digest
     * @return
     */
    private static String getEncode32lower(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString().toLowerCase(); // 小写
        // return builder.toString().toUpperCase(); // 大写
        // java.lang.String.toUpperCase(Locale locale)
        // 方法将在此字符串中的所有字符为大写的规则给定的Locale.
        // return builder.toString().toUpperCase(Locale.getDefault()); // 大写
//        return builder.toString();
    }





    /**
     * 16位加密大写
     *
     * @param digest
     * @return
     */
    private static String getEncode16upper(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        // 16位加密，从第9位到25位
//        return builder.substring(8, 24).toString().toLowerCase();  //小写
		return builder.substring(8, 24).toString();   //大写
    }



    /**
     * 16位加密小写
     *
     * @param digest
     * @return
     */
    private static String getEncode16lower(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        // 16位加密，从第9位到25位
        return builder.substring(8, 24).toString().toLowerCase();  //小写
//        return builder.substring(8, 24).toString();   //大写
    }



}
