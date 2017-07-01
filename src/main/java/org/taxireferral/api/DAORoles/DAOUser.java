package org.taxireferral.api.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelRoles.User;

import java.sql.*;

/**
 * Created by sumeet on 2/4/17.
 */
public class DAOUser {

    private HikariDataSource dataSource = Globals.getDataSource();


    public DAOUser() {
    }

    // insert User
    // update user
    // updateToken : generate token using user id and password


    // check username exist
    // check email exist
    // check phone exist

    // setPhoneVerified
    // setEmailverified


    // verifyUser() : check user authentication
    // getProfile : give information to the user


    //--------------------
    // insert user
    // update user
    // update email
    // update password
    // update token
    // verify user | for authentication filter
    // get profile | for login
    // check username exists | to check username at time of registration
    // check google ID | to check whether the person is registered or not
    // save google profile | create google account


    // set email verification code
    // check email verification code
    // set email verified




    public int insertUser(User user, boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertItemSubmission = "INSERT INTO "
                + User.TABLE_NAME
                + "("

                + User.USERNAME + ","
                + User.PASSWORD + ","
                + User.E_MAIL + ","

                + User.PHONE + ","
                + User.NAME + ","
                + User.GENDER + ","

//                + User.PROFILE_IMAGE_ID + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ""
                + ") VALUES(?,?,? ,?,?,? ,?,?,?,?)";


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statement.setString(++i,user.getUsername());
            statement.setString(++i,user.getPassword());
            statement.setString(++i,user.getEmail());

            statement.setString(++i,user.getPhone());
            statement.setString(++i,user.getName());
            statement.setObject(++i,user.getGender());

//            statement.setObject(++i,user.getProfileImageID());
            statement.setObject(++i,user.getRole());
            statement.setObject(++i,user.isAccountPrivate());
            statement.setString(++i,user.getAbout());

            rowCountItems = statement.executeUpdate();


            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }



            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

                    idOfInsertedRow=-1;
                    rowCountItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(getRowCount)
        {
            return rowCountItems;
        }
        else
        {
            return idOfInsertedRow;
        }
    }





    public int updateEmail(User user)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "
                + User.E_MAIL + "=?,"

                + " WHERE " + User.USERNAME + " = ?";
//                + " AND " + User.PASSWORD + " = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setString(++i,user.getEmail());
            statement.setObject(++i,false);
            statement.setString(++i,user.getUsername());
//            statement.setString(++i,user.getPassword());

            rowCountUpdated = statement.executeUpdate();

            System.out.println("Total rows updated: " + rowCountUpdated);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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




//    public int setEmailVerificationCode(User user)
//    {
//
//        String updateStatement = "UPDATE " + User.TABLE_NAME
//
//                + " SET "
//                + User.EMAIL_VERIFICATION_CODE + "=?,"
//                + User.EMAIL_VERIFICATION_CODE_TIMESTAMP_EXPIRES + "=?"
//
//                + " WHERE " + User.USERNAME + " = ?";
////                + " AND " + User.PASSWORD + " = ?";
//
//
//        Connection connection = null;
//        PreparedStatement statement = null;
//
//        int rowCountUpdated = 0;
//
//        try {
//
//            connection = dataSource.getConnection();
//            statement = connection.prepareStatement(updateStatement);
//
//            int i = 0;
//
//            statement.setString(++i,user.getEmailVerificationCode());
//            statement.setTimestamp(++i,user.getEmailVerificationCodeTimestampExpires());
//
//            statement.setString(++i,user.getUsername());
////            statement.setString(++i,user.getPassword());
//
//            rowCountUpdated = statement.executeUpdate();
//
//            System.out.println("Total rows updated: " + rowCountUpdated);
//
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally
//
//        {
//
//            try {
//
//                if(statement!=null)
//                {statement.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            try {
//
//                if(connection!=null)
//                {connection.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        return rowCountUpdated;
//    }



//    public User checkEmailCode(String username)
//    {
//
//        boolean isFirst = true;
//
//        String query = "SELECT "
//
//                + User.E_MAIL + ","
//                + User.IS_EMAIL_VERIFIED + ","
//                + User.EMAIL_VERIFICATION_CODE + ","
//                + User.EMAIL_VERIFICATION_CODE_TIMESTAMP_EXPIRES + ""
//
//                + " FROM " + User.TABLE_NAME
//                + " WHERE " + User.USERNAME + " = ? ";
//
////        CAST (" + User.TIMESTAMP_TOKEN_EXPIRES + " AS TIMESTAMP)"
//
//
//
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet rs = null;
//
//
//        //Distributor distributor = null;
//        User user = null;
//
//        try {
//
//            connection = dataSource.getConnection();
//            statement = connection.prepareStatement(query);
//
//            int i = 0;
//            statement.setString(++i,username); // username
//
//
//            rs = statement.executeQuery();
//
//            while(rs.next())
//            {
//                user = new User();
//
//                user.setEmail(rs.getString(User.E_MAIL));
//                user.setEmailVerified(rs.getBoolean(User.IS_EMAIL_VERIFIED));
//                user.setEmailVerificationCode(rs.getString(User.EMAIL_VERIFICATION_CODE));
//                user.setEmailVerificationCodeTimestampExpires(rs.getTimestamp(User.EMAIL_VERIFICATION_CODE_TIMESTAMP_EXPIRES));
//
//
////                System.out.println(" USER TEMP : " + user.getEmailVerificationCodeTimestampExpires().toLocaleString());
//            }
//
////            System.out.println(" USER TEMP NULL");
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally
//
//        {
//
//            try {
//                if(rs!=null)
//                {rs.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            try {
//
//                if(statement!=null)
//                {statement.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            try {
//
//                if(connection!=null)
//                {connection.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        return user;
//    }
//





//    public int setEmailVerified(User user)
//    {
//
//        String updateStatement = "UPDATE " + User.TABLE_NAME
//
//                + " SET "
//                + User.IS_EMAIL_VERIFIED + "=?"
//
//                + " WHERE " + User.USERNAME + " = ?"
////                + " AND " + User.PASSWORD + " = ?"
//                + " AND " + User.EMAIL_VERIFICATION_CODE + " = ?"
//                + " AND " + User.EMAIL_VERIFICATION_CODE_TIMESTAMP_EXPIRES + " > now()";
//
//
//        Connection connection = null;
//        PreparedStatement statement = null;
//
//        int rowCountUpdated = 0;
//
//        try {
//
//            connection = dataSource.getConnection();
//            statement = connection.prepareStatement(updateStatement);
//
//            int i = 0;
//
//            statement.setObject(++i,true);
//            statement.setString(++i,user.getUsername());
////            statement.setString(++i,user.getPassword());
//            statement.setString(++i,user.getEmailVerificationCode());
//
//            rowCountUpdated = statement.executeUpdate();
//
//            System.out.println("Total rows updated: " + rowCountUpdated);
//
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally
//
//        {
//
//            try {
//
//                if(statement!=null)
//                {statement.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            try {
//
//                if(connection!=null)
//                {connection.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        return rowCountUpdated;
//    }



















}
