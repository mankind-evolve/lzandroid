package com.lazy.android.basefunc.LZUtils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.lazy.android.R;


/**
 * @ClassName: HaloToast
 * @Description: 显示Toast信息类
 * @author 
 * 
 */
public class UtilsToast {

	public final static void ShowToast(Context c, String s) {
		Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
	}

	public final static void ShowLongToast(Context c, String s) {
		Toast.makeText(c, s, Toast.LENGTH_LONG).show();
	}

	public final static void ShowToast(Context c, int resId) {
		String s = c.getResources().getString(resId);
		Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
	}

	public final static void show(Context context, String str) {
		show(context, str, Toast.LENGTH_SHORT);
	}

	public final static void show(Context context, int res) {
		show(context, res, Toast.LENGTH_SHORT);
	}

	public final static void show(Context context, int res, int duration) {
		show(context, context.getString(res), duration);
	}

	public final static void show(Context context, String str, int duration) {
//		View layout = getToastView(context);
//		TextView text = (TextView) layout.findViewById(R.id.text);
//		text.setVisibility(View.VISIBLE);
//		text.setText(str);
//		showToast(layout, duration);
	}

	public final static void showSuccess(Context context) {
		showSuccess(context, "");
	}

	public final static void showSuccess(Context context, int res) {
		showSuccess(context, context.getString(res));
	}

	public final static void showSuccess(Context context, String str) {
		showIcon(context, str, true);
	}

	public final static void showMessage(Context context, int res, int gravity,
			int yOffset) {
		showMessage(context, context.getString(res), gravity, yOffset);
	}

	public final static void showMessage(Context context, String str,
			int gravity, int yOffset) {
		TextView layout = new TextView(context);
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(lp);

		if (!TextUtils.isEmpty(str)) {
			layout.setText(str);
			layout.setTextSize(R.dimen.text_size_18);
			layout.setTextColor(Color.WHITE);
			layout.setGravity(Gravity.CENTER);
			layout.setBackgroundColor(UtilsGraphic.getColor(0x696969));
			layout.setPadding(0, 10, 0, 10);
		}
		showToast(layout, Toast.LENGTH_LONG, Gravity.BOTTOM, yOffset);
	}

	public final static void showFailed(Context context) {
		showFailed(context, "");
	}

	public final static void showFailed(Context context, int res) {
		showFailed(context, context.getString(res));
	}

	public final static void showFailed(Context context, String str) {
		if (str == null) {
			return;
		}
		showIcon(context, str, false);
	}

    private final static void showToast(View view, int duration) {
        showToast(view,duration, Gravity.CENTER,0);
    }

	private static View getToastView(Context context) {
//		return LayoutInflater.from(context).inflate(R.layout.toast, null);
		return null;
	}

	private static void showToast(View view, int duration, int gravity,
			int yOffset) {
		final Toast toast = new Toast(view.getContext());
		toast.setGravity(gravity, 0, yOffset);
		toast.setDuration(duration);
		toast.setView(view);
		toast.show();
	}

	private static void showIcon(Context context, String str, boolean success) {
		/*View layout = getToastView(context);
		ImageView iv = (ImageView) layout.findViewById(R.id.icon);
		iv.setVisibility(View.VISIBLE);
		iv.setImageResource(success ? R.drawable.toast_done
				: R.drawable.toast_error);
		if (!TextUtils.isEmpty(str)) {
			TextView text = (TextView) layout.findViewById(R.id.text);
			text.setVisibility(View.VISIBLE);
			text.setText(str);
		}
		showToast(layout, Toast.LENGTH_SHORT);
	}*/
    }
}
