package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.TripRequest;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelEndpoints.TripRequestEndPoint;
import org.taxireferral.api.ModelRoles.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sumeet on 9/7/17.
 */
public class DAOTripHistory {


    private HikariDataSource dataSource = Globals.getDataSource();


    // getTripHistoryForEndUser()
    // getTripHistoryForDriver()
    // rateAndReviewForDriver()
    // rateAndReviewForEndUser()




    public TripRequestEndPoint getTripHistoryForEndUser(
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



    public TripRequestEndPoint getTripHistoryForDriver(
            Integer endUserID,
            Integer driverID,
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
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + " as user_profile_image"

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



        if(driverID != null)
        {
            queryJoin = queryJoin + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ?";
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

                if(driverID != null)
                {
                    statement.setObject(++i,driverID);
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
                    endUser.setProfileImagePath(rs.getString("user_profile_image"));


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

                if(driverID != null)
                {
                    statementCount.setObject(++i,driverID);
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
