package com.smart.school.util.helper;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.smart.school.util.CustomLog;

/**
 * 현재위치 위도, 경도 가져오는 클래스
 * @author taekwon
 *
 */
public class LocationHelper {
	private CustomLog log = new CustomLog();

	final int REQUEST_NET_SETTING = 7000;
	final int REQUEST_NETWORK_CHECK = 7001;

	final int TYPE_TEXTVIEW = 70;

	/**
	 * Context 객체 입니다.
	 */
	private Context mContext;

	private Activity mAct;

	/**
	 * 위도값을 저장할 변수 입니다.
	 */
	private double mLat;
	/**
	 * 경도값을 저장할 변수 입니다.
	 */
	private double mLng;
	/**
	 * GPS의 정확도 입니다.
	 */
	private int mAccuracy;
	/**
	 * 셋팅유무를 저장할 변수 입니다.
	 */
	private boolean isLocationSetting = false;
	/**
	 * ACCURACY의 변수 (자세히)
	 */
	public static final int ACCURACY_FINE = 1;
	/**
	 * ACCURACY의 변수 (보통)
	 */
	public static final int ACCURACY_COARSE = 2;

	String locationProvider;
	//위치 정보 매니져 객체
	LocationManager locationManager;
	int count; //위치 정보 갱신 횟수를 세기 위한 변수

	private double douLat;
	private double douLng;

	public LocationHelper(Activity act) {
		this.mContext = act;
		this.mAct = act;
//        this.mAccuracy = ACCURACY_COARSE;

		//위치 정보 매니져 객체 얻어오기
		locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		//위치정보 공급자 얻어오기
		locationProvider = locationManager.getBestProvider(new Criteria(), true);
//        Toast.makeText(this, "위치정보 공급자:"+locationProvider, 0).show();
		//가장 최근의 Location 객체 얻어오기
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		//실내에서 GPS 켰을 시 의 예외처리
		if(location == null){
        	location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
 	   	}

       if(location != null){
    	   locationManager.requestLocationUpdates(locationProvider, 2000, 10, mLocationListener);

           douLat = location.getLatitude();
           douLng = location.getLongitude();

           setDouLat(douLat);
           setDouLng(douLng);
       }
    }
 
    private void setDouLat(double douLat) {
		this.douLat = douLat;
	}

	public double getDouLat() {
		return douLat;
	}
	private void setDouLng(double douLng) {
		this.douLng = douLng;
	}

	public double getDouLng() {
		return douLng;
	}

	private static final int TWO_MINUTES = 1000 * 60 * 2;
	 
	/** 기존의 위치와 비교하여 더 좋은지 여부를 판단하고 위치 정보를 갱신하면 될 것이다
	  * @param location  기존의 위치와 비교할 새로운 위치정보
	  * @param currentBestLocation  현재 보유하고 있는 위치 정보
	  */
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // 기존의 위치 정보가 없다면 새로운게 좋은것이다.
	        return true;
	    }
	 
	    // 새로 들어온 위치 정보가 최근 것인지 아닌지 여부를 구분
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
	    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
	    boolean isNewer = timeDelta > 0;
	 
	    // 2분을 지났다면 새로운 위치 정보가 더 좋은 것이다.
	    if (isSignificantlyNewer) {
	        return true;
	    // 만약 새로들어온 위치 정보가 2분이전의 정보라면 안 좋은 것이다.
	    } else if (isSignificantlyOlder) {
	        return false;
	    }
	 
	    // 정확성을 가져와서 비교
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;
	 
	    // 위치 정보 프로바이더의 종류가 같은가?
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());
	 
	    // 정확성과 시간을 조합하여 품질을 결정
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else return isNewer && !isSignificantlyLessAccurate && isFromSameProvider;
    }
	 
	/** 두개의 위치 프로바이더가 같은지 비교하는 함수 */
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
 

	
//    /**
//     * 셋팅을 시작한다.
//     */
//    public void run() {
//
//    	
//        final LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
//        final Criteria criteria = new Criteria();
//        criteria.setAccuracy(mAccuracy);
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);       	// 정확도
//        criteria.setPowerRequirement(Criteria.POWER_LOW);    	// 전원 소비량
//        criteria.setAltitudeRequired(false);                 	// 고도, 높이 값을 얻어 올지를 결정
//        criteria.setBearingRequired(false);                  	// provider 기본 정보
//        criteria.setSpeedRequired(false);                    	//속도
//        criteria.setCostAllowed(true);                          //위치 정보를 얻어 오는데 들어가는 금전적 비용
//
//        // Provider 생성
//        final String bestProvider = locationManager.getBestProvider(criteria, true);
//        locationManager.requestLocationUpdates(bestProvider, 2000, 10, mLocationListener);
// 
//        Location location = locationManager.getLastKnownLocation(bestProvider);
//        updateWithNewLocation(location);
//    }
//     
	
	
    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }
 
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
//         
    private void updateWithNewLocation(Location location) {
        if (location == null) {  
            isLocationSetting = false;
            return;
        }
         
        mLat = location.getLatitude();  
        mLng = location.getLongitude();
        isLocationSetting = true;
        
        douLat = location.getLatitude();
        douLng = location.getLongitude();

        setDouLat(douLat);
        setDouLng(douLng);

//        Toast.makeText(mContext, "Location 가 셋팅 되었습니다.", Toast.LENGTH_SHORT).show();
    }
//     
//    /**
//     * 위도값을 리턴합니다.
//     * @return
//     */
//    public double getLat() {
//        return mLat;
//    }
//     
//    /**
//     * 경도값을 리턴합니다.
//     * @return
//     */
//    public double getLng() {
//        return mLng;
//    }
//     
//    /**
//     * 
//     * @return
//     */
//    public boolean isLocationSetting() {
//        return isLocationSetting;
//    }
//         
//    /**
//     * Criteria 객체에 Accuracy를 셋팅한다.
//     * @param accuracy
//     */
//    public void setAccuracy(int accuracy) { 
//        mAccuracy = accuracy;
//    }
//     
//    /**
//     * 위도와 경도값을 기준으로 주소값을 리턴합니다. 
//     * @param mContext Activity
//     * @param lat 위도
//     * @param lon 경도
//     * @return
//     */
//    public String getAddress() {
//        return LocationHelper.getAddress(mContext ,mLat , mLng);
//    }
//     
//    /**
//     * 지정한 위도와 경도값을 기준으로 주소를 리턴합니다.
//     * static 키워드로 설정 
//     * @param lat 위도
//     * @param lng 경도
//     * @return
//     */
//    public static String getAddress(Context mContext ,double lat , double lng) {
//        String addressString = "No address found";
//        Geocoder gc = new Geocoder(mContext, Locale.getDefault());
//        try {
//            List<Address> addresses = gc.getFromLocation(lat, lng, 1);
//            if (addresses.size() > 0) {
//                Address address = addresses.get(0);
//                addressString = address.getAddressLine(0);
//                addressString = addressString.substring(addressString.indexOf(" ") + 1);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return addressString;
//    }
    
}
