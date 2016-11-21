package com.lazy.android.basefunc.LZUtils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.lazy.android.config.ConfigStaticType.SettingField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author
 * @ClassName: SettingHelper
 * @Description: Shared Preferences读写数据
 */
public final class UtilsShared {
	public final static String TAG = "SettingHelper";
	private final static String DATA_NAME = "lzandroid.dat";
	private final static String SPLITCHAR = ",";

	public static int getInt(Context context, SettingField field, int defaultValue) {
		return context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getInt(field.name(), defaultValue);
	}

	public static void setInt(Context context, SettingField field, int value) {
		Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
		editor.putInt(field.name(), value);
		editor.commit();
	}

	public static String getString(Context context, SettingField field, String defaultValue) {
		return context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getString(field.name(), defaultValue);
	}

	public static void setString(Context context, SettingField field, String value) {
		Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
		editor.putString(field.name(), value);
		editor.commit();
	}

	public static long getLong(Context context, SettingField field, long defaultValue) {
		return context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getLong(field.name(), defaultValue);
	}

	public static void setLong(Context context, SettingField field, long value) {
		Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
		editor.putLong(field.name(), value);
		editor.commit();
	}

	public static boolean getBoolean(Context context, SettingField field, boolean defaultValue) {
		return context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getBoolean(field.name(), defaultValue);
	}

	public static void setBoolean(Context context, SettingField field, boolean value) {
		Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
		editor.putBoolean(field.name(), value);
		editor.commit();
	}

	public static void setArraylong(Context context, SettingField field, ArrayList<Long> arrayLong) {
		if (arrayLong != null && arrayLong.size() > 0) {
			String str = "";
			for (int index = 0, count = arrayLong.size(); index < count; ++index) {
				str = str + arrayLong.get(index).toString() + SPLITCHAR;
			}
			Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
			editor.putString(field.name(), str);
			editor.commit();
		}
	}

	public static void removeKey(Context context, SettingField field) {
		Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
		editor.remove(field.name());
		editor.commit();
	}

	public static ArrayList<Long> getArrayLong(Context context, SettingField field, String defaultValue) {
		ArrayList<Long> arrayLong = new ArrayList<Long>();
		String str = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getString(field.name(), defaultValue);
		if (str != null) {
			String[] ids = str.split(SPLITCHAR);
			for (int index = 0; index < ids.length; ++index) {
				long id = Long.parseLong(ids[index]);
				arrayLong.add(id);
			}
		}
		return arrayLong;
	}

	public static void setArrayString(Context context, SettingField field, ArrayList<String> displayList) {
		String str = "";
		Integer num = 0;
		for (int index = 0, count = displayList.size(); index < count; ++index) {
			str = str + displayList.get(index) + SPLITCHAR;
			num++;
		}
		Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
		editor.putString(field.name(), str);
		editor.commit();
	}

	public static void setMapString(Context context, SettingField field, HashMap<String, Integer> displayList) {
		String str = "";
		if (displayList != null) {
			Collection<String> collection = displayList.keySet();
			Iterator<String> iterator = collection.iterator();
			while (iterator.hasNext()) {
				String value = (String) iterator.next();
				str = str + value + SPLITCHAR;
			}
		}
		Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
		editor.putString(field.name(), str);
		editor.commit();
	}

	public static HashMap<String, Integer> getMapString(Context context, SettingField field, String defaultValue) {
		HashMap<String, Integer> displayList = new HashMap<String, Integer>();
		String str = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getString(field.name(), defaultValue);
		if (str != null) {
			String[] ids = str.split(SPLITCHAR);
			for (int index = ids.length - 1; index >= 0; index--) {
				if (!ids[index].equals(""))
					displayList.put(ids[index], index);
			}
		}
		return displayList;
	}

	public static ArrayList<String> getArrayString(Context context, SettingField field, String defaultValue) {
		ArrayList<String> displayList = new ArrayList<String>();
		String str = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getString(field.name(), defaultValue);
		if (str != null) {
			String[] ids = str.split(SPLITCHAR);
			for (int index = 0; index < ids.length; index++) {
				if (!ids[index].equals(""))
					displayList.add(ids[index]);
			}
		}
		return displayList;
	}

}
