package com.smart.school.util.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardHelper {
	public static void showKeyboard(Activity activity, View view) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}

	public static void hideKeyboard(Activity activity, View view) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 키보드를 숨기는 함수
	 * 
	 * @param v
	 */
	public static void hideKeyboard(Context ctx, View view) {
		
		// Soft 키보드 숨기기 
		InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE); 
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0); 

	}

	public static void dialogkeyboardType(EditText v, int type){
		if(type == 0){
			/** 다이얼로그 소프트 키보드 안나오게*/
			v.setInputType(0);
		}else{
			/** 다이얼로그 소프트 키보드 나오게*/
			v.setInputType(1);
		}
	}
}
