package com.lazy.android.z_sample.divview.addressbook;


public class lzcitylist_Model_City {
	public String name;
	public String pinyin;
	public String tel;

	public lzcitylist_Model_City() {
	}

	public lzcitylist_Model_City(String name, String pinyin) {
		this.name = name;
		this.pinyin = pinyin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "lzcitylist_Model_City{" +
			"name='" + name + '\'' +
			", pinyin='" + pinyin + '\'' +
			", tel='" + tel + '\'' +
			'}';
	}

	public static lzcitylist_Model_City addpy (lzcitylist_Model_City before){
		before.setPinyin(lzcitylist_PingYinUtil.getPingYin(before.getName()));
		return before;
	}

}
