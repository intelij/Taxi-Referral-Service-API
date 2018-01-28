package org.taxireferral.api.Globals;

/**
 * Created by sumeet on 22/3/17.
 */
public class GlobalConstants {



    public static String BASE_URI = "http://0.0.0.0:5500";

	public static String POSTGRES_CONNECTION_URL;
    public static String CONNECTION_URL_CREATE_DB;
	public static String POSTGRES_USERNAME;
	public static String POSTGRES_PASSWORD;

//    public static final int NOTIFICATION_TYPE_TRIP_REQUESTS = 1;
//    public static final int NOTIFICATION_TYPE_CURRENT_TRIP = 2;


    // credits and offers
    public static int REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION; // credited in the account of the one who refers
    public static int REFERRAL_CREDIT_FOR_END_USER_REGISTRATION; // referral credit - credited into the account of the one who refers

    public static int JOINING_CREDIT_FOR_DRIVER; // credit applied when a driver joins the platform
    public static int JOINING_CREDIT_FOR_END_USER; // credit applied when a end user joins the platform



    // constants
    public static int TOKEN_DURATION_MINUTES; // 24 hours for expiry of a token
    public static int EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES;
    public static int PHONE_OTP_EXPIRY_MINUTES;
    public static int PASSWORD_RESET_CODE_EXPIRY_MINUTES;
    public static int TRIP_REQUEST_EXPIRY_MINUTES;
    public static int TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES;

    public static int MONTHS_TO_EXTEND_TAXI_REGISTRATION_MIN;
    public static int MONTHS_TO_EXTEND_TAXI_REGISTRATION_MAX;

    public static int max_limit;
    // constants for restricting taxi charges
    public static int max_min_trip_charges;
    public static int max_charges_per_km;
    public static int free_pickup_distance;
    public static int taxi_referral_charges;

    public static int free_start_waiting_minutes;
    public static int free_minutes_per_km; // for calculating the waiting charges
    public static int wait_charges_per_minute;
    public static int tax_rate_in_percent;

    public static int MIN_TAX_ACCOUNT_BALANCE;
    public static int MIN_SERVICE_ACCOUNT_BALANCE;

    // value to multiply with shortest distance which gives approx real distance
    public static double SHORTEST_DISTANCE_MULTIPLIER;

    // services get suspended if the user current_dues exceed the max current dues
    public static int CREDIT_LIMIT_FOR_DRIVER = - 1000;
//    public static final int CREDIT_LIMIT_FOR_END_USER = - 1000;



    // style url for maps
    public static String TILESERVER_GL_STYLE_URL;
    public static String GEOCODER_ADDRESS;
    public static String NOTIFICATION_SERVER_HOST_MQTT;



    // roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_DRIVER = "DRIVER";
    public static final String ROLE_END_USER = "END_USER";





    // role codes
    public static final int ROLE_ADMIN_CODE = 1;
    public static final int ROLE_STAFF_CODE = 2;
    public static final int ROLE_DRIVER_CODE = 3;
    public static final int ROLE_END_USER_CODE = 4;

    // Trip Request Status
    public static final int TAXI_REQUESTED = 1;   // Color : White  // accept request - driver app
    public static final int REQUEST_APPROVED = 2; // Color : Yellow // Request Pickup - end user app
    public static final int PICKUP_REQUESTED = 3; // Color : Green  // start pickup   - driver app

    // Current Trip Status
    public static final int PICKUP_APPROVED = 1;
    public static final int START_JOURNEY_REQUESTED_BY_END_USER = 2;
    public static final int START_APPROVED_AND_TRIP_STARTED = 3;

    // Vehicle status
    public static final int AVIALABLE = 1;
    public static final int NOT_AVIALABLE = 2;
    public static final int OCCUPIED = 3;

}
