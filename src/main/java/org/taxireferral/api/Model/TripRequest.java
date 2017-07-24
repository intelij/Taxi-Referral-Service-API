package org.taxireferral.api.Model;

import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 22/3/17.
 */
public class TripRequest {

    // Table Name
    public static final String TABLE_NAME = "TRIP_REQUEST";


    // Column names
    public static final String TRIP_REQUEST_ID = "TRIP_REQUEST_ID"; // primary key

    public static final String VEHICLE_ID = "VEHICLE_ID"; // foreign key
    public static final String END_USER_ID = "END_USER_ID"; // foreign key

    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
    public static final String TIMESTAMP_EXPIRES = "TIMESTAMP_EXPIRES";

    public static final String TRIP_REQUEST_STATUS = "TRIP_REQUEST_STATUS";

    public static final String LAT_PICK_UP = "LAT_PICK_UP";
    public static final String LON_PICK_UP = "LON_PICK_UP";
    public static final String PICK_UP_ADDRESS = "PICK_UP_ADDRESS";

    public static final String LAT_DESTINATION = "LAT_DESTINATION";
    public static final String LON_DESTINATION = "LON_DESTINATION";
    public static final String DESTINATION_ADDRESS = "DESTINATION_ADDRESS";


    // people above 18 are considered as adults in the context of taxi referral service
    public static final String ADULTS_MALES_COUNT = "ADULTS_MALES_COUNT";
    public static final String ADULTS_FEMALES_COUNT = "ADULTS_FEMALES_COUNT";
    public static final String CHILDREN_COUNT = "CHILDREN_COUNT";




    // Create Table Statement
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + TripRequest.TABLE_NAME + "("

                    + " " + TripRequest.TRIP_REQUEST_ID + " SERIAL PRIMARY KEY,"
                    + " " + TripRequest.VEHICLE_ID + " int NOT NULL,"
                    + " " + TripRequest.END_USER_ID + " int NOT NULL,"

                    + " " + TripRequest.TIMESTAMP_CREATED + "  timestamp with time zone NOT NULL DEFAULT now(),"
                    + " " + TripRequest.TIMESTAMP_EXPIRES + "  timestamp with time zone NOT NULL,"

                    + " " + TripRequest.TRIP_REQUEST_STATUS + " int NOT NULL default 1,"

                    + " " + TripRequest.LAT_PICK_UP + " float NOT NULL default 300,"
                    + " " + TripRequest.LON_PICK_UP + " float NOT NULL default 300,"
                    + " " + TripRequest.PICK_UP_ADDRESS + " text,"

                    + " " + TripRequest.LAT_DESTINATION + " float NOT NULL default 300,"
                    + " " + TripRequest.LON_DESTINATION + " float NOT NULL default 300,"
                    + " " + TripRequest.DESTINATION_ADDRESS + " text,"

                    + " " + TripRequest.ADULTS_MALES_COUNT + " int,"
                    + " " + TripRequest.ADULTS_FEMALES_COUNT + " int,"
                    + " " + TripRequest.CHILDREN_COUNT + " int,"

                    + " FOREIGN KEY(" + TripRequest.VEHICLE_ID +") REFERENCES " + Vehicle.TABLE_NAME + "(" + Vehicle.VEHICLE_ID + ") ON DELETE CASCADE,"
                    + " FOREIGN KEY(" + TripRequest.END_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE"

                    + ")";




    // instance variables

    private int tripRequestID;
    private int vehicleID;
    private int endUserID;

    private Timestamp timestampCreated;
    private Timestamp timestampExpires;

    private int tripRequestStatus;


    private double latPickUp;
    private double lonPickUp;
    private String pickUpAddress;

    private double latDestination;
    private double lonDestination;
    private String destinationAddress;

    private int adultMalesCount;
    private int adultFemalesCount;
    private int childrenCount;


    // stores the taxi
    private Vehicle rt_vehicle;
    private User rt_end_user;



    // getter and setters

    public User getRt_end_user() {
        return rt_end_user;
    }

    public void setRt_end_user(User rt_end_user) {
        this.rt_end_user = rt_end_user;
    }

    public Vehicle getRt_vehicle() {
        return rt_vehicle;
    }

    public void setRt_vehicle(Vehicle rt_vehicle) {
        this.rt_vehicle = rt_vehicle;
    }

    public int getTripRequestID() {
        return tripRequestID;
    }

    public void setTripRequestID(int tripRequestID) {
        this.tripRequestID = tripRequestID;
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

    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Timestamp getTimestampExpires() {
        return timestampExpires;
    }

    public void setTimestampExpires(Timestamp timestampExpires) {
        this.timestampExpires = timestampExpires;
    }

    public int getTripRequestStatus() {
        return tripRequestStatus;
    }

    public void setTripRequestStatus(int tripRequestStatus) {
        this.tripRequestStatus = tripRequestStatus;
    }

    public double getLatPickUp() {
        return latPickUp;
    }

    public void setLatPickUp(double latPickUp) {
        this.latPickUp = latPickUp;
    }

    public double getLonPickUp() {
        return lonPickUp;
    }

    public void setLonPickUp(double lonPickUp) {
        this.lonPickUp = lonPickUp;
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

    public int getAdultMalesCount() {
        return adultMalesCount;
    }

    public void setAdultMalesCount(int adultMalesCount) {
        this.adultMalesCount = adultMalesCount;
    }

    public int getAdultFemalesCount() {
        return adultFemalesCount;
    }

    public void setAdultFemalesCount(int adultFemalesCount) {
        this.adultFemalesCount = adultFemalesCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }
}
