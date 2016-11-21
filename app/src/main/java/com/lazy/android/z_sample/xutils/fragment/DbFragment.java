package com.lazy.android.z_sample.xutils.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lazy.android.basemodel.sqlite.UserBean;
import com.lazy.android.R;
import com.lazy.android.baseui.base.LZBaseFragment;
import com.lazy.android.config.ConfigDatebase;
import com.lazy.android.config.ConfigFilePath;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/**
 * Created by Administrator on 2016/1/15.
 */
@ContentView(R.layout.sample_fragment_dbfragment)
public class DbFragment extends LZBaseFragment {

	@ViewInject(R.id.xutils_db_result)
	TextView xutils_db_result;
	private UserBean userBean;
	protected DbManager mDbManager = null;


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initsqlite();
		userBean = new UserBean("chenglei", "asdasdasd");
		String url = Environment.getExternalStorageDirectory().getAbsolutePath();
		Toast.makeText(getActivity(), "url=" + url, Toast.LENGTH_SHORT).show();


	}

//	初始化数据库
	private void initsqlite() {
		mDbManager = x.getDb(ConfigDatebase.daoConfig);
	}


	@Event(R.id.xutils_db_one_add)
	public void xutils_db_one_add_Event(View v) {


		Toast.makeText(getActivity(), "asdasdads", Toast.LENGTH_SHORT).show();
//		try {
//			mDbManager.save(userBean);
//		} catch (DbException e) {
//			e.printStackTrace();
//		}


		userBean.save(mDbManager);
	}

	@Event(R.id.xutils_db_one_delete)
	public void xutils_db_one_delete_Event(View v) {
		userBean.delete(mDbManager, 33);
	}

	@Event(R.id.xutils_db_one_select)
	private void xutils_db_one_select_Event(View v) {
		Toast.makeText(getActivity(), "asda", Toast.LENGTH_SHORT).show();
		UserBean result = (UserBean) userBean.select(mDbManager, 2);
		Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_SHORT).show();
	}

	@Event(R.id.xutils_db_one_update)
	public void xutils_db_one_update_Event(View v) {
		userBean.update(mDbManager, 2, new KeyValue("username","111111111"),new KeyValue("password","23333333333333"));
}


	@Event(R.id.xutils_db_list_add)
	public void xutils_db_list_add_Event(View v) {

	}

	@Event(R.id.xutils_db_list_delete)
	public void xutils_db_list_delete_Event(View v) {

	}

	@Event(R.id.xutils_db_list_select)
	public void xutils_db_list_select_Event(View v) {

	}

	@Event(R.id.xutils_db_list_update)
	public void xutils_db_list_update_Event(View v) {

	}


}
