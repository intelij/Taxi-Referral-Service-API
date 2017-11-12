package org.taxireferral.api.Globals;

public class GlobalConstantsBackup {



    // credits and offers
    public static int REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION = 200; // credited in the account of the one who refers
    public static int REFERRAL_CREDIT_FOR_END_USER_REGISTRATION = 100; // referral credit - credited into the account of the one who refers

    public static int JOINING_CREDIT_FOR_DRIVER = 1000; // credit applied when a driver joins the platform
    public static int JOINING_CREDIT_FOR_END_USER = 500; // credit applied when a end user joins the platform


    // constants
    public static int TOKEN_DURATION_MINUTES = 24 * 60; // 24 hours for expiry of a token
    public static int EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES = 10;
    public static int PHONE_OTP_EXPIRY_MINUTES = 10;
    public static int PASSWORD_RESET_CODE_EXPIRY_MINUTES = 10;
    public static int TRIP_REQUEST_EXPIRY_MINUTES = 10;
    public static int TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES = 10;

    public static int MONTHS_TO_EXTEND_TAXI_REGISTRATION_MIN = 0;
    public static int MONTHS_TO_EXTEND_TAXI_REGISTRATION_MAX = 12;




    public static int max_limit = 100;    // 100 items per fetch
    // constants for restricting taxi charges
    public static int max_min_trip_charges = 50;    // 20 bucks
    public static int max_charges_per_km = 15;  // 12 bucks per km
    //    public static final int min_free_pickup_distance = 5; // 3 km
//    public static final int max_free_pickup_distance = 3; // 3 km
    public static int free_pickup_distance = 2; // 2 km
    public static int taxi_referral_charges = 10; // 3 bucks per km



    public static int free_start_waiting_minutes = 5;
    public static int free_minutes_per_km = 3; // for calculating the waiting charges
    public static int wait_charges_per_minute = 2;
    public static int tax_rate_in_percent = 5;

    public static int MIN_TAX_ACCOUNT_BALANCE = -50;
    public static int MIN_SERVICE_ACCOUNT_BALANCE = 0;

    // value to multiply with shortest distance which gives approx real distance
    public static double SHORTEST_DISTANCE_MULTIPLIER = 1.6;

    // services get suspended if the user current_dues exceed the max current dues
    public static int CREDIT_LIMIT_FOR_DRIVER = - 1000;
//    public static final int CREDIT_LIMIT_FOR_END_USER = - 1000;


}
