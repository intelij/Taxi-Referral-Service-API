package org.taxireferral.api.ModelRoles;

import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelRoles.Deprecated.Staff;

/**
 * Created by sumeet on 28/8/17.
 */
public class StaffPermissions {

    // Table Name for User
    public static final String TABLE_NAME = "STAFF_PERMISSIONS";

    // Column names
    public static final String PERMISSION_ID = "PERMISSION_ID";
    public static final String STAFF_ID = "STAFF_ID";
    public static final String DESIGNATION = "DESIGNATION";
    public static final String PERMIT_TAXI_REGISTRATION_AND_RENEWAL = "PERMIT_TAXI_REGISTRATION_AND_RENEWAL";


    // Create Table CurrentServiceConfiguration Provider
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + StaffPermissions.TABLE_NAME + "("
                    + " " + StaffPermissions.PERMISSION_ID + " SERIAL PRIMARY KEY,"
                    + " " + StaffPermissions.STAFF_ID + " int UNIQUE NOT NULL ,"
                    + " " + StaffPermissions.DESIGNATION + " text,"
                    + " " + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + " boolean NOT NULL default 'f',"
                    + " FOREIGN KEY(" + StaffPermissions.STAFF_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE"
                    + ")";







    // instance variables
    private int permissionID;
    private int staffUserID;
    private String designation;
    private boolean permitTaxiRegistrationAndRenewal;





    // getter and setters
    public int getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }

    public int getStaffUserID() {
        return staffUserID;
    }

    public void setStaffUserID(int staffUserID) {
        this.staffUserID = staffUserID;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public boolean isPermitTaxiRegistrationAndRenewal() {
        return permitTaxiRegistrationAndRenewal;
    }

    public void setPermitTaxiRegistrationAndRenewal(boolean permitTaxiRegistrationAndRenewal) {
        this.permitTaxiRegistrationAndRenewal = permitTaxiRegistrationAndRenewal;
    }
}
