package org.taxireferral.api.ModelIssues;

import org.taxireferral.api.Model.TripHistory;
import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 28/2/17.
 */
public class TripIssues {



    // Table Name
    public static final String TABLE_NAME = "ITEM_IMAGES";

    // column names
    public static final String ISSUE_ID = "ISSUE_ID";

    public static final String TRIP_ID = "TRIP_ID";

    public static final String OPTION_ID = "OPTION_ID";

    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";

    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
    public static final String TIMESTAMP_UPDATED = "TIMESTAMP_UPDATED";

    public static final String SUBMITTED_BY = "SUBMITTED_BY";
    public static final String ROLE_OF_USER_SUBMITTED = "ROLE_OF_USER_SUBMITTED";

    public static final String IS_RESOLVED = "IS_RESOLVED";
    public static final String TIMESTAMP_RESOLVED = "TIMESTAMP_UPDATED";






    // create table statement
    public static final String createTablePostgres = "CREATE TABLE IF NOT EXISTS "

            + TripIssues.TABLE_NAME + "("


            + " " + TripIssues.ISSUE_ID + " SERIAL PRIMARY KEY,"

            + " " + TripIssues.TRIP_ID + " int NOT NULL,"
            + " " + TripIssues.OPTION_ID + " int ,"

            + " " + TripIssues.TITLE + " text,"
            + " " + TripIssues.DESCRIPTION + " text,"

            + " " + TripIssues.TIMESTAMP_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"
            + " " + TripIssues.TIMESTAMP_UPDATED + " timestamp with time zone,"

            + " " + TripIssues.SUBMITTED_BY + " int,"
            + " " + TripIssues.ROLE_OF_USER_SUBMITTED + " int,"

            + " " + TripIssues.IS_RESOLVED + " boolean NOT NULL default 'f',"
            + " " + TripIssues.TIMESTAMP_RESOLVED + " timestamp with time zone,"

            + " FOREIGN KEY(" + TripIssues.TRIP_ID +") REFERENCES " + TripHistory.TABLE_NAME + "(" + TripHistory.TRIP_HISTORY_ID + ") ON DELETE CASCADE ,"
            + " FOREIGN KEY(" + TripIssues.OPTION_ID +") REFERENCES " + TripIssueOption.TABLE_NAME + "(" + TripIssueOption.TRIP_OPTION_ID + ") ON DELETE SET NULL ,"
            + " FOREIGN KEY(" + TripIssues.SUBMITTED_BY +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL "
            + ")";




    // instance variables


    private int issueID;
    private int optionID;

    private String title;
    private String description;

    private Timestamp timestampCreated;
    private Timestamp timestampUpdated;

    private int submittedBy;
    private int roleOfUserSubmitted;

    private boolean isResolved;
    private Timestamp timestampResolved;





    // getter and setters


    public int getOptionID() {
        return optionID;
    }

    public void setOptionID(int optionID) {
        this.optionID = optionID;
    }

    public int getIssueID() {
        return issueID;
    }

    public void setIssueID(int issueID) {
        this.issueID = issueID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Timestamp getTimestampUpdated() {
        return timestampUpdated;
    }

    public void setTimestampUpdated(Timestamp timestampUpdated) {
        this.timestampUpdated = timestampUpdated;
    }

    public int getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(int submittedBy) {
        this.submittedBy = submittedBy;
    }

    public int getRoleOfUserSubmitted() {
        return roleOfUserSubmitted;
    }

    public void setRoleOfUserSubmitted(int roleOfUserSubmitted) {
        this.roleOfUserSubmitted = roleOfUserSubmitted;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

    public Timestamp getTimestampResolved() {
        return timestampResolved;
    }

    public void setTimestampResolved(Timestamp timestampResolved) {
        this.timestampResolved = timestampResolved;
    }
}
