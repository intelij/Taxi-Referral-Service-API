package org.taxireferral.api.ModelEmergency;

/**
 * Created by sumeet on 2/8/17.
 */
public class EmergencyReport {

    // type of emergency
    public static final int MEDICAL_EMERGENCY = 1;
    public static final int SEXUAL_ASSAULT = 2;

    // sub_types of emergency
    public static final int MEDICAL_EMERGENCY_ACCIDENT = 1;
    public static final int MEDICAL_EMERGENCY_HEART_ATTACK = 1;
    public static final int MEDICAL_EMERGENCY_PREGNANCY = 1;
    public static final int MEDICAL_EMERGENCY_OTHERS = 1;


    // Table Name for User
    public static final String TABLE_NAME = "USER_TABLE";

    // Column names
    public static final String REPORT_ID = "REPORT_ID";
    public static final String DRIVER_ID = "DRIVER_ID";
    public static final String END_USER_ID = "END_USER_ID";

    public static final String REPORTED_BY = "REPORTED_BY";

    public static final String TYPE_OF_EMERGENCY = "TYPE_OF_EMERGENCY";
    public static final String SUB_TYPE_OF_EMERGENCY = "SUB_TYPE_OF_EMERGENCY";

    public static final String STATUS = "STATUS";

}
