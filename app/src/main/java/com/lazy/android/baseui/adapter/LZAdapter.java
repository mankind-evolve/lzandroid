package com.lazy.android.baseui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class LZAdapter<T> extends BaseAdapter {
	private static final String TAG = "CMAdapter";
	protected LayoutInflater mInflater;
	protected int mLayoutId;
	protected List<T> mArray;
	protected Context mContext;
	protected View.OnClickListener mClickListener = null;
	protected String prev = "";

	/** 是否存在第一个条目不相同布局 */
	private boolean isHaveTopView = false;


	public LZAdapter(Context context, int layoutId, List<T> array) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mLayoutId = layoutId;
		mArray = array;
	}

	public boolean isHaveTopView() {
		return isHaveTopView;
	}

	public void setHaveTopView(boolean isHaveTopView) {
		this.isHaveTopView = isHaveTopView;
	}

	@Override
	public int getCount() {
		if (isHaveTopView) {
			return mArray.size() + 1;
		}
		return mArray.size();
	}

	@Override
	public T getItem(int position) {
		if (position >= mArray.size()) {
			position = mArray.size() - 1;
		}
		return mArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = createView(position);
		}
		if (isHaveTopView) {
			bindView(getItem(position - 1), position, view);
		} else {
			bindView(getItem(position), position, view);
		}
		return view;
	}

	@Override
	public boolean isEnabled(int position) {
		if (position >= mArray.size()) {
			return false;
		}
		return super.isEnabled(position);
	}
	/**
	 * 创建ViewHolder视图。子类实现此方法创建具体的ViewHolder
	 * @param view
	 * @param data
	 * @return
	 */
	public abstract Object createViewHolder(View view, T data);
	/**
	 * 给ViewHolder视图绑定值。子类实现此方法
	 * @param data
	 * @param position
	 * @param view
	 */
	public abstract void bindView(T data, int position, View view);

	public void getFirstData() {

	}

	public void getData() {

	}
	
	public void setData(List<T> dataList) {
		mArray = dataList;
	}
	
	public int getLayoutId() {
		return mLayoutId;
	}

	public Object getViewHolder(View view, T data) {
		Object holder = (Object) view.getTag();
		if (holder == null) {
			holder = createViewHolder(view, data);
			view.setTag(holder);
		}
		return holder;
	}

	/**
	 * @Title: updateData
	 * @Description: 更新数据
	 * @param aArray
	 * @param refreshed
	 *            是否要刷新view界面
	 * @return void
	 */
	public void updateData(List<T> aArray, boolean refreshed) {
        mArray = aArray;
		if (refreshed) {
			notifyDataSetChanged();
		}
	}
	
	protected View createView(int position) {
		return mInflater.inflate(mLayoutId,null);
	}

	/**
	 * @Title: bindClicker
	 * @Description: 将view的onclick绑定到单击事件处理器
	 * @param data
	 *            View的getTag()函数将返回此数据
	 * @param v
	 * @return void
	 */
	protected void bindClicker(T data, View v) {
		v.setTag(data);
		v.setOnClickListener(mClickListener);
	}
    protected void bindClicker(int position, View v) {
		v.setTag(position);
		v.setOnClickListener(mClickListener);
	}

	/**
	 * @Title: setOnClickerListener
	 * @Description: 单击事件处理器
	 * @param listener
	 * @return void
	 */
	public void setOnClickerListener(View.OnClickListener listener) {
		mClickListener = listener;
	}


}
