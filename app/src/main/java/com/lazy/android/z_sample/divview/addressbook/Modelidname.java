package com.lazy.android.z_sample.divview.addressbook;

import com.lazy.android.basefunc.LZJson.LZJson;

/**
 * Created by chenglei on 16/8/14.
 */
public class Modelidname {

	private String id = "";
	private String  name = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Modelidname() {
	}

	public Modelidname(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Modelidname{" +
			"id='" + id + '\'' +
			", name='" + name + '\'' +
			'}';
	}
}
