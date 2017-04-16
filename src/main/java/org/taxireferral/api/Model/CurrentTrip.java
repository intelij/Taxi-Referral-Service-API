package org.taxireferral.api.Model;

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

    public static final String TIMESTAMP_STARTED = "TIMESTAMP_STARTED";
    public static final String TIMESTAMP_FINISHED = "TIMESTAMP_FINISHED";

    public static final String LAT_PICK_UP_LOCATION = "LAT_PICK_UP_LOCATION";
    public static final String LON_PICK_UP_LOCATION = "LON_PICK_UP_LOCATION";

    public static final String LAT_DESTINATION = "LAT_DESTINATION";
    public static final String LON_DESTINATION = "LON_DESTINATION";

    public static final String LAT_CURRENT = "LAT_CURRENT"; // not required - because it equals vehicle coordinates
    public static final String LON_CURRENT = "LON_CURRENT"; // not required - because it equals vehicle coordinates

    public static final String DISTANCE_TRAVELLED = "DISTANCE_TRAVELLED";

    public static final String CHARGES_PER_KM = "CHARGES_PER_KM";
    public static final String MIN_CHARGES = "MIN_CHARGES";
    public static final String REFERRAL_CHARGES = "REFERRAL_CHARGES";

}
