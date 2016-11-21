package com.lazy.android.z_sample.divview;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.lazy.android.z_sample.divview.netlistWithSwipeMenuListView.NetListSwipeActivity;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.lazy.android.Manifest;
import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;
import com.lazy.android.baseui.common.CustomAlertDialog;
import com.lazy.android.baseui.common.LZDialogDiy;
import com.lazy.android.baseui.crumbs.CrumbsCityPick.CrumbsCityPickActivity;
import com.lazy.android.baseui.crumbs.CrumbsPickerListviewActivity;
import com.lazy.android.baseui.crumbs.CrumbsPickerListviewsActivity;
import com.lazy.android.baseui.crumbs.CrumbsPickerDateActivity;
import com.lazy.android.baseui.crumbs.CrumbsPickerDiyActivity;
import com.lazy.android.z_sample.divview.HorizontalScrollView.Activity_hscroll;
import com.lazy.android.z_sample.divview.activityButtonup.ActivityButtonUpActivity;
import com.lazy.android.z_sample.divview.addressbook.addressbookActivity;
import com.lazy.android.z_sample.divview.circleimageview.CircleImageViewActivity;
import com.lazy.android.z_sample.divview.citylist.lzcitylist_IndexActivity;
import com.lazy.android.z_sample.divview.indexdemo.indexdemoActivity;
import com.lazy.android.z_sample.divview.netlist.NetListActivity;
import com.lazy.android.z_sample.divview.orderlist.orderlistActivity;
import com.lazy.android.z_sample.divview.slidingmenu.SlidingmenuIndexActivity;
import com.lazy.android.z_sample.divview.timelist.timelistActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/3/4.
 */
@ContentView(R.layout.sample_divview_index_activity)
public class DivviewIndexActivity extends LZBaseActivity implements OnDateSetListener {
	private ArrayList<String> selectdata = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();



	}

	@Event(value = R.id.SlidingMenu, type = View.OnClickListener.class)
	private void slidingmenuEvent(View view) {
		startActivity(new Intent(this, SlidingmenuIndexActivity.class));
	}

	@Event(value = R.id.circleimageview, type = View.OnClickListener.class)
	private void circleimageviewEvent(View view) {
		startActivity(new Intent(this, CircleImageViewActivity.class));
	}

	@Event(value = R.id.activity_button_up, type = View.OnClickListener.class)
	private void activity_button_up_Event(View view) {
		startActivity(new Intent(this, ActivityButtonUpActivity.class));
	}

	@Event(R.id.citylist)
	private void citylist_Event(View view) {
		startActivity(new Intent(this, lzcitylist_IndexActivity.class));
	}


	//	简单选择拾取器
	@Event(R.id.sexpick)
	private void sexpick_Event(View view) {
		selectdata.clear();
		selectdata.add("数据1");
		selectdata.add("数据2");
		selectdata.add("数据3");
		selectdata.add("数据4");
		Intent intent = new Intent(this, CrumbsPickerDiyActivity.class);
		intent.putExtra("data", selectdata);
		intent.putExtra("title", "标题");
		startActivityForResult(intent, 0);
	}


	//	返回结果处理
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
//		性别拾取器
			if (requestCode == 0) {
				int position = data.getIntExtra(CrumbsPickerDiyActivity.INTENT_KEY, 0);
				ToastShow(selectdata.get(position));
			}

			if (requestCode == 1) {
				int position = data.getIntExtra(CrumbsPickerDiyActivity.INTENT_KEY, 0);
				ToastShow(position + "");
				selectpostion = position;
			}

			if (requestCode == 2) {
				ArrayList<Integer> selectresult = data.getIntegerArrayListExtra(CrumbsPickerListviewsActivity.INTENT_KEY);
				ToastShow(selectresult.toString());
				this.selectresult = selectresult;
			}
		}
	}


	//	请求网络列表 上拉加载 下拉刷新
	@Event(R.id.netlist)
	private void netlist_click(View view) {
		startActivity(new Intent(this, NetListActivity.class));
	}

//	请求网络列表，带swipe
	@Event(R.id.netlistwithswipe)
	private void netlistwithswipe_click(View view){
		startActivity(new Intent(this, NetListSwipeActivity.class));
	}

	//	横向滚动列表
	@Event(R.id.HorizontalScrollView)
	private void HorizontalScrollView_Event(View view) {
		startActivity(new Intent(this, Activity_hscroll.class));
	}


	//	日期选择器
	@Event(R.id.timepick)
	private void timepick_Event(View view) {
		startActivity(new Intent(this, CrumbsPickerDateActivity.class));
	}


	//	城市选择器
	@Event(R.id.citypick)
	private void citypick_Event(View view) {
		startActivity(new Intent(this, CrumbsCityPickActivity.class));
	}

	//	列表选择器单选
	private int selectpostion = -1;

	@Event(R.id.listpick)
	private void listpick_Event(View view) {
		ArrayList<String> data = new ArrayList<String>(Arrays.asList("item1", "item2", "item3", "item4", "item5", "item5"));
		Intent intent = new Intent(getActivity(), CrumbsPickerListviewActivity.class);
		intent.putExtra("data", data);
		intent.putExtra("title", "标题");
		intent.putExtra("selected", selectpostion);
		startActivityForResult(intent, 1);
	}


	//	列表选择器多选
	ArrayList<Integer> selectresult = new ArrayList<>();
	@Event(R.id.listspick)
	private void listspick_Event(View view) {
		ArrayList<String> data = new ArrayList<String>(Arrays.asList("item1", "item2", "item3", "item4", "item5", "item5"));
		Intent intent = new Intent(this, CrumbsPickerListviewsActivity.class);
		intent.putExtra("data", data);
		intent.putExtra("title", "多选标题");
		intent.putExtra("select",selectresult);
		startActivityForResult(intent, 2);
	}

