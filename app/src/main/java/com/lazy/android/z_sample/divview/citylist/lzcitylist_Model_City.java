package com.lazy.android.z_sample.divview.citylist;

public class lzcitylist_Model_City {
	public String name;
	public String pinyi;

	public lzcitylist_Model_City(String name, String pinyi) {
		super();
		this.name = name;
		this.pinyi = pinyi;
	}

	public lzcitylist_Model_City() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyi() {
		return pinyi;
	}

	public void setPinyi(String pinyi) {
		this.pinyi = pinyi;
	}

}
