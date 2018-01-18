package org.taxireferral.api.ModelImages;

import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 28/2/17.
 */
public class TaxiImage {



    // Table Name
    public static final String TABLE_NAME = "ITEM_IMAGES";

    // column names
    public static final String IMAGE_ID = "IMAGE_ID";
    public static final String DRIVER_ID = "DRIVER_ID";
    public static final String VEHICLE_ID = "VEHICLE_ID";
    public static final String IMAGE_FILENAME = "IMAGE_FILENAME";

    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
    public static final String TIMESTAMP_UPDATED = "TIMESTAMP_UPDATED";

    public static final String CAPTION_TITLE = "CAPTION_TITLE";
    public static final String CAPTION = "CAPTION";
    public static final String IMAGE_COPYRIGHTS = "IMAGE_COPYRIGHTS";
    public static final String IMAGE_ORDER = "IMAGE_ORDER";

    public static final String IS_APPROVED = "IS_APPROVED";
    public static final String APPROVED_BY = "APPROVED_BY";
    public static final String TIMESTAMP_APPROVED = "TIMESTAMP_APPROVED";
    public static final String REVIEWER_FEEDBACK = "REVIEWER_FEEDBACK";
    public static final String NOTES_FOR_REVIEWER = "NOTES_FOR_REVIEWER";

    public static final String SUBMITTED_BY = "SUBMITTED_BY";
    public static final String IS_DOCUMENT = "IS_DOCUMENT";
    public static final String IS_PRIVATE = "IS_PRIVATE";



    // create table statement
    public static final String createTablePostgres = "CREATE TABLE IF NOT EXISTS "

            + TaxiImage.TABLE_NAME + "("

            + " " + TaxiImage.IMAGE_ID + " SERIAL PRIMARY KEY,"
            + " " + TaxiImage.DRIVER_ID + " int,"
            + " " + TaxiImage.VEHICLE_ID + " int,"
            + " " + TaxiImage.IMAGE_FILENAME + " text,"

            + " " + TaxiImage.TIMESTAMP_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"
            + " " + TaxiImage.TIMESTAMP_UPDATED + " timestamp with time zone,"

            + " " + TaxiImage.CAPTION_TITLE + " text,"
            + " " + TaxiImage.CAPTION + " text,"
            + " " + TaxiImage.IMAGE_COPYRIGHTS + " text,"
            + " " + TaxiImage.IMAGE_ORDER + " int NOT NULL default 0,"

            + " " + TaxiImage.IS_APPROVED + " boolean NOT NULL default 'f',"
            + " " + TaxiImage.APPROVED_BY + " int,"
            + " " + TaxiImage.TIMESTAMP_APPROVED + " timestamp with time zone,"
            + " " + TaxiImage.REVIEWER_FEEDBACK + " text,"
            + " " + TaxiImage.NOTES_FOR_REVIEWER + " text,"

            + " " + TaxiImage.SUBMITTED_BY + " int,"
            + " " + TaxiImage.IS_DOCUMENT + " boolean NOT NULL default 'f',"
            + " " + TaxiImage.IS_PRIVATE + " boolean NOT NULL default 'f',"

            + " FOREIGN KEY(" + TaxiImage.DRIVER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL ,"
            + " FOREIGN KEY(" + TaxiImage.VEHICLE_ID +") REFERENCES " + Vehicle.TABLE_NAME + "(" + Vehicle.VEHICLE_ID + ") ON DELETE SET NULL ,"
            + " FOREIGN KEY(" + TaxiImage.SUBMITTED_BY +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL ,"
            + " FOREIGN KEY(" + TaxiImage.APPROVED_BY +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL "
            + ")";









    // instance variables

    private int imageID;
    private int driverID;
    private int vehicleID;
    private String imageFilename;

    private Timestamp timestampCreated;
    private Timestamp timestampUpdated;

    private String captionTitle;
    private String caption;
    private String imageCopyrights;
    private int imageOrder;

    private Boolean isApproved;
    private int approvedBy;
    private Timestamp timestampApproved;
    private String reviewerFeedback;
    private String notesForReviewer;

    private int submittedBy;
    private boolean isDocument;
    private boolean isPrivate;


    // getter and setters


    public int getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(int submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public int getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(int approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Timestamp getTimestampApproved() {
        return timestampApproved;
    }

    public void setTimestampApproved(Timestamp timestampApproved) {
        this.timestampApproved = timestampApproved;
    }

    public String getReviewerFeedback() {
        return reviewerFeedback;
    }

    public void setReviewerFeedback(String reviewerFeedback) {
        this.reviewerFeedback = reviewerFeedback;
    }

    public String getNotesForReviewer() {
        return notesForReviewer;
    }

    public void setNotesForReviewer(String notesForReviewer) {
        this.notesForReviewer = notesForReviewer;
    }

    public boolean isDocument() {
        return isDocument;
    }

    public void setDocument(boolean document) {
        isDocument = document;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Timestamp getTimestampUpdated() {

        return timestampUpdated;
    }

    public void setTimestampUpdated(Timestamp timestampUpdated) {
        this.timestampUpdated = timestampUpdated;
    }

    public int getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(int imageOrder) {
        this.imageOrder = imageOrder;
    }

    public String getImageCopyrights() {
        return imageCopyrights;
    }

    public void setImageCopyrights(String imageCopyrights) {
        this.imageCopyrights = imageCopyrights;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public String getCaptionTitle() {
        return captionTitle;
    }

    public void setCaptionTitle(String captionTitle) {
        this.captionTitle = captionTitle;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
