package org.taxireferral.api.Model;

/**
 * Created by sumeet on 9/3/17.
 */
public class Vehicle {

    // Table Name
    public static final String TABLE_NAME = "VEHICLE";


    // Column names
    public static final String VEHICLE_ID = "VEHICLE_ID"; // primary key
    public static final String MIN_TRIP_CHARGES = "MIN_TRIP_CHARGES";
    public static final String CHARGES_PER_KM = "CHARGES_PER_KM";
    public static final String VEHICLE_MODEL = "VEHICLE_MODEL"; // foreign key
    public static final String STATUS = "STATUS";
    public static final String LAT_CURRENT = "LAT_CURRENT";
    public static final String LON_CURRENT = "LON_CURRENT";

    public static final String TIMESTAMP_REGISTERED = "TIMESTAMP_REGISTERED";
    public static final String IP_ADDRESS = "IP_ADDRESS";
    public static final String PORT = "PORT";




}
