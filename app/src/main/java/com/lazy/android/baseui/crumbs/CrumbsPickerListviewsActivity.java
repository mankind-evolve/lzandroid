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
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenglei on 16/4/15. 使用方法
 * 传两个参数  一个是标题 一个是数据源，返回选择的position

 ArrayList<String> data = MConfigStaticType.getConfigSubject(getApplicationContext());
 Intent intent = new Intent(this, CrumbsPickerListviewsActivity.class);
 intent.putExtra("data", data);
 intent.putExtra("title", "辅导内容 ");
 startActivityForResult(intent, 3);


 在onActivityResult方法中处理

 ArrayList<Integer> seleresult =  data.getIntegerArrayListExtra(CrumbsPickerListviewsActivity.INTENT_KEY);

 返回的是ArrayList<Integer> seleresult
 */

@ContentView(R.layout.lz_listviewsdialog)
public class CrumbsPickerListviewsActivity extends LZBaseActivity {

	@ViewInject(R.id.listviewdialog_text) TextView listviewdialog_text;
	@ViewInject(R.id.listviewdialog_lv) ListView listviewdialog_lv;

	public  static int RESULT_CODE = 1;
	public final static String INTENT_KEY = "result";
	private ArrayList<String> PLANETS;
	private ArrayList<Map<String,String>> data = new ArrayList<Map<String,String>>();
	private String title;
	private CrumbsPickerListviewsAdapter adapter;
	private ArrayList<Integer> selected = new ArrayList<>();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initdata();
		listviewdialog_text.setText(title);
		adapter = new CrumbsPickerListviewsAdapter(this,R.layout.lz_item_crumbslistviews,PLANETS,selected);
		listviewdialog_lv.setAdapter(adapter);
		listviewdialog_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				adapter.addin(position);
				adapter.notifyDataSetChanged();
			}
		});


	}

	//	初始化数据源
	private void initdata() {
		Intent intent = getIntent();
//    获得数据源和标题
		PLANETS = (ArrayList<String>) intent.getSerializableExtra("data");
		title = intent.getStringExtra("title");
		selected = intent.getIntegerArrayListExtra("select");
		for (int i = 0; i < PLANETS.size(); i++) {
			HashMap<String,String> map = new HashMap();
			map.put("name",PLANETS.get(i));
			data.add(map);
		}
	}

//	点击确定，返回数据
	@Event(R.id.listviewdialog_button)
	private void listviewdialog_button_Event(View view){
		String result = adapter.getPositions();
		ArrayList<Integer> resultint = new ArrayList<>();
		if(result != null && !result.equals("")){
			String[] selects = result.split(",");
			for(String item : selects){
				resultint.add(Integer.valueOf(item));
			}
		}
		Intent intent = new Intent();
		intent.putIntegerArrayListExtra(INTENT_KEY, resultint);
		setResult(RESULT_CODE, intent);
		getActivity().finish();
	}


	@Event(R.id.listviewdialog_ll)
	private void listviewdialog_ll_Event(View v){
		this.finish();
	}

}
