package org.taxireferral.api.Model;

import org.taxireferral.api.ModelRoles.User;

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




    // Create Table CurrentServiceConfiguration Provider
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + VehicleType.TABLE_NAME + "("
                    + " " + VehicleType.VEHICLE_TYPE_ID + " SERIAL PRIMARY KEY,"
                    + " " + VehicleType.NAME + " text ,"
                    + " " + VehicleType.IMAGE_PATH + " text "
                    + ")";



    // instance variables

    private int vehicleTypeID;
    private String name;
    private String imagePath;




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
