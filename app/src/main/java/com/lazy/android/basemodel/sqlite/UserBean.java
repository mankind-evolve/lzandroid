package com.lazy.android.basemodel.sqlite;

import com.lazy.android.basemodel.LZBaseSqliteBean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/1/29.
 * Email: 187228131@qq.com
 */

@Table(name = "user")
public class UserBean extends LZBaseSqliteBean {

	@Column(name = "id", isId = true)
	private int id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	public UserBean() {
	}

	public UserBean(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "userBean{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}