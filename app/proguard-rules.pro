#Add project specific ProGuard rules here.
#By default, the flags in this file are appended to flags specified
#in D:\Android\android-sdk-windows/tools/proguard/proguard-android.txt
#You can edit the include path and order by changing the proguardFiles
#directive in build.gradle.
#
#For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html
#
#Add any project specific keep options here:
#
#If your project uses WebView with JS, uncomment the following
#and specify the fully qualified class name to the JavaScript interface
#class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#----------支付宝专用--------------
#-flattenpackagehierarchy
#-allowaccessmodification
#-printmapping map.txt
#----------------



-keep class org.bytedeco.javacpp.** {*;}
-dontwarn java.awt.**
-dontwarn org.bytedeco.javacv.**
-dontwarn org.bytedeco.javacpp.**

-keepclasseswithmembernames class * {
    native <methods>;
}
-keep @interface org.bytedeco.javacpp.annotation.*,javax.inject.*
-keepclasseswithmembernames class * {
    @org.bytedeco.* <fields>;
}
-keepclasseswithmembernames class * {
    @org.bytedeco.* <methods>;
}
-keepattributes *Annotation*, SourceFile, SourceDir, InnerClasses




-keep class com.lazy.android.** {*;}                                  #保持配置文件不被混淆





-optimizationpasses 5                                                           # 指定代码的压缩级别
-dontusemixedcaseclassnames                                                     # 是否使用大小写混合
-dontskipnonpubliclibraryclasses                                                # 是否混淆第三方jar
-dontpreverify                                                                  # 混淆时是否做预校验
-verbose                                                                        # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法

-keep public class * extends android.app.Activity                               # 保持哪些类不被混淆
-keep public class * extends android.app.Application                            # 保持哪些类不被混淆
-keep public class * extends android.app.Service                                # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver                  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider                    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper               # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference                      # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService              # 保持哪些类不被混淆
-keep public class * extends java.lang.Throwable {*;}
-keep public class * extends java.lang.Exception {*;}

-keepclasseswithmembernames class * {                                           # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {                                               # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);     # 保持自定义控件类不被混淆
}

-keepclassmembers class * extends android.app.Activity {                        # 保持自定义控件类不被混淆
   public void *(android.view.View);
}

-keepclassmembers enum * {                                                      # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {                                # 保持 Parcelable 不被混淆
  public static final android.os.Parcelable$Creator *;
}

#-libraryjars src/main/jniLibs/armeabi/libavcodec.so
#-libraryjars src/main/jniLibs/armeabi/libavfilter.so
#-libraryjars src/main/jniLibs/armeabi/libavformat.so
#-libraryjars src/main/jniLibs/armeabi/libavutil.so
#-libraryjars src/main/jniLibs/armeabi/libcheckneon.so
#-libraryjars src/main/jniLibs/armeabi/libffmpeginvoke.so
#-libraryjars src/main/jniLibs/armeabi/libjniavcodec.so
#-libraryjars src/main/jniLibs/armeabi/libjniavfilter.so
#-libraryjars src/main/jniLibs/armeabi/libjniavformat.so
#-libraryjars src/main/jniLibs/armeabi/libjniavutil.so
#-libraryjars src/main/jniLibs/armeabi/libjniopencv_core.so
#-libraryjars src/main/jniLibs/armeabi/libjniopencv_imgproc.so
#-libraryjars src/main/jniLibs/armeabi/libjniswresample.so
#-libraryjars src/main/jniLibs/armeabi/libjniswscale.so
#-libraryjars src/main/jniLibs/armeabi/libswresample.so
#-libraryjars src/main/jniLibs/armeabi/libswscale.so
#-libraryjars src/main/jniLibs/armeabi/libtbb.so
#-libraryjars src/main/jniLibs/armeabi/libumeng_opustool.so
#-libraryjars src/main/jniLibs/armeabi/libweibosdkcore.so
#
#-libraryjars src/main/jniLibs/armeabi-v7a/libavcodec.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libavfilter.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libavformat.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libavutil.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libcheckneon.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libffmpeginvoke.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libjniavcodec.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libjniavfilter.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libjniavformat.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libjniavutil.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libjniopencv_core.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libjniopencv_imgproc.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libjniswresample.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libjniswscale.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libswresample.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libswscale.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libtbb.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libumeng_opustool.so
#-libraryjars src/main/jniLibs/armeabi-v7a/libweibosdkcore.so
#
#
#-libraryjars src/main/jniLibs/mips/libavcodec.so
#-libraryjars src/main/jniLibs/mips/libavfilter.so
#-libraryjars src/main/jniLibs/mips/libavformat.so
#-libraryjars src/main/jniLibs/mips/libavutil.so
#-libraryjars src/main/jniLibs/mips/libcheckneon.so
#-libraryjars src/main/jniLibs/mips/libffmpeginvoke.so
#-libraryjars src/main/jniLibs/mips/libjniavcodec.so
#-libraryjars src/main/jniLibs/mips/libjniavfilter.so
#-libraryjars src/main/jniLibs/mips/libjniavformat.so
#-libraryjars src/main/jniLibs/mips/libjniavutil.so
#-libraryjars src/main/jniLibs/mips/libjniopencv_core.so
#-libraryjars src/main/jniLibs/mips/libjniopencv_imgproc.so
#-libraryjars src/main/jniLibs/mips/libjniswresample.so
#-libraryjars src/main/jniLibs/mips/libjniswscale.so
#-libraryjars src/main/jniLibs/mips/libswresample.so
#-libraryjars src/main/jniLibs/mips/libswscale.so
#-libraryjars src/main/jniLibs/mips/libtbb.so
#-libraryjars src/main/jniLibs/mips/libumeng_opustool.so
#-libraryjars src/main/jniLibs/mips/libweibosdkcore.so
#
#
#-libraryjars src/main/jniLibs/x86/libavcodec.so
#-libraryjars src/main/jniLibs/x86/libavfilter.so
#-libraryjars src/main/jniLibs/x86/libavformat.so
#-libraryjars src/main/jniLibs/x86/libavutil.so
#-libraryjars src/main/jniLibs/x86/libcheckneon.so
#-libraryjars src/main/jniLibs/x86/libffmpeginvoke.so
#-libraryjars src/main/jniLibs/x86/libjniavcodec.so
#-libraryjars src/main/jniLibs/x86/libjniavfilter.so
#-libraryjars src/main/jniLibs/x86/libjniavformat.so
#-libraryjars src/main/jniLibs/x86/libjniavutil.so
#-libraryjars src/main/jniLibs/x86/libjniopencv_core.so
#-libraryjars src/main/jniLibs/x86/libjniopencv_imgproc.so
#-libraryjars src/main/jniLibs/x86/libjniswresample.so
#-libraryjars src/main/jniLibs/x86/libjniswscale.so
#-libraryjars src/main/jniLibs/x86/libswresample.so
#-libraryjars src/main/jniLibs/x86/libswscale.so
#-libraryjars src/main/jniLibs/x86/libtbb.so
#-libraryjars src/main/jniLibs/x86/libumeng_opustool.so
#-libraryjars src/main/jniLibs/x86/libweibosdkcore.so




