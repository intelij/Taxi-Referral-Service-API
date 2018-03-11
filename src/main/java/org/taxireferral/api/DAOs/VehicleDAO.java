package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.TripHistory;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.Model.VehicleType;
import org.taxireferral.api.ModelBilling.Transaction;
import org.taxireferral.api.ModelEndpoints.VehicleEndPoint;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.ModelUtility.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by sumeet on 18/4/17.
 */
public class VehicleDAO {

    private HikariDataSource dataSource = Globals.getDataSource();

    // add / insert vehicle (Vehicle vehicle)
    // update vehicle (Vehicle vehicle)
    // update location (double lat, double lon)
    // updateStatus(int status) : update status for drivers

        /* Set Status for Taxi */
    // setTaxiAvailable(int userID)
    // setTaxiNotAvailable(int userID)

    // delete vehicle

    // getProfileByDriver(int driverID) : get vehicle details for driver ID
    // getVehicles(double latPickUp, double latPickUp, String searchString)
    // getTaxisForAdmin




    public int insert_vehicle(Vehicle vehicle, boolean getRowCount)
    {
        Connection connection = null;
        PreparedStatement statementInsert = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;


        String insert = "";


        insert = "INSERT INTO "
                + Vehicle.TABLE_NAME
                + "("

                + Vehicle.DRIVER_ID + ","
                + Vehicle.PROFILE_IMAGE_URL + ","

                + Vehicle.VEHICLE_MODEL_NAME  + ","
                + Vehicle.SEATING_CAPACITY    + ","

                + Vehicle.MIN_TRIP_CHARGES + ","
                + Vehicle.CHARGES_PER_KM + ""

                + ") "
                + "VALUES(?,?, ?,? ,?,?)";




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementInsert = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementInsert.setObject(++i,vehicle.getDriverID());
            statementInsert.setString(++i,vehicle.getProfileImageURL());

            statementInsert.setObject(++i,vehicle.getVehicleModelName());
            statementInsert.setObject(++i,vehicle.getSeatingCapacity());

            statementInsert.setObject(++i,vehicle.getMinTripCharges());
            statementInsert.setObject(++i,vehicle.getChargesPerKM());


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






    public int extend_registration(int vehicleID, int monthsToExtend)
    {

        if(monthsToExtend > GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MAX ||
                monthsToExtend < GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MIN)
        {
            return 0;
        }


        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(vehicle.getEnabledUpto().getTime());
        cal.add(Calendar.MONTH, monthsToExtend);


        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + Vehicle.TABLE_NAME
                + " SET " + Vehicle.ENABLED + " = TRUE ,"
                            + Vehicle.ENABLED_UPTO + " = ?"
                + " WHERE " + Vehicle.VEHICLE_ID + " = ?";




        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setTimestamp(++i,new Timestamp(cal.getTimeInMillis()));
            statementUpdate.setObject(++i,vehicleID);


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










    public int enableVehicleByAdmin(boolean enabled, int vehicleID)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        PreparedStatement statementUpdateDUES = null;
        PreparedStatement statementCreateTransaction = null;
        PreparedStatement statementReferrerCredited = null;

        int rowCountItems = -1;

        String update = "";

        String updateDUES = "";
        String createTransaction = "";
        String updateRefferCredited = "";


        update =  " UPDATE " + Vehicle.TABLE_NAME
                + " SET " + Vehicle.ENABLED + " = ? "
                + " WHERE " + Vehicle.VEHICLE_ID + " = ?";


        // add referral credit to the referrer if the referrer is not credited
        updateDUES =  " UPDATE " + User.TABLE_NAME
                + " SET "
                + User.SERVICE_ACCOUNT_BALANCE + " = " + User.SERVICE_ACCOUNT_BALANCE + " + ?"
//                + User.TOTAL_CREDITS + " = " + User.TOTAL_CREDITS + " + ?"
                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ( "
                + " SELECT " + User.REFERRED_BY
                + " FROM " + User.TABLE_NAME
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + User.TABLE_NAME + "." + User.USER_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + ")"
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + " = ? "
                + " AND " + User.TABLE_NAME + "." + User.IS_REFERRER_CREDITED + " = "  + " FALSE )";


//
//        createTransaction = "INSERT INTO " + Transaction.TABLE_NAME
//                + "("
//
//                + Transaction.USER_ID + ","
//
//                + Transaction.TITLE + ","
//                + Transaction.DESCRIPTION + ","
//
//                + Transaction.TRANSACTION_TYPE + ","
//                + Transaction.TRANSACTION_AMOUNT + ","
//
//                + Transaction.IS_CREDIT + ","
//
//                + Transaction.CURRENT_DUES_BEFORE_TRANSACTION + ","
//                + Transaction.SERVICE_BALANCE_AFTER_TRANSACTION + ""
//
//                + ") "
//                + " SELECT "
//
//                + User.TABLE_NAME + "." + User.USER_ID + ","
//                + " '" + Transaction.TITLE_REFERRAL_CREDIT_APPLIED + "',"
//                + " '" + Transaction.DESCRIPTION_REFERRAL_CREDIT_APPLIED + "',"
//
//                + Transaction.TRANSACTION_TYPE_USER_REFERRAL_CREDIT + ","
//                + " ? ,"
//
//                + " true " + ","
//
//                + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + " - ?,"
//                + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + ""
//
//                + " FROM " + User.TABLE_NAME
//                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ( "
//                + " SELECT " + User.REFERRED_BY
//                + " FROM " + User.TABLE_NAME
//                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + User.TABLE_NAME + "." + User.USER_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + ")"
//                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + " = ? "
//                + " AND " + User.TABLE_NAME + "." + User.IS_REFERRER_CREDITED + " = "  + " FALSE )";


        createTransaction = "INSERT INTO " + Transaction.TABLE_NAME
                + "("

                + Transaction.USER_ID + ","

                + Transaction.TITLE + ","
                + Transaction.DESCRIPTION + ","

                + Transaction.TRANSACTION_TYPE + ","
                + Transaction.TRANSACTION_AMOUNT + ","

                + Transaction.IS_CREDIT + ","

//                + Transaction.CURRENT_DUES_BEFORE_TRANSACTION + ","
                + Transaction.SERVICE_BALANCE_AFTER_TRANSACTION + ""

                + ") "
                + " SELECT "

                + "referrer." + User.USER_ID + ","
                +  "'" + Transaction.TITLE_REFERRAL_CREDIT_APPLIED + "',"
                + " '" + Transaction.DESCRIPTION_REFERRAL_CREDIT_APPLIED + " for referring '" +  " || registered." + User.NAME + " || ' | User ID : ' || registered." + User.USER_ID + "::text " + ","

                + Transaction.TRANSACTION_TYPE_USER_REFERRAL_CREDIT + ","
                + " ? ,"

                + " true " + ","

//                + "referrer." + User.SERVICE_ACCOUNT_BALANCE + " - ?,"
                + "referrer." + User.SERVICE_ACCOUNT_BALANCE + ""

                + " FROM " + User.TABLE_NAME + " referrer "
                + " INNER JOIN " + User.TABLE_NAME + " registered " + " ON ( " + "referrer." + User.USER_ID + " = " + "registered." + User.REFERRED_BY + " ) "
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (registered." + User.USER_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " ) "
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + " = ? "
                + " AND registered." + User.IS_REFERRER_CREDITED + " = "  + " FALSE";






        // mark referrer credited
        updateRefferCredited =  " UPDATE " + User.TABLE_NAME
                + " SET " + User.IS_REFERRER_CREDITED + " = TRUE "
                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ( "
                + " SELECT " + User.USER_ID
                + " FROM " + User.TABLE_NAME
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + User.TABLE_NAME + "." + User.USER_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + ")"
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + " = ? "
                + " AND " + User.TABLE_NAME + "." + User.IS_REFERRER_CREDITED + " = "  + " FALSE )";



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setObject(++i,enabled);
            statementUpdate.setObject(++i,vehicleID);

            rowCountItems = statementUpdate.executeUpdate();


            if(enabled) {

                statementUpdateDUES = connection.prepareStatement(updateDUES);
                i = 0;


                statementUpdateDUES.setObject(++i, GlobalConstants.REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION);
//                statementUpdateDUES.setObject(++i, GlobalConstants.REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION);

                statementUpdateDUES.setObject(++i, vehicleID);

                statementUpdateDUES.executeUpdate();



                statementCreateTransaction = connection.prepareStatement(createTransaction);
                i = 0;

//                statementCreateTransaction.setObject(++i, GlobalConstants.REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION);
                statementCreateTransaction.setObject(++i, GlobalConstants.REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION);

                statementCreateTransaction.setObject(++i, vehicleID);
                statementCreateTransaction.executeUpdate();




                statementReferrerCredited = connection.prepareStatement(updateRefferCredited);
                i = 0;
                statementReferrerCredited.setObject(++i, vehicleID);
                statementReferrerCredited.executeUpdate();

            }


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


            if (statementUpdateDUES != null) {
                try {
                    statementUpdateDUES.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            if (statementCreateTransaction != null) {
                try {
                    statementCreateTransaction.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }



            if (statementReferrerCredited != null) {
                try {
                    statementReferrerCredited.close();
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








    public int updateVehicleByAdmin(Vehicle vehicle)
    {

        if(vehicle.getMinTripCharges()> GlobalConstants.max_min_trip_charges)
        {
            vehicle.setMinTripCharges(GlobalConstants.max_min_trip_charges);
        }

        if(vehicle.getChargesPerKM()>GlobalConstants.max_charges_per_km)
        {
            vehicle.setChargesPerKM(GlobalConstants.max_charges_per_km);
        }




        Connection connection = null;
        PreparedStatement statementUpdate = null;
        PreparedStatement statementUpdateUser = null;


        int rowCountItems = -1;
        int rowCountUpdateUser = -1;

        String updateVehicle = "";
        String updateUser = "";


        updateVehicle =  " UPDATE " + Vehicle.TABLE_NAME
                        + " SET "   + Vehicle.VEHICLE_MODEL_NAME  + "=?,"
                                    + Vehicle.SEATING_CAPACITY    + "=?,"
                                    + Vehicle.PROFILE_IMAGE_URL   + "=?,"
                                    + Vehicle.MIN_TRIP_CHARGES    + "=?,"
                                    + Vehicle.CHARGES_PER_KM      + "=?,"
                                    + Vehicle.VEHICLE_REGISTRATION_NUMBER      + "=?"
                        + " WHERE " + Vehicle.DRIVER_ID          + " = ?";




        updateUser = "UPDATE " + User.TABLE_NAME
                    + " SET "

                    + User.NAME + "=?,"
                    + User.GENDER + "=?"
//                    + User.PROFILE_IMAGE_URL + "=?"

                    + " WHERE " + User.USER_ID + " = ?";




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(updateVehicle);
            int i = 0;


            statementUpdate.setString(++i,vehicle.getVehicleModelName());
            statementUpdate.setObject(++i,vehicle.getSeatingCapacity());
            statementUpdate.setString(++i,vehicle.getProfileImageURL());
            statementUpdate.setObject(++i,vehicle.getMinTripCharges());
            statementUpdate.setObject(++i,vehicle.getChargesPerKM());
            statementUpdate.setString(++i,vehicle.getRegistrationNumber());


            statementUpdate.setObject(++i,vehicle.getDriverID());

            rowCountItems = statementUpdate.executeUpdate();



            statementUpdateUser = connection.prepareStatement(updateUser);

            User user = vehicle.getRt_driver();

            i = 0;

            statementUpdateUser.setString(++i,user.getName());
            statementUpdateUser.setObject(++i,user.getGender());
//            statementUpdateUser.setString(++i,user.getProfileImagePath());

            statementUpdateUser.setObject(++i,vehicle.getDriverID());


            rowCountUpdateUser = statementUpdateUser.executeUpdate();


            System.out.println("UpdateVehicleByAdmin : Total rows updated : " + rowCountUpdateUser);





            connection.commit();

        } catch (SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

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



            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }



        return rowCountItems + rowCountUpdateUser;
    }






    public int updateVehicleByDriverNew(Vehicle vehicle)
    {



        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + Vehicle.TABLE_NAME
                + " SET "   + Vehicle.MIN_TRIP_CHARGES    + "=?,"
                            + Vehicle.CHARGES_PER_KM      + "=?,"
                            + Vehicle.FILTER_BY_DESTINATION   + "=?,"
                            + Vehicle.DESTINATION_FILTER_LAT   + "=?,"
                            + Vehicle.DESTINATION_FILTER_LON   + "=?,"
                            + Vehicle.DESTINATION_FILTER_RADIUS   + "=?"
                + " FROM "  + VehicleType.TABLE_NAME
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_TYPE + " = " + VehicleType.TABLE_NAME + "." + VehicleType.VEHICLE_TYPE_ID
                + " AND "   + Vehicle.DRIVER_ID  + " = ?"
                + " AND "   + Vehicle.TABLE_NAME + "." + Vehicle.MIN_TRIP_CHARGES + " <= " + VehicleType.TABLE_NAME + "." + VehicleType.MAX_MIN_TRIP_CHARGE
                + " AND "   + Vehicle.TABLE_NAME + "." + Vehicle.CHARGES_PER_KM + " <= " + VehicleType.TABLE_NAME + "." + VehicleType.MAX_CHARGES_PER_KM ;




//        update =  " UPDATE " + Vehicle.TABLE_NAME
//                + " SET "   + Vehicle.MIN_TRIP_CHARGES    + "=?,"
//                            + Vehicle.CHARGES_PER_KM      + "=?,"
//                            + Vehicle.FILTER_BY_DESTINATION   + "=?,"
//                            + Vehicle.DESTINATION_FILTER_LAT   + "=?,"
//                            + Vehicle.DESTINATION_FILTER_LON   + "=?,"
//                            + Vehicle.DESTINATION_FILTER_RADIUS   + "=?"
//                + " FROM "  + VehicleType.TABLE_NAME
//                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_TYPE + " = " + VehicleType.TABLE_NAME + "." + VehicleType.VEHICLE_TYPE_ID
//                + " AND "   + Vehicle.DRIVER_ID          + " = ?"
//                + " AND ( "   + " ( " + Vehicle.VEHICLE_TYPE + " IS NOT NULL "
//                                + " AND "   + Vehicle.TABLE_NAME + "." + Vehicle.MIN_TRIP_CHARGES + " <= " + VehicleType.TABLE_NAME + "." + VehicleType.MAX_MIN_TRIP_CHARGE
//                                + " AND "   + Vehicle.TABLE_NAME + "." + Vehicle.CHARGES_PER_KM + " <= " + VehicleType.TABLE_NAME + "." + VehicleType.MAX_CHARGES_PER_KM  + " ) "
//                + " OR " + " ( "  + Vehicle.VEHICLE_TYPE + " IS NULL "
//                                + " AND "   + Vehicle.TABLE_NAME + "." + Vehicle.MIN_TRIP_CHARGES + " <= " + GlobalConstants.max_min_trip_charges
//                                + " AND "   + Vehicle.TABLE_NAME + "." + Vehicle.CHARGES_PER_KM + " <= " + GlobalConstants.max_charges_per_km + " )"
//                + " ) ";
//
//


        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementUpdate.setObject(++i,vehicle.getMinTripCharges());
            statementUpdate.setObject(++i,vehicle.getChargesPerKM());

            statementUpdate.setObject(++i,vehicle.isFilterByDestination());
            statementUpdate.setObject(++i,vehicle.getLatDestinationFilter());
            statementUpdate.setObject(++i,vehicle.getLonDestinationFilter());
            statementUpdate.setObject(++i,vehicle.getRadiusDestinationFilter());


            statementUpdate.setObject(++i,vehicle.getDriverID());

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








    public int updateVehicleByDriver(Vehicle vehicle)
    {

        if(vehicle.getMinTripCharges()> GlobalConstants.max_min_trip_charges)
        {
            vehicle.setMinTripCharges(GlobalConstants.max_min_trip_charges);
        }

        if(vehicle.getChargesPerKM()>GlobalConstants.max_charges_per_km)
        {
            vehicle.setChargesPerKM(GlobalConstants.max_charges_per_km);
        }


        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";


//
//
//        update =  " UPDATE " + Vehicle.TABLE_NAME
//                + " SET "   + Vehicle.VEHICLE_MODEL_NAME  + "=?,"
//                            + Vehicle.SEATING_CAPACITY    + "=?,"
//                            + Vehicle.PROFILE_IMAGE_URL   + "=?,"
//                            + Vehicle.MIN_TRIP_CHARGES    + "=?,"
//                            + Vehicle.CHARGES_PER_KM      + "=?"
//                + " WHERE " + Vehicle.DRIVER_ID          + " = ?";
//


        update =  " UPDATE " + Vehicle.TABLE_NAME
                + " SET "   + Vehicle.MIN_TRIP_CHARGES    + "=?,"
                            + Vehicle.CHARGES_PER_KM      + "=?,"
                            + Vehicle.FILTER_BY_DESTINATION   + "=?,"
                            + Vehicle.DESTINATION_FILTER_LAT   + "=?,"
                            + Vehicle.DESTINATION_FILTER_LON   + "=?,"
                            + Vehicle.DESTINATION_FILTER_RADIUS   + "=?"

                + " WHERE " + Vehicle.DRIVER_ID          + " = ?";





        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementUpdate.setObject(++i,vehicle.getMinTripCharges());
            statementUpdate.setObject(++i,vehicle.getChargesPerKM());

            statementUpdate.setObject(++i,vehicle.isFilterByDestination());
            statementUpdate.setObject(++i,vehicle.getLatDestinationFilter());
            statementUpdate.setObject(++i,vehicle.getLonDestinationFilter());
            statementUpdate.setObject(++i,vehicle.getRadiusDestinationFilter());


            statementUpdate.setObject(++i,vehicle.getDriverID());

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




    public int updateLocationByDriver(Location location, int driverID)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

//        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String update = "";



//        update = "UPDATE " + Vehicle.TABLE_NAME
//
//                + " SET "
//
//                + Vehicle.LAT_CURRENT + "=?,"
//                + Vehicle.LON_CURRENT + "=?,"
//                + Vehicle.TIMESTAMP_LOCATION_UPDATED + "= now()"
//
//                + " FROM " + User.TABLE_NAME
//                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID
//                + " AND " + User.TABLE_NAME + "." + User.USER_ID + " = ?";


//        + " WHERE " + Vehicle.VEHICLE_ID + " = ?";




        update = "UPDATE " + Vehicle.TABLE_NAME

                + " SET "

                + Vehicle.LAT_CURRENT + "=?,"
                + Vehicle.LON_CURRENT + "=?,"
                + Vehicle.TIMESTAMP_LOCATION_UPDATED + "= now()"

                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ?";



        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setObject(++i,location.getLatitude());
            statementUpdate.setObject(++i,location.getLongitude());

            statementUpdate.setObject(++i,driverID);

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



    public int setTaxiNotAvailable(int userID)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

//        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String update = "";


//        update =  " UPDATE " + Vehicle.TABLE_NAME
//                + " SET " + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.NOT_AVIALABLE
//                + " FROM " + User.TABLE_NAME
//                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID
//                + " AND " + User.TABLE_NAME + "." + User.USER_ID + " = ?"
//                + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.AVIALABLE;


        update =  " UPDATE " + Vehicle.TABLE_NAME
                + " SET " + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.NOT_AVIALABLE
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ?"
                + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.AVIALABLE;



        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementUpdate.setObject(++i,userID);

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




    public int setTaxiAvailable(int userID)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

//        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String update = "";



//        update =  " UPDATE " + Vehicle.TABLE_NAME
//                + " SET " + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.AVIALABLE
//                + " FROM " + User.TABLE_NAME
//                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID
//                + " AND " + User.TABLE_NAME + "." + User.USER_ID + " = ?"
//                + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.NOT_AVIALABLE;



        update =  " UPDATE " + Vehicle.TABLE_NAME
                + " SET " + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.AVIALABLE
                + " FROM " + User.TABLE_NAME
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ?"
                + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID
                + " AND " + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + " >= " +  GlobalConstants.MIN_SERVICE_ACCOUNT_BALANCE + " - " + User.TABLE_NAME + "." + User.EXTENDED_CREDIT_LIMIT
                + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.NOT_AVIALABLE;



        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementUpdate.setObject(++i,userID);

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





    // marked as deprecated
    public int updateStatus(int status, int vehicleID)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

//        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String update = "";



        update = "UPDATE " + Vehicle.TABLE_NAME
                + " SET " + Vehicle.VEHICLE_STATUS + "=?"
                + " WHERE " + Vehicle.VEHICLE_ID + " = ?";




        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setObject(++i,status);
            statementUpdate.setObject(++i,vehicleID);

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




    public int deleteVehicle(int vehicleID)
    {

        String deleteStatement =  " DELETE FROM " + Vehicle.TABLE_NAME
                                + " WHERE " + Vehicle.VEHICLE_ID + " = ?";


        Connection connection= null;
        PreparedStatement statement = null;
        int rowCountDeleted = 0;


        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteStatement);
            statement.setInt(1,vehicleID);

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







    public Vehicle getProfileByDriver(int driverID,
                                      Double latCenter, Double lonCenter)
    {

        String query = " ";


        query = "SELECT "

                + " (6371.01 * acos(cos( radians(" + latCenter + ")) * cos( radians(" + Vehicle.LAT_CURRENT + " )) * cos(radians( "
                + Vehicle.LON_CURRENT + ") - radians("
                + lonCenter + "))" + " + sin( radians(" + latCenter + ")) * sin(radians(" + Vehicle.LAT_CURRENT + "))))" + " as distance ,"

                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.PROFILE_IMAGE_URL + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_MODEL_NAME + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.SEATING_CAPACITY + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.MIN_TRIP_CHARGES + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.CHARGES_PER_KM + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.LAT_CURRENT + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.LON_CURRENT + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.TIMESTAMP_LOCATION_UPDATED + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED_UPTO + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.FILTER_BY_DESTINATION + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.DESTINATION_FILTER_LAT + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.DESTINATION_FILTER_LON + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.DESTINATION_FILTER_RADIUS + ","

                + User.TABLE_NAME + "." + User.USER_ID + ","
                + User.TABLE_NAME + "." + User.E_MAIL + ","
                + User.TABLE_NAME + "." + User.PHONE + ","
                + User.TABLE_NAME + "." + User.IS_ACCOUNT_PRIVATE + ","
                + User.TABLE_NAME + "." + User.ABOUT + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + " as user_profile_image,"

                + User.TABLE_NAME + "." + User.TAX_ACCOUNT_BALANCE + ","
                + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + ","

                + User.TABLE_NAME + "." + User.EXTENDED_CREDIT_LIMIT + ""

                + " FROM " + Vehicle.TABLE_NAME
                + " INNER JOIN " + User.TABLE_NAME + " ON (" + Vehicle.DRIVER_ID + " = " + User.USER_ID + ")"
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = ?";


//        + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED +  "= TRUE "


        query = query + " group by "
                        + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ","
                        + User.TABLE_NAME + "." + User.USER_ID;


        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        Vehicle vehicle = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setObject(++i,driverID);

            rs = statement.executeQuery();


            while(rs.next())
            {


                vehicle = new Vehicle();

                vehicle.setRt_distance(rs.getFloat("distance"));

                vehicle.setVehicleID(rs.getInt(Vehicle.VEHICLE_ID));
                vehicle.setDriverID(rs.getInt(Vehicle.DRIVER_ID));
                vehicle.setProfileImageURL(rs.getString(Vehicle.PROFILE_IMAGE_URL));

                vehicle.setVehicleStatus(rs.getInt(Vehicle.VEHICLE_STATUS));

                vehicle.setVehicleModelName(rs.getString(Vehicle.VEHICLE_MODEL_NAME));
                vehicle.setSeatingCapacity(rs.getInt(Vehicle.SEATING_CAPACITY));

                vehicle.setMinTripCharges(rs.getInt(Vehicle.MIN_TRIP_CHARGES));
                vehicle.setChargesPerKM(rs.getInt(Vehicle.CHARGES_PER_KM));

                vehicle.setLatCurrent(rs.getFloat(Vehicle.LAT_CURRENT));
                vehicle.setLonCurrent(rs.getFloat(Vehicle.LON_CURRENT));

                vehicle.setLocationUpdated(rs.getTimestamp(Vehicle.TIMESTAMP_LOCATION_UPDATED));

                vehicle.setEnabled(rs.getBoolean(Vehicle.ENABLED));
                vehicle.setEnabledUpto(rs.getTimestamp(Vehicle.ENABLED_UPTO));

                vehicle.setFilterByDestination(rs.getBoolean(Vehicle.FILTER_BY_DESTINATION));
                vehicle.setLatDestinationFilter(rs.getDouble(Vehicle.DESTINATION_FILTER_LAT));
                vehicle.setLonDestinationFilter(rs.getDouble(Vehicle.DESTINATION_FILTER_LON));
                vehicle.setRadiusDestinationFilter(rs.getDouble(Vehicle.DESTINATION_FILTER_RADIUS));


                User driver = new User();

                driver.setUserID(rs.getInt(User.USER_ID));
                driver.setEmail(rs.getString(User.E_MAIL));
                driver.setPhone(rs.getString(User.PHONE));
                driver.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                driver.setAbout(rs.getString(User.ABOUT));
                driver.setName(rs.getString(User.NAME));
                driver.setGender(rs.getBoolean(User.GENDER));
                driver.setProfileImagePath(rs.getString("user_profile_image"));

                driver.setTaxAccountBalance(rs.getDouble(User.TAX_ACCOUNT_BALANCE));
                driver.setServiceAccountBalance(rs.getDouble(User.SERVICE_ACCOUNT_BALANCE));
                driver.setExtendedCreditLimit(rs.getDouble(User.EXTENDED_CREDIT_LIMIT));

                vehicle.setRt_driver(driver);

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

        return vehicle;
    }










    public VehicleEndPoint getTaxisAvailable(
            Double latPickUp, Double lonPickUp,
            double latDestination, double lonDestination,
            Double tripDistance,
            Boolean gender,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata
    ) {


        boolean isfirst = true;

        String queryCount = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


        String queryJoin = "SELECT DISTINCT "

                + "6371 * acos( cos( radians("
                + latPickUp + ")) * cos( radians(" +  Vehicle.LAT_CURRENT +  ") ) * cos(radians(" + Vehicle.LON_CURRENT +  ") - radians("
                + lonPickUp + "))"
                + " + sin( radians(" + latPickUp + ")) * sin(radians(" + Vehicle.LAT_CURRENT + "))) as distance" + ","


                + "(  ( Greatest( ( 6371 * acos( cos( radians("
                + latPickUp + ")) * cos( radians(" +  Vehicle.LAT_CURRENT +  ") ) * cos(radians(" + Vehicle.LON_CURRENT +  ") - radians("
                + lonPickUp + "))"
                + " + sin( radians(" + latPickUp + ")) * sin(radians(" + Vehicle.LAT_CURRENT + "))) ) * " + GlobalConstants.SHORTEST_DISTANCE_MULTIPLIER + " - " + GlobalConstants.free_pickup_distance + ", 0 ) + " + tripDistance + " ) * " + Vehicle.CHARGES_PER_KM + " + " + GlobalConstants.taxi_referral_charges + " ) * (1 + 0.01 * " + GlobalConstants.tax_rate_in_percent + ") as fare_estimate,"


                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_MODEL_NAME + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.SEATING_CAPACITY + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.MIN_TRIP_CHARGES + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.CHARGES_PER_KM + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.PROFILE_IMAGE_URL + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.LAT_CURRENT + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.LON_CURRENT + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.TIMESTAMP_LOCATION_UPDATED + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.TIMESTAMP_PROFILE_CREATED + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_REGISTRATION_NUMBER + ","

                +  "avg(" + TripHistory.TABLE_NAME + "." + TripHistory.RATING_BY_END_USER + ") as avg_rating" + ","
                +  "count( " + TripHistory.TABLE_NAME + "." + TripHistory.RATING_BY_END_USER + ") as rating_count" + ","
                +  "sum( " + TripHistory.TABLE_NAME + "." + TripHistory.DISTANCE_TRAVELLED_FOR_TRIP + ") as experience" + ","

                + User.TABLE_NAME + "." + User.PHONE + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","
                + User.TABLE_NAME + "." + User.ABOUT + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ","
                + User.TABLE_NAME + "." + User.IS_ACCOUNT_PRIVATE + ""

                + " FROM " + User.TABLE_NAME
                + " INNER JOIN " + Vehicle.TABLE_NAME + " ON (" + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " LEFT OUTER JOIN " + TripHistory.TABLE_NAME + " ON ( " + TripHistory.TABLE_NAME + "." + TripHistory.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.AVIALABLE
                + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED + " = TRUE "
                + " AND " + User.TABLE_NAME + "." + User.TAX_ACCOUNT_BALANCE + " > " + GlobalConstants.MIN_TAX_ACCOUNT_BALANCE
                + " AND " + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + " > " + GlobalConstants.MIN_SERVICE_ACCOUNT_BALANCE + " - " + User.TABLE_NAME + "." + User.EXTENDED_CREDIT_LIMIT
                + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED_UPTO + " > now()"
                + " AND ( " + Vehicle.TABLE_NAME + "." + Vehicle.FILTER_BY_DESTINATION + " = FALSE"
                + " OR " + " ( " + Vehicle.TABLE_NAME + "." + Vehicle.FILTER_BY_DESTINATION + " = TRUE AND "
                + "( " +
                "( 6371 * acos( cos( radians("
                + latDestination + ")) * cos( radians(" +  Vehicle.DESTINATION_FILTER_LAT +  ") ) * cos(radians(" + Vehicle.DESTINATION_FILTER_LON +  ") - radians("
                + lonDestination + "))"
                + " + sin( radians(" + latDestination + ")) * sin(radians(" + Vehicle.DESTINATION_FILTER_LAT + "))))"
                + " < " + Vehicle.TABLE_NAME + "." + Vehicle.DESTINATION_FILTER_RADIUS + ")" + ")"
                + ")";








//                + " LEFT OUTER JOIN " + VehicleTypeVersion.TABLE_NAME
//                + " ON (" + VehicleType.TABLE_NAME + "." + VehicleType.VEHICLE_TYPE_ID + " = " + VehicleTypeVersion.TABLE_NAME + "." + VehicleTypeVersion.PARENT + ")";



        if(gender != null)
        {
            queryJoin = queryJoin + " AND " + User.TABLE_NAME + "." + User.GENDER + " = ?";
        }
//



        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
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

        VehicleEndPoint endPoint = new VehicleEndPoint();

        ArrayList<Vehicle> itemList = new ArrayList<>();
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

                if(gender!=null)
                {
                    statement.setObject(++i,gender);
                }


                rs = statement.executeQuery();

                while(rs.next()) {

                    Vehicle vehicle = new Vehicle();

                    vehicle.setRt_distance(rs.getFloat("distance"));
                    vehicle.setRt_fare_estimate(rs.getFloat("fare_estimate"));

                    vehicle.setVehicleID(rs.getInt(Vehicle.VEHICLE_ID));
                    vehicle.setDriverID(rs.getInt(Vehicle.DRIVER_ID));

                    vehicle.setVehicleModelName(rs.getString(Vehicle.VEHICLE_MODEL_NAME));
                    vehicle.setSeatingCapacity(rs.getInt(Vehicle.SEATING_CAPACITY));

                    vehicle.setVehicleStatus(rs.getInt(Vehicle.VEHICLE_STATUS));
                    vehicle.setMinTripCharges(rs.getInt(Vehicle.MIN_TRIP_CHARGES));
                    vehicle.setChargesPerKM(rs.getInt(Vehicle.CHARGES_PER_KM));



                    vehicle.setLatCurrent(rs.getFloat(Vehicle.LAT_CURRENT));
                    vehicle.setLonCurrent(rs.getFloat(Vehicle.LON_CURRENT));

                    vehicle.setLocationUpdated(rs.getTimestamp(Vehicle.TIMESTAMP_LOCATION_UPDATED));
                    vehicle.setProfileCreated(rs.getTimestamp(Vehicle.TIMESTAMP_PROFILE_CREATED));

                    vehicle.setRegistrationNumber(rs.getString(Vehicle.VEHICLE_REGISTRATION_NUMBER));
//                    vehicle.setInsuranceID(rs.getString(Vehicle.VEHICLE_INSURANCE_NUMBER));
//                    vehicle.setPollutionCertificateID(rs.getString(Vehicle.VEHICLE_PUC_ID));

                    vehicle.setRt_rating_avg(rs.getDouble("avg_rating"));
                    vehicle.setRt_rating_count(rs.getInt("rating_count"));
                    vehicle.setRt_kms_total(rs.getDouble("experience"));


                    User driver = new User();

                    driver.setUserID(vehicle.getDriverID());

//                    driver.setGender(rs.getBoolean(User.GENDER));
//                    driver.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
                    driver.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));


                    if (driver.isAccountPrivate())
                    {
                        vehicle.setProfileImageURL(null);
                        driver.setPhone(null);
                        driver.setName(null);
                        driver.setAbout(null);
                    }
                    else
                    {
                        vehicle.setProfileImageURL(rs.getString(Vehicle.PROFILE_IMAGE_URL));
                        driver.setPhone(rs.getString(User.PHONE));
                        driver.setName(rs.getString(User.NAME));
                        driver.setAbout(rs.getString(User.ABOUT));
                    }




                    vehicle.setRt_driver(driver);

                    itemList.add(vehicle);
                }


                endPoint.setResults(itemList);

            }


            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;

                if(gender!=null)
                {
                    statementCount.setObject(++i,gender);
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










    public VehicleEndPoint getTaxiProfileForAdmin(
            Double latPickUp, Double lonPickUp,
            Boolean gender,
            Boolean isEnabled,
            Boolean registrationExpired,
            boolean taxBalanceExhausted,
            boolean serviceBalanceExhausted,
            Integer status,
            String searchString,
            String searchStringVehicleID,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata
    ) {


        boolean isfirst = true;

        String queryCount = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


        String queryJoin = "SELECT DISTINCT "

                + "6371 * acos( cos( radians("
                + latPickUp + ")) * cos( radians(" +  Vehicle.LAT_CURRENT +  ") ) * cos(radians(" + Vehicle.LON_CURRENT +  ") - radians("
                + lonPickUp + "))"
                + " + sin( radians(" + latPickUp + ")) * sin(radians(" + Vehicle.LAT_CURRENT + "))) as distance" + ","


                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.DRIVER_ID + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_MODEL_NAME + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.SEATING_CAPACITY + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED_UPTO + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.RENEWED_BY + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.MIN_TRIP_CHARGES + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.CHARGES_PER_KM + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.PROFILE_IMAGE_URL + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.LAT_CURRENT + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.LON_CURRENT + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.TIMESTAMP_LOCATION_UPDATED + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.TIMESTAMP_PROFILE_CREATED + ","

                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_REGISTRATION_NUMBER + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_INSURANCE_NUMBER + ","
                + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_PUC_ID + ","

                +  "avg(" + TripHistory.TABLE_NAME + "." + TripHistory.RATING_BY_END_USER + ") as avg_rating" + ","
                +  "count( " + TripHistory.TABLE_NAME + "." + TripHistory.RATING_BY_END_USER + ") as rating_count" + ","

                + User.TABLE_NAME + "." + User.PHONE + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ","
                + User.TABLE_NAME + "." + User.TAX_ACCOUNT_BALANCE + ","
                + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + ""

                + " FROM " + Vehicle.TABLE_NAME
                + " INNER JOIN " + User.TABLE_NAME + " ON (" + Vehicle.DRIVER_ID + " = " + User.USER_ID + ")"
                + " LEFT OUTER JOIN " + TripHistory.TABLE_NAME + " ON ( " + TripHistory.TABLE_NAME + "." + TripHistory.VEHICLE_ID + " = " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + ")"
                + " WHERE TRUE ";




        if(status!=null)
        {
            queryJoin = queryJoin + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + " = ?";
        }




        if(isEnabled!=null)
        {
            queryJoin = queryJoin + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED + " = ?";
        }



        if(registrationExpired!=null)
        {
            if(registrationExpired)
            {
                queryJoin = queryJoin + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED_UPTO + " < now()";
            }
            else
            {
                queryJoin = queryJoin + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED_UPTO + " > now()";
            }
        }


        if(taxBalanceExhausted)
        {
            queryJoin = queryJoin + " AND " + User.TABLE_NAME + "." + User.TAX_ACCOUNT_BALANCE + " <= " + GlobalConstants.MIN_TAX_ACCOUNT_BALANCE;
        }




        if(serviceBalanceExhausted)
        {
            queryJoin = queryJoin + " AND " + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + " <= " + GlobalConstants.MIN_SERVICE_ACCOUNT_BALANCE;
        }





        if(gender != null)
        {
            queryJoin = queryJoin + " AND " + User.TABLE_NAME + "." + User.GENDER + " = ?";
        }




        if(searchString !=null) {
//
//            Shop.TABLE_NAME + "." + Shop.SHOP_NAME + " ilike '%" + searchString + "%'"
//                    + " or " + Shop.TABLE_NAME + "." + Shop.LONG_DESCRIPTION + " ilike '%" + searchString + "%'"
//                    + " or " + Shop.TABLE_NAME + "." + Shop.SHOP_ADDRESS + " ilike '%" + searchString + "%'"

            String queryPartSearch = " AND CAST ( " + User.TABLE_NAME + "." + User.USER_ID + " AS text )" + " ilike '%" + searchString + "%'" + " ";

            queryJoin = queryJoin + queryPartSearch;
        }

//


        if(searchStringVehicleID !=null) {

            String queryPartSearch = " AND CAST ( " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_ID + " AS text )" + " ilike '%" + searchString + "%'" + " ";

            queryJoin = queryJoin + queryPartSearch;
        }



        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
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


        VehicleEndPoint endPoint = new VehicleEndPoint();

        ArrayList<Vehicle> itemList = new ArrayList<Vehicle>();
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

                if(isEnabled!=null)
                {
                    statement.setObject(++i,isEnabled);
                }

                if(gender!=null)
                {
                    statement.setObject(++i,gender);
                }


                rs = statement.executeQuery();

                while(rs.next())
                {

                    Vehicle vehicle = new Vehicle();

                    vehicle.setRt_distance(rs.getFloat("distance"));

                    vehicle.setVehicleID(rs.getInt(Vehicle.VEHICLE_ID));
                    vehicle.setDriverID(rs.getInt(Vehicle.DRIVER_ID));

                    vehicle.setVehicleModelName(rs.getString(Vehicle.VEHICLE_MODEL_NAME));
                    vehicle.setSeatingCapacity(rs.getInt(Vehicle.SEATING_CAPACITY));

                    vehicle.setEnabled(rs.getBoolean(Vehicle.ENABLED));
                    vehicle.setEnabledUpto(rs.getTimestamp(Vehicle.ENABLED_UPTO));
                    vehicle.setRenewedBy(rs.getInt(Vehicle.RENEWED_BY));

                    vehicle.setVehicleStatus(rs.getInt(Vehicle.VEHICLE_STATUS));
                    vehicle.setMinTripCharges(rs.getInt(Vehicle.MIN_TRIP_CHARGES));
                    vehicle.setChargesPerKM(rs.getInt(Vehicle.CHARGES_PER_KM));

                    vehicle.setProfileImageURL(rs.getString(Vehicle.PROFILE_IMAGE_URL));

                    vehicle.setLatCurrent(rs.getFloat(Vehicle.LAT_CURRENT));
                    vehicle.setLonCurrent(rs.getFloat(Vehicle.LON_CURRENT));

                    vehicle.setLocationUpdated(rs.getTimestamp(Vehicle.TIMESTAMP_LOCATION_UPDATED));
                    vehicle.setProfileCreated(rs.getTimestamp(Vehicle.TIMESTAMP_PROFILE_CREATED));

                    vehicle.setRegistrationNumber(rs.getString(Vehicle.VEHICLE_REGISTRATION_NUMBER));
                    vehicle.setInsuranceID(rs.getString(Vehicle.VEHICLE_INSURANCE_NUMBER));
                    vehicle.setPollutionCertificateID(rs.getString(Vehicle.VEHICLE_PUC_ID));

                    vehicle.setRt_rating_avg(rs.getDouble("avg_rating"));
                    vehicle.setRt_rating_count(rs.getInt("rating_count"));


                    User driver = new User();

                    driver.setUserID(vehicle.getDriverID());
                    driver.setPhone(rs.getString(User.PHONE));
                    driver.setName(rs.getString(User.NAME));
                    driver.setGender(rs.getBoolean(User.GENDER));
                    driver.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));

                    driver.setTaxAccountBalance(rs.getDouble(User.TAX_ACCOUNT_BALANCE));
                    driver.setServiceAccountBalance(rs.getDouble(User.SERVICE_ACCOUNT_BALANCE));

                    vehicle.setRt_driver(driver);

                    itemList.add(vehicle);
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

                if(isEnabled!=null)
                {
                    statementCount.setObject(++i,isEnabled);
                }


                if(gender!=null)
                {
                    statementCount.setObject(++i,gender);
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










    /*******************
     *
     * isApproved true -> accepted
     * isApproved NULL -> under review
     * isApproved false -> rejected
     *
     * parent NULL -> insert_submission (inserts a new entry)
     * parent NOT NULL -> update_submission(
     *
     * timestamp_applied ->
     * a) timestamp at which the version was applied to the entry
     * b) sort of timestamp applied in the descending order shows the version history
     *
     * backup exists -> tells if the backup exists for the given entry or not
     *
     * -------------------------------------------------------------------------------
     *
     * insert_an_entry
     * 1. a) Insert into entry table
     * b) mark backup exists as false
     * (reconsider)b) mark backup exists as true
     * (reconsider)2. insert into versions table
     *
     * update_an_entry
     * 1. take a backup if backup does not exist
     * 2. a) update the entry b) mark backup exists as false
     *
     * restore_a_version
     * 1. take a backup if backup does not exist
     * 2. a) update an entry from a version b) mark backup exists as true
     * 3. update the version fields - timestamp_applied to now()
     *
     *
     * submit_an_entry
     * 1. a) insert into versions table b) parentID is null, isApproved is null (indicates under review),
     * timestampApplied as null
     *
     * approve_an_entry
     * 1. a) create a backup of the current entry if not exist
     * 2. a)copy from versions table to an entry table b) mark backup exists as true
     * 3.Update version fields isApproved, timestampApplied, timestampApproved and also parentID
     *
     * submit_an_update_to_an_entry
     * 1. a)create a version with isApproved NULL, timestamp created now(), timestamp applied null,
     *      parent ID assigned
     *
     * approve_an_update
     * 1. a)create a backup of the current entry if not exist b) mark backup exists as true
     * 2. copy the version into the entry
     * 3. update version fields timestampApplied to now(), isApproved to true, timestampApproved to now(),
     *
     *
     * reject_an_entry
     * 1. set isApproved from NULL to false, mark timestamp approved as now()
     *
     * reject_an_update
     * 2. set isApproved from NULL to false, mark timestamp approved as now()
     *
     *
     * delete_an_entry
     * 1. deleting an entry deletes all of its
     *
     *
     *
     *
     *
     * ------------------------
     *
     * backup_exists
     *
     * timestamp_applied
     * timestamp_approved
     * is_approved
     *
     *
     *
     *
     *
     *
     * ------------------------------------
     * insert_an_item

     * update_an_item
     * submit_an_update
     * restore_a_version
     * approve_an_insert
     * approve_an_update
     *
     *
     */
}
