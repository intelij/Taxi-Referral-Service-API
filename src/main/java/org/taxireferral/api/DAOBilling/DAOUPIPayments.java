package org.taxireferral.api.DAOBilling;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.TripHistory;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelBilling.Endpoints.UPIPaymentEndPoint;
import org.taxireferral.api.ModelBilling.Transaction;
import org.taxireferral.api.ModelBilling.TransactionTaxAccount;
import org.taxireferral.api.ModelBilling.UPIPaymentRequest;
import org.taxireferral.api.ModelEndpoints.VehicleEndPoint;
import org.taxireferral.api.ModelRoles.User;

import java.sql.*;
import java.util.ArrayList;
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







    public int approveTaxPaymentRequest(UPIPaymentRequest request)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;
        PreparedStatement statementUpdateUser = null;
        PreparedStatement statementCreateTransactionTax = null;


        int rowCountItems = -1;

        String updateRequest = "";
        String updateUser = "";
        String createTransactionTAX = "";


        updateUser =  " UPDATE " + User.TABLE_NAME
                + " SET "   + User.TAX_ACCOUNT_BALANCE + " = " + User.TABLE_NAME + "." + User.TAX_ACCOUNT_BALANCE + " + " + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.AMOUNT + ""
                + " FROM " + UPIPaymentRequest.TABLE_NAME
                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = " + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.USER_ID
                + " AND "   + UPIPaymentRequest.UPI_PAYMENT_ID + " = ?"
                + " AND " + UPIPaymentRequest.ACCOUNT_TYPE + " = " + UPIPaymentRequest.TAX_ACCOUNT
                + " AND " + UPIPaymentRequest.IS_APPROVED + " IS NULL ";




        updateRequest =  " UPDATE " + UPIPaymentRequest.TABLE_NAME
                        + " SET "   + UPIPaymentRequest.IS_APPROVED + " = true,"
                                    + UPIPaymentRequest.REMARKS_AND_FEEDBACK + " = ?"
                        + " WHERE " + UPIPaymentRequest.UPI_PAYMENT_ID + " = ?";




        createTransactionTAX = "INSERT INTO " + TransactionTaxAccount.TABLE_NAME
                + "("

                + TransactionTaxAccount.USER_ID + ","

                + TransactionTaxAccount.TITLE + ","
                + TransactionTaxAccount.DESCRIPTION + ","

                + TransactionTaxAccount.TRANSACTION_TYPE + ","
                + TransactionTaxAccount.TAX_AMOUNT + ","

                + TransactionTaxAccount.IS_CREDIT + ","

                + TransactionTaxAccount.TAX_BALANCE_AFTER_TRANSACTION + ""

                + ") "
                + " SELECT "

                + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.USER_ID + ","
                + " ' Credit Added : ' ,"
                + " ' Credit added into your account ',"

                + Transaction.TRANSACTION_TYPE_PAYMENT_MADE + ","
                + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.AMOUNT +  ","

                + " true " + ","
                + User.TABLE_NAME + "." + User.TAX_ACCOUNT_BALANCE + ""

                + " FROM " + User.TABLE_NAME
                + " INNER JOIN " + UPIPaymentRequest.TABLE_NAME + " ON ( " + UPIPaymentRequest.TABLE_NAME + "." +  UPIPaymentRequest.USER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + " ) "
                + " AND " + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.UPI_PAYMENT_ID + " = ? ";







        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);



            statementUpdateUser = connection.prepareStatement(updateUser,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementUpdateUser.setObject(++i,request.getUpiPaymentID());
            rowCountItems = rowCountItems + statementUpdateUser.executeUpdate();



            statementUpdate = connection.prepareStatement(updateRequest,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0;

            statementUpdate.setObject(++i,request.getRemarksAndFeedback());
            statementUpdate.setObject(++i,request.getUpiPaymentID());

            rowCountItems = statementUpdate.executeUpdate();




            statementCreateTransactionTax = connection.prepareStatement(createTransactionTAX,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0;

            statementCreateTransactionTax.setObject(++i,request.getUpiPaymentID());
            rowCountItems = rowCountItems + statementCreateTransactionTax.executeUpdate();



            connection.commit();

        } catch (SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

//                    idOfInsertedRow=-1;
                    rowCountItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
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


            if (statementUpdateUser != null) {
                try {
                    statementUpdateUser.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            if (statementCreateTransactionTax != null) {
                try {
                    statementCreateTransactionTax.close();
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











    public UPIPaymentEndPoint getUPIPaymentRequests(
            Integer status,
            String searchString,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata
    ) {


        String queryCount = "";


        String queryJoin = "SELECT "

                    + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.UPI_PAYMENT_ID + ","
                    + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.USER_ID + ","

                    + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.DESCRIPTION + ","
                    + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.STATUS + ","
                    + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.TIMESTAMP_CREATED + ","

                    + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.AMOUNT + ","
                    + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.ACCOUNT_TYPE + ","

                    + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.IS_APPROVED + ","
                    + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.REMARKS_AND_FEEDBACK + ","
                    + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.TIMESTAMP_APPROVED + ","
                    + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.APPROVED_BY_STAFF_ID + ""

                    + " FROM " + UPIPaymentRequest.TABLE_NAME
                    + " WHERE TRUE ";


        if(status!=null)
        {
            queryJoin = queryJoin + " AND " + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.STATUS + " = ?";
        }




        if(searchString !=null) {

            String queryPartSearch = " AND CAST ( " + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.UPI_PAYMENT_ID + " AS text )" + " ilike '%" + searchString + "%'" + " ";

            queryJoin = queryJoin + queryPartSearch;
        }

//





//         all the non-aggregate columns which are present in select must be present in group by also.
//        queryJoin = queryJoin
//                + " group by "
//                + UPIPaymentRequest.TABLE_NAME + "." + UPIPaymentRequest.UPI_PAYMENT_ID + "";


        queryCount = queryJoin;



        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

//				queryNormal = queryNormal + queryPartSortBy;
                queryJoin = queryJoin + queryPartSortBy;
            }
        }



        if(limit != null)
        {

            String queryPartLimitOffset = "";

            if(offset!=null)
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

            }else
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
            }


//			queryNormal = queryNormal + queryPartLimitOffset;
            queryJoin = queryJoin + queryPartLimitOffset;
        }









		/*

		Applying filters Ends

		 */

        // Applying filters




        queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";



        UPIPaymentEndPoint endPoint = new UPIPaymentEndPoint();

        ArrayList<UPIPaymentRequest> itemList = new ArrayList<>();
        Connection connection = null;

        PreparedStatement statement = null;
        ResultSet rs = null;

        PreparedStatement statementCount = null;
        ResultSet resultSetCount = null;

        try {

            connection = dataSource.getConnection();

            int i = 0;


            if(!getOnlyMetadata)
            {
                statement = connection.prepareStatement(queryJoin);

                if(status!=null)
                {
                    statement.setObject(++i,status);
                }



                rs = statement.executeQuery();

                while(rs.next())
                {
                    UPIPaymentRequest paymentRequest  = new UPIPaymentRequest();

                    paymentRequest.setUpiPaymentID(rs.getInt(UPIPaymentRequest.UPI_PAYMENT_ID));
                    paymentRequest.setUserID(rs.getInt(UPIPaymentRequest.USER_ID));
                    paymentRequest.setDescription(rs.getString(UPIPaymentRequest.DESCRIPTION));

                    paymentRequest.setStatus(rs.getInt(UPIPaymentRequest.STATUS));
                    paymentRequest.setTimestampCreated(rs.getTimestamp(UPIPaymentRequest.TIMESTAMP_CREATED));

                    paymentRequest.setAmount(rs.getInt(UPIPaymentRequest.AMOUNT));
                    paymentRequest.setAccountType(rs.getInt(UPIPaymentRequest.ACCOUNT_TYPE));

                    paymentRequest.setApproved(rs.getBoolean(UPIPaymentRequest.IS_APPROVED));
                    paymentRequest.setRemarksAndFeedback(rs.getString(UPIPaymentRequest.REMARKS_AND_FEEDBACK));
                    paymentRequest.setTimestampApproved(rs.getTimestamp(UPIPaymentRequest.TIMESTAMP_APPROVED));
                    paymentRequest.setApprovedBy(rs.getInt(UPIPaymentRequest.APPROVED_BY_STAFF_ID));

                    itemList.add(paymentRequest);
                }



                endPoint.setResults(itemList);

            }


            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;


                if(status!=null)
                {
                    statementCount.setObject(++i,status);
                }


                resultSetCount = statementCount.executeQuery();

                while(resultSetCount.next())
                {
                    endPoint.setItemCount(resultSetCount.getInt("item_count"));
                }
            }






        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        finally

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
                if(resultSetCount!=null)
                {resultSetCount.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statementCount!=null)
                {statementCount.close();}
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

        return endPoint;
    }



}
