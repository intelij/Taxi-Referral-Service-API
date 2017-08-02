package org.taxireferral.api.ModelUtility;

/**
 * Created by sumeet on 8/6/17.
 */
public class LocationCurrentTrip {

    // class for storing latitude and longitude

    private double latitude;
    private double longitude;
    private double distanceTravelledForPickup;
    private double distanceTravelledForTrip;


    public double getDistanceTravelledForPickup() {
        return distanceTravelledForPickup;
    }

    public void setDistanceTravelledForPickup(double distanceTravelledForPickup) {
        this.distanceTravelledForPickup = distanceTravelledForPickup;
    }

    public double getDistanceTravelledForTrip() {
        return distanceTravelledForTrip;
    }

    public void setDistanceTravelledForTrip(double distanceTravelledForTrip) {
        this.distanceTravelledForTrip = distanceTravelledForTrip;
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
}
