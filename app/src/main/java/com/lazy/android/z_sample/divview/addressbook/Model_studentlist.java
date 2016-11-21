package com.lazy.android.z_sample.divview.addressbook;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lazy.android.basemodel.LZBaseData;
import com.lazy.android.baseui.common.citylist.ContactItemInterface;
import com.lazy.android.baseui.common.citylist.pinyin.PinYin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by chenglei on 16/9/7.
 */
public class Model_studentlist extends LZBaseData implements  ContactItemInterface {
	private String pinyin = "";
	private String id = "";
	private Modelidnamestatus company = new Modelidnamestatus();
	private Modelidnamestatus member = new Modelidnamestatus();
	private String name = "";
	private String photo = "";
	private String account = "";
	private String prepay = "";
	private Modelidname fee = new Modelidname();
	private String service = "";
	private String spell = "";
	private String school = "";
	private Modelidname grade = new Modelidname();
	private String classes = "";
	private String teacher = "";
	private String coach = "";
	private String taboos = "";
	private String birthday = "";
	private String gender = "";
	private String height = "";
	private String weight = "";
	private String reciver = "";
	private String expire = "";
	private String change = "";
	private Modelidname status = new Modelidname();
	private String date = "";


	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Modelidnamestatus getCompany() {
		return company;
	}

	public void setCompany(Modelidnamestatus company) {
		this.company = company;
	}

	public Modelidnamestatus getMember() {
		return member;
	}

	public void setMember(Modelidnamestatus member) {
		this.member = member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPrepay() {
		return prepay;
	}

	public void setPrepay(String prepay) {
		this.prepay = prepay;
	}

	public Modelidname getFee() {
		return fee;
	}

	public void setFee(Modelidname fee) {
		this.fee = fee;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Modelidname getGrade() {
		return grade;
	}

	public void setGrade(Modelidname grade) {
		this.grade = grade;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public String getTaboos() {
		return taboos;
	}

	public void setTaboos(String taboos) {
		this.taboos = taboos;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public Modelidname getStatus() {
		return status;
	}

	public void setStatus(Modelidname status) {
		this.status = status;
	}

	public String getDates() {
		return date;
	}

	public void setDates(String date) {
		this.date = date;
	}

	@Override
	public String getItemForIndex() {
		return PinYin.getPinYin(name);
	}

	@Override
	public String getDisplayInfo() {
		return name;
	}


	public long getId() {
		return Long.valueOf(id);
	}


	@Override
	public String toString() {
		return "Model_studentlist{" +
			"pinyin='" + pinyin + '\'' +
			", name='" + name + '\'' +
			", photo='" + photo + '\'' +
			'}';
	}

	public static Model_studentlist parents(String json){
		Gson gson = new Gson();
		Model_studentlist data = gson.fromJson(json,Model_studentlist.class);
		return data;
	}



	public static Model_studentlist addpy (Model_studentlist before){
		before.setPinyin(lzcitylist_PingYinUtil.getPingYin(before.getName()));
		return before;
	}




}
