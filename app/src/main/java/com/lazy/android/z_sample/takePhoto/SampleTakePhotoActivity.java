package com.lazy.android.z_sample.takePhoto;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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
 * Created by chenglei on 16/4/7.
 */

@ContentView(R.layout.sample_takephoto_activity)
public class SampleTakePhotoActivity extends LZBaseActivity {

	@ViewInject(R.id.takephoto_msg) private TextView takephoto_msg;

//	必须定义的变量
	private FunctionConfig.Builder functionConfigBuilder;
	private List<PhotoInfo> mPhotoList = new ArrayList<>();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();



//		初始化 相册控件   必须
		LZTakePhotoConfig.init(this);



	}




//	单选打开相册
	@Event(R.id.takephoto_openphoto_radio)
	private void takephoto_openphoto_radio_Event(View view){
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
	}


//	多选打开相册
	@Event(R.id.takephoto_openphoto_checkbox)
	private void takephoto_openphoto_checkbox_event(View view){
//		多选打开相册配置
		functionConfigBuilder = new FunctionConfig.Builder();

		functionConfigBuilder.setMutiSelectMaxSize(4);//配置多选数量
		functionConfigBuilder.setEnableEdit(true);//开启编辑功能
		functionConfigBuilder.setEnableRotate(true);//开启旋转功能
		functionConfigBuilder.setEnableCrop(true);//开启裁剪功能
		functionConfigBuilder.setCropSquare(true);
		functionConfigBuilder.setEnableCamera(true);
		functionConfigBuilder.setEnablePreview(true);//是否开启预览功能
		functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合 必加

//		functionConfigBuilder.setMutiSelectMaxSize(maxSize)
//		functionConfigBuilder.setEnableEdit(true)
//		functionConfigBuilder.setEnableRotate(true);
//		 functionConfigBuilder.setEnableCrop(true);
//		functionConfigBuilder.setCropSquare(true);
//		functionConfigBuilder.setEnableCamera(true);
//		functionConfigBuilder.setEnablePreview(true);



		GalleryFinal.openGalleryMuti(LZTakePhotoConfig.REQUEST_CODE_GALLERY, functionConfigBuilder.build(), mOnHanlderResultCallback);
	}


//	使用拍照
	@Event(R.id.takephoto_takephoto)
	private void takephoto_takephoto_event(View view){
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
		GalleryFinal.openCamera(LZTakePhotoConfig.REQUEST_CODE_CAMERA,functionConfigBuilder.build(), mOnHanlderResultCallback);
	}

//	使用裁剪
	@Event(R.id.takephoto_photocut)
	private void takephoto_photocut_event(View view){
		GalleryFinal.openCrop(LZTakePhotoConfig.REQUEST_CODE_CROP, "imageurl", mOnHanlderResultCallback);
	}

//	使用图片编辑
	@Event(R.id.takephoto_photoedit)
	private void takephoto_photoedit_event(View view){
		GalleryFinal.openEdit(LZTakePhotoConfig.REQUEST_CODE_EDIT,"imageurl", mOnHanlderResultCallback);
	}


//	返回回调
	private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
		@Override
		public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
			mPhotoList.addAll(resultList);
			if (resultList != null) {
				ToastShow("返回的标识码为 ： "+reqeustCode);
			}

			String result = "";
			for (int i = 0 ; i < resultList.size() ; i++){
				result += resultList.get(i).getPhotoPath() + "\n";
			}
			takephoto_msg.setText(result);
		}

		@Override
		public void onHanlderFailure(int requestCode, String errorMsg) {
			Toast.makeText(SampleTakePhotoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
		}
	};


	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(true);
		mCommonHead.setMiddleTitle("拍照功能");
	}
}
