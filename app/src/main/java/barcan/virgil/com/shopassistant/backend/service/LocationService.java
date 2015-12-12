package barcan.virgil.com.shopassistant.backend.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.model.Company;
import barcan.virgil.com.shopassistant.model.Product;

/**
 * Created by virgil on 12.12.2015.
 */
public class LocationService extends Service implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String BROADCAST_ACTION = "LOCATION_UPDATE";
    public static final int LOCATION_UPDATE_INTERVAL = 1000 * 10; //milliseconds
    public static final int FASTEST_LOCATION_UPDATE_INTERVAL = 1000 * 5; //milliseconds
    private static final String TAG = "LocationService";

    private boolean currentlyProcessingLocation = false;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private Intent intent;
    private Controller controller;

    private List<Company> userShoppingListCompanies;

    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("LocationService.onCreate");
        Log.v(TAG, "LocationService.onCreate");

        //TODO: Do some initialization
        controller = Controller.getInstance();
        intent = new Intent(BROADCAST_ACTION);

        userShoppingListCompanies = controller.getShoppingListCompanies(controller.getConnectedUser());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("LocationService.onStartCommand");
        Log.v(TAG, "LocationService.onStartCommand");

        // if we are currently trying to get a location and the alarm manager has called this again,
        // no need to start processing a new location
        if (!currentlyProcessingLocation) {
            currentlyProcessingLocation = true;
            startTracking();
        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("LocationService.onLocationChanged: position: " + location.getLatitude() + ", " + location.getLongitude() + " accuracy: " + location.getAccuracy());
        Log.v(TAG, "LocationService.onLocationChanged: position: " + location.getLatitude() + ", " + location.getLongitude() + " accuracy: " + location.getAccuracy());

        if (location != null) {

            currentLocation = location;

            // we have our desired accuracy of 500 meters so lets quit this service,
            // onDestroy will be called and stop our location updates
            if (location.getAccuracy() < 2500.0f) {
                //stopLocationUpdates();
                //TODO: send notification about location or calculate distance to shops
                calculateDistancesAndNotify();
            }
        }
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

        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
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

    /**
     * This method calculates distances from current position to all shops and, if needed, notifies the user
     */
    private void calculateDistancesAndNotify() {
        Log.v(TAG, "LocationService.calculateDistancesAndNotify");

        Map<Company, Boolean> notifyUserAboutCompany = new HashMap<>();

        for (Company company : userShoppingListCompanies) {
            Location shopLocation = new Location("");

            if (company.getLocation() != null) {
                shopLocation.setLatitude(company.getLocation().getLatitude());
                shopLocation.setLongitude(company.getLocation().getLongitude());

                System.out.println(company.getCompanyName() + " location: " + shopLocation.getLatitude() + ", " + shopLocation.getLongitude());

                float distanceToShop = currentLocation.distanceTo(shopLocation);
                if (distanceToShop < 100) {
                    //User is to within 100 meters of the company => NOTIFY
                    notifyUserAboutCompany.put(company, true);
                    System.out.println("LocationService.calculateDistancesAndNotify: YOU ARE CLOSE TO THE PRODUCTS SOLD BY: " + company.getCompanyName() + " distance=" + distanceToShop);
                } else {
                    notifyUserAboutCompany.put(company, false);
                    System.out.println("LocationService.calculateDistancesAndNotify: YOU ARE NOT CLOSE TO THE PRODUCTS SOLD BY: " + company.getCompanyName() + " distance=" + distanceToShop);
                }
            }
            else {
                //The location of the shop is not set, skip it
            }
        }
    }
}
