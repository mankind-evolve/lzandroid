package com.lazy.android.basefunc.LZUtils;

import java.io.File;

/**
 * Created by chenglei on 16/9/8.
 */
public class UtilsFiles {

//	检测文件是否存在
	public static boolean fileIsExists(String path){
		try{
			File f=new File(path);
			if(!f.exists()){
				return false;
			}

		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
}
