package com.lazy.android.baseui.common;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lazy.android.R;
import com.lazy.android.baseui.adapter.LZAdapter;

import java.util.List;

/**
 * Created by chenglei on 16/8/31.
 */
public class CommonAdapterTextonly extends LZAdapter {

	private BuyhistoryAdapterViewHolder viewHolder;

	public CommonAdapterTextonly(Context context, int layoutId, List array) {
		super(context, layoutId, array);

	}

	@Override
	public Object createViewHolder(View view, Object data) {
		if (view.getTag() == null) {
			viewHolder = new BuyhistoryAdapterViewHolder();
			viewHolder.item_textonly = (TextView) view.findViewById(R.id.item_textonly);
		}
		return viewHolder;
	}

	@Override
	public void bindView(Object data, int position, View view) {
		viewHolder = (BuyhistoryAdapterViewHolder) getViewHolder(view, data);
		viewHolder.item_textonly.setText(data.toString());
	}



	public class BuyhistoryAdapterViewHolder {
		private TextView item_textonly;
	}

}
