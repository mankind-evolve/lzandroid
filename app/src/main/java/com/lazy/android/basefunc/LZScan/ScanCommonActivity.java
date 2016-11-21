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
package com.lazy.android.basefunc.LZScan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.lazy.android.R;
import com.lazy.android.basefunc.LZScan.zxing.ScanListener;
import com.lazy.android.basefunc.LZScan.zxing.ScanManager;
import com.lazy.android.basefunc.LZScan.zxing.decode.DecodeThread;
import com.lazy.android.basefunc.LZScan.zxing.decode.Utils;
import com.lazy.android.baseui.base.LZBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * 二维码扫描使用
 *
 * @author 刘红亮  2015年4月29日  下午5:49:45
 */
@ContentView(R.layout.lzscan_common_activity_bak)
public final class ScanCommonActivity extends LZBaseActivity implements ScanListener {
    static final String TAG = ScanCommonActivitybak.class.getSimpleName();
    final int PHOTOREQUESTCODE = 1111;
    private int scanMode;//扫描模型（条形，二维码，全部）
    ScanManager scanManager;//扫描管理器

    @ViewInject(R.id.capture_preview) SurfaceView scanPreview;
    @ViewInject(R.id.capture_container) View scanContainer;
    @ViewInject(R.id.capture_crop_view) View scanCropView;
    @ViewInject(R.id.capture_scan_line) ImageView scanLine;
    @ViewInject(R.id.iv_light) TextView iv_light;
    @ViewInject(R.id.qrcode_g_gallery) TextView qrcode_g_gallery;
    @ViewInject(R.id.qrcode_ic_back) TextView qrcode_ic_back;

    @ViewInject(R.id.service_register_rescan)  Button rescan;
    @ViewInject(R.id.scan_image)  ImageView scan_image;
    @ViewInject(R.id.scan_image) ImageView authorize_return;
    @ViewInject(R.id.common_title_TV_center) TextView title;
    @ViewInject(R.id.scan_hint) TextView scan_hint;
    @ViewInject(R.id.tv_scan_result) TextView tv_scan_result;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        scanMode=getIntent().getIntExtra(ScanConstant.REQUEST_SCAN_MODE, ScanConstant.REQUEST_SCAN_MODE_ALL_MODE);
        //构造出扫描管理器
        scanManager = new ScanManager(this, scanPreview, scanContainer, scanCropView, scanLine, scanMode,this);
    }




    @Override
    public void scanResult(Result rawResult, Bundle bundle) {
        //扫描成功后，扫描器不会再连续扫描，如需连续扫描，调用reScan()方法。
        //scanManager.reScan();
//		Toast.makeText(that, "result="+rawResult.getText(), Toast.LENGTH_LONG).show();

        if (!scanManager.isScanning()) { //如果当前不是在扫描状态
            //设置再次扫描按钮出现
            rescan.setVisibility(View.VISIBLE);
            scan_image.setVisibility(View.VISIBLE);
            Bitmap barcode = null;
            byte[] compressedBitmap = bundle.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (compressedBitmap != null) {
                barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                barcode = barcode.copy(Bitmap.Config.ARGB_8888, true);
            }
            scan_image.setImageBitmap(barcode);
        }
        rescan.setVisibility(View.VISIBLE);
        scan_image.setVisibility(View.VISIBLE);
        tv_scan_result.setVisibility(View.VISIBLE);
        tv_scan_result.setText("结果："+rawResult.getText());

    }

    @Override
    public void scanError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        //相机扫描出错时
        if(e.getMessage()!=null&&e.getMessage().startsWith("相机")){
            scanPreview.setVisibility(View.INVISIBLE);
        }
    }



    public void showPictures(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

//    再次扫描
    void startScan() {
        if (rescan.getVisibility() == View.VISIBLE) {
            rescan.setVisibility(View.INVISIBLE);
            scan_image.setVisibility(View.GONE);
            scanManager.reScan();
        }
    }


    @Event(R.id.qrcode_g_gallery)
    private void qrcode_g_gallery_Event(View view){
        showPictures(PHOTOREQUESTCODE);
    }

    @Event(R.id.iv_light)
    private void iv_light_Event(View view){
        scanManager.switchLight();
    }

    @Event(R.id.qrcode_ic_back)
    private void qrcode_ic_back_Event(View view){
        finish();
    }

    @Event(R.id.service_register_rescan)
    private void service_register_rescan_Event(View view){
        startScan();
    }

    @Event(R.id.authorize_return)
    private void authorize_return_Event(View view){
        finish();
    }




    @Override
    public void onResume() {
        super.onResume();
        scanManager.onResume();
        rescan.setVisibility(View.INVISIBLE);
//        scan_image.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        scanManager.onPause();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String photo_path;
        Toast.makeText(ScanCommonActivity.this, "resultCode="+resultCode, Toast.LENGTH_SHORT).show();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTOREQUESTCODE:
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(data.getData(), proj, null, null, null);
                    if (cursor.moveToFirst()) {
                        int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        photo_path = cursor.getString(colum_index);
                        if (photo_path == null) {
                            photo_path = Utils.getPath(getApplicationContext(), data.getData());
                        }
                        scanManager.scanningImage(photo_path);
                    }
            }
        }
    }

}