package com.lazy.android.z_sample.xutils.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/1/15.
 */
@ContentView(R.layout.sample_fragment_viewfragment)
public class ViewFragment extends LZBaseFragment {

	@ViewInject(R.id.viewFragment_text) private TextView viewFragment_text;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		viewFragment_text.setText("修改后的文字");
	}

	@Event(value = R.id.viewFragment_text,type = View.OnClickListener.class)
	private void textEvent(View view){
		Toast.makeText(getBaseActivity(), "this", Toast.LENGTH_SHORT).show();
	}

}
