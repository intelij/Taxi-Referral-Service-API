package org.taxireferral.api.ModelBilling;

import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 4/10/17.
 */
public class UPIPaymentRequest {


    // constants
    public static final int TAX_ACCOUNT = 1;
    public static final int SERVICE_ACCOUNT = 2;

    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAILED = 2;




    // Table Name
    public static final String TABLE_NAME = "UPI_PAYMENT_REQUEST";


    // Column names
    public static final String UPI_PAYMENT_ID = "UPI_PAYMENT_ID";
    public static final String USER_ID = "USER_ID";

    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String STATUS = "STATUS";
    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";

    public static final String AMOUNT = "AMOUNT";
    public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";

    public static final String IS_APPROVED = "IS_APPROVED";
    public static final String REMARKS_AND_FEEDBACK = "REMARKS_AND_FEEDBACK";
    public static final String TIMESTAMP_APPROVED = "TIMESTAMP_APPROVED";
    public static final String APPROVED_BY_STAFF_ID = "APPROVED_BY_STAFF_ID";

    public static final String IS_CREDITED = "IS_CREDITED";




    // Create Table statement
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + UPIPaymentRequest.TABLE_NAME + "("

                    + " " + UPIPaymentRequest.UPI_PAYMENT_ID + " SERIAL PRIMARY KEY,"
                    + " " + UPIPaymentRequest.USER_ID + " int NOT NULL,"

                    + " " + UPIPaymentRequest.DESCRIPTION + " text,"
                    + " " + UPIPaymentRequest.STATUS + " int,"
                    + " " + UPIPaymentRequest.TIMESTAMP_CREATED + " timestamp with time zone NOT NULL default now(),"

                    + " " + UPIPaymentRequest.AMOUNT + " float NOT NULL,"
                    + " " + UPIPaymentRequest.ACCOUNT_TYPE + " int NOT NULL,"

                    + " " + UPIPaymentRequest.IS_APPROVED + " boolean,"
                    + " " + UPIPaymentRequest.REMARKS_AND_FEEDBACK + " text,"
                    + " " + UPIPaymentRequest.TIMESTAMP_APPROVED + " timestamp with time zone,"
                    + " " + UPIPaymentRequest.APPROVED_BY_STAFF_ID + " int,"

                    + " " + UPIPaymentRequest.IS_CREDITED + " boolean NOT NULL default 'f',"

                    + " FOREIGN KEY(" + UPIPaymentRequest.USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL"
                    + ")";

    //+ " FOREIGN KEY(" + UPIPaymentRequest.APPROVED_BY_STAFF_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL"



    // instance variables

    private int upiPaymentID;
    private int userID;

    private String description;
    private int status;
    private Timestamp timestampCreated;

    private double amount;
    private int accountType;

    private boolean isApproved;
    private String remarksAndFeedback;
    private Timestamp timestampApproved;
    private int approvedBy;

    private boolean isCredited;







    // getter and setters


    public boolean isCredited() {
        return isCredited;
    }

    public void setCredited(boolean credited) {
        isCredited = credited;
    }

    public Timestamp getTimestampApproved() {
        return timestampApproved;
    }

    public void setTimestampApproved(Timestamp timestampApproved) {
        this.timestampApproved = timestampApproved;
    }

    public int getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(int approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUpiPaymentID() {
        return upiPaymentID;
    }

    public void setUpiPaymentID(int upiPaymentID) {
        this.upiPaymentID = upiPaymentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getRemarksAndFeedback() {
        return remarksAndFeedback;
    }

    public void setRemarksAndFeedback(String remarksAndFeedback) {
        this.remarksAndFeedback = remarksAndFeedback;
    }
}
