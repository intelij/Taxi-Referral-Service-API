package org.taxireferral.api.Model;

import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 9/3/17.
 */

public class Vehicle {



    // Table Name
    public static final String TABLE_NAME = "VEHICLE";

    // Column names
    public static final String VEHICLE_ID = "VEHICLE_ID"; // primary key
    public static final String DRIVER_ID = "DRIVER_ID"; // foreign key
//    public static final String VEHICLE_MODEL = "VEHICLE_MODEL"; // foreign key

    public static final String VEHICLE_MODEL_NAME = "VEHICLE_MODEL_NAME";
    public static final String SEATING_CAPACITY = "SEATING_CAPACITY";

    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";

    public static final String ENABLED = "ENABLED";
    public static final String ENABLED_UPTO = "ENABLED_UPTO";
    public static final String RENEWED_BY = "RENEWED_BY";

    public static final String VEHICLE_STATUS = "VEHICLE_STATUS";


    public static final String MIN_TRIP_CHARGES = "MIN_TRIP_CHARGES";
    public static final String CHARGES_PER_KM = "CHARGES_PER_KM";


    public static final String LAT_CURRENT = "LAT_CURRENT";
    public static final String LON_CURRENT = "LON_CURRENT";
    public static final String TIMESTAMP_LOCATION_UPDATED = "TIMESTAMP_LOCATION_UPDATED";


    // maximum pick up distance which is free of cost. If pick up distance
    // exceeds this distance customer will pay extra charges for pickup
    // Extra charges = pick_up_distance - max_free_pickup_distance

//    public static final String MAX_FREE_PICKUP_DISTANCE = "MAX_FREE_PICKUP_DISTANCE";


    // timestamp at which the vehicle profile was created
    public static final String TIMESTAMP_PROFILE_CREATED = "TIMESTAMP_PROFILE_CREATED";
    public static final String VEHICLE_REGISTRATION_NUMBER = "VEHICLE_REGISTRATION_NUMBER";
    public static final String VEHICLE_INSURANCE_NUMBER = "VEHICLE_INSURANCE_NUMBER";
    public static final String VEHICLE_PUC_ID = "VEHICLE_PUC_ID";

//    public static final String DRIVING_LICENSE = "DRIVING_LICENSE";






    // Create Table Statement
    public static final String createTableVehiclePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + Vehicle.TABLE_NAME + "("
                    + " " + Vehicle.VEHICLE_ID + " SERIAL PRIMARY KEY,"
                    + " " + Vehicle.DRIVER_ID + " int UNIQUE NOT NULL ,"

                    + " " + Vehicle.VEHICLE_MODEL_NAME + " text,"
                    + " " + Vehicle.SEATING_CAPACITY + " int NOT NULL DEFAULT 0,"

                    + " " + Vehicle.PROFILE_IMAGE_URL + " text,"

                    + " " + Vehicle.ENABLED + " boolean NOT NULL default 'f',"
                    + " " + Vehicle.ENABLED_UPTO + " timestamp with time zone NOT NULL DEFAULT now(),"
                    + " " + Vehicle.RENEWED_BY + " int,"

                    + " " + Vehicle.VEHICLE_STATUS + " int NOT NULL default 2,"
                    // 2 stands for status not-available. Refer GlobalConstants for the values

                    + " " + Vehicle.MIN_TRIP_CHARGES + " int NOT NULL default 0,"
                    + " " + Vehicle.CHARGES_PER_KM + " int NOT NULL default 0,"

                    + " " + Vehicle.LAT_CURRENT + " float NOT NULL default 0,"
                    + " " + Vehicle.LON_CURRENT + " float NOT NULL default 0,"

                    + " " + Vehicle.TIMESTAMP_LOCATION_UPDATED + "  timestamp with time zone NOT NULL DEFAULT now(),"
                    + " " + Vehicle.TIMESTAMP_PROFILE_CREATED + "  timestamp with time zone NOT NULL DEFAULT now(),"

                    + " " + Vehicle.VEHICLE_REGISTRATION_NUMBER + " text,"
                    + " " + Vehicle.VEHICLE_INSURANCE_NUMBER + " text,"
                    + " " + Vehicle.VEHICLE_PUC_ID + " text,"

                    + " FOREIGN KEY(" + Vehicle.DRIVER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE"
                    + ")";







    // instance variables
    private int vehicleID;
    private int driverID;

    private String vehicleModelName;
    private int seatingCapacity;

    private String profileImageURL;

    private boolean enabled;
    private Timestamp enabledUpto;
    private int renewedBy;

    private int vehicleStatus;

    private int minTripCharges;
    private int chargesPerKM;

    private double latCurrent;
    private double lonCurrent;

    private Timestamp locationUpdated;
    private Timestamp profileCreated;

    private String registrationNumber;
    private String insuranceID;
    private String pollutionCertificateID;

    private double rt_distance;
    private User rt_driver;
    private double rt_fare_estimate;





    // getter and setters


    public double getRt_fare_estimate() {
        return rt_fare_estimate;
    }

    public void setRt_fare_estimate(double rt_fare_estimate) {
        this.rt_fare_estimate = rt_fare_estimate;
    }

    public Timestamp getEnabledUpto() {
        return enabledUpto;
    }

    public void setEnabledUpto(Timestamp enabledUpto) {
        this.enabledUpto = enabledUpto;
    }

    public int getRenewedBy() {
        return renewedBy;
    }

    public void setRenewedBy(int renewedBy) {
        this.renewedBy = renewedBy;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getPollutionCertificateID() {
        return pollutionCertificateID;
    }

    public void setPollutionCertificateID(String pollutionCertificateID) {
        this.pollutionCertificateID = pollutionCertificateID;
    }

    public String getVehicleModelName() {
        return vehicleModelName;
    }

    public void setVehicleModelName(String vehicleModelName) {
        this.vehicleModelName = vehicleModelName;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public User getRt_driver() {
        return rt_driver;
    }

    public void setRt_driver(User rt_driver) {
        this.rt_driver = rt_driver;
    }

    public int getMinTripCharges() {
        return minTripCharges;
    }

    public void setMinTripCharges(int minTripCharges) {
        this.minTripCharges = minTripCharges;
    }

    public double getRt_distance() {
        return rt_distance;
    }

    public void setRt_distance(double rt_distance) {
        this.rt_distance = rt_distance;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(int vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public int getChargesPerKM() {
        return chargesPerKM;
    }

    public void setChargesPerKM(int chargesPerKM) {
        this.chargesPerKM = chargesPerKM;
    }

    public double getLatCurrent() {
        return latCurrent;
    }

    public void setLatCurrent(double latCurrent) {
        this.latCurrent = latCurrent;
    }

    public double getLonCurrent() {
        return lonCurrent;
    }

    public void setLonCurrent(double lonCurrent) {
        this.lonCurrent = lonCurrent;
    }

    public Timestamp getLocationUpdated() {
        return locationUpdated;
    }

    public void setLocationUpdated(Timestamp locationUpdated) {
        this.locationUpdated = locationUpdated;
    }

    public Timestamp getProfileCreated() {
        return profileCreated;
    }

    public void setProfileCreated(Timestamp profileCreated) {
        this.profileCreated = profileCreated;
    }
}
