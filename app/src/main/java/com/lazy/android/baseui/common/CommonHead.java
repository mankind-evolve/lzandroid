package com.lazy.android.baseui.common;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lazy.android.config.ConfigStaticType;
import com.lazy.android.R;
import com.lazy.android.basefunc.LZUtils.UtilsShared;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/1/18.
 */
public class CommonHead extends LinearLayout {

	private LinearLayout common_head,common_headleft,common_headmiddle,common_headright,common_headleft_leftimage_out;
	private TextView common_headleft_middletext,common_headmiddle_middletext,common_headright_middletext;
	private ImageView common_headleft_leftimage,common_headleft_rightimage,common_headmiddle_leftimage,common_headmiddle_rightimage,common_headright_leftimage,common_headright_rightimage;
	private CircleImageView common_headleft_headimg;
	private EditText common_headmiddle_searchtext;

	public CommonHead(Context context) {
		super(context);
	}

	public CommonHead(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommonHead(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initCommonHead();
	}


	private void initheadview(){
		common_head = (LinearLayout) findViewById(R.id.common_head);

		common_headleft = (LinearLayout) findViewById(R.id.common_headleft);
		common_headmiddle = (LinearLayout) findViewById(R.id.common_headmiddle);
		common_headright = (LinearLayout) findViewById(R.id.common_headright);

		common_headleft_leftimage = (ImageView) findViewById(R.id.common_headleft_leftimage);
		common_headleft_middletext = (TextView) findViewById(R.id.common_headleft_middletext);
		common_headleft_rightimage = (ImageView) findViewById(R.id.common_headleft_rightimage);
		common_headleft_leftimage_out = (LinearLayout) findViewById(R.id.common_headleft_leftimage_out);
		common_headleft_headimg = (CircleImageView) findViewById(R.id.common_headleft_headimg);

		common_headmiddle_leftimage = (ImageView) findViewById(R.id.common_headmiddle_leftimage);
		common_headmiddle_middletext = (TextView) findViewById(R.id.common_headmiddle_middletext);
		common_headmiddle_rightimage = (ImageView) findViewById(R.id.common_headmiddle_rightimage);
		common_headmiddle_searchtext = (EditText) findViewById(R.id.common_headmiddle_searchtext);

		common_headright_leftimage = (ImageView) findViewById(R.id.common_headright_leftimage);
		common_headright_middletext = (TextView) findViewById(R.id.common_headright_middletext);
		common_headright_rightimage = (ImageView) findViewById(R.id.common_headright_rightimage);

	}

	/**
	 * 自定义初步的公用头部视图
	 */
	public void initCommonHead(){

		initheadview();
		common_headleft_leftimage.setImageResource(R.drawable.lz_arrow_left);
		common_headleft_middletext.setVisibility(GONE);
		common_headleft_rightimage.setVisibility(GONE);

		common_headmiddle_leftimage.setVisibility(GONE);
		common_headmiddle_rightimage.setVisibility(GONE);

		common_headright_leftimage.setVisibility(GONE);
		common_headright_middletext.setVisibility(VISIBLE);
		common_headright_rightimage.setVisibility(GONE);
	}


	/**
	 * @Title: setLeftLayoutVisible
	 * @Description: 设置标题栏左端是否显示
	 * @param visible
	 * @return void
	 */
	public void setLeftLayoutVisible(boolean visible) {
		if (visible) {
			common_headleft.setVisibility(View.VISIBLE);
		} else {
			common_headleft.setVisibility(View.INVISIBLE);
		}
	}



	/**
	 * @Title: addLeftLayoutView
	 * @Description: 设置标题栏中间标题是否显示内容
	 * @param visible
	 * @return void
	 */
	public void setMiddleLayoutVisible(boolean visible) {
		if (visible) {
			common_headmiddle.setVisibility(View.VISIBLE);
		} else {
			common_headmiddle.setVisibility(View.INVISIBLE);
		}
	}


	/**
	 * @Title setMiddleTitle
	 * @Description: 设置标题栏中间显示内容
	 * @param content
	 */
	public void setMiddleTitle(String content) {
		setMiddleLayoutVisible(true);
		common_headmiddle_middletext.setText(content);
	}

	/**
	 * @Title: setRightLayoutVisible
	 * @Description: 设置右端是否显示
	 * @param visible
	 * @return void
	 */
	public void setRightLayoutVisible(boolean visible) {
		if (visible) {
			common_headright.setVisibility(View.VISIBLE);
		} else {
			common_headright.setVisibility(View.GONE);
		}
	}

//	右端显示文字
	public void setRightLayoutText(String content){

		common_headright_middletext.setText(content);
		common_headright_middletext.setVisibility(VISIBLE);
		common_headright_leftimage.setVisibility(GONE);
		common_headright_rightimage.setVisibility(GONE);

	}

//	设置右端点击事件
	public void setRightLayoutTextClick(OnClickListener OnClickListener){
		common_headright_middletext.setOnClickListener(OnClickListener);
		common_headright_leftimage.setVisibility(GONE);
		common_headright_rightimage.setVisibility(GONE);
	}

//	刷新左侧头像
	public void refreshHeadimg(){
		if(UtilsShared.getBoolean(getContext(), ConfigStaticType.SettingField.LOGIN_SUCCESS, false)){
//			String headurl = UtilsShared.getString(getContext(), ConfigStaticType.SettingField.XB_HEADIMG, "");
//			if(!headurl.equals("")){
//				common_headleft_headimg.setImageBitmap(BitmapFactory.decodeFile(headurl));
//			}else{
//				common_headleft_headimg.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.lz_headimg_null));
//			}
		}else{
			common_headleft_headimg.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nologin));
		}
	}

//	初始化二级首页的头部菜单
//	type = 0 不带搜索框的头部
//	type != 0 带搜索框的头部 searchhit 搜索的提示文字  middleclick搜索框点击事件  rightimg右侧图片地址   rightimgclick右侧图片点击事件
	public void initheadtop(OnClickListener leftimgclick,int type,String searchhit,OnClickListener middleclick,int rightimg,OnClickListener rightimgclick){
		initheadview();
		common_headleft_leftimage_out.setVisibility(GONE);
		common_headleft_leftimage.setVisibility(GONE);
		common_headleft_middletext.setVisibility(GONE);
		common_headleft_rightimage.setVisibility(GONE);
		common_headleft_headimg.setVisibility(VISIBLE);
		common_headleft_headimg.setOnClickListener(leftimgclick);
		if(type == 0){
			common_headmiddle_leftimage.setVisibility(GONE);
			common_headmiddle_middletext.setVisibility(VISIBLE);
			common_headmiddle_rightimage.setVisibility(GONE);
			common_headmiddle_searchtext.setVisibility(GONE);
		}else{
			common_headmiddle_leftimage.setVisibility(GONE);
			common_headmiddle_middletext.setVisibility(GONE);
			common_headmiddle_rightimage.setVisibility(GONE);
			common_headmiddle_searchtext.setVisibility(VISIBLE);
			common_headmiddle_searchtext.setHint(searchhit);
			if(middleclick != null){
				common_headmiddle_searchtext.setOnClickListener(middleclick);
			}
		}

		common_headright_leftimage.setVisibility(GONE);
		common_headright_middletext.setVisibility(GONE);
		common_headright_rightimage.setVisibility(VISIBLE);
		common_headright_rightimage.setImageResource(rightimg);
		common_headright.setOnClickListener(rightimgclick);
	}





}
