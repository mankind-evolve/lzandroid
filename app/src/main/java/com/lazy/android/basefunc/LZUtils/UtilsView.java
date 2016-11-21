package com.lazy.android.basefunc.LZUtils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by Administrator on 2015/6/16.
 */
public class UtilsView {
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity activity,float bgAlpha)
    {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }
}
