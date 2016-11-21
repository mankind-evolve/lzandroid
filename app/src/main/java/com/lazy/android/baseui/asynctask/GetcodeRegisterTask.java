package com.lazy.android.baseui.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.lazy.android.R;

/**
 * Created by Administrator on 2015/5/13.
 * 使用方法   参数列表：上下文环境 ， 载体textview  , 倒计时多少秒 ， 倒计时拼接的字符串 ， 倒计时完成后显示的文字
 * new GetcodeRegisterTask(this,register_recode,15,"秒后重新发送","获取验证码").execute();
 */
public class GetcodeRegisterTask extends AsyncTask {
    private Context context;
    private TextView textView;
    private int time;
    private String text , textafter;

    public GetcodeRegisterTask(Context context,TextView textView , int time , String text , String textafter){
        this.context = context;
        this.textView = textView;
        this.time = time;
        this.text = text;
        this.textafter = textafter;
    }

    //    后台运行方法
    @Override
    protected Object doInBackground(Object[] params) {
        for(int i = time ; i >=0  ; i-- ){
            try {
                publishProgress(i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //    更新UI的方法
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        textView.setText(values[0]+text);
        if(((Integer)values[0])==0){
            textView.setClickable(true);
            textView.setBackgroundColor(context.getResources().getColor(R.color.main));
            textView.setText(textafter);
        }else {
            textView.setClickable(false);
            textView.setBackgroundColor(context.getResources().getColor(R.color.main));
        }

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
