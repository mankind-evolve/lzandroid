package com.lazy.android.z_sample.divview.netlistWithSwipeMenuListView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lazy.android.R;
import com.lazy.android.basemodel.LZRefreshListData;
import com.lazy.android.baseprotocol.LZHttpProtocolHandlerBase;
import com.lazy.android.baseui.adapter.LZAdapter;
import com.lazy.android.baseui.base.LZBaseActivity;
import com.lazy.android.baseui.common.pulltorefresh.PullToRefreshBase;
import com.lazy.android.baseui.common.pulltorefresh.PullToRefreshListView;
import com.lazy.android.config.ConfigStaticType;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by chenglei on 16/6/16.
 */
@ContentView(R.layout.sample_divview_netlist_activity)
public class NetListSwipeActivity extends LZBaseActivity implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {

	private PullToRefreshListView pullToRefreshListView;
	private ListView listView;
	private Boolean refreshed = true;
	private LZRefreshListData datalist = new LZRefreshListData();
	private LZAdapter adapter;
	@ViewInject(R.id.test) private TextView test;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCommonHead();
		LZRefreshListData<itemmodel> refreshListData = new LZRefreshListData<itemmodel>();
//		初始化视图
		initView();
//		初始化数据
		initdata();
//		绑定数据
		bound();
	}


	@Override
	public void initView() {
		super.initView();
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.PullToRefreshListView);
		pullToRefreshListView.setOnRefreshListener(this);
		pullToRefreshListView.setEmptyView();//没有记录时候的界面设置
		pullToRefreshListView.setLastPage(false);
		pullToRefreshListView.onRefreshComplete(pullToRefreshListView.hasPullFromTop());
//		pullToRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
//			@Override
//			public void onLastItemVisible() {
//				doRequest(false);
//			}
//		});

		listView = pullToRefreshListView.getRefreshableView();
		listView.setOnItemClickListener(this);
	}

	//	绑定数据
	private void bound() {
		adapter = new itemAdapter(this, R.layout.sample_divview_netlist_item, datalist.getDataList());
		listView.setAdapter(adapter);
//		listView.addFooterView(test);
	}

	//	初始化数据
	private void initdata() {
		doRequest(true);
	}


	@Override
	public void initCommonHead() {
		super.initCommonHead();
		mCommonHead.setMiddleTitle("网络请求页面列表");
	}

	@Override
	public void onPullDownToRefresh() {
		doRequest(true);
	}

	@Override
	public void onPullUpToRefresh() {
		doRequest(false);
	}

////	第一次加载
//	@Override
//	public void onFristLoading() {
//		doRequest(false);
//	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onHttpStart(LZHttpProtocolHandlerBase protocol) {
		super.onHttpStart(protocol);
	}

	@Override
	public void onHttpProgress(long total, long current, boolean isUploading) {
		super.onHttpProgress(total, current, isUploading);
	}

	@Override
	public void onHttpFailure(Exception except, String msg) {
		super.onHttpFailure(except, msg);
	}

	@Override
	public void onHttpSuccess(LZHttpProtocolHandlerBase protocol) {
		super.onHttpSuccess(protocol);
//		网络通顺
		if (protocol.isHttpSuccess()) {
//			网络通顺，服务器返回的信息正确
			if (protocol.isProtocolSuccess()) {
//				网络通顺，服务器返回的信息正确，没有数据
				if (protocol.getProtocolType() == ConfigStaticType.ProtocolStatus.RESULT_SUCCESS_EMPTY) {

				}
				//网络通顺，服务器返回的信息正确，有数据
				else {
					netlistProtocolList handler = (netlistProtocolList) protocol;
					datalist = handler.getDataList();
					adapter.updateData(datalist.getDataList(), true);
					//下拉刷新结束，上拉加载最后
					pullToRefreshListView.onRefreshComplete(pullToRefreshListView.hasPullFromTop());
					pullToRefreshListView.setLastPage(handler.getDataList().isLastPage());
				}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}


		private void doRequest(Boolean refreshed) {
		new netlistProtocolList(this, refreshed, datalist, this);
	}


}
