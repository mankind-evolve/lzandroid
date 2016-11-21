package com.lazy.android.basefunc.LZUtils;

import android.content.Context;

import com.lazy.android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author
 * @ClassName: DateUtils
 * @Description: 时间相关
 */
public final class UtilsDate {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat sdfYearMonth = new SimpleDateFormat("yyyy年MM月");
	private final static SimpleDateFormat sdfYearMonthEn = new SimpleDateFormat("yyyy-MM");
	private final static SimpleDateFormat sdfYearMonthDay = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdfYearMonthDayCn = new SimpleDateFormat("yyyy年MM月dd日");
	private final static SimpleDateFormat sdfHourMinute = new SimpleDateFormat("HH:mm");
	private final static SimpleDateFormat sdfYMDHM = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
	private final static SimpleDateFormat sdfMD = new SimpleDateFormat("MM.dd");
	private final static SimpleDateFormat sdfE = new SimpleDateFormat("E");
	private final static SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
	private final static SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 时间格式化成 yyyy-MM-dd HH:mm
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String getYYYYMMDDHHmm(long timeMillis) {
		if (timeMillis <= 0) {
			return "";
		}
		Date date = new Date(timeMillis);
		String time = sdf.format(date);
		return time;
	}

	/**
	 * 时间格式化 yyyyMMDD
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String getYMD(long timeMillis) {
		if (timeMillis <= 0) {
			return "";
		}
		Date date = new Date(timeMillis);
		String time = sdfYMD.format(date);
		return time;
	}

	/**
	 * yyyy年MM月
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String getYYMMCn(long timeMillis) {
		if (timeMillis <= 0) {
			return "";
		}
		Date date = new Date(timeMillis);
		String time = sdfYearMonth.format(date);
		return time;
	}

	/**
	 * yyyy-MM
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String getYYMMEn(long timeMillis) {
		if (timeMillis <= 0) {
			return "";
		}
		Date date = new Date(timeMillis);
		String time = sdfYearMonthEn.format(date);
		return time;
	}

	/**
	 * yyyy-MM-dd
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String getYYYYMMDD(long timeMillis) {
		if (timeMillis <= 0) {
			return "";
		}
		Date date = new Date(timeMillis);
		String time = sdfYearMonthDay.format(date);
		return time;
	}

	/**
	 * yyyy年MM月dd日
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String getYYYYMMDDCN(long timeMillis) {
		if (timeMillis <= 0) {
			return "";
		}
		Date date = new Date(timeMillis);
		String time = sdfYearMonthDayCn.format(date);
		return time;
	}

	/**
	 * HH:mm
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String getHHmm(long timeMillis) {
		if (timeMillis <= 0) {
			return "";
		}
		Date date = new Date(timeMillis);
		String time = sdfHourMinute.format(date);
		return time;
	}

	/**
	 * 返回年号 yyyy
	 *
	 * @return
	 */
	public static String getCurrentYear() {
		Date date = new Date(System.currentTimeMillis());
		String time = sdfYear.format(date);
		return time;
	}

	/**
	 * 2014年1月11日 14时20分
	 * yyyy年MM月 HH时mm分
	 *
	 * @return
	 */
	public static String getYMDHM(long timeMillis) {
		if (timeMillis <= 0) {
			return "";
		}
		Date date = new Date(timeMillis);
		String time = sdfYMDHM.format(date);
		return time;
	}

	public static String getMd(long timeMillis) {
		if (timeMillis <= 0) {
			return "";
		}
		Date date = new Date(timeMillis);
		String time = sdfMD.format(date);
		return time;
	}

	public static String getE(long timeMillis) {
		if (timeMillis <= 0) {
			return "";
		}
		Date date = new Date(timeMillis);
		String time = sdfE.format(date);
		return time;
	}

	public static String getHour(long timeMillis) {
		if (timeMillis <= 0) {
			return "";
		}
		Date date = new Date(timeMillis);
		String time = sdfHour.format(date);
		return time;
	}

