package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.CurrentTrip;
import org.taxireferral.api.Model.TripHistory;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.ModelUtility.LocationCurrentTrip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sumeet on 4/7/17.
 */
public class DAOCurrentTrip {

    private HikariDataSource dataSource = Globals.getDataSource();




    // finish_trip - copy the current trip into the trip history and then deletes the current trip
                    // - updates the distance travelled for trip
                    // - updates the destination_coordinates and address
                    // - updates timestamp finished

            // - copies the current trip into the trip history table
            // - deletes the trip details in current trip table
            // - update the taxi vehicle status to available
            // - notify driver about status update


    // start_trip_by_driver - notifies the end_user
    // start_trip_by_end_user - notifies the driver

    // approve_start_by driver
            // - updates the pick_up_distance
            // - updates the pick_up_location and address
            // - updates timestamp started
            // - notify the end-user

    // approve_start_by_end_user - similar to above except for current status check
            // - updates the pick_up_distance
            // - updates the pick_up_location and address
            // - updates the timestamp started
            // - notify the driver



    // getCurrentTripEndUser(int endUserID)
    // getCurrentTripDriver(int driverID)
    // getStatusCurrentTripDriver(int driverID)




    /* PENDING */

    // cancelTripByDriver(int driverID,int currentTripID)
    // cancelTripByEndUser(int endUserID,int currentTripID)

    /* To be reviewed */

    // GetCurrentTrip - fetches the list of current trips







//    int currentTripID





