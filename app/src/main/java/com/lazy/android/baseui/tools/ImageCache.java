package com.lazy.android.baseui.tools;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageCache {
	private static LruCache<String, Bitmap> cache;
	
	public static void putBitmap(Bitmap bitmap, String imageurl) {
		if (cache == null) {
			long memory = Runtime.getRuntime().maxMemory();
			cache = new LruCache<String, Bitmap>((int) (memory / 8)) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					return value.getRowBytes() * value.getHeight();
				}
			};
		}
		cache.put(imageurl, bitmap);
	}

	public static Bitmap getBitmap(String imageurl) {
		Bitmap bitmap = null;
		if (cache != null) {
			bitmap = cache.get(imageurl);
		}
		return bitmap;
	}

	public static void remove(String uri) {
		Bitmap bitmap = cache.remove(uri);
		if (bitmap != null) {
			bitmap.recycle();
			bitmap = null;
		}
	}
	
	public static void clear() {
		cache.evictAll();
	}
}
