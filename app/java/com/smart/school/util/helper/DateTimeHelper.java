package com.smart.school.util.helper;

import android.content.Context;

import com.smart.school.R;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeHelper {
	public final static String DEFUALT_DATE_FORMAT = "yyyyMMdd";

	public static String date(String format) {
		try {
			Date date = new Date();
			SimpleDateFormat simple_date_format = new SimpleDateFormat(format);
			return simple_date_format.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 현재 날짜 및 시간을 구한다. 사용법 : DateUtil.date(포맷을 정해준다) <br>
	 * DateUtil.date("yyyyMMddhhmmss") <br>
	 * DateUtil.date("yyyy.MM.dd hh:mm:ss") <br>
	 * 
	 * @param format
	 *            출력포맷 (API에서 SimpleDateFormat 참조)
	 * @return 포맷에 맞게 현재시간을 리턴
	 */
	public static String date(Date date, String format) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(
				format);
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String date(String date, String format) {
		if (date == null || date.length() != 8)
			return "";

		return date.substring(0, 4) + format + date.substring(4, 6) + format
				+ date.substring(6, 8);
	}

	public static String addDateToFormatedString(String pattern, int day) {
		Calendar calendar = Calendar.getInstance(); // 현재 날짜/시간 등의 각종 정보 얻기

		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				+ day);

		SimpleDateFormat formatter = new SimpleDateFormat(
				pattern);
		String dateString = formatter.format(calendar.getTime());
		return dateString;
	}

	public static String addDateToFormatedString(String pattern, String date,
			int day) {
		Calendar calendar = Calendar.getInstance(); // 현재 날짜/시간 등의 각종 정보 얻기
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 2);
		calendar.set(Calendar.DAY_OF_MONTH,
				Integer.parseInt(date.substring(6, 8)));
		calendar.add(Calendar.DAY_OF_MONTH, day);
		SimpleDateFormat formatter = new SimpleDateFormat(
				pattern);
		String dateString = formatter.format(calendar.getTime());
		return dateString;
	}

	/**
	 * yyyy년 mm월의 마지막 날을 구한다. 사용방법 : DateUtil.getLastDayString("2000","08") <br>
	 * 2000년 8월의 마지막 날인 31일이 리턴 된다.
	 * 
	 * @param yyyy
	 *            년도
	 * @param mm
	 *            월
	 * @return yyyy년 mm월의 마지막 날
	 */
	public static String getLastDayString(String yyyy, String mm) {
		if (yyyy == null || yyyy.length() < 4 || mm == null || "".equals(mm)) {
			return "";
		} else {
			return String.valueOf(getLastDay(Integer.parseInt(yyyy),
					Integer.parseInt(mm)));
		}
	}

	/**
	 * yyyy년 mm월의 마지막 날을 구한다. 사용방법 : DateUtil.getLastDay("2000","08") <br>
	 * 2000년 8월의 마지막 날인 31일이 리턴 된다.
	 * 
	 * @param yyyy
	 *            년도
	 * @param mm
	 *            월
	 * @return yyyy년 mm월의 마지막 날
	 */
	public static int getLastDay(String yyyy, String mm) {
		if (yyyy == null || yyyy.length() < 4 || mm == null || "".equals(mm)) {
			return 0;
		} else {
			return getLastDay(Integer.parseInt(yyyy), Integer.parseInt(mm));
		}
	}

	/**
	 * yyyy년 mm월의 마지막 날을 구한다. 사용방법 : DateUtil.getLastDay("2000","08") <br>
	 * 2000년 8월의 마지막 날인 31일이 리턴 된다.
	 * 
	 * @param yyyy
	 *            년도
	 * @param mm
	 *            월
	 * @return yyyy년 mm월의 마지막 날
	 */
	public static int getLastDay(int yyyy, int mm) {
		Calendar Cal = Calendar.getInstance();
		Cal.set(yyyy, mm - 1, 1);
		int Max = Cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return Max;
	}

	/**
	 * yyyy년 mm월의 마지막 날을 구한다. 사용방법 : DateUtil.getLastDay("2000","08") <br>
	 * 2000년 8월의 마지막 날인 31일이 리턴 된다.
	 * 
	 * @param yyyy
	 *            년도
	 * @param mm
	 *            월
	 * @return yyyy년 mm월의 마지막 날
	 */
	public static String getLastDayString(int yyyy, int mm) {
		return String.valueOf(getLastDay(yyyy, mm));
	}

	/**
	 * yyyy년 mm월의 다음 달을 구한다. 사용방법 : DateUtil.getNextMonth("200012") <br>
	 * 2000년 12월의 다음달인 200101이 리턴된다.
	 * 
//	 * @param yyyyMM
	 *            yyyy년 MM월
	 * @return yyyy년 mm월의 다음 달을 200301 형식으로 리턴
	 */
	public static String dateMonthTerm(String yyyy, String mm, int term) {
		if (yyyy == null || yyyy.length() < 4 || mm == null || "".equals(mm)) {
			return "";
		} else {
			if (mm.length() < 2) {
				mm = StringHelper.lpad(mm, 2, "0");
			}
			Calendar Cal = Calendar.getInstance();
			Cal.set(Integer.parseInt(yyyy), Integer.parseInt(mm) - 1, 1);
			Cal.add(Calendar.MONTH, term);
			int n_month = Cal.get(Calendar.MONTH) + 1;
			String month = null;
			if (n_month < 10) {
				month = "0" + (Cal.get(Calendar.MONTH) + 1);
			} else {
				month = String.valueOf(Cal.get(Calendar.MONTH) + 1);
			}
			return Cal.get(Calendar.YEAR) + month;
		}
	}

	public static String toDateTime(String strDate) {
		SimpleDateFormat formatter_one = new SimpleDateFormat(
				"EEE, dd MMM yyyy hh:mm:ss", Locale.ENGLISH);
		SimpleDateFormat formatter_two = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date frmTime = formatter_one.parse(strDate, pos);
		return formatter_two.format(frmTime);
	}

	public static String getWeekString(String yyyymmdd) {
		int yyyy = 0;
		int mm = 0;
		int dd = 0;
		if (yyyymmdd.length() > 8) {
			yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
			mm = Integer.parseInt(yyyymmdd.substring(5, 7)) - 1;
			dd = Integer.parseInt(yyyymmdd.substring(8, 10));
		} else if (yyyymmdd.length() == 8) {
			yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
			mm = Integer.parseInt(yyyymmdd.substring(4, 6)) - 1;
			dd = Integer.parseInt(yyyymmdd.substring(6, 8));
		} else {
			return "";
		}

		return getWeekString(yyyy, mm, dd);
	}

	public static String getWeekString(int year, int month, int day) {
		String week = "";

		int i = getWeek(year, month, day);

		if (i == 1) {
			week = "일요일";
		} else if (i == 2) {
			week = "월요일";
		} else if (i == 3) {
			week = "화요일";
		} else if (i == 4) {
			week = "수요일";
		} else if (i == 5) {
			week = "목요일";
		} else if (i == 6) {
			week = "금요일";
		} else if (i == 7) {
			week = "토요일";
		}

		return week;
	}

	public static String getWeekString(String year, String month, String day) {
		return getWeekString(Integer.parseInt(year), Integer.parseInt(month),
				Integer.parseInt(day));
	}

	public static String getFirstWeekString(int year, int month) {
		return getWeekString(year, month, 1);
	}

	public static String getFirstWeekString(String year, String month) {
		return getWeekString(Integer.parseInt(year), Integer.parseInt(month), 1);
	}

	public static int getWeek(String yyyymmdd) {
		int yyyy = 0;
		int mm = 0;
		int dd = 0;
		if (yyyymmdd.length() > 8) {
			yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
			mm = Integer.parseInt(yyyymmdd.substring(5, 7)) - 1;
			dd = Integer.parseInt(yyyymmdd.substring(8, 10));
		} else if (yyyymmdd.length() == 8) {
			yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
			mm = Integer.parseInt(yyyymmdd.substring(4, 6)) - 1;
			dd = Integer.parseInt(yyyymmdd.substring(6, 8));
		} else {
			return 0;
		}

		return getWeek(yyyy, mm, dd);
	}

	public static int getWeek(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance(); // 현재 날킈/시간 등의 각종 정보 얻기

		calendar.set(year, month - 1, day);

		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static int getWeek(String year, String month, String day) {
		return getWeek(Integer.parseInt(year), Integer.parseInt(month),
				Integer.parseInt(day));
	}

	public static int getFirstWeek(int year, int month) {
		return getWeek(year, month, 1);
	}

	public static int getFirstWeek(String year, String month) {
		return getWeek(Integer.parseInt(year), Integer.parseInt(month), 1);
	}

	public static int getMonthWeek(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance(); // 현재 날킈/시간 등의 각종 정보 얻기

		calendar.set(year, month - 1, day);

		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	public static int getMonthLastWeek(int year, int month) {
		return getMonthWeek(year, month, getLastDay(year, month));
	}

	public static int getMonthLastWeek(String yyyy, String mm) {
		if (yyyy == null || yyyy.length() < 4 || mm == null || "".equals(mm)) {
			return 0;
		} else {
			return getMonthLastWeek(Integer.parseInt(yyyy),
					Integer.parseInt(mm));
		}
	}

	public static Date toDate() {
		return toDate(date(DEFUALT_DATE_FORMAT), DEFUALT_DATE_FORMAT);
	}

	public static Date toDate(String date) {
		return toDate(date, DEFUALT_DATE_FORMAT);
	}

	public static Date toDate(String dateTime, String format) {
		Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					format);
			date = simpleDateFormat.parse(dateTime);
		} catch (Exception e) {

		}
		return date;
	}

	/**
	 * 조회시작일, 조회종료일을 가지고 화면에 필요한 문자열로 합성하여 리턴하는 함수
	 * 
	 * @param startDt
	 *            조회시작일 (yyyymmdd)
	 * @param endDt
	 *            조회종료일 (yyyymmdd)
	 * @return
	 */
	public static String getBetweenPeriod(String startDt, String endDt) {

		String result = "";

		if (startDt == null || endDt == null || "".equals(startDt)
				|| "".equals(endDt))
			return result;

		String sYear = startDt.substring(0, 4);

		String sMonth = startDt.substring(4, 6);
		if (sMonth.startsWith("0"))
			sMonth = sMonth.replaceAll("0", "");

		String sDay = startDt.substring(6, 8);
		if (sDay.startsWith("0"))
			sDay = sDay.replaceAll("0", "");

		String eMonth = endDt.substring(4, 6);
		if (eMonth.startsWith("0"))
			eMonth = eMonth.replaceAll("0", "");

		String eDay = endDt.substring(6, 8);
		if (eDay.startsWith("0"))
			eDay = eDay.replaceAll("0", "");

		result = sYear + "년 " + sMonth + "월" + sDay + "일" + "~ " + eMonth + "월"
				+ eDay + "일";

		return result;

	}

	public static long diffOfDate(Date begin, Date end) {
		return diffOfDate(begin, end, DEFUALT_DATE_FORMAT);
	}

	public static long diffOfDate(Date begin, Date end, String format) {
		return diffOfDate(date(begin, format), date(end, format));
	}

	public static long diffOfDate(String begin, String end) {
		return diffOfDate(begin, end, DEFUALT_DATE_FORMAT);
	}

	public static long diffOfDate(String begin, String end, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		long diffDays = 0;
		try {
			Date beginDate = formatter.parse(begin);
			Date endDate = formatter.parse(end);

			long diff = endDate.getTime() - beginDate.getTime();
			diffDays = diff / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return diffDays;
	}

	public static boolean isDateValidate(String date) {
		boolean isCheck = false;

		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					DEFUALT_DATE_FORMAT);
			simpleDateFormat.setLenient(false);
			simpleDateFormat.parse(date);
			isCheck = true;
		} catch (ParseException e) {
			isCheck = false;
		} catch (IllegalArgumentException e) {
			isCheck = false;
		} catch (Exception e) {
			isCheck = false;
		}

		return isCheck;
	}
	
	/**
	 * 임의 날짜에서 n 만큼 앞으로 이동
	 * @param pattern
	 * @param year
	 * @param month
	 * @param day
	 * @param iaddDay
	 * @return
	 */
	public static String getAddDate (String pattern , int year, int month ,int day,  int iaddDay )
	{
		Calendar calendar = Calendar.getInstance(); // 현재 날짜/시간 등의 각종 정보 얻기

		calendar.set(year, month-1, day);
		calendar.add ( Calendar.DAY_OF_MONTH, iaddDay );
		
		SimpleDateFormat formatter = new SimpleDateFormat( pattern );
		String dateString = formatter.format(calendar.getTime());
		
		return dateString;	
	}
	
	/**
	 * 지정한 날짜의 내일
	 * @param pattern
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public String getTommorrow (String pattern , int year, int month ,int day ) {

		Calendar today = Calendar.getInstance ( );
		today.set(year, month-1, day);
		today.add ( Calendar.DATE, 1 );
		
		Date tomorrow = today.getTime ( );

		SimpleDateFormat formatter = new SimpleDateFormat( pattern );
		String dateString = formatter.format(tomorrow);
		
		return dateString;	
	}
	
	/**
	 * 지정한 날짜의 내일
	 * @param pattern
	 * @param strDate
	 * @return
	 */
	public static String getTommorrow (String pattern , String strDate ) {
		
		// 입력할 날짜의 문자열이 pattern(ex yyyy-MM-dd) 형식이므로 해당 형식으로 포매터를 생성한다.
	   SimpleDateFormat format = new SimpleDateFormat(pattern);

	   //SimpleDateFormat.parse()메소드를 통해 Date객체를 생성한다.
	   //SimpleDateFormat.parse()메소드는 입력한 문자열 형식의 날짜가
	   //포맷과 다를경우 java.text.ParseException을 발생한다.
	   String dateString = "";
		try {
			Date date = format.parse(strDate);
			
			Calendar today = Calendar.getInstance ( );
			
			int y =date.getYear() + 1900;
			int m = date.getMonth() +1;
            int d = date.getDate();
			today.set(y, m -1, d );
			today.add ( Calendar.DATE, 1 );
			
			Date tomorrow = today.getTime ( );

			SimpleDateFormat formatter = new SimpleDateFormat( pattern );
			dateString = formatter.format(tomorrow);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dateString;
	}
	
	/**
	 * 지정한 날짜의 어제
	 * @param pattern
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public String getYesterday (String pattern , int year, int month ,int day ) {

		Calendar today = Calendar.getInstance ( );
		today.set(year, month-1, day);
		today.add ( Calendar.DATE, -1 );
		
		Date tomorrow = today.getTime ( );

		SimpleDateFormat formatter = new SimpleDateFormat( pattern );
		String dateString = formatter.format(tomorrow);
		
		return dateString;	
	}
	
	/**
	 * 지정한 날짜의 어제
	 * @param pattern
	 * @param strDate
	 * @return
	 */
	public static String getYesterday (String pattern , String strDate ) {

		// 입력할 날짜의 문자열이 pattern(ex yyyy-MM-dd) 형식이므로 해당 형식으로 포매터를 생성한다.
		   SimpleDateFormat format = new SimpleDateFormat(pattern);

		   //SimpleDateFormat.parse()메소드를 통해 Date객체를 생성한다.
		   //SimpleDateFormat.parse()메소드는 입력한 문자열 형식의 날짜가
		   //포맷과 다를경우 java.text.ParseException을 발생한다.
		   String dateString = "";

		   try {
				Date date= format.parse(strDate);
				
				Calendar today = Calendar.getInstance ( );

				int y =date.getYear() + 1900;
				int m = date.getMonth() +1;
               int d = date.getDate();

				today.set(y, m -1, d );
				today.add ( Calendar.DATE, -1 );
				
				Date tomorrow = today.getTime ( );

				SimpleDateFormat formatter = new SimpleDateFormat( pattern );
				dateString = formatter.format(tomorrow);
				
				return dateString;
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return dateString;
	}		
	
	// 각 도시별 날짜를 얻는 메소드 yyyy-mm-dd
	public static String getDate(String zone){
		TimeZone tz	 = TimeZone.getTimeZone(zone);
		Calendar cal = new GregorianCalendar(tz);
		
		String date=cal.get(Calendar.YEAR)+"."+((cal.get(Calendar.MONTH)+1) > 9 ? (cal.get(Calendar.MONTH)+1) : "0"+(cal.get(Calendar.MONTH)+1))+
				"."+(cal.get(Calendar.DATE) > 9 ? cal.get(Calendar.DATE) : "0"+cal.get(Calendar.DATE));
		return date;
	}
	 
	 // 각 도시별 시간을 얻는 메소드  hh:mm:ss
	public static String getTime(String zone){
		TimeZone tz	 = TimeZone.getTimeZone(zone);
		Calendar cal = new GregorianCalendar(tz);
		
//		HOUR_OF_DAY
		String am_pm = cal.get(Calendar.AM_PM) == 0 ? "AM":"PM";
		
		String time =  am_pm +" "+ (cal.get(Calendar.HOUR) > 9 ? cal.get(Calendar.HOUR) :"0"+cal.get(Calendar.HOUR)) +
				": "+(cal.get(Calendar.MINUTE) > 9 ? cal.get(Calendar.MINUTE) :"0"+cal.get(Calendar.MINUTE));
		return time;
	}

	public static String getStrDayOfWeekTime(Context ctx, String dayOfWeek) {
		String strDayOfWeek = "";
		switch (dayOfWeek) {
			case "0": {
				strDayOfWeek = "(" + (ctx.getString(R.string.s101)) + (")");
			}
			break;

			case "1": {
				strDayOfWeek = "(" + (ctx.getString(R.string.s102)) + (")");
			}
			break;

			case "2": {
				strDayOfWeek = "(" + (ctx.getString(R.string.s103)) + (")");
			}
			break;

			case "3": {
				strDayOfWeek = "(" + (ctx.getString(R.string.s104)) + (")");
			}
			break;

			case "4": {
				strDayOfWeek = "(" + (ctx.getString(R.string.s105)) + (")");
			}
			break;

			case "5": {
				strDayOfWeek = "(" + (ctx.getString(R.string.s106)) + (")");
			}
			break;

			case "6": {
				strDayOfWeek = "(" + (ctx.getString(R.string.s107)) + (")");
			}
			break;
		}
		return strDayOfWeek;
	}
}



