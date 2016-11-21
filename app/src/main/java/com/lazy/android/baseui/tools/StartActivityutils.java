package com.lazy.android.baseui.tools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class StartActivityutils {
//	单纯跳转
	public static void startActivityByIntent(Context context, Class clazz) {
		Intent intent = new Intent(context, clazz);
		context.startActivity(intent);
	}
	
//	携带int型数据跳转
	public static void startActivityTakeData(Context context,Class clazz,String key,int value){
		Intent intent = new Intent(context,clazz);
		Bundle bundle = new Bundle();
		bundle.putInt(key, value);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	
//	携带String型数据跳转
	public static void startActivityTakeData(Context context,Class clazz,String key,String value){
		Intent intent = new Intent(context,clazz);
		Bundle bundle = new Bundle();
		bundle.putString(key, value);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	
	
	
}
