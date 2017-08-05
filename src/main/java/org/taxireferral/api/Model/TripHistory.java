package org.taxireferral.api.Model;

import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 22/3/17.
 */
public class TripHistory {

    // Table Name
    public static final String TABLE_NAME = "TRIP_HISTORY";

    // Column names
    public static final String TRIP_HISTORY_ID = "TRIP_HISTORY_ID"; // primary key
    public static final String VEHICLE_ID = "VEHICLE_ID"; // foreign key
    public static final String END_USER_ID = "END_USER_ID"; // foreign key

            // ratings are the extra fields compared to current trip
    public static final String DRIVER_RATING = "DRIVER_RATING";
    public static final String END_USER_RATING = "END_USER_RATING";

    public static final String FEEDBACK_BY_DRIVER = "FEEDBACK_BY_DRIVER";
    public static final String FEEDBACK_BY_END_USER = "FEEDBACK_BY_END_USER";

    public static final String IS_CANCELLED = "IS_CANCELLED";
    public static final String IS_CANCELLED_BY_USER = "IS_CANCELLED_BY_USER";
    public static final String REASON_FOR_CANCELLATION = "REASON_FOR_CANCELLATION";

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


    // people above 18 are considered as adults in the context of taxi referral service
    //    public static final String ADULTS_TOTAL = "ADULTS_TOTAL";




    // Create Table Statement
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + TripHistory.TABLE_NAME + "("

                    + " " + TripHistory.TRIP_HISTORY_ID + " SERIAL PRIMARY KEY,"
                    + " " + TripHistory.VEHICLE_ID + " int NOT NULL,"
                    + " " + TripHistory.END_USER_ID + " int NOT NULL,"

                    + " " + TripHistory.DRIVER_RATING + " int," // can be null
                    + " " + TripHistory.END_USER_RATING + " int," // can be null

                    + " " + TripHistory.FEEDBACK_BY_DRIVER + " text,"
                    + " " + TripHistory.FEEDBACK_BY_END_USER + " text,"

                    + " " + TripHistory.IS_CANCELLED + " boolean NOT NULL default 'f',"
                    + " " + TripHistory.IS_CANCELLED_BY_USER + " boolean NOT NULL default 'f',"
                    + " " + TripHistory.REASON_FOR_CANCELLATION + " text,"

                    + " " + TripHistory.TIMESTAMP_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"
                    + " " + TripHistory.TIMESTAMP_STARTED + " timestamp with time zone,"
                    + " " + TripHistory.TIMESTAMP_FINISHED + " timestamp with time zone,"

                    + " " + TripHistory.LAT_START_LOCATION + " float NOT NULL default 300,"
                    + " " + TripHistory.LON_START_LOCATION + " float NOT NULL default 300,"

                    + " " + TripHistory.LAT_PICK_UP_LOCATION + " float NOT NULL default 300,"
                    + " " + TripHistory.LON_PICK_UP_LOCATION + " float NOT NULL default 300,"
                    + " " + TripHistory.PICK_UP_ADDRESS + " text,"

                    + " " + TripHistory.LAT_DESTINATION + " float NOT NULL default 300,"
                    + " " + TripHistory.LON_DESTINATION + " float NOT NULL default 300,"
                    + " " + TripHistory.DESTINATION_ADDRESS + " text,"

                    + " " + TripHistory.DISTANCE_TRAVELLED_FOR_PICKUP + " float NOT NULL default 0,"
                    + " " + TripHistory.DISTANCE_TRAVELLED_FOR_TRIP + " float NOT NULL default 0,"

                    + " " + TripHistory.FREE_PICKUP_DISTANCE + " float NOT NULL default 0,"
                    + " " + TripHistory.REFERRAL_CHARGES + " float NOT NULL default 0,"

                    + " " + TripHistory.MIN_TRIP_CHARGES + " float NOT NULL default 0,"
                    + " " + TripHistory.CHARGES_PER_KM + " float NOT NULL default 0,"

                    + " FOREIGN KEY(" + TripHistory.VEHICLE_ID +") REFERENCES " + Vehicle.TABLE_NAME + "(" + Vehicle.VEHICLE_ID + ") ON DELETE CASCADE,"
                    + " FOREIGN KEY(" + TripHistory.END_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE"
                    + ")";






    // instance variables

    private int tripHistoryID;
    private int vehicleID;
    private int endUserID;

    private int driverRating;
    private int endUserRating;

    private String feedbackByDriver;
    private String feedbackByEndUser;

    private boolean isCancelled;
    private boolean isCancelledByUser;
    private String reasonForCancellation;

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







    // getter and setters

    public String getFeedbackByDriver() {
        return feedbackByDriver;
    }

    public void setFeedbackByDriver(String feedbackByDriver) {
        this.feedbackByDriver = feedbackByDriver;
    }

    public String getFeedbackByEndUser() {
        return feedbackByEndUser;
    }

    public void setFeedbackByEndUser(String feedbackByEndUser) {
        this.feedbackByEndUser = feedbackByEndUser;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean isCancelledByUser() {
        return isCancelledByUser;
    }

    public void setCancelledByUser(boolean cancelledByUser) {
        isCancelledByUser = cancelledByUser;
    }

    public String getReasonForCancellation() {
        return reasonForCancellation;
    }

    public void setReasonForCancellation(String reasonForCancellation) {
        this.reasonForCancellation = reasonForCancellation;
    }

    public int getTripHistoryID() {
        return tripHistoryID;
    }

    public void setTripHistoryID(int tripHistoryID) {
        this.tripHistoryID = tripHistoryID;
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

    public int getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(int driverRating) {
        this.driverRating = driverRating;
    }

    public int getEndUserRating() {
        return endUserRating;
    }

    public void setEndUserRating(int endUserRating) {
        this.endUserRating = endUserRating;
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

    public double getReferralCharges() {
        return referralCharges;
    }

    public void setReferralCharges(double referralCharges) {
        this.referralCharges = referralCharges;
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
}
