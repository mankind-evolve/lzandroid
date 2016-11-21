package com.lazy.android.baseui.asynctask;

import android.os.AsyncTask;

/**
 * Created by Administrator on 2015/5/13.
 */
public class TaskDemo extends AsyncTask {

    //    后台运行方法
    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    //    更新UI的方法
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    //    任务开始前的准备工作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //    任务结束的时候调用的方法
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    //    取消任务调用的方法
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}
