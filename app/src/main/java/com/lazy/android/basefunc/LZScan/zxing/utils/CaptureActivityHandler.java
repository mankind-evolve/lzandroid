/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lazy.android.basefunc.LZScan.zxing.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.zxing.Result;
import com.lazy.android.R;
import com.lazy.android.basefunc.LZScan.zxing.ScanManager;
import com.lazy.android.basefunc.LZScan.zxing.camera.CameraManager;
import com.lazy.android.basefunc.LZScan.zxing.decode.DecodeThread;

/**
 * This class handles shouquan_ic_all the messaging which comprises the state machine for
 * capture.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class CaptureActivityHandler extends Handler {

	public static final int restart_preview = 0;
	public static final int decode_succeeded = 1;
	public static final int decode_failed = 2;
	public static final int decode_error = 3;
	public static final int return_scan_result = 4;
	public static final int decode = 5;
	public static final int quit = 6;

	final ScanManager scanManager;
	final DecodeThread decodeThread;
	final CameraManager cameraManager;
	State state;

	enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(ScanManager scanManager, CameraManager cameraManager, int decodeMode) {
		this.scanManager = scanManager;
		decodeThread = new DecodeThread(scanManager, decodeMode);
		decodeThread.start();
		state = State.SUCCESS;

		// Start ourselves capturing previews and decoding.
		this.cameraManager = cameraManager;
		cameraManager.startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {
		switch (message.what) {
		case restart_preview:
			restartPreviewAndDecode();
			break;
		case decode_succeeded:
			state = State.SUCCESS;
			Bundle bundle = message.getData();

			scanManager.handleDecode((Result) message.obj, bundle);
			break;
		case decode_failed:
			// We're decoding as fast as possible, so when one decode fails,
			// start another.
			state = State.PREVIEW;
			cameraManager.requestPreviewFrame(decodeThread.getHandler(), CaptureActivityHandler.decode);
			break;
		case decode_error:
			scanManager.handleDecodeError((Exception)message.obj);
			break;
		case return_scan_result:
//			activity.setResult(Activity.RESULT_OK, (Intent) message.obj);
//			activity.finish();
			break;
		}
	}

	public void quitSynchronously() {
		state = State.DONE;
		cameraManager.stopPreview();
		Message quit = Message.obtain(decodeThread.getHandler(), CaptureActivityHandler.quit);
		quit.sendToTarget();
		try {
			// Wait at most half a shouquan_icon_second; should be enough time, and onPause()
			// will timeout quickly
			decodeThread.join(500L);
		} catch (InterruptedException e) {
			// continue
		}

		// Be absolutely sure we don't send any queued up messages
		removeMessages(CaptureActivityHandler.decode_succeeded);
		removeMessages(CaptureActivityHandler.decode_failed);
	}

	void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			cameraManager.requestPreviewFrame(decodeThread.getHandler(), CaptureActivityHandler.decode);
		}
	}
	/**
	 * 
	 * @return 返回当前扫描状态，是否可扫描,State.PREVIEW 是可扫描状态
	 */
	public boolean  isScanning() {
		if(state == State.PREVIEW){
			return true;
		}
		return false;
	}
}
