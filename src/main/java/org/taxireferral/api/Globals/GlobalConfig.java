package org.taxireferral.api.Globals;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.eclipse.jetty.util.PathWatcher;

public class GlobalConfig {




    public static void loadGlobalConfiguration()
    {

        org.apache.commons.configuration2.Configuration configuration = getConfiguration();

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

        GlobalConstants.MAILGUN_API_KEY = configuration.getString(ConfigurationKeys.MAILGUN_API_KEY);
        GlobalConstants.MAILGUN_DOMAIN = configuration.getString(ConfigurationKeys.MAILGUN_DOMAIN_KEY);
        GlobalConstants.MAILGUN_EMAIL = configuration.getString(ConfigurationKeys.MAILGUN_EMAIL_KEY);
        GlobalConstants.MAILGUN_NAME = configuration.getString(ConfigurationKeys.MAILGUN_NAME_KEY);

        GlobalConstants.MSG91_SMS_SERVICE_API_KEY = configuration.getString(ConfigurationKeys.MSG91_SMS_SERVICE_API_KEY);
        GlobalConstants.FIREBASE_DRIVER_KEY = configuration.getString(ConfigurationKeys.FIREBASE_DRIVER_KEY);
        GlobalConstants.FIREBASE_END_USER_KEY = configuration.getString(ConfigurationKeys.FIREBASE_END_USER_KEY);


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

        GlobalConstants.DEFAULT_COUNTRY_CODE = configuration.getString(ConfigurationKeys.KEY_DEFAULT_COUNTRY_CODE);

        GlobalConstants.TILESERVER_GL_STYLE_URL = configuration.getString(ConfigurationKeys.KEY_TILESERVER_GL_STYLE_URL);
        GlobalConstants.NOTIFICATION_SERVER_HOST_MQTT = configuration.getString(ConfigurationKeys.KEY_MQTT_SERVER_ADDRESS);
        GlobalConstants.GEOCODER_ADDRESS = configuration.getString(ConfigurationKeys.KEY_GEOCODER_ADDRESS);

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

        System.out.println("Mailgun Name : " + GlobalConstants.MAILGUN_NAME);
        System.out.println("Mailgun E-mail : " + GlobalConstants.MAILGUN_EMAIL);
        System.out.println("Mailgun Domain : " + GlobalConstants.MAILGUN_DOMAIN);
        System.out.println("Mailgun API KEY : " + GlobalConstants.MAILGUN_API_KEY);

        System.out.println("MSG91_KEY : " + GlobalConstants.MSG91_SMS_SERVICE_API_KEY);
        System.out.println("FIREBASE_DRIVER_KEY : " + GlobalConstants.FIREBASE_DRIVER_KEY);
        System.out.println("FIREBASE_END_USER_KEY : " + GlobalConstants.FIREBASE_END_USER_KEY);

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


        System.out.println("Default Country Code : " + GlobalConstants.DEFAULT_COUNTRY_CODE);
        System.out.println("TileServerGL StyleURL : " + GlobalConstants.TILESERVER_GL_STYLE_URL);
        System.out.println("MQTT Host address : " + GlobalConstants.NOTIFICATION_SERVER_HOST_MQTT);
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
            GlobalConfig.configuration = builder.getConfiguration();

        }
        catch(ConfigurationException cex)
        {
            // loading of the configuration file failed

            System.out.println(cex.getStackTrace());

            configuration = null;
        }
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





//    public static String getMsg91APIKey()
//    {
//
//        if(MSG91_SMS_SERVICE_API_KEY==null)
//        {
//            org.apache.commons.configuration2.Configuration configuration = GlobalConfig.getConfiguration();
//
//
//            if(configuration==null)
//            {
//                System.out.println("Configuration is null : getMsg91APIKey() !");
//
//                return null ;
//            }
//
//
//            MSG91_SMS_SERVICE_API_KEY = configuration.getString(ConfigurationKeys.MSG91_SMS_SERVICE_API_KEY);
//        }
//
//
//        System.out.println("Msg91 API KEY : " + MSG91_SMS_SERVICE_API_KEY);
//
//        return MSG91_SMS_SERVICE_API_KEY;
//    }








//
//    public static String getFirebaseDriverKey()
//    {
//
//        if(FIREBASE_DRIVER_KEY==null)
//        {
//            org.apache.commons.configuration2.Configuration configuration = GlobalConfig.getConfiguration();
//
//
//            if(configuration==null)
//            {
//                System.out.println("Configuration is null : getFirebaseDriverKey() !");
//
//                return null ;
//            }
//
//
//            FIREBASE_DRIVER_KEY = configuration.getString(ConfigurationKeys.FIREBASE_DRIVER_KEY);
//        }
//
//
//
//        System.out.println("Firebase Driver Key : " + FIREBASE_DRIVER_KEY);
//
//        return FIREBASE_DRIVER_KEY;
//    }
//



//
//    public static String getFirebaseEndUserKey()
//    {
//
//        if(FIREBASE_END_USER_KEY==null)
//        {
//            org.apache.commons.configuration2.Configuration configuration = GlobalConfig.getConfiguration();
//
//
//            if(configuration==null)
//            {
//                System.out.println("Configuration is null : getFirebaseEndUserKey() !");
//
//                return null ;
//            }
//
//
//            FIREBASE_END_USER_KEY = configuration.getString(ConfigurationKeys.FIREBASE_END_USER_KEY);
//        }
//
//
//
//
//        System.out.println("FirebaseEndUserKey : " + FIREBASE_END_USER_KEY);
//
//        return FIREBASE_END_USER_KEY;
//    }


}
