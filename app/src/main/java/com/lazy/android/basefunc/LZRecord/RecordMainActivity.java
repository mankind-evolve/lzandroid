package com.lazy.android.basefunc.LZRecord;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lazy.android.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import sz.itguy.utils.FileUtil;
import sz.itguy.wxlikevideo.camera.CameraHelper;
import sz.itguy.wxlikevideo.recorder.WXLikeVideoRecorder;
import sz.itguy.wxlikevideo.views.CameraPreviewView;

/**
 * 新视频录制页面
 *
 * @author Martin
 */
public class RecordMainActivity extends Activity implements View.OnTouchListener {

    private static final String TAG = "NewRecordVideoActivity";

    // 输出宽度
    private static final int OUTPUT_WIDTH = 900;
    // 输出高度
    private static final int OUTPUT_HEIGHT = 900;
    // 宽高比
    private static final float RATIO = 1f * OUTPUT_WIDTH / OUTPUT_HEIGHT;

    private Camera mCamera;

    private WXLikeVideoRecorder mRecorder;

    private static final int CANCEL_RECORD_OFFSET = -100;
    private float mDownX, mDownY;
    private boolean isCancelRecord = false;
    private ImageView movie_progressBar;

    private  int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int cameraId = CameraHelper.getDefaultCameraID();
        // Create an instance of Camera
        mCamera = CameraHelper.getCameraInstance(cameraId);
        if (null == mCamera) {
            Toast.makeText(this, "打开相机失败！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        // 初始化录像机
        mRecorder = new WXLikeVideoRecorder(this, FileUtil.MEDIA_FILE_DIR);
        mRecorder.setOutputSize(OUTPUT_WIDTH, OUTPUT_HEIGHT);


        setContentView(R.layout.lzrecord_recordermain_activity);
        CameraPreviewView preview = (CameraPreviewView) findViewById(R.id.camera_preview);
        preview.setCamera(mCamera, cameraId);

        mRecorder.setCameraPreviewView(preview);

        findViewById(R.id.button_start).setOnTouchListener(this);
        movie_progressBar = (ImageView) findViewById(R.id.movie_progressBar);

        ((TextView) findViewById(R.id.filePathTextView)).setText("请在" + FileUtil.MEDIA_FILE_DIR + "查看录制的视频文件");


//        获得屏幕的宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRecorder != null) {
            boolean recording = mRecorder.isRecording();
            // 页面不可见就要停止录制
            mRecorder.stopRecording();
            // 录制时退出，直接舍弃视频
            if (recording) {
                FileUtil.deleteFile(mRecorder.getFilePath());
            }
        }
        releaseCamera();              // release the camera immediately on pause event
        finish();
    }

    private void releaseCamera() {
        if (mCamera != null){
            mCamera.setPreviewCallback(null);
            // 释放前先停止预览
            mCamera.stopPreview();
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    /**
     * 开始录制
     */
    private void startRecord() {
        if (mRecorder.isRecording()) {
            Toast.makeText(this, "正在录制中…", Toast.LENGTH_SHORT).show();
            return;
        }

        // initialize video camera
        if (prepareVideoRecorder()) {
            // 录制视频
            if (!mRecorder.startRecording())
                Toast.makeText(this, "录制失败…", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 准备视频录制器
     * @return
     */
    private boolean prepareVideoRecorder(){
        if (!FileUtil.isSDCardMounted()) {
            Toast.makeText(this, "SD卡不可用！", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 停止录制
     */
    private void stopRecord() {
        mRecorder.stopRecording();
        String videoPath = mRecorder.getFilePath();
        String imgPath = getVideoimg(videoPath);
        // 没有录制视频
        if (null == videoPath) {
            return;
        }
        // 若取消录制，则删除文件，否则通知宿主页面发送视频
        if (isCancelRecord) {
            FileUtil.deleteFile(videoPath);
        } else {
            // 告诉宿主页面录制视频的路径
            Intent intent = new Intent(this, PlayVideoActiviy.class);
            intent.putExtra(PlayVideoActiviy.KEY_FILE_PATH,videoPath);
            intent.putExtra(PlayVideoActiviy.KEY_IMG_PATH, imgPath);
            startActivity(intent);
        }
    }


//    更新进度条的handler
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what <= 0){
                stopRecord();
            }
            ViewGroup.LayoutParams params = movie_progressBar.getLayoutParams();
            params.width = msg.what;
            movie_progressBar.setLayoutParams(params);
        }
    };


    @Override
    public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isCancelRecord = false;
                    mDownX = event.getX();
                    mDownY = event.getY();
                    startRecord();

                    new Thread(){
                        @Override
                        public void run() {
                            int screenWidthtem = screenWidth;
                            do {
                                screenWidthtem = screenWidthtem - (screenWidth/700);
                                Message message = new Message();
                                message.what = Integer.valueOf(screenWidthtem+"");
                                mHandler.sendMessage(message);
                                try {
                                    sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }while (screenWidthtem>0);
                        }
                    }.start();

                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!mRecorder.isRecording())
                        return false;

                    float y = event.getY();
                    if (y - mDownY < CANCEL_RECORD_OFFSET) {
                        if (!isCancelRecord) {
                            // cancel record
                            isCancelRecord = true;
                            Toast.makeText(this, "cancel record", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        isCancelRecord = false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    stopRecord();
                    break;
            }

        return true;
    }

    /**
     * 开始录制失败回调任务
     *
     * @author Martin
     */
    public static class StartRecordFailCallbackRunnable implements Runnable {

        private WeakReference<RecordMainActivity> mNewRecordVideoActivityWeakReference;

        public StartRecordFailCallbackRunnable(RecordMainActivity activity) {
            mNewRecordVideoActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            RecordMainActivity activity;
            if (null == (activity = mNewRecordVideoActivityWeakReference.get()))
                return;

            String filePath = activity.mRecorder.getFilePath();
            if (!TextUtils.isEmpty(filePath)) {
                FileUtil.deleteFile(filePath);
                Toast.makeText(activity, "Start record failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 停止录制回调任务
     *
     * @author Martin
     */
    public static class StopRecordCallbackRunnable implements Runnable {

        private WeakReference<RecordMainActivity> mNewRecordVideoActivityWeakReference;

        public StopRecordCallbackRunnable(RecordMainActivity activity) {
            mNewRecordVideoActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            RecordMainActivity activity;
            if (null == (activity = mNewRecordVideoActivityWeakReference.get()))
                return;

            String filePath = activity.mRecorder.getFilePath();
            if (!TextUtils.isEmpty(filePath)) {
                if (activity.isCancelRecord) {
                    FileUtil.deleteFile(filePath);
                } else {
                    Toast.makeText(activity, "Video file path: " + filePath, Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    /**
     * 生成视频缩略图（这里获取第一帧）
     * @param filePath
     * @return
     */
    public String getVideoimg(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime(TimeUnit.MILLISECONDS.toMicros(1));
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        File f = new File(mRecorder.getImgPath());
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mRecorder.getImgPath();

    }



}


//public class MyThread implements Runnable {
//    @Override
//    public void run() {
//        while (count <= 20) {
//            // 从消息池中获取消息，如果没有消息，创建一个消息，如果有，则取出来消息携带数据，由handler发送
//            Message message = Message.obtain();
//            message.arg1 = count;
//            message.obj = "jack";
//            count++;
//            handler.sendMessage(message);
//        }
//    }
//}
