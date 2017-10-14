import android.app.Activity;
import android.content.Context;

/**
 * Created by Aaron on 10/13/17.
 */

public class GeoActivity extends Activity {

    // some constructor
    public GeoActivity() {
    }

    // initialize location things
    public void initializeLocation() {
        LocationManager location = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // when location changes
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                // when status of provider changes
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

}
