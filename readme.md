欢迎围观lzandroid,为了防止重复造轮子，此框架是本人工作中遇到的一部分的常用需求的积累，目前只发布了很少的一部分，希望可以和大家一起慢慢完善，希望将来我们的android开发工程师有需要的时候可以直接把此框架拉出来就用，并且节省项目40%以上的开发时间

## 测试打包key及服务器php代码

打包key目录   /key/lazy

WEB服务器目录 /key/web/


##目录结构

### APP
*  application   <底层application>
*  basedb        <底层sqlite数据库操作类>
*  basefunc      <底层功能类>
*  basemodel     <底层mode类>
*  baseprotocol  <底层网络请求类>
*  baseservice   <底层service类>
*  baseui        <底层ui界面类>
*  config        <常用配置类>
*  handler       <常用handler类>
*  sample        <demo项目代码>
*  umengCallback <友盟回调类>
*  wxapi         <微信回调类>


##首页界面效果



![image](https://raw.githubusercontent.com/mankind-evolve/lzandroid/master/key/web/showpic/0.png)




##屏幕适配


### 由于android和苹果的屏幕不一样，美工需要做大量的工作去适配android和苹果的app，洒家琢磨着用苹果的效果图和标注去适配安卓，试验成功。给美工节省30%以上的工作量，思路如下
* 已知IOS2倍效果图尺寸640*960
* android最好适配的尺寸720*1280,在此尺寸下2px=1dp
* 把 ios 效果图放大到android的尺寸  为    640 * 960 * 1.125 = 720*1080
* 根据适配计宽不计高的原则， 200像素省掉

### 得出结论：IOS 2倍效果图的尺寸下    2px / 1.125 = 1.77777px  = 1dp
* 为了避免图片发虚的情况，请用IOS的三倍图的切图，二倍图的标注。
* 关于屏幕的高度，请按照权重排版，根据项目的实际情况自由发挥









