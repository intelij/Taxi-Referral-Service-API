package org.taxireferral.api.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sargue.mailgun.Configuration;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.taxireferral.api.DAORoles.DAOUser;
import org.taxireferral.api.JDBCContract;

import javax.ws.rs.core.MediaType;
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


    // static reference for holding security accountApproved

    public static Object accountApproved;




    // mailgun configuration

    public static Configuration configurationMailgun = new Configuration()
            .domain("vedic-astrology-forum.com")
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

}