	/**
	 * @param beginTime 开始时间
	 * @param endTime   结束时间
	 * @return 相差的年数(endTime - beginTime)。如果beginTime > endTime，则返回相差年数的负值
	 * @Title: getIntervalYear
	 * @Description: 计算两个时间相差的年数
	 */
	public static int getIntervalYear(long beginTime, long endTime) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(beginTime);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(endTime);
		return cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
	}

	public static int getIntervalDay(long beginTime, long endTime) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(beginTime);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(endTime);

		return cal2.get(Calendar.DAY_OF_YEAR) - cal1.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * @param time1 the first date(UTC milliseconds)
	 * @param time2 the second date(UTC milliseconds)
	 * @return true
	 * @Title: isSampleDay
	 * @Description: 是否是同一天
	 */
	public static boolean isSameDay(long time1, long time2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(time1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(time2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * <p>
	 * Checks if two dates are on the same day ignoring time.
	 * </p>
	 *
	 * @param date1 the first date, not altered, not null
	 * @param date2 the second date, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException if either date is <code>null</code>
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * <p>
	 * Checks if two calendars represent the same day ignoring time.
	 * </p>
	 *
	 * @param cal1 the first calendar, not altered, not null
	 * @param cal2 the second calendar, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException if either calendar is <code>null</code>
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
			.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * <p>
	 * Checks if a date is today.
	 * </p>
	 *
	 * @param date the date, not altered, not null.
	 * @return true if the date is today.
	 * @throws IllegalArgumentException if the date is <code>null</code>
	 */
	public static boolean isToday(Date date) {
		return isSameDay(date, Calendar.getInstance().getTime());
	}

	/**
	 * <p>
	 * Checks if a calendar date is today.
	 * </p>
	 *
	 * @param cal the calendar, not altered, not null
	 * @return true if cal date is today
	 * @throws IllegalArgumentException if the calendar is <code>null</code>
	 */
	public static boolean isToday(Calendar cal) {
		return isSameDay(cal, Calendar.getInstance());
	}

	/**
	 * <p>
	 * Checks if the first date is before the second date ignoring time.
	 * </p>
	 *
	 * @param date1 the first date, not altered, not null
	 * @param date2 the second date, not altered, not null
	 * @return true if the first date day is before the second date day.
	 * @throws IllegalArgumentException if the date is <code>null</code>
	 */
	public static boolean isBeforeDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isBeforeDay(cal1, cal2);
	}

	/**
	 * <p>
	 * Checks if the first calendar date is before the second calendar date
	 * ignoring time.
	 * </p>
	 *
	 * @param cal1 the first calendar, not altered, not null.
	 * @param cal2 the second calendar, not altered, not null.
	 * @return true if cal1 date is before cal2 date ignoring time.
	 * @throws IllegalArgumentException if either of the calendars are
	 *                                  <code>null</code>
	 */
	public static boolean isBeforeDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA))
			return true;
		if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA))
			return false;
		if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR))
			return true;
		if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR))
			return false;
		return cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * <p>
	 * Checks if the first date is after the second date ignoring time.
	 * </p>
	 *
	 * @param date1 the first date, not altered, not null
	 * @param date2 the second date, not altered, not null
	 * @return true if the first date day is after the second date day.
	 * @throws IllegalArgumentException if the date is <code>null</code>
	 */
	public static boolean isAfterDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isAfterDay(cal1, cal2);
	}

	/**
	 * <p>
	 * Checks if the first calendar date is after the second calendar date
	 * ignoring time.
	 * </p>
	 *
	 * @param cal1 the first calendar, not altered, not null.
	 * @param cal2 the second calendar, not altered, not null.
	 * @return true if cal1 date is after cal2 date ignoring time.
	 * @throws IllegalArgumentException if either of the calendars are
	 *                                  <code>null</code>
	 */
	public static boolean isAfterDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA))
			return false;
		if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA))
			return true;
		if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR))
			return false;
		if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR))
			return true;
		return cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * <p>
	 * Checks if a date is after today and within a number of days in the
	 * future.
	 * </p>
	 *
	 * @param date the date to check, not altered, not null.
	 * @param days the number of days.
	 * @return true if the date day is after today and within days in the future
	 * .
	 * @throws IllegalArgumentException if the date is <code>null</code>
	 */
	public static boolean isWithinDaysFuture(Date date, int days) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return isWithinDaysFuture(cal, days);
	}

	/**
	 * <p>
	 * Checks if a calendar date is after today and within a number of days in
	 * the future.
	 * </p>
	 *
	 * @param cal  the calendar, not altered, not null
	 * @param days the number of days.
	 * @return true if the calendar date day is after today and within days in
	 * the future .
	 * @throws IllegalArgumentException if the calendar is <code>null</code>
	 */
	public static boolean isWithinDaysFuture(Calendar cal, int days) {
		if (cal == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar today = Calendar.getInstance();
		Calendar future = Calendar.getInstance();
		future.add(Calendar.DAY_OF_YEAR, days);
		return (isAfterDay(cal, today) && !isAfterDay(cal, future));
	}

	/**
	 * Returns the given date with the time set to the start of the day.
	 */
	public static Date getStart(Date date) {
		return clearTime(date);
	}

	/**
	 * Returns the given date with the time values cleared.
	 */
	public static Date clearTime(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * Determines whether or not a date has any time values (hour, minute,
	 * seconds or millisecondsReturns the given date with the time values
	 * cleared.
	 */

	/**
	 * Determines whether or not a date has any time values.
	 *
	 * @param date The date.
	 * @return true iff the date is not null and any of the date's hour, minute,
	 * seconds or millisecond values are greater than zero.
	 */
	public static boolean hasTime(Date date) {
		if (date == null) {
			return false;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (c.get(Calendar.HOUR_OF_DAY) > 0) {
			return true;
		}
		if (c.get(Calendar.MINUTE) > 0) {
			return true;
		}
		if (c.get(Calendar.SECOND) > 0) {
			return true;
		}
		if (c.get(Calendar.MILLISECOND) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the given date with time set to the end of the day
	 */
	public static Date getEnd(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * Returns the maximum of two dates. A null date is treated as being less
	 * than any non-null date.
	 */
	public static Date max(Date d1, Date d2) {
		if (d1 == null && d2 == null)
			return null;
		if (d1 == null)
			return d2;
		if (d2 == null)
			return d1;
		return (d1.after(d2)) ? d1 : d2;
	}

	/**
	 * Returns the minimum of two dates. A null date is treated as being greater
	 * than any non-null date.
	 */
	public static Date min(Date d1, Date d2) {
		if (d1 == null && d2 == null)
			return null;
		if (d1 == null)
			return d2;
		if (d2 == null)
			return d1;
		return (d1.before(d2)) ? d1 : d2;
	}

	/**
	 * The maximum date possible.
	 */
	public static Date MAX_DATE = new Date(Long.MAX_VALUE);


	/**
	 * @param
	 * @return 星座
	 * @Title: getConstellation
	 * @Description: 根据月与日得到星座
	 */
	public static String getAstro(Context context, int month, int day) {
//		星座     日期(公历)    英文名 
//		魔羯座 (12/22 - 1/19) Capricorn 
//		水瓶座 (1/20 - 2/18) Aquarius 
//		双鱼座 (2/19 - 3/20) Pisces 
//		牡羊座 (3/21 - 4/20) Aries 
//		金牛座 (4/21 - 5/20) Taurus 
//		双子座 (5/21 - 6/21) Gemini 
//		巨蟹座 (6/22 - 7/22) Cancer 
//		狮子座 (7/23 - 8/22) Leo 
//		处女座 (8/23 - 9/22) Virgo 
//		天秤座 (9/23 - 10/22) Libra 
//		天蝎座 (10/23 - 11/21) Scorpio 
//		射手座 (11/22 - 12/21) Sagittarius

		final String[] astroArr = context.getResources().getStringArray(R.array.astros);
		//月份                                1   2   3   4   5   6   7   8   9   10  11  12
		final int[] constellationEdgeDay = {19, 18, 20, 19, 20, 21, 22, 22, 22, 23, 22, 21};
		if (day > 31 || day < 1)
			return "";
		else {
			switch (month) {
				case 1:
					if (day <= constellationEdgeDay[0])
						return astroArr[0];
					else
						return astroArr[1];
				case 2:
					if (day <= constellationEdgeDay[1])
						return astroArr[1];
					else
						return astroArr[2];
				case 3:
					if (day <= constellationEdgeDay[2])
						return astroArr[2];
					else
						return astroArr[3];
				case 4:
					if (day <= constellationEdgeDay[3])
						return astroArr[3];
					else
						return astroArr[4];
				case 5:
					if (day <= constellationEdgeDay[4])
						return astroArr[4];
					else
						return astroArr[5];
				case 6:
					if (day <= constellationEdgeDay[5])
						return astroArr[5];
					else
						return astroArr[6];
				case 7:
					if (day <= constellationEdgeDay[6])
						return astroArr[6];
					else
						return astroArr[7];
				case 8:
					if (day <= constellationEdgeDay[7])
						return astroArr[7];
					else
						return astroArr[8];
				case 9:
					if (day <= constellationEdgeDay[8])
						return astroArr[8];
					else
						return astroArr[9];
				case 10:
					if (day <= constellationEdgeDay[9])
						return astroArr[9];
					else
						return astroArr[10];
				case 11:
					if (day <= constellationEdgeDay[10])
						return astroArr[10];
					else
						return astroArr[11];
				case 12:
					if (day <= constellationEdgeDay[11])
						return astroArr[11];
					else
						return astroArr[0];
				default:
					return "";
			}
		}
	}



		public static String getStringData(String type,int n){
			Calendar c = Calendar.getInstance();
			c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
			c.add(Calendar.DATE,n);
			String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
			String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
			if(Integer.valueOf(mMonth) < 10 ){
				mMonth = "0" + mMonth;
			}
			String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
			if(Integer.valueOf(mDay) < 10){
				mDay = "0" + mDay;
			}
			String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
			if("1".equals(mWay)){
				mWay ="周日";
			}else if("2".equals(mWay)){
				mWay ="周一";
			}else if("3".equals(mWay)){
				mWay ="周二";
			}else if("4".equals(mWay)){
				mWay ="周三";
			}else if("5".equals(mWay)){
				mWay ="周四";
			}else if("6".equals(mWay)){
				mWay ="周五";
			}else if("7".equals(mWay)){
				mWay ="周六";
			}
			if(type.equals("week")){
				return mWay;
			}else if(type.equals("year")){
				return mYear;
			}else if(type.equals("month")){
				return mMonth;
			}else if(type.equals("date")){
				return mDay;
			}else if(type.equals("month.date")){
				return mMonth+"."+mDay;
			}else if(type.equals("datenum")){
				return mYear + mMonth + mDay;
			}
			return mYear + "年" + mMonth + "月" + mDay+"日"+"/星期"+mWay;
		}


	public static String judgeWeek(long systemTime){
		Calendar calendar = Calendar.getInstance();
		String str = "星期";
		Date date = new Date(systemTime);
		calendar.setTime(date);
		switch (calendar.get(Calendar.DAY_OF_WEEK)){
			case 1:
				str += "天";
				break;
			case 2:
				str += "一";
				break;
			case 3:
				str += "二";
				break;
			case 4:
				str += "三";
				break;
			case 5:
				str += "四";
				break;
			case 6:
				str += "五";
				break;
			case 7:
				str += "六";
				break;
			default:
				break;
		}
		return str;
	}




	public static int getWeekint(long systemTime){
		Calendar calendar = Calendar.getInstance();
		int str = 1;
		Date date = new Date(systemTime);
		calendar.setTime(date);
		switch (calendar.get(Calendar.DAY_OF_WEEK)){
			case 1:
				str = 7;
				break;
			case 2:
				str = 1;
				break;
			case 3:
				str = 2;
				break;
			case 4:
				str = 3;
				break;
			case 5:
				str = 4;
				break;
			case 6:
				str = 5;
				break;
			case 7:
				str = 6;
				break;
			default:
				break;
		}
		return str;
	}



	/**
	 * 根据用户生日计算年龄
	 */
	public static int getAgeByBirthday(Long birthdaylong) {
		Date birthday = new Date(birthdaylong);
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			throw new IllegalArgumentException(
				"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}




}