    public int update_location_and_distance(int driverID, LocationCurrentTrip locationCurrentTrip)
    {

        Connection connection = null;

        PreparedStatement statementUpdateLocation = null;
        PreparedStatement statementUpdateDistance = null;

        int rowCountUpdateLocation = -1;
        int rowCountUpdateDistance = -1;

        String updateLocation = "";
        String updateDistance = "";



        updateLocation = " UPDATE " + Vehicle.TABLE_NAME +

                        " SET "
                        + Vehicle.LAT_CURRENT + "=?,"
                        + Vehicle.LON_CURRENT + "=?,"
                        + Vehicle.TIMESTAMP_LOCATION_UPDATED + "= now()"

                        + " FROM " + User.TABLE_NAME
                        + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID
                        + " AND " + User.TABLE_NAME + "." + User.USER_ID + " = ?";


        updateDistance = " UPDATE " + CurrentTrip.TABLE_NAME +

                " SET "
                + CurrentTrip.DISTANCE_TRAVELLED_FOR_PICKUP + " = " + CurrentTrip.DISTANCE_TRAVELLED_FOR_PICKUP + " + ?,"
                + CurrentTrip.DISTANCE_TRAVELLED_FOR_TRIP + " = " + CurrentTrip.DISTANCE_TRAVELLED_FOR_TRIP + " + ?"

                + " FROM " + Vehicle.TABLE_NAME
                + " WHERE " + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID
                + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ?";




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            statementUpdateLocation = connection.prepareStatement(updateLocation);
            int i = 0;

            statementUpdateLocation.setObject(++i,locationCurrentTrip.getLatitude());
            statementUpdateLocation.setObject(++i,locationCurrentTrip.getLongitude());
            statementUpdateLocation.setObject(++i,driverID);

            rowCountUpdateLocation = statementUpdateLocation.executeUpdate();



            statementUpdateDistance = connection.prepareStatement(updateDistance);
            i = 0;

            statementUpdateLocation.setObject(++i,locationCurrentTrip.getDistancePickupIncrement());
            statementUpdateLocation.setObject(++i,locationCurrentTrip.getDistanceTripIncrement());
            statementUpdateLocation.setObject(++i,driverID);

            rowCountUpdateLocation = statementUpdateLocation.executeUpdate();


            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {


                    rowCountUpdateLocation = 0;
                    rowCountUpdateDistance = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {


            if (statementUpdateLocation != null) {
                try {
                    statementUpdateLocation.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }



            if (statementUpdateDistance != null) {
                try {
                    statementUpdateDistance.close();
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


        return rowCountUpdateLocation;
    }







    public int finish_trip(int driverID, boolean getRowCount, TripHistory tripHistory)
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

        insert = "INSERT INTO " + TripHistory.TABLE_NAME
                + "("

                + TripHistory.VEHICLE_ID + ","
                + TripHistory.END_USER_ID + ","

                + TripHistory.TIMESTAMP_CREATED + ","
                + TripHistory.TIMESTAMP_STARTED + ","
                + TripHistory.TIMESTAMP_FINISHED + ","

                + TripHistory.LAT_START_LOCATION + ","
                + TripHistory.LON_START_LOCATION + ","

                + TripHistory.LAT_PICK_UP_LOCATION + ","
                + TripHistory.LON_PICK_UP_LOCATION + ","
                + TripHistory.PICK_UP_ADDRESS + ","

                + TripHistory.LAT_DESTINATION + ","
                + TripHistory.LON_DESTINATION + ","
                + TripHistory.DESTINATION_ADDRESS + ","

                + TripHistory.DISTANCE_TRAVELLED_FOR_PICKUP + ","
                + TripHistory.DISTANCE_TRAVELLED_FOR_TRIP + ","

                + TripHistory.FREE_PICKUP_DISTANCE + ","
                + TripHistory.REFERRAL_CHARGES + ","

                + TripHistory.MIN_TRIP_CHARGES + ","
                + TripHistory.CHARGES_PER_KM + ""

                + ") "
                + " SELECT "

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.END_USER_ID + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.TIMESTAMP_CREATED + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.TIMESTAMP_STARTED + ","
                + " now(),"

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LAT_START_LOCATION + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LON_START_LOCATION + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LAT_PICK_UP_LOCATION + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LON_PICK_UP_LOCATION + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.PICK_UP_ADDRESS + ","

                + tripHistory.getLatDestination() + ","
                + tripHistory.getLonDestination() + ","
                + tripHistory.getDestinationAddress() + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.DISTANCE_TRAVELLED_FOR_PICKUP + ","
                + tripHistory.getDistanceTravelledForTrip() + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.FREE_PICKUP_DISTANCE + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.REFERRAL_CHARGES + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.MIN_TRIP_CHARGES + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CHARGES_PER_KM + ""

                + " FROM " + CurrentTrip.TABLE_NAME
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON " + "(" + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ?";


//        + " WHERE " + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_ID + " = ? "

        deleteTripRequest =   " DELETE FROM " + CurrentTrip.TABLE_NAME
                            + " USING " + Vehicle.TABLE_NAME
                            + " WHERE " + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID
                            + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ?";

//                            + CurrentTrip.TABLE_NAME
//                            + " WHERE " + CurrentTrip.CURRENT_TRIP_ID + " = ?";


        updateStatus =  " UPDATE " + Vehicle.TABLE_NAME +
                        " SET " + Vehicle.VEHICLE_STATUS + " = ? " +
                        " WHERE " + Vehicle.DRIVER_ID + " = ? ";



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            statementInsert = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

//            statementInsert.setObject(++i,currentTripID);
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

                statementDelete.setObject(++i,driverID);
                rowCountDelete = statementDelete.executeUpdate();



                statementUpdate = connection.prepareStatement(updateStatus);
                i = 0;

                statementUpdate.setObject(++i,GlobalConstants.AVIALABLE);
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










//    int currentTripID,
    public int start_trip_by_driver(int driverID)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";


        update =  " UPDATE " + CurrentTrip.TABLE_NAME
                + " SET "    + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.START_JOURNEY_REQUESTED_BY_DRIVER
                + " FROM "   + Vehicle.TABLE_NAME
                + " WHERE "  + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID
                + " AND "    + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ? "
                + " AND "    + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.PICKUP_APPROVED;

//        + " AND "
//        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_ID + " = ? "



        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


//            statementUpdate.setObject(++i,status);
//            statementUpdate.setObject(++i,currentTripID);
            statementUpdate.setObject(++i,driverID);

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





    public int start_trip_by_end_user(int endUserID)
    {
        // please note the end user can be of any role including the user registered as driver, staff or admin

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + CurrentTrip.TABLE_NAME
                + " SET " + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.START_JOURNEY_REQUESTED_BY_END_USER + ""
                + " WHERE " +  CurrentTrip.END_USER_ID + " = ?"
                + " AND " + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.PICKUP_APPROVED;


//        + CurrentTrip.CURRENT_TRIP_ID + " = ?" " AND " +
//            statementUpdate.setObject(++i,currentTripID);


        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

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




    public int approve_start_by_driver(CurrentTrip currentTrip, int driverID)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";


        update =  " UPDATE " + CurrentTrip.TABLE_NAME
                + " SET "
                + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.START_APPROVED_AND_TRIP_STARTED + ","
                + CurrentTrip.DISTANCE_TRAVELLED_FOR_PICKUP + " = ?,"
                + CurrentTrip.LAT_PICK_UP_LOCATION + " = ?,"
                + CurrentTrip.LON_PICK_UP_LOCATION + " = ?,"
                + CurrentTrip.PICK_UP_ADDRESS + " = ?,"
                + CurrentTrip.TIMESTAMP_STARTED + " = now()"
                + " FROM "   + Vehicle.TABLE_NAME
                + " WHERE "  + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID
                + " AND "    + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ? "
                + " AND "    + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.START_JOURNEY_REQUESTED_BY_END_USER;

//        + " AND "
//        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_ID + " = ? "

//                + " WHERE " + CurrentTrip.CURRENT_TRIP_ID + " = ?"



        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setObject(++i,currentTrip.getDistanceTravelledForPickup());
            statementUpdate.setObject(++i,currentTrip.getLatPickUpLocation());
            statementUpdate.setObject(++i,currentTrip.getLonPickUpLocation());
            statementUpdate.setString(++i,currentTrip.getPickUpAddress());

//            statementUpdate.setObject(++i,currentTrip.getCurrentTripID());
            statementUpdate.setObject(++i,driverID);

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





    public int approve_start_by_end_user(CurrentTrip currentTrip, int endUserID)
    {
        // please ensure that if of user making the request is same as id of end user in the current trip

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + CurrentTrip.TABLE_NAME
                + " SET "
                + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.START_APPROVED_AND_TRIP_STARTED + ","
                + CurrentTrip.DISTANCE_TRAVELLED_FOR_PICKUP + " = ?,"
                + CurrentTrip.LAT_PICK_UP_LOCATION + " = ?,"
                + CurrentTrip.LON_PICK_UP_LOCATION + " = ?,"
                + CurrentTrip.PICK_UP_ADDRESS + " = ?,"
                + CurrentTrip.TIMESTAMP_STARTED + " = now(),"
                + " WHERE " + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.START_JOURNEY_REQUESTED_BY_DRIVER
                + " AND " + CurrentTrip.END_USER_ID + " = ?";


//       + " AND "  + CurrentTrip.CURRENT_TRIP_ID + " = ?"

        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setObject(++i,currentTrip.getDistanceTravelledForPickup());
            statementUpdate.setObject(++i,currentTrip.getLatPickUpLocation());
            statementUpdate.setObject(++i,currentTrip.getLonPickUpLocation());
            statementUpdate.setString(++i,currentTrip.getPickUpAddress());


//            statementUpdate.setObject(++i,currentTrip.getCurrentTripID());
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






    public CurrentTrip getCurrentTripForEndUser(int endUserID)
    {

        String query = " SELECT "
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_ID + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.END_USER_ID + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_STATUS + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.TIMESTAMP_CREATED + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.TIMESTAMP_STARTED + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.TIMESTAMP_FINISHED + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LAT_START_LOCATION + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LON_START_LOCATION + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LAT_PICK_UP_LOCATION + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LON_PICK_UP_LOCATION + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.PICK_UP_ADDRESS + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LAT_DESTINATION + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LON_DESTINATION + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.DESTINATION_ADDRESS + ","


                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.DISTANCE_TRAVELLED_FOR_PICKUP + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.DISTANCE_TRAVELLED_FOR_TRIP + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.FREE_PICKUP_DISTANCE + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.REFERRAL_CHARGES + ","

                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.MIN_TRIP_CHARGES + ","
                + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CHARGES_PER_KM + ","

                + User.TABLE_NAME + "." + User.PHONE + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ""

                + " FROM "   + CurrentTrip.TABLE_NAME
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                + " INNER JOIN " + User.TABLE_NAME + " ON (" + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE "  + CurrentTrip.TABLE_NAME + "." + CurrentTrip.END_USER_ID + " = ? ";



//        + " AND "    + User.USER_ID + " = ? "

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        CurrentTrip currentTrip = null;

        try {

//            System.out.println(query);

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setObject(++i,endUserID);


            rs = statement.executeQuery();

            while(rs.next())
            {
                currentTrip = new CurrentTrip();
                currentTrip.setCurrentTripID(rs.getInt(CurrentTrip.CURRENT_TRIP_ID));
                currentTrip.setVehicleID(rs.getInt(CurrentTrip.VEHICLE_ID));
                currentTrip.setEndUserID(rs.getInt(CurrentTrip.END_USER_ID));

                currentTrip.setCurrentTripStatus(rs.getInt(CurrentTrip.CURRENT_TRIP_STATUS));

                currentTrip.setTimestampCreated(rs.getTimestamp(CurrentTrip.TIMESTAMP_CREATED));
                currentTrip.setTimestampStarted(rs.getTimestamp(CurrentTrip.TIMESTAMP_STARTED));
                currentTrip.setTimestampFinished(rs.getTimestamp(CurrentTrip.TIMESTAMP_FINISHED));

                currentTrip.setLatStartLocation(rs.getDouble(CurrentTrip.LAT_START_LOCATION));
                currentTrip.setLonStartLocation(rs.getDouble(CurrentTrip.LON_START_LOCATION));

                currentTrip.setLatPickUpLocation(rs.getDouble(CurrentTrip.LAT_PICK_UP_LOCATION));
                currentTrip.setLonPickUpLocation(rs.getDouble(CurrentTrip.LON_PICK_UP_LOCATION));
                currentTrip.setPickUpAddress(rs.getString(CurrentTrip.PICK_UP_ADDRESS));

                currentTrip.setLatDestination(rs.getDouble(CurrentTrip.LAT_DESTINATION));
                currentTrip.setLonDestination(rs.getDouble(CurrentTrip.LON_DESTINATION));
                currentTrip.setDestinationAddress(rs.getString(CurrentTrip.DESTINATION_ADDRESS));

                currentTrip.setDistanceTravelledForPickup(rs.getDouble(CurrentTrip.DISTANCE_TRAVELLED_FOR_PICKUP));
                currentTrip.setDistanceTravelledForTrip(rs.getDouble(CurrentTrip.DISTANCE_TRAVELLED_FOR_TRIP));

                currentTrip.setFreePickUpDistance(rs.getDouble(CurrentTrip.FREE_PICKUP_DISTANCE));
                currentTrip.setReferralCharges(rs.getDouble(CurrentTrip.REFERRAL_CHARGES));

                currentTrip.setMinTripCharges(rs.getDouble(CurrentTrip.MIN_TRIP_CHARGES));
                currentTrip.setChargesPerKm(rs.getDouble(CurrentTrip.CHARGES_PER_KM));
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

        return currentTrip;
    }






    public CurrentTrip getCurrentTripStatusForDriver(int driverID)
    {

        String query =
                " SELECT "
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_ID + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_STATUS + ""

                        + " FROM "   + CurrentTrip.TABLE_NAME
                        + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                        + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ? ";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        CurrentTrip currentTrip = null;

        try {

//            System.out.println(query);

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;

            statement.setObject(++i,driverID);


            rs = statement.executeQuery();

            while(rs.next())
            {
                currentTrip = new CurrentTrip();
                currentTrip.setCurrentTripID(rs.getInt(CurrentTrip.CURRENT_TRIP_ID));
                currentTrip.setCurrentTripStatus(rs.getInt(CurrentTrip.CURRENT_TRIP_STATUS));
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

        return currentTrip;
    }




    public CurrentTrip getCurrentTripForDriver(int driverID)
    {

        String query =
                " SELECT "
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_ID + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.END_USER_ID + ","

                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_STATUS + ","

                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.TIMESTAMP_CREATED + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.TIMESTAMP_STARTED + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.TIMESTAMP_FINISHED + ","

                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LAT_START_LOCATION + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LON_START_LOCATION + ","

                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LAT_PICK_UP_LOCATION + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LON_PICK_UP_LOCATION + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.PICK_UP_ADDRESS + ","

                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LAT_DESTINATION + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.LON_DESTINATION + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.DESTINATION_ADDRESS + ","


                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.DISTANCE_TRAVELLED_FOR_PICKUP + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.DISTANCE_TRAVELLED_FOR_TRIP + ","

                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.FREE_PICKUP_DISTANCE + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.REFERRAL_CHARGES + ","

                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.MIN_TRIP_CHARGES + ","
                        + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CHARGES_PER_KM + ","

                        + User.TABLE_NAME + "." + User.PHONE + ","
                        + User.TABLE_NAME + "." + User.NAME + ","
                        + User.TABLE_NAME + "." + User.GENDER + ","
                        + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ""

                        + " FROM "   + CurrentTrip.TABLE_NAME
                        + " INNER JOIN " + User.TABLE_NAME + " ON (" + User.TABLE_NAME + "." + User.USER_ID + " = " + CurrentTrip.TABLE_NAME + "." + CurrentTrip.END_USER_ID + ")"
                        + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                        + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ? ";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        CurrentTrip currentTrip = null;

        try {

//            System.out.println(query);

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;

            statement.setObject(++i,driverID);


            rs = statement.executeQuery();

            while(rs.next())
            {
                currentTrip = new CurrentTrip();

                currentTrip.setCurrentTripID(rs.getInt(CurrentTrip.CURRENT_TRIP_ID));
                currentTrip.setVehicleID(rs.getInt(CurrentTrip.VEHICLE_ID));
                currentTrip.setEndUserID(rs.getInt(CurrentTrip.END_USER_ID));

                currentTrip.setCurrentTripStatus(rs.getInt(CurrentTrip.CURRENT_TRIP_STATUS));

                currentTrip.setTimestampCreated(rs.getTimestamp(CurrentTrip.TIMESTAMP_CREATED));
                currentTrip.setTimestampStarted(rs.getTimestamp(CurrentTrip.TIMESTAMP_STARTED));
                currentTrip.setTimestampFinished(rs.getTimestamp(CurrentTrip.TIMESTAMP_FINISHED));

                currentTrip.setLatStartLocation(rs.getDouble(CurrentTrip.LAT_START_LOCATION));
                currentTrip.setLonStartLocation(rs.getDouble(CurrentTrip.LON_START_LOCATION));

                currentTrip.setLatPickUpLocation(rs.getDouble(CurrentTrip.LAT_PICK_UP_LOCATION));
                currentTrip.setLonPickUpLocation(rs.getDouble(CurrentTrip.LON_PICK_UP_LOCATION));
                currentTrip.setPickUpAddress(rs.getString(CurrentTrip.PICK_UP_ADDRESS));

                currentTrip.setLatDestination(rs.getDouble(CurrentTrip.LAT_DESTINATION));
                currentTrip.setLonDestination(rs.getDouble(CurrentTrip.LON_DESTINATION));
                currentTrip.setDestinationAddress(rs.getString(CurrentTrip.DESTINATION_ADDRESS));

                currentTrip.setDistanceTravelledForPickup(rs.getDouble(CurrentTrip.DISTANCE_TRAVELLED_FOR_PICKUP));
                currentTrip.setDistanceTravelledForTrip(rs.getDouble(CurrentTrip.DISTANCE_TRAVELLED_FOR_TRIP));

                currentTrip.setFreePickUpDistance(rs.getDouble(CurrentTrip.FREE_PICKUP_DISTANCE));
                currentTrip.setReferralCharges(rs.getDouble(CurrentTrip.REFERRAL_CHARGES));

                currentTrip.setMinTripCharges(rs.getDouble(CurrentTrip.MIN_TRIP_CHARGES));
                currentTrip.setChargesPerKm(rs.getDouble(CurrentTrip.CHARGES_PER_KM));


                User endUser = new User();

                endUser.setUserID(currentTrip.getEndUserID());
                endUser.setPhone(rs.getString(User.PHONE));
                endUser.setName(rs.getString(User.NAME));
                endUser.setGender(rs.getBoolean(User.GENDER));
                endUser.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));

                currentTrip.setRt_end_user(endUser);
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

        return currentTrip;
    }





}
