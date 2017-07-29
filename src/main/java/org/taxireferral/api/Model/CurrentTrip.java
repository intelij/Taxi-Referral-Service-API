package org.taxireferral.api.Model;

import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 22/3/17.
 */
public class CurrentTrip {


    // Table Name
    public static final String TABLE_NAME = "CURRENT_TRIP";

    // Column names
    public static final String CURRENT_TRIP_ID = "CURRENT_TRIP_ID"; // primary key
    public static final String VEHICLE_ID = "VEHICLE_ID"; // foreign key unique
    public static final String END_USER_ID = "END_USER_ID"; // foreign key unique

    public static final String CURRENT_TRIP_STATUS = "CURRENT_TRIP_STATUS";

    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
    public static final String TIMESTAMP_STARTED = "TIMESTAMP_STARTED";
    public static final String TIMESTAMP_FINISHED = "TIMESTAMP_FINISHED";

    public static final String LAT_START_LOCATION = "LAT_START_LOCATION";
    public static final String LON_START_LOCATION = "LON_START_LOCATION";

    public static final String LAT_PICK_UP_LOCATION = "LAT_PICK_UP_LOCATION";
    public static final String LON_PICK_UP_LOCATION = "LON_PICK_UP_LOCATION";
    public static final String PICK_UP_ADDRESS = "PICK_UP_ADDRESS";

    public static final String LAT_DESTINATION = "LAT_DESTINATION";
    public static final String LON_DESTINATION = "LON_DESTINATION";
    public static final String DESTINATION_ADDRESS = "DESTINATION_ADDRESS";

    public static final String DISTANCE_TRAVELLED_FOR_PICKUP = "DISTANCE_TRAVELLED_FOR_PICKUP";
    public static final String DISTANCE_TRAVELLED_FOR_TRIP = "DISTANCE_TRAVELLED_FOR_TRIP";

    public static final String FREE_PICKUP_DISTANCE = "FREE_PICKUP_DISTANCE";
    public static final String REFERRAL_CHARGES = "REFERRAL_CHARGES";

    public static final String MIN_TRIP_CHARGES = "MIN_TRIP_CHARGES";
    public static final String CHARGES_PER_KM = "CHARGES_PER_KM";



//    public static final String LAT_CURRENT = "LAT_CURRENT"; // not required - because it equals vehicle coordinates
//    public static final String LON_CURRENT = "LON_CURRENT"; // not required - because it equals vehicle coordinates

    // fraud detection mechanisms
    // 1. If distance travelled is not in sync with
    // shortest distance calculated with lat and lon then its a case of fraud or tampering




    // Create Table Statement
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + CurrentTrip.TABLE_NAME + "("


                    + " " + CurrentTrip.CURRENT_TRIP_ID + " SERIAL PRIMARY KEY,"
                    + " " + CurrentTrip.VEHICLE_ID + " int UNIQUE NOT NULL,"
                    + " " + CurrentTrip.END_USER_ID + " int UNIQUE NOT NULL,"

                    + " " + CurrentTrip.CURRENT_TRIP_STATUS + " int NOT NULL,"

                    + " " + CurrentTrip.TIMESTAMP_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"
                    + " " + CurrentTrip.TIMESTAMP_STARTED + " timestamp with time zone,"
                    + " " + CurrentTrip.TIMESTAMP_FINISHED + " timestamp with time zone,"

                    + " " + CurrentTrip.LAT_START_LOCATION + " float NOT NULL default 300,"
                    + " " + CurrentTrip.LON_START_LOCATION + " float NOT NULL default 300,"

                    + " " + CurrentTrip.LAT_PICK_UP_LOCATION + " float NOT NULL default 300,"
                    + " " + CurrentTrip.LON_PICK_UP_LOCATION + " float NOT NULL default 300,"
                    + " " + CurrentTrip.PICK_UP_ADDRESS + " text,"

                    + " " + CurrentTrip.LAT_DESTINATION + " float NOT NULL default 300,"
                    + " " + CurrentTrip.LON_DESTINATION + " float NOT NULL default 300,"
                    + " " + CurrentTrip.DESTINATION_ADDRESS + " text,"

                    + " " + CurrentTrip.DISTANCE_TRAVELLED_FOR_PICKUP + " float NOT NULL default 0,"
                    + " " + CurrentTrip.DISTANCE_TRAVELLED_FOR_TRIP + " float NOT NULL default 0,"

