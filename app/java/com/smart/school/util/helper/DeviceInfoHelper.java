package com.smart.school.util.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.UUID;

public class DeviceInfoHelper {

	@SuppressWarnings("unused")
	private final static String TAG = DeviceInfoHelper.class.getSimpleName();

	public static int getWidth(Context context) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
//		windowManager.getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics.widthPixels;
	}

	public static int getHeight(Context context) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
//		windowManager.getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics.heightPixels;
	}


	/**
	 * 단말의 해상도 값을 리턴하는 함수
	 *
	 * @param context : Context
	 * @return : DisplayMetricsDensity
	 */
	public static float getDisplayMetricsDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}


	/**
	 * Pixel 값을 단말에 맞는 사이즈로 변경하여 리턴하는 함수
	 *
	 * @param context
	 * @param p
	 * @return
	 */
	public static int getPixel(Context context, int p) {

		float den = getDisplayMetricsDensity(context);

		if (den != 1) {
			return (int) (p * den + 0.5);
		}

		return p;
	}


	public static String getOsName() {
		return System.getProperty("os.name");
	}

	public static String getOsArch() {
		return System.getProperty("os.arch");
	}

	public static String getOsVersion() {
		return System.getProperty("os.version");
	}

	public static String getAndroidOsVersion() {
		return Build.VERSION.RELEASE;
	}

	public static String getDisplay(){
		return Build.DISPLAY;
	}

	public static String getDevice() {
		return Build.DEVICE;
	}

	public static String getBoard() {
		return Build.BOARD;
	}

	public static String getBrand() {
		return Build.BRAND;
	}

	public static String getModel() {
		return Build.MODEL;
	}

	/**
	 * 전화번호
	 *
	 * @param context
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getLine1Number(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
		String lineNumber = telephonyManager.getLine1Number();

		if (lineNumber == null || lineNumber.length() < 1)
			lineNumber = "01000000000";


		return lineNumber;
	}

	/**
	 * 전화번호
	 *
	 * @param context
	 * @return
	 */
	public static String getLine1NumberLocaleFormat(Context context) {
		return PhoneNumberUtils.formatNumber(getLine1Number(context));
	}

	/**
	 * 전화번호(+82 국가코드 제거)
	 * 앞에 0이 없는 경우 추가
	 * @param context
	 * @return
	 */
	public static String getLine1NumberLocaleRemove(Context context) {
		String lineNumber = PhoneNumberUtils
				.formatNumber(getLine1Number(context)).replaceAll("-", "");
		if (lineNumber.startsWith("+")) {
			lineNumber = "0" + lineNumber.substring(lineNumber.indexOf("10", 2)
            );
		} else if (lineNumber.startsWith("10")) {
			lineNumber = "0" + lineNumber;
		}
		return lineNumber;
	}

	/**
	 * 단말기 ID
	 *
	 * @param context
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);

		return telephonyManager.getDeviceId();
	}

	/**
	 * 이동통신사 코드를 리턴
	 *
	 * @param context
	 * @return
	 */
	public static String getTelecom(Context context) {

		String result = "";
		String tel = getSimOperator(context);

		if (tel.equals("45005")) {//SKT
			result = "1";
		} else if (tel.equals("45008")) {//KT
			result = "2";
		} else {//LG//45006
			result = "3";
		}
		return result;
	}

	public static String getSerialNumber(Context context) {
		String serialNumber = "";
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);

			// (?) Lenovo Tab (https://stackoverflow.com/a/34819027/1276306)
			serialNumber = (String) get.invoke(c, "gsm.sn1");

			if (serialNumber.equals(""))
				// Samsung Galaxy S5 (SM-G900F) : 6.0.1
				// Samsung Galaxy S6 (SM-G920F) : 7.0
				// Samsung Galaxy Tab 4 (SM-T530) : 5.0.2
				// (?) Samsung Galaxy Tab 2 (https://gist.github.com/jgold6/f46b1c049a1ee94fdb52)
				serialNumber = (String) get.invoke(c, "ril.serialnumber");

			if (serialNumber.equals(""))
				// Google Nexus 5 : 6.0.1
				// Honor 5C (NEM-L51) : 7.0
				// Honor 5X (KIW-L21) : 6.0.1
				// Huawei M2 (M2-801w) : 5.1.1
				// (?) HTC Nexus One : 2.3.4 (https://gist.github.com/tetsu-koba/992373)
				serialNumber = (String) get.invoke(c, "ro.serialno");

			if (serialNumber.equals(""))
				// (?) Samsung Galaxy Tab 3 (https://stackoverflow.com/a/27274950/1276306)
				serialNumber = (String) get.invoke(c, "sys.serialnumber");

			if (serialNumber.equals(""))
				// Honor 9 Lite (LLD-L31) : 8.0
				serialNumber = Build.SERIAL;

			// If none of the methods above worked
			if (serialNumber.equals(""))
				serialNumber = null;
		} catch (Exception e) {
			e.printStackTrace();
			serialNumber = null;
		}

		return serialNumber;
	}

	/**
	 * 단말기 UUID
	 * @param context : Activiity
	 * @return device UUID
	 */
	public static String getDevicetUUID(Context context) {

		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String tmDevice = "" + tm.getDeviceId();
		String tmSerial = "" + tm.getSimSerialNumber();
		String androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	    return deviceUuid.toString();
	}

	/**
	 * 음성통화 상태 반환
	 *
	 * @param context
	 * @return
	 */
	public static int getCallState(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getCallState();
	}

	/**
	 * 데이타통신 상태 반환
	 *
	 * @param context
	 * @return
	 */
	public static int getDataState(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getDataState();
	}

	/**
	 * SW버전
	 *
	 * @param context
	 * @return
	 */
	public static String getDeviceSoftwareVersion(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getDeviceSoftwareVersion();
	}

	/**
	 * 국가코드
	 *
	 * @param context
	 * @return
	 */
	public static String getNetworkCountryIso(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getNetworkCountryIso();
	}

	public static String getNetworkCountryCode(Context context){
		TelephonyManager systemService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String countryIso = systemService.getSimCountryIso();

		return getCountryCode(countryIso.toUpperCase());
	}

	private static String getCountryCode(String countryIso){
		String countryCode = "";
		switch (countryIso){
			case "KR":	countryCode = "82"; break;
			case "US":	countryCode = "1"; break;
			case "JP":	countryCode = "81"; break;
			case "CN":	countryCode = "86"; break;
			case "HK":	countryCode = "852"; break;
			case "MO":	countryCode = "853"; break;
			case "TW":	countryCode = "886"; break;
		}
		return countryCode;
	}

	/**
	 * 국가코드
	 *
	 * @param context
	 * @return
	 */
	public static String getSimCountryIso(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getSimCountryIso();
	}

	/**
	 * 망 사업자코드
	 *
	 * @param context
	 * @return
	 */
	public static String getNetworkOperator(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getNetworkOperator();
	}

	/**
	 * 망 사업자코드
	 *
	 * @param context
	 * @return
	 */
	public static String getSimOperator(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getSimOperator();
	}

	/**
	 * 망 사업자명
	 *
	 * @param context
	 * @return
	 */
	public static String getNetworkOperatorName(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getNetworkOperatorName();
	}

	/**
	 * 망 사업자명
	 *
	 * @param context
	 * @return
	 */
	public static String getSimOperatorName(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getSimOperatorName();
	}

	/**
	 * 망 시스템 방식
	 *
	 * @param context
	 * @return
	 */
	public static int getNetworkType(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getNetworkType();
	}

	/**
	 * 단말기 종류
	 *
	 * @param context
	 * @return
	 */
	public static int getPhoneType(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getPhoneType();
	}

	/**
	 * sim serial number
	 *
	 * @param context
	 * @return
	 */
	public static String getSimSerialNumber(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		return systemService.getSimSerialNumber();
	}

	/**
	 * sim 카드 상태
	 *
	 * @param context
	 * @return
	 */
	public static int getSimState(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getSimState();
	}

	/**
	 * 가입자 ID 조회
	 *
	 * @param context
	 * @return
	 */
	public static String getSubscriberId(Context context) {
		TelephonyManager systemService = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return systemService.getSubscriberId();
	}

	public static boolean isNetworkState(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// 3G 를 사용하는지 확인힌다.
		boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnected();

		// WIFI 를 사용하는지 확인한다.
		boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnected();

        return is3g || isWifi;
	}

	public static boolean isWifi(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// WIFI 를 사용하는지 확인한다.
		boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnected();

		return isWifi;
	}

	public static boolean is3G(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// 3G 를 사용하는지 확인힌다.
		boolean is3g = false;
		if (manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null)
		{
			is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
		}
		return is3g;
	}

