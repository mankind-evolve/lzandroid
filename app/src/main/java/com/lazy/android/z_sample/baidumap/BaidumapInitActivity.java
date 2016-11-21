package com.lazy.android.z_sample.baidumap;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenglei on 16/6/27.
 */
@ContentView(R.layout.sample_baidumap_init_activity)
public class BaidumapInitActivity extends LZBaseActivity {


	@ViewInject(R.id.baidumap_View) private MapView baidumap_View;
	private  BaiduMap mBaiduMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();

		mBaiduMap = baidumap_View.getMap();
//		普通地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//		卫星地图
//		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
////		空白地图
//		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);


		//开启交通图
		mBaiduMap.setTrafficEnabled(true);
		//开启热力图
		mBaiduMap.setBaiduHeatMapEnabled(true);


//		地图标注
	//定义Maker坐标点
			LatLng point = new LatLng(39.963175, 116.400244);
	//构建Marker图标
			BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.map_location);
	//构建MarkerOption，用于在地图上添加Marker
			OverlayOptions option = new MarkerOptions()
				.position(point)
				.icon(bitmap);
	//在地图上添加Marker，并显示
			mBaiduMap.addOverlay(option);


//		几何覆盖
		//定义多边形的五个顶点
		LatLng pt1 = new LatLng(39.93923, 116.357428);
		LatLng pt2 = new LatLng(39.91923, 116.327428);
		LatLng pt3 = new LatLng(39.89923, 116.347428);
		LatLng pt4 = new LatLng(39.89923, 116.367428);
		LatLng pt5 = new LatLng(39.91923, 116.387428);
		List<LatLng> pts = new ArrayList<LatLng>();
		pts.add(pt1);
		pts.add(pt2);
		pts.add(pt3);
		pts.add(pt4);
		pts.add(pt5);
//构建用户绘制多边形的Option对象
		OverlayOptions polygonOption = new PolygonOptions()
			.points(pts)
			.stroke(new Stroke(5, 0xAA00FF00))
			.fillColor(0xAAFFFF00);
//在地图上添加多边形Option，用于显示
		mBaiduMap.addOverlay(polygonOption);



//		文字覆盖
		//定义文字所显示的坐标点
		LatLng llText = new LatLng(39.86923, 116.397428);
//构建文字Option对象，用于在地图上添加文字
		OverlayOptions textOption = new TextOptions()
			.bgColor(0xAAFFFF00)
			.fontSize(24)
			.fontColor(0xFFFF00FF)
			.text("百度地图SDK")
			.rotate(-30)
			.position(llText);
//在地图上添加该文字对象并显示
		mBaiduMap.addOverlay(textOption);

	}



	@Override
	public void onDestroy() {
		super.onDestroy();
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		baidumap_View.onDestroy();
	}
	@Override
	public void onResume() {
		super.onResume();
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		baidumap_View.onResume();
	}
	@Override
	public void onPause() {
		super.onPause();
		//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		baidumap_View.onPause();
	}





	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(false);
		mCommonHead.setMiddleTitle("百度地图初始化");
	}
}
