package org.taxireferral.api.DAOServiceRequests;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.TripHistory;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.Model.VehicleType;
import org.taxireferral.api.ModelEndpoints.VehicleEndPoint;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.ModelServices.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOService {

    private HikariDataSource dataSource = Globals.getDataSource();



    // Create
    // update
    // delete
    // getServiceListForAdmin()
    // getServiceListForDriver()




    public int insert(Service service, boolean getRowCount)
    {
        Connection connection = null;
        PreparedStatement statementInsert = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;


        String insert = "";


        insert = "INSERT INTO "
                + Service.TABLE_NAME
                + "("

                + Service.SERVICE_NAME + ","
                + Service.SERVICE_DESCRIPTION + ","
                + Service.AVAILABLE_FOR_DRIVER + ","
                + Service.AVAILABLE_FOR_END_USER + ","
                + Service.TOTAL_CHARGE + ","
                + Service.TAX_RATE + ","
                + Service.STAFF_PAY + ""

                + ") "
                + "VALUES(?,?,?, ?,?,?, ?)";




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementInsert = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementInsert.setString(++i,service.getServiceName());
            statementInsert.setString(++i,service.getServiceDescription());
            statementInsert.setObject(++i,service.isAvailableForDriver());
            statementInsert.setObject(++i,service.isAvailableForEndUser());
            statementInsert.setObject(++i,service.getTotalCharge());
            statementInsert.setObject(++i,service.getTaxRate());
            statementInsert.setObject(++i,service.getStaffPay());


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





    public int update(Service service)
    {



        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + Service.TABLE_NAME
                + " SET " + Service.SERVICE_NAME + "=?,"
                            + Service.SERVICE_DESCRIPTION + "=?,"
                            + Service.AVAILABLE_FOR_DRIVER + "=?,"
                            + Service.AVAILABLE_FOR_END_USER + "=?,"
                            + Service.TOTAL_CHARGE + "=?,"
                            + Service.TAX_RATE + "=?,"
                            + Service.STAFF_PAY + "=?"

                + " WHERE "  + Service.SERVICE_ID  + " = ?";





        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setString(++i,service.getServiceName());
            statementUpdate.setString(++i,service.getServiceDescription());
            statementUpdate.setObject(++i,service.isAvailableForDriver());
            statementUpdate.setObject(++i,service.isAvailableForEndUser());
            statementUpdate.setObject(++i,service.getTotalCharge());
            statementUpdate.setObject(++i,service.getTaxRate());
            statementUpdate.setObject(++i,service.getStaffPay());

            statementUpdate.setObject(++i,service.getServiceID());


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




    public int delete(int serviceID)
    {



        String deleteStatement =  " DELETE FROM " + Service.TABLE_NAME
                                + " WHERE " + Service.SERVICE_ID + " = ?";



        Connection connection= null;
        PreparedStatement statement = null;
        int rowCountDeleted = 0;


        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteStatement);
            statement.setInt(1,serviceID);

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






    public VehicleEndPoint getServicesListForAdmin(
            Boolean availableForDriver,
            Boolean availableForEndUser,



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



}
