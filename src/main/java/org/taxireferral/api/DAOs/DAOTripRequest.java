package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.CurrentTrip;
import org.taxireferral.api.Model.TripRequest;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelEndpoints.TripRequestEndPoint;
import org.taxireferral.api.ModelRoles.EmailVerificationCode;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.ModelUtility.Location;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sumeet on 3/7/17.
 */
public class DAOTripRequest {


    private HikariDataSource dataSource = Globals.getDataSource();

    // create trip request
    // set_request_approved
            // - extend expiry to few more minutes
    // request_pick_up
            // getTripStatus - not required

    // approvePickup - by driver - deletes the request and copy request into current trip
        // - copy trip request into current trip
        // - delete the trip request
        // - update the taxi vehicle status to occupied
        // - notify driver about status update


    // getTripRequests - for end user
    // getTripRequests - for driver

    // checkTripRequestExists(int endUserID, int vehicleID)

    // checkTripRequestStatus(int tripRequestID,int endUserID)
    // checkTripRequestStatusForDriver(int tripRequestID,int driverID)




    /* PENDING METHODS */

    // deleteExpiredRequests - deletes all the expired requests in the table - triggered every day or every week or on certain events




    public TripRequest checkTripRequestStatus(int tripRequestID, int endUserID)
    {

        String query =
                        " SELECT "
                        + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_ID + ","
                        + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_STATUS + ""
                        + " FROM "   + TripRequest.TABLE_NAME
                        + " WHERE "  + TripRequest.TRIP_REQUEST_ID + " = ? "
                        + " AND "    + TripRequest.END_USER_ID + " = ? "
                        + " AND "    + TripRequest.TIMESTAMP_EXPIRES + " > now()";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        TripRequest tripRequest = null;

        try {

//            System.out.println(query);

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setObject(++i,tripRequestID);
            statement.setObject(++i,endUserID);


            rs = statement.executeQuery();

            while(rs.next())
            {
                tripRequest = new TripRequest();
                tripRequest.setTripRequestID(rs.getInt(TripRequest.TRIP_REQUEST_ID));
                tripRequest.setTripRequestStatus(rs.getInt(TripRequest.TRIP_REQUEST_STATUS));

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

        return tripRequest;
    }






    public TripRequest checkTripRequestStatusForDriver(int tripRequestID, int driverID)
    {

        String query =
                  " SELECT "
                  + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_ID + ","
                  + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_STATUS + ""
                + " FROM "   + TripRequest.TABLE_NAME
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                + " INNER JOIN " + User.TABLE_NAME + " ON (" + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE "  + TripRequest.TRIP_REQUEST_ID + " = ? "
                + " AND "    + User.USER_ID + " = ? "
                + " AND "    + TripRequest.TIMESTAMP_EXPIRES + " > now()";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        TripRequest tripRequest = null;

        try {

//            System.out.println(query);

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setObject(++i,tripRequestID);
            statement.setObject(++i,driverID);


            rs = statement.executeQuery();

            while(rs.next())
            {
                tripRequest = new TripRequest();



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

        return tripRequest;
    }





    public boolean checkTripRequestExists(int endUserID, int vehicleID)
    {


        String query = "SELECT " + TripRequest.TRIP_REQUEST_ID + ""
                + " FROM "   + TripRequest.TABLE_NAME
                + " WHERE "  + TripRequest.END_USER_ID + " = ? "
                + " AND "    + TripRequest.VEHICLE_ID + " = ? "
                + " AND "    + TripRequest.TIMESTAMP_EXPIRES + " > now()";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        try {

//            System.out.println(query);

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setObject(++i,endUserID);
            statement.setObject(++i,vehicleID);

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



    public int approve_pickup(int tripRequestID, int driverID, boolean getRowCount, Location currentLocation)
    {
        // note of precaution : please check the end user id is the id of the user who is requesting to create the trip

        Connection connection = null;
        PreparedStatement statementInsert = null;
        PreparedStatement statementDelete = null;
        PreparedStatement statementUpdate = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        int rowCountDelete = -1;
        int rowCountUpdateStatus = -1;


        String deleteTripRequest = "";
        String updateStatus = "";


        String insert = "";

        insert = "INSERT INTO " + CurrentTrip.TABLE_NAME
                + "("
                + CurrentTrip.VEHICLE_ID + ","
                + CurrentTrip.END_USER_ID + ","

                + CurrentTrip.CURRENT_TRIP_STATUS + ","

                + CurrentTrip.LAT_START_LOCATION + ","
                + CurrentTrip.LON_START_LOCATION + ","

                + CurrentTrip.FREE_PICKUP_DISTANCE + ","
                + CurrentTrip.REFERRAL_CHARGES + ","

                + CurrentTrip.MIN_TRIP_CHARGES + ","
                + CurrentTrip.CHARGES_PER_KM + ""
                + ") "
                + " SELECT "
                + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.END_USER_ID + ","

                + GlobalConstants.PICKUP_APPROVED + ","

                + currentLocation.getLatitude() + ","
                + currentLocation.getLongitude() + ","

                + GlobalConstants.free_pickup_distance + ","
                + GlobalConstants.taxi_referral_charges + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.MIN_TRIP_CHARGES + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.CHARGES_PER_KM + ""

                + " FROM " + TripRequest.TABLE_NAME
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON " + "(" + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                + " INNER JOIN " + User.TABLE_NAME + " ON (" + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID  + " )"
                + " WHERE " + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_ID + " = ? "
                + " AND " + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_STATUS + " = ? "
                + " AND " + User.TABLE_NAME + "." + User.USER_ID + " = ?";



        deleteTripRequest =   " DELETE FROM " + TripRequest.TABLE_NAME
                            + " WHERE " + TripRequest.TRIP_REQUEST_ID + " = ?";


        updateStatus =  " UPDATE " + Vehicle.TABLE_NAME +
                        " SET " + Vehicle.VEHICLE_STATUS + " = ? " +
                        " FROM " + User.TABLE_NAME +
                        " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID +
                        " AND " + User.USER_ID + " = ? ";



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            statementInsert = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementInsert.setObject(++i,tripRequestID);
            statementInsert.setObject(++i,GlobalConstants.PICKUP_REQUESTED);
            statementInsert.setObject(++i,driverID);

            rowCountItems = statementInsert.executeUpdate();
            ResultSet rs = statementInsert.getGeneratedKeys();
            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }


            if(idOfInsertedRow > 0)
            {
                // execute the following statements only if the insert is successful


                statementDelete = connection.prepareStatement(deleteTripRequest);
                i = 0;

                statementDelete.setObject(++i,tripRequestID);
                rowCountDelete = statementDelete.executeUpdate();



                statementUpdate = connection.prepareStatement(updateStatus);
                i = 0;

                statementUpdate.setObject(++i,GlobalConstants.OCCUPIED);
                statementUpdate.setObject(++i,driverID);

                rowCountUpdateStatus = statementUpdate.executeUpdate();
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



            if (statementDelete != null) {
                try {
                    statementDelete.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


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

        if(getRowCount)
        {
            return rowCountItems;
        }
        else
        {
            return idOfInsertedRow;
        }
    }








    public int create_trip_request(TripRequest tripRequest, boolean getRowCount)
    {
        // note of precaution : please check the end user id is the id of the user who is requesting to create the trip

        Connection connection = null;
        PreparedStatement statementInsert = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;


        String insert = "";


        insert = "INSERT INTO "
                + TripRequest.TABLE_NAME
                + "("

                + TripRequest.VEHICLE_ID + ","
                + TripRequest.END_USER_ID + ","

                + TripRequest.TIMESTAMP_EXPIRES + ","
                + TripRequest.TRIP_REQUEST_STATUS + ","

                + TripRequest.LAT_PICK_UP + ","
                + TripRequest.LON_PICK_UP + ","
                + TripRequest.PICK_UP_ADDRESS + ","

                + TripRequest.LAT_DESTINATION + ","
                + TripRequest.LON_DESTINATION + ","
                + TripRequest.DESTINATION_ADDRESS + ","

                + TripRequest.ADULTS_MALES_COUNT + ","
                + TripRequest.ADULTS_FEMALES_COUNT + ","
                + TripRequest.CHILDREN_COUNT + ""

                + ") "
                + "VALUES(?,? ,?,? ,?,?,? ,?,?,? ,?,?,?)";



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            Timestamp timestampExpiry = new Timestamp(System.currentTimeMillis() + GlobalConstants.TRIP_REQUEST_EXPIRY_MINUTES * 60 * 1000);
            tripRequest.setTimestampExpires(timestampExpiry);


            statementInsert = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementInsert.setObject(++i,tripRequest.getVehicleID());
            statementInsert.setObject(++i,tripRequest.getEndUserID());

            statementInsert.setTimestamp(++i,tripRequest.getTimestampExpires());
            statementInsert.setObject(++i,GlobalConstants.TAXI_REQUESTED);

            statementInsert.setObject(++i,tripRequest.getLatPickUp());
            statementInsert.setObject(++i,tripRequest.getLonPickUp());
            statementInsert.setString(++i,tripRequest.getPickUpAddress());

            statementInsert.setObject(++i,tripRequest.getLatDestination());
            statementInsert.setObject(++i,tripRequest.getLonDestination());
            statementInsert.setString(++i,tripRequest.getDestinationAddress());

            statementInsert.setObject(++i,tripRequest.getAdultMalesCount());
            statementInsert.setObject(++i,tripRequest.getAdultFemalesCount());
            statementInsert.setObject(++i,tripRequest.getChildrenCount());

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








    public int set_request_approved(int tripRequestID, int driverID)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";


        Timestamp timestampExpiry = new Timestamp(System.currentTimeMillis() + GlobalConstants.TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES * 60 * 1000);



        update =  " UPDATE " + TripRequest.TABLE_NAME
                + " SET "
                + TripRequest.TRIP_REQUEST_STATUS + " = " + GlobalConstants.REQUEST_APPROVED + ","
                + TripRequest.TIMESTAMP_EXPIRES + " = ?"
                + " FROM "   + Vehicle.TABLE_NAME
                + " WHERE "  + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID
                + " AND "    + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ? "
                + " AND "    + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_ID + " = ?"
                + " AND "    + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_STATUS + " = " + GlobalConstants.TAXI_REQUESTED;





//        + " FROM "   + Vehicle.TABLE_NAME

//        + " AND "    + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID
//                + " AND "    + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ? "

//        + " AND " + TripRequest.VEHICLE_ID + " = (SELECT " + Vehicle.VEHICLE_ID + " FROM " + Vehicle.TABLE_NAME + " WHERE " + Vehicle.DRIVER_ID + " =?)"



        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementUpdate.setTimestamp(++i,timestampExpiry);
            statementUpdate.setObject(++i,driverID);
            statementUpdate.setObject(++i,tripRequestID);

            rowCountItems = statementUpdate.executeUpdate();


//            connection.commit();

        } catch (SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

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






    public int request_pick_up(int tripRequestID, int endUserID)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + TripRequest.TABLE_NAME
                + " SET " + TripRequest.TRIP_REQUEST_STATUS + " = " + GlobalConstants.PICKUP_REQUESTED
                + " WHERE " + TripRequest.TRIP_REQUEST_ID + " = ?"
                + " AND " + TripRequest.END_USER_ID + " = ?"
                + " AND " + TripRequest.TRIP_REQUEST_STATUS + " = " + GlobalConstants.REQUEST_APPROVED;




        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setObject(++i,tripRequestID);
            statementUpdate.setObject(++i,endUserID);

            rowCountItems = statementUpdate.executeUpdate();


//            connection.commit();

        } catch (SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

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








    public TripRequestEndPoint getTripRequests(
            Integer endUserID,
            Integer vehicleID,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata)
    {


        boolean isfirst = true;

        String queryCount = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


        String queryJoin = "SELECT DISTINCT "

                + "6371 * acos( cos( radians("
                + TripRequest.LAT_PICK_UP + ")) * cos( radians(" +  Vehicle.LAT_CURRENT +  ") ) * cos(radians(" + Vehicle.LON_CURRENT +  ") - radians("
                + TripRequest.LON_PICK_UP + "))"
                + " + sin( radians(" + TripRequest.LAT_PICK_UP + ")) * sin(radians(" + Vehicle.LAT_CURRENT + "))) as distance" + ","


                + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_ID + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.END_USER_ID + ","

                + TripRequest.TABLE_NAME + "." + TripRequest.TIMESTAMP_CREATED + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.TIMESTAMP_EXPIRES + ","

                + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_STATUS + ","

                + TripRequest.TABLE_NAME + "." + TripRequest.LAT_PICK_UP + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.LON_PICK_UP + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.PICK_UP_ADDRESS + ","

                + TripRequest.TABLE_NAME + "." + TripRequest.LAT_DESTINATION + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.LON_DESTINATION + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.DESTINATION_ADDRESS + ","

                + TripRequest.TABLE_NAME + "." + TripRequest.ADULTS_MALES_COUNT + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.ADULTS_FEMALES_COUNT + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.CHILDREN_COUNT + ","


                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.PROFILE_IMAGE_URL + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.MIN_TRIP_CHARGES + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.CHARGES_PER_KM + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.LAT_CURRENT + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.LON_CURRENT + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.TIMESTAMP_LOCATION_UPDATED + ","

                + User.TABLE_NAME + "." + User.PHONE + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ""

                + " FROM " + TripRequest.TABLE_NAME
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                + " INNER JOIN " + User.TABLE_NAME + " ON (" + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED + " = TRUE "
                + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.AVIALABLE
                + " AND " + TripRequest.TABLE_NAME + "." + TripRequest.TIMESTAMP_EXPIRES + " > now()";




        if(endUserID != null)
        {
            queryJoin = queryJoin + " AND " + TripRequest.TABLE_NAME + "." + TripRequest.END_USER_ID + " = ?";
        }



        if(vehicleID != null)
        {
            queryJoin = queryJoin + " AND " + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + " = ?";
        }
//



        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ","
                + User.TABLE_NAME + "." + User.USER_ID;


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


        TripRequestEndPoint endPoint = new TripRequestEndPoint();

        ArrayList<TripRequest> itemList = new ArrayList<>();
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

                if(endUserID != null)
                {
                    statement.setObject(++i,endUserID);
                }

                if(vehicleID != null)
                {
                    statement.setObject(++i,vehicleID);
                }

                rs = statement.executeQuery();

                while(rs.next())
                {

                    TripRequest tripRequest = new TripRequest();

                    tripRequest.setTripRequestID(rs.getInt(TripRequest.TRIP_REQUEST_ID));
                    tripRequest.setVehicleID(rs.getInt(TripRequest.VEHICLE_ID));
                    tripRequest.setEndUserID(rs.getInt(TripRequest.END_USER_ID));

                    tripRequest.setTimestampCreated(rs.getTimestamp(TripRequest.TIMESTAMP_CREATED));
                    tripRequest.setTimestampExpires(rs.getTimestamp(TripRequest.TIMESTAMP_EXPIRES));

                    tripRequest.setTripRequestStatus(rs.getInt(TripRequest.TRIP_REQUEST_STATUS));

                    tripRequest.setLatPickUp(rs.getFloat(TripRequest.LAT_PICK_UP));
                    tripRequest.setLonPickUp(rs.getFloat(TripRequest.LON_PICK_UP));
                    tripRequest.setPickUpAddress(rs.getString(TripRequest.PICK_UP_ADDRESS));

                    tripRequest.setLatDestination(rs.getFloat(TripRequest.LAT_DESTINATION));
                    tripRequest.setLonDestination(rs.getFloat(TripRequest.LON_DESTINATION));
                    tripRequest.setDestinationAddress(rs.getString(TripRequest.DESTINATION_ADDRESS));

                    tripRequest.setAdultMalesCount(rs.getInt(TripRequest.ADULTS_MALES_COUNT));
                    tripRequest.setAdultFemalesCount(rs.getInt(TripRequest.ADULTS_FEMALES_COUNT));
                    tripRequest.setChildrenCount(rs.getInt(TripRequest.CHILDREN_COUNT));


                    Vehicle vehicle = new Vehicle();

                    vehicle.setRt_distance(rs.getFloat("distance"));

                    vehicle.setVehicleID(rs.getInt(Vehicle.VEHICLE_ID));
                    vehicle.setDriverID(rs.getInt(Vehicle.DRIVER_ID));
                    vehicle.setProfileImageURL(rs.getString(Vehicle.PROFILE_IMAGE_URL));

                    vehicle.setVehicleStatus(rs.getInt(Vehicle.VEHICLE_STATUS));

                    vehicle.setMinTripCharges(rs.getInt(Vehicle.MIN_TRIP_CHARGES));
                    vehicle.setChargesPerKM(rs.getInt(Vehicle.CHARGES_PER_KM));

                    vehicle.setLatCurrent(rs.getFloat(Vehicle.LAT_CURRENT));
                    vehicle.setLonCurrent(rs.getFloat(Vehicle.LON_CURRENT));

                    vehicle.setLocationUpdated(rs.getTimestamp(Vehicle.TIMESTAMP_LOCATION_UPDATED));

                    User driver = new User();

                    driver.setUserID(vehicle.getDriverID());
                    driver.setPhone(rs.getString(User.PHONE));
                    driver.setName(rs.getString(User.NAME));
                    driver.setGender(rs.getBoolean(User.GENDER));
                    driver.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));

                    vehicle.setRt_driver(driver);

                    tripRequest.setRt_vehicle(vehicle);
                    itemList.add(tripRequest);
                }

                endPoint.setResults(itemList);

            }


            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;



                if(endUserID != null)
                {
                    statementCount.setObject(++i,endUserID);
                }

                if(vehicleID != null)
                {
                    statementCount.setObject(++i,vehicleID);
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



    public TripRequestEndPoint getTripRequestsForDriver(
            Integer endUserID,
            Integer vehicleID,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata)
    {


        boolean isfirst = true;

        String queryCount = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


        String queryJoin = "SELECT DISTINCT "

                + "6371 * acos( cos( radians("
                + TripRequest.LAT_PICK_UP + ")) * cos( radians(" +  Vehicle.LAT_CURRENT +  ") ) * cos(radians(" + Vehicle.LON_CURRENT +  ") - radians("
                + TripRequest.LON_PICK_UP + "))"
                + " + sin( radians(" + TripRequest.LAT_PICK_UP + ")) * sin(radians(" + Vehicle.LAT_CURRENT + "))) as distance" + ","


                + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_ID + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.END_USER_ID + ","

                + TripRequest.TABLE_NAME + "." + TripRequest.TIMESTAMP_CREATED + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.TIMESTAMP_EXPIRES + ","

                + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_STATUS + ","

                + TripRequest.TABLE_NAME + "." + TripRequest.LAT_PICK_UP + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.LON_PICK_UP + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.PICK_UP_ADDRESS + ","

                + TripRequest.TABLE_NAME + "." + TripRequest.LAT_DESTINATION + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.LON_DESTINATION + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.DESTINATION_ADDRESS + ","

                + TripRequest.TABLE_NAME + "." + TripRequest.ADULTS_MALES_COUNT + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.ADULTS_FEMALES_COUNT + ","
                + TripRequest.TABLE_NAME + "." + TripRequest.CHILDREN_COUNT + ","


                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.PROFILE_IMAGE_URL + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.MIN_TRIP_CHARGES + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.CHARGES_PER_KM + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.LAT_CURRENT + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.LON_CURRENT + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.TIMESTAMP_LOCATION_UPDATED + ","

                + User.TABLE_NAME + "." + User.PHONE + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ""

                + " FROM " + TripRequest.TABLE_NAME
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                + " INNER JOIN " + User.TABLE_NAME + " ON (" + TripRequest.TABLE_NAME + "." + TripRequest.END_USER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED + " = TRUE "
                + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.AVIALABLE
                + " AND " + TripRequest.TABLE_NAME + "." + TripRequest.TIMESTAMP_EXPIRES + " > now()";




        if(endUserID != null)
        {
            queryJoin = queryJoin + " AND " + TripRequest.TABLE_NAME + "." + TripRequest.END_USER_ID + " = ?";
        }



        if(vehicleID != null)
        {
            queryJoin = queryJoin + " AND " + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + " = ?";
        }
//



        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + TripRequest.TABLE_NAME + "." + TripRequest.TRIP_REQUEST_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ","
                + User.TABLE_NAME + "." + User.USER_ID;


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


        TripRequestEndPoint endPoint = new TripRequestEndPoint();

        ArrayList<TripRequest> itemList = new ArrayList<>();
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

                if(endUserID != null)
                {
                    statement.setObject(++i,endUserID);
                }

                if(vehicleID != null)
                {
                    statement.setObject(++i,vehicleID);
                }

                rs = statement.executeQuery();

                while(rs.next())
                {

                    TripRequest tripRequest = new TripRequest();

                    tripRequest.setTripRequestID(rs.getInt(TripRequest.TRIP_REQUEST_ID));
                    tripRequest.setVehicleID(rs.getInt(TripRequest.VEHICLE_ID));
                    tripRequest.setEndUserID(rs.getInt(TripRequest.END_USER_ID));

                    tripRequest.setTimestampCreated(rs.getTimestamp(TripRequest.TIMESTAMP_CREATED));
                    tripRequest.setTimestampExpires(rs.getTimestamp(TripRequest.TIMESTAMP_EXPIRES));

                    tripRequest.setTripRequestStatus(rs.getInt(TripRequest.TRIP_REQUEST_STATUS));

                    tripRequest.setLatPickUp(rs.getFloat(TripRequest.LAT_PICK_UP));
                    tripRequest.setLonPickUp(rs.getFloat(TripRequest.LON_PICK_UP));
                    tripRequest.setPickUpAddress(rs.getString(TripRequest.PICK_UP_ADDRESS));

                    tripRequest.setLatDestination(rs.getFloat(TripRequest.LAT_DESTINATION));
                    tripRequest.setLonDestination(rs.getFloat(TripRequest.LON_DESTINATION));
                    tripRequest.setDestinationAddress(rs.getString(TripRequest.DESTINATION_ADDRESS));

                    tripRequest.setAdultMalesCount(rs.getInt(TripRequest.ADULTS_MALES_COUNT));
                    tripRequest.setAdultFemalesCount(rs.getInt(TripRequest.ADULTS_FEMALES_COUNT));
                    tripRequest.setChildrenCount(rs.getInt(TripRequest.CHILDREN_COUNT));


                    Vehicle vehicle = new Vehicle();

                    vehicle.setRt_distance(rs.getFloat("distance"));

                    vehicle.setVehicleID(rs.getInt(Vehicle.VEHICLE_ID));
                    vehicle.setDriverID(rs.getInt(Vehicle.DRIVER_ID));
                    vehicle.setProfileImageURL(rs.getString(Vehicle.PROFILE_IMAGE_URL));

                    vehicle.setVehicleStatus(rs.getInt(Vehicle.VEHICLE_STATUS));

                    vehicle.setMinTripCharges(rs.getInt(Vehicle.MIN_TRIP_CHARGES));
                    vehicle.setChargesPerKM(rs.getInt(Vehicle.CHARGES_PER_KM));

                    vehicle.setLatCurrent(rs.getFloat(Vehicle.LAT_CURRENT));
                    vehicle.setLonCurrent(rs.getFloat(Vehicle.LON_CURRENT));

                    vehicle.setLocationUpdated(rs.getTimestamp(Vehicle.TIMESTAMP_LOCATION_UPDATED));

                    User endUser = new User();

                    endUser.setUserID(vehicle.getDriverID());
                    endUser.setPhone(rs.getString(User.PHONE));
                    endUser.setName(rs.getString(User.NAME));
                    endUser.setGender(rs.getBoolean(User.GENDER));
                    endUser.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));

                    tripRequest.setRt_end_user(endUser);

                    tripRequest.setRt_vehicle(vehicle);
                    itemList.add(tripRequest);
                }

                endPoint.setResults(itemList);

            }


            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;



                if(endUserID != null)
                {
                    statementCount.setObject(++i,endUserID);
                }

                if(vehicleID != null)
                {
                    statementCount.setObject(++i,vehicleID);
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
