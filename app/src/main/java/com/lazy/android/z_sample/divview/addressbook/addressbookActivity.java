package com.lazy.android.z_sample.divview.addressbook;

import android.content.Context;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;
import com.lazy.android.z_sample.divview.citylist.lzcitylist_PingYinUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by chenglei on 16/9/29.
 */
@ContentView(R.layout.lz_divview_activity_addressbook)
public class addressbookActivity extends LZBaseActivity implements AbsListView.OnScrollListener {

	private BaseAdapter adapter;
	private ResultListAdapter resultListAdapter;
	private TextView overlay; // 对话框首字母textview

	@ViewInject(R.id.list_view)
	private ListView personList;
	@ViewInject(R.id.sh)
	private EditText sh;
	@ViewInject(R.id.tv_noresult)
	private TextView tv_noresult;
	@ViewInject(R.id.search_result)
	private ListView resultList;
	@ViewInject(R.id.MyLetterListView01)
	private lzcitylist_MyLetterListView letterListView;// A-Z listview


	private String[] sections;// 存放存在的汉语拼音首字母
	private Handler handler;
	private OverlayThread overlayThread; // 显示首字母对话框
	private HashMap<String, Integer> alphaIndexer = new HashMap<String, Integer>();// 存放存在的汉语拼音首字母和与之对应的列表位置
	private ArrayList<lzcitylist_Model_City> allLzcitylistModelCity_lists = new ArrayList<lzcitylist_Model_City>(); // 所有城市列表
	private ArrayList<lzcitylist_Model_City> lzcitylistModelCity_result = new ArrayList<lzcitylist_Model_City>();  //搜索结果城市
	private boolean isScroll = false;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();
		initdata();
		sh.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString() == null || "".equals(s.toString())) {
					letterListView.setVisibility(View.VISIBLE);
					personList.setVisibility(View.VISIBLE);
					resultList.setVisibility(View.GONE);
					tv_noresult.setVisibility(View.GONE);
				} else {
					lzcitylistModelCity_result.clear();
					letterListView.setVisibility(View.GONE);
					personList.setVisibility(View.GONE);
					getResultCityList(s.toString());
					if (lzcitylistModelCity_result.size() <= 0) {
						tv_noresult.setVisibility(View.VISIBLE);
						resultList.setVisibility(View.GONE);
					} else {
						tv_noresult.setVisibility(View.GONE);
						resultList.setVisibility(View.VISIBLE);
						resultListAdapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		handler = new Handler();
		overlayThread = new OverlayThread();
		personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				ToastShow(position + "");

			}
		});
		personList.setAdapter(adapter);
		personList.setOnScrollListener(this);
		letterListView.setOnTouchingLetterChangedListener(new lzcitylist_MyLetterListView.OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				isScroll = false;
				if (alphaIndexer.get(s) != null) {
					int position = alphaIndexer.get(s);
					personList.setSelection(position);
					overlay.setText(s);
					overlay.setVisibility(View.VISIBLE);
					handler.removeCallbacks(overlayThread);
					// 延迟一秒后执行，让overlay为不可见
					handler.postDelayed(overlayThread, 1000);
				}
			}
		});
		resultListAdapter = new ResultListAdapter(this, lzcitylistModelCity_result);
		resultList.setAdapter(resultListAdapter);
		resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), lzcitylistModelCity_result.get(position).getName(), Toast.LENGTH_SHORT).show();
			}
		});
		initOverlay();

	}

	//	初始化页面数据
	private void initdata() {
		allLzcitylistModelCity_lists = (ArrayList<lzcitylist_Model_City>) getPhoneNumberFromMobile(this);
//		数据排序
		Collections.sort(allLzcitylistModelCity_lists, comparator);
//		数据布局
		adapter = new ListAdapter(this, allLzcitylistModelCity_lists);
		personList.setAdapter(adapter);
	}


	/**
	 * a-z排序
	 */
	@SuppressWarnings("rawtypes")
	Comparator comparator = new Comparator<lzcitylist_Model_City>() {
		@Override
		public int compare(lzcitylist_Model_City lhs, lzcitylist_Model_City rhs) {
			String a = lhs.getPinyin().substring(0, 1);
			String b = rhs.getPinyin().substring(0, 1);
			int flag = a.compareTo(b);
			if (flag == 0) {
				return a.compareTo(b);
			} else {
				return flag;
			}
		}
	};


	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
//		super.initCommonHead();
//		mCommonHead.setLeftLayoutVisible(true);
//		mCommonHead.setMiddleTitle("通讯录");
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {
			isScroll = true;
		}
	}

