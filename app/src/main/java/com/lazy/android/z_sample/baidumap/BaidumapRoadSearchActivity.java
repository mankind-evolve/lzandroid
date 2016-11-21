package com.lazy.android.z_sample.baidumap;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenglei on 16/6/29.
 */
@ContentView(R.layout.sample_baidumap_roadsearch_activity)
public class BaidumapRoadSearchActivity extends LZBaseActivity {

	@ViewInject(R.id.baidumapsearch_mapview)
	MapView baidumapsearch_mapview;
	@ViewInject(R.id.baiduroad_start)
	EditText baiduroad_start;
	@ViewInject(R.id.baiduroad_end)
	EditText baiduroad_end;
	private RoutePlanSearch mSearch;
	private PlanNode stNode;
	private PlanNode enNode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();
//		初始化线路规划对象
		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(listener);

	}


	//	创建公交线路规划检索监听者
	OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
		public void onGetWalkingRouteResult(WalkingRouteResult result) {
			//获取步行线路规划结果
			List<String> datas = new ArrayList<String>();
			List<WalkingRouteLine> routeLines = result.getRouteLines();
			if (routeLines != null) {
				for (WalkingRouteLine drivingRouteLine : routeLines) {
					// 分别列出路线的第一步
					datas.add(drivingRouteLine.getAllStep().get(0).getExitInstructions() + ",距离："
						+ drivingRouteLine.getDistance() / 1000.f + "千米，大约用时:" + drivingRouteLine.getDuration() / 60 + "分");
				}
			}
			String toastshow = "";
			for (int i = 0; i < datas.size(); i++) {
				toastshow += (datas.get(i) + "|");
			}
			ToastShow(toastshow);

		}

		public void onGetTransitRouteResult(TransitRouteResult result) {
			//获取公交换乘路径规划结果
			String toastshow = "";
			List<TransitRouteLine> lines = result.getRouteLines();
			for(int i = 0 ; i < lines.size() ; i++){
				List<TransitRouteLine.TransitStep> steps = lines.get(i).getAllStep();
				for(int j =0 ; j < steps.size() ; j++){
					toastshow += (steps.get(j).getInstructions() + "|||");
				}
			}
			ToastShow(toastshow);
		}

		public void onGetDrivingRouteResult(DrivingRouteResult result) {
			//获取驾车线路规划结果
			List<String> datas = new ArrayList<String>();
			List<DrivingRouteLine> routeLines = result.getRouteLines();
			if (routeLines != null) {
				for (DrivingRouteLine drivingRouteLine : routeLines) {
					// 分别列出路线的第一步
					datas.add(drivingRouteLine.getAllStep().get(0).getExitInstructions() + ",距离："
						+ drivingRouteLine.getDistance() / 1000.f + "千米，大约用时:" + drivingRouteLine.getDuration() / 60 + "分");
				}
			}
			String toastshow = "";
			for (int i = 0; i < datas.size(); i++) {
				toastshow += (datas.get(i) + "|");
			}
			ToastShow(toastshow);

		}

		@Override
		public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

		}
	};


	//	获得填写的信息
	private void getinfo() {
		stNode = PlanNode.withCityNameAndPlaceName("北京", baiduroad_start.getText().toString());
		enNode = PlanNode.withCityNameAndPlaceName("北京", baiduroad_end.getText().toString());
	}

	//	公交线路规划
	@Event(R.id.baiduroad_bus)
	private void baiduroad_bus_Event(View view) {
		getinfo();
		mSearch.transitSearch((new TransitRoutePlanOption())
			.from(stNode)
			.city("北京")
			.to(enNode));

	}

	//	驾车线路规划
	@Event(R.id.baiduroad_car)
	private void baiduroad_car_Event(View v) {
		getinfo();
		mSearch.drivingSearch((new DrivingRoutePlanOption())
			.from(stNode)
			.to(enNode));

	}

	//	步行线路规划
	@Event(R.id.baiduroad_walk)
	private void baiduroad_walk_Event(View view) {
		getinfo();
		mSearch.walkingSearch((new WalkingRoutePlanOption())
			.from(stNode)
			.to(enNode));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		baidumapsearch_mapview.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		baidumapsearch_mapview.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		baidumapsearch_mapview.onPause();
	}


	//	POI检索监听者；
	private OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
		public void onGetPoiResult(PoiResult result) {
			//获取POI检索结果

			List<PoiAddrInfo> adds = result.getAllAddr();
			List<PoiInfo> pois = result.getAllPoi();
			List<CityInfo> citys = result.getSuggestCityList();

			String toasts = "";
			for (int i = 0; i < 5; i++) {
				toasts += ("add=" + pois.get(i).address + "|name=" + pois.get(i).name + "|phonenum=" + pois.get(i).phoneNum
					+ "|location=" + pois.get(i).location.toString());
			}

			ToastShow(toasts);
		}

		public void onGetPoiDetailResult(PoiDetailResult result) {
			//获取Place详情页检索结果
		}

		@Override
		public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

		}
	};

	@Event(R.id.baidumapsearch_button)
	private void baidumapsearch_button_Event(View view) {
//		PoiSearch mPoiSearch = PoiSearch.newInstance();
//		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
//		mPoiSearch.searchInCity((new PoiCitySearchOption())
//			.city("北京")
//			.keyword(baidumapsearch_text.getText().toString())
//			.pageNum(10));

	}


	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(true);
		mCommonHead.setMiddleTitle("百度地图线路检索");
	}
}