                    + " " + CurrentTrip.FREE_PICKUP_DISTANCE + " float NOT NULL default 0,"
                    + " " + CurrentTrip.REFERRAL_CHARGES + " float NOT NULL default 0,"

                    + " " + CurrentTrip.MIN_TRIP_CHARGES + " float NOT NULL default 0,"
                    + " " + CurrentTrip.CHARGES_PER_KM + " float NOT NULL default 0,"

                    + " FOREIGN KEY(" + CurrentTrip.VEHICLE_ID +") REFERENCES " + Vehicle.TABLE_NAME + "(" + Vehicle.VEHICLE_ID + ") ON DELETE CASCADE,"
                    + " FOREIGN KEY(" + CurrentTrip.END_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE"
                    + ")";



    // instance variables

    private int currentTripID;
    private int vehicleID;
    private int endUserID;

    private int currentTripStatus;

    private Timestamp timestampCreated;
    private Timestamp timestampStarted;
    private Timestamp timestampFinished;

    private double latStartLocation;
    private double lonStartLocation;

    private double latPickUpLocation;
    private double lonPickUpLocation;
    private String pickUpAddress;

    private double latDestination;
    private double lonDestination;
    private String destinationAddress;

    private double distanceTravelledForPickup; // in kilometers
    private double distanceTravelledForTrip; // in kilometers

    private double freePickUpDistance; // distance upto which there are no charges for pick up
    private double referralCharges;
    private double minTripCharges;
    private double chargesPerKm;


    private Vehicle rt_vehicle;
    private User rt_end_user;





    // getter and setters


    public Vehicle getRt_vehicle() {
        return rt_vehicle;
    }

    public void setRt_vehicle(Vehicle rt_vehicle) {
        this.rt_vehicle = rt_vehicle;
    }

    public User getRt_end_user() {
        return rt_end_user;
    }

    public void setRt_end_user(User rt_end_user) {
        this.rt_end_user = rt_end_user;
    }

    public int getCurrentTripID() {
        return currentTripID;
    }

    public void setCurrentTripID(int currentTripID) {
        this.currentTripID = currentTripID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(int endUserID) {
        this.endUserID = endUserID;
    }

    public int getCurrentTripStatus() {
        return currentTripStatus;
    }

    public void setCurrentTripStatus(int currentTripStatus) {
        this.currentTripStatus = currentTripStatus;
    }

    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Timestamp getTimestampStarted() {
        return timestampStarted;
    }

    public void setTimestampStarted(Timestamp timestampStarted) {
        this.timestampStarted = timestampStarted;
    }

    public Timestamp getTimestampFinished() {
        return timestampFinished;
    }

    public void setTimestampFinished(Timestamp timestampFinished) {
        this.timestampFinished = timestampFinished;
    }

    public double getLatStartLocation() {
        return latStartLocation;
    }

    public void setLatStartLocation(double latStartLocation) {
        this.latStartLocation = latStartLocation;
    }

    public double getLonStartLocation() {
        return lonStartLocation;
    }

    public void setLonStartLocation(double lonStartLocation) {
        this.lonStartLocation = lonStartLocation;
    }

    public double getLatPickUpLocation() {
        return latPickUpLocation;
    }

    public void setLatPickUpLocation(double latPickUpLocation) {
        this.latPickUpLocation = latPickUpLocation;
    }

    public double getLonPickUpLocation() {
        return lonPickUpLocation;
    }

    public void setLonPickUpLocation(double lonPickUpLocation) {
        this.lonPickUpLocation = lonPickUpLocation;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public double getLatDestination() {
        return latDestination;
    }

    public void setLatDestination(double latDestination) {
        this.latDestination = latDestination;
    }

    public double getLonDestination() {
        return lonDestination;
    }

    public void setLonDestination(double lonDestination) {
        this.lonDestination = lonDestination;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
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

    public double getFreePickUpDistance() {
        return freePickUpDistance;
    }

    public void setFreePickUpDistance(double freePickUpDistance) {
        this.freePickUpDistance = freePickUpDistance;
    }

    public double getMinTripCharges() {
        return minTripCharges;
    }

    public void setMinTripCharges(double minTripCharges) {
        this.minTripCharges = minTripCharges;
    }

    public double getChargesPerKm() {
        return chargesPerKm;
    }

    public void setChargesPerKm(double chargesPerKm) {
        this.chargesPerKm = chargesPerKm;
    }

    public double getReferralCharges() {
        return referralCharges;
    }

    public void setReferralCharges(double referralCharges) {
        this.referralCharges = referralCharges;
    }
}
