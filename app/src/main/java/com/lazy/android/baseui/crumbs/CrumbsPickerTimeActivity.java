package com.lazy.android.baseui.crumbs;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;

/**
 * Created by chenglei on 16/9/7.
 */


public class CrumbsPickerTimeActivity extends LZBaseActivity implements OnDateSetListener {

	public final static int ALL = 0;
	public final static int YMD = 1;
	public final static int HM = 2;
	public final static int MDHM = 3;
	public final static int YM = 4;
	public final static int Y = 5;

	TimePickerDialog mDialogAll;
	TimePickerDialog mDialogYearMonth;
	TimePickerDialog mDialogYearMonthDay;
	TimePickerDialog mDialogMonthDayHourMinute;
	TimePickerDialog mDialogHourMinute;
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	private int type = ALL;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent() != null) {
			type = getIntent().getIntExtra("type",ALL);
		}

		switch (type){
			case ALL:
				showall();
				break;

		}

	}



//	显示所有
	public void showall(){
		long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
		mDialogAll = new TimePickerDialog.Builder()
			.setCallBack(this)
			.setCancelStringId("取消")
			.setSureStringId("Sure")
			.setTitleStringId("TimePicker")
			.setYearText("Year")
			.setMonthText("Month")
			.setDayText("Day")
			.setHourText("Hour")
			.setMinuteText("Minute")
			.setCyclic(false)
			.setMinMillseconds(System.currentTimeMillis())
			.setMaxMillseconds(System.currentTimeMillis() + tenYears)
			.setCurrentMillseconds(System.currentTimeMillis())
			.setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
			.setType(Type.ALL)
			.setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
			.setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
			.setWheelItemTextSize(12)
			.build();

		mDialogAll.show(getSupportFragmentManager(), "all");
	}


	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

	}
}
