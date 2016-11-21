package com.lazy.android.baseui.crumbs;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.lazy.android.baseui.base.LZBaseActivity;

import java.util.Calendar;

/**
 * Created by chenglei on 16/5/7.
 */
public class CrumbsPickerDateActivity extends LZBaseActivity {

	public final static int RESULT_CODE = 11;



	private DatePickerDialog.OnDateSetListener DatePickerListener = new DatePickerDialog.OnDateSetListener(){

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
							  int dayOfMonth) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Calendar objTime = Calendar.getInstance();
		int iYear = objTime.get(Calendar.YEAR);
		int iMonth = objTime.get(Calendar.MONTH);
		int iDay = objTime.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog picker = new DatePickerDialog(this, DatePickerListener,
			iYear, iMonth, iDay);
		picker.setCancelable(true);
		picker.setCanceledOnTouchOutside(true);
		picker.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.d("Picker", "Correct behavior!");
				}
			});
		picker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.d("Picker", "Cancel!");
				}
			});
		picker.show();
	}


}
