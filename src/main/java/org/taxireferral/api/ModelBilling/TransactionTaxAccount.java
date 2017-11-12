package org.taxireferral.api.ModelBilling;

import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 4/8/17.
 */
public class TransactionTaxAccount {



    // Table Name for User
    public static final String TABLE_NAME = "TRANSACTION_HISTORY_TAX_ACCOUNT";


    // Column names
    public static final String TRANSACTION_ID = "TRANSACTION_ID";
    public static final String USER_ID = "USER_ID";

    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";

    public static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";

    public static final String TAX_AMOUNT = "TAX_AMOUNT";
    public static final String IS_CREDIT = "IS_CREDIT"; // indicates whether transaction is credit or debit
    public static final String TIMESTAMP_OCCURRED = "TIMESTAMP_OCCURRED";
    public static final String TAX_BALANCE_AFTER_TRANSACTION = "TAX_BALANCE_AFTER_TRANSACTION";




    // Create Table statement
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + TransactionTaxAccount.TABLE_NAME + "("
                    + " " + TransactionTaxAccount.TRANSACTION_ID + " SERIAL PRIMARY KEY,"
                    + " " + TransactionTaxAccount.USER_ID + " int,"

                    + " " + TransactionTaxAccount.TITLE + " text,"
                    + " " + TransactionTaxAccount.DESCRIPTION + " text,"

                    + " " + TransactionTaxAccount.TRANSACTION_TYPE + " int,"
                    + " " + TransactionTaxAccount.TAX_AMOUNT + " float NOT NULL default 0,"

                    + " " + TransactionTaxAccount.IS_CREDIT + " boolean NOT NULL,"
                    + " " + TransactionTaxAccount.TIMESTAMP_OCCURRED + " timestamp with time zone NOT NULL default now(),"
                    + " " + TransactionTaxAccount.TAX_BALANCE_AFTER_TRANSACTION + " float NOT NULL,"

                    + " FOREIGN KEY(" + TransactionTaxAccount.USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE"
                    + ")";





    // Instance Variables
    private int transactionID;
    private int userID;

    private String title;
    private String description;

    private int transactionType;
    private double taxAmount;

    private boolean isCredit;

    private Timestamp timestampOccurred;

    private double taxBalanceAfterTransaction;




    // getter and setters


    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getTaxBalanceAfterTransaction() {
        return taxBalanceAfterTransaction;
    }

    public void setTaxBalanceAfterTransaction(double taxBalanceAfterTransaction) {
        this.taxBalanceAfterTransaction = taxBalanceAfterTransaction;
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

}
