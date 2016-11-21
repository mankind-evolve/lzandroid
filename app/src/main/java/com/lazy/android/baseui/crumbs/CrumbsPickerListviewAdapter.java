package com.lazy.android.baseui.crumbs;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lazy.android.R;
import com.lazy.android.baseui.adapter.LZAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by chenglei on 16/4/15. 使用方法
 * 传两个参数  一个是标题 一个是数据源，返回选择的position
 * Intent intent = new Intent(this, CrumbsListviewActivity.class);
 * intent.putExtra("data",pettypename);
 * intent.putExtra("title","选择品种");
 * startActivityForResult(intent, CrumbsListviewActivity.RESULT_CODE);
 * 在onActivityResult方法中处理
 * if(resultCode == CrumbsListviewActivity.RESULT_CODE){
 * int position = data.getIntExtra(CrumbsListviewActivity.INTENT_KEY,0);
 * }
 */

public class CrumbsPickerListviewAdapter extends LZAdapter {
//	记录是否选中的数据
	private CrumbsListViewViewHolder viewHolder;
	private int selected_postion;

	public CrumbsPickerListviewAdapter(Context context, int layoutId, List array) {
		super(context, layoutId, array);
	}

	public CrumbsPickerListviewAdapter(Context context, int layoutId, List array, int selected_postion) {
		super(context, layoutId, array);
		this.selected_postion = selected_postion;
	}

	@Override
	public Object createViewHolder(View view, Object data) {
		if (view.getTag() == null) {
			viewHolder = new CrumbsListViewViewHolder();
			viewHolder.m_item_text_bordbottom_text = (TextView) view.findViewById(R.id.m_item_text_bordbottom_text);
			viewHolder.m_item_text_bordbottom_select = (TextView) view.findViewById(R.id.m_item_text_bordbottom_select);
		}
		return viewHolder;
	}

	@Override
	public void bindView(Object data, int position, View view) {
		viewHolder = (CrumbsListViewViewHolder) getViewHolder(view, data);
		viewHolder.m_item_text_bordbottom_text.setText(data.toString());
		if(position == selected_postion){
			viewHolder.m_item_text_bordbottom_select.setVisibility(View.VISIBLE);
		}else{
			viewHolder.m_item_text_bordbottom_select.setVisibility(View.INVISIBLE);
		}
	}

	public class CrumbsListViewViewHolder {
		private TextView m_item_text_bordbottom_select;
		private TextView m_item_text_bordbottom_text;
	}
}
