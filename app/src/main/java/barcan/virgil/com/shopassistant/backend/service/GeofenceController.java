package barcan.virgil.com.shopassistant.backend.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by virgil on 14.12.2015.
 */
public class GeofenceController implements GoogleApiClient.ConnectionCallbacks {

    private final String TAG = GeofenceController.class.getName();

    private Context context;
    private GoogleApiClient googleApiClient;
    private List<NamedGeofence> namedGeofences;

    private static GeofenceController instance;

    public static GeofenceController getInstance() {
        if (instance == null) {
            instance = new GeofenceController();
        }

        return instance;
    }

    public void initGeofenceController(Context context) {
        this.context = context.getApplicationContext();

        namedGeofences = new ArrayList<>();
        loadGeofences();
    }

    private void loadGeofences() {
        //TODO: Get the Shops' locations
    }

    private void connectWithCallbacks(GoogleApiClient.ConnectionCallbacks callbacks) {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(callbacks)
                .addOnConnectionFailedListener(connectionFailedListener)
                .build();
        googleApiClient.connect();
    }

    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            Log.e(TAG, "Connecting to GoogleApiClient failed.");
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        Intent intent = new Intent();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
