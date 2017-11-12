package org.taxireferral.api.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jdk.nashorn.internal.objects.Global;
import net.sargue.mailgun.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.taxireferral.api.DAORoles.*;
import org.taxireferral.api.DAORoles.deprecated.DAOUser;
import org.taxireferral.api.DAOSettings.DAOServiceConfig;
import org.taxireferral.api.DAOs.*;
import org.taxireferral.api.JDBCContract;
import org.taxireferral.api.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;

/**
 * Created by sumeet on 22/3/17.
 */
public class Globals {


    // secure randon for generating tokens
    public static SecureRandom random = new SecureRandom();


//    public static DAOUser daoUser = new DAOUser();
    public static DAOUserNew daoUserNew = new DAOUserNew();
    public static DAOResetPassword daoResetPassword = new DAOResetPassword();
    public static DAOUserSignUp daoUserSignUp = new DAOUserSignUp();
    public static DAOStaff daoStaff = new DAOStaff();
    public static DAOServiceConfig daoServiceConfig = new DAOServiceConfig();

    public static VehicleDAO vehicleDAO = new VehicleDAO();
    public static DAOTripRequest tripRequestDAO = new DAOTripRequest();
    public static DAOCurrentTrip daoCurrentTrip = new DAOCurrentTrip();
    public static DAOTripHistory daoTripHistory = new DAOTripHistory();

    public static DAOTransaction daoTransaction = new DAOTransaction();


    public static DAOEmailVerificationCodes daoEmailVerificationCodes = new DAOEmailVerificationCodes();
    public static DAOPhoneVerificationCodes daoPhoneVerificationCodes = new DAOPhoneVerificationCodes();

    public static VehicleTypeDAOVersions daoVehicleTypeNew = new VehicleTypeDAOVersions();
    public static VehicleTypeDAOGet daoVehicleTypeGet = new VehicleTypeDAOGet();

    public static DAOUserNotifications userNotifications = new DAOUserNotifications();

    // static reference for holding security accountApproved

    public static Object accountApproved;


    // mailgun configuration

    private static Configuration configurationMailgun;



    public static Configuration getMailgunConfiguration()
    {

        if(configurationMailgun==null)
        {

            org.apache.commons.configuration2.Configuration configuration = Globals.getConfiguration();


            if(configuration==null)
            {
                System.out.println("Configuration is null : getMailgunConfiguration() !");

                return null ;
            }


            String domain = configuration.getString(ConfigurationKeys.MAILGUN_DOMAIN);
            String api_key = configuration.getString(ConfigurationKeys.MAILGUN_API_KEY);
            String email = configuration.getString(ConfigurationKeys.MAILGUN_EMAIL);
            String name = configuration.getString(ConfigurationKeys.MAILGUN_NAME);

            configurationMailgun = new Configuration()
                    .domain(domain)
                    .apiKey(api_key)
                    .from(name,email);



//            configurationMailgun = new Configuration()
//                    .domain("community.nearbyshops.org")
//                    .apiKey("key-b73ddc1406a0f651e579cb21d388864d")
//                    .from("Sumeet", "postmaster@vedic-astrology-forum.com");



            return configurationMailgun;
        }
        else
        {
            return configurationMailgun;
        }
    }




    // Configure Connection Pooling


    private static HikariDataSource dataSource;

