package com.lazy.android.baseui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lazy.android.R;

/**
 * Created by Administrator on 2016/1/18.
 */



public class CommonFoot extends LinearLayout {
	private ImageView foot_menu_item1_img,foot_menu_item2_img,foot_menu_item3_img,foot_menu_item4_img,foot_menu_item5_img;
	private TextView foot_menu_item1_text,foot_menu_item2_text,foot_menu_item3_text,foot_menu_item4_text,foot_menu_item5_text;
	private LinearLayout foot_menu_item1_ll,foot_menu_item2_ll,foot_menu_item3_ll,foot_menu_item4_ll,foot_menu_item5_ll;


	public CommonFoot(Context context) {
		super(context);
	}

	public CommonFoot(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommonFoot(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initCommonFoot();
	}




	/**
	 * 自定义初步的公用底部视图
	 */
	public void initCommonFoot(){
		foot_menu_item1_img = (ImageView) findViewById(R.id.foot_menu_item1_img);
		foot_menu_item2_img = (ImageView) findViewById(R.id.foot_menu_item2_img);
		foot_menu_item3_img = (ImageView) findViewById(R.id.foot_menu_item3_img);
		foot_menu_item4_img = (ImageView) findViewById(R.id.foot_menu_item4_img);
		foot_menu_item5_img = (ImageView) findViewById(R.id.foot_menu_item5_img);


		foot_menu_item1_text = (TextView) findViewById(R.id.foot_menu_item1_text);
		foot_menu_item2_text = (TextView) findViewById(R.id.foot_menu_item2_text);
		foot_menu_item3_text = (TextView) findViewById(R.id.foot_menu_item3_text);
		foot_menu_item4_text = (TextView) findViewById(R.id.foot_menu_item4_text);
		foot_menu_item5_text = (TextView) findViewById(R.id.foot_menu_item5_text);

		foot_menu_item1_ll = (LinearLayout) findViewById(R.id.foot_menu_item1_ll);
		foot_menu_item2_ll = (LinearLayout) findViewById(R.id.foot_menu_item2_ll);
		foot_menu_item3_ll = (LinearLayout) findViewById(R.id.foot_menu_item3_ll);
		foot_menu_item4_ll = (LinearLayout) findViewById(R.id.foot_menu_item4_ll);
		foot_menu_item5_ll = (LinearLayout) findViewById(R.id.foot_menu_item5_ll);
	}



	public void setmenuclick(OnClickListener menuclick1,OnClickListener menuclick2,OnClickListener menuclick3,OnClickListener menuclick4,OnClickListener menuclick5){
		foot_menu_item1_ll.setOnClickListener(menuclick1);
		foot_menu_item2_ll.setOnClickListener(menuclick2);
		foot_menu_item3_ll.setOnClickListener(menuclick3);
		foot_menu_item4_ll.setOnClickListener(menuclick4);
		foot_menu_item5_ll.setOnClickListener(menuclick5);
	}



	//	选中状态
	public void menuselected(int i){
		foot_menu_item1_img.setImageResource(R.drawable.lz_ico_copy_off);
		foot_menu_item2_img.setImageResource(R.drawable.lz_ico_copy_off);
		foot_menu_item3_img.setImageResource(R.drawable.lz_ico_copy_off);
		foot_menu_item4_img.setImageResource(R.drawable.lz_ico_copy_off);
		foot_menu_item5_img.setImageResource(R.drawable.lz_ico_copy_off);
		foot_menu_item1_text.setTextColor(getResources().getColor(R.color.menu_off));
		foot_menu_item2_text.setTextColor(getResources().getColor(R.color.menu_off));
		foot_menu_item3_text.setTextColor(getResources().getColor(R.color.menu_off));
		foot_menu_item4_text.setTextColor(getResources().getColor(R.color.menu_off));
		foot_menu_item5_text.setTextColor(getResources().getColor(R.color.menu_off));
		switch (i){
			case 1:
				foot_menu_item1_img.setImageResource(R.drawable.lz_ico_copy_on);
				foot_menu_item1_text.setTextColor(getResources().getColor(R.color.menu_on));
				break;
			case 2:
				foot_menu_item2_img.setImageResource(R.drawable.lz_ico_copy_on);
				foot_menu_item2_text.setTextColor(getResources().getColor(R.color.menu_on));
				break;
			case 3:
				foot_menu_item3_img.setImageResource(R.drawable.lz_ico_copy_on);
				foot_menu_item3_text.setTextColor(getResources().getColor(R.color.menu_on));
				break;
			case 4:
				foot_menu_item4_img.setImageResource(R.drawable.lz_ico_copy_on);
				foot_menu_item4_text.setTextColor(getResources().getColor(R.color.menu_on));
				break;
			case 5:
				foot_menu_item5_img.setImageResource(R.drawable.lz_ico_copy_on);
				foot_menu_item5_text.setTextColor(getResources().getColor(R.color.menu_on));
				break;
		}
	}

}
