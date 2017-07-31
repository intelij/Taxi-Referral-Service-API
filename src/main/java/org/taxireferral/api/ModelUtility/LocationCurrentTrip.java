package org.taxireferral.api.ModelUtility;

/**
 * Created by sumeet on 8/6/17.
 */
public class LocationCurrentTrip {

    // class for storing latitude and longitude

    private double latitude;
    private double longitude;
    private double distancePickupIncrement;
    private double distanceTripIncrement;




    public double getDistancePickupIncrement() {
        return distancePickupIncrement;
    }

    public void setDistancePickupIncrement(double distancePickupIncrement) {
        this.distancePickupIncrement = distancePickupIncrement;
    }

    public double getDistanceTripIncrement() {
        return distanceTripIncrement;
    }

    public void setDistanceTripIncrement(double distanceTripIncrement) {
        this.distanceTripIncrement = distanceTripIncrement;
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
