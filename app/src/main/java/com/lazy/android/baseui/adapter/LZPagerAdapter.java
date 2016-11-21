package com.lazy.android.baseui.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


public abstract class LZPagerAdapter<T> extends PagerAdapter {
	protected int mLayoutId;
	protected Context mContext;
	protected List<T> mArray;
	protected float mPreviewCount;
	
	private float mPageWidth;

	/**
	 * 
	 * @param context
	 * @param layoutId 布局id
	 * @param previewCount 要显示的预览个数，通常传1
	 * @param array list数据
	 */
	public LZPagerAdapter(Context context, int layoutId, int previewCount, List<T> array) {
		super();
		this.mContext = context;
		this.mLayoutId = layoutId;
		this.mPreviewCount = previewCount;
		this.mPageWidth = 1f / (float) previewCount;
		this.mArray = array;
	}
	
	public int getLayoutId() {
		return mLayoutId;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// 处理越界
		if (mArray.size() >= position) {
			T data = mArray.get(position);
			return getView(container, data, position);
		} else {
			return null;
		}
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewGroup) container).removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == (View) object;
	}

	@Override
	public int getCount() {
		return mArray != null ? mArray.size() : 0;
	}
	
	@Override
	public float getPageWidth(int position) {
		return mPageWidth;
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
	}

	/**
	 * 子类实现此方法填充条目
	 * @param container
	 * @param data
	 * @param position
	 * @return
	 */
	public abstract Object getView(ViewGroup container, T data, int position);
}
