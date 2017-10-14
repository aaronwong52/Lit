package com.example.aaron.lit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Aaron on 10/13/17.
 */

public class geofinder extends Service
{
    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;

    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;
        GoogleMap map;
        Marker CurrentMarker;

        public LocationListener(String provider)
        {
            Log.e(TAG, "LocationListener " + provider);

            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            if (CurrentMarker != null)
                CurrentMarker.remove();
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markeroptions = new MarkerOptions();
            markeroptions.position(latlng);
            markeroptions.title("User position");
            markeroptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            CurrentMarker = map.addMarker(markeroptions);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11));
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listeners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
/*
    public class LocalBinder extends Binder {
        geofinder getService() {
            return geofinder.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    boolean provider_status;
    Location loc;

    // initialize location things
    public void updateLocation() {
        final LocationManager loc_manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // when location changes
                String locationProvider = LocationManager.NETWORK_PROVIDER;
                location = loc_manager.getLastKnownLocation(locationProvider);
                if (isBetterLocation(location, loc))
                    loc = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                // when status of provider changes
                if (status == 0)
                    provider_status = false;
            }

            public void onProviderEnabled(String provider) {
                // when provider is enabled by user
            }

            public void onProviderDisabled(String provider) {
                // when provider is disabled by user
            }
        };

        // update location every 5 seconds with a 1 meter change in position
        loc_manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, listener);
        if (loc.getAccuracy() < 2)
            loc_manager.removeUpdates(listener);
    }
    public Location returnLoc() {
        return loc;
    }
    private boolean isBetterLocation(Location location, Location current_loc) {
        if (current_loc == null)
            return true;
        float time_diff = location.getTime() - current_loc.getTime();
        float accuracy_diff = location.getAccuracy() - current_loc.getAccuracy();
        boolean moreAccurate = false;
        boolean newer = false;
        if (time_diff > 0)
            newer = true;
        if (accuracy_diff > 0)
            moreAccurate = true;
        if (newer && moreAccurate)
            return true;
        else return false;
    }
}*/