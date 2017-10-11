package org.taxireferral.api.ModelUtility;

/**
 * Created by sumeet on 8/6/17.
 */
public class LocationCurrentTrip {

    // class for storing latitude and longitude

    private double latitude;
    private double longitude;
    private double bearing;
    private double speed;
    private double distanceTravelledForPickup;
    private double distanceTravelledForTrip;
    private int rt_end_user_id;



    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getRt_end_user_id() {
        return rt_end_user_id;
    }

    public void setRt_end_user_id(int rt_end_user_id) {
        this.rt_end_user_id = rt_end_user_id;
    }

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