    public static HikariDataSource getDataSource() {


        if (dataSource == null) {


            org.apache.commons.configuration2.Configuration configuration = Globals.getConfiguration();


            if(configuration==null)
            {
                System.out.println("Configuration is null : getDataSource() HikariCP !");

                return null ;
            }



            String connection_url = configuration.getString(ConfigurationKeys.CONNECTION_URL);
            String username = configuration.getString(ConfigurationKeys.POSTGRES_USERNAME);
            String password = configuration.getString(ConfigurationKeys.POSTGRES_PASSWORD);



            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(connection_url);
            config.setUsername(username);
            config.setPassword(password);

            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }







    public static void loadGlobalConfiguration()
    {

        org.apache.commons.configuration2.Configuration configuration = Globals.getConfiguration();

        if(configuration==null)
        {
            System.out.println("Configuration is null : Unable to load Global Configuration !");

            return;
        }


        GlobalConstants.BASE_URI = configuration.getString(ConfigurationKeys.BASE_URL);

        GlobalConstants.POSTGRES_CONNECTION_URL = configuration.getString(ConfigurationKeys.CONNECTION_URL);
        GlobalConstants.CONNECTION_URL_CREATE_DB = configuration.getString(ConfigurationKeys.CONNECTION_URL_CREATE_DB);
        GlobalConstants.POSTGRES_USERNAME = configuration.getString(ConfigurationKeys.POSTGRES_USERNAME);
        GlobalConstants.POSTGRES_PASSWORD = configuration.getString(ConfigurationKeys.POSTGRES_PASSWORD);


        MSG91_SMS_SERVICE_API_KEY = configuration.getString(ConfigurationKeys.MSG91_SMS_SERVICE_API_KEY);
        FIREBASE_DRIVER_KEY = configuration.getString(ConfigurationKeys.FIREBASE_DRIVER_KEY);
        FIREBASE_END_USER_KEY = configuration.getString(ConfigurationKeys.FIREBASE_END_USER_KEY);


        GlobalConstants.REFERRAL_CREDIT_FOR_END_USER_REGISTRATION = configuration.getInt(ConfigurationKeys.REFERRAL_CREDIT_END_USER_REGISTRATION);
        GlobalConstants.REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION = configuration.getInt(ConfigurationKeys.REFERRAL_CREDIT_DRIVER_REGISTRATION);

        GlobalConstants.JOINING_CREDIT_FOR_DRIVER = configuration.getInt(ConfigurationKeys.JOINING_CREDIT_DRIVER);
        GlobalConstants.JOINING_CREDIT_FOR_END_USER = configuration.getInt(ConfigurationKeys.JOINING_CREDIT_END_USER);

        GlobalConstants.TOKEN_DURATION_MINUTES = configuration.getInt(ConfigurationKeys.TOKEN_DURATION_MINUTES);
        GlobalConstants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES = configuration.getInt(ConfigurationKeys.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES);
        GlobalConstants.PHONE_OTP_EXPIRY_MINUTES = configuration.getInt(ConfigurationKeys.PHONE_OTP_EXPIRY_MINUTES);

        GlobalConstants.PASSWORD_RESET_CODE_EXPIRY_MINUTES = configuration.getInt(ConfigurationKeys.PASSWORD_RESET_CODE_EXPIRY_MINUTES);

        GlobalConstants.TRIP_REQUEST_EXPIRY_MINUTES = configuration.getInt(ConfigurationKeys.TRIP_REQUEST_EXPIRY_MINUTES);
        GlobalConstants.TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES = configuration.getInt(ConfigurationKeys.TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES);

        GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MIN = configuration.getInt(ConfigurationKeys.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MIN);
        GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MAX = configuration.getInt(ConfigurationKeys.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MAX);



        GlobalConstants.max_limit = configuration.getInt(ConfigurationKeys.KEY_MAX_LIMIT);
        GlobalConstants.max_min_trip_charges = configuration.getInt(ConfigurationKeys.KEY_MAX_MIN_TRIP_CHARGES);
        GlobalConstants.max_charges_per_km = configuration.getInt(ConfigurationKeys.KEY_MAX_CHARGES_PER_KM);
        GlobalConstants.free_pickup_distance = configuration.getInt(ConfigurationKeys.KEY_FREE_PICKUP_DISTANCE);
        GlobalConstants.taxi_referral_charges = configuration.getInt(ConfigurationKeys.KEY_TAXI_REFERRAL_CHARGES);

        GlobalConstants.free_start_waiting_minutes = configuration.getInt(ConfigurationKeys.KEY_FREE_START_WAITING_MINUTES);
        GlobalConstants.free_minutes_per_km = configuration.getInt(ConfigurationKeys.KEY_FREE_MINUTES_PER_KM);
        GlobalConstants.wait_charges_per_minute = configuration.getInt(ConfigurationKeys.KEY_WAIT_CHARGES_PER_MINUTE);
        GlobalConstants.tax_rate_in_percent = configuration.getInt(ConfigurationKeys.KEY_TAX_RATE_IN_PERCENT);

        GlobalConstants.MIN_TAX_ACCOUNT_BALANCE = configuration.getInt(ConfigurationKeys.KEY_MIN_TAX_ACCOUNT_BALANCE);
        GlobalConstants.MIN_SERVICE_ACCOUNT_BALANCE = configuration.getInt(ConfigurationKeys.KEY_MIN_SERVICE_ACCOUNT_BALANCE);

        GlobalConstants.SHORTEST_DISTANCE_MULTIPLIER = configuration.getDouble(ConfigurationKeys.KEY_SHORTEST_DISTANCE_MULTIPLIER);

        GlobalConstants.TILESERVER_GL_STYLE_URL = configuration.getString(ConfigurationKeys.KEY_TILESERVER_GL_STYLE_URL);


        printGlobalConfiguration();

    }





    static void printGlobalConfiguration()
    {
        System.out.println("Printing API Configuration :  ");

        System.out.println("Base URI : " + GlobalConstants.BASE_URI);

        System.out.println("Postgres Connection URL : " + GlobalConstants.POSTGRES_CONNECTION_URL);
        System.out.println("Connection URL Create DB : " + GlobalConstants.CONNECTION_URL_CREATE_DB);

        System.out.println("Postgres Username : " + GlobalConstants.POSTGRES_USERNAME);
        System.out.println("Postgres Password : " + GlobalConstants.POSTGRES_PASSWORD);

        System.out.println("MSG91_KEY : " + MSG91_SMS_SERVICE_API_KEY);

        System.out.println("FIREBASE_DRIVER_KEY : " + FIREBASE_DRIVER_KEY);
        System.out.println("FIREBASE_END_USER_KEY : " + FIREBASE_END_USER_KEY);

        System.out.println("REFERRAL CREDIT FOR DRIVER REGISTRATION : " + GlobalConstants.REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION);
        System.out.println("REFERRAL CREDIT FOR END USER REGISTRATION : " + GlobalConstants.REFERRAL_CREDIT_FOR_END_USER_REGISTRATION);

        System.out.println("Joining Credit For Driver : " + GlobalConstants.JOINING_CREDIT_FOR_DRIVER);
        System.out.println("Joining Credit For End-User : " + GlobalConstants.JOINING_CREDIT_FOR_END_USER);

        System.out.println("Token Duration Minutes : " + GlobalConstants.TOKEN_DURATION_MINUTES);
        System.out.println("Email Verification Code Expiry Minutes : " + GlobalConstants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES);

        System.out.println("Phone OTP Expiry Minutes : " + GlobalConstants.PHONE_OTP_EXPIRY_MINUTES);
        System.out.println("Password Reset Code Expiry Minutes : " + GlobalConstants.PASSWORD_RESET_CODE_EXPIRY_MINUTES);

        System.out.println("Trip Request Code Expiry Minutes : " + GlobalConstants.TRIP_REQUEST_EXPIRY_MINUTES);
        System.out.println("Trip Request Code Expiry Extension Minutes : " + GlobalConstants.TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES);


        System.out.println("Months to extend Taxi Registration Minimum : " + GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MIN);
        System.out.println("Months to extend Taxi Registration Maximum : " + GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MAX);

        System.out.println("MAX LIMIT : " + GlobalConstants.max_limit);
        System.out.println("MAX MIN TRIP CHARGES : " + GlobalConstants.max_min_trip_charges);



        System.out.println("TileServerGL StyleURL : " + GlobalConstants.TILESERVER_GL_STYLE_URL);
    }





    private static org.apache.commons.configuration2.Configuration configuration;


    public static org.apache.commons.configuration2.Configuration getConfiguration() {
        if (configuration == null) {
            Parameters params = new Parameters();
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("api_config.properties"));


            try {
                configuration = builder.getConfiguration();

            } catch (ConfigurationException cex) {
                // loading of the configuration file failed

                System.out.println(cex.getStackTrace());

                configuration = null;
            }

            return configuration;
        } else {
            return configuration;
        }
    }





