package org.taxireferral.api.Model;

/**
 * Created by sumeet on 22/3/17.
 */
public class VehicleModel {

    // Table Name
    public static final String TABLE_NAME = "VEHICLE_MODEL";


    // Column names
    public static final String VEHICLE_MODEL_ID = "VEHICLE_MODEL_ID"; // primary key
    public static final String NAME = "NAME";
    public static final String PROFILE_IMAGE = "PROFILE_IMAGE";

    public static final String VEHICLE_TYPE_ID = "VEHICLE_TYPE_ID"; // foreign key

    public static final String SEATING_CAPACITY = "SEATING_CAPACITY";


}
