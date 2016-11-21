package com.lazy.android.z_sample.divview.netlist;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lazy.android.R;
import com.lazy.android.baseui.adapter.LZAdapter;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by chenglei on 16/6/17.
 */
public class itemAdapter extends LZAdapter<itemmodel> {

	private ListItemViewHolder viewHolder;

	public itemAdapter(Context context, int layoutId, List<itemmodel> array) {
		super(context, layoutId, array);

	}

	@Override
	public Object createViewHolder(View view, itemmodel data) {
		if (view.getTag() == null) {
			viewHolder = new ListItemViewHolder();
			viewHolder.itemHeadimg = (ImageView) view.findViewById(R.id.divview_netlist_item_headimg);
			viewHolder.itemNickname = (TextView) view.findViewById(R.id.divview_netlist_item_text);
		}
		return viewHolder;
	}

	@Override
	public void bindView(itemmodel data, int position, View view) {
		viewHolder = (ListItemViewHolder) getViewHolder(view, data);
		viewHolder.itemNickname.setText(data.getNickname());
		String imageUrl = data.getHeadimg();


		ImageOptions imageOptions = new ImageOptions.Builder()
			// 加载中或错误图片的ScaleType
			//.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
			// 默认自动适应大小
			// .setSize(...)
			.setIgnoreGif(false)
			// 如果使用本地文件url, 添加这个设置可以在本地文件更新后刷新立即生效.
			//.setUseMemCache(false)
			.setImageScaleType(ImageView.ScaleType.CENTER).build();
		x.image().bind(viewHolder.itemHeadimg, imageUrl, imageOptions);


	}



	public class ListItemViewHolder{
		private ImageView itemHeadimg;
		private TextView itemNickname;
	}


}