    public static void reloadConfiguration()
    {

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("api_config.properties"));


        try
        {
            configuration = builder.getConfiguration();

        }
        catch(ConfigurationException cex)
        {
            // loading of the configuration file failed

            System.out.println(cex.getStackTrace());

            configuration = null;
        }
    }




    private static String MSG91_SMS_SERVICE_API_KEY = null;

    public static String getMsg91APIKey()
    {

        if(MSG91_SMS_SERVICE_API_KEY==null)
        {
            org.apache.commons.configuration2.Configuration configuration = Globals.getConfiguration();


            if(configuration==null)
            {
                System.out.println("Configuration is null : getMsg91APIKey() !");

                return null ;
            }


            MSG91_SMS_SERVICE_API_KEY = configuration.getString(ConfigurationKeys.MSG91_SMS_SERVICE_API_KEY);
        }


        System.out.println("Msg91 API KEY : " + MSG91_SMS_SERVICE_API_KEY);

        return MSG91_SMS_SERVICE_API_KEY;
    }








    private static String FIREBASE_DRIVER_KEY = null;
    private static String FIREBASE_END_USER_KEY = null;


    public static String getFirebaseDriverKey()
    {

        if(FIREBASE_DRIVER_KEY==null)
        {
            org.apache.commons.configuration2.Configuration configuration = Globals.getConfiguration();


            if(configuration==null)
            {
                System.out.println("Configuration is null : getFirebaseDriverKey() !");

                return null ;
            }


            FIREBASE_DRIVER_KEY = configuration.getString(ConfigurationKeys.FIREBASE_DRIVER_KEY);
        }



        System.out.println("Firebase Driver Key : " + FIREBASE_DRIVER_KEY);

        return FIREBASE_DRIVER_KEY;
    }





    public static String getFirebaseEndUserKey()
    {

        if(FIREBASE_END_USER_KEY==null)
        {
            org.apache.commons.configuration2.Configuration configuration = Globals.getConfiguration();


            if(configuration==null)
            {
                System.out.println("Configuration is null : getFirebaseEndUserKey() !");

                return null ;
            }


            FIREBASE_END_USER_KEY = configuration.getString(ConfigurationKeys.FIREBASE_END_USER_KEY);
        }


        System.out.println("FirebaseEndUserKey : " + FIREBASE_END_USER_KEY);

        return FIREBASE_END_USER_KEY;
    }







    /* Send Notification throught firebase */






    // configure Notifications




    // broadcast messages to end user

