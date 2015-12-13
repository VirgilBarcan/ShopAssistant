package barcan.virgil.com.shopassistant.backend.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.frontend.ShowProductActivity;
import barcan.virgil.com.shopassistant.model.Company;
import barcan.virgil.com.shopassistant.model.User;

/**
 * Created by virgil on 13.12.2015.
 */
public class LocationReceiver extends BroadcastReceiver {

    private static final String TAG = "LocationReceiver";

    private Context context;
    private Location currentLocation;
    private Controller controller;
    private User connectedUser;
    private List<Company> userShoppingListCompanies;

    private static LocationReceiver instance;

    public LocationReceiver() {

    }

    public LocationReceiver(Controller controller) {
        this.controller = controller;
        connectedUser = this.controller.getConnectedUser();
        userShoppingListCompanies = this.controller.getShoppingListCompanies(this.connectedUser);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("LocationReceiver.onReceive");

        String locationKey = "com.google.android.location.LOCATION";

        if (instance == null) {
            System.out.println("LocationReceiver.onReceive: instance null");

            instance = new LocationReceiver(Controller.getInstance());
        }

        this.context = context;

        if (intent.hasExtra(locationKey)) {
            Location location = (Location) intent.getExtras().get(locationKey);
            currentLocation = location;

            calculateDistancesAndNotify();
        }
    }

    /**
     * This method calculates distances from current position to all shops and, if needed, notifies the user
     */
    private void calculateDistancesAndNotify() {
        Log.v(TAG, "LocationReceiver.calculateDistancesAndNotify");

        Map<Company, Boolean> notifyUserAboutCompany = new HashMap<>();

        for (Company company : instance.userShoppingListCompanies) {
            Location shopLocation = new Location("");

            if (company.getLocation() != null) {
                shopLocation.setLatitude(company.getLocation().getLatitude());
                shopLocation.setLongitude(company.getLocation().getLongitude());

                System.out.println(company.getCompanyName() + " location: " + shopLocation.getLatitude() + ", " + shopLocation.getLongitude());

                float distanceToShop = currentLocation.distanceTo(shopLocation);
                if (distanceToShop < 500) {
                    //User is to within 100 meters of the company => NOTIFY
                    createNotification(shopLocation);

                    notifyUserAboutCompany.put(company, true);
                    System.out.println("LocationReceiver.calculateDistancesAndNotify: YOU ARE CLOSE TO THE PRODUCTS SOLD BY: " + company.getCompanyName() + " distance=" + distanceToShop);
                } else {
                    notifyUserAboutCompany.put(company, false);
                    System.out.println("LocationReceiver.calculateDistancesAndNotify: YOU ARE NOT CLOSE TO THE PRODUCTS SOLD BY: " + company.getCompanyName() + " distance=" + distanceToShop);
                }
            }
            else {
                //The location of the shop is not set, skip it
            }
        }
    }

    /**
     * This method creates the notification that signals the user he is close to a shop
     */
    private void createNotification(Location shopLocation) {
        System.out.println("LocationReceiver.createNotification");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String location = shopLocation.getLatitude() + "," + shopLocation.getLongitude();
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location + "&mode=w");
        Intent intentShowMap = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        intentShowMap.setPackage("com.google.android.apps.maps");

        Intent intentShowProducts = new Intent(context, ShowProductActivity.class);

        PendingIntent pendingIntentShowProducts = PendingIntent.getActivity(context, 0, intentShowProducts, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentShowMap = PendingIntent.getActivity(context, 0, intentShowMap, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications)
                .setStyle(new Notification.BigTextStyle())
                .addAction(R.drawable.ic_home, "PRODUCTS", pendingIntentShowProducts)
                .addAction(R.drawable.ic_edit_location, "MAP", pendingIntentShowMap)
                .setAutoCancel(true)
                .setContentTitle("Go grab them!")
                .setContentText("You are close to your wanted products!")
                .build();

        notificationManager.notify(1, notification);
    }
}
