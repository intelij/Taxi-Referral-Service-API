package org.taxireferral.api.ModelBilling;

import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 4/8/17.
 */
public class TransactionHistory {


    // constants
    public static final int TRANSACTION_TYPE_TAXI_REFERRAL_CHARGE = 1;
    public static final int TRANSACTION_TYPE_JOINING_CREDIT = 2;
    public static final int TRANSACTION_TYPE_USER_REFERRAL_CREDIT = 3;
    public static final int TRANSACTION_TYPE_PAYMENT_MADE = 4;

    public static final String TITLE_REFERRAL_CHARGE_FOR_TRIP = "Referral Charge";
    public static final String DESCRIPTION_REFERRAL_CHARGE_FOR_TRIP = "Referral Charges for Trip";


    // Table Name for User
    public static final String TABLE_NAME = "TRANSACTION_HISTORY";


    // Column names
    public static final String TRANSACTION_ID = "TRANSACTION_ID";
    public static final String USER_ID = "USER_ID";

    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";

    public static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";

    public static final String TRANSACTION_AMOUNT = "TRANSACTION_AMOUNT";
    public static final String IS_CREDIT = "IS_CREDIT"; // indicates whether transaction is credit or debit

    public static final String TIMESTAMP_OCCURRED = "TIMESTAMP_OCCURRED";

    public static final String CURRENT_DUES_BEFORE_TRANSACTION = "CURRENT_DUES_BEFORE_TRANSACTION";
    public static final String CURRENT_DUES_AFTER_TRANSACTION = "CURRENT_DUES_AFTER_TRANSACTION";






    // Create Table statement
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + TransactionHistory.TABLE_NAME + "("
                    + " " + TransactionHistory.TRANSACTION_ID + " SERIAL PRIMARY KEY,"
                    + " " + TransactionHistory.USER_ID + " int,"

                    + " " + TransactionHistory.TITLE + " text,"
                    + " " + TransactionHistory.DESCRIPTION + " text,"

                    + " " + TransactionHistory.TRANSACTION_TYPE + " int,"
                    + " " + TransactionHistory.TRANSACTION_AMOUNT + " float NOT NULL,"

                    + " " + TransactionHistory.IS_CREDIT + " boolean NOT NULL,"

                    + " " + TransactionHistory.TIMESTAMP_OCCURRED + " timestamp with time zone NOT NULL default now(),"

                    + " " + TransactionHistory.CURRENT_DUES_BEFORE_TRANSACTION + " float NOT NULL,"
                    + " " + TransactionHistory.CURRENT_DUES_AFTER_TRANSACTION + " float NOT NULL,"

                    + " FOREIGN KEY(" + TransactionHistory.USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE"
                    + ")";




    // Instance Variables
    private int transactionID;
    private int userID;

    private String title;
    private String description;

    private int transactionType;
    private double transactionAmount;
    private boolean isCredit;

    private Timestamp timestampOccurred;

    private double currentDuesBeforeTransaction;
    private double currentDuesAfterTransaction;





    // getter and setters
    public double getCurrentDuesBeforeTransaction() {
        return currentDuesBeforeTransaction;
    }

    public void setCurrentDuesBeforeTransaction(double currentDuesBeforeTransaction) {
        this.currentDuesBeforeTransaction = currentDuesBeforeTransaction;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public boolean isCredit() {
        return isCredit;
    }

    public void setCredit(boolean credit) {
        isCredit = credit;
    }

    public Timestamp getTimestampOccurred() {
        return timestampOccurred;
    }

    public void setTimestampOccurred(Timestamp timestampOccurred) {
        this.timestampOccurred = timestampOccurred;
    }

    public double getCurrentDuesAfterTransaction() {
        return currentDuesAfterTransaction;
    }

    public void setCurrentDuesAfterTransaction(double currentDuesAfterTransaction) {
        this.currentDuesAfterTransaction = currentDuesAfterTransaction;
    }
}
