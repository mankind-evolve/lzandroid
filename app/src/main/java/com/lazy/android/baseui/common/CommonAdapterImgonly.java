package com.lazy.android.baseui.common;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.lazy.android.R;
import com.lazy.android.baseui.adapter.LZAdapter;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by chenglei on 16/8/31.
 */
public class CommonAdapterImgonly extends LZAdapter {

	private CommonAdapterImgonlyViewHolder viewHolder;
	private ImageOptions imageOptions = new ImageOptions.Builder()
		.setSize(DensityUtil.dip2px(150), DensityUtil.dip2px(150))
		.setRadius(DensityUtil.dip2px(5))
		// 如果ImageView的大小不是定义为wrap_content, 不要crop.
		.setCrop(true)
		// 加载中或错误图片的ScaleType
		//.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
//			.setLoadingDrawableId(R.mipmap.ic_launcher)
//			.setFailureDrawableId(R.mipmap.ic_launcher)
		.build();


	public CommonAdapterImgonly(Context context, int layoutId, List array) {
		super(context, layoutId, array);

	}

	@Override
	public Object createViewHolder(View view, Object data) {
		if (view.getTag() == null) {
			viewHolder = new CommonAdapterImgonlyViewHolder();
			viewHolder.item_imgonly = (ImageView) view.findViewById(R.id.item_imgonly);
		}
		return viewHolder;
	}

	@Override
	public void bindView(Object data, int position, View view) {
		viewHolder = (CommonAdapterImgonlyViewHolder) getViewHolder(view, data);
		String url = data.toString();
		if(!url.equals("first")){
			if(url.startsWith("http")){
				x.image().bind(viewHolder.item_imgonly,url,imageOptions);
			}else{
				viewHolder.item_imgonly.setImageBitmap(BitmapFactory.decodeFile(url));
			}
		}else{
			viewHolder.item_imgonly.setImageResource(R.drawable.lz_eduinfo_add);
		}
//		viewHolder.item_imgonly.setText(data.toString());
	}



	public class CommonAdapterImgonlyViewHolder {
		private ImageView item_imgonly;
	}

}
