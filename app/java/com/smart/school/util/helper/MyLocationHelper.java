package com.smart.school.util.helper;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.smart.school.app.config.iConfig;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by art on 2017-05-24.
 * GoogleApiClient Location
 */

public class MyLocationHelper implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private final String TAG = "LocationHelper";
    private final long INTERVAL = 1000 * 10;
    private final long FASTEST_INTERVAL = 1000 * 5;

    private Context mContext;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

    private String mLastUpdateTime;

    private double dLat;
    private double dLng;

    private OnLocationResult mOnLocationResult;

    public MyLocationHelper(Context context){
        mContext = context;
        mOnLocationResult = (OnLocationResult)mContext;

        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

//        setGoogleApiClientConnect();
    }

    public void setGoogleApiClientConnect(){
        /*if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();

        }*/
        mGoogleApiClient.connect();
    }

    public void setGoogleApiClientDisconnect(){
        mGoogleApiClient.disconnect();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, (Activity) mContext, 0).show();
            return false;
        }
    }

    public void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

//        updateUI();
    }

    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    public void updateUI() {
        if (null != mCurrentLocation) {
            dLat = mCurrentLocation.getLatitude();
            dLng = mCurrentLocation.getLongitude();

            mOnLocationResult.setLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
//            PrefManager.savePrefString(this, "CURRENT_LAT", strLat);
//            PrefManager.savePrefString(this, "CURRENT_LNG", strLng);

            CSharedPreferencesHelper.setValue(mContext, "CURRENT_LOCATION", iConfig.KEY_LAT, String.valueOf(dLat));
            CSharedPreferencesHelper.setValue(mContext, "CURRENT_LOCATION", iConfig.KEY_LNG, String.valueOf(dLng));
			/*tvLocation.setText("At Time: " + mLastUpdateTime + "\n" +
					"Latitude: " + lat + "\n" +
					"Longitude: " + lng + "\n" +
					"Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
					"Provider: " + mCurrentLocation.getProvider());*/
        } else {
//            PrefManager.savePrefString(this, "CURRENT_LAT", "");
//            PrefManager.savePrefString(this, "CURRENT_LNG", "");
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            updateUI();
        }

//        if (mRequestingLocationUpdates) {
            startLocationUpdates();
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
    }

    public interface OnLocationResult{
        void setLocation(double lat, double lng);
    }


}