#-libraryjars src/main/jniLibs/armeabi/libopencv_core.so
#-libraryjars src/main/jniLibs/armeabi/libopencv_imgproc.so
#-libraryjars src/main/jniLibs/armeabi/libjniopencv_core.so





#-keep class MyClass;                                                            # 保持自己定义的类不被混淆





# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

######################   Xutils混淆配置   ######################
-keepattributes Signature,*Annotation*
-keep public class org.xutils.** {
    public protected *;
}
-keep public interface org.xutils.** {
    public protected *;
}
-keepclassmembers class * extends org.xutils.** {
    public protected *;
}
-keepclassmembers @org.xutils.db.annotation.* class * {*;}
-keepclassmembers @org.xutils.http.annotation.* class * {*;}
-keepclassmembers class * {
    @org.xutils.view.annotation.Event <methods>;
}
######################   END   ######################


######################   与webview交互的混淆   ######################
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class com.lazy.android.base.LZHtml.LZWebView{*;}
-keepclassmembers class com.lazy.android.xiaobai.ui.common.CommonWebViewActivity{*;}
-keepclassmembers class com.lazy.android.xiaobai.ui.common.CommonWebViewObject{*;}
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
######################   END   ######################


######################   takephoto模块的混淆配置   ######################
-keep class cn.finalteam.galleryfinal.widget.*{*;}
-keep class cn.finalteam.galleryfinal.widget.crop.*{*;}
-keep class cn.finalteam.galleryfinal.widget.zoonview.*{*;}
######################   END   ######################



# webview + js
# keep 使用 webview 的类
-keepclassmembers class com.goldnet.mobile.activity.InfoDetailActivity {
   public *;
}
# keep 使用 webview 的类的所有的内部类
-keepclassmembers   class com.goldnet.mobile.activity.InfoDetailActivity$*{
    *;
}

#如果引用了v4或者v7包，可以忽略警告，因为用不到android.support
-dontwarn android.support.**

#保持自定义组件不被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
#-keepclassmembers enum * {
#  public static **[] values();
#  public static ** valueOf(java.lang.String);
#}

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

#不混淆资源类
#-keepclassmembers class **.R$* {
#    public static <fields>;
#}

#xUtils(保持注解，及使用注解的Activity不被混淆，不然会影响Activity中你使用注解相关的代码无法使用)
-keep class * extends java.lang.annotation.Annotation {*;}
-keep class com.otb.designerassist.activity.** {*;}

#自己项目特殊处理代码（这些地方我使用了Gson类库和注解，所以不希望被混淆，以免影响程序）
-keep class com.otb.designerassist.entity.** {*;}
-keep class com.otb.designerassist.http.rspdata.** {*;}
-keep class com.otb.designerassist.service.** {*;}

##混淆保护自己项目的部分代码以及引用的第三方jar包library（想混淆去掉"#"）



# 以libaray的形式引用的图片加载框架,不想混淆（注意，此处不是jar包形式，想混淆去掉"#"）
#-keep class com.nostra13.universalimageloader.** { *; }

