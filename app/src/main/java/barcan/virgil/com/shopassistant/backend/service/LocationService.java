package barcan.virgil.com.shopassistant.backend.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.frontend.ShowProductActivity;
import barcan.virgil.com.shopassistant.model.Company;

/**
 * Created by virgil on 12.12.2015.
 */
public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String BROADCAST_ACTION = "LOCATION_UPDATE";
    public static final int LOCATION_UPDATE_INTERVAL = 1000 * 10; //milliseconds
    public static final int FASTEST_LOCATION_UPDATE_INTERVAL = 1000 * 5; //milliseconds
    private static final String TAG = "LocationService";

    private boolean currentlyProcessingLocation = false;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Controller controller;

    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("LocationService.onCreate");
        Log.v(TAG, "LocationService.onCreate");

        //TODO: Do some initialization
        controller = Controller.getInstance();

        Log.i(TAG, "LocationService.onCreate: controller=" + controller);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("LocationService.onStartCommand");
        Log.v(TAG, "LocationService.onStartCommand");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // if we are currently trying to get a location and the alarm manager has called this again,
                // no need to start processing a new location
                if (!currentlyProcessingLocation) {
                    currentlyProcessingLocation = true;
                    startTracking();
                }
            }
        };
        runnable.run();
        this.stopSelf();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("LocationService.onConnected");
        Log.v(TAG, "LocationService.onConnected");

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_LOCATION_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        Intent intent = new Intent(this, LocationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("LocationService.onConnectionSuspended");
        Log.v(TAG, "LocationService.onConnectionSuspended");

        stopLocationUpdates();
        stopSelf();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("LocationService.onConnectionFailed");
        Log.v(TAG, "LocationService.onConnectionSuspended");
    }

    /**
     * This method starts tracking GPS position
     * It registers itself to LocationServices
     */
    private void startTracking() {
        System.out.println("LocationService.startTracking");
        Log.v(TAG, "LocationService.onConnectionSuspended");

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        }
        else {
            System.out.println("LocationService.startTracking: ERROR! Unable to connect to Google Play services!");
        }
    }

    /**
     * This method stops receiving location updates
     */
    private void stopLocationUpdates() {
        Log.v(TAG, "LocationService.stopLocationUpdates");

        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

}
