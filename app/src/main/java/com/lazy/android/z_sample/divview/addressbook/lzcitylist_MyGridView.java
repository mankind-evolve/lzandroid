package com.lazy.android.z_sample.divview.addressbook;

import android.widget.GridView;

public class lzcitylist_MyGridView extends GridView {
	public lzcitylist_MyGridView(android.content.Context context,
								 android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 *
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}