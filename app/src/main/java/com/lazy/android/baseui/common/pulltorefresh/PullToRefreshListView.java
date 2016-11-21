package com.lazy.android.baseui.common.pulltorefresh;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ListView;

public class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {
	//	private LoadingLayout headerLoadingView;
	//	private LoadingLayout footerLoadingView;
	class InternalListView extends ListView implements EmptyViewMethodAccessor {
		public InternalListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshListView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}
	}

	public PullToRefreshListView(Context context) {
		super(context);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshListView(Context context, int mode) {
		super(context, mode);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setDisableScrollingWhileRefreshing(false);
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalListView) getRefreshableView()).getContextMenuInfo();
	}

	@Override
	protected final ListView createRefreshableView(Context context, AttributeSet attrs) {
		ListView lv = new InternalListView(context, attrs);
		// Set it to this so it can be used in ListActivity/ListFragment
		lv.setId(android.R.id.list);
		lv.setCacheColorHint(Color.TRANSPARENT);
		lv.setFadingEdgeLength(0);
		return lv;
	}
}
