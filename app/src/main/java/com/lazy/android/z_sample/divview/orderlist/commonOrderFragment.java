package com.lazy.android.z_sample.divview.orderlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by chenglei on 16/10/4.
 */
@ContentView(R.layout.lz_common_fragment)
public class commonOrderFragment extends LZBaseFragment {

	@ViewInject(R.id.tag) private TextView tag;


	public static commonOrderFragment newInstance(String tag) {
		Bundle args = new Bundle();
		args.putString("tag",tag);
		commonOrderFragment fragment = new commonOrderFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String tagtext = getArguments().get("tag").toString();
		tag.setText(tagtext);
	}


}
