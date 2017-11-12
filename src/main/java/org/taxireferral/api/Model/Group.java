package org.taxireferral.api.Model;

public class Group {


    // Status
    public static final int STATUS_CREATED = 1;
    public static final int STATUS_PAYMENT_LOCKED = 2;
    public static final int STATUS_TRIP_STARTED = 3;

    public static final int STATUS_CANCELLED = 4;
    


    // Table Name
    public static final String TABLE_NAME = "TRIP_GROUP";

    // Column names
    public static final String GROUP_ID = "GROUP_ID"; // primary key
    public static final String ADMIN_ID = "ADMIN_ID"; // user id of admin

    public static final String TRIP_STATUS = "TRIP_STATUS";

    public static final String START_TIME = "START_TIME";

    public static final String START_RADIUS = "START_RADIUS";
    public static final String LAT_START = "LAT_START";
    public static final String LON_START = "LON_START";
    public static final String START_ADDRESS = "START_ADDRESS";

    public static final String DESTINATION_RADIUS = "DESTINATION_RADIUS";
    public static final String LAT_DESTINATION = "LAT_DESTINATION";
    public static final String LON_DESTINATION = "LON_DESTINATION";
    public static final String DESTINATION_ADDRESS = "DESTINATION_ADDRESS";


    public static final String MIN_CHARGE_PER_HEAD = "MIN_CHARGE_PER_HEAD"; // minimum charge for single person
    public static final String MAX_CHARGE_PER_HEAD = "MAX_CHARGE_PER_HEAD"; // maximum charge for single person



}
