package com.smart.school.util.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.smart.school.adapter.item.CityInfoItem;
import com.smart.school.adapter.item.CommItem;
import com.smart.school.adapter.item.MustBuyNoItem;
import com.smart.school.school.item.LoginItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class CSharedPreferencesHelper {


	@SuppressWarnings("unused")
	private final static String ClassName	= "[" + CSharedPreferencesHelper.class.getSimpleName() + "]";
	
	/**
	 * String value getter
	 * 
	 * @param context
	 * @param args
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(Context context, String args, String key, String defaultValue) {

		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);

		return prefs.getString(key, defaultValue);
	}

	/**
	 * int value getter
	 * 
	 * @param context
	 * @param args
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getValue(Context context, String args, String key, int defaultValue) {

		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);

		return prefs.getInt(key, defaultValue);
	}

	/**
	 * long value getter
	 * 
	 * @param context
	 * @param args
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getValue(Context context, String args, String key, long defaultValue) {

		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);

		return prefs.getLong(key, defaultValue);
	}
	
	public static boolean getWeekendValue(Context context, String args, String key, boolean check){
		
		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);
		
		return prefs.getBoolean(key, check);
	}
	
	public static boolean getFOPromotion(Context context, String args, String key, boolean check){
		
		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);
		
		return prefs.getBoolean(key, check);
	}
	
	public static int getDateValue(Context context, String args, String key){
		
		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);
		
		return prefs.getInt(key, 0);
	}
	
	/**
	 * boolean value getter
	 * 
	 * @param context
	 * @param args
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getValue(Context context, String args, String key, boolean defaultValue) {

		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);

		return prefs.getBoolean(key, defaultValue);
	}
	
	/**
	 * byte value getter
	 * 
	 * @param context
	 * @param args
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static byte getValue(Context context, String args, String key, byte defaultValue) {

		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);

		String value	= prefs.getString(key, "");
		
		if("".equals(value))
			return defaultValue;
		
		byte retValue	= Byte.parseByte(value);
		
		return retValue;
	}

	/**
	 *  ArrayList<String> value getter
	 * 
	 * @param context
	 * @param //args
	 * @param //key
	 * @param //defaultValue
	 * @return
	 */
	public static HashMap<String, String> getValue(Context context, String GroupNm) {
		
		SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(context);
		
		Map m = prefs.getAll();
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		Set s = m.keySet();
		
		Object[] objs = s.toArray();
		
		String key = null;
		
		for (int i = 0; i < objs.length; i++) {
			
			key = (String) objs[i];
			
			map.put(key, (String) m.get(key));
			
		}
		
		return map;
	}
	
	public static ArrayList<String> getValue(Context context, String GroupNm, String name) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    String json = prefs.getString(GroupNm, null);
	    ArrayList<String> urls = new ArrayList<String>();
	    
	    if (json != null) {
	    	
	        try {
	        	
	            JSONArray jsonarr = new JSONArray(json);
	            
	            for (int i = 0; i < jsonarr.length(); i++) {
	            	
	                String url = jsonarr.optString(i);
	                urls.add(url);
	            }
	            
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	    }
	    return urls;
	}


	/**
	 *
	 * @param context
	 * @param key
	 * @return
	 * @throws JSONException
	 */

	/**
	 *
	 * @param context
	 * @param key
	 * @return
	 * @throws JSONException
	 */

	/**
	 *
	 * @param context
	 * @param key
	 * @return
	 * @throws JSONException
	 */

	/**
	 *
	 * @param context
	 * @param key
	 * @return
	 * @throws JSONException
	 */

	/**
	 *
	 * @param context
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	public static ArrayList<LoginItem> getValueLogin(Context context, String key) throws JSONException {

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String json = prefs.getString(key, null);
		ArrayList<LoginItem> urls = new ArrayList<LoginItem>();

		if (json != null) {
			try {
				JSONArray values = new JSONArray(json);

				for (int i = 0; i < values.length(); i++) {
					JSONObject jo = values.getJSONObject(i);

					String user_no			= jo.optString("user_no", 	"");
					String user_id			= jo.optString("user_id", 	"");
					String user_pw			= jo.optString("user_pw",		"");
					String user_name		= jo.optString("user_name", 	"");
					String user_tel			= jo.optString("user_tel", 	"");
					String user_kind		= jo.optString("user_kind", 	"");
					String user_info_value1 = jo.optString("user_info_value1", 	"");
					String user_info_value2 = jo.optString("user_info_value2", 	"");
					String user_info_value3 = jo.optString("user_info_value3", 	"");
					String bus_no			= jo.optString("bus_no","");
					String busstop_no		= jo.optString("busstop_no","");
					String allergy_info		= jo.optString("allergy_info","");

					LoginItem item = new LoginItem(
									user_no,
									user_id,
									user_pw,
									user_name,
									user_tel,
									user_kind,
									user_info_value1,
									user_info_value2,
									user_info_value3,
									bus_no,
									busstop_no,
									allergy_info
					);
					urls.add(item);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return urls;
	}

	public static ArrayList<CommItem> getValue_Surf3(Context context, String key) throws JSONException {

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String json = prefs.getString(key, null);

		ArrayList<CommItem> arrItem = new ArrayList<CommItem>();

		try{

			JSONArray jarr = new JSONArray(json);



		}catch(Exception e){
			e.printStackTrace();
		}

		return arrItem;
	}


	public static MustBuyNoItem getMustBuyNo(Context context){

		String jsonMustBuy = getValue(context, "SURF", "MUST_BUY_NO", "");
//		Type listType = new TypeToken<ArrayList<MustBuyNoItem>>(){}.getType();
		MustBuyNoItem item = new Gson().fromJson(jsonMustBuy, MustBuyNoItem.class);

		if(item == null){
			item = new MustBuyNoItem();
			return item;
		}
		return item;
	}

	public static CityInfoItem getCityInfo(Context context){

		String jsonCityInfo = getValue(context, "SURF", "CITY_INFO", "");
		CityInfoItem item = new Gson().fromJson(jsonCityInfo, CityInfoItem.class);

		if(item == null){
			item = new CityInfoItem();
			return item;
		}
		return item;
	}

	/**
	 * String value setter
	 * 
	 * @param context
	 * @param //argsf
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean setValue(Context context, String args, String key, String value) {

		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);

		SharedPreferences.Editor ed = prefs.edit();

		ed.putString(key, value);

		return ed.commit();
	}
	
	/**
	 * int value setter
	 * 
	 * @param context
	 * @param args
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean setValue(Context context, String args, String key, int value) {

		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);

		SharedPreferences.Editor ed = prefs.edit();

		ed.putInt(key, value);

		return ed.commit();
	}

	/**
	 * long value setter
	 * 
	 * @param context
	 * @param args
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean setValue(Context context, String args, String key, long value) {

		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);

		SharedPreferences.Editor ed = prefs.edit();

		ed.putLong(key, value);

		return ed.commit();
	}
	
	/**
	 * boolean value setter 
	 * 
	 * @param context
	 * @param args
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean setValue(Context context, String args, String key, boolean value) {

		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);

		SharedPreferences.Editor ed = prefs.edit();

		ed.putBoolean(key, value);

		return ed.commit();
	}

	/**
	 * byte value setter 
	 * 
	 * @param context
	 * @param args
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean setValue(Context context, String args, String key, byte value) {

		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);

		SharedPreferences.Editor ed = prefs.edit();
		 
		ed.putString(key, Byte.toString(value));

		return ed.commit();
	}
	
	/**
	 * ArrayList<String> value setter 
	 * 
	 * @param context
	 * @param //args
	 * @param key
	 * @param //value
	 * @return
	 */
	public static boolean setValue(Context context, String key, String name, ArrayList<String> values) {

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor editor = prefs.edit();
	    
	    JSONArray jsonarr = new JSONArray();
	    
	    for (int i = 0; i < values.size(); i++) {
			jsonarr.put(values.get(i));
	    }
		if (!values.isEmpty()) {

			editor.putString(key, jsonarr.toString());
		        
		    } else {
		    	
		        editor.putString(key, null);
		        
		    }
		    
	    return editor.commit();
	}
	
	public static void setValue(Context context, String GroupNm, Map<String, String> map) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		
		Set s = map.keySet();
		Object[] objs = s.toArray();
		String key = null;
		
		for (int i = 0; i < objs.length; i++) {
			
			key = (String) objs[i];
			
			editor.putString(key, map.get(key));
			
			editor.commit();
		}
	}

	public static HashMap<String, String> setValue(Context context, String GroupNm) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Map m = prefs.getAll();
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		Set s = m.keySet();
		
		Object[] objs = s.toArray();
		
		String key = null;
		
		for (int i = 0; i < objs.length; i++) {
			
			key = (String) objs[i];
			
			map.put(key, (String) m.get(key));
			
		}
		
		return map;
	}
	
	public static void setDateValue(Context context, String args, String key, int date){
		
		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putInt(key, date);
		editor.commit();
	}

	/**
	 *
	 * @param context
	 * @param key
	 * @param values
	 */

	/**
	 *
	 * @param context
	 * @param key
	 * @param values
	 */


	/**
	 *
	 * @param context
	 * @param key
	 * @param values
	 */


	/**
	 *
	 * @param context
	 * @param key
	 * @param values
	 * @return
	 */
	public static boolean setValueLogin(Context context, String key, ArrayList<LoginItem> values) {

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();

		JSONArray jsonarr = new JSONArray();
		JSONObject jsonobject = new JSONObject();
		for (int i = 0; i < values.size(); i++) {
			LoginItem v = values.get(i);

			String user_no			= v.getUser_no();
			String user_id			= v.getUser_id();
			String user_pw			= v.getUser_pw();
			String user_name		= v.getUser_name();
			String user_tel			= v.getUser_tel();
			String user_kind		= v.getUser_kind();
			String user_info_value1	= v.getUser_info_value1();
			String user_info_value2	= v.getUser_info_value2();
			String user_info_value3	= v.getUser_info_value3();

			try {
				jsonobject.put("user_no",		user_no);
				jsonobject.put("user_id",		user_id);
				jsonobject.put("user_pw",		user_pw);
				jsonobject.put("user_name",	user_name);
				jsonobject.put("user_tel",	user_tel);
				jsonobject.put("user_kind",	user_kind);
				jsonobject.put("user_info_value1",	user_info_value1);
				jsonobject.put("user_info_value2",	user_info_value2);
				jsonobject.put("user_info_value3",	user_info_value3);


			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonarr.put(jsonobject);
		}
		if (!values.isEmpty()) {
			editor.putString(key, jsonarr.toString());
		} else {
			editor.putString(key, null);
		}

		return editor.commit();
	}

	public static boolean setValueCard(Context context, String key, ArrayList<CommItem> mCard) {//저장값
		//578
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();

		Gson gson = new Gson();
		String result = gson.toJson(mCard);

		JSONArray jsonarr = new JSONArray();


		editor.putString(key, jsonarr.toString());

		return editor.commit();
	}



	public static void setMustBuyNo(Context context, MustBuyNoItem item){

		Gson gson = new Gson();
		String result = gson.toJson(item);
		setValue(context, "SURF", "MUST_BUY_NO", result);
	}

	public static void setCityInfo(Context context, CityInfoItem item){

		Gson gson = new Gson();
		String result = gson.toJson(item);
		setValue(context, "SURF", "CITY_INFO", result);
	}

	public static boolean removeValue(Context context, String args, String key) {

		SharedPreferences prefs = context.getSharedPreferences(args, Activity.MODE_PRIVATE);
		
		SharedPreferences.Editor ed = prefs.edit();
		
		ed.remove(key);
		
		return ed.commit();
	}
	
	public static boolean removeValue(Context context, String GroupNm) {
		
		SharedPreferences pref = context.getSharedPreferences(GroupNm, Activity.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = pref.edit();
		
		editor.clear();
		
		return editor.commit();
	}


	public static boolean removeValueLoginInfo(Context context, String key){

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

		SharedPreferences.Editor editor = prefs.edit();

		editor.clear();

		return editor.commit();
	}




}
