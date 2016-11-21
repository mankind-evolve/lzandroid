package com.lazy.android.z_sample.divview.addressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lazy.android.R;

import java.util.ArrayList;

/**
 * Created by chenglei on 16/9/15.
 */
public class ResultListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<lzcitylist_Model_City> results = new ArrayList<lzcitylist_Model_City>();

	public ResultListAdapter(Context context, ArrayList<lzcitylist_Model_City> results) {
		inflater = LayoutInflater.from(context);
		this.results = results;
	}

	@Override
	public int getCount() {
		return results.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.lzcitylsit_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView
				.findViewById(R.id.name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.name.setText(results.get(position).getName());
		return convertView;
	}

	class ViewHolder {
		TextView name;
	}
}
