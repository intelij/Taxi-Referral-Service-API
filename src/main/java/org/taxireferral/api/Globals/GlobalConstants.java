package org.taxireferral.api.Globals;

/**
 * Created by sumeet on 22/3/17.
 */
public class GlobalConstants {


    /* API Keys : Begin */


    public static final String MSG91_SMS_SERVICE_API_KEY = "169752ACa9EZW5gjr59903723";



    /* API Keys : End */

    // firebase server keys
    public static final String FIREBASE_DRIVER_KEY = "Key=" + "AIzaSyCujFExkfJ5bYwDYpMfbSWLr4NVemwD0KY";
    public static final String FIREBASE_END_USER_KEY = "Key=" + "AIzaSyC4eRU7FEf-DiRheyaQZrde-vI6W32taLU";

//    public static final int NOTIFICATION_TYPE_TRIP_REQUESTS = 1;
//    public static final int NOTIFICATION_TYPE_CURRENT_TRIP = 2;



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
    public static final int CREDIT_LIMIT_FOR_DRIVER = - 1000;
//    public static final int CREDIT_LIMIT_FOR_END_USER = - 1000;




    // constants
    public static final int TOKEN_DURATION_MINUTES = 24 * 60; // 24 hours for expiry of a token
    public static final int EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES = 10;
    public static final int PHONE_OTP_EXPIRY_MINUTES = 10;
    public static final int PASSWORD_RESET_CODE_EXPIRY_MINUTES = 10;
    public static final int TRIP_REQUEST_EXPIRY_MINUTES = 10;
    public static final int TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES = 10;

    public static final int MONTHS_TO_EXTEND_TAXI_REGISTRATION_MIN = 0;
    public static final int MONTHS_TO_EXTEND_TAXI_REGISTRATION_MAX = 12;



    public static final int max_limit = 100;    // 100 items per fetch
    // constants for restricting taxi charges
    public static final int max_min_trip_charges = 50;    // 20 bucks
    public static final int max_charges_per_km = 15;  // 12 bucks per km
    //    public static final int min_free_pickup_distance = 5; // 3 km
//    public static final int max_free_pickup_distance = 3; // 3 km
    public static final int free_pickup_distance = 2; // 2 km
    public static final int taxi_referral_charges = 10; // 3 bucks per km



    public static final int free_start_waiting_minutes = 5;
    public static final int free_minutes_per_km = 3; // for calculating the waiting charges
    public static final int wait_charges_per_minute = 2;
    public static final int tax_rate_in_percent = 5;

    public static final int MIN_TAX_ACCOUNT_BALANCE = 0;
    public static final int MIN_SERVICE_ACCOUNT_BALANCE = 0;




    // value to multiply with shortest distance which gives approx real distance
    public static final double SHORTEST_DISTANCE_MULTIPLIER = 1.6;

}
