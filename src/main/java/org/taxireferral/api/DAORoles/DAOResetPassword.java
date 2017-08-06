package org.taxireferral.api.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelRoles.User;

import java.math.BigInteger;
import java.sql.*;

/**
 * Created by sumeet on 6/8/17.
 */
public class DAOResetPassword {


    private HikariDataSource dataSource = Globals.getDataSource();


    /* Functions for password reset code */

    // resetPassword
    // generateResetCode
    // checkPasswordResetCode




    public int resetPassword(User user)
    {

        String updateStatement =  " UPDATE " + User.TABLE_NAME
                                + " SET " + User.PASSWORD + "=?"
                                + " WHERE " + " ( " + User.E_MAIL + " = ?" + " OR " + User.PHONE + " = ? )"
                                + " AND " + User.PASSWORD_RESET_CODE + "=?"
                                + " AND " + User.RESET_CODE_EXPIRES  + " > now() ";



        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;


            statement.setString(++i,user.getPassword());
            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getPhone());
            statement.setString(++i,user.getPasswordResetCode());


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




    public int generateResetCode(User user,String resetCode, Timestamp timestampExpiry)
    {
//        String emailOrPhone


        String updateStatement =  " UPDATE " + User.TABLE_NAME
                                + " SET " + User.PASSWORD_RESET_CODE + "=?, "
                                          + User.RESET_CODE_EXPIRES + "=?"
                                + " WHERE " + User.E_MAIL + " = ?" + " OR " + User.PHONE + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;


            statement.setString(++i,resetCode);
            statement.setTimestamp(++i,timestampExpiry);

            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getPhone());


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





    public boolean checkPasswordResetCode(String emailOrPhone, String resetCode)
    {

        String query = "SELECT " + User.USER_ID + ""
                + " FROM "   + User.TABLE_NAME
                + " WHERE "  + " ( " + User.E_MAIL + " = ? " + " OR " + User.PHONE + " = ? )"
                + " AND "    + User.PASSWORD_RESET_CODE + " = ? "
                + " AND "    + User.TIMESTAMP_TOKEN_EXPIRES + " > now()";


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
            statement.setString(++i,emailOrPhone);
            statement.setString(++i,emailOrPhone);
            statement.setString(++i,resetCode);


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
}