//	滑动显示首字母对话框
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (!isScroll) {
			return;
		}
		if (mReady) {
			String text;
			String name = allLzcitylistModelCity_lists.get(firstVisibleItem).getName();
			String pinyin = allLzcitylistModelCity_lists.get(firstVisibleItem).getPinyin();
			text = lzcitylist_PingYinUtil.converterToFirstSpell(pinyin).substring(0, 1).toUpperCase();
			overlay.setText(text);
			overlay.setVisibility(View.VISIBLE);
			handler.removeCallbacks(overlayThread);
			// 延迟一秒后执行，让overlay为不可见
			handler.postDelayed(overlayThread, 1000);
		}
		Log.i("chenglei","firstVisibleItem=" + firstVisibleItem + "|visibleItemCount=" + visibleItemCount + "|totalItemCount=" + totalItemCount);
	}


	// 设置overlay不可见
	private class OverlayThread implements Runnable {
		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}
	}





	// 初始化汉语拼音首字母弹出提示框
	private boolean mReady;

	private void initOverlay() {
		mReady = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.lzcitylsit_overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.TYPE_APPLICATION,
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}


	private class ResultListAdapter extends BaseAdapter {
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
				convertView = inflater.inflate(R.layout.lz_studentlist_item, null);
				viewHolder = new ViewHolder();
				viewHolder.name = (TextView) convertView.findViewById(R.id.studentlist_name);
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


	public class ListAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<lzcitylist_Model_City> list;
		final int VIEW_TYPE = 5;

		public ListAdapter(Context context, List<lzcitylist_Model_City> list) {
			this.inflater = LayoutInflater.from(context);
			this.list = list;
			this.context = context;
			sections = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				// 当前汉语拼音首字母
				String currentStr = getAlpha(list.get(i).getPinyin());
				// 上一个汉语拼音首字母，如果不存在为" "
				String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1).getPinyin()) : " ";
				if (!previewStr.equals(currentStr)) {
					String name = getAlpha(list.get(i).getPinyin());
					alphaIndexer.put(name, i);
					sections[i] = name;
				}
			}
		}

		@Override
		public int getViewTypeCount() {
			return VIEW_TYPE;
		}

		@Override
		public int getItemViewType(int position) {
//			return position < 4 ? position : 4;
			return 4;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		ViewHolder holder;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.lz_item_phoneonly, null);
				holder = new ViewHolder();
				holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.phone = (TextView) convertView.findViewById(R.id.phone);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (position >= 0) {
				holder.name.setText(list.get(position).getName());
				holder.phone.setText(list.get(position).getTel());
				String currentStr = getAlpha(list.get(position).getPinyin());
				String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getPinyin()) : " ";
//					设置首字母拼音
				if (!previewStr.equals(currentStr)) {
					holder.alpha.setVisibility(View.VISIBLE);
					holder.alpha.setText(currentStr);
				} else {
					holder.alpha.setVisibility(View.GONE);
				}
			}

			return convertView;
		}

		private class ViewHolder {
			TextView alpha; // 首字母标题
			TextView name; // 名字
			TextView phone;
		}
	}


	// 获得汉语拼音首字母
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式，判断首字母是否是英文字母
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else if (str.equals("0")) {
			return "定位";
		} else if (str.equals("1")) {
			return "最近";
		} else if (str.equals("2")) {
			return "热门";
		} else if (str.equals("3")) {
			return "全部";
		} else {
			return "#";
		}
	}


	//	从数据库中筛选出结果数据库
//	@SuppressWarnings("unchecked")
	private void getResultCityList(String keyword) {
		char[] str = keyword.toCharArray();
		String key = "^";
		for (int i = 0; i < str.length; i++) {
			key += (str[i] + "").toLowerCase() + ".*";
		}
		ToastShow(key);
		for (lzcitylist_Model_City item : allLzcitylistModelCity_lists) {
			if (item.getPinyin().matches(key) || item.getName().matches(key)) {
				lzcitylistModelCity_result.add(item);
			}
		}
		Collections.sort(lzcitylistModelCity_result, comparator);
	}


	//	读取通讯录里面的联系人数据
	private List<lzcitylist_Model_City> list;
	public List<lzcitylist_Model_City> getPhoneNumberFromMobile(Context context) {
		// TODO Auto-generated constructor stub
		list = new ArrayList<lzcitylist_Model_City>();
		Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		//moveToNext方法返回的是一个boolean类型的数据
		while (cursor.moveToNext()) {
			//读取通讯录的姓名
			String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			//读取通讯录的号码
			String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			lzcitylist_Model_City phoneInfo = new lzcitylist_Model_City();
			phoneInfo.setName(name);
			phoneInfo.setTel(number);
			lzcitylist_Model_City.addpy(phoneInfo);
			list.add(phoneInfo);
		}
		return list;
	}


}
