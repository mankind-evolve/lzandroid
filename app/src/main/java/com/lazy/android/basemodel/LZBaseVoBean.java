package com.lazy.android.basemodel;

import com.lazy.android.basefunc.LZJson.LZJson;

/**
 * Created by Administrator on 2016/2/1.
 * Email: 187228131@qq.com
 */
public class LZBaseVoBean {



//	Bean -> JsonString
	public String toJson(){
		LZJson lzJson = new LZJson();
		String json = lzJson.toJson(this);
		return json;
	}

//	JsonString -> Bean
	public LZBaseVoBean toObject(String json){
		LZJson lzJson = new LZJson();
		LZBaseVoBean object = (LZBaseVoBean) lzJson.fromJson(json, this);
		return object;
	}





}
