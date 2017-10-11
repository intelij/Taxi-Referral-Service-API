package org.taxireferral.api.Model;

import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 22/3/17.
 */
public class VehicleTypeVersion {

    // Table Name
    public static final String TABLE_NAME = "VEHICLE_TYPE_VERSION";

    // Column names
    public static final String VERSION_ID = "VERSION_ID"; // primary key
    public static final String NAME = "NAME";
    public static final String IMAGE_PATH = "IMAGE_PATH";
    public static final String DESCRIPTION = "DESCRIPTION";

    // extra fields added due to submission feature
    public static final String PARENT = "PARENT";
    public static final String SUBMITTED_BY = "SUBMITTED_BY";
    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
    public static final String TIMESTAMP_APPLIED = "TIMESTAMP_APPLIED";
    public static final String TIMESTAMP_APPROVED = "TIMESTAMP_APPROVED";

    public static final String IS_APPROVED = "IS_APPROVED";
    public static final String NOTES_FOR_REVIEWER = "NOTES_FOR_REVIEWER";
    public static final String REVIEWER_FEEDBACK = "REVIEWER_FEEDBACK";
    public static final String REVIEWED_BY = "REVIEWED_BY";


    //    public static final String TIMESTAMP_UPDATED = "TIMESTAMP_UPDATED";


    // Create Table CurrentServiceConfiguration Provider
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + VehicleTypeVersion.TABLE_NAME + "("
                    + " " + VehicleTypeVersion.VERSION_ID + " SERIAL PRIMARY KEY,"
                    + " " + VehicleTypeVersion.NAME + " text ,"
                    + " " + VehicleTypeVersion.IMAGE_PATH + " text ,"
                    + " " + VehicleTypeVersion.DESCRIPTION + " text ,"

                    // meta data for versioning and submission review
                    + " " + VehicleTypeVersion.PARENT + " int ,"
                    + " " + VehicleTypeVersion.SUBMITTED_BY + " int ,"
                    + " " + VehicleTypeVersion.TIMESTAMP_CREATED + " timestamp with time zone not null default now() ,"
                    + " " + VehicleTypeVersion.TIMESTAMP_APPLIED + " timestamp with time zone ,"
                    + " " + VehicleTypeVersion.TIMESTAMP_APPROVED + " timestamp with time zone ,"

                    + " " + VehicleTypeVersion.IS_APPROVED + " boolean ,"
                    + " " + VehicleTypeVersion.NOTES_FOR_REVIEWER + " text ,"
                    + " " + VehicleTypeVersion.REVIEWER_FEEDBACK + " text ,"
                    + " " + VehicleTypeVersion.REVIEWED_BY + " int ,"

                    + " FOREIGN KEY(" + VehicleTypeVersion.PARENT +") REFERENCES " + VehicleType.TABLE_NAME + "(" + VehicleType.VEHICLE_TYPE_ID + ") ON DELETE CASCADE,"
                    + " FOREIGN KEY(" + VehicleTypeVersion.SUBMITTED_BY +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL"
                    + ")";




    // instance variables

    private int versionID;
    private String name;
    private String imagePath;
    private String description;

    private Integer parent;  // parent can be a null value therefore a non-primitive is used
    private Timestamp timestampCreated;
    private Timestamp timestampApplied;
    private Timestamp timestampApproved;

    // isApproved can be a null value which indicates status under-review therefore a non-primitive is used
    private Boolean isApproved;

    private Integer submittedBy;
    private String notesForReviewer;
    private String reviewerFeedback;
    private Integer reviewedBy;


    // meta data for temporary server to client communication
    // note that fields starting with rt_ prefix are not stored in the database
    public static final int RESPONSE_CREATED = 1;
    public static final int RESPONSE_UPDATED = 2;
    public static final int RESPONSE_SUBMISSION_FOR_CREATE_ACCEPTED = 3;
    public static final int RESPONSE_SUBMISSION_FOR_UPDATE_ACCEPTED = 4;
    public static final int RESTORE_SUCCESSFUL = 5;
    public static final int APPROVE_SUCCESSFUL = 6;
    public static final int REJECT_SUCCESSFUL = 7;

    private int rt_response_code;
    private String rt_message_from_server;




    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(Integer submittedBy) {
        this.submittedBy = submittedBy;
    }

    public void setReviewedBy(Integer reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getRt_message_from_server() {
        return rt_message_from_server;
    }

    public void setRt_message_from_server(String rt_message_from_server) {
        this.rt_message_from_server = rt_message_from_server;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public int getRt_response_code() {
        return rt_response_code;
    }

    public void setRt_response_code(int rt_response_code) {
        this.rt_response_code = rt_response_code;
    }



    public int getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(int reviewedBy) {
        this.reviewedBy = reviewedBy;
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

    public int getVersionID() {
        return versionID;
    }

    public void setVersionID(int versionID) {
        this.versionID = versionID;
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
