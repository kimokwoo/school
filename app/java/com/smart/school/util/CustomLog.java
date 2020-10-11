package com.smart.school.util;

import android.util.Log;

import retrofit2.Call;

public class CustomLog {
	
	/**
	 * 1. "true" string은 DEBUG_MODE에서만 사용하고 새로 추가하지 말자. ant build에 영향이 있음.
	 * 2. Log 내용을 완적히 막으려면 아래 method 안에 내용들을 주석처리하세요.
	 * */
	private static boolean DEBUG_MODE = false;
	private static final String TAG = "Surf > ";
	
	public static int i(String tag, String msg){
		if(DEBUG_MODE){
			return Log.i(TAG + tag, msg);
			
		}		
		return 0;
	}
	
	public static int w(String tag, String msg){
		if(DEBUG_MODE){
			return Log.w(TAG + tag, msg);
		}
		return 0;
	}


	public static int d(String tag, String msg){
		if(DEBUG_MODE){
			return Log.d(TAG + tag, msg);
		}
		return 0;
	}

	public static int d2(Call<?> call, String tag){
		if(DEBUG_MODE) {
			return Log.d(TAG + tag, String.valueOf(call.request()));
		}
		return 0;
	}

	public static <T> int d(String tag, Call<T> call){
		if(DEBUG_MODE){
			return Log.d(TAG + tag, String.valueOf(call.request()));
		}
		return 0;
	}
	
	public static int e(String tag, String msg){
		if(DEBUG_MODE){
			return Log.e(TAG + tag, msg);
		}
		return 0;
	}
	
	public static int v(String tag, String msg){
		if(DEBUG_MODE){
			return Log.v(TAG + tag, msg);
		}
		return 0;
	}
}
