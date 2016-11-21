package com.lazy.android.baseui.crumbs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lazy.android.R;
import com.lazy.android.basefunc.LZTakePhoto.LZTakePhotoConfig;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by chenglei on 16/4/14.
 */
@ContentView(R.layout.lz_crumbs_takephoto_activity)
public class CrumbsTakephotoActivity extends LZBaseActivity {

	@ViewInject(R.id.crumbs_takephoto_layout) private RelativeLayout crumbs_takephoto_layout;
	private FunctionConfig.Builder functionConfigBuilder;
	private List<PhotoInfo> mPhotoList=new ArrayList<>();

//	返回结果代码
	public static final int RESULT_ONE_CODE = 0 ;
	public static final int RESULT_MORE_CODE = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		初始化 相册控件   必须
		LZTakePhotoConfig.init(this);

//		解决左右两边有空隙
		getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

//		点击其他的地方关闭页面
//		crumbs_takephoto_layout.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				finish();
//				return true;
//			}
//		});
	}


//	打开拍照
	@Event(R.id.crumbs_takephoto_take)
	private void crumbs_takephoto_take_Event(View view){
//		使用拍照配置
		functionConfigBuilder = new FunctionConfig.Builder();
		functionConfigBuilder.setEnableEdit(true);//开启编辑功能
		functionConfigBuilder.setEnableCrop(true);//开启裁剪功能
		functionConfigBuilder.setEnableRotate(true);//开启旋转功能
		functionConfigBuilder.setEnableCamera(true);//开启相机功能
		functionConfigBuilder.setCropSquare(true);//裁剪正方形
//		functionConfigBuilder.takePhotoFolter(File file);//配置拍照保存目录，不做配置的话默认是/sdcard/DCIM/GalleryFinal/
		functionConfigBuilder.setForceCrop(true);//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
		functionConfigBuilder.setForceCropEdit(false);//在开启强制裁剪功能时是否可以对图片进行编辑（也就是是否显示旋转图标和拍照图标）
		functionConfigBuilder.setEnablePreview(true);//是否开启预览功能
		functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合  必加

		GalleryFinal.openCamera(LZTakePhotoConfig.REQUEST_CODE_CAMERA,functionConfigBuilder.build(),mOnHanlderResultCallback);
	}


//	单选打开相册
	@Event(R.id.crumbs_takephoto_select)
	private void crumbs_takephoto_select_Event(View view){

//		单选打开相册配置
		functionConfigBuilder = new FunctionConfig.Builder();
		functionConfigBuilder.setEnableEdit(true);//开启编辑功能
		functionConfigBuilder.setEnableCrop(true);//开启裁剪功能
		functionConfigBuilder.setEnableRotate(true);//开启旋转功能
		functionConfigBuilder.setEnableCamera(true);//开启相机功能
		functionConfigBuilder.setCropSquare(true);//裁剪正方形
//		functionConfigBuilder.takePhotoFolter(File file);//配置拍照保存目录，不做配置的话默认是/sdcard/DCIM/GalleryFinal/
		functionConfigBuilder.setForceCrop(true);//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
		functionConfigBuilder.setForceCropEdit(false);//在开启强制裁剪功能时是否可以对图片进行编辑（也就是是否显示旋转图标和拍照图标）
		functionConfigBuilder.setEnablePreview(true);//是否开启预览功能
		functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合  必加

		GalleryFinal.openGallerySingle(LZTakePhotoConfig.REQUEST_CODE_GALLERY, functionConfigBuilder.build(), mOnHanlderResultCallback);
//
////		单选打开相册配置
//		FunctionConfig config = new FunctionConfig.Builder()
//			.setMutiSelectMaxSize(8)
//			.build();
//		GalleryFinal.openGallerySingle(LZTakePhotoConfig.REQUEST_CODE_GALLERY, config,mOnHanlderResultCallback);
	}


	@Event(R.id.crumbs_takephoto_cancle)
	private void crumbs_takephoto_cancle_Event(View view){
		this.finish();
	}






	//	返回回调
	private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
		@Override
		public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
			mPhotoList.addAll(resultList);
			if (resultList != null) {
				Intent intent = new Intent();
				intent.putExtra("headimg",resultList.get(0).getPhotoPath());
				setResult(RESULT_ONE_CODE, intent);
				CrumbsTakephotoActivity.this.finish();
			}
		}

		@Override
		public void onHanlderFailure(int requestCode, String errorMsg) {
			Toast.makeText(CrumbsTakephotoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
		}
	};


}




