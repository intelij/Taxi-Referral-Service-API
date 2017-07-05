package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.CurrentTrip;
import org.taxireferral.api.Model.TripRequest;
import org.taxireferral.api.Model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by sumeet on 4/7/17.
 */
public class DAOCurrentTrip {

    private HikariDataSource dataSource = Globals.getDataSource();


    // start_trip_by_driver - notifies the end_user
    // start_trip_by_end_user - notifies the driver
    // approve_start_by driver
            // - updates the pick_up_distance
            // - updates the pick_up_location and address
            // - updates timestamp started
    // approve_start_by_end_user - similar to above except for current status check
            // - updates the pick_up_distance
            // - updates the pick_up_location and address
            // - updates the timestamp started
    // finish_trip - copy the current trip into the trip history and then deletes the current trip
            // - updates the distance travelled for trip
            // - updates the destination_coordinates and address
            // - updates timestamp finished
            // - copies the current trip into the trip history table
            // - deletes the trip details in current trip table



    public int start_trip_by_driver(int currentTripID,int driverID)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + CurrentTrip.TABLE_NAME
                + " SET "    + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.START_JOURNEY_REQUESTED_BY_DRIVER
                + " FROM "   + Vehicle.TABLE_NAME
                + " WHERE "  + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_ID + " = ? "
                + " AND "    + CurrentTrip.TABLE_NAME + "." + CurrentTrip.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID
                + " AND "    + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ? "
                + " AND "    + CurrentTrip.TABLE_NAME + "." + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.PICKUP_APPROVED;


        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


//            statementUpdate.setObject(++i,status);
            statementUpdate.setObject(++i,currentTripID);
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





    public int start_trip_by_end_user(int currentTripID, int endUserID)
    {
        // please note the end user can be of any role including the user registered as driver, staff or admin

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + CurrentTrip.TABLE_NAME
                + " SET " + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.START_JOURNEY_REQUESTED_BY_END_USER + ""
                + " WHERE " + CurrentTrip.CURRENT_TRIP_ID + " = ?"
                + " AND " + CurrentTrip.END_USER_ID + " = ?"
                + " AND " + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.PICKUP_APPROVED;


        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementUpdate.setObject(++i,currentTripID);
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




    public int approve_start_by_driver(CurrentTrip currentTrip)
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
                + CurrentTrip.TIMESTAMP_STARTED + " = now(),"
                + " WHERE " + CurrentTrip.CURRENT_TRIP_ID + " = ?"
                + " AND " + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.START_JOURNEY_REQUESTED_BY_END_USER;


        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setObject(++i,currentTrip.getDistanceTravelledForPickup());
            statementUpdate.setObject(++i,currentTrip.getLatPickUpLocation());
            statementUpdate.setObject(++i,currentTrip.getLonPickUpLocation());
            statementUpdate.setString(++i,currentTrip.getPickUpAddress());
            statementUpdate.setObject(++i,currentTrip.getCurrentTripID());

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





    public int approve_start_by_end_user(CurrentTrip currentTrip)
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
                + " WHERE " + CurrentTrip.CURRENT_TRIP_ID + " = ?"
                + " AND " + CurrentTrip.CURRENT_TRIP_STATUS + " = " + GlobalConstants.START_JOURNEY_REQUESTED_BY_DRIVER
                + " AND " + CurrentTrip.END_USER_ID + " = ?";


        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setObject(++i,currentTrip.getDistanceTravelledForPickup());
            statementUpdate.setObject(++i,currentTrip.getLatPickUpLocation());
            statementUpdate.setObject(++i,currentTrip.getLonPickUpLocation());
            statementUpdate.setString(++i,currentTrip.getPickUpAddress());

            statementUpdate.setObject(++i,currentTrip.getCurrentTripID());
            statementUpdate.setObject(++i,currentTrip.getEndUserID());

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














}
