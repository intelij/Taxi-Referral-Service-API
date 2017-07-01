package org.taxireferral.api.Model;

/**
 * Created by sumeet on 22/3/17.
 */
public class TripRequest {

    // Table Name
    public static final String TABLE_NAME = "TRIP_REQUEST";


    // Column names
    public static final String TRIP_REQUEST_ID = "TRIP_REQUEST_ID"; // primary key

    public static final String VEHICLE_ID = "VEHICLE_ID"; // foreign key unique
    public static final String END_USER_ID = "END_USER_ID"; // foreign key unique

    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
    public static final String TIMESTAMP_EXPIRES = "TIMESTAMP_EXPIRES";

    public static final String TRIP_REQUEST_STATUS = "TRIP_REQUEST_STATUS";

    public static final String LAT_PICK_UP_LOCATION = "LAT_PICK_UP_LOCATION";
    public static final String LON_PICK_UP_LOCATION = "LON_PICK_UP_LOCATION";
    public static final String PICK_UP_ADDRESS = "PICK_UP_ADDRESS";

    public static final String LAT_DESTINATION = "LAT_DESTINATION";
    public static final String LON_DESTINATION = "LON_DESTINATION";
    public static final String DESTINATION_ADDRESS = "DESTINATION_ADDRESS";


    // people above 18 are considered as adults in the context of taxi referral service
    public static final String ADULTS_TOTAL = "ADULTS_TOTAL";
}
