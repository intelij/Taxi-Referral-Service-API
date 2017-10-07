package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.TripHistory;
import org.taxireferral.api.Model.TripRequest;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelEndpoints.TripHistoryEndPoint;
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

    /* Function and Methods */

    // getTripHistoryForEndUser()
    // getTripHistoryForDriver()
    // rateAndReviewForDriver()
    // rateAndReviewForEndUser()

    

    public TripHistoryEndPoint getTripHistoryForEndUser(
            Integer endUserID,
            Integer vehicleID,
            Boolean isCancelled,
            Boolean isCancelledByEndUser,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata)
    {


        boolean isfirst = true;

        String queryCount = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


//        + "6371 * acos( cos( radians("
//                + TripRequest.LAT_PICK_UP + ")) * cos( radians(" +  Vehicle.LAT_CURRENT +  ") ) * cos(radians(" + Vehicle.LON_CURRENT +  ") - radians("
//                + TripRequest.LON_PICK_UP + "))"
//                + " + sin( radians(" + TripRequest.LAT_PICK_UP + ")) * sin(radians(" + Vehicle.LAT_CURRENT + "))) as distance" + ","


        String queryJoin = "SELECT DISTINCT "

                + TripHistory.TABLE_NAME + "." + TripHistory.TRIP_HISTORY_ID + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.VEHICLE_ID + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.END_USER_ID + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.RATING_BY_DRIVER + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.RATING_BY_END_USER + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.FEEDBACK_BY_DRIVER + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.FEEDBACK_BY_END_USER + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.IS_CANCELLED + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.IS_CANCELLED_BY_END_USER + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.REASON_FOR_CANCELLATION + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.TIMESTAMP_CREATED + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.TIMESTAMP_STARTED + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.TIMESTAMP_FINISHED + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.LAT_START_LOCATION + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.LON_START_LOCATION + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.LAT_PICK_UP_LOCATION + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.LON_PICK_UP_LOCATION + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.PICK_UP_ADDRESS + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.LAT_DESTINATION + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.LON_DESTINATION + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.DESTINATION_ADDRESS + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.DISTANCE_TRAVELLED_FOR_PICKUP + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.DISTANCE_TRAVELLED_FOR_TRIP + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.FREE_PICKUP_DISTANCE + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.REFERRAL_CHARGES + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.MIN_TRIP_CHARGES + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.CHARGES_PER_KM + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.FREE_START_WAITING_MINUTES + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.FREE_MINUTES_PER_KM + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.WAIT_CHARGES_PER_MINUTE + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.TAX_RATE + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.PROFILE_IMAGE_URL + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_MODEL_NAME + ","

                + User.TABLE_NAME + "." + User.PHONE + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ""

                + " FROM " + TripHistory.TABLE_NAME
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + TripHistory.TABLE_NAME + "." + TripHistory.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                + " INNER JOIN " + User.TABLE_NAME + " ON (" + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE " + TripHistory.TABLE_NAME + "." + TripHistory.END_USER_ID + " = ? ";




//
//        if(endUserID != null)
//        {
//            queryJoin = queryJoin + " AND " + TripHistory.TABLE_NAME + "." + TripHistory.END_USER_ID + " = ?";
//        }



        if(vehicleID != null)
        {
            queryJoin = queryJoin + " AND " + TripRequest.TABLE_NAME + "." + TripRequest.VEHICLE_ID + " = ?";
        }
//

        if(isCancelled != null)
        {
            queryJoin = queryJoin + " AND " + TripHistory.TABLE_NAME + "." + TripHistory.IS_CANCELLED + " = ?";
        }


        if(isCancelledByEndUser != null)
        {
            queryJoin = queryJoin + " AND " + TripHistory.TABLE_NAME + "." + TripHistory.IS_CANCELLED_BY_END_USER + " = ?";
        }






        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + TripHistory.TABLE_NAME + "." + TripHistory.TRIP_HISTORY_ID + ","
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


        TripHistoryEndPoint endPoint = new TripHistoryEndPoint();

        ArrayList<TripHistory> itemList = new ArrayList<>();
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

                statement.setObject(++i,endUserID);

//                if(endUserID != null)
//                {
//
//                }

                if(vehicleID != null)
                {
                    statement.setObject(++i,vehicleID);
                }

                if(isCancelled!=null)
                {
                    statement.setObject(++i,isCancelled);
                }


                if(isCancelledByEndUser!=null)
                {
                    statement.setObject(++i,isCancelledByEndUser);
                }



                rs = statement.executeQuery();

                while(rs.next())
                {

                    TripHistory tripHistory = new TripHistory();

                    tripHistory.setTripHistoryID(rs.getInt(TripHistory.TRIP_HISTORY_ID));
                    tripHistory.setVehicleID(rs.getInt(TripHistory.VEHICLE_ID));
                    tripHistory.setEndUserID(rs.getInt(TripHistory.END_USER_ID));

                    tripHistory.setRatingByDriver(rs.getInt(TripHistory.RATING_BY_DRIVER));
                    tripHistory.setRatingByEndUser(rs.getInt(TripHistory.RATING_BY_END_USER));

                    tripHistory.setFeedbackByDriver(rs.getString(TripHistory.FEEDBACK_BY_DRIVER));
                    tripHistory.setFeedbackByEndUser(rs.getString(TripHistory.FEEDBACK_BY_END_USER));

                    tripHistory.setCancelled(rs.getBoolean(TripHistory.IS_CANCELLED));
                    tripHistory.setCancelledByUser(rs.getBoolean(TripHistory.IS_CANCELLED_BY_END_USER));
                    tripHistory.setReasonForCancellation(rs.getString(TripHistory.REASON_FOR_CANCELLATION));

                    tripHistory.setTimestampCreated(rs.getTimestamp(TripHistory.TIMESTAMP_CREATED));
                    tripHistory.setTimestampStarted(rs.getTimestamp(TripHistory.TIMESTAMP_STARTED));
                    tripHistory.setTimestampFinished(rs.getTimestamp(TripHistory.TIMESTAMP_FINISHED));

                    tripHistory.setLatStartLocation(rs.getDouble(TripHistory.LAT_START_LOCATION));
                    tripHistory.setLonStartLocation(rs.getDouble(TripHistory.LON_START_LOCATION));

                    tripHistory.setLatPickUpLocation(rs.getDouble(TripHistory.LAT_PICK_UP_LOCATION));
                    tripHistory.setLonPickUpLocation(rs.getDouble(TripHistory.LON_PICK_UP_LOCATION));
                    tripHistory.setPickUpAddress(rs.getString(TripHistory.PICK_UP_ADDRESS));

                    tripHistory.setLatDestination(rs.getDouble(TripHistory.LAT_DESTINATION));
                    tripHistory.setLonDestination(rs.getDouble(TripHistory.LON_DESTINATION));
                    tripHistory.setDestinationAddress(rs.getString(TripHistory.DESTINATION_ADDRESS));

                    tripHistory.setDistanceTravelledForPickup(rs.getDouble(TripHistory.DISTANCE_TRAVELLED_FOR_PICKUP));
                    tripHistory.setDistanceTravelledForTrip(rs.getDouble(TripHistory.DISTANCE_TRAVELLED_FOR_TRIP));

                    tripHistory.setFreePickUpDistance(rs.getDouble(TripHistory.FREE_PICKUP_DISTANCE));
                    tripHistory.setReferralCharges(rs.getDouble(TripHistory.REFERRAL_CHARGES));

                    tripHistory.setMinTripCharges(rs.getDouble(TripHistory.MIN_TRIP_CHARGES));
                    tripHistory.setChargesPerKm(rs.getDouble(TripHistory.CHARGES_PER_KM));


                    tripHistory.setFreeStartWaitMinutes(rs.getInt(TripHistory.FREE_START_WAITING_MINUTES));
                    tripHistory.setFreeMinutesPerKm(rs.getInt(TripHistory.FREE_MINUTES_PER_KM));
                    tripHistory.setWaitingChargePerMinute(rs.getInt(TripHistory.WAIT_CHARGES_PER_MINUTE));
                    tripHistory.setTaxRate(rs.getInt(TripHistory.TAX_RATE));



                    Vehicle vehicle = new Vehicle();
//                    vehicle.setRt_distance(rs.getFloat("distance"));

                    vehicle.setVehicleID(rs.getInt(Vehicle.VEHICLE_ID));
                    vehicle.setDriverID(rs.getInt(Vehicle.DRIVER_ID));
                    vehicle.setProfileImageURL(rs.getString(Vehicle.PROFILE_IMAGE_URL));
                    vehicle.setVehicleModelName(rs.getString(Vehicle.VEHICLE_MODEL_NAME));


                    User driver = new User();

                    driver.setUserID(vehicle.getDriverID());
                    driver.setPhone(rs.getString(User.PHONE));
                    driver.setName(rs.getString(User.NAME));
                    driver.setGender(rs.getBoolean(User.GENDER));
                    driver.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));

                    vehicle.setRt_driver(driver);

                    tripHistory.setRt_vehicle(vehicle);
                    itemList.add(tripHistory);
                }

                endPoint.setResults(itemList);

            }


            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;

