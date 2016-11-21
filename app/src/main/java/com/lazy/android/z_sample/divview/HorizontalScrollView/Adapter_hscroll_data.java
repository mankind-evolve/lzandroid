package com.lazy.android.z_sample.divview.HorizontalScrollView;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lazy.android.R;
import com.lazy.android.baseui.adapter.LZAdapter;

import java.util.List;

/**
 * Created by chenglei on 16/8/31.
 */
public class Adapter_hscroll_data extends LZAdapter {

	private DatalistAdapterViewHolder viewHolder;

	public Adapter_hscroll_data(Context context, int layoutId, List array) {
		super(context, layoutId, array);

	}

	@Override
	public Object createViewHolder(View view, Object data) {
		if (view.getTag() == null) {
			viewHolder = new DatalistAdapterViewHolder();
			viewHolder.lz_hscrollitem_img = (ImageView) view.findViewById(R.id.lz_hscrollitem_img);
		}
		return viewHolder;
	}

	@Override
	public void bindView(Object data, int position, View view) {
		viewHolder = (DatalistAdapterViewHolder) getViewHolder(view, data);
		viewHolder.lz_hscrollitem_img.setImageResource((int)data);
	}



	public class DatalistAdapterViewHolder {
		private ImageView lz_hscrollitem_img;
	}

}
