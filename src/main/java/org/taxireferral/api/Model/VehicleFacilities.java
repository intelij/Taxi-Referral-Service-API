package org.taxireferral.api.Model;

import org.taxireferral.api.ModelRoles.User;

/**
 * Created by sumeet on 22/3/17.
 */
public class VehicleFacilities {


    // Table Name
    public static final String TABLE_NAME = "VEHICLE_FACILITIES";


    // Column names
    public static final String FACILITIES_ID = "FACILITIES_ID"; // foreign key
    public static final String VEHICLE_ID = "VEHICLE_ID"; // foreign key


//    public static final String NAME = "NAME";
//    public static final String IMAGE_PATH = "IMAGE_PATH";
//    public static final String DESCRIPTION = "DESCRIPTION";



    // Create Table CurrentServiceConfiguration Provider
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + VehicleFacilities.TABLE_NAME + "("
                    + " " + VehicleFacilities.FACILITIES_ID + " int NOT NULL,"
                    + " " + VehicleFacilities.VEHICLE_ID + " int NOT NULL,"

                    + " FOREIGN KEY(" + VehicleFacilities.VEHICLE_ID +") REFERENCES " + Vehicle.TABLE_NAME + "(" + Vehicle.VEHICLE_ID + ") ON DELETE CASCADE,"
                    + " FOREIGN KEY(" + VehicleFacilities.FACILITIES_ID +") REFERENCES " + Facilities.TABLE_NAME + "(" + Facilities.FACILITIES_ID + ") ON DELETE CASCADE"
                    + ")";


    // + " " + VehicleType.TIMESTAMP_UPDATED + " timestamp with time zone not null default now() ,"


    // instance variables

//    private int facilitiesID;
//    private String name;
//    private String imagePath;
//    private String description;

}
