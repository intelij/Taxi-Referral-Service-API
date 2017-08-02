package org.taxireferral.api.ModelRoles;

import org.taxireferral.api.Model.Vehicle;

import java.sql.Timestamp;

/**
 * Created by sumeet on 29/5/16.
 */
public class User {



    // constants
    public static final int REGISTRATION_MODE_EMAIL = 1;
    public static final int REGISTRATION_MODE_PHONE = 2;


    // Table Name for User
    public static final String TABLE_NAME = "USER_TABLE";

    // Column names
    public static final String USER_ID = "USER_ID";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    public static final String E_MAIL = "E_MAIL";
//    public static final String IS_EMAIL_VERIFIED = "IS_EMAIL_VERIFIED";
    public static final String PHONE = "PHONE";
//    public static final String IS_PHONE_VERIFIED = "IS_PHONE_VERIFIED";
    public static final String NAME = "NAME";

    public static final String GENDER = "GENDER";
    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
    public static final String ROLE = "ROLE";

    public static final String IS_ACCOUNT_PRIVATE = "IS_ACCOUNT_PRIVATE";
    public static final String ABOUT = "ABOUT";
    public static final String ENABLED = "ENABLED";
    public static final String GOOGLE_ID = "GOOGLE_ID";

    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
    public static final String TIMESTAMP_UPDATED = "TIMESTAMP_UPDATED";

    public static final String TOKEN = "TOKEN";
    public static final String TIMESTAMP_TOKEN_EXPIRES = "TIMESTAMP_TOKEN_EXPIRES";


    // current_due = total_billed - total_credits - total_paid
    public static final String CURRENT_DUES = "CURRENT_DUES";
    public static final String TOTAL_BILLED = "TOTAL_BILLED";
    public static final String TOTAL_CREDITS = "TOTAL_CREDITS";
    public static final String TOTAL_PAID = "TOTAL_PAID";

    public static final String REFERRED_BY = "REFERRED_BY";
    public static final String IS_REFERRER_CREDITED = "IS_REFERRER_CREDITED";

    // verified accounts are an indication that identity of the user is verified by a staff member
    public static final String IS_VERIFIED = "IS_VERIFIED";



    // Create Table CurrentServiceConfiguration Provider
    public static final String createTableUsernamesPostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + User.TABLE_NAME + "("
                    + " " + User.USER_ID + " SERIAL PRIMARY KEY,"
                    + " " + User.USERNAME + " text UNIQUE ,"
                    + " " + User.PASSWORD + " text NOT NULL,"

                    + " " + User.E_MAIL + " text UNIQUE ,"
                    + " " + User.PHONE + " text UNIQUE,"
                    + " " + User.NAME + " text ,"

                    + " " + User.GENDER + " boolean,"
                    + " " + User.PROFILE_IMAGE_URL + " text ,"
                    + " " + User.ROLE + " int ,"

                    + " " + User.IS_ACCOUNT_PRIVATE + " boolean,"
                    + " " + User.ABOUT + " text,"
                    + " " + User.ENABLED + " boolean NOT NULL default 'f',"
                    + " " + User.GOOGLE_ID + " text,"

                    + " " + User.TIMESTAMP_CREATED + "  timestamp with time zone NOT NULL DEFAULT now(),"
                    + " " + User.TIMESTAMP_UPDATED + "  timestamp with time zone NOT NULL DEFAULT now(),"

                    + " " + User.TOKEN + "  text,"
                    + " " + User.TIMESTAMP_TOKEN_EXPIRES + "  timestamp with time zone,"
                    + "CHECK (" + User.USERNAME + " IS NOT NULL OR " + User.E_MAIL + " IS NOT NULL OR " + User.PHONE + " IS NOT NULL " +  ")"
                    + ")";






//    public static final String upgradeTableSchema =
//            "ALTER TABLE IF EXISTS " + User.TABLE_NAME
//                    + " ADD COLUMN IF NOT EXISTS " + User.ENABLED + " boolean NOT NULL default 'f',"
//                    + " ADD COLUMN IF NOT EXISTS " + User.GOOGLE_ID + " text,"
//                    + " ADD COLUMN IF NOT EXISTS " + User.EMAIL_VERIFICATION_CODE + " text,"
//                    + " ADD COLUMN IF NOT EXISTS " + User.EMAIL_VERIFICATION_CODE_TIMESTAMP_EXPIRES + " timestamp with time zone";




    // Instance Variables
    private int userID;
    private String username;
    private String password;

    private String email;
    private String phone;
    private String name;

    private Boolean gender;
    private String profileImagePath;
    private int role;

    private boolean isAccountPrivate;
    private String about;
    private boolean enabled;
    private String googleID;

    private Timestamp timestampCreated;
    private Timestamp timestampUpdated;

    private String token;
    private Timestamp timestampTokenExpires;


    private String rt_email_verification_code;
    private String rt_phone_verification_code;
    private int rt_registration_mode; // 1 for registration by email 2 for registration by phone
    private Vehicle rt_vehicle;





    // Getters and Setters


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Vehicle getRt_vehicle() {
        return rt_vehicle;
    }

    public void setRt_vehicle(Vehicle rt_vehicle) {
        this.rt_vehicle = rt_vehicle;
    }

    public int getRt_registration_mode() {
        return rt_registration_mode;
    }

    public void setRt_registration_mode(int rt_registration_mode) {
        this.rt_registration_mode = rt_registration_mode;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public String getRt_email_verification_code() {
        return rt_email_verification_code;
    }

    public void setRt_email_verification_code(String rt_email_verification_code) {
        this.rt_email_verification_code = rt_email_verification_code;
    }

    public String getRt_phone_verification_code() {
        return rt_phone_verification_code;
    }

    public void setRt_phone_verification_code(String rt_phone_verification_code) {
        this.rt_phone_verification_code = rt_phone_verification_code;
    }


    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public boolean isAccountPrivate() {
        return isAccountPrivate;
    }

    public void setAccountPrivate(boolean accountPrivate) {
        isAccountPrivate = accountPrivate;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Timestamp getTimestampTokenExpires() {
        return timestampTokenExpires;
    }

    public void setTimestampTokenExpires(Timestamp timestampTokenExpires) {
        this.timestampTokenExpires = timestampTokenExpires;
    }


}
