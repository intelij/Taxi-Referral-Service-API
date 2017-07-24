package org.taxireferral.api.Globals;

/**
 * Created by sumeet on 22/3/17.
 */
public class GlobalConstants {

    // roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_DRIVER = "DRIVER";
    public static final String ROLE_END_USER = "END_USER";


//    public static final String ROLE_DRIVER_DISABLED = "DRIVER_DISABLED";
//    public static final String ROLE_END_USER_DISABLED = "END_USER_DISABLED";

    // role codes
    public static final int ROLE_ADMIN_CODE = 1;
    public static final int ROLE_STAFF_CODE = 2;
    public static final int ROLE_DRIVER_CODE = 3;
    public static final int ROLE_END_USER_CODE = 4;





    // constants
    public static final int TOKEN_DURATION_MINUTES = 20;
    public static final int EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES = 10;
    public static final int TRIP_REQUEST_EXPIRY_MINUTES = 10;
    public static final int TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES = 10;


    public static final int max_limit = 100;    // 100 items per fetch

    // constants for restricting taxi charges
    public static final int max_min_trip_charges = 20;    // 20 bucks
    public static final int max_charges_per_km = 12;  // 12 bucks per km
//    public static final int min_free_pickup_distance = 5; // 3 km
//    public static final int max_free_pickup_distance = 3; // 3 km
    public static final int free_pickup_distance = 3; // 3 km
    public static final int taxi_referral_charges = 5; // 3 bucks per km



    // Trip Request Status : driver to customer negotiation status
    // Send Request
    public static final int TAXI_REQUESTED = 1;   // Color : White  // accept request - driver app
    public static final int REQUEST_APPROVED = 2; // Color : Yellow // Request Pickup - end user app
    public static final int PICKUP_REQUESTED = 3; // Color : Green  // start pickup   - driver app
//    public static final int PICKUP_APPROVED = 4;  // Color : Green


    

    // Current Trip Status
    public static final int PICKUP_APPROVED = 1;
    public static final int START_JOURNEY_REQUESTED_BY_DRIVER = 2;
    public static final int START_JOURNEY_REQUESTED_BY_END_USER = 3;
    public static final int START_APPROVED_AND_TRIP_STARTED = 4;



//    public static final int PICKUP_LOCATION_SENT = 3;

    // Vehicle status
    public static final int AVIALABLE = 1;
    public static final int NOT_AVIALABLE = 2;
    public static final int OCCUPIED = 3;

}
