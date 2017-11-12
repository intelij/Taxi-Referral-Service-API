package org.taxireferral.api.DAOSettings;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelSettings.ServiceConfiguration;

import java.sql.*;

/**
 * Created by sumeet on 31/8/17.
 */
public class DAOServiceConfig {

    private HikariDataSource dataSource = Globals.getDataSource();


    /* Service Configuration : Begin */

//    updateServiceConfiguration(ServiceConfiguration serviceConfig);
//    getServiceConfiguration();

    /* Service Configuration : End */




    public int updateServiceConfig(ServiceConfiguration serviceConfig)
    {


        String insertUpdateServiceConfig =

                "INSERT INTO " + ServiceConfiguration.TABLE_NAME
                        + "("
                        + ServiceConfiguration.SERVICE_CONFIGURATION_ID + ","
                        + ServiceConfiguration.LOGO_IMAGE_PATH + ","
                        + ServiceConfiguration.BACKDROP_IMAGE_PATH + ","

                        + ServiceConfiguration.SERVICE_NAME + ","
                        + ServiceConfiguration.HELPLINE_NUMBER + ","

                        + ServiceConfiguration.DESCRIPTION_SHORT + ","
                        + ServiceConfiguration.DESCRIPTION_LONG + ","

                        + ServiceConfiguration.ADDRESS + ","
                        + ServiceConfiguration.CITY + ","
                        + ServiceConfiguration.PINCODE + ","
                        + ServiceConfiguration.LANDMARK + ","
                        + ServiceConfiguration.STATE + ","
                        + ServiceConfiguration.COUNTRY + ","

                        + ServiceConfiguration.ISO_COUNTRY_CODE + ","
                        + ServiceConfiguration.ISO_LANGUAGE_CODE + ","
                        + ServiceConfiguration.ISO_CURRENCY_CODE + ","

                        + ServiceConfiguration.LAT_CENTER + ","
                        + ServiceConfiguration.LON_CENTER + ","
                        + ServiceConfiguration.SERVICE_RANGE + ","

                        + ServiceConfiguration.UPDATED + ","
                        + ServiceConfiguration.STYLE_URL + ""
                        + ") values(?,?,? ,?,?  ,?,? ,?,?,?,?,?,? ,?,?,? ,?,?,? ,?,?)"
                        + " ON CONFLICT (" + ServiceConfiguration.SERVICE_CONFIGURATION_ID + ")"
                        + " DO UPDATE "
                        + " SET "
                        + ServiceConfiguration.SERVICE_CONFIGURATION_ID + "= excluded." + ServiceConfiguration.SERVICE_CONFIGURATION_ID + " , "
                        + ServiceConfiguration.LOGO_IMAGE_PATH + "= excluded." + ServiceConfiguration.LOGO_IMAGE_PATH + " , "
                        + ServiceConfiguration.BACKDROP_IMAGE_PATH + "= excluded." + ServiceConfiguration.BACKDROP_IMAGE_PATH + " , "

                        + ServiceConfiguration.SERVICE_NAME + "= excluded." + ServiceConfiguration.SERVICE_NAME + " , "
                        + ServiceConfiguration.HELPLINE_NUMBER + "= excluded." + ServiceConfiguration.HELPLINE_NUMBER + " , "

                        + ServiceConfiguration.DESCRIPTION_SHORT + "= excluded." + ServiceConfiguration.DESCRIPTION_SHORT + " , "
                        + ServiceConfiguration.DESCRIPTION_LONG + "= excluded." + ServiceConfiguration.DESCRIPTION_LONG + " , "

                        + ServiceConfiguration.ADDRESS + "= excluded." + ServiceConfiguration.ADDRESS + " , "
                        + ServiceConfiguration.CITY + "= excluded." + ServiceConfiguration.CITY + " , "
                        + ServiceConfiguration.PINCODE + "= excluded." + ServiceConfiguration.PINCODE + " , "
                        + ServiceConfiguration.LANDMARK + "= excluded." + ServiceConfiguration.LANDMARK + " , "
                        + ServiceConfiguration.STATE + "= excluded." + ServiceConfiguration.STATE + " , "
                        + ServiceConfiguration.COUNTRY + "= excluded." + ServiceConfiguration.COUNTRY + " , "


                        + ServiceConfiguration.ISO_COUNTRY_CODE + "= excluded." + ServiceConfiguration.ISO_COUNTRY_CODE + " , "
                        + ServiceConfiguration.ISO_LANGUAGE_CODE + "= excluded." + ServiceConfiguration.ISO_LANGUAGE_CODE + " , "
                        + ServiceConfiguration.ISO_CURRENCY_CODE + "= excluded." + ServiceConfiguration.ISO_CURRENCY_CODE + " , "

                        + ServiceConfiguration.LAT_CENTER + "= excluded." + ServiceConfiguration.LAT_CENTER + " , "
                        + ServiceConfiguration.LON_CENTER + "= excluded." + ServiceConfiguration.LON_CENTER + " , "
                        + ServiceConfiguration.SERVICE_RANGE + "= excluded." + ServiceConfiguration.SERVICE_RANGE + " , "

                        + ServiceConfiguration.UPDATED + "= excluded." + ServiceConfiguration.UPDATED + ","
                        + ServiceConfiguration.STYLE_URL + "= excluded." + ServiceConfiguration.STYLE_URL + "";




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

            statement.setObject(++i,serviceConfig.getStyleURL());

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








    public ServiceConfiguration getServiceConfig()
    {

        String query = "SELECT "

                + ServiceConfiguration.SERVICE_CONFIGURATION_ID + ","
                + ServiceConfiguration.LOGO_IMAGE_PATH + ","
                + ServiceConfiguration.BACKDROP_IMAGE_PATH + ","

                + ServiceConfiguration.SERVICE_NAME + ","
                + ServiceConfiguration.HELPLINE_NUMBER + ","

                + ServiceConfiguration.DESCRIPTION_SHORT + ","
                + ServiceConfiguration.DESCRIPTION_LONG + ","

                + ServiceConfiguration.ADDRESS + ","
                + ServiceConfiguration.CITY + ","
                + ServiceConfiguration.PINCODE + ","
                + ServiceConfiguration.LANDMARK + ","
                + ServiceConfiguration.STATE + ","
                + ServiceConfiguration.COUNTRY + ","

                + ServiceConfiguration.ISO_COUNTRY_CODE + ","
                + ServiceConfiguration.ISO_LANGUAGE_CODE + ","
                + ServiceConfiguration.ISO_CURRENCY_CODE + ","

                + ServiceConfiguration.LAT_CENTER + ","
                + ServiceConfiguration.LON_CENTER + ","
                + ServiceConfiguration.SERVICE_RANGE + ","

                + ServiceConfiguration.CREATED + ","
                + ServiceConfiguration.UPDATED + ","

                + ServiceConfiguration.STYLE_URL + ""

                + " FROM " + ServiceConfiguration.TABLE_NAME
                + " WHERE " + ServiceConfiguration.SERVICE_CONFIGURATION_ID  + " = 1 ";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        ServiceConfiguration serviceConfig = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


//            statement.setObject(++i,userID); // username


            rs = statement.executeQuery();

            while(rs.next())
            {
                serviceConfig = new ServiceConfiguration();

//                serviceConfig.setServiceID(rs.getInt(ServiceConfiguration.SERVICE_CONFIGURATION_ID));
//                serviceConfig.setLogoImagePath(rs.getString(ServiceConfiguration.LOGO_IMAGE_PATH));
//                serviceConfig.setRt_distance(rs.getDouble("distance"));


                serviceConfig.setServiceID(rs.getInt(ServiceConfiguration.SERVICE_CONFIGURATION_ID));
                serviceConfig.setLogoImagePath(rs.getString(ServiceConfiguration.LOGO_IMAGE_PATH));
                serviceConfig.setBackdropImagePath(rs.getString(ServiceConfiguration.BACKDROP_IMAGE_PATH));

                serviceConfig.setServiceName(rs.getString(ServiceConfiguration.SERVICE_NAME));
                serviceConfig.setHelplineNumber(rs.getString(ServiceConfiguration.HELPLINE_NUMBER));

                serviceConfig.setDescriptionShort(rs.getString(ServiceConfiguration.DESCRIPTION_SHORT));
                serviceConfig.setDescriptionLong(rs.getString(ServiceConfiguration.DESCRIPTION_LONG));

                serviceConfig.setAddress(rs.getString(ServiceConfiguration.ADDRESS));
                serviceConfig.setCity(rs.getString(ServiceConfiguration.CITY));
                serviceConfig.setPincode(rs.getLong(ServiceConfiguration.PINCODE));
                serviceConfig.setLandmark(rs.getString(ServiceConfiguration.LANDMARK));
                serviceConfig.setState(rs.getString(ServiceConfiguration.STATE));
                serviceConfig.setCountry(rs.getString(ServiceConfiguration.COUNTRY));

                serviceConfig.setISOCountryCode(rs.getString(ServiceConfiguration.ISO_COUNTRY_CODE));
                serviceConfig.setISOLanguageCode(rs.getString(ServiceConfiguration.ISO_LANGUAGE_CODE));
                serviceConfig.setISOCurrencyCode(rs.getString(ServiceConfiguration.ISO_CURRENCY_CODE));

                serviceConfig.setLatCenter(rs.getDouble(ServiceConfiguration.LAT_CENTER));
                serviceConfig.setLonCenter(rs.getDouble(ServiceConfiguration.LON_CENTER));
                serviceConfig.setServiceRange(rs.getInt(ServiceConfiguration.SERVICE_RANGE));

                serviceConfig.setCreated(rs.getTimestamp(ServiceConfiguration.CREATED));
                serviceConfig.setUpdated(rs.getTimestamp(ServiceConfiguration.UPDATED));


//                serviceConfig.setStyleURL(rs.getString(ServiceConfiguration.STYLE_URL));
                serviceConfig.setStyleURL(GlobalConstants.TILESERVER_GL_STYLE_URL);
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
