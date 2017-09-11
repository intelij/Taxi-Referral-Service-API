package org.taxireferral.api.DAOSettings;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelRoles.StaffPermissions;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.ModelSettings.ServiceConfigurationLocal;

import java.sql.*;

/**
 * Created by sumeet on 31/8/17.
 */
public class DAOServiceConfig {

    private HikariDataSource dataSource = Globals.getDataSource();


    /* Service Configuration : Begin */

//    updateServiceConfiguration(ServiceConfigurationLocal serviceConfig);
//    getServiceConfiguration();

    /* Service Configuration : End */




    public int updateServiceConfig(ServiceConfigurationLocal serviceConfig)
    {


        String insertUpdateServiceConfig =

                "INSERT INTO " + ServiceConfigurationLocal.TABLE_NAME
                        + "("
                        + ServiceConfigurationLocal.SERVICE_CONFIGURATION_ID + ","
                        + ServiceConfigurationLocal.LOGO_IMAGE_PATH + ","
                        + ServiceConfigurationLocal.BACKDROP_IMAGE_PATH + ","

                        + ServiceConfigurationLocal.SERVICE_NAME + ","
                        + ServiceConfigurationLocal.HELPLINE_NUMBER + ","

                        + ServiceConfigurationLocal.DESCRIPTION_SHORT + ","
                        + ServiceConfigurationLocal.DESCRIPTION_LONG + ","

                        + ServiceConfigurationLocal.ADDRESS + ","
                        + ServiceConfigurationLocal.CITY + ","
                        + ServiceConfigurationLocal.PINCODE + ","
                        + ServiceConfigurationLocal.LANDMARK + ","
                        + ServiceConfigurationLocal.STATE + ","
                        + ServiceConfigurationLocal.COUNTRY + ","

                        + ServiceConfigurationLocal.ISO_COUNTRY_CODE + ","
                        + ServiceConfigurationLocal.ISO_LANGUAGE_CODE + ","
                        + ServiceConfigurationLocal.ISO_CURRENCY_CODE + ","

                        + ServiceConfigurationLocal.LAT_CENTER + ","
                        + ServiceConfigurationLocal.LON_CENTER + ","
                        + ServiceConfigurationLocal.SERVICE_RANGE + ","

                        + ServiceConfigurationLocal.UPDATED + ""
                        + ") values(?,?,? ,?,?  ,?,? ,?,?,?,?,?,? ,?,?,? ,?,?,? ,?)"
                        + " ON CONFLICT (" + ServiceConfigurationLocal.SERVICE_CONFIGURATION_ID + ")"
                        + " DO UPDATE "
                        + " SET "
                        + ServiceConfigurationLocal.SERVICE_CONFIGURATION_ID + "= excluded." + ServiceConfigurationLocal.SERVICE_CONFIGURATION_ID + " , "
                        + ServiceConfigurationLocal.LOGO_IMAGE_PATH + "= excluded." + ServiceConfigurationLocal.LOGO_IMAGE_PATH + " , "
                        + ServiceConfigurationLocal.BACKDROP_IMAGE_PATH + "= excluded." + ServiceConfigurationLocal.BACKDROP_IMAGE_PATH + " , "

                        + ServiceConfigurationLocal.SERVICE_NAME + "= excluded." + ServiceConfigurationLocal.SERVICE_NAME + " , "
                        + ServiceConfigurationLocal.HELPLINE_NUMBER + "= excluded." + ServiceConfigurationLocal.HELPLINE_NUMBER + " , "

                        + ServiceConfigurationLocal.DESCRIPTION_SHORT + "= excluded." + ServiceConfigurationLocal.DESCRIPTION_SHORT + " , "
                        + ServiceConfigurationLocal.DESCRIPTION_LONG + "= excluded." + ServiceConfigurationLocal.DESCRIPTION_LONG + " , "

                        + ServiceConfigurationLocal.ADDRESS + "= excluded." + ServiceConfigurationLocal.ADDRESS + " , "
                        + ServiceConfigurationLocal.CITY + "= excluded." + ServiceConfigurationLocal.CITY + " , "
                        + ServiceConfigurationLocal.PINCODE + "= excluded." + ServiceConfigurationLocal.PINCODE + " , "
                        + ServiceConfigurationLocal.LANDMARK + "= excluded." + ServiceConfigurationLocal.LANDMARK + " , "
                        + ServiceConfigurationLocal.STATE + "= excluded." + ServiceConfigurationLocal.STATE + " , "
                        + ServiceConfigurationLocal.COUNTRY + "= excluded." + ServiceConfigurationLocal.COUNTRY + " , "


                        + ServiceConfigurationLocal.ISO_COUNTRY_CODE + "= excluded." + ServiceConfigurationLocal.ISO_COUNTRY_CODE + " , "
                        + ServiceConfigurationLocal.ISO_LANGUAGE_CODE + "= excluded." + ServiceConfigurationLocal.ISO_LANGUAGE_CODE + " , "
                        + ServiceConfigurationLocal.ISO_CURRENCY_CODE + "= excluded." + ServiceConfigurationLocal.ISO_CURRENCY_CODE + " , "

                        + ServiceConfigurationLocal.LAT_CENTER + "= excluded." + ServiceConfigurationLocal.LAT_CENTER + " , "
                        + ServiceConfigurationLocal.LON_CENTER + "= excluded." + ServiceConfigurationLocal.LON_CENTER + " , "
                        + ServiceConfigurationLocal.SERVICE_RANGE + "= excluded." + ServiceConfigurationLocal.SERVICE_RANGE + " , "

                        + ServiceConfigurationLocal.UPDATED + "= excluded." + ServiceConfigurationLocal.UPDATED + "";




        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);



