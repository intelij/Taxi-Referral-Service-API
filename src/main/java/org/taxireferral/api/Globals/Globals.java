package org.taxireferral.api.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sargue.mailgun.Configuration;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.taxireferral.api.DAORoles.*;
import org.taxireferral.api.DAORoles.deprecated.DAOUser;
import org.taxireferral.api.DAOs.*;
import org.taxireferral.api.JDBCContract;

import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sumeet on 22/3/17.
 */
public class Globals {


    // secure randon for generating tokens
    public static SecureRandom random = new SecureRandom();




    public static DAOUser daoUser = new DAOUser();
    public static DAOUserNew daoUserNew = new DAOUserNew();
    public static DAOResetPassword daoResetPassword = new DAOResetPassword();
    public static DAOUserSignUp daoUserSignUp = new DAOUserSignUp();
    public static DAOStaff daoStaff = new DAOStaff();

    public static VehicleDAO vehicleDAO = new VehicleDAO();
    public static DAOTripRequest tripRequestDAO = new DAOTripRequest();
    public static DAOCurrentTrip daoCurrentTrip = new DAOCurrentTrip();
    public static DAOTripHistory daoTripHistory = new DAOTripHistory();

    public static DAOTransaction daoTransaction = new DAOTransaction();


    public static DAOEmailVerificationCodes daoEmailVerificationCodes = new DAOEmailVerificationCodes();
    public static DAOPhoneVerificationCodes daoPhoneVerificationCodes = new DAOPhoneVerificationCodes();

    public static VehicleTypeDAONew daoVehicleTypeNew = new VehicleTypeDAONew();
    public static VehicleTypeDAOGet daoVehicleTypeGet = new VehicleTypeDAOGet();

    public static DAOUserNotifications userNotifications = new DAOUserNotifications();

    // static reference for holding security accountApproved

    public static Object accountApproved;




    // mailgun configuration

    public static Configuration configurationMailgun = new Configuration()
            .domain("community.nearbyshops.org")
            .apiKey("key-b73ddc1406a0f651e579cb21d388864d")
            .from("Sumeet", "postmaster@vedic-astrology-forum.com");






    // Configure Connection Pooling



    private static HikariDataSource dataSource;

    public static HikariDataSource getDataSource()
    {
        if(dataSource==null)
        {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(JDBCContract.CURRENT_CONNECTION_URL);
            config.setUsername(JDBCContract.CURRENT_USERNAME);
            config.setPassword(JDBCContract.CURRENT_PASSWORD);

            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }


    /* Send Notification throught firebase */






    // configure Notifications




    // broadcast messages to end user

    public static Map<Integer,SseBroadcaster> broadcasterMapEndUser = new HashMap<>();

    public static String broadcastMessageToEndUser(String title, String message, int endUserID) {

        OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
        OutboundEvent event = eventBuilder.name(title)
                .mediaType(MediaType.TEXT_PLAIN_TYPE)
                .data(String.class, message)
                .build();


        if(broadcasterMapEndUser.get(endUserID)!=null)
        {
            broadcasterMapEndUser.get(endUserID).broadcast(event);
        }

        return "Message '" + message + "' has been broadcast.";
    }








    public static String sendSms(String messageText) {
        try {
            // Construct data
            String user = "username=" + URLEncoder.encode("karmicroutes@gmail.com", "UTF-8");
            String hash = "&hash=" + URLEncoder.encode("e8a6d7746e66d0f8184c12f7eb37bfdd6faf734babb1623ff6622a8680091047", "UTF-8");
            String message = "&message=" + URLEncoder.encode(messageText, "UTF-8");
            String sender = "&sender=" + URLEncoder.encode("TXTLCL", "UTF-8");
            String numbers = "&numbers=" + URLEncoder.encode("919490523891", "UTF-8");

            // Send data
            String data = "http://api.textlocal.in/send/?" + user + hash + numbers + message + sender;
            URL url = new URL(data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            System.out.println("Sending SMS !");

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult = "";
            while ((line = rd.readLine()) != null) {
                // Process line...
                sResult = sResult + line + " ";
            }
            rd.close();

            return sResult;

        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }

    }


}
