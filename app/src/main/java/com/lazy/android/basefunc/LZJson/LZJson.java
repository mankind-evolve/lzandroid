package com.lazy.android.basefunc.LZJson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Administrator on 2016/1/26.
 * Email: 187228131@qq.com
 * used: xutils
 */

public final class LZJson {

	/**
	 * Object -> jsonString
	 */

	public String toJson(Object object) {
		Gson gson = new Gson();
		String json = gson.toJson(object);
		return json;
	}

	public String toJson(List<Object> list) {
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}


	/**
	 * jsonString -> Object
	 */
	public Object fromJson(String string, Object object) {
		Gson gson = new Gson();
		Object toObject = gson.fromJson(string, object.getClass());
		return toObject;
	}



	public List<Object> fromJson(String string, List<Object> list) {
		Gson gson = new Gson();
//		List<Object> result = new ArrayList<>();
//		try {
//			JSONArray arraylist = new JSONArray(string);
//			for(int i = 0 ; i < arraylist.length() ; i ++ ){
//				Object object = fromJson(arraylist.get(i).toString(),list.get(i));
//				Log.i("chenglei","object=" + object.toString());
//				result.add(object);
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		List<Object> toList = gson.fromJson(string, new TypeToken<List<Object>>() {}.getType());
		for(int i = 0; i < toList.size() ; i++)
		{
			Object p = toList.get(i);
		}
		return toList;
	}


}
