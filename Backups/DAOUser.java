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
    // setemailverified


    // verifyUser() : check user authentication
    // getProfile : give information to the user





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

                + User.PROFILE_IMAGE_URL + ","
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
            statement.setObject(++i,user.isGender());

            statement.setString(++i,user.getProfileImageURL());
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




    public int updateUser(User user)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "

                + User.USERNAME + "=?,"
//                + User.PASSWORD + "=?,"
//                + User.E_MAIL + "=?,"

                + User.PHONE + "=?,"
                + User.NAME + "=?,"
                + User.GENDER + "=?,"

                + User.PROFILE_IMAGE_URL + "=?,"
                + User.IS_ACCOUNT_PRIVATE + "=?,"
                + User.ABOUT + "=?"

                + " WHERE " + User.USER_ID + " = ?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setString(++i,user.getUsername());
//            statement.setString(++i,user.getPassword());
//            statement.setString(++i,user.getEmail());

            statement.setString(++i,user.getPhone());
            statement.setString(++i,user.getName());
            statement.setObject(++i,user.isGender());

            statement.setString(++i,user.getProfileImageURL());
            statement.setObject(++i,user.isAccountPrivate());
            statement.setString(++i,user.getAbout());

            statement.setObject(++i,user.getUserID());


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



    public int updateEmail(User user)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "
                + User.E_MAIL + "=?,"
                + User.IS_EMAIL_VERIFIED + "=?"

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




    public int setEmailVerificationCode(User user)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "
                + User.EMAIL_VERIFICATION_CODE + "=?,"
                + User.EMAIL_VERIFICATION_CODE_TIMESTAMP_EXPIRES + "=?"

                + " WHERE " + User.USERNAME + " = ?";
//                + " AND " + User.PASSWORD + " = ?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setString(++i,user.getEmailVerificationCode());
            statement.setTimestamp(++i,user.getEmailVerificationCodeTimestampExpires());

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



    public User checkEmailTimestamp(String username)
    {

        boolean isFirst = true;

        String query = "SELECT "

                + User.E_MAIL + ","
                + User.IS_EMAIL_VERIFIED + ","
                + User.EMAIL_VERIFICATION_CODE + ","
                + User.EMAIL_VERIFICATION_CODE_TIMESTAMP_EXPIRES + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE " + User.USERNAME + " = ? ";

//        CAST (" + User.TIMESTAMP_TOKEN_EXPIRES + " AS TIMESTAMP)"



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setString(++i,username); // username


            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

                user.setEmail(rs.getString(User.E_MAIL));
                user.setEmailVerified(rs.getBoolean(User.IS_EMAIL_VERIFIED));
                user.setEmailVerificationCode(rs.getString(User.EMAIL_VERIFICATION_CODE));
                user.setEmailVerificationCodeTimestampExpires(rs.getTimestamp(User.EMAIL_VERIFICATION_CODE_TIMESTAMP_EXPIRES));


//                System.out.println(" USER TEMP : " + user.getEmailVerificationCodeTimestampExpires().toLocaleString());
            }

//            System.out.println(" USER TEMP NULL");

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






    public int setEmailVerified(User user)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "
                + User.IS_EMAIL_VERIFIED + "=?"

                + " WHERE " + User.USERNAME + " = ?"
//                + " AND " + User.PASSWORD + " = ?"
                + " AND " + User.EMAIL_VERIFICATION_CODE + " = ?"
                + " AND " + User.EMAIL_VERIFICATION_CODE_TIMESTAMP_EXPIRES + " > now()";



        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setObject(++i,true);
            statement.setString(++i,user.getUsername());
//            statement.setString(++i,user.getPassword());
            statement.setString(++i,user.getEmailVerificationCode());

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





    public int updatePassword(User user, String oldPassword)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "
                + User.PASSWORD + "=?"

                + " WHERE " + User.USERNAME + " = ?"
                + " AND " + User.PASSWORD + " = ?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

//            statement.setString(++i,user.getToken());
//            statement.setTimestamp(++i,user.getTimestampTokenExpires());

            statement.setString(++i,user.getPassword());

            statement.setString(++i,user.getUsername());
            statement.setString(++i,oldPassword);


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








    public int updateToken(User user)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "
                + User.TOKEN + "=?,"
                + User.TIMESTAMP_TOKEN_EXPIRES + "=?"

                + " WHERE "
                + User.USERNAME + " = ?"
                + " AND " + User.PASSWORD + " = ?";


//                + " ( " + User.USERNAME + " = ? "
//                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
//                + " OR " + " ( " + User.E_MAIL + " = ?" + " AND " + User.IS_EMAIL_VERIFIED + " = TRUE " + ")"
//                + " OR " + " ( " + User.PHONE + " = ?" + " AND " + User.IS_PHONE_VERIFIED + " = TRUE " + ")"
//                + ")"
//                + " AND " + User.PASSWORD + " = ? ";



        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setString(++i,user.getToken());
            statement.setTimestamp(++i,user.getTimestampTokenExpires());




