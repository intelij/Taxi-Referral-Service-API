package org.taxireferral.api.Model;

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
    public static final String CUSTOMER_RATING = "CUSTOMER_RATING";


    public static final String TIMESTAMP_STARTED = "TIMESTAMP_STARTED";
    public static final String TIMESTAMP_FINISHED = "TIMESTAMP_FINISHED";


    public static final String LAT_PICK_UP_LOCATION = "LAT_PICK_UP_LOCATION";
    public static final String LON_PICK_UP_LOCATION = "LON_PICK_UP_LOCATION";
    public static final String PICK_UP_ADDRESS = "PICK_UP_ADDRESS";


    public static final String LAT_DESTINATION = "LAT_DESTINATION";
    public static final String LON_DESTINATION = "LON_DESTINATION";
    public static final String DESTINATION_ADDRESS = "DESTINATION_ADDRESS";


    public static final String DISTANCE_TRAVELLED = "DISTANCE_TRAVELLED";

    public static final String CHARGES_PER_KM = "CHARGES_PER_KM";
    public static final String MIN_CHARGES = "MIN_CHARGES";
    public static final String REFERRAL_CHARGES = "REFERRAL_CHARGES";


    // people above 18 are considered as adults in the context of taxi referral service
    public static final String ADULTS_TOTAL = "ADULTS_TOTAL";



}
