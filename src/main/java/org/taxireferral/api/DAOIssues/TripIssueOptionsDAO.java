package org.taxireferral.api.DAOIssues;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelIssues.IssueOptionsEndpoint;
import org.taxireferral.api.ModelIssues.TripIssueOption;
import org.taxireferral.api.ModelImages.TaxiImage;
import org.taxireferral.api.ModelImages.TaxiImageEndpoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TripIssueOptionsDAO {

    private HikariDataSource dataSource = Globals.getDataSource();



    public int insert(TripIssueOption option, boolean getRowCount)
    {


        Connection connection = null;
        PreparedStatement statement = null;
        int idOfInsertedRow = -1;
        int rowCount = 0;




        String insert = "INSERT INTO "
                + TripIssueOption.TABLE_NAME
                + "("

                + TripIssueOption.TRIP_OPTION_ID + ","
                + TripIssueOption.OPTION_TEXT + ","
                + TripIssueOption.OPTION_ORDER + ""

                + ") VALUES(?,?,? )";


        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);

            int i = 0;
            statement.setObject(++i,option.getTripOptionID());
            statement.setString(++i,option.getOptionText());
            statement.setObject(++i,option.getOptionOrder());



            rowCount = statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally
        {

            try {

                if(statement!=null)
                {statement.close();}

            }
            catch (SQLException e) {
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


        if(getRowCount)
        {
            return rowCount;
        }
        else
        {
            return idOfInsertedRow;
        }

    }






    public int update(TripIssueOption option)
    {

        String updateStatement = "UPDATE " + TripIssueOption.TABLE_NAME

                + " SET "

                + TripIssueOption.OPTION_TEXT + "=?,"
                + TripIssueOption.OPTION_ORDER + "=?"

                + " WHERE " + TripIssueOption.TRIP_OPTION_ID + "=?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);



            int i = 0;
            statement.setString(++i,option.getOptionText());
            statement.setObject(++i,option.getOptionOrder());
            statement.setObject(++i,option.getTripOptionID());

            rowCountUpdated = statement.executeUpdate();
            System.out.println("TripIssueOption :  rows updated: " + rowCountUpdated);


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





    public int deleteOption(int optionID)
    {

        String deleteStatement = " DELETE FROM " + TripIssueOption.TABLE_NAME +
                                 " WHERE " + TripIssueOption.TRIP_OPTION_ID + " = ?";

        Connection connection= null;
        PreparedStatement statement = null;
        int rowCountDeleted = 0;
        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteStatement);
            int i = 0;
            statement.setInt(++i,optionID);

            rowCountDeleted = statement.executeUpdate();

            System.out.println("Rows Deleted: " + rowCountDeleted);

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

        return rowCountDeleted;
    }






    public IssueOptionsEndpoint getOptions(
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata
    ) {

        String queryCount = "";


        String queryJoin = "SELECT "

                + TripIssueOption.TABLE_NAME + "." + TripIssueOption.TRIP_OPTION_ID + ","
                + TripIssueOption.TABLE_NAME + "." + TripIssueOption.OPTION_TEXT + ","
                + TripIssueOption.TABLE_NAME + "." + TripIssueOption.OPTION_ORDER + ""

                + " FROM " + TripIssueOption.TABLE_NAME
                + " WHERE TRUE ";


        queryCount = queryJoin;



        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

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


            queryJoin = queryJoin + queryPartLimitOffset;
        }









		/*

		Applying filters Ends

		 */

        // Applying filters




        queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";


        IssueOptionsEndpoint endPoint = new IssueOptionsEndpoint();

        ArrayList<TripIssueOption> itemList = new ArrayList<>();
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


                rs = statement.executeQuery();

                while(rs.next())
                {

                    TripIssueOption tripIssueOption = new TripIssueOption();

                    tripIssueOption.setTripOptionID(rs.getInt(TripIssueOption.TRIP_OPTION_ID));
                    tripIssueOption.setOptionText(rs.getString(TripIssueOption.OPTION_TEXT));
                    tripIssueOption.setOptionOrder(rs.getInt(TripIssueOption.OPTION_ORDER));

                    itemList.add(tripIssueOption);
                }

                endPoint.setResults(itemList);

            }


            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;

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
