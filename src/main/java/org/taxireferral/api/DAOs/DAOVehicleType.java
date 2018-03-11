package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.Model.VehicleType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOVehicleType {

    private HikariDataSource dataSource = Globals.getDataSource();


    // insert vehicle type
    // update vehicle type
    // delete vehicle type
    // get single
    // get many admin




    public int insertVehicleType(VehicleType vehicleType, boolean getRowCount)
    {
        Connection connection = null;
        PreparedStatement statementInsert = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;


        String insert = "";


        insert = "INSERT INTO "
                + VehicleType.TABLE_NAME
                + "("

                + VehicleType.NAME + ","
                + VehicleType.IMAGE_PATH + ","
                + VehicleType.DESCRIPTION + ","
                + VehicleType.MAX_MIN_TRIP_CHARGE + ","
                + VehicleType.MAX_CHARGES_PER_KM + ""
                + ") "
                + "VALUES(?,?,?, ?,?)";




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementInsert = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementInsert.setObject(++i,vehicleType.getName());
            statementInsert.setString(++i,vehicleType.getImagePath());

            statementInsert.setObject(++i,vehicleType.getDescription());
            statementInsert.setObject(++i,vehicleType.getMaxMinTripCharges());
            statementInsert.setObject(++i,vehicleType.getMaxChargesPerKm());


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






    public int updateVehicleType(VehicleType vehicleType)
    {



        Connection connection = null;
        PreparedStatement statementUpdate = null;

        int rowCountItems = -1;

        String update = "";



        update =  " UPDATE " + VehicleType.TABLE_NAME
                + " SET "    + VehicleType.NAME    + "=?,"
                             + VehicleType.IMAGE_PATH      + "=?,"
                            + VehicleType.DESCRIPTION      + "=?,"
                            + VehicleType.MAX_MIN_TRIP_CHARGE    + "=?,"
                            + VehicleType.MAX_CHARGES_PER_KM      + "=?"
                + " WHERE "  + VehicleType.VEHICLE_TYPE_ID        + " = ?";





        try {

            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;



            statementUpdate.setObject(++i,vehicleType.getName());
            statementUpdate.setString(++i,vehicleType.getImagePath());

            statementUpdate.setObject(++i,vehicleType.getDescription());
            statementUpdate.setObject(++i,vehicleType.getMaxMinTripCharges());
            statementUpdate.setObject(++i,vehicleType.getMaxChargesPerKm());

            statementUpdate.setObject(++i,vehicleType.getVehicleTypeID());

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





    public int deleteVehicle(int vehicleTypeID)
    {

        String deleteStatement =  " DELETE FROM " + VehicleType.TABLE_NAME
                                + " WHERE " + VehicleType.VEHICLE_TYPE_ID + " = ?";


        Connection connection= null;
        PreparedStatement statement = null;
        int rowCountDeleted = 0;


        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteStatement);
            statement.setInt(1,vehicleTypeID);

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



}
