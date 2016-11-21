package com.lazy.android.baseui.crumbs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lazy.android.R;
import com.lazy.android.baseui.adapter.LZAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

public class CrumbsPickerListviewsAdapter extends LZAdapter {
//	记录是否选中的数据
	private ArrayList<HashMap> data = new ArrayList<HashMap>();
	private CrumbsListViewsViewHolder viewHolder;
	private String positions = "";

	public CrumbsPickerListviewsAdapter(Context context, int layoutId, List array) {
		super(context, layoutId, array);
		addarr((ArrayList<String>) array);
	}

	public CrumbsPickerListviewsAdapter(Context context, int layoutId, List array, ArrayList<Integer> selected) {
		super(context, layoutId, array);
		addarr((ArrayList<String>) array);
		for(int i = 0 ; i < data.size() ; i ++){
			for(Integer item:selected){
				if(i == item){
					data.get(i).put("selected","1");
				}
			}
		}
		for(int i = 0 ; i < data.size() ; i ++){
			if(data.get(i).get("selected").equals("1")){
				if(positions.equals("")){
					positions +=  i;
				}else{
					positions += "," +  i;
				}
			}
		}
	}

	public void addarr(ArrayList<String> stringarr){
		for(int i = 0 ; i < stringarr.size() ; i++){
			HashMap<String,String> hashMap = new HashMap<>();
			hashMap.put("title",stringarr.get(i).toString());
			hashMap.put("selected","0");
			data.add(hashMap);
		}
	}

	public void adddata(ArrayList<HashMap> datas){
		data =  datas;
	}

	@Override
	public Object createViewHolder(View view, Object data) {
		if (view.getTag() == null) {
			viewHolder = new CrumbsListViewsViewHolder();
			viewHolder.m_item_crumbslistviews_ico = (TextView) view.findViewById(R.id.m_item_crumbslistviews_ico);
			viewHolder.m_item_crumbslistviews_text = (TextView) view.findViewById(R.id.m_item_crumbslistviews_text);
		}
		return viewHolder;
	}

	//	是否为选中状态
	private Boolean isin(int position) {
		if(data.get(position).get("selected").equals("1")){
			return true;
		}
		return false;
	}

	public String getPositions() {
		return positions;
	}


	//	点击事件
	public void addin(int position) {
		if(isin(position)){
			data.get(position).put("selected","0");
		}else{
			data.get(position).put("selected","1");
		}
		positions = "";
		for(int i = 0 ; i < data.size() ; i ++){
			if(data.get(i).get("selected").equals("1")){
				if(positions.equals("")){
					positions +=  i;
				}else{
					positions += "," +  i;
				}
			}
		}
	}

	@Override
	public void bindView(Object data, int position, View view) {
		viewHolder = (CrumbsListViewsViewHolder) getViewHolder(view, data);
		viewHolder.m_item_crumbslistviews_text.setText(data.toString());
		if (isin(position)) {
			viewHolder.m_item_crumbslistviews_ico.setVisibility(View.VISIBLE);
		} else {
			viewHolder.m_item_crumbslistviews_ico.setVisibility(View.INVISIBLE);
		}
	}

	public class CrumbsListViewsViewHolder {
		private TextView m_item_crumbslistviews_ico;
		private TextView m_item_crumbslistviews_text;
	}
}
