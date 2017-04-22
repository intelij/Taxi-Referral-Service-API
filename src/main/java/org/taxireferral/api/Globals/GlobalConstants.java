package org.taxireferral.api.Globals;

/**
 * Created by sumeet on 22/3/17.
 */
public class GlobalConstants {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_DRIVER = "DRIVER";
    public static final String ROLE_END_USER = "END_USER";


    public static final String ROLE_DRIVER_DISABLED = "DRIVER_DISABLED";
    public static final String ROLE_END_USER_DISABLED = "END_USER_DISABLED";

    public static final int ROLE_ADMIN_CODE = 1;
    public static final int ROLE_STAFF_CODE = 2;
    public static final int ROLE_DRIVER_CODE = 3;
    public static final int ROLE_END_USER_CODE = 4;


    public static final int TOKEN_DURATION_MINUTES = 3;


    public static final int STATUS_TAXI_REQUESTED = 1;
    public static final int STATUS_REQUEST_APPROVED = 2;
    public static final int PICKUP_LOCATION_SENT = 3;
    public static final int START_JOURNEY_REQUESTED_DRIVER = 4;
    public static final int START_JOURNEY_REQUESTED_END_USER = 5;
    public static final int APPROVE_START = 6;


    public static final int EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES = 10;
}
