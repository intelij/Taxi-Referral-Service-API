package org.taxireferral.api.ModelServices;

import org.taxireferral.api.ModelRoles.User;

import java.sql.Time;
import java.sql.Timestamp;


public class ServiceRequest {

    // allotToSelf
    // GiveUpAllotment
    // getRequestsForStaff(Null and Filtered for StaffMember)

    // allotToStaff
    // SetRequestFulfilled
    // CancelRequest
    // getRequestsForUser()


    // constants
    public static final int STATUS_CREATED = 1;
    public static final int STATUS_ALOTTED = 2;
    public static final int STATUS_FULFILLED = 3;
    public static final int STATUS_CANCELLED = 4;


    // Table Name
    public static final String TABLE_NAME = "SERVICE_REQUEST";


    // Column names
    public static final String SERVICE_REQUEST_ID = "SERVICE_REQUEST_ID"; // primary key
    public static final String TOTAL_CHARGE = "TOTAL_CHARGE";
    public static final String TAX_RATE = "TAX_RATE";
    public static final String STAFF_PAY = "STAFF_PAY";
    public static final String STATUS = "STATUS";
    public static final String IS_FULFILLED = "IS_FULFILLED";
    public static final String FULLFILLED_BY_STAFF_ID = "FULLFILLED_BY_STAFF_ID";
    public static final String SUBMITTED_BY = "SUBMITTED_BY";

    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
    public static final String TIMESTAMP_ALOTTED = "TIMESTAMP_ALOTTED";
    public static final String TIMESTAMP_FULFILLED = "TIMESTAMP_FULFILLED";
    public static final String TIMESTAMP_CANCELLED = "TIMESTAMP_CANCELLED";

    public static final String RATING_BY_CUSTOMER = "RATING_BY_CUSTOMER";
    public static final String FEEDBACK_BY_CUSTOMER = "FEEDBACK_BY_CUSTOMER";
    public static final String RATING_BY_STAFF = "RATING_BY_STAFF";
    public static final String FEEDBACK_BY_STAFF = "FEEDBACK_BY_STAFF";

    public static final String REASON_FOR_CANCELLATION = "REASON_FOR_CANCELLATION";



    // Create Table Statement
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + ServiceRequest.TABLE_NAME + "("

                    + " " + ServiceRequest.SERVICE_REQUEST_ID + " SERIAL PRIMARY KEY," // primary key
                    + " " + ServiceRequest.TOTAL_CHARGE + " float NOT NULL default 0,"
                    + " " + ServiceRequest.TAX_RATE + " float NOT NULL default 0,"
                    + " " + ServiceRequest.STAFF_PAY + " float NOT NULL default 0,"
                    + " " + ServiceRequest.STATUS + " int NOT NULL default 1,"
//                    + " " + ServiceRequest.IS_FULFILLED + " boolean NOT NULL default 'f',"
                    + " " + ServiceRequest.FULLFILLED_BY_STAFF_ID + " int," // foreign key
                    + " " + ServiceRequest.SUBMITTED_BY + " int NOT NULL," // foreign key

                    + " " + ServiceRequest.TIMESTAMP_CREATED + "  timestamp with time zone NOT NULL DEFAULT now(),"
                    + " " + ServiceRequest.TIMESTAMP_ALOTTED + "  timestamp with time zone,"
                    + " " + ServiceRequest.TIMESTAMP_FULFILLED + "  timestamp with time zone,"
                    + " " + ServiceRequest.TIMESTAMP_CANCELLED + "  timestamp with time zone,"

                    + " " + ServiceRequest.RATING_BY_CUSTOMER + " int,"
                    + " " + ServiceRequest.FEEDBACK_BY_CUSTOMER + " text,"
                    + " " + ServiceRequest.RATING_BY_STAFF + " int,"
                    + " " + ServiceRequest.FEEDBACK_BY_STAFF + " text,"
                    + " " + ServiceRequest.REASON_FOR_CANCELLATION + " text,"

                    + " FOREIGN KEY(" + ServiceRequest.FULLFILLED_BY_STAFF_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL,"
                    + " FOREIGN KEY(" + ServiceRequest.SUBMITTED_BY +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL"
                    + ")";



    // instance variables

    private int serviceRequestID;
    private double totalCharge;
    private double taxRate;
    private double staffPay;
    private int status;
    private int fulfilledBy;
    private int submittedBy;

    private Timestamp timestampCreated;
    private Timestamp timestampAlotted;
    private Timestamp timestampFulfilled;
    private Timestamp timestampCancelled;

    private int ratingByCustomer;
    private String feedbackByCustomer;
    private int ratingByStaff;
    private String feedbackByStaff;
    private String reasonForCancellation;





    // getter and setter

    public int getServiceRequestID() {
        return serviceRequestID;
    }

    public void setServiceRequestID(int serviceRequestID) {
        this.serviceRequestID = serviceRequestID;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getStaffPay() {
        return staffPay;
    }

    public void setStaffPay(double staffPay) {
        this.staffPay = staffPay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFulfilledBy() {
        return fulfilledBy;
    }

    public void setFulfilledBy(int fulfilledBy) {
        this.fulfilledBy = fulfilledBy;
    }

    public int getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(int submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Timestamp getTimestampAlotted() {
        return timestampAlotted;
    }

    public void setTimestampAlotted(Timestamp timestampAlotted) {
        this.timestampAlotted = timestampAlotted;
    }

    public Timestamp getTimestampFulfilled() {
        return timestampFulfilled;
    }

    public void setTimestampFulfilled(Timestamp timestampFulfilled) {
        this.timestampFulfilled = timestampFulfilled;
    }

    public Timestamp getTimestampCancelled() {
        return timestampCancelled;
    }

    public void setTimestampCancelled(Timestamp timestampCancelled) {
        this.timestampCancelled = timestampCancelled;
    }

    public int getRatingByCustomer() {
        return ratingByCustomer;
    }

    public void setRatingByCustomer(int ratingByCustomer) {
        this.ratingByCustomer = ratingByCustomer;
    }

    public String getFeedbackByCustomer() {
        return feedbackByCustomer;
    }

    public void setFeedbackByCustomer(String feedbackByCustomer) {
        this.feedbackByCustomer = feedbackByCustomer;
    }

    public int getRatingByStaff() {
        return ratingByStaff;
    }

    public void setRatingByStaff(int ratingByStaff) {
        this.ratingByStaff = ratingByStaff;
    }

    public String getFeedbackByStaff() {
        return feedbackByStaff;
    }

    public void setFeedbackByStaff(String feedbackByStaff) {
        this.feedbackByStaff = feedbackByStaff;
    }

    public String getReasonForCancellation() {
        return reasonForCancellation;
    }

    public void setReasonForCancellation(String reasonForCancellation) {
        this.reasonForCancellation = reasonForCancellation;
    }
}
