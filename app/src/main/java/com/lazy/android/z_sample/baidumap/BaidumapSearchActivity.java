package com.lazy.android.z_sample.baidumap;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by chenglei on 16/6/29.
 */
@ContentView(R.layout.sample_baidumap_search_activity)
public class BaidumapSearchActivity extends LZBaseActivity {

	@ViewInject(R.id.baidumapsearch_mapview) MapView baidumapsearch_mapview;
	@ViewInject(R.id.baidumapsearch_text) EditText baidumapsearch_text;
	@ViewInject(R.id.baidumapsearch_button) TextView baidumapsearch_button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();
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
	private OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
		public void onGetPoiResult(PoiResult result){
			//获取POI检索结果

			List<PoiAddrInfo> adds = result.getAllAddr();
			List<PoiInfo> pois = result.getAllPoi();
			List<CityInfo> citys = result.getSuggestCityList();

			String toasts="";
			for (int i=0 ; i < 5 ; i++){
					toasts += ("add=" + pois.get(i).address + "|name=" + pois.get(i).name + "|phonenum=" + pois.get(i).phoneNum
						+ "|location=" + pois.get(i).location.toString() );
			}

			ToastShow(toasts);
		}

		public void onGetPoiDetailResult(PoiDetailResult result){
			//获取Place详情页检索结果
		}

		@Override
		public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

		}
	};

	@Event(R.id.baidumapsearch_button)
	private void baidumapsearch_button_Event(View view){
		PoiSearch mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
		mPoiSearch.searchInCity((new PoiCitySearchOption())
			.city("北京")
			.keyword(baidumapsearch_text.getText().toString())
			.pageNum(10));

	}




	/**
	 * 设置头部布局
	 */
	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setLeftLayoutVisible(true);
		mCommonHead.setMiddleTitle("百度地图检索");
	}
}
