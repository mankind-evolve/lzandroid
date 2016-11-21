package com.lazy.android.config;

public final class mConfigProtocolType {

//	获得http协议内容的请求
	public static final int TEST = 1000;





	/**
	 *  登录
	 *
	 */
	public static final int LOGIN = 0;


	/**
	 *  初始化
	 *
	 */
	public static final int INIT = LOGIN + 1;


	/**
	 *  新版本升级
	 *
	 */
	public static final int UPLOAD = INIT + 1;

	/**
	 *  配置
	 *
	 */
	public static final int CONFIG = UPLOAD + 1;

	/**
	 *  登录
	 *
	 */
	public static final int LOGINOUT = CONFIG + 1;

	/**
	 *  登录
	 *
	 */
	public static final int RESETPASSWORD = LOGINOUT + 1;

	/**
	 *  获取用户信息
	 *
	 */
	public static final int GET_USER_INFO = RESETPASSWORD + 1;

	/**
	 *  修改用户信息
	 *
	 */
	public static final int EDIT_USER_INFO = GET_USER_INFO + 1;

	/**
	 *  修改商户信息
	 *
	 */
	public static final int EDIT_COMPANY_INFO = EDIT_USER_INFO + 1;


	/**
	 *  提交用户信息
	 *
	 */
	public static final int POST_USER_INFO = EDIT_COMPANY_INFO + 1;

	/**
	 *  获取工作安排列表
	 *
	 */
	public static final int WORK_TODAY_LIST = POST_USER_INFO + 1;


	/**
	 *  获取工作管理列表
	 *
	 */
	public static final int SET_WORK_INDEX = WORK_TODAY_LIST + 1;


	/**
	 *  获取工作详情
	 *
	 */
	public static final int WORK_INFO = SET_WORK_INDEX + 1;


	/**
	 *  管理工作编辑
	 *
	 */
	public static final int WORK_EDIT = WORK_INFO + 1;


	/**
	 *  管理工作新增
	 *
	 */
	public static final int WORK_ADD = WORK_EDIT + 1;


	/**
	 *  管理学生编辑
	 *
	 */
	public static final int STUDENT_ADD = WORK_ADD + 1;


	/**
	 *  管理学生新增
	 *
	 */
	public static final int STUDENT_EDIT = STUDENT_ADD + 1;


	/**
	 *  管理学生删除
	 *
	 */
	public static final int STUDENT_DEL = STUDENT_ADD + 1;



	/**
	 * 菜谱编辑
	 *
	 */
	public static final int DIET_ADD = STUDENT_DEL + 1;


	/**
	 *  菜谱新增
	 *
	 */
	public static final int DIET_EDIT = DIET_ADD + 1;


	/**
	 *  菜谱删除
	 *
	 */
	public static final int DIET_DEL = DIET_EDIT + 1;



	/**
	 *  菜谱详情
	 *
	 */
	public static final int DIET_DETAIL = DIET_DEL + 1;


	/**
	 *  管理工作删除
	 *
	 */
	public static final int WORK_DEL = DIET_DEL + 1;

	/**
	 *  获取处理人列表
	 *
	 */
	public static final int USER_LIST = WORK_DEL + 1;

	/**
	 *  获取学生列表
	 *
	 */
	public static final int STUDENTS_LIST = USER_LIST + 1;

	/**
	 *  获取学生列表
	 *
	 */
	public static final int STUDENT_LIST = STUDENTS_LIST + 1;


	/**
	 *  获取接送记录列表
	 *
	 */
	public static final int RECEIVE_STUDENTLIST = STUDENT_LIST + 1;




	/**
	 *  获取今日教辅列表
	 *
	 */
	public static final int EDUCATION_TODAY = RECEIVE_STUDENTLIST + 1;



	/**
	 *  获取今日教辅详情
	 *
	 */
	public static final int EDUCATION_TODAYRESULT = EDUCATION_TODAY + 1;



	/**
	 *  获取教辅详情
	 *
	 */
	public static final int EDUCATION_INFO = EDUCATION_TODAYRESULT + 1;



	/**
	 *  获取作业列表详情
	 *
	 */
	public static final int EDUCATION_INFO_SCHOOL = EDUCATION_INFO + 1;



	/**
	 *  获取采购列表
	 *
	 */
	public static final int BUYRECORDER = EDUCATION_INFO_SCHOOL + 1;



	/**
	 *  接到的学生
	 *
	 */
	public static final int RECEIVE_STUDENTLIST_DOWN = BUYRECORDER + 1;


	/**
	 *  获取学生信息
	 *
	 */
	public static final int STUDENT_GETINFO = RECEIVE_STUDENTLIST_DOWN + 1;


	/**
	 *  验证接送学生的验证码
	 *
	 */
	public static final int RECEIVE_CODE = STUDENT_GETINFO + 1;


	/**
	 *  提交学生异常
	 *
	 */
	public static final int STUDENT_ERROR = RECEIVE_CODE + 1;


	/**
	 *  获取家长信息
	 *
	 */
	public static final int PARENTS_GETINFO = STUDENT_ERROR + 1;




	/**
	 *  任务其他
	 *
	 */
	public static final int WORK_OTHER = PARENTS_GETINFO + 1;


	/**
	 *  采购历史
	 *
	 */
	public static final int BUY_HISTORY = WORK_OTHER + 1;



	/**
	 *  增加采购记录
	 *
	 */
	public static final int BUY_ADD = BUY_HISTORY + 1;





	/**
	 *  增加多条采购记录
	 *
	 */
	public static final int BUY_ADD_MORE = BUY_ADD + 1;



	/**
	 *  新增员工
	 *
	 */
	public static final int EMPLOYEEINFO_ADD = BUY_ADD_MORE + 1;




	/**
	 *  查找员工记录
	 *
	 */
	public static final int EMPLOYEEINFO_SELECT = EMPLOYEEINFO_ADD + 1;



	/**
	 *  删除员工记录
	 *
	 */
	public static final int EMPLOYEEINFO_DEL = EMPLOYEEINFO_SELECT + 1;



	/**
	 *  修改员工记录
	 *
	 */
	public static final int EMPLOYEEINFO_EDIT = EMPLOYEEINFO_DEL + 1;


	/**
	 *  获取员工列表
	 *
	 */
	public static final int EMPLOYEEINFO_LIST = EMPLOYEEINFO_EDIT + 1;



	/**
	 *  营业设置
	 *
	 */
	public static final int SET_WORKSET = EMPLOYEEINFO_LIST + 1;



}
