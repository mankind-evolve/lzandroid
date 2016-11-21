package com.lazy.android.basefunc.LZRecord;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lazy.android.R;
import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 播放视频页面
 *
 * @author Martin
 */
public class PlayVideoActiviy extends Activity {

    public static final String TAG = "PlayVideoActiviy";

    public static final String KEY_FILE_PATH = "file_path";
    public static final String KEY_IMG_PATH = "img_path";

    private String filePath;
    private String imgPath;

    private ScalableVideoView mScalableVideoView;
    private ImageView mPlayImageView;
    private ImageView mThumbnailImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filePath = getIntent().getStringExtra(KEY_FILE_PATH);
        imgPath = getIntent().getStringExtra(KEY_IMG_PATH);
        if (TextUtils.isEmpty(filePath)) {
            Toast.makeText(this, "视频路径错误", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(R.layout.lzrecord_play_activity);
        mScalableVideoView = (ScalableVideoView) findViewById(R.id.video_view);
        try {
            // 这个调用是为了初始化mediaplayer并让它能及时和surface绑定
            mScalableVideoView.setDataSource("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mPlayImageView = (ImageView) findViewById(R.id.playImageView);

        mThumbnailImageView = (ImageView) findViewById(R.id.thumbnailImageView);
        mThumbnailImageView.setImageBitmap(BitmapFactory.decodeFile(imgPath));
    }

    /**
     * 获取视频缩略图（这里获取第一帧）
     * @param filePath
     * @return
     */
    public Bitmap getVideoThumbnail(String filePath) {
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
        return bitmap;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_view:
                mScalableVideoView.stop();
                mPlayImageView.setVisibility(View.VISIBLE);
                mThumbnailImageView.setVisibility(View.VISIBLE);
                break;
            case R.id.playImageView:
                try {
                    mScalableVideoView.setDataSource(filePath);
                    mScalableVideoView.setLooping(true);
                    mScalableVideoView.prepare();
                    mScalableVideoView.start();
                    mPlayImageView.setVisibility(View.GONE);
                    mThumbnailImageView.setVisibility(View.GONE);
                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    Toast.makeText(this, "播放视频异常", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
