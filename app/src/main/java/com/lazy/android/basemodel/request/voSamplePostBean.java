package com.lazy.android.basemodel.request;

import com.lazy.android.basemodel.LZBaseVoBean;

/**
 * Created by Administrator on 2016/1/26.
 * Email: 187228131@qq.com
 */
public class voSamplePostBean extends LZBaseVoBean {
	private String username;
	private String password;

	public voSamplePostBean(String username, String password) {
		this.username = username;
		this.password = password;
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

}
