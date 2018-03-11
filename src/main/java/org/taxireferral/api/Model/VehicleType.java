package org.taxireferral.api.Model;

import org.taxireferral.api.ModelRoles.User;
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

    public static final String MAX_MIN_TRIP_CHARGE = "MAX_MIN_TRIP_CHARGE";
    public static final String MAX_CHARGES_PER_KM = "MAX_CHARGES_PER_KM";




    // Create Table CurrentServiceConfiguration Provider
    public static final String createTable =

            "CREATE TABLE IF NOT EXISTS "
                    + VehicleType.TABLE_NAME + "("
                    + " " + VehicleType.VEHICLE_TYPE_ID + " SERIAL PRIMARY KEY,"
                    + " " + VehicleType.NAME + " text ,"
                    + " " + VehicleType.IMAGE_PATH + " text ,"
                    + " " + VehicleType.DESCRIPTION + " text ,"
                    + " " + VehicleType.MAX_MIN_TRIP_CHARGE + " float ,"
                    + " " + VehicleType.MAX_CHARGES_PER_KM + " float "
                    + ")";




    // instance variables

    private int vehicleTypeID;
    private String name;
    private String imagePath;
    private String description;
    private double maxMinTripCharges;
    private double maxChargesPerKm;




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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMaxMinTripCharges() {
        return maxMinTripCharges;
    }

    public void setMaxMinTripCharges(double maxMinTripCharges) {
        this.maxMinTripCharges = maxMinTripCharges;
    }

    public double getMaxChargesPerKm() {
        return maxChargesPerKm;
    }

    public void setMaxChargesPerKm(double maxChargesPerKm) {
        this.maxChargesPerKm = maxChargesPerKm;
    }
}
