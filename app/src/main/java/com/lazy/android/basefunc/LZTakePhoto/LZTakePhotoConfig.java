package com.lazy.android.basefunc.LZTakePhoto;

import android.content.Context;

import com.lazy.android.config.ConfigFilePath;
import com.lazy.android.basefunc.LZImage.LZImageConfig;

import java.io.File;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by chenglei on 16/4/8.
 * 基于Github上的开源项目
 * https://github.com/pengjianbo/GalleryFinal
 */
public class LZTakePhotoConfig {


//	拍照返回
	public final static int REQUEST_CODE_CAMERA = 1000;
//	打开相册返回
	public final static int REQUEST_CODE_GALLERY = 1001;
//	裁剪相册返回
	public final static int REQUEST_CODE_CROP = 1002;
//	编辑图片返回代码
	public final static int REQUEST_CODE_EDIT = 1003;


	public static void init(Context context){

		//		初始化UILImageLoader
		LZImageConfig.initUILImageLoader(context);


//        默认主题
		ThemeConfig theme = ThemeConfig.DEFAULT;
//        自定义主题
//        ThemeConfig theme = new ThemeConfig.Builder()
//            .setTitleBarBgColor(Color.rgb(0xFF, 0x57, 0x22))
//            .setTitleBarTextColor(Color.BLACK)
//            .setTitleBarIconColor(Color.BLACK)
//            .setFabNornalColor(Color.RED)
//            .setFabPressedColor(Color.BLUE)
//            .setCheckNornalColor(Color.WHITE)
//            .setCheckSelectedColor(Color.BLACK)
//            .setIconBack(R.drawable.lztakephoto_action_previous_item)
//            .setIconRotate(R.drawable.lztakephoto_action_repeat)
//            .setIconCrop(R.drawable.lztakephoto_action_crop)
//            .setIconCamera(R.drawable.lztakephoto_action_camera)
//            .build();
//配置功能
		FunctionConfig functionConfigBuilder = new FunctionConfig.Builder()
//		.setEnableEdit(false)//开启编辑功能
//		.setEnableCrop(false)//开启裁剪功能
//		.setEnableRotate(false)//开启旋转功能
//		.setEnableCamera(true)//开启相机功能
		.build();


//配置imageloader
//		基于xutils加载图片    基于xutils加载，多选图片 - 用正方形裁剪 - 预览的时候 - 不显示正方形
//		ImageLoader imageloader = new LZTakePhotoXUtilsImageLoader();

//		基于imageloader加载图片
		ImageLoader imageloader = new LZTakePhotoUILImageLoader();

		CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme)
			.setFunctionConfig(functionConfigBuilder)
//			配置缓存路径
			.setEditPhotoCacheFolder(new File(ConfigFilePath.FILE_CACHE))
//			配置保存路径
			.setTakePhotoFolder(new File(ConfigFilePath.FILE_IMAGE))
			.build();
		GalleryFinal.init(coreConfig);
	}
}
