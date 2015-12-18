package barcan.virgil.com.shopassistant.backend.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.frontend.ShowProductActivity;

/**
 * Created by virgil on 14.12.2015.
 */
public class GeofenceIntentService extends IntentService {

    private final String TAG = GeofenceIntentService.class.getName();

    private List<NamedGeofence> namedGeofences;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GeofenceIntentService(String name) {
        super(name);
    }

    public GeofenceIntentService() {
        super("GeofenceIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("GeofenceIntentService.onHandleIntent");
        //Proof of Concept
        initNamedGeofences();

        GeofencingEvent event = GeofencingEvent.fromIntent(intent);

        if (event != null) {

            if (event.hasError()) {
                onError(event.getErrorCode());
            }
            else {
                int transition = event.getGeofenceTransition();

                if (transition == Geofence.GEOFENCE_TRANSITION_ENTER || transition == Geofence.GEOFENCE_TRANSITION_DWELL || transition == Geofence.GEOFENCE_TRANSITION_EXIT) {

                    List<String> geofenceIds = new ArrayList<>();

                    for (Geofence geofence : event.getTriggeringGeofences()) {
                        geofenceIds.add(geofence.getRequestId());
                    }

                    if (transition == Geofence.GEOFENCE_TRANSITION_ENTER || transition == Geofence.GEOFENCE_TRANSITION_DWELL) {
                        onEnteredGeofences(geofenceIds);
                    }
                }
            }
        }
    }

    private void initNamedGeofences() {

        NamedGeofence namedGeofenceEmag = new NamedGeofence();
        namedGeofenceEmag.setName("eMag");
        namedGeofenceEmag.setLatitude(47.172595);
        namedGeofenceEmag.setLongitude(27.559435);
        namedGeofenceEmag.setRadius((float) 0.5);
        namedGeofences.add(namedGeofenceEmag);

        NamedGeofence namedGeofenceAltex = new NamedGeofence();
        namedGeofenceEmag.setName("Altex");
        namedGeofenceEmag.setLatitude(47.155441);
        namedGeofenceEmag.setLongitude(27.604732);
        namedGeofenceEmag.setRadius((float) 0.5);
        namedGeofences.add(namedGeofenceAltex);

        NamedGeofence namedGeofenceContinental = new NamedGeofence();
        namedGeofenceEmag.setName("Continental");
        namedGeofenceEmag.setLatitude(47.137192);
        namedGeofenceEmag.setLongitude(27.598248);
        namedGeofenceEmag.setRadius((float) 0.5);
        namedGeofences.add(namedGeofenceContinental);

    }

    private void onEnteredGeofences(List<String> geofenceIds) {
        System.out.println("GeofenceIntentService.onEnteredGeofences");
        for (String geofenceId : geofenceIds) {
            String geofenceName = "";

            for (NamedGeofence namedGeofence : namedGeofences) {
                if (namedGeofence.getId().equals(geofenceId)) {
                    geofenceName = namedGeofence.getName();
                    break;
                }
            }

            String contextText = "You are close to the products sold by " + geofenceName;

            NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
            Intent intent = new Intent(this, ShowProductActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentTitle("Go grab them!")
                    .setContentText(contextText)
                    .setContentIntent(pendingNotificationIntent)
                    .setStyle(new Notification.BigTextStyle())
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(0, notification);
        }
    }

    private void onError(int i) {
        Log.e(TAG, "Geofencing Error: " + i);
    }
}
