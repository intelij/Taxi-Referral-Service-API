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
import org.taxireferral.api.DAOBilling.DAOUPIPayments;
import org.taxireferral.api.DAOImages.TaxiImagesDAO;
import org.taxireferral.api.DAOIssues.TripIssueOptionsDAO;
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
    public static DAOUPIPayments daoupiPayments = new DAOUPIPayments();


    public static DAOEmailVerificationCodes daoEmailVerificationCodes = new DAOEmailVerificationCodes();
    public static DAOPhoneVerificationCodes daoPhoneVerificationCodes = new DAOPhoneVerificationCodes();

    public static VehicleTypeDAOVersions daoVehicleTypeNew = new VehicleTypeDAOVersions();
    public static VehicleTypeDAOGet daoVehicleTypeGet = new VehicleTypeDAOGet();

    public static TaxiImagesDAO daoTaxiImages = new TaxiImagesDAO();
    public static TripIssueOptionsDAO issueOptionsDAO = new TripIssueOptionsDAO();

    public static DAOUserNotifications userNotifications = new DAOUserNotifications();

    // static reference for holding security accountApproved

    public static Object accountApproved;






//    // mailgun configuration

    private static Configuration configurationMailgun;


    public static Configuration getMailgunConfiguration()
    {

        if(configurationMailgun==null)
        {

            configurationMailgun = new Configuration()
                    .domain(GlobalConstants.MAILGUN_DOMAIN)
                    .apiKey(GlobalConstants.MAILGUN_API_KEY)
                    .from(GlobalConstants.MAILGUN_NAME,GlobalConstants.MAILGUN_EMAIL);

            return configurationMailgun;
        }
        else
        {
            return configurationMailgun;
        }
    }



//    public static Configuration getMailgunConfiguration()
//    {
//
//        if(configurationMailgun==null)
//        {
////
////            org.apache.commons.configuration2.Configuration configuration = GlobalConfig.getConfiguration();
////
////
////            if(configuration==null)
////            {
////                System.out.println("Configuration is null : getMailgunConfiguration() !");
////
////                return null ;
////            }
////
////
////
////
//////            String domain = configuration.getString(ConfigurationKeys.MAILGUN_DOMAIN_KEY);
//////            String api_key = configuration.getString(ConfigurationKeys.MAILGUN_API_KEY);
//////            String email = configuration.getString(ConfigurationKeys.MAILGUN_EMAIL_KEY);
//////            String name = configuration.getString(ConfigurationKeys.MAILGUN_NAME_KEY);
//
//            configurationMailgun = new Configuration()
//                    .domain(GlobalConstants.MAILGUN_DOMAIN)
//                    .apiKey(GlobalConstants.MAILGUN_API_KEY)
//                    .from(GlobalConstants.MAILGUN_NAME,GlobalConstants.MAILGUN_EMAIL);
//
//
//
//
////            configurationMailgun = new Configuration()
////                    .domain("community.nearbyshops.org")
////                    .apiKey("key-b73ddc1406a0f651e579cb21d388864d")
////                    .from("Sumeet", "postmaster@vedic-astrology-forum.com");
//
//
//
//            return configurationMailgun;
//        }
//        else
//        {
//            return configurationMailgun;
//        }
//    }




    // Configure Connection Pooling


    private static HikariDataSource dataSource;

    public static HikariDataSource getDataSource() {


        if (dataSource == null) {

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(GlobalConstants.POSTGRES_CONNECTION_URL);
            config.setUsername(GlobalConstants.POSTGRES_USERNAME);
            config.setPassword(GlobalConstants.POSTGRES_PASSWORD);

            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }


}
