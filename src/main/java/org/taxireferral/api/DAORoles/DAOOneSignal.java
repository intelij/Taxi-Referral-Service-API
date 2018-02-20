package org.taxireferral.api.DAORoles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariDataSource;
import okhttp3.*;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelOneSignal.Message;
import org.taxireferral.api.ModelOneSignal.OneSignalData;
import org.taxireferral.api.ModelOneSignal.SignalNotification;
import org.taxireferral.api.ModelOneSignal.Title;
import org.taxireferral.api.ModelRoles.OneSignalIDs;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.ModelUtility.LocationCurrentTrip;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOOneSignal {


    private HikariDataSource dataSource = Globals.getDataSource();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");




    public void sendNotificationToDriver(int userID,
                                             String largeIcon,
                                             String bigPicture,
                                             String sound,
                                             int priority,
                                             String title,
                                             String message,
                                             int screenToOpen,
                                             int notificationType)
    {

        sendNotificationToUser(userID,
                GlobalConstants.ONE_SIGNAL_APP_ID_DRIVER,
                GlobalConstants.ONE_SIGNAL_API_KEY_DRIVER,
                largeIcon,
                bigPicture,
                sound,
                priority,
                title,
                message,
                screenToOpen,
                notificationType);

    }




    public void sendNotificationToEndUser(int userID,
                                         String largeIcon,
                                         String bigPicture,
                                         String sound,
                                         int priority,
                                         String title,
                                         String message,
                                         int screenToOpen,
                                         int notificationType)
    {

        sendNotificationToUser(userID,
                GlobalConstants.ONE_SIGNAL_APP_ID_USER,
                GlobalConstants.ONE_SIGNAL_API_KEY_USER,
                largeIcon,
                bigPicture,
                sound,
                priority,
                title,
                message,
                screenToOpen,
                notificationType);

    }







    public void sendNotificationToUser(
            int userID,
            String appID,
            String apiKey,
            String largeIcon,
            String bigPicture,
            String sound,
            int priority,
            String title,
            String message,
            int screenToOpen,
            int notificationType
    )
    {
        User user = getPlayerID(userID);

        SignalNotification notification  = new SignalNotification();
        notification.setApp_id(appID);
        notification.getInclude_player_ids().add(user.getRt_oneSignalPlayerID());
        notification.setLarge_icon(largeIcon);
        notification.setBig_picture(bigPicture);
        notification.setAndroid_sound(sound);
        notification.setPriority(priority);
        notification.setHeadings(new Title(title));
        notification.setContents(new Message(message));
        notification.setData(new OneSignalData(notificationType,screenToOpen));


        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        String json = gson.toJson(notification);

        apiKey = "basic " + apiKey;

        final Request request = new Request.Builder()
                .url("https://onesignal.com/api/v1/notifications")
                .addHeader("Authorization",apiKey)
                .addHeader("Content-Type","application/json")
                .post(RequestBody.create(JSON,json))
                .build();


        System.out.print(json + "\n");


        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                System.out.print("Sending notification failed !");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                if(response.isSuccessful())
                {
                    System.out.print(response.body().string() + "\n");
//                    FirebaseResponse firebaseResponse = gson.fromJson(response.body().string(),FirebaseResponse.class);
                }
                else
                {
                    System.out.print(response.toString()+ "\n");
                }

            }
        });
    }







    public User getPlayerID(int userID)
    {

        boolean isFirst = true;

        String query = "SELECT " + OneSignalIDs.PLAYER_ID + ""
                    + " FROM " + OneSignalIDs.TABLE_NAME
                    + " WHERE " + OneSignalIDs.USER_ID + " = ? ";


        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setObject(++i,userID); // username



            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

//                user.setUserID(rs.getInt(OneSignalIDs.USER_ID));
                user.setRt_oneSignalPlayerID(rs.getString(OneSignalIDs.PLAYER_ID));
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return user;
    }




    public int updateOneSignalID(int userID,String playerID)
    {


        String insert =

                "INSERT INTO " + OneSignalIDs.TABLE_NAME
                        + "("
                        + OneSignalIDs.USER_ID + ","
                        + OneSignalIDs.PLAYER_ID + ""
                        + ") values(?,?)"
                        + " ON CONFLICT (" + OneSignalIDs.USER_ID + ")"
                        + " DO UPDATE "
                        + " SET "
                        + OneSignalIDs.PLAYER_ID + "= excluded." + OneSignalIDs.PLAYER_ID ;




        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statement.setObject(++i,userID);
            statement.setObject(++i,playerID);

            rowCountUpdated = statement.executeUpdate();


            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

//                    idOfInsertedRow=-1;
//                    rowCountItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return rowCountUpdated;
    }




}