//	定位城市
	@Event(R.id.baidulocation)
	private void baidulocation_Event(View v){
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			//申请定位权限
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
		} else {
			BaiduMapLocation(true);
		}

	}

	//	横向滚动时间列表
	@Event(R.id.timelist)
	private void timelist_Event(View view) {
		startActivity(new Intent(this, timelistActivity.class));
	}


	int i = 0 ;
	//	通讯录页面
	@Event(R.id.addressbook)
	private void addressbook_Event(View view) {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
			//申请 WRITE_CONTACTS 权限
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS},0);
		} else {
			startActivity(new Intent(this, addressbookActivity.class));
		}
	}


//	自定义dialog
	@Event(R.id.dialog_div)
	private void dialog_div_Event(View view){


		new CustomAlertDialog(this,R.layout.lz_dialog_buy_addedit).show();

//		LZDialogDiy.Builder builder = new LZDialogDiy.Builder(this);
//		builder.create(R.layout.lz_custom_load_dialog);
//		builder.setMessage("这个就是自定义的提示框");
//		builder.setTitle("提示");
//		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				//设置你的操作事项
//			}
//		});
		new LZDialogDiy(this);




	}

	//	订单列表
	@Event(R.id.orderlist)
	private void orderlist_Event(View view) {
		startActivity(new Intent(this, orderlistActivity.class));
	}


	//	时间选择器
	@Event(R.id.timepicker_time)
	private void timepicker_time_Event(View view) {
		long ftenYears = 50L * 365 * 1000 * 60 * 60 * 24L;
		TimePickerDialog mDialogHourMinute = new TimePickerDialog.Builder()
			.setTitleStringId("自定义标题")
//			.setCancelStringId("自定义取消")
//			.setSureStringId("自定义确定")
//			.setYearText("Year")
//			.setMonthText("Month")
//			.setDayText("Day")
//			.setHourText("Hour")
//			.setMinuteText("Minute")
			.setMinMillseconds(System.currentTimeMillis() - ftenYears)
			.setMaxMillseconds(System.currentTimeMillis() + ftenYears)
			.setType(Type.HOURS_MINS)
			.setCallBack(this)

			.setCurrentMillseconds(System.currentTimeMillis())
			.setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
			.setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
			.build();
		mDialogHourMinute.show(getSupportFragmentManager(), "时间选择器");
	}

	//	日期选择器
	@Event(R.id.timepicker_data)
	private void timepicker_data_Event(View view) {
		long ftenYears = 50L * 365 * 1000 * 60 * 60 * 24L;
		TimePickerDialog mDialogHourMinute = new TimePickerDialog.Builder()
			.setTitleStringId("自定义标题")
//			.setCancelStringId("自定义取消")
//			.setSureStringId("自定义确定")
//			.setYearText("Year")
//			.setMonthText("Month")
//			.setDayText("Day")
//			.setHourText("Hour")
//			.setMinuteText("Minute")
			.setMinMillseconds(System.currentTimeMillis() - ftenYears)
			.setMaxMillseconds(System.currentTimeMillis() + ftenYears)
			.setType(Type.YEAR_MONTH_DAY)
			.setCallBack(this)

			.setCurrentMillseconds(System.currentTimeMillis())
			.setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
			.setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
			.build();
		mDialogHourMinute.show(getSupportFragmentManager(), "日期选择器");
	}

	//	详细选择器
	@Event(R.id.timepicker_all)
	private void timepicker_all_Event(View view) {
		long ftenYears = 50L * 365 * 1000 * 60 * 60 * 24L;
		TimePickerDialog mDialogHourMinute = new TimePickerDialog.Builder()
			.setTitleStringId("自定义标题")
//			.setCancelStringId("自定义取消")
//			.setSureStringId("自定义确定")
//			.setYearText("Year")
//			.setMonthText("Month")
//			.setDayText("Day")
//			.setHourText("Hour")
//			.setMinuteText("Minute")
			.setMinMillseconds(System.currentTimeMillis() - ftenYears)
			.setMaxMillseconds(System.currentTimeMillis() + ftenYears)
			.setType(Type.ALL)
			.setCallBack(this)

			.setCurrentMillseconds(System.currentTimeMillis())
			.setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
			.setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
			.build();
		mDialogHourMinute.show(getSupportFragmentManager(), "详细选择器");
	}


	//	跳转首页
	@Event(R.id.indexdemo)
	private void indexdemo_Event(View view) {
		startActivity(new Intent(this, indexdemoActivity.class));
	}

	//	时间选择后回调
	@Override
	public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
		ToastShow(timePickerView.getTag() + "|" + millseconds);
	}


	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(true);
		mCommonHead.setMiddleTitle("自定义界面");
	}


	@Override
	public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 1: {
				if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
					ToastShow("请设置权限");
					Intent intent = new Intent();
					intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
					intent.setData(Uri.fromParts("package", getPackageName(), null));
					startActivity(intent);
				}
				return;
			}
		}
	}
}
