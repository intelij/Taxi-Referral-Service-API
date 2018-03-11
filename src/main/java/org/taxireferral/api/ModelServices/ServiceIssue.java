package org.taxireferral.api.ModelServices;

public class ServiceIssue {


    // constants
    public static final int STATUS_CREATED = 1;
    public static final int STATUS_RESOLVED = 2;


    // Table Name
    public static final String TABLE_NAME = "SERVICE_ISSUE";


    // Column names
    public static final String SERVICE_ISSUE_ID = "SERVICE_ISSUE_ID"; // primary key
    public static final String SERVICE_REQUEST = "SERVICE_REQUEST"; // foreign key

    public static final String TITLE = "TITLE";
    public static final String ISSUE_DESCRIPTION = "ISSUE_DESCRIPTION";
    public static final String STATUS = "STATUS";
    public static final String CREATED_BY = "CREATED_BY";
    public static final String CREATED_BY_USER_ROLE = "CREATED_BY_USER_ROLE";
    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
    public static final String TIMESTAMP_RESOLVED = "TIMESTAMP_RESOLVED";


}