//	public static boolean is4G(Context mContext) {
//		ConnectivityManager manager = (ConnectivityManager) mContext
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//		// 4G 를 사용하는지 확인힌다.
//		boolean is4g = false;
//		if (manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX) != null) 
//		{
//			is4g = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).isConnected();
//		}
//		return is4g;
//	}


	public static String getNetwork(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		return networkInfo.getTypeName();
	}

	/**
	 * 접속 망 상태를 얻어오는 메소드
	 *
	 * @param cont
	 * @return "3G" / "WIFI"
	 */
	public static String getNetworkTypeName(Context cont) {
		// Android api return 값 구분 "WIFI" / "mobile"
		ConnectivityManager connectivityManager = (ConnectivityManager) cont
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// TODO. mobile 일 경우, 리턴 string
		if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnected())
			return "3G";
		else if (connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).isConnected())
			return "WIFI";
		return null;
	}

	/*
	 * public static boolean isWiFiConnected(Context mContext) { String
	 * connectionType = getNetwork(mContext); if (
	 * "WIFI".equalsIgnoreCase(connectionType) ) { return true; } else {
	 * Toast.makeText(mContext, R.string.WIFI_NOT_CONNECTED,
	 * Toast.LENGTH_LONG).show(); return false; } }
	 */

	/**
	 * 비행기모드가 켜져있는지 확인 (비행기 모드가 켜져있으면 true)
	 *
	 * @param context
	 * @return
	 */
	public static boolean isAirplaneMode(Context context) {
		boolean isResult = false;

		if (Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) == 1) {
			isResult = true;
		}
		return isResult;
	}

	/**
	 * 내폰 전화번호 가져오기
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context){

		TelephonyManager systemService = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

		@SuppressLint({"MissingPermission", "HardwareIds"}) String phoneNumber = systemService.getLine1Number();

		if(phoneNumber != null) {
			phoneNumber = phoneNumber.substring(phoneNumber.length() - 10);

			phoneNumber = "0" + phoneNumber;
		}else{
			phoneNumber = "";
		}
		
		return phoneNumber;
	}
	
	/**
	 * 전화번호에 자동으로 하이픈(-) 추가--
	 * @param number
	 * @return
	 */
	public static String getFormatNumber(String number){
		
		String phone = PhoneNumberUtils.formatNumber(number);
		
		return phone;
	}
}
