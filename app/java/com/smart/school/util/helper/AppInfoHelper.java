package com.smart.school.util.helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public class AppInfoHelper {
	private final static String TAG = "AppInfoHelper";
	
	public static int getAppVersion(Context context) {
		int version = 0;
		try {
			PackageInfo i = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			version = i.versionCode;
		} catch (NameNotFoundException e) {
		}
		
		return version;
	}
	
	public static String getAppVersionCode(Context context) {
		String version = "";
		try {
			PackageInfo i = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			version = i.versionName;
		} catch (NameNotFoundException e) {
		}
		
		return version;
	}

	public static String getSDKVersion(Context cont) {
		int version = Build.VERSION.SDK_INT;
		String ret = String.valueOf(version);

		return ret;
	}
	
	public static String getAppPackageName(Context context) {
		String packageName = "";
		try {
			PackageInfo i = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			packageName = i.packageName;
		} catch (NameNotFoundException e) {
		}
		return packageName;
	}

}
