package tw.com.ezlifetech.ezReco.util;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;


/**
 * 日期格式轉換
 * <p>Title: 日期格式轉換</p>
 * <p>Description: 日期格式轉換</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: TradeVan</p>
 * @author TradeVan T40
 * @version 1.0
 */
public class DateUtil extends DateFormatUtils {
	public static final int TYPE_ADD = 1;//加
	public static final int TYPE_DEL = 2;//減
	public static final String ITEM_YEAR="YEAR";//年
	public static final String ITEM_MONTH="MONTH";//年
	public static final String ITEM_DAY="DAY";//年
	/**
	 * 取得系統日期時間
	 * @param pattern String 格式
	 * @return String 系統日期或時間
	 */
	public static String getSystemTime(String pattern) {
		return DateFormatUtils.format((new Date()), pattern);
	}
	
	/**
	 * 取得系統日期和時間
	 * <p>格式：yyyyMMddHHmmssSS</p>
	 * <p>24小時制且到毫秒</p>
	 * @return String 系統日期和時間
	 */
	public static String getSystemDateTime() {
		return getSystemTime("yyyyMMddHHmmssSS");
	}
	public static String getSystemDateTime2() {
		return getSystemTime("yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 取得系統日期和時間
	 * <p>格式：yyyyMMddHHmmssSS</p>
	 * <p>24小時制且到毫秒</p>
	 * @return Date 系統日期和時間
	 */
	public static Date getSystemDateTimeObject() {
		try {
			return parseDay(getSystemTime("yyyyMMddHHmmssSS"), "yyyyMMddHHmmssSS");
		} catch (ParseException pex) {
			return null;
		}
	}
	
	/**
	 * 取得系統年份
	 * <p>格式：yyyy</p>
	 * @return String 系統年份
	 */
	public static String getSystemYear() {
		return getSystemTime("yyyy");
	}
	
	/**
	 * 取得系統民國年
	 * @return String 系統民國年
	 */
	public static String getSystemChineseYear() {
		try {
			int year = (Integer.parseInt(getSystemTime("yyyy"))-1911);
			return String.valueOf(year);
		} catch(NumberFormatException nfex) {
			return "";
		}
	}
	
	/**
	 * 取得系統月份
	 * <p>格式：yyyyMM</p>
	 * @return String 系統月份
	 */
	public static String getSystemMonth() {
		return getSystemTime("yyyyMM");
	}
	
	/**
	 * 取得系統民國年和月份
	 * @return String 系統民國年和月份
	 */
	public static String getSystemChineseMonth() {
		try {
			int year = (Integer.parseInt(getSystemTime("yyyy"))-1911);
			return String.valueOf((year+""+getSystemTime("MM")));
		} catch(NumberFormatException nfex) {
			return "";
		}
	}
	
	/**
	 * 取得系統日期
	 * <p>格式：yyyyMMdd</p>
	 * @return String 系統日期
	 */
	public static String getSystemDate() {
		return getSystemTime("yyyyMMdd");
	}
	/**
	 * 取得系統日期
	 * <p>格式：yyyy/MM/dd</p>
	 * @return String 系統日期
	 */
	public static String getSystemDate2() {
		return getSystemTime("yyyy/MM/dd");
	}
	
	/**
	 * 將yyyymmdd格式的資料轉成10碼yyyy/mm/dd的格式
	 * @param date
	 * @return
	 */
	public static String parseDateToTen(String date){
		String val="";
		if(StringUtil.isNotBlank(date) && date.length() == 8){
			val=date.substring(0,4)+"/"+date.substring(4,6)+"/"+date.substring(6);
		}
		return val;
	}
	
	
	/**
	 * 取得系統民國年和日期
	 * @return String 系統民國年和日期
	 */
	public static String getSystemChineseDate() {
		try {
			int year = (Integer.parseInt(getSystemTime("yyyy"))-1911);
			return String.valueOf((year+""+getSystemTime("MMdd")));
		} catch(NumberFormatException nfex) {
			return "";
		}
	}
	
	/**
	 * 取得系統日期
	 * <p>格式：yyyyMMdd</p>
	 * @return Date 系統日期
	 */
	public static Date getSystemDateObject() {
		try {
			return parseDay(getSystemTime("yyyyMMdd"), "yyyyMMdd");
		} catch (ParseException pex) {
			return null;
		}
	}
	
	/**
	 * 取得系統時間
	 * <p>格式：HHmmss</p>
	 * <p>24小時制</p>
	 * @return String 系統時間
	 */
	public static String getSystemTime() {
		return getSystemTime("HHmmss");
	}
	
	/**
	 * 判斷日期是否有效
	 * @param date Object Object資料
	 * <p>Object會轉成Date或String</p>
	 * @param pattern String 格式
	 * @return boolean 日期是否有效
	 */
	public static boolean isValidDateDay(Object date, String pattern) {
		String source = getSystemDateTime();
		
		if(date instanceof Date) {
			Date dateValue = ((Date)date);
			source = DateFormatUtils.format(dateValue, pattern);
		} else {
			source = ((String)date);
		}
		
		return isValidDate(source, pattern);
	}
	
	/**
	 * 判斷日期是否有效
	 * @param source String 字串資料
	 * @param pattern String 格式
	 * @return boolean 日期是否有效
	 */
	public static boolean isValidDate(String source, String pattern) {
		boolean isVaild = false;
		SimpleDateFormat dateFormat = ((SimpleDateFormat)SimpleDateFormat.getInstance());
		dateFormat.setLenient(false);
		dateFormat.applyPattern(pattern);
		
		try {
			dateFormat.parse(source);
			isVaild = true;
		} catch (ParseException pex) {
			isVaild = false;
		}
		
		return isVaild;
	}
	
	/**
	 * 解析字串為日期
	 * @param date String 資料
	 * @param pattern String 格式
	 * @return Date 日期格式資料內容
	 * @throws ParseException
	 */
	public static Date parseDay(String date, String pattern) throws ParseException {
		String[] parsePatterns = new String[]{pattern};
		try {
			return DateUtils.parseDate(date, parsePatterns);
		} catch (ParseException pex) {
			throw pex;
		}
	}
	/**
	 * 將日期轉成字串
	 * @param date Data 資料
	 * @param pattern String 格式
	 * @return String 日期格式資料內容
	 */
	public static String parseDateToString(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
		
	}
	/**
	 * 將字串型態的日期格式成指定format
	 * @param date
	 * @param pattern
	 * @return String
	 * @throws ParseException
	 */
	public static String parseDateToString(String date,String pattern) throws ParseException{
		return parseDateToString(parseDay(date,"yyyyMMddHHmmssSS"),pattern);
	}
	
	/**
	 * 將字串型態的日期格式成指定format
	 * @param date 日期字串
	 * @param orignalPattern 日期字串舊格式
	 * @param pattern 日期字串新格式
	 * @return String
	 * @throws ParseException
	 */
	public static String parseDateToString(String date, String orignalPattern, String pattern) throws ParseException{
		return parseDateToString(parseDay(date, orignalPattern),pattern);
	}
	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int differenceSecond(Date start, Date end) {
		return ((int)(differenceBetweenDate(start, end)/1000));
	}
	
	public static int differenceMinute(Date start, Date end) {
		return ((int)(differenceSecond(start, end)/60));
	}
	
	public static int differenceHour(Date start, Date end) {
		return ((int)(differenceMinute(start, end)/60));
	}
	
	public static int differenceDay(Date start, Date end) {
		return ((int)(differenceHour(start, end)/24));
	}
	
	public static int differenceWeek(Date start, Date end) {
		return ((int)(differenceDay(start, end)/7));
	}
	
	public static int differenceMonth(Date start, Date end) {
		return ((int)(differenceDay(start, end)/30));
	}
	
	public static int differenceYear(Date start, Date end) {
		return ((int)(differenceDay(start, end)/365));
	}
	
	/**
	 * 將日期字串中的分隔符號取代掉成yyyyMMdd的格式
	 * @param value 日期資料
	 * @return String yyyyMMdd日期格式
	 */
	public static String clearDatePatten(String value){
		value = StringUtil.getParameter(value);
		value = value.replaceAll("/", "").replaceAll("-", "").replaceAll("－", "");
		value = value.replaceAll(":", "").replaceAll("：", "");
		value = value.replaceAll(" ", "").replaceAll("　", "");
    	return value;
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private static long differenceBetweenDate(Date start, Date end) {
		long different = 0;
		
		if(start!=null && end!=null) {
			long longStart = start.getTime();
			long longEnd = end.getTime();
			different = (longEnd-longStart);
		}
		
		return different;
	}
	/**
	 * 将毫秒数换算成x天x时x分x秒x毫秒
	 */
	public static String formatMS(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		   long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
		return strDay + "天" + strHour + "小時" + strMinute + "分" + strSecond + "秒." + strMilliSecond;
	}
	/**
	 * 日期加減
	 * @param date 傳入的日期，可為民國年或西元年(例：20090101或2009/01/01或0980101或098/01/01)
	 * @param type 要加或減(DateUtil.TYPE_ADD,DateUtil.TYPE_DEL)
	 * @param item 年或月或日(DateUtil.ITEM_YEAR,DateUtil.ITEM_MONTH,DateUtil.ITEM_DAY)
	 * @param num  加減的值
	 * @return 民國年格式的日期
	 */
	public static String getDateDiff(String date,int type,String item,int num) throws Exception{
		try {
			Calendar c = Calendar.getInstance();
			date=date.replaceAll("/","");//將2009/01/01或098/01/01的格式轉換
			int y=Integer.parseInt(date.substring(0,4));//取年
			int m=Integer.parseInt(date.substring(4,6));//取月
			int d=Integer.parseInt(date.substring(6,8));//取日
			
			//判斷傳入的type是年或月或日，且是要加或減，及加減的天數計算
			if(item.equals(ITEM_YEAR))
				y = (type == TYPE_ADD?y+num:y-num);
			else if(item.equals(ITEM_MONTH))
				m = (type == TYPE_ADD?m+num:m-num);
			else if(item.equals(ITEM_DAY))
				d = (type == TYPE_ADD?d+num:d-num);
			
			c.set(y, m -1, d);//因為0代表1月，所以要減1
			
			return c.get(Calendar.YEAR) 
					+ StringUtil.fillWithZero(String.valueOf(c.get(Calendar.MONTH) + 1), 2, StringUtil.PADDING_TYPE_LEFT) 
					+ StringUtil.fillWithZero(String.valueOf(c.get(Calendar.DATE)), 2, StringUtil.PADDING_TYPE_LEFT);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	/**
	 * 日期時間差(天)
	 * @param beginDate yyyyMMdd
	 * @param endDate yyyyMMdd
	 * @return String  
	 * @throws ParseException 
	 */
	public static String timeDifferenceDays(String sDate,String eDate) throws ParseException{
		long days;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = sdf.parse(sDate);
		Date endDate = sdf.parse(eDate);
		days = TimeUnit.MILLISECONDS.toDays(endDate.getTime() -beginDate.getTime());
		return String.valueOf(days);
	}
	
	/**
	 * 字串日期轉毫秒數
	 * @param dateStr
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String transferTimeToMillionSeconds(String dateStr, String pattern) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		long millionSeconds = sdf.parse(dateStr).getTime();//毫秒
		return String.valueOf(millionSeconds);
	}
	/**
	 * 當天的星期(數字轉國字)
	 * @param str
	 * @return
	 */
	public static String transferDayOfWeekIntToString(int num){
		switch (num){
			case 1:
				return "日";
			case 2:
				return "一";
			case 3:
				return "二";
			case 4:
				return "三";
			case 5:
				return "四";
			case 6:
				return "五";
			case 7:
				return "六";
			default:
				return "";
		}
	}
	/**
	 * 根據當天日期取得本週所有日期
	 * @param date 當天日期
	 * @param pattern 日期格式
	 * @return Map<Integerm String> 1:SUNDAY -> 7 SATURDAY
	 */
	public static Map<Integer, String> getSundayToSaturday(Date date, String pattern) {
		Map<Integer, String> weekDateMap = new HashMap<Integer, String>();
	
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		weekDateMap.put(Calendar.SUNDAY, parseDateToString(cal.getTime(), pattern));
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		weekDateMap.put(Calendar.MONDAY, parseDateToString(cal.getTime(), pattern));
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		weekDateMap.put(Calendar.TUESDAY, parseDateToString(cal.getTime(), pattern));
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		weekDateMap.put(Calendar.WEDNESDAY, parseDateToString(cal.getTime(), pattern));
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		weekDateMap.put(Calendar.THURSDAY, parseDateToString(cal.getTime(), pattern));
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		weekDateMap.put(Calendar.FRIDAY, parseDateToString(cal.getTime(), pattern));
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		weekDateMap.put(Calendar.SATURDAY, parseDateToString(cal.getTime(), pattern));
		
		return weekDateMap;
	}
	
	/**
	 * 根據傳入的日期取得下週時間
	 * @param date 傳入的日期
	 * @param pattern 傳入日期格式
	 * @return
	 * @throws ParseException
	 */
	public static String getNextWeekDate(String date, String pattern) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.parseDay(date, pattern));
		cal.add(Calendar.DATE,6);
		String nextDate = parseDateToString(cal.getTime(), pattern);
		return nextDate;
	}
	
	/**
	 * 根據傳入的日期取得下個月該時間前一天
	 * @param date 傳入的日期
	 * @param day 該日期幾號
	 * @param pattern 傳入日期格式
	 * @return
	 * @throws ParseException
	 */
	public static String getNextMonthDate(String date, Integer day, String pattern) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDay(date, pattern));
		cal.add(Calendar.MONTH,1);//加一个月
		cal.set(Calendar.DATE, day);//把日期设置为当月第一天
		cal.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
		String nextDate = parseDateToString(cal.getTime(), pattern);
		return nextDate;
	}
	
	/**
	 * 根據傳入的日期取得上週時間
	 * @param date 傳入的日期
	 * @param pattern 傳入日期格式
	 * @return
	 * @throws ParseException
	 */
	public static String getPrevWeekDate(String date, String pattern) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDay(date, pattern));
		cal.add(Calendar.DATE,-6);
		String nextDate = parseDateToString(cal.getTime(), pattern);
		return nextDate;
	}
	
	/**
	 * 根據傳入的日期取得上個月該時間後一天
	 * @param date 傳入的日期
	 * @param day 該日期幾號
	 * @param pattern 傳入日期格式
	 * @return
	 * @throws ParseException
	 */
	public static String getPrevMonthDate(String date, Integer day, String pattern) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDay(date, pattern));
		cal.add(Calendar.MONTH,-1);//加一个月
		cal.set(Calendar.DATE, day);//把日期设置为当月第一天
		cal.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
		String nextDate = parseDateToString(cal.getTime(), pattern);
		return nextDate;
	}
	
	/**
	 * 根據傳入的日期取得一週的時間
	 * @param date 傳入的日期
	 * @param pattern 傳入日期格式
	 * @return
	 * @throws ParseException
	 */
	public static String getWeekDate(String date, String pattern) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.parseDay(date, pattern));
		cal.add(Calendar.DATE,7);
		String nextDate = parseDateToString(cal.getTime(), pattern);
		return nextDate;
	}
	
	/**
	 * 根據傳入的日期取前或後N天的時間
	 * @param date
	 * @param pattern
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static String getPrevDayDate(String date,String pattern, Integer day) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.parseDay(date, pattern));
		cal.add(Calendar.DATE, day);
		String nextDate = parseDateToString(cal.getTime(), pattern);
		return nextDate;
	}
	
}
