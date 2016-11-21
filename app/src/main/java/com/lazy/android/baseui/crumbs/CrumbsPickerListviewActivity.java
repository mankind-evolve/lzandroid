package com.lazy.android.baseui.crumbs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenglei on 16/4/15. 使用方法
 * 传两个参数  一个是标题 一个是数据源，返回选择的position

    Intent intent = new Intent(this, CrumbsPickerListviewActivity.class);
    intent.putExtra("data",pettypename);
    intent.putExtra("title","选择品种");
    startActivityForResult(intent, CrumbsPickerListviewActivity.RESULT_CODE);

 * 在onActivityResult方法中处理
	 if(data != null){
	 	if(resultCode == CrumbsPickerListviewActivity.RESULT_CODE){
	 		int position = data.getIntExtra(CrumbsPickerListviewActivity.INTENT_KEY,0);
	 	}
	 }
 */

@ContentView(R.layout.lz_listviewdialog)
public class CrumbsPickerListviewActivity extends LZBaseActivity {

	public  static int RESULT_CODE = 0;
	public final static String INTENT_KEY = "result";
	@ViewInject(R.id.listviewdialog_text) TextView listviewdialog_text;
	@ViewInject(R.id.listviewdialog_lv) ListView listviewdialog_lv;

	private ArrayList<String> PLANETS;
	private ArrayList<Map<String,String>> data = new ArrayList<Map<String,String>>();
	private int selectedpostion = -1;
	private String title;
	private CrumbsPickerListviewAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initdata();
		listviewdialog_text.setText(title);
		adapter = new CrumbsPickerListviewAdapter(this,R.layout.lz_item_crumbslistview,PLANETS,selectedpostion);
		listviewdialog_lv.setAdapter(adapter);
		listviewdialog_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra(INTENT_KEY, position);
				setResult(RESULT_CODE, intent);
				getActivity().finish();
			}
		});
	}

	//	初始化数据源
	private void initdata() {
		Intent intent = getIntent();
//    获得数据源和标题
		PLANETS = (ArrayList<String>) intent.getSerializableExtra("data");
		title = intent.getStringExtra("title");
		selectedpostion = intent.getIntExtra("selected",-1);
		for (int i = 0; i < PLANETS.size(); i++) {
			HashMap<String,String> map = new HashMap();
			map.put("name",PLANETS.get(i));
			data.add(map);
		}
	}

}
