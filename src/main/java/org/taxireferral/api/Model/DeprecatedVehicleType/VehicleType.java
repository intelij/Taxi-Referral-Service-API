package org.taxireferral.api.Model.DeprecatedVehicleType;

import java.sql.Timestamp;

/**
 * Created by sumeet on 22/3/17.
 */
public class VehicleType {

    // Table Name
    public static final String TABLE_NAME = "VEHICLE_TYPE";


    // Column names
    public static final String VEHICLE_TYPE_ID = "VEHICLE_TYPE_ID"; // primary key
    public static final String NAME = "NAME";
    public static final String IMAGE_PATH = "IMAGE_PATH";
    public static final String DESCRIPTION = "DESCRIPTION";





    // Create Table CurrentServiceConfiguration Provider
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + VehicleType.TABLE_NAME + "("
                    + " " + VehicleType.VEHICLE_TYPE_ID + " SERIAL PRIMARY KEY,"
                    + " " + VehicleType.NAME + " text ,"
                    + " " + VehicleType.IMAGE_PATH + " text ,"
                    + " " + VehicleType.DESCRIPTION + " text "
                    + ")";


    // + " " + VehicleType.TIMESTAMP_UPDATED + " timestamp with time zone not null default now() ,"


    // instance variables

    private int vehicleTypeID;
    private String name;
    private String imagePath;
    private String description;

//    private int parent;
    private boolean backupExists;
    private Timestamp timestampCreated;
//    private Timestamp timestampUpdated;
//    private Timestamp timestampApplied;
//    private Timestamp timestampApproved;
//    private boolean isApproved;
    private Integer submittedBy;
//    private String notesForReviewer;
//    private String reviewerFeedback;
//    private int reviewedBy;






    public boolean isBackupExists() {
        return backupExists;
    }

    public void setBackupExists(boolean backupExists) {
        this.backupExists = backupExists;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }


    // getter and setter methods

    public int getVehicleTypeID() {
        return vehicleTypeID;
    }

    public void setVehicleTypeID(int vehicleTypeID) {
        this.vehicleTypeID = vehicleTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
