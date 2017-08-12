package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.Model.VehicleType;
import org.taxireferral.api.Model.VehicleTypeVersion;
import org.taxireferral.api.ModelEndpoints.VehicleEndPoint;
import org.taxireferral.api.ModelEndpoints.VehicleTypeEndPoint;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.ModelUtility.Location;

import java.sql.*;
import java.util.ArrayList;

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

    // getVehicle(int driverID) : get vehicle details for driver ID
    // getVehicles(double latPickUp, double latPickUp, String searchString)




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

                + Vehicle.MIN_TRIP_CHARGES + ","
                + Vehicle.CHARGES_PER_KM + ""
                + ") "
                + "VALUES(?,?,?,?)";




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementInsert = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementInsert.setObject(++i,vehicle.getDriverID());
            statementInsert.setString(++i,vehicle.getProfileImageURL());
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





    public int update_vehicle_by_admin(Vehicle vehicle)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + Vehicle.TABLE_NAME
                + " SET " + Vehicle.ENABLED + "=?"
                + " WHERE " + Vehicle.VEHICLE_ID + " = ?";




        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setObject(++i,vehicle.isEnabled());
            statementUpdate.setObject(++i,vehicle.getVehicleID());

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




    public int update_vehicle(Vehicle vehicle)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + Vehicle.TABLE_NAME
                + " SET "   + Vehicle.VEHICLE_MODEL_NAME  + "=?,"
                            + Vehicle.SEATING_CAPACITY    + "=?,"
                            + Vehicle.PROFILE_IMAGE_URL   + "=?,"
                            + Vehicle.MIN_TRIP_CHARGES    + "=?,"
                            + Vehicle.CHARGES_PER_KM      + "=?"
                + " WHERE " + Vehicle.DRIVER_ID          + " = ?";




        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setString(++i,vehicle.getVehicleModelName());
            statementUpdate.setObject(++i,vehicle.getSeatingCapacity());
            statementUpdate.setString(++i,vehicle.getProfileImageURL());
            statementUpdate.setObject(++i,vehicle.getMinTripCharges());
            statementUpdate.setObject(++i,vehicle.getChargesPerKM());

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




    public int update_location(Location location,int driverID)
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
                + " AND " + User.TABLE_NAME + "." + User.CURRENT_DUES + " <= " + User.TABLE_NAME + "." + User.EXTENDED_CREDIT_LIMIT + " + " + GlobalConstants.CREDIT_LIMIT_FOR_DRIVER
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
    public int update_status(int status, int vehicleID)
    {

        Connection connection = null;
        PreparedStatement statementUpdate = null;

//        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String update = "";



        update = "UPDATE " + Vehicle.TABLE_NAME

                + " SET "
                + Vehicle.VEHICLE_STATUS + "=?"
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







    public Vehicle getVehicle(int driverID,
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

                + User.TABLE_NAME + "." + User.USER_ID + ","
                + User.TABLE_NAME + "." + User.E_MAIL + ","
                + User.TABLE_NAME + "." + User.PHONE + ","
                + User.TABLE_NAME + "." + User.IS_ACCOUNT_PRIVATE + ","
                + User.TABLE_NAME + "." + User.ABOUT + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + " as user_profile_image,"

                + User.TABLE_NAME + "." + User.CURRENT_DUES + ","
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


                User driver = new User();

                driver.setUserID(rs.getInt(User.USER_ID));
                driver.setEmail(rs.getString(User.E_MAIL));
                driver.setPhone(rs.getString(User.PHONE));
                driver.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                driver.setAbout(rs.getString(User.ABOUT));
                driver.setName(rs.getString(User.NAME));
                driver.setGender(rs.getBoolean(User.GENDER));
                driver.setProfileImagePath(rs.getString("user_profile_image"));

                driver.setCurrentDues(rs.getDouble(User.CURRENT_DUES));
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

                + " FROM " + Vehicle.TABLE_NAME
                + " INNER JOIN " + User.TABLE_NAME + " ON (" + Vehicle.DRIVER_ID + " = " + User.USER_ID + ")"
                + " WHERE " + Vehicle.TABLE_NAME + "." + Vehicle.ENABLED + " = TRUE "
                + " AND " + Vehicle.TABLE_NAME + "." + Vehicle.VEHICLE_STATUS + " = " + GlobalConstants.AVIALABLE;


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