//    public static Map<Integer,SseBroadcaster> broadcasterMapEndUser = new HashMap<>();
//
//    public static String broadcastMessageToEndUser(String title, String message, int endUserID) {
//
//        OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
//        OutboundEvent event = eventBuilder.name(title)
//                .mediaType(MediaType.TEXT_PLAIN_TYPE)
//                .data(String.class, message)
//                .build();
//
//
//        if(broadcasterMapEndUser.get(endUserID)!=null)
//        {
//            broadcasterMapEndUser.get(endUserID).broadcast(event);
//        }
//
//        return "Message '" + message + "' has been broadcast.";
//    }








//    public static String sendSms(String messageText) {
//        try {
//            // Construct data
//            String user = "username=" + URLEncoder.encode("karmicroutes@gmail.com", "UTF-8");
//            String hash = "&hash=" + URLEncoder.encode("e8a6d7746e66d0f8184c12f7eb37bfdd6faf734babb1623ff6622a8680091047", "UTF-8");
//            String message = "&message=" + URLEncoder.encode(messageText, "UTF-8");
//            String sender = "&sender=" + URLEncoder.encode("TXTLCL", "UTF-8");
//            String numbers = "&numbers=" + URLEncoder.encode("919490523891", "UTF-8");
//
//            // Send data
//            String data = "http://api.textlocal.in/send/?" + user + hash + numbers + message + sender;
//            URL url = new URL(data);
//            URLConnection conn = url.openConnection();
//            conn.setDoOutput(true);
//
//            System.out.println("Sending SMS !");
//
//            // Get the response
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            String sResult = "";
//            while ((line = rd.readLine()) != null) {
//                // Process line...
//                sResult = sResult + line + " ";
//            }
//            rd.close();
//
//            return sResult;
//
//        } catch (Exception e) {
//            System.out.println("Error SMS " + e);
//            return "Error " + e;
//        }
//
//    }


}
