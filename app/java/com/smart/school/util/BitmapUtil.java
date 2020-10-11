package com.smart.school.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class BitmapUtil {
	
	public static Bitmap getBitmap(String p_Value , int newHeight, int newWidth){
	    BitmapFactory.Options bfOptions=new BitmapFactory.Options();
	    InputStream l_input  	  = null;
	    Bitmap 		l_bitmap 	  = null;
	    Bitmap 		l_scaleBitmap = null;
	    
	    bfOptions.inDither=false;                     
	    bfOptions.inPurgeable=true;                   
	    bfOptions.inInputShareable=true;              
	    bfOptions.inTempStorage=new byte[32 * 1024]; 
	    
		try {
			l_input = new URL(p_Value).openStream();
			l_bitmap =BitmapFactory.decodeStream(new PatchInputStream(l_input),null,bfOptions);
			l_input.close();
		}catch (MalformedURLException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(l_bitmap != null){
			l_scaleBitmap = getResizedBitmap(l_bitmap , newHeight , newWidth);
		}
		return l_scaleBitmap;
	}
	
	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		 
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		 
		return resizedBitmap;
		 
	}
	
	public static Bitmap getScaledBitmap(String picturePath, int width, int height) {
		
	    BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
	    sizeOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(picturePath, sizeOptions);

	    int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

	    sizeOptions.inJustDecodeBounds = false;
	    sizeOptions.inSampleSize = inSampleSize;

	    return BitmapFactory.decodeFile(picturePath, sizeOptions);
	    
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);

	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }

	    return inSampleSize;
	    
	}

	public static Bitmap getBitmapFromURL(String src) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(src);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			return BitmapFactory.decodeStream(input);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			if(connection!=null){
				connection.disconnect();
			}
		}
	}

	/**
	 * Bitmap 이미지를 가운데를 기준으로 w, h 크기 만큼 crop한다.
	 *
	 * @param src 원본
	 * @param w 넓이
	 * @param h 높이
	 * @return
	 */
	public static Bitmap cropCenterBitmap(Bitmap src, int w, int h) {
		if(src == null)
			return null;

		int width = src.getWidth();
		int height = src.getHeight();

		if(width < w && height < h)
			return src;

		int x = 0;
		int y = 0;

		if(width > w)
			x = (width - w)/2;

		if(height > h)
			y = (height - h)/2;

		int cw = w; // crop width
		int ch = h; // crop height

		if(w > width)
			cw = width;

		if(h > height)
			ch = height;

		return Bitmap.createBitmap(src, x, y, cw, ch);
	}

	public static Bitmap downloadImage(String url, int reSize) {
		Bitmap bitmap = null;
		InputStream stream = null;
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inSampleSize = reSize;

		try {
			stream = getHttpConnection(url);
			bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
			stream.close();
		}
		catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("downloadImage"+ e1.toString());
		}
		return bitmap;
	}

	public static  InputStream getHttpConnection(String urlString)  throws IOException {

		InputStream stream = null;
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();

		try {
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			httpConnection.setRequestMethod("GET");
			httpConnection.connect();

			if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				stream = httpConnection.getInputStream();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("downloadImage" + ex.toString());
		}
		return stream;
	}

	/**
	 * Bitmap이미지의 가로, 세로 사이즈를 리사이징 한다.
	 *
	 * @param source 원본 Bitmap 객체
	 * @param maxResolution 제한 해상도
	 * @return 리사이즈된 이미지 Bitmap 객체
	 */
	public static Bitmap resizeBitmapImage(Bitmap source, int maxResolution)
	{
		if(source != null) {
			int width = source.getWidth();
			int height = source.getHeight();
			int newWidth = width;
			int newHeight = height;
			float rate;

			if (width > height) {
				if (maxResolution < width) {
					rate = maxResolution / (float) width;
					newHeight = (int) (height * rate);
					newWidth = maxResolution;
				}
			} else {
				if (maxResolution < height) {
					rate = maxResolution / (float) height;
					newWidth = (int) (width * rate);
					newHeight = maxResolution;
				}
			}
			return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
		}
		return null;
	}

	/**
	 * 지정한 패스의 파일의 EXIF 정보를 읽어서 회전시킬 각도 구하기
	 *
	 * @param filepath
	 *       bitmap file path
	 * @return degree
	 */
	public synchronized static int GetExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;

		try {
			//.png 파일에서는 안됨
			if(!filepath.contains(".png")) {
				exif = new ExifInterface(filepath);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

			if (orientation != -1) {
				// We only recognize a subset of orientation tag values.
				switch(orientation) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						degree = 90;
						break;

					case ExifInterface.ORIENTATION_ROTATE_180:
						degree = 180;
						break;

					case ExifInterface.ORIENTATION_ROTATE_270:
						degree = 270;
						break;
				}
			}
		}

		return degree;
	}

	/**
	 * 지정한 패스의 파일을 EXIF 정보에 맞춰 회전시키기
	 *
	 * @param bitmap
	 *       bitmap handle
	 * @return Bitmap
	 */
	public synchronized static Bitmap GetRotatedBitmap(Bitmap bitmap, int degrees) {
		if (degrees != 0 && bitmap != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) bitmap.getWidth() / 2,
					(float) bitmap.getHeight() / 2 );
			try {
				Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), m, true);
				if (bitmap != b2) {
					bitmap.recycle();
					bitmap = b2;
				}
			}
			catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}

		return bitmap;
	}

	/**
	 * Image LOCAL_PATH Save (input Bitmap -> saved file JPEG)
	 * @param bitmap : input bitmap file
	 * @param name   : output file name
	 */
	public static void saveBitmapToJpeg(Bitmap bitmap, String filePath, String name){
		// Get Absolute Path in External Sdcard
//		String file_name 	= name/* +".jpg"*/;

		File file_path;
		try{
			file_path = new File(filePath);
			if(!file_path.isDirectory()){
				file_path.mkdirs();
			}
			File imgFile = new File(filePath + name);
			boolean isCreate = imgFile.createNewFile();
			if(isCreate) {
                FileOutputStream out = new FileOutputStream(imgFile);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.close();
            }

		}catch(FileNotFoundException exception){
			exception.printStackTrace();
		}catch(IOException exception){
			exception.getMessage();
		}
	}

	/**
	 * Image LOCAL_PATH Save (input Bitmap -> saved file JPEG)
	 * @param bitmap : input bitmap file
	 * @param filePath   : output file name
	 */
	public static void saveBitmapToJpeg2(Bitmap bitmap, String filePath){

		File file_path;
		try{
			file_path = new File(filePath);
			if(!file_path.isDirectory()){
				file_path.mkdirs();
			}
			File imgFile = new File(filePath);
			imgFile.createNewFile();
			FileOutputStream out = new FileOutputStream(imgFile);

			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.close();

		}catch(FileNotFoundException exception){
			exception.getMessage();
		}catch(IOException exception){
			exception.printStackTrace();
		}
	}

	/**
	 * Image LOCAL_PATH Save (input Bitmap -> saved file JPEG)
	 * @param bitmap : input bitmap file
	 * @param name   : output file name
	 */
	public static String saveBitmapToJpeg3(Bitmap bitmap, String name){
		// Get Absolute Path in External Sdcard
//		String file_name 	= name/* +".jpg"*/;
		String savePath 	= Environment.getExternalStorageDirectory() + "/dcim/camera/";

		File file_path;
		try{
			file_path = new File(savePath);
			if(!file_path.isDirectory()){
				file_path.mkdirs();
			}
			File imgFile = new File(savePath + name);
			imgFile.createNewFile();
			FileOutputStream out = new FileOutputStream(imgFile);

			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.close();
		}catch(FileNotFoundException exception){
			exception.getMessage();
		}catch(IOException exception){
			exception.getMessage();
		}
		return savePath + name;
	}


	/**
	 * Image LOCAL_PATH Save (input Bitmap -> saved file JPEG)
	 * @param bitmap : input bitmap file
	 * @param name   : output file name
	 */
	public static String saveBitmapToPng(Bitmap bitmap, String name){
		// Get Absolute Path in External Sdcard
//		String file_name 	= name/* +".jpg"*/;
		String savePath 	= Environment.getExternalStorageDirectory() + "/dcim/camera/";

		File file_path;
		try{
			file_path = new File(savePath);
			if(!file_path.isDirectory()){
				file_path.mkdirs();
			}
			File imgFile = new File(savePath + name);
			imgFile.createNewFile();
			FileOutputStream out = new FileOutputStream(imgFile);

			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.close();
		}catch(FileNotFoundException exception){
			exception.getMessage();
		}catch(IOException exception){
			exception.getMessage();
		}
		return savePath + name;
	}

	/**
	 * Read the saved image
	 * @param path : Image Path
	 * @return saved image
	 */
	public static String[] readImage(String path){
		File file = new File(path);

		return file.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				boolean isImg = false;

				if(name.toLowerCase().endsWith(".png")) isImg = true;
				if(name.toLowerCase().endsWith(".jpg")) isImg = true;
				return isImg;
			}
		});
	}

	/**
	 * image url을 받아서 bitmap을 생성하고 리턴합니다
	 * @param url 얻고자 하는 image url
	 * @return 생성된 bitmap
	 */
	private static Bitmap getBitmap(String url) {
		URL imgUrl = null;
		HttpURLConnection connection = null;
		InputStream is = null;

		Bitmap retBitmap = null;

		try {
			imgUrl = new URL(url);
			connection = (HttpURLConnection) imgUrl.openConnection();
			connection.setDoInput(true); //url로 input받는 flag 허용
			connection.connect(); //연결
			is = connection.getInputStream(); // get inputstream
			retBitmap = BitmapFactory.decodeStream(is);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			return retBitmap;
		}
	}

	public static Bitmap decodeUriToBitmap(Context context, Uri sendUri) {
		Bitmap getBitmap = null;
		try {
			InputStream image_stream;
			try {
				image_stream = context.getContentResolver().openInputStream(sendUri);
				getBitmap = BitmapFactory.decodeStream(image_stream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getBitmap;
	}
}