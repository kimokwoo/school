package com.smart.school.app.config;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.smart.school.util.LruBitmapCache;
import com.smart.school.util.helper.CSharedPreferencesHelper;


public class AppController extends Application {

	public static final String TAG = AppController.class
			.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static AppController mInstance;

	private static boolean isStatus = false;

	private UncaughtExceptionHandler uncaughtExceptionHandler;

	@Override
	public void onCreate() {
		uncaughtExceptionHandler = new UncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
		super.onCreate();
		mInstance = this;

	}

	public static synchronized Context getGlobalApplicationContext() {
		if(mInstance == null)
			throw new IllegalStateException("this application does not inherit GlobalApplication");
		return mInstance;
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	public void setStatus(boolean isStatus){
		AppController.isStatus = isStatus;
	}

	public boolean getStatus(){
		return isStatus;
	}


	public String getLangCode(Context context){
		return CSharedPreferencesHelper.getValue(context, "SURF", "LANGUAGE", "");
	}

	public UncaughtExceptionHandler getUncaughtExceptionHandler() {
		return uncaughtExceptionHandler;
	}

	public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			Log.e("comm", "error : " + e.toString());
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(10);
		}
	}
}