###-------- Gson 相关的混淆配置--------
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }

###-------- pulltorefresh 相关的混淆配置---------
-dontwarn com.handmark.pulltorefresh.library.**
-keep class com.handmark.pulltorefresh.library.** { *;}
-dontwarn com.handmark.pulltorefresh.library.extras.**
-keep class com.handmark.pulltorefresh.library.extras.** { *;}
-dontwarn com.handmark.pulltorefresh.library.internal.**
-keep class com.handmark.pulltorefresh.library.internal.** { *;}

###--------------umeng 相关的混淆配置-----------
-keep class com.umeng.** { *; }
-keep class com.umeng.analytics.** { *; }
-keep class com.umeng.common.** { *; }
-keep class com.umeng.newxp.** { *; }

-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**


-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**

-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**

-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

-keep public class [your_pkg].R$*{
    public static final int *;
}


#友盟统计混淆代码
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keep public class com.lazy.android.R$*{
    public static final int *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


##----------------高德地图代码混淆--------------##
#-------------------------------------------------


-dontwarn android.support.v4.**

-keep class android.support.v4.** { *; }

-keep interface android.support.v4.app.** { *; }

-keep public class * extends android.support.v4.**

-keep public class * extends android.app.Fragment

#libs文件夹下的第三方jar包，注意名称

#-libraryjars   libs/Android_Location_V1.3.2.jar
#
#-libraryjars   libs/AMap_3DMap_V2.4.1.jar

-dontwarn com.amap.api.**

-dontwarn com.a.a.**

-dontwarn com.autonavi.**

-keep class com.amap.api.**  {*;}

-keep class com.autonavi.**  {*;}

-keep class com.a.a.**  {*;}


#fastjson
#-libraryjars libs/fastjson-1.1.34.android.jar
#------------------------------------------------------------------------
#-dontwarn android.support.**
#-dontwarn com.alibaba.fastjson.**
#-libraryjars libs/fastjson-1.1.33.jar
-dontwarn android.support.**
-dontwarn com.alibaba.fastjson.**



#-libraryjars libs/fastjson-1.1.33.jar
#-libraryjars libs/locSDK_3.3.jar

-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses
-ignorewarnings

#--------------百度地图混淆规则开始--------------
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**
#--------------百度地图混淆规则结束--------------
-keep class com.alibaba.fastjson.** { *; }

-keepclassmembers class * {
public <methods>;
}

-keepattributes Signature

#--------------支付宝------------------
#-libraryjars libs/alipaySDK-20150610.jar

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}


-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# adding this in to preserve line numbers so that the stack traces
# can be remapped
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
#---------------------银联---------------
-keep  public class com.unionpay.uppay.net.HttpConnection {
	public <methods>;
}
-keep  public class com.unionpay.uppay.net.HttpParameters {
	public <methods>;
}
-keep  public class com.unionpay.uppay.model.BankCardInfo {
	public <methods>;
}
-keep  public class com.unionpay.uppay.model.PAAInfo {
	public <methods>;
}
-keep  public class com.unionpay.uppay.model.ResponseInfo {
	public <methods>;
}
-keep  public class com.unionpay.uppay.model.PurchaseInfo {
	public <methods>;
}
-keep  public class com.unionpay.uppay.util.DeviceInfo {
	public <methods>;
}
-keep  public class java.util.HashMap {
	public <methods>;
}
-keep  public class java.lang.String {
	public <methods>;
}
-keep  public class java.util.List {
	public <methods>;
}
-keep  public class com.unionpay.uppay.util.PayEngine {
	public <methods>;
	native <methods>;
}
#--------------------环信---------------------------
-keep class com.easemob.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
-dontwarn  com.easemob.**
#2.0.9后的不需要加下面这个keep
#-keep class org.xbill.DNS.** {*;}
#另外，demo中发送表情的时候使用到反射，需要keep SmileUtils
-keep class com.easemob.chatuidemo.utils.SmileUtils {*;}
#注意前面的包名，如果把这个类复制到自己的项目底下，比如放在com.example.utils底下，应该这么写(实际要去掉#)
-keep class com.dianzhi.teacher.hxchat.utils.SmileUtils {*;}
#-keep class com.dianzhi.teacher.hxchat.adapter.VoicePlayClickListener {*;}
#如果使用easeui库，需要这么写
-keep class com.easemob.easeui.utils.EaseSmileUtils {*;}

#2.0.9后加入语音通话功能，如需使用此功能的api，加入以下keep
-dontwarn ch.imvs.**
-dontwarn org.slf4j.**
-keep class org.ice4j.** {*;}
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}
#--------------------------------


#-----------------极光推送-----------
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

#==================gson==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

#==================protobuf======================
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}



#================友盟第三方混淆开始=====================


-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**


-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**

-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep public class com.umeng.soexample.R$*{
    public static final int *;
}
-keep public class com.umeng.soexample.R$*{
    public static final int *;
}
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keep class com.linkedin.** { *; }
-keepattributes Signature

#====================友盟混淆结束=========================

######################   支付宝模块的混淆配置   ######################

#-libraryjars libs/alipaySdk-20160427.jar
#
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}

######################   END   ######################



