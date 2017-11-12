package org.taxireferral.api.DAOBilling;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelBilling.UPIPaymentRequest;

import java.sql.*;
import java.util.Calendar;

public class DAOUPIPayments {

    private HikariDataSource dataSource = Globals.getDataSource();





    public int createPaymentRequest(UPIPaymentRequest request, boolean getRowCount)
    {
        Connection connection = null;
        PreparedStatement statementInsert = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;


        String insert = "";


        insert = "INSERT INTO "
                + UPIPaymentRequest.TABLE_NAME
                + "("

                + UPIPaymentRequest.USER_ID + ","
                + UPIPaymentRequest.DESCRIPTION + ","
                + UPIPaymentRequest.AMOUNT + ","
                + UPIPaymentRequest.ACCOUNT_TYPE + ""
                + ") "
                + "VALUES(?,?, ?,?)";




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementInsert = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementInsert.setObject(++i,request.getUserID());
            statementInsert.setString(++i,request.getDescription());

            statementInsert.setObject(++i,request.getAmount());
            statementInsert.setObject(++i,request.getAccountType());


            rowCountItems = statementInsert.executeUpdate();
            ResultSet rs = statementInsert.getGeneratedKeys();

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


            if (statementInsert != null) {
                try {
                    statementInsert.close();
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







    public int updateStatus(UPIPaymentRequest request)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + UPIPaymentRequest.TABLE_NAME
                + " SET " + UPIPaymentRequest.STATUS + " = ?"
                + " WHERE " + UPIPaymentRequest.UPI_PAYMENT_ID + " = ?";




        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementUpdate.setObject(++i,request.getStatus());
            statementUpdate.setObject(++i,request.getUpiPaymentID());

            rowCountItems = statementUpdate.executeUpdate();
//            connection.commit();

        } catch (SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

//            if (connection != null) {
//                try {
//
//                    idOfInsertedRow=-1;
//                    rowCountItems = 0;
//
//                    connection.rollback();
//                } catch (SQLException e1) {
//                    e1.printStackTrace();
//                }
//            }
        }
        finally
        {

            if (statementUpdate != null) {
                try {
                    statementUpdate.close();
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



        return rowCountItems;
    }





    public int approvePaymentRequest(UPIPaymentRequest request)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;
        PreparedStatement statementUpdateUser = null;
        PreparedStatement statementCreateTransactionTax = null;
        PreparedStatement statementCreateTransactionService = null;


        int rowCountItems = -1;

        String updateRequest = "";
        String updateUser = "";
        String createTransactionTAX = "";
        String createTransactionSERVICE = "";



        updateRequest =  " UPDATE " + UPIPaymentRequest.TABLE_NAME
                        + " SET "   + UPIPaymentRequest.IS_APPROVED + " = ?,"
                                    + UPIPaymentRequest.REMARKS_AND_FEEDBACK + " = ?"
                        + " WHERE " + UPIPaymentRequest.UPI_PAYMENT_ID + " = ?";





        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(updateRequest,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementUpdate.setObject(++i,request.getStatus());
            statementUpdate.setObject(++i,request.getUpiPaymentID());

            rowCountItems = statementUpdate.executeUpdate();
//            connection.commit();

        } catch (SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

//            if (connection != null) {
//                try {
//
//                    idOfInsertedRow=-1;
//                    rowCountItems = 0;
//
//                    connection.rollback();
//                } catch (SQLException e1) {
//                    e1.printStackTrace();
//                }
//            }
        }
        finally
        {

            if (statementUpdate != null) {
                try {
                    statementUpdate.close();
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



        return rowCountItems;
    }











}