//                if(endUserID != null)
//                {
//
//                }

                statementCount.setObject(++i,endUserID);

                if(vehicleID != null)
                {
                    statementCount.setObject(++i,vehicleID);
                }



                if(isCancelled!=null)
                {
                    statementCount.setObject(++i,isCancelled);
                }


                if(isCancelledByEndUser!=null)
                {
                    statementCount.setObject(++i,isCancelledByEndUser);
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







//
//                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ","
//            + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + ","
//            + Vehicle.TABLE_NAME + "." + Vehicle.PROFILE_IMAGE_URL + ","
//            + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + ","
//
//            + Vehicle.TABLE_NAME + "." + Vehicle.MIN_TRIP_CHARGES + ","
//            + Vehicle.TABLE_NAME + "." + Vehicle.CHARGES_PER_KM + ","
//
//            + Vehicle.TABLE_NAME + "." + Vehicle.LAT_CURRENT + ","
//            + Vehicle.TABLE_NAME + "." + Vehicle.LON_CURRENT + ","
//            + Vehicle.TABLE_NAME + "." + Vehicle.TIMESTAMP_LOCATION_UPDATED + ","



    public TripHistoryEndPoint getTripHistoryForDriver(
            Integer endUserID,
            Integer driverID,
            Boolean isCancelled,
            Boolean isCancelledByEndUser,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata)
    {


        boolean isfirst = true;

        String queryCount = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


        String queryJoin = "SELECT DISTINCT "

                + TripHistory.TABLE_NAME + "." + TripHistory.TRIP_HISTORY_ID + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.VEHICLE_ID + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.END_USER_ID + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.RATING_BY_DRIVER + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.RATING_BY_END_USER + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.FEEDBACK_BY_DRIVER + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.FEEDBACK_BY_END_USER + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.IS_CANCELLED + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.IS_CANCELLED_BY_END_USER + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.REASON_FOR_CANCELLATION + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.TIMESTAMP_CREATED + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.TIMESTAMP_STARTED + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.TIMESTAMP_FINISHED + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.LAT_START_LOCATION + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.LON_START_LOCATION + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.LAT_PICK_UP_LOCATION + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.LON_PICK_UP_LOCATION + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.PICK_UP_ADDRESS + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.LAT_DESTINATION + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.LON_DESTINATION + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.DESTINATION_ADDRESS + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.DISTANCE_TRAVELLED_FOR_PICKUP + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.DISTANCE_TRAVELLED_FOR_TRIP + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.FREE_PICKUP_DISTANCE + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.REFERRAL_CHARGES + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.MIN_TRIP_CHARGES + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.CHARGES_PER_KM + ","

                + TripHistory.TABLE_NAME + "." + TripHistory.FREE_START_WAITING_MINUTES + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.FREE_MINUTES_PER_KM + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.WAIT_CHARGES_PER_MINUTE + ","
                + TripHistory.TABLE_NAME + "." + TripHistory.TAX_RATE + ","

                + User.TABLE_NAME + "." + User.USER_ID + ","
                + User.TABLE_NAME + "." + User.PHONE + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + " as user_profile_image"

                + " FROM " + TripHistory.TABLE_NAME
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + TripHistory.TABLE_NAME + "." + TripHistory.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                + " INNER JOIN " + User.TABLE_NAME + " ON (" + TripHistory.TABLE_NAME + "." + TripHistory.END_USER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ?";


//        + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED + " = TRUE "

        if(endUserID != null)
        {
            queryJoin = queryJoin + " AND " + TripHistory.TABLE_NAME + "." + TripHistory.END_USER_ID + " = ?";
        }


        if(isCancelled != null)
        {
            queryJoin = queryJoin + " AND " + TripHistory.TABLE_NAME + "." + TripHistory.IS_CANCELLED + " = ?";
        }


        if(isCancelledByEndUser != null)
        {
            queryJoin = queryJoin + " AND " + TripHistory.TABLE_NAME + "." + TripHistory.IS_CANCELLED_BY_END_USER + " = ?";
        }






//
//        if(driverID != null)
//        {
//            queryJoin = queryJoin + " AND " ;
//        }
////



        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + TripHistory.TABLE_NAME + "." + TripHistory.TRIP_HISTORY_ID + ","
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


        TripHistoryEndPoint endPoint = new TripHistoryEndPoint();

        ArrayList<TripHistory> itemList = new ArrayList<>();
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


                if(driverID != null)
                {
                    statement.setObject(++i,driverID);
                }

                if(endUserID != null)
                {
                    statement.setObject(++i,endUserID);
                }

                if(isCancelled!=null)
                {
                    statement.setObject(++i,isCancelled);
                }


                if(isCancelledByEndUser!=null)
                {
                    statement.setObject(++i,isCancelledByEndUser);
                }




                rs = statement.executeQuery();

                while(rs.next())
                {

                    TripHistory tripHistory = new TripHistory();

                    tripHistory.setTripHistoryID(rs.getInt(TripHistory.TRIP_HISTORY_ID));
                    tripHistory.setVehicleID(rs.getInt(TripHistory.VEHICLE_ID));
                    tripHistory.setEndUserID(rs.getInt(TripHistory.END_USER_ID));

                    tripHistory.setRatingByDriver(rs.getInt(TripHistory.RATING_BY_DRIVER));
                    tripHistory.setRatingByEndUser(rs.getInt(TripHistory.RATING_BY_END_USER));

                    tripHistory.setFeedbackByDriver(rs.getString(TripHistory.FEEDBACK_BY_DRIVER));
                    tripHistory.setFeedbackByEndUser(rs.getString(TripHistory.FEEDBACK_BY_END_USER));

                    tripHistory.setCancelled(rs.getBoolean(TripHistory.IS_CANCELLED));
                    tripHistory.setCancelledByUser(rs.getBoolean(TripHistory.IS_CANCELLED_BY_END_USER));
                    tripHistory.setReasonForCancellation(rs.getString(TripHistory.REASON_FOR_CANCELLATION));

                    tripHistory.setTimestampCreated(rs.getTimestamp(TripHistory.TIMESTAMP_CREATED));
                    tripHistory.setTimestampStarted(rs.getTimestamp(TripHistory.TIMESTAMP_STARTED));
                    tripHistory.setTimestampFinished(rs.getTimestamp(TripHistory.TIMESTAMP_FINISHED));

                    tripHistory.setLatStartLocation(rs.getDouble(TripHistory.LAT_START_LOCATION));
                    tripHistory.setLonStartLocation(rs.getDouble(TripHistory.LON_START_LOCATION));

                    tripHistory.setLatPickUpLocation(rs.getDouble(TripHistory.LAT_PICK_UP_LOCATION));
                    tripHistory.setLonPickUpLocation(rs.getDouble(TripHistory.LON_PICK_UP_LOCATION));
                    tripHistory.setPickUpAddress(rs.getString(TripHistory.PICK_UP_ADDRESS));

                    tripHistory.setLatDestination(rs.getDouble(TripHistory.LAT_DESTINATION));
                    tripHistory.setLonDestination(rs.getDouble(TripHistory.LON_DESTINATION));
                    tripHistory.setDestinationAddress(rs.getString(TripHistory.DESTINATION_ADDRESS));

                    tripHistory.setDistanceTravelledForPickup(rs.getDouble(TripHistory.DISTANCE_TRAVELLED_FOR_PICKUP));
                    tripHistory.setDistanceTravelledForTrip(rs.getDouble(TripHistory.DISTANCE_TRAVELLED_FOR_TRIP));

                    tripHistory.setFreePickUpDistance(rs.getDouble(TripHistory.FREE_PICKUP_DISTANCE));
                    tripHistory.setReferralCharges(rs.getDouble(TripHistory.REFERRAL_CHARGES));

                    tripHistory.setMinTripCharges(rs.getDouble(TripHistory.MIN_TRIP_CHARGES));
                    tripHistory.setChargesPerKm(rs.getDouble(TripHistory.CHARGES_PER_KM));


                    tripHistory.setFreeStartWaitMinutes(rs.getInt(TripHistory.FREE_START_WAITING_MINUTES));
                    tripHistory.setFreeMinutesPerKm(rs.getInt(TripHistory.FREE_MINUTES_PER_KM));
                    tripHistory.setWaitingChargePerMinute(rs.getInt(TripHistory.WAIT_CHARGES_PER_MINUTE));
                    tripHistory.setTaxRate(rs.getInt(TripHistory.TAX_RATE));


                    User endUser = new User();

                    endUser.setUserID(rs.getInt(User.USER_ID));
                    endUser.setPhone(rs.getString(User.PHONE));
                    endUser.setName(rs.getString(User.NAME));
                    endUser.setGender(rs.getBoolean(User.GENDER));
                    endUser.setProfileImagePath(rs.getString("user_profile_image"));

                    tripHistory.setRt_end_user(endUser);
                    itemList.add(tripHistory);
                }

                endPoint.setResults(itemList);

            }


            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;


                if(driverID != null)
                {
                    statementCount.setObject(++i,driverID);
                }


                if(endUserID != null)
                {
                    statementCount.setObject(++i,endUserID);
                }




                if(isCancelled!=null)
                {
                    statementCount.setObject(++i,isCancelled);
                }


                if(isCancelledByEndUser!=null)
                {
                    statementCount.setObject(++i,isCancelledByEndUser);
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
