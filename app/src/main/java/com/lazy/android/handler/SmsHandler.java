package com.lazy.android.handler;

import android.os.Handler;
import android.os.Message;
import android.widget.Button;

/**
 * Created by chenglei on 16/5/23.
 */
public class SmsHandler extends Handler {
	private Button button;
	private int time = 60;

	public SmsHandler() {
	}

	public SmsHandler(Button button) {
		this.button = button;
	}

	public SmsHandler(Button button, int time) {
		this.button = button;
		this.time = time;
	}

	@Override
	public void handleMessage(Message msg) {
		if(msg.what <= 0){
			button.setClickable(true);
			button.setText("获取验证码");
		}else{
			button.setText(msg.what + "秒后再发送");
			button.setClickable(false);
		}
	}
}
