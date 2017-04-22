package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.Model.VehicleType;
import org.taxireferral.api.ModelRoles.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sumeet on 18/4/17.
 */
public class VehicleTypeDAO {

    private HikariDataSource dataSource = Globals.getDataSource();

    // add vehicle type
    // update vehicle type
    // getVehicleTypes(string searchString, ) : getDifferent kinds of vehicles





    public int insertVehicleType(VehicleType vehicleType, boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertStatement = "INSERT INTO "
                + VehicleType.TABLE_NAME
                + "("

                + VehicleType.NAME + ","
                + VehicleType.IMAGE_PATH + ""

                + ") VALUES(?,?)";


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statement.setString(++i,vehicleType.getName());

//            statement.setString(++i,Ve.getPassword());
//            statement.setString(++i,user.getEmail());
//
//            statement.setString(++i,user.getPhone());
//            statement.setString(++i,user.getName());
//            statement.setObject(++i,user.isGender());
//
//            statement.setString(++i,user.getProfileImageURL());
//            statement.setObject(++i,user.getRole());
//            statement.setObject(++i,user.isAccountPrivate());
//            statement.setString(++i,user.getAbout());

            rowCountItems = statement.executeUpdate();


            ResultSet rs = statement.getGeneratedKeys();

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

            if (statement != null) {
                try {
                    statement.close();
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




}
