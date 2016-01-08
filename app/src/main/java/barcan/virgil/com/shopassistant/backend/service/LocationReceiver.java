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
import barcan.virgil.com.shopassistant.frontend.MainActivity;
import barcan.virgil.com.shopassistant.model.Company;
import barcan.virgil.com.shopassistant.model.Constants;
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

    public LocationReceiver() {
    }

    private void init() {
        if (this.controller == null)
            this.controller = Controller.getInstance(context);

        this.controller.initializeSharedPreferences();
        this.controller.createDatabaseHelper();

        if (this.connectedUser == null)
            this.connectedUser = this.controller.getConnectedUser();
        System.out.println("LocationReceiver.LocationReceiver: " + this.connectedUser);

        if (this.userShoppingListCompanies == null)
            this.userShoppingListCompanies = this.controller.getShoppingListCompanies(this.connectedUser);
        System.out.println("LocationReceiver.LocationReceiver: " + this.userShoppingListCompanies);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("LocationReceiver.onReceive");

        String locationKey = "com.google.android.location.LOCATION";

        this.context = context;

        init();

        if (intent.hasExtra(locationKey)) {
            Location location = (Location) intent.getExtras().get(locationKey);
            this.currentLocation = location;

            calculateDistancesAndNotify();
        }
    }

    /**
     * This method calculates distances from current position to all shops and, if needed, notifies the user
     */
    private void calculateDistancesAndNotify() {
        Log.v(TAG, "LocationReceiver.calculateDistancesAndNotify");

        Map<Company, Boolean> notifyUserAboutCompany = new HashMap<>();

        System.out.println("LocationReceiver.calculateDistancesAndNotify: " + this.userShoppingListCompanies);

        for (Company company : this.userShoppingListCompanies) {
            Location shopLocation = new Location("");

            if (company.getLocation() != null) {
                shopLocation.setLatitude(company.getLocation().getLatitude());
                shopLocation.setLongitude(company.getLocation().getLongitude());

                System.out.println(company.getCompanyName() + " location: " + shopLocation.getLatitude() + ", " + shopLocation.getLongitude());

                float distanceToShop = this.currentLocation.distanceTo(shopLocation);
                if (distanceToShop < 500) {
                    //User is to within 100 meters of the company => NOTIFY
                    this.createNotification(company.getCompanyName(), shopLocation);

                    notifyUserAboutCompany.put(company, true);
                    System.out.println("LocationReceiver.calculateDistancesAndNotify: YOU ARE CLOSE TO THE PRODUCTS SOLD BY: " + company.getCompanyName() + " distance=" + distanceToShop);
                } else {
                    notifyUserAboutCompany.put(company, false);
                    System.out.println("LocationReceiver.calculateDistancesAndNotify: YOU ARE NOT CLOSE TO THE PRODUCTS SOLD BY: " + company.getCompanyName() + " distance=" + distanceToShop);
                }
            }
        }
    }

    /**
     * This method creates the notification that signals the user he is close to a shop
     */
    private void createNotification(String companyName, Location shopLocation) {
        System.out.println("LocationReceiver.createNotification");

        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);

        String location = shopLocation.getLatitude() + "," + shopLocation.getLongitude();
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location + "&mode=w");
        Intent intentShowMap = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        intentShowMap.setPackage("com.google.android.apps.maps");

        Intent intentShowProducts = new Intent(context, MainActivity.class);
        intentShowProducts.putExtra(Constants.ACTIVITY_TO_START, "UserMainScreenActivity");
        intentShowProducts.putExtra(Constants.FRAGMENT_TO_START, "UserShoppingListFragment");
        intentShowProducts.putExtra(Constants.SHOP_PRODUCTS_TO_SHOW, companyName);

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