            statement = connection.prepareStatement(insertUpdateServiceConfig);

            int i = 0;



            statement.setObject(++i,1);
            statement.setString(++i, serviceConfig.getLogoImagePath());
            statement.setString(++i, serviceConfig.getBackdropImagePath());

            statement.setString(++i, serviceConfig.getServiceName());
            statement.setString(++i, serviceConfig.getHelplineNumber());

            statement.setString(++i, serviceConfig.getDescriptionShort());
            statement.setString(++i, serviceConfig.getDescriptionLong());

            statement.setString(++i, serviceConfig.getAddress());
            statement.setString(++i, serviceConfig.getCity());
            statement.setObject(++i, serviceConfig.getPincode());
            statement.setString(++i, serviceConfig.getLandmark());
            statement.setString(++i, serviceConfig.getState());
            statement.setString(++i, serviceConfig.getCountry());

            statement.setString(++i, serviceConfig.getISOCountryCode());
            statement.setString(++i, serviceConfig.getISOLanguageCode());
            statement.setString(++i, serviceConfig.getISOCurrencyCode());

            statement.setObject(++i, serviceConfig.getLatCenter());
            statement.setObject(++i, serviceConfig.getLonCenter());
            statement.setObject(++i, serviceConfig.getServiceRange());

            statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));


            rowCountUpdated = statement.executeUpdate();
            System.out.println("Total rows updated: " + rowCountUpdated);



            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

//                    idOfInsertedRow=-1;
//                    rowCountItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
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

        return rowCountUpdated;
    }








    public ServiceConfigurationLocal getServiceConfig()
    {

        String query = "SELECT "

                + ServiceConfigurationLocal.SERVICE_CONFIGURATION_ID + ","
                + ServiceConfigurationLocal.LOGO_IMAGE_PATH + ","
                + ServiceConfigurationLocal.BACKDROP_IMAGE_PATH + ","

                + ServiceConfigurationLocal.SERVICE_NAME + ","
                + ServiceConfigurationLocal.HELPLINE_NUMBER + ","

                + ServiceConfigurationLocal.DESCRIPTION_SHORT + ","
                + ServiceConfigurationLocal.DESCRIPTION_LONG + ","

                + ServiceConfigurationLocal.ADDRESS + ","
                + ServiceConfigurationLocal.CITY + ","
                + ServiceConfigurationLocal.PINCODE + ","
                + ServiceConfigurationLocal.LANDMARK + ","
                + ServiceConfigurationLocal.STATE + ","
                + ServiceConfigurationLocal.COUNTRY + ","

                + ServiceConfigurationLocal.ISO_COUNTRY_CODE + ","
                + ServiceConfigurationLocal.ISO_LANGUAGE_CODE + ","
                + ServiceConfigurationLocal.ISO_CURRENCY_CODE + ","

                + ServiceConfigurationLocal.LAT_CENTER + ","
                + ServiceConfigurationLocal.LON_CENTER + ","
                + ServiceConfigurationLocal.SERVICE_RANGE + ","

                + ServiceConfigurationLocal.CREATED + ","
                + ServiceConfigurationLocal.UPDATED + ""

                + " FROM " + ServiceConfigurationLocal.TABLE_NAME
                + " WHERE " + ServiceConfigurationLocal.SERVICE_CONFIGURATION_ID  + " = 1 ";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        ServiceConfigurationLocal serviceConfig = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


//            statement.setObject(++i,userID); // username


            rs = statement.executeQuery();

            while(rs.next())
            {
                serviceConfig = new ServiceConfigurationLocal();

//                serviceConfig.setServiceID(rs.getInt(ServiceConfigurationLocal.SERVICE_CONFIGURATION_ID));
//                serviceConfig.setLogoImagePath(rs.getString(ServiceConfigurationLocal.LOGO_IMAGE_PATH));
//                serviceConfig.setRt_distance(rs.getDouble("distance"));


                serviceConfig.setServiceID(rs.getInt(ServiceConfigurationLocal.SERVICE_CONFIGURATION_ID));
                serviceConfig.setLogoImagePath(rs.getString(ServiceConfigurationLocal.LOGO_IMAGE_PATH));
                serviceConfig.setBackdropImagePath(rs.getString(ServiceConfigurationLocal.BACKDROP_IMAGE_PATH));

                serviceConfig.setServiceName(rs.getString(ServiceConfigurationLocal.SERVICE_NAME));
                serviceConfig.setHelplineNumber(rs.getString(ServiceConfigurationLocal.HELPLINE_NUMBER));

                serviceConfig.setDescriptionShort(rs.getString(ServiceConfigurationLocal.DESCRIPTION_SHORT));
                serviceConfig.setDescriptionLong(rs.getString(ServiceConfigurationLocal.DESCRIPTION_LONG));

                serviceConfig.setAddress(rs.getString(ServiceConfigurationLocal.ADDRESS));
                serviceConfig.setCity(rs.getString(ServiceConfigurationLocal.CITY));
                serviceConfig.setPincode(rs.getLong(ServiceConfigurationLocal.PINCODE));
                serviceConfig.setLandmark(rs.getString(ServiceConfigurationLocal.LANDMARK));
                serviceConfig.setState(rs.getString(ServiceConfigurationLocal.STATE));
                serviceConfig.setCountry(rs.getString(ServiceConfigurationLocal.COUNTRY));

                serviceConfig.setISOCountryCode(rs.getString(ServiceConfigurationLocal.ISO_COUNTRY_CODE));
                serviceConfig.setISOLanguageCode(rs.getString(ServiceConfigurationLocal.ISO_LANGUAGE_CODE));
                serviceConfig.setISOCurrencyCode(rs.getString(ServiceConfigurationLocal.ISO_CURRENCY_CODE));

                serviceConfig.setLatCenter(rs.getDouble(ServiceConfigurationLocal.LAT_CENTER));
                serviceConfig.setLonCenter(rs.getDouble(ServiceConfigurationLocal.LON_CENTER));
                serviceConfig.setServiceRange(rs.getInt(ServiceConfigurationLocal.SERVICE_RANGE));

                serviceConfig.setCreated(rs.getTimestamp(ServiceConfigurationLocal.CREATED));
                serviceConfig.setUpdated(rs.getTimestamp(ServiceConfigurationLocal.UPDATED));
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

        return serviceConfig;
    }







}
