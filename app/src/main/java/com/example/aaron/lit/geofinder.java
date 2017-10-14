package com.example.aaron.lit;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Binder;
import android.os.IBinder;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Aaron on 10/13/17.
 */

public class geofinder extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        geofinder getService() {
            return geofinder.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    // some constructor
    public geofinder() {
    }

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
}