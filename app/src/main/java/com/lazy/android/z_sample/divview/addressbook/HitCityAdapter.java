package com.lazy.android.z_sample.divview.addressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lazy.android.R;

import java.util.List;

/**
 * Created by chenglei on 16/9/15.
 */
public class HitCityAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<String> hotCitys;

	public HitCityAdapter(Context context, List<String> hotCitys) {
		this.context = context;
		inflater = LayoutInflater.from(this.context);
		this.hotCitys = hotCitys;
	}

	@Override
	public int getCount() {
		return hotCitys.size();
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
		convertView = inflater.inflate(R.layout.lzcitylsit_item_city, null);
		TextView city = (TextView) convertView.findViewById(R.id.city);
		city.setText(hotCitys.get(position));
		return convertView;
	}
}
