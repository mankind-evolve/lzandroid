package com.lazy.android.config;

/**
 * @author gavin
 */
public class ConfigStaticType {

	public final static class CSHttpHeader {
		public final static String UA_VALUE = "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;bcj 1.0.0) AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1";
		public final static String MOBILE_OS = "sp-os";
		public final static String MOBILE_OS_VERSION = "sp-os-version";
		public final static String APP_VERSION = "sp-app-version";
		public final static String ACCESS_TOKEN = "token";
		public final static String USER_ID = "userid";
		public final static String REGIONID = "sp-regionid";
		public final static String COOKIE = "Cookie";
	}


	public final class ProtocolStatus {

		/**
		 * 结果执行成功: OK
		 */
		public final static int RESULT_SUCCESS = 1;

		/**
		 * 结果执行失败: FAIL
		 */
		public final static int RESULT_FAIL = -1;

		/**
		 * 结果执行成功但结果集为空： No Content
		 */
		public final static int RESULT_SUCCESS_EMPTY = -1;
		/**
		 * 需要登录鉴权，表示需要用户登录
		 */
		public final static int RESULT_ERROR_UNAUTH = 0;
		/**
		 * 结果执行失败: 服务器内部错误，message返回详细错误信息 Internal Server Error
		 */
		public final static int RESULT_ERROR = 3;
		/**
		 * 参数非法
		 */
		public static final int RESULT_ERROR_ILLEGAL_PARAMETER = 4;
		/**
		 * token过期。没有访问权限
		 */
		public static final int RESULT_ERROR_UNAUTHORIZED = 2003;
		/**
		 * 5002激活码不存在
		 */
		public static final int RESULT_ERROR_UNFINDCODE = 5002;
		/**
		 * 1402 原密码错误
		 */
		public static final int RESULT_ERROR_OLDPASSWORD_NOPASS = 1402;
		/**
		 * 1202 手机号已经注册
		 */
		public static final int RESULT_ERROR_TELHAVED = 1202;
	}






	//	页面状态
	public final class activityStatic{
		/**
		 * 增加
		 */
		public final static int ADD = 1;
		/**
		 * 删除
		 */
		public final static int DEL = 2;
		/**
		 * 修改
		 */
		public final static int EDIT = 3;
		/**
		 * 查找
		 */
		public final static int SELECT = 4;


	}



	//	美昂XML文件本地存储命名
	public enum SettingField {
		LOGIN_ACTIVITY,
		ISPAYSUCCESS,
		ACCESS_TOKEN,
		CONFIG_SUBJECT,
		CONFIG_PRICE,
		CONFIG_TYPE,

//		角色
		ISADMIN,
		ISEMPLOYEE,

//		机构配置
		COMPANY_ON_TIME,
		COMPANY_FOOD_TIME,
		COMPANY_CATERING,
		COMPANY_CONTENT_TIME,

//		角色资料
		LOGO,
		NAME,
		ID,
		ADMINNAME,
		ADMINTEL,
		LICENSE,
		ADDRESS,
		LOGIN_SUCCESS
	}


	//	XML本地存储命名
	public enum bSettingField {

		XB_CITYNAME,
		XB_HEADIMG,
		XB_NICKNAME,
		XB_SEX,
		XB_TOKEN,
		XB_UID,
		XB_USERNAME,
		XB_ISSHARE,
		XB_MOBILE,
		XB_ORDERID,
		XB_ISPAYSUCCESS,
		//	服务器的根地址 图片服务器  暂存
		XB_ROOT,
		XB_IMGROOT,
		/**
		 * 登录标志
		 */
		XB_LOGIN_SUCCESS,
//	private String cityname;
//	private String headimg;
//	private String nickname;
//	private int sex;
//	private String token;
//	private int uid;
//	private String username;
//


		/**
		 * token
		 */
		CONFIG_SUBJECT,
		/**
		 * token
		 */
		CONFIG_PRICE,
		/**
		 * token
		 */
		CONFIG_TYPE,
		/**
		 * token
		 */
		TEMP,
		/**
		 * token
		 */
		ACCESS_TOKEN,
		/**
		 * 应用的第一次启动
		 */
		FIRST_SPLASH,
		/**
		 * 分发渠道
		 */
		PUBLISH_CHANNEL,
		/**
		 * app版本号
		 */
		VERSION,
		/**
		 * 登录用户id
		 */
		USER_ID,
		/**
		 * 手机号码
		 */
		MOBILE,
		/**
		 * 登录用户昵称
		 */
		NICKNAME,
		/**
		 * 登录用户地区regionId
		 */
		REGIONID,
		/**
		 * 登录方式
		 */
		LOGIN_TYPE,

		/**
		 * 登录用户头像Url
		 */
		AVATAR_URL,
		/**
		 * 登录用户性别
		 */
		GENDER,
		/**
		 * 登录标志
		 */
		LOGIN_SUCCESS,
		/**
		 * 用户身份类型roleType
		 */
		ROLETYPE,
		/**
		 * 用户坐标的经度
		 */
		LONGITUDE,
		/**
		 * 用户坐标的维度
		 */
		LATITUDE,
		/**
		 * 记住当前跳转登录页面的activity
		 */
		LOGIN_ACTIVITY;
	}

	public final class getSmscodeType {
		//		获得注册验证码
		public final static int REGISTER_CODE = 0;
		//		获得找回密码验证码
		public final static int GETPWD_CODE = 1;
		//		获得手机号登录的验证码
		public final static int TELLOGIN_CODE = 2;
	}


	/**
	 * 性别
	 */
	public enum SEX {
		MEN(1, "男"), WOMEN(0, "女"), NULL(-1, "保密");

		public int key = 1;
		public String name = "男";

		SEX(int key, String name) {
			this.key = key;
			this.name = name;
		}

		public static int toint(String name) {
			if (name.equals(WOMEN.name)) {
				return WOMEN.key;
			} else if (name.equals(MEN.name)) {
				return MEN.key;
			} else if (name.equals(NULL.name)) {
				return NULL.key;
			}
			return NULL.key;
		}

	}


	/**
	 * 注册 or 重置
	 *
	 * @author gavin
	 */
	public enum RegisterOrReset {
		REGISTER(0, "注册"), RESET(1, "重置");

		private int key = 0;
		private String name = "注册";

		RegisterOrReset(int key, String name) {
			this.setKey(key);
			this.setName(name);
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}


}
