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






    // Trip Request Status : driver to customer negotiation status
    // Send Request
    public static final int TAXI_REQUESTED = 1;   // Color : White  // accept request - driver app
    public static final int REQUEST_APPROVED = 2; // Color : Yellow // Request Pickup - end user app
    public static final int PICKUP_REQUESTED = 3; // Color : Green  // start pickup   - driver app
//    public static final int PICKUP_APPROVED = 4;  // Color : Green


    

    // Current Trip Status
    public static final int PICKUP_APPROVED = 1;
    public static final int START_JOURNEY_REQUESTED_BY_END_USER = 2;
    public static final int START_APPROVED_AND_TRIP_STARTED = 3;

//    public static final int START_JOURNEY_REQUESTED_BY_DRIVER = 2;


//    public static final int PICKUP_LOCATION_SENT = 3;

    // Vehicle status
    public static final int AVIALABLE = 1;
    public static final int NOT_AVIALABLE = 2;
    public static final int OCCUPIED = 3;






    // credits and offers
    public static final int REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION = 200; // credited in the account of the one who refers
    public static final int REFERRAL_CREDIT_FOR_END_USER_REGISTRATION = 100; // referral credit - credited into the account of the one who refers

    public static final int JOINING_CREDIT_FOR_DRIVER = 1000; // credit applied when a driver joins the platform
    public static final int JOINING_CREDIT_FOR_END_USER = 500; // credit applied when a end user joins the platform

    // services get suspended if the user current_dues exceed the max current dues
    public static final int CREDIT_LIMIT_FOR_DRIVER = 1000;
    public static final int CREDIT_LIMIT_FOR_END_USER = 1000;


    // constants
    public static final int TOKEN_DURATION_MINUTES = 1;
    public static final int EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES = 10;
    public static final int PHONE_OTP_EXPIRY_MINUTES = 10;
    public static final int TRIP_REQUEST_EXPIRY_MINUTES = 10;
    public static final int TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES = 10;


    public static final int max_limit = 100;    // 100 items per fetch
    // constants for restricting taxi charges
    public static final int max_min_trip_charges = 50;    // 20 bucks
    public static final int max_charges_per_km = 15;  // 12 bucks per km
    //    public static final int min_free_pickup_distance = 5; // 3 km
//    public static final int max_free_pickup_distance = 3; // 3 km
    public static final int free_pickup_distance = 3; // 3 km
    public static final int taxi_referral_charges = 5; // 3 bucks per km
    public static final int free_minutes_per_km = 3; // for calculating the waiting charges

}
