package org.taxireferral.api.Model;

import java.sql.Timestamp;

/**
 * Created by sumeet on 22/3/17.
 */
public class Facilities {


    // wi-fi, water, newspaper, ac,


    // Table Name
    public static final String TABLE_NAME = "FACILITIES";


    // Column names
    public static final String FACILITIES_ID = "FACILITIES_ID"; // primary key
    public static final String NAME = "NAME";
    public static final String IMAGE_PATH = "IMAGE_PATH";
    public static final String DESCRIPTION = "DESCRIPTION";




    // Create Table CurrentServiceConfiguration Provider
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + Facilities.TABLE_NAME + "("
                    + " " + Facilities.FACILITIES_ID + " SERIAL PRIMARY KEY,"
                    + " " + Facilities.NAME + " text ,"
                    + " " + Facilities.IMAGE_PATH + " text ,"
                    + " " + Facilities.DESCRIPTION + " text "
                    + ")";


    // + " " + VehicleType.TIMESTAMP_UPDATED + " timestamp with time zone not null default now() ,"


    // instance variables

    private int facilitiesID;
    private String name;
    private String imagePath;
    private String description;




    public int getFacilitiesID() {
        return facilitiesID;
    }

    public void setFacilitiesID(int facilitiesID) {
        this.facilitiesID = facilitiesID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
