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

    public static final String TRIP_RATING_FOR_DRIVER = "TRIP_RATING_FOR_DRIVER";
    public static final String TRIP_RATING_FOR_CUSTOMER = "TRIP_RATING_FOR_CUSTOMER";






}
