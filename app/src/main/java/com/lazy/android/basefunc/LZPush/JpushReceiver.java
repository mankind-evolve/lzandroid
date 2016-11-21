package com.lazy.android.basefunc.LZPush;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lazy.android.basefunc.LZLogger.Logger;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.finalteam.toolsfinal.StringUtils;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by chenglei on 16/6/24.
 */
public class JpushReceiver extends BroadcastReceiver {

	private static final String TAG = "TalkReceiver";

	private NotificationManager nm;
	private Context mcontext;

	@Override
	public void onReceive(Context context, Intent intent) {
		mcontext = context;
		if (null == nm) {
			nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}

		Bundle bundle = intent.getExtras();
		String title = bundle.getString(JPushInterface.EXTRA_TITLE);
		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
//		String content = bundle.getString(JPushInterface.CON)
		Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: ");

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String id = JPushInterface.getRegistrationID(mcontext);
			Toast.makeText(mcontext, "JPush用户注册成功,注册id=" + id, Toast.LENGTH_SHORT).show();

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			Toast.makeText(mcontext, "接受到推送下来的自定义消息, title=" + title +";message="+message+";extras="+extras, Toast.LENGTH_SHORT).show();
			// Push Talk messages are push down by custom message format
//			processCustomMessage(context, bundle);

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//			Log.d(TAG, "接受到推送下来的通知, title=" + title +";alert="+alert+";extras="+extras);
			Toast.makeText(mcontext, "接受到推送下来的通知, title=" + title +";alert="+alert+";extras="+extras, Toast.LENGTH_SHORT).show();

//			receivingNotification(context,bundle);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Toast.makeText(mcontext, "用户点击打开了通知", Toast.LENGTH_SHORT).show();

//			openNotification(context,bundle);

		} else {
			Log.d(TAG, "Unhandled intent - " + intent.getAction());
		}
	}

//	private void processCustomMessage(Context context, Bundle bundle) {
//		String title = bundle.getString(JPushInterface.EXTRA_TITLE);
//		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//		if (StringUtils.isEmpty(title)) {
//			Log.w(TAG, "Unexpected: empty title (friend). Give up");
//			return;
//		}
//
//		boolean needIncreaseUnread = true;
//
////		if (title.equalsIgnoreCase(Config.myName)) {
//			Log.d(TAG, "Message from myself. Give up");
//			needIncreaseUnread = false;
////			if (!Config.IS_TEST_MODE) {
////				return;
////			}
////		}
//
//		String channel = null;
//		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//		try {
//			JSONObject extrasJson = new JSONObject(extras);
//			channel = extrasJson.optString("Constants.KEY_CHANNEL");
//		} catch (Exception e) {
//			Log.w(TAG, "Unexpected: extras is not a valid json", e);
//		}
//
//		// Send message to UI (Webview) only when UI is up
//		if (!Config.isBackground) {
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(Constants.KEY_MESSAGE, message);
//			msgIntent.putExtra(Constants.KEY_TITLE, title);
//			if (null != channel) {
//				msgIntent.putExtra(Constants.KEY_CHANNEL, channel);
//			}
//
//			JSONObject all = new JSONObject();
//			try {
//				all.put(Constants.KEY_TITLE, title);
//				all.put(Constants.KEY_MESSAGE, message);
//				all.put(Constants.KEY_EXTRAS, new JSONObject(extras));
//			} catch (JSONException e) {
//			}
//			msgIntent.putExtra("all", all.toString());
//
//			context.sendBroadcast(msgIntent);
//		}
//
//		String chatting = title;
//		if (!StringUtils.isEmpty(channel)) {
//			chatting = channel;
//		}
//
//		String currentChatting = MyPreferenceManager.getString(Constants.PREF_CURRENT_CHATTING, null);
//		if (chatting.equalsIgnoreCase(currentChatting)) {
//			Logger.d(TAG, "Is now chatting with - " + chatting + ". Dont show notificaiton.");
//			needIncreaseUnread = false;
//			if (!Config.IS_TEST_MODE) {
//				return;
//			}
//		}
//
//		if (needIncreaseUnread) {
//			unreadMessage(title, channel);
//		}
//
//		NotificationHelper.showMessageNotification(context, nm, title, message, channel);
//	}

	// When received message, increase unread number for Recent Chat
//	private void unreadMessage(final String friend, final String channel) {
//		new Thread() {
//			public void run() {
//				String chattingFriend = null;
//				if (StringUtils.isEmpty(channel)) {
//					chattingFriend = friend;
//				}
//
//				Map<String, String> params = new HashMap<String, String>();
//				params.put("udid", Config.udid);
//				params.put("friend", chattingFriend);
//				params.put("channel_name", channel);
//
//				try {
//					HttpHelper.post(Constants.PATH_UNREAD, params);
//				} catch (Exception e) {
//					Logger.e(TAG, "Call pushtalk api to report unread error", e);
//				}
//			}
//		}.start();
//	}
}
