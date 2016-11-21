package com.lazy.android.baseui.common;

import com.lazy.android.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;

public class CommonViewUtils {	

	public static final CustomLoadDialog getWaitDlg(Context context,
			String title, String content) {
		return getWaitDlg(context, title, content, true);
	}

	public static final CustomLoadDialog getWaitDlg(Context context, int title,
			int content) {
		String t = title > 0 ? context.getString(title) : null;
		String c = content > 0 ? context.getString(content) : null;
		return getWaitDlg(context, t, c);
	}

	public static final CustomLoadDialog getWaitDlg(Context context,
			String title, String content, boolean cancelable) {		
		CustomLoadDialog dialog = new CustomLoadDialog(context);
		dialog.setTitle(title);
		dialog.setContent(content);
		
		return dialog;
	}

	public final static void showInfoDialog(Context context, int title,
			int message) {
		Builder dlg = new AlertDialog.Builder(context);
		dlg.setMessage(message);
		if (title > 0) {
			dlg.setTitle(title);
		}
		dlg.setPositiveButton(R.string.ok, null);
		dlg.show();
	}

	public final static void showInfoDialog(Context context, String title,
			String message) {
		Builder dlg = new AlertDialog.Builder(context);
		dlg.setMessage(message);
		if (title.length() > 0) {
			dlg.setTitle(title);
		}
		dlg.setPositiveButton(R.string.ok, null);
		dlg.show();
	}

	public final static void showInfoDialog(Context c, int title, int message,
			DialogInterface.OnClickListener l) {
		Builder dlg = new AlertDialog.Builder(c);
		dlg.setMessage(message);
		if (title > 0) {
			dlg.setTitle(title);
		}
		dlg.setPositiveButton(R.string.ok, l);
		dlg.show();
	}

	public final static void showInfoDialog(Context c, int title,
			String message, DialogInterface.OnClickListener l) {
		Builder dlg = new AlertDialog.Builder(c);
		dlg.setMessage(message);
		if (title > 0) {
			dlg.setTitle(title);
		}
		dlg.setPositiveButton(R.string.ok, l);
		dlg.show();
	}

	public final static AlertDialog showSingleSelectDialog(Context context, int title,
			int array, DialogInterface.OnClickListener listener) {
		AlertDialog dialog = new AlertDialog.Builder(context).setTitle(title)
				.setItems(array, listener).create();
		dialog.show();
		return dialog;
	}

	public final static AlertDialog showSingleSelectDialog(Context context, int title,
			String[] item, DialogInterface.OnClickListener listener) {
		AlertDialog dialog = new AlertDialog.Builder(context).setTitle(title)
				.setItems(item, listener).create();
		dialog.show();
		return dialog;
	}

	public final static AlertDialog showSingleSelectDialog(Context context,
			String[] item, DialogInterface.OnClickListener listener) {
		AlertDialog dialog = new AlertDialog.Builder(context).setTitle(R.string.please_select)
				.setItems(item, listener).create();
		dialog.show();
		return dialog;
	}

	public final static AlertDialog showSingleSelectDialog(Context context,
			String title, String[] item,
			DialogInterface.OnClickListener listener) {
		AlertDialog dialog = new AlertDialog.Builder(context).setTitle(title).setItems(item, listener).create();
		dialog.show();
		return dialog;
	}

	public static Dialog getImageDlg(Context c, Bitmap bitmap) {
		Dialog dlg = new Dialog(c);
		dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		ImageView image = new ImageView(c);
		image.setImageBitmap(bitmap);
		LayoutParams l = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		dlg.setContentView(image, l);
		dlg.show();
		return dlg;
	}
}
