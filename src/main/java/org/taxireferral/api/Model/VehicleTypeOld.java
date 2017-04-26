package org.taxireferral.api.Model;

import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 22/3/17.
 */
public class VehicleTypeOld {

    // Table Name
    public static final String TABLE_NAME = "VEHICLE_TYPE_OLD";


    // Column names
    public static final String VEHICLE_TYPE_ID = "VEHICLE_TYPE_ID"; // primary key
    public static final String NAME = "NAME";
    public static final String IMAGE_PATH = "IMAGE_PATH";
    public static final String DESCRIPTION = "DESCRIPTION";

    public static final String PARENT = "PARENT";
    public static final String BACKUP_EXISTS = "BACKUP_EXISTS";
    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
    public static final String TIMESTAMP_UPDATED = "TIMESTAMP_UPDATED";
    public static final String TIMESTAMP_APPLIED = "TIMESTAMP_APPLIED";
    public static final String TIMESTAMP_APPROVED = "TIMESTAMP_APPROVED";
    public static final String IS_APPROVED = "IS_APPROVED";
    public static final String SUBMITTED_BY = "SUBMITTED_BY";
    public static final String NOTES_FOR_REVIEWER = "NOTES_FOR_REVIEWER";
    public static final String REVIEWER_FEEDBACK = "REVIEWER_FEEDBACK";
    public static final String REVIEWED_BY = "REVIEWED_BY";



    // Create Table CurrentServiceConfiguration Provider
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + VehicleTypeOld.TABLE_NAME + "("
                    + " " + VehicleTypeOld.VEHICLE_TYPE_ID + " SERIAL PRIMARY KEY,"
                    + " " + VehicleTypeOld.NAME + " text ,"
                    + " " + VehicleTypeOld.IMAGE_PATH + " text ,"
                    + " " + VehicleTypeOld.DESCRIPTION + " text ,"

                    + " " + VehicleTypeOld.PARENT + " int ,"
                    + " " + VehicleTypeOld.BACKUP_EXISTS + " boolean not null default 'f' ,"
                    + " " + VehicleTypeOld.TIMESTAMP_CREATED + " timestamp with time zone not null default now() ,"
                    + " " + VehicleTypeOld.TIMESTAMP_UPDATED + " timestamp with time zone not null default now() ,"
                    + " " + VehicleTypeOld.TIMESTAMP_APPLIED + " timestamp with time zone ,"
                    + " " + VehicleTypeOld.TIMESTAMP_APPROVED + " timestamp with time zone ,"

                    + " " + VehicleTypeOld.IS_APPROVED + " boolean ,"
                    + " " + VehicleTypeOld.SUBMITTED_BY + " int ,"
                    + " " + VehicleTypeOld.NOTES_FOR_REVIEWER + " text ,"
                    + " " + VehicleTypeOld.REVIEWER_FEEDBACK + " text ,"
                    + " " + VehicleTypeOld.REVIEWED_BY + " int ,"
                    + " FOREIGN KEY(" + VehicleTypeOld.PARENT +") REFERENCES " + VehicleTypeOld.TABLE_NAME + "(" + VehicleTypeOld.VEHICLE_TYPE_ID + ") ON DELETE CASCADE,"
                    + " FOREIGN KEY(" + VehicleTypeOld.SUBMITTED_BY +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL"
                    + ")";


        //    + " " + VehicleTypeOld.IS_APPROVED + " boolean not null default 'f' ,"



    // instance variables

    private int vehicleTypeID;
    private String name;
    private String imagePath;
    private String description;

    private int parent;
    private boolean backupExists;
    private Timestamp timestampCreated;
    private Timestamp timestampUpdated;
    private Timestamp timestampApplied;
    private Timestamp timestampApproved;
    private boolean isApproved;
    private Integer submittedBy;
    private String notesForReviewer;
    private String reviewerFeedback;
    private int reviewedBy;





    public int getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(int reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

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

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Timestamp getTimestampUpdated() {
        return timestampUpdated;
    }

    public void setTimestampUpdated(Timestamp timestampUpdated) {
        this.timestampUpdated = timestampUpdated;
    }

    public Timestamp getTimestampApplied() {
        return timestampApplied;
    }

    public void setTimestampApplied(Timestamp timestampApplied) {
        this.timestampApplied = timestampApplied;
    }

    public Timestamp getTimestampApproved() {
        return timestampApproved;
    }

    public void setTimestampApproved(Timestamp timestampApproved) {
        this.timestampApproved = timestampApproved;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Integer getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(Integer submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getNotesForReviewer() {
        return notesForReviewer;
    }

    public void setNotesForReviewer(String notesForReviewer) {
        this.notesForReviewer = notesForReviewer;
    }

    public String getReviewerFeedback() {
        return reviewerFeedback;
    }

    public void setReviewerFeedback(String reviewerFeedback) {
        this.reviewerFeedback = reviewerFeedback;
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
