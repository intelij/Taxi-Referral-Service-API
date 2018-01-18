package org.taxireferral.api.ModelIssues;

public class TripIssueOption {


    // Table Name
    public static final String TABLE_NAME = "ITEM_IMAGES";

    // column names
    public static final String TRIP_OPTION_ID = "TRIP_OPTION_ID";
    public static final String OPTION_TEXT = "OPTION_TEXT";
    public static final String OPTION_ORDER = "OPTION_ORDER";



    // create table statement
    public static final String createTablePostgres = "CREATE TABLE IF NOT EXISTS "
            + TripIssueOption.TABLE_NAME + "("

            + " " + TripIssueOption.TRIP_OPTION_ID + " SERIAL PRIMARY KEY,"
            + " " + TripIssueOption.OPTION_TEXT + " text,"
            + " " + TripIssueOption.OPTION_ORDER + " int"

            + ")";



    // instance variables

    private int tripOptionID;
    private String optionText;
    private int optionOrder;


    // getter and setters


    public int getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(int optionOrder) {
        this.optionOrder = optionOrder;
    }

    public int getTripOptionID() {
        return tripOptionID;
    }

    public void setTripOptionID(int tripOptionID) {
        this.tripOptionID = tripOptionID;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
}
