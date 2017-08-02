package org.taxireferral.api.ModelEmergency.Comments;

/**
 * Created by sumeet on 2/8/17.
 */
public class EmergencyComments {

    // Table Name for User
    public static final String TABLE_NAME = "EMERGENCY_COMMENTS";

    // Column names
    public static final String COMMENT_ID = "COMMENT_ID";
    public static final String EMERGENCY_ID = "EMERGENCY_ID"; // foreign key emergency
    public static final String CREATED_BY = "CREATED_BY"; // foreign key user

    public static final String COMMENT_TEXT = "COMMENT_TEXT";


}
