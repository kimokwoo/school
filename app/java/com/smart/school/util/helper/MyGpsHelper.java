package com.smart.school.util.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.smart.school.app.config.iConfig;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by surf on 2018. 2. 14..
 */

public class MyGpsHelper {

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    public Context mContext;

    private Double dLat = 0.0;
    private Double dLng = 0.0;

    public MyGpsHelper(Context context){
        mContext = context;
        settingGPS();

        Location userLocation = getMyLocation();
        if(userLocation != null){
            dLat = userLocation.getLatitude();
            dLng = userLocation.getLongitude();

            CSharedPreferencesHelper.setValue(mContext, "SURF", iConfig.KEY_LAT, String.valueOf(dLat));
            CSharedPreferencesHelper.setValue(mContext, "SURF", iConfig.KEY_LNG, String.valueOf(dLng));
        }
    }

    @SuppressLint("MissingPermission")
    private Location getMyLocation(){
        // 수동으로 위치 구하기
        Location currentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(currentLocation != null){
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        }
        return currentLocation;
    }

    private void settingGPS() {
        // Acquire a reference to the system Location Manager
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                dLat = location.getLatitude();
                dLng = location.getLongitude();

                CSharedPreferencesHelper.setValue(mContext, "SURF", iConfig.KEY_LAT, String.valueOf(dLat));
                CSharedPreferencesHelper.setValue(mContext, "SURF", iConfig.KEY_LNG, String.valueOf(dLng));

                stopListening();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
    }

    private void stopListening(){
        mLocationManager.removeUpdates(mLocationListener);
    }

    /**
     * 위도값을 가져옵니다.
     * */
    public double getLatitude(){
        return dLat;
    }

    /**
     * 경도값을 가져옵니다.
     * */
    public double getLongitude(){
        return dLng;
    }

    public static void turnGpsOn(final Context ctx) {
        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                /*.setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);*/

        LocationSettingsRequest.Builder settingsBuilder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        settingsBuilder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(ctx)
                .checkLocationSettings(settingsBuilder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);

                } catch (ApiException ex) {
                    switch (ex.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) ex;
                                resolvableApiException.startResolutionForResult((Activity) ctx, iConfig.REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                            break;
                    }
                }
            }
        });
    }
}
