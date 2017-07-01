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
    public static final String CHARGES_PER_KM = "CHARGES_PER_KM";
    public static final String MIN_TRIP_CHARGES = "MIN_TRIP_CHARGES";
    public static final String REFERRAL_CHARGES = "REFERRAL_CHARGES";

    // people above 18 are considered as adults in the context of taxi referral service
    public static final String ADULTS_TOTAL = "ADULTS_TOTAL";


//    public static final String LAT_CURRENT = "LAT_CURRENT"; // not required - because it equals vehicle coordinates
//    public static final String LON_CURRENT = "LON_CURRENT"; // not required - because it equals vehicle coordinates

    // fraud detection mechanisms
    // 1. If distance travelled is not in sync with
    // shortest distance calculated with lat and lon then its a case of fraud or tampering
}
