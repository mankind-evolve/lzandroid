package com.lazy.android.z_sample.divview.addressbook;

/**
 * Created by chenglei on 16/8/14.
 */
public class Modelidnamestatus {

	private String id ="";
	private String  name = "";
	private String status = "";

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}




	@Override
	public String toString() {
		return "Modelidnamestatus{" +
			"status='" + status + '\'' +
			", name='" + name + '\'' +
			", id='" + id + '\'' +
			'}';
	}
}
