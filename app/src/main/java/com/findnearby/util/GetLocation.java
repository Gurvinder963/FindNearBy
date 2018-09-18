package com.findnearby.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.findnearby.interfaces.OnLoactionFindListner;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class GetLocation {


    private static final int REQUEST_CHECK_SETTINGS = 21213;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private LocationRequest mLocationRequest;
    Activity mActivity;
    OnLoactionFindListner mLoactionFindListner;


    public GetLocation(Activity context, OnLoactionFindListner loactionFindListner){
      this.mActivity=context;
      this.mLoactionFindListner=loactionFindListner;

    }
    public void build(){
        createLocationRequest();
        checkLocationSettings();
    }
    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest).setAlwaysShow(false);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(Objects.requireNonNull(mActivity)).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {


                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location requests here.
                    getLocation();
                } catch (ApiException exception) {

                    switch (exception.getStatusCode()) {

                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            // Cast to a resolvable exception.
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                mActivity.startIntentSenderForResult(resolvable.getResolution().getIntentSender(), REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null);
                                // resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException | ClassCastException e) {

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                    Log.d("djhfj", "djfhdj");
                }
            }
        });


    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);

        Task<Location> location = mFusedLocationClient.getLastLocation();
        location.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                   // Toast.makeText(mActivity, "LAT: " + location.getLatitude() + " LONG: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    String latLngString = location.getLatitude() + "," + location.getLongitude();
                    mLoactionFindListner.onFind(latLngString);

                }
                {
                    Toast.makeText(mActivity,"Unable to find location ,Please try again! ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
