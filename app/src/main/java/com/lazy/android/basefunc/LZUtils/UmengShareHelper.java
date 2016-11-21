//package com.baicaijia.baicaijia.base.utils;
//
//import android.content.Context;
//
//
//import com.baicaijia.baicaijia.LZConfig.LZConfig_System;
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.UMSocialService;
//import com.umeng.socialize.media.QZoneShareContent;
//import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.weixin.controller.UMWXHandler;
//import com.umeng.socialize.weixin.media.CircleShareContent;
//import com.umeng.socialize.weixin.media.WeiXinShareContent;
//
//import java.util.HashMap;
//
///**
// * Created by Administrator on 2015/6/4.
// */
//public class UmengShareHelper {
//    private Context context;
//    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
//
//    public UMSocialService getmController() {
//        return mController;
//    }
//
//    private  UmengShareHelper(Context context) {
//        this.context=context;
//    }
//
//    public static UmengShareHelper getinstance(Context context){
//        UmengShareHelper umengShareHelper =  new UmengShareHelper(context);
//        return umengShareHelper;
//    }
//
//    //    微信分享
//    public  void  weChat(HashMap hashMap){
//
//    // 添加微信平台
//        UMWXHandler wxHandler = new UMWXHandler(context, LZConfig_System.WECHAT_APPID,LZConfig_System.WECHAT_APPSECRET);
//        wxHandler.addToSocialSDK();
//
//        //设置微信分享的内容
//        WeiXinShareContent weixinContent = new WeiXinShareContent();
//        //设置分享文字
//        weixinContent.setShareContent("测试微信分享内容");
//        //设置title
//        weixinContent.setTitle("测试微信分享标题");
//        //设置分享内容跳转URL
//        weixinContent.setTargetUrl("http://www.baidu.com/");
//
//        mController.setShareMedia(weixinContent);
//
//    }
//
//
////    微信朋友圈
//    public void weChat_Circle(HashMap hashMap){
//        // 添加微信朋友圈
//        UMWXHandler wxCircleHandler = new UMWXHandler(context,LZConfig_System.WECHAT_APPID,LZConfig_System.WECHAT_APPSECRET);
//        wxCircleHandler.setToCircle(true);
//        wxCircleHandler.addToSocialSDK();
//
////        设置微信朋友圈分享内容
//        CircleShareContent circleMedia = new CircleShareContent();
//        circleMedia.setShareContent("测试微信朋友圈分享内容");
//        //设置朋友圈title
//        circleMedia.setTitle("测试微信朋友圈分享标题");
//        circleMedia.setTargetUrl("http://www.sina.com/");
//        mController.setShareMedia(circleMedia);
//
//    }
//
////    QQ空间
//    public void qzone(HashMap hashMap){
//        // 添加微信朋友圈
//        UMWXHandler wxCircleHandler = new UMWXHandler(context,LZConfig_System.WECHAT_APPID,LZConfig_System.WECHAT_APPSECRET);
//        wxCircleHandler.setToCircle(true);
//        wxCircleHandler.addToSocialSDK();
//
//
////        设置QQ空间分享内容
//        QZoneShareContent qzone = new QZoneShareContent();
////设置分享文字
//        qzone.setShareContent("测试QQ空间内容");
////设置点击消息的跳转URL
//        qzone.setTargetUrl("http://www.qqzone.com/");
////设置分享内容的标题
//        qzone.setTitle("测试QQ空间标题");
////设置分享图片
////        qzone.setShareImage(urlImage);
//        qzone.setShareImage(new UMImage(context,"http://img5.imgtn.bdimg.com/it/u=2605984149,3031378428&fm=11&gp=0.jpg"));
//        mController.setShareMedia(qzone);
//    }
//
//
//
//
//
//}
