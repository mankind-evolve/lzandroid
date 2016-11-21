package com.lazy.android.z_sample.divview.netlistWithSwipeMenuListView;

import com.lazy.android.basemodel.LZBaseData;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by chenglei on 16/6/17.
 */
public class itemmodel extends LZBaseData {

	private long id;
	private String nickname;
	private String headimg;

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}



	public static itemmodel parse(JSONObject jsonObject) {
		itemmodel itemmodel = new Gson().fromJson(jsonObject.toString(), com.lazy.android.z_sample.divview.netlistWithSwipeMenuListView.itemmodel.class);
		return itemmodel;
	}

}
