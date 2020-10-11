package com.smart.school.util.helper;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Base64;
import android.util.Base64InputStream;

import com.smart.school.util.CustomLog;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
	private static final String TAG = "StringHelper";
	private static CustomLog log = new CustomLog();

	// null 문제를 처리한다.
	public static String toSafeString(String source) {
		if (source == null)
			return "";
		else
			return source;
	}

	public static String toUTF8String(String str)
			throws UnsupportedEncodingException {
		if (str == null)
			return "";

		String new_str = new String(str.getBytes("EUC-KR"), StandardCharsets.UTF_8);
		return new_str;
	}

	// 
	public static String get_cut_string(String str, int sz)
			throws UnsupportedEncodingException {
		if (str == null || str.equals("") || str.getBytes().length <= sz)
			return str;

		int i = 0;
		String rlt = "";
		String a = str;
		String imsi = a.substring(0, 1);
		while (i < sz) {
			byte[] ar = imsi.getBytes();
			i += ar.length;
			rlt += imsi;
			a = a.substring(1);
			if (a.length() == 1)
				imsi = a;
			else if (a.length() > 1)
				imsi = a.substring(0, 1);
		}

		return rlt;
	}

	// mb_substr() : 한글 문자를 하나로 취급한다.
	// public static String mb_substr(String str, int count)
	// {
	// if (str == null)
	// return "";
	//	
	// String tmp = str;
	// int slen = 0, blen = 0;
	// char c;
	// try
	// {
	// if( tmp.getBytes("MS949").length > count )
	// {
	// while (blen+1 < count)
	// {
	// c = tmp.charAt(slen);
	// blen++;
	// slen++;
	// if ( c>127 )
	// blen++; //2-byte character..
	// }
	// tmp = tmp.substring(0,slen);
	// }
	// }
	// catch(java.io.UnsupportedEncodingException e)
	// {
	// }
	//
	// return tmp;
	// }
	
	public static String getString(InputStream inputStream) {
		String data = "";
		try {
			int c;
			while ((c = inputStream.read()) != -1)
				data = data + (char) c;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
				}
			}
		}

		return data;
	}

	public static String getString(Context context, int resourceID) {
		StringBuilder contents = new StringBuilder();
		String sep = System.getProperty("line.separator");

		try {
			InputStream is = context.getResources().openRawResource(resourceID);

			BufferedReader input = new BufferedReader(
					new InputStreamReader(is), 1024 * 8);
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(sep);
				}
			} finally {
				input.close();
			}
		} catch (FileNotFoundException ex) {
			CustomLog.e(TAG, "Couldn't find the file " + resourceID + " " + ex);
			return null;
		} catch (IOException ex) {
			CustomLog.e(TAG, "Error reading file " + resourceID + " " + ex);
			return null;
		}

		return contents.toString();
	}
	
	public static String padZero(int num, int count) {
		String rtnValue = "";
		String strNum = "";
		
		if(num > 0) {
			strNum = String.valueOf(num);
		}
		
		for(int i = count; i > strNum.length(); i--) {
			rtnValue = rtnValue + "0";
		}
		
		rtnValue = rtnValue + num;
		
		return rtnValue;
	}
	
	public static String[] getHPhoneNum(String hPhone) {
		hPhone = hPhone.replaceAll("-", "");
		String[] arrHPhone = new String[3];
		
		if(hPhone.length() == 10) {
			arrHPhone[0] = hPhone.substring(0, 3);
			arrHPhone[1] = hPhone.substring(3, 6);
			arrHPhone[2] = hPhone.substring(6);
		} else if(hPhone.length() == 11) {
			arrHPhone[0] = hPhone.substring(0, 3);
			arrHPhone[1] = hPhone.substring(3, 7);
			arrHPhone[2] = hPhone.substring(7);
		} else {
			arrHPhone[0] = "";
			arrHPhone[1] = "";
			arrHPhone[2] = "";
		}
		
		return arrHPhone;
	}
	
	public static String getExceptionString(Exception ex){
		if(ex!=null){
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream printStream = new PrintStream(out);							
			ex.printStackTrace(printStream);
			return out.toString();
		}else{
			return "";
		}		
	}
	
	public static String lpad(String data, int count, String addString){
		String tmpData = (data == null)?"":data;
		if(tmpData.length()>=count){
			return tmpData;
		}else{
			return lpad(addString+tmpData, count, addString);
		}
	}
	
	public static String null2trim(String data) {
		if (data == null)
			return "";
		else
			return data.trim();
	}

	public static int null2zero(String data) {
		if (data == null)
			return 0;
		else
			return Integer.parseInt(data.trim());
	}
	
	public static int null2zeroMilliSecond(String data) {
		if (data == null)
			return 0;
		else
			return Integer.parseInt(data.trim())*1000;
	}

	public static long null2LongZero(String data) {
		if (data == null)
			return 0;
		else
			return Long.parseLong(data.trim());
	}

	public static double null2DoubleZero(String data) {
		if (data == null)
			return 0;
		else
			return Double.parseDouble(data.trim());
	}

	public static float null2FloatZero(String data) {
		if (data == null)
			return 0;
		else
			return Float.parseFloat(data.trim());
	}
	
	public static boolean null2Boolean(String data) {
		if (data == null)
			return false;
		else {
			return data.trim().equalsIgnoreCase("Y") || data.trim().equalsIgnoreCase("TRUE");
		}
	}
	
	public static int null2trim(String value, int nullValue){
		if(value == null || "null".equals(value) || "".equals(value)) return nullValue;
		return Integer.parseInt(value);
	}
	
	/* --- */
	
	/**
//	 * @param int
	 * @return String
	 */
	public static String spaces(int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append(' ');
			//sb.append(Character.SPACE_SEPARATOR);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param str
	 *            
//	 * @param int
	 * @return String 
	 */
	public static String cutStringByBytes(String str, int length) {
		byte[] bytes = str.getBytes();
		int len = bytes.length;
		int counter = 0;
		if (length >= len) {
			return str + spaces(length - len);
		}
		for (int i = length - 1; i >= 0; i--) {
			if (((int) bytes[i] & 0x80) != 0)
				counter++;
		}
		return new String(bytes, 0, length + (counter % 2));
	}

	/**
	 * 
	 * 
	 * @param str
	 *            
//	 * @param int
	 * @return String 
	 */
	public static String cutInStringByBytes(String str, int length) {
		byte[] bytes = str.getBytes();
		int len = bytes.length;
		int counter = 0;
		if (length >= len) {
			return str + spaces(length - len);
		}
		for (int i = length - 1; i >= 0; i--) {
			if (((int) bytes[i] & 0x80) != 0)
				counter++;
		}
		return new String(bytes, 0, length - (counter % 2));
	}
	
	/**
	 * 
	 * str 
//	 * @param String str
	 * @return HashMap rtnMap
	 * @throws Exception
	 */
	public static HashMap<String, String> getStrToMap(String str) throws Exception{
		HashMap<String, String> rtnMap = null;
		try{

			if( str == null || "".equals(str)){
				return rtnMap;
			}

			rtnMap = new HashMap<String, String>();

			String[] sArray = getSplitToArray(str, ";");	
			for( int i = 0 ; i < sArray.length; i++){				
				String[] temp = getSplitToArray(sArray[i], "=");
				if(temp.length > 0 ){
					rtnMap.put(temp[0], temp[1]);
				}
			}

		}catch(Exception e){
			throw e;
		}

		return rtnMap;
	}

	/**
	 * 
	 * 
	 * ex) String[] str = StringUtil.getSplitToArray("s1=s2;s3=s4", ";");
//	 * @param String str
//	 * @param String delimiter
	 * @return String[] 
	 */
	public static String[] getSplitToArray(String str, String delimiter) {
		if(str == null) return null;
		String[] sArray = str.split(delimiter);
		return sArray;
	}
	
	/**
	 * MD5
	 * @param
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encryptMD5(String data) throws NoSuchAlgorithmException {
		
		if( data == null) return null;
		
		StringBuffer result = new StringBuffer();
		MessageDigest md = MessageDigest.getInstance("MD5");

		byte[] encryptedData = md.digest(data.getBytes());

		for (int i = 0; i < encryptedData.length; i++) {			
			result.append(Integer.toString((encryptedData[i] & 0xf0) >> 4, 16));
			result.append(Integer.toString(encryptedData[i] & 0x0f, 16));
		}

		return result.toString();
	}
	
	public static boolean parameterBooleanConvert(String data, String value) {
		if (data == null){
			return false;
		}else {
			return data.trim().equals(value);
		}
	}
	
	/**
	*	@param	source
	*	@param	seperator
	*	@return
	*/
	public static Vector<String> token(String  source, String  seperator)
	{
		Vector<String>  vector = new Vector<String>();
		if(source==null || source.trim().equals("") || seperator==null || seperator.equals(""))
		{
			return vector;
		}
		
		StringTokenizer tokens = new StringTokenizer(source, seperator);
		
		String  token = "";
		while(tokens.hasMoreTokens())
		{
			token = tokens.nextToken();
			if(token!=null && token.equals("")==false && token.trim()!=null && token.trim().equals("")==false)
			{
				vector.addElement(token.trim());
			}
		}
		
		return vector;
	}
	
	public static String convertUnicode(String src){
		char[] temp=src.toCharArray();
		StringBuffer result=new StringBuffer(temp.length);

		for(int i=0;i<temp.length;i++){
			if(temp[i]=='&' && temp.length>i+7 && temp[i+1]=='#' && temp[i+7]==';'){
				try{
					result.append((char)Integer.parseInt(src.substring(i+2, i+7)));
					i=i+7;
				}catch(NumberFormatException e){
					result.append(temp[i]);
				}
			} else
				result.append(temp[i]);
		}

		result.trimToSize();
		return result.toString();
	}
	
	public static String convertToUnicode(String src){
		src = src.trim();
		char[] chBuffer = src.toCharArray();
		StringBuffer buffer = new StringBuffer();
	
		for (int i = 0; i < chBuffer.length; i++) {
			if(((int) chBuffer[i] == 32)) {
				buffer.append(" ");
				continue;
			}
			buffer.append("\\u");
			buffer.append(Integer.toHexString((int) chBuffer[i]));
		}
		
		return buffer.toString();
	}
	
	/**
	 * String 값이 숫자인지 return. 
	 * @param str 버퍼
	 * @return boolean (문자열중 숫자가 아닌것이 있으면 false를 리턴한다.)
	 */
	public static boolean isDigit(String str) {
		char[] c = str.toCharArray();
		if(str.equals(""))return false;
		for (int i = 0; i < c.length; i++) {
			if (!Character.isDigit(c[i]))
				return false;
		}
		return true;
	}

	public static Object stringToObject(String str) {
		try {
			return new ObjectInputStream(new Base64InputStream(
					new ByteArrayInputStream(str.getBytes()), Base64.NO_PADDING | Base64.NO_WRAP)).readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isEmptyString(String str){
		return str == null || str.length() == 0;
	}

	/**
	 * 모든 HTML 태그를 제거하고 반환한다.
	 * @param str : String
	 * @return String
	 */
	public static String handleEscapeCharacter(String str) {
		String[] escapeCharacters = { "&gt;", "&lt;", "&amp;", "&quot;", "&apos;" };
		String[] onReadableCharacter = {">", "<", "&", "\"\"", "'"};
		for (int i = 0; i < escapeCharacters.length; i++) {
			str = str.replace(escapeCharacters[i], onReadableCharacter[i]);
		} return str;
	}

	public static String setTextHtml(String html){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
		} else {
			return Html.fromHtml(html).toString();
		}
	}

	public static boolean isValidPhoeNumber(String phoneNumber){
		String regex = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(phoneNumber);
		return m.matches();
	}
}
