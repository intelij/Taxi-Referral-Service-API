package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.VehicleType;
import org.taxireferral.api.Model.VehicleTypeVersion;
import org.taxireferral.api.ModelEndpoints.VehicleTypeEndPoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sumeet on 27/4/17.
 */
public class VehicleTypeDAOGet {

    private HikariDataSource dataSource = Globals.getDataSource();



    public VehicleTypeEndPoint getVehicleType(
            Integer submittedBy,
            Boolean parentNULL,
            Integer parentID,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount
    ) {


        boolean isfirst = true;



        String queryCount = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


        String queryJoin = "SELECT DISTINCT "

                + VehicleType.TABLE_NAME + "." + VehicleType.VEHICLE_TYPE_ID + ","
                + VehicleType.TABLE_NAME + "." + VehicleType.NAME + ","
                + VehicleType.TABLE_NAME + "." + VehicleType.IMAGE_PATH + ","
                + VehicleType.TABLE_NAME + "." + VehicleType.DESCRIPTION + ""

                + " FROM " + VehicleType.TABLE_NAME
                + " LEFT OUTER JOIN " + VehicleTypeVersion.TABLE_NAME
                + " ON (" + VehicleType.TABLE_NAME + "." + VehicleType.VEHICLE_TYPE_ID + " = " + VehicleTypeVersion.TABLE_NAME + "." + VehicleTypeVersion.PARENT + ")";



        if(submittedBy != null)
        {
            queryJoin = queryJoin
                    + " WHERE "
                    + VehicleTypeVersion.TABLE_NAME
                    + "."
                    + VehicleTypeVersion.SUBMITTED_BY + " = ?";

            isfirst = false;
        }



        if(parentNULL!=null && parentNULL)
        {

            String queryPartStatus = VehicleTypeVersion.TABLE_NAME + "." + VehicleTypeVersion.PARENT + " IS NULL ";

            if(isfirst)
            {
                queryJoin = queryJoin + " WHERE " + queryPartStatus;

                isfirst = false;
            }
            else
            {
                queryJoin = queryJoin + " AND " + queryPartStatus;
            }

        }



        if(parentID !=null)
        {

            String queryPart = VehicleTypeVersion.TABLE_NAME + "." + VehicleTypeVersion.PARENT + " = ? ";

            if(isfirst)
            {
                queryJoin = queryJoin + " WHERE " + queryPart;

                isfirst = false;
            }
            else
            {
                queryJoin = queryJoin + " AND " + queryPart;
            }

        }






        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + VehicleType.TABLE_NAME + "." + VehicleType.VEHICLE_TYPE_ID ;


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


        VehicleTypeEndPoint endPoint = new VehicleTypeEndPoint();

        ArrayList<VehicleType> itemList = new ArrayList<VehicleType>();
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statementCount = null;

        ResultSet rs = null;
        ResultSet resultSetCount = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(queryJoin);

            int i = 0;
            if(submittedBy!=null)
            {
                statement.setInt(++i,submittedBy);
            }


            if(parentID!=null)
            {
                statement.setInt(++i,parentID);
            }


            rs = statement.executeQuery();

            while(rs.next())
            {

                VehicleType vehicleType = new VehicleType();

                vehicleType.setVehicleTypeID(rs.getInt(VehicleType.VEHICLE_TYPE_ID));
                vehicleType.setName(rs.getString(VehicleType.NAME));
                vehicleType.setImagePath(rs.getString(VehicleType.IMAGE_PATH));
                vehicleType.setDescription(rs.getString(VehicleType.DESCRIPTION));

                itemList.add(vehicleType);
            }




            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;
                if(submittedBy!=null)
                {
                    statementCount.setInt(++i,submittedBy);
                }


                if(parentID!=null)
                {
                    statementCount.setInt(++i,parentID);
                }


                resultSetCount = statementCount.executeQuery();

                while(resultSetCount.next())
                {
                    endPoint.setItemCount(resultSetCount.getInt("item_count"));
                }
            }


            endPoint.setResults(itemList);



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