//            statement.setString(++i,username); // username
//            statement.setString(++i,username); // userID
//            statement.setString(++i,username); // email
//            statement.setString(++i,username); // phone
//            statement.setString(++i,token); // token

            statement.setString(++i,user.getUsername());
            statement.setString(++i,user.getPassword());

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








    public User verifyUser(String username, String token)
    {

        boolean isFirst = true;

        String query = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
                + User.ENABLED + ","
                + User.ROLE + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + " AND " + User.IS_EMAIL_VERIFIED + " = TRUE " + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + " AND " + User.IS_PHONE_VERIFIED + " = TRUE " + ")" + ")"
                + " AND " + User.TOKEN + " = ? "
                + " AND " + User.TIMESTAMP_TOKEN_EXPIRES + " > now()";

//        CAST (" + User.TIMESTAMP_TOKEN_EXPIRES + " AS TIMESTAMP)"



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

//            System.out.println(query);

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setString(++i,username); // username
            statement.setString(++i,username); // userID
            statement.setString(++i,username); // email
            statement.setString(++i,username); // phone
            statement.setString(++i,token); // token
//            statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));


            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
                user.setEnabled(rs.getBoolean(User.ENABLED));
                user.setRole(rs.getInt(User.ROLE));
            }


            //System.out.println("Total itemCategories queried " + itemCategoryList.size());



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





    public User getProfile(String username, String password)
    {

        boolean isFirst = true;

        String query = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
//                + User.PASSWORD + ","
                + User.E_MAIL + ","
                + User.IS_EMAIL_VERIFIED + ","
                + User.PHONE + ","
//                + User.IS_PHONE_VERIFIED + ","
                + User.NAME + ","
                + User.GENDER + ","
                + User.PROFILE_IMAGE_URL + ","
//                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ""
//                + User.TOKEN + ","
//                + User.TIMESTAMP_TOKEN_EXPIRES + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + " AND " + User.IS_EMAIL_VERIFIED + " = TRUE " + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + " AND " + User.IS_PHONE_VERIFIED + " = TRUE " + ")"
                + ")"
                + " AND " + User.PASSWORD + " = ? ";



//                + " ( " + User.USERNAME + " = ? "
//                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? )";

//        CAST (" + User.TIMESTAMP_TOKEN_EXPIRES + " AS TIMESTAMP)"



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


            statement.setString(++i,username); // username
            statement.setString(++i,username); // userID
            statement.setString(++i,username); // email
            statement.setString(++i,username); // phone
            statement.setString(++i,password); // password


//            statement.setString(++i,username); // username
//            statement.setString(++i,username); // userID


            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
//                user.setPassword(rs.getString(User.PASSWORD));
                user.setEmail(rs.getString(User.E_MAIL));
                user.setEmailVerified(rs.getBoolean(User.IS_EMAIL_VERIFIED));
                user.setPhone(rs.getString(User.PHONE));
//                user.setPhoneVerified(rs.getBoolean(User.IS_PHONE_VERIFIED));
                user.setName(rs.getString(User.NAME));
                user.setGender(rs.getBoolean(User.GENDER));
                user.setProfileImageURL(rs.getString(User.PROFILE_IMAGE_URL));
//                user.setRole(rs.getInt(User.ROLE));
                user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                user.setAbout(rs.getString(User.ABOUT));

//                user.setToken(rs.getString(User.TOKEN));
//                user.setTimestampTokenExpires(rs.getTimestamp(User.TIMESTAMP_TOKEN_EXPIRES));

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



    public boolean checkUsernameExists(String username)
    {

        String query = "SELECT " + User.USERNAME
                + " FROM " + User.TABLE_NAME
                + " WHERE " + User.USERNAME + " = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        System.out.println("Checked Username : " + username);

//		ShopAdmin shopAdmin = null;



        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            statement.setObject(1,username);

            rs = statement.executeQuery();


            while(rs.next())
            {

                return true;
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

        return false;
    }







    public User checkGoogleID(String googleID) {


        String query = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
                + User.PASSWORD + ","
                + User.E_MAIL + ","
                + User.IS_EMAIL_VERIFIED + ","
                + User.PHONE + ","
                + User.NAME + ","
                + User.GENDER + ","
                + User.PROFILE_IMAGE_URL + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE " + User.GOOGLE_ID + " = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        System.out.println("Checked Google ID : " + googleID);


        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            statement.setObject(1,googleID);

            rs = statement.executeQuery();


            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
                user.setPassword(rs.getString(User.PASSWORD));
                user.setEmail(rs.getString(User.E_MAIL));
                user.setEmailVerified(rs.getBoolean(User.IS_EMAIL_VERIFIED));
                user.setPhone(rs.getString(User.PHONE));
                user.setName(rs.getString(User.NAME));
                user.setGender(rs.getBoolean(User.GENDER));
                user.setProfileImageURL(rs.getString(User.PROFILE_IMAGE_URL));
                user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                user.setAbout(rs.getString(User.ABOUT));
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




    public int saveGoogleProfile(User user, boolean getRowCount)
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

                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.GOOGLE_ID + ","

                + User.TOKEN + ","
                + User.TIMESTAMP_TOKEN_EXPIRES + ","

                + User.ABOUT + ""


                + ") VALUES(?,?,? ,?,?,? ,?,?,?,?  ,?,?, ?)";


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
            statement.setObject(++i,user.isGender());

            statement.setString(++i,user.getProfileImageURL());
            statement.setObject(++i,user.getRole());
            statement.setObject(++i,user.isAccountPrivate());

            statement.setString(++i,user.getGoogleID());
            statement.setString(++i,user.getToken());
            statement.setTimestamp(++i,user.getTimestampTokenExpires());

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


}
