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

    

    // mailgun configuration

    private static Configuration configurationMailgun;



    public static Configuration getMailgunConfiguration()
    {

        if(configurationMailgun==null)
        {

            org.apache.commons.configuration2.Configuration configuration = GlobalConfig.getConfiguration();


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


            org.apache.commons.configuration2.Configuration configuration = GlobalConfig.getConfiguration();


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
