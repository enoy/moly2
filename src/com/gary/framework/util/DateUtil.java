package com.gary.framework.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 * @author gary
 *
 */
public class DateUtil {
	
	/**
	 * 获取年
	 * @return
	 */
	public static int getYear(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	/**
	 * 获取月
	 * @return
	 */
	public static int getMonth(){
		return Calendar.getInstance().get(Calendar.MONTH )+1;
	}
	
	/**
	 * 获取日
	 * @return
	 */
	public static int getDate(){
		return Calendar.getInstance().get(Calendar.DATE);
	}
	
	/**
	 * 获取时
	 * @return
	 */
	public static int getHour(){
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取分
	 * @return
	 */
	public static int getMinute(){
		return Calendar.getInstance().get(Calendar.MINUTE);
	}
	
	/**
	 * 获取秒
	 * @return
	 */
	public static int getSecond(){
		return Calendar.getInstance().get(Calendar.SECOND);
	}
	
	/**
	 * 获取yyyy-mm格式年+月
	 * @return
	 */
	public static String getYear_Month(){
		Calendar cal = Calendar.getInstance();
		int month = getMonth();
		if(month < 10){
			return String.valueOf(cal.get(Calendar.YEAR)) + "-0" +String.valueOf(month);
		}else{
			return String.valueOf(cal.get(Calendar.YEAR)) + "-" +String.valueOf(month);
		}
	}
	
	/**
	 * 获取yyyy-mm-dd格式年+月+日
	 * @return
	 */
	public static String getYearMonthDay(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}
	
	
	public static String getYesterdayYearMonthDay(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 获取当前java.util.Date类型时间
	 * @return
	 */
	public static Date getCurrentDate(){
		return new Date();
	}
	
	/**
	 * 获取当前Timestamp类型时间
	 * @return
	 */
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(getCurrentDate().getTime());
	}
	
	public static String getCurrentDateStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(getCurrentDate());
	}
	
	public static String getDateStr(Date d){
		if(d == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}
	
	/**
	 * 计算两个日期的相隔天数
	 * @param d1
	 * 			日期1
	 * @param d2
	 * 			日期2
	 * @return
	 */
	public static int getDaysBetween(Calendar d1, Calendar d2) {
		if (d1.after(d2)) {
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}
	
	public static int getDaysBetween(String d1, String d2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal1 = Calendar.getInstance();
		cal1.clear();
		Calendar cal2 = (Calendar) cal1.clone();
		try {
			cal1.setTime(sdf.parse(d1));
			cal2.setTime(sdf.parse(d2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getDaysBetween(cal1, cal2);
	}

	/**
	 * 计算2个日期之间的工作日相隔天数
	 * @param d1
	 * 			日期1
	 * @param d2
	 *			日期2 
	 * @return
	 */
	public static int getWorkingDay(Calendar d1, Calendar d2) {
		int result = -1;
		if (d1.after(d2)) {
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		
		//开始日期的日期偏移量
		int charge_start_date = 0;
		//结束日期的日期偏移量
		int charge_end_date = 0;
		// 日期不在同一个日期内
		int stmp;
		int etmp;
		stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
		// 开始日期为星期六和星期日时偏移量为0
		if (stmp != 0 && stmp != 6) {
			charge_start_date = stmp - 1;
		}
		// 结束日期为星期六和星期日时偏移量为0
		if (etmp != 0 && etmp != 6) {
			charge_end_date = etmp - 1;
		}
		result = (getDaysBetween(getNextMonday(d1), getNextMonday(d2)) / 7)
			* 5 + charge_start_date - charge_end_date;
		return result + 1;
	}
	
	/**
	 * 获得休息日
	 * @param d1
	 * 			日期1
	 * @param d2
	 * 			日期2
	 * @return
	 */
	public static int getHolidays(Calendar d1,Calendar d2){
		return getDaysBetween(d1, d2) - getWorkingDay(d1, d2);
	}
	
	/**
	 * 获得日期的下一个星期一的日期
	 * @param date
	 * @return
	 */
	public static Calendar getNextMonday(Calendar date) {
		Calendar result = null;
		result = date;
		do {
			result = (Calendar) result.clone();
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	} 
	
	/**
	 * 获取指定年-月(yyyy-mm)的工作日天数
	 * @param year_month
	 * @return
	 */
	public static int getWorkingDayOfMonth(String year_month){
		int year = Integer.parseInt(year_month.substring(0,year_month.indexOf("-")));
		int month = Integer.parseInt(year_month.substring(year_month.indexOf("-") + 1));
		Calendar start = Calendar.getInstance();
		start.clear();
		start.set(Calendar.YEAR, year);
		start.set(Calendar.MONTH, month - 1);
		start.set(Calendar.DAY_OF_MONTH, 1);
		
		Calendar end = Calendar.getInstance();
		end.clear();
		end = (Calendar) start.clone();
		end.add(Calendar.MONTH,1);
		end.add(Calendar.DAY_OF_MONTH,-1);
		
		return getWorkingDay(start,end);
	}
	
	/**
	 * 设置日期
	 * @param year
	 * 			年
	 * @param month
	 * 			月
	 * @param date
	 * 			日
	 * @return
	 */
	public static Calendar setCalendar(int year, int month, int date){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, date);
		return calendar;
	}
	
	/**
	 * 输出yyyy-mm-dd格式日期
	 * @param calendar
	 * @return
	 */
	public static String printCalendar(Calendar calendar){
		StringBuffer sb = new StringBuffer();
		sb.append(calendar.get(Calendar.YEAR));
		sb.append("-");
		sb.append(calendar.get(Calendar.MONTH) + 1);
		sb.append("-");
		sb.append(calendar.get(Calendar.DATE));
		return sb.toString();
	}
	
	/**
	 * 输出日期
	 * @param d
	 * @return
	 */
	public static String printDate(Date d){
		if(d == null){
			return null;
		}else{
			DateFormat formatter = DateFormat.getDateTimeInstance();
			return formatter.format(d);
		}
	}
	
	/**
	 * 时间比较,精确到分钟
	 * @param time1
	 * @param time2
	 * @return 
	 * 		大于0:time1>time2,等于0:time1==time2,小于0:time1<time2
	 */
	public static int compareTime(String time1, String time2){
		int hour1 = Integer.parseInt(time1.substring(0,time1.indexOf(':')));
		int hour2 = Integer.parseInt(time2.substring(0,time2.indexOf(':')));
		if(hour1 != hour2){
			return hour1 - hour2;
		}else{
			int minutes1 = Integer.parseInt(time1.substring(time1.indexOf(':')+1));
			int minutes2 = Integer.parseInt(time2.substring(time2.indexOf(':')+1));
			return minutes1 - minutes2;
		}
	}
	
	/**
	 * 显示友好的时间差
	 * @param from
	 * @param to
	 * @return
	 */
	public static String getTimeBetween(java.util.Date from, java.util.Date to){
		long millisecondsBetween = Math.abs(from.getTime() - to.getTime());
		long timeBetween = millisecondsBetween / 1000;
		return parseNiceTime(timeBetween);
	}
	
	/**
	 * Hibernate日期工具
	 * @param dateStr
	 * 			日期字符串，YYYY-MM-DD格式
	 * @return	
	 * 			日期数组,Date[0]=YYYY-MM-DD 0:00:00,Date[1]=YYYY-MM-DD 23:59:59
	 */
	public static Date[] hibernateDateHelper(String dateStr){
		Date[] dateArray = new Date[2];
		dateArray[0] = parseDate(dateStr);
		dateArray[0] = getDateStart(dateArray[0]);
		dateArray[1] = getDateEnd(dateArray[0]);
		return dateArray;
	}
	
	/**
	 * 格式化日期
	 * @param dateStr
	 * 			String 字符型日期
	 * @param format
	 * 			String 格式
	 * @return Date
	 * 			日期
	 */
	public static Date parseDate(String dateStr, String format) {
		DateFormat dateFormat = null;
		Date date = null;
		try {
			dateFormat = new SimpleDateFormat(format);
			String dt = dateStr.replaceAll("-", "/");
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
			}
			date = (Date) dateFormat.parse(dt);
		} catch (Exception e) {
		}
		return date;
	}
	
	/**
	 * 获取日期最早时间，如传入2010-12-26，返回2010-12-26 0:00:00
	 * @param date
	 * @return
	 */
	public static Date getDateStart(String d){
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(d));
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 获取日期最早时间，如传入2010-12-26，返回2010-12-26 0:00:00
	 * @param d
	 * @return
	 */
	public static Timestamp getTimestampStart(String d){
		return new Timestamp(getDateStart(d).getTime());
	}
	
	/**
	 * 获取日期最早时间，如传入2010-12-26，返回2010-12-26 0:00:00
	 * @param date
	 * @return
	 */
	public static Date getDateStart(Date d){
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 获取日期最晚时间，如传入2010-12-26，返回2010-12-26 23:59:59
	 * @param date
	 * @return
	 */
	public static Date getDateEnd(String d){
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(d));
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	/**
	 * 获取日期最晚时间，如传入2010-12-26，返回2010-12-26 23:59:59
	 * @param date
	 * @return
	 */
	public static Timestamp getTimestampEnd(String d){
		return new Timestamp(getDateEnd(d).getTime());
	}
	
	/**
	 * 获取日期最晚时间，如传入2010-12-26，返回2010-12-26 23:59:59
	 * @param date
	 * @return
	 */
	public static Date getDateEnd(Date d){
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 格式化日期
	 * @param dateStr
	 * 			String 字符型日期：YYYY-MM-DD 格式
	 * @return Date
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy/MM/dd");
	}

	/**
	 * 获取当前系统的UNIX时间戳
	 * @return
	 */
	public static long getCurrentUnixTime(){
		return System.currentTimeMillis()/1000;
	}


	/**
	 * Unix时间转换成yyyy-MM-dd HH:mm:ss格式
	 * @param time
	 * @return
	 */
	public static String setUnixTime(long time){
		return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date(time));
	}
	
	/**
	 * 将秒转化成友好的时间
	 * @param time
	 * 		秒
	 * @return
	 */
	public static String parseNiceTime(long time){
		long day = time / 86400;
		String result = day + "天";
		
		time = time - day * 86400;
		
		long hour = time / 3600;
		result = result + hour + "时";
		
		time = time - hour * 3600;
		
		long minute = time / 60;
		result = result + minute + "分";
		
		time = time - minute * 60;
		
		long second = time;
		result = result + second + "秒";
		
		return result;
	}
	
	public static int getMaxDate(String year,String month){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
}
