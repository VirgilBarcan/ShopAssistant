package barcan.virgil.com.shopassistant.backend.service;

import com.google.android.gms.location.Geofence;

import java.util.UUID;

/**
 * Created by virgil on 14.12.2015.
 */
public class NamedGeofence implements Comparable {

    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private float radius;

    public Geofence geofence() {
        id = UUID.randomUUID().toString();
        return new Geofence.Builder()
                .setRequestId(id)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setCircularRegion(latitude, longitude, radius)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public int compareTo(Object another) {
        NamedGeofence other = (NamedGeofence) another;
        return name.compareTo(other.name);
    }
}
