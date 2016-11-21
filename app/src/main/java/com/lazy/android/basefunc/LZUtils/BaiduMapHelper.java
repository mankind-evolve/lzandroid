package com.lazy.android.basefunc.LZUtils;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
* Created by Administrator on 2015/5/5.
* 使用方法：   new BaiduMapHelper(getApplicationContext(),new BDLocationListener() {
                                    @Override
                                    public void onReceiveLocation(BDLocation bdLocation) {
                                         Toast.makeText(ListCompanyActivity.this,bdLocation.getLongitude()+" | "+bdLocation.getLatitude()+"",Toast.LENGTH_SHORT).show();
                                    }
                            });
*
*/
public class BaiduMapHelper  {

    private LocationClient mLocationClient;
    private BDLocation returnsult;
    private Context context;
    private BDLocationListener bdLocationListener;

    public BaiduMapHelper(Context context,BDLocationListener bdLocationListener,final int time) {
        this.context = context;
        this.bdLocationListener = bdLocationListener ;
        baidulocation();
        new Thread(new Runnable() {
            public void run() {
                baidulocation_start();   //开启定位
                try {
                    Thread.sleep(time*1000);   //定位设定秒数
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                baidulocation_stop();   //关闭定位
            }
        }).start();
    }



    //    开启百度地图定位
    public void baidulocation_start(){
        mLocationClient.start();
    }
    //    关闭百度地图定位
    public void baidulocation_stop(){
        mLocationClient.stop();
    }
    //    获取地理位置维度
    public double getLatitude(){
        return  returnsult.getLatitude();
    }
    //    获取地理位置经度
    public double getLongitude(){
        return  returnsult.getLongitude();
    }
    //    百度地图定位的方法
    public void baidulocation() {
        mLocationClient = new LocationClient(context);     //声明LocationClient类
//		配置LocationClientOption类
        LocationClientOption opt = new LocationClientOption();
        opt.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);        //定位模式 低功耗
        opt.setIsNeedAddress(true);
        opt.setCoorType("bd09ll");
        opt.setScanSpan(200);  //1000毫秒请求一次
//		设置参数
        mLocationClient.setLocOption(opt);
//		注册监听
        mLocationClient.registerLocationListener(bdLocationListener);
    }




}
