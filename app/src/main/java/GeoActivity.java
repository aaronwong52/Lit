import android.app.Activity;
import android.content.Context;

/**
 * Created by Aaron on 10/13/17.
 */

public class GeoActivity extends Activity {

    // some constructor
    public GeoActivity() {
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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, locationListener);
    }
    private boolean isBetterLocation(Location location, Location current_loc) {
        if (current_loc == null)
            return true;
        float time_diff = location.getTime() - current_loc.getTime();
        int accuracy_diff = (int) location.getAccuracy() - current_loc.getAccuracy();
        boolean moreAccurate = false;
        boolean newer = false;
        if (time_diff > 0)
            newer = true;
        if (accuracy_diff > 0)
            moreAccurate = true;
        if (newer && moreAccurate)
            return true;
    }
}
