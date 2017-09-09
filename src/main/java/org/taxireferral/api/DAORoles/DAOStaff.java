package org.taxireferral.api.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelEndpoints.UserEndpoint;
import org.taxireferral.api.ModelEndpoints.VehicleEndPoint;
import org.taxireferral.api.ModelRoles.PhoneVerificationCode;
import org.taxireferral.api.ModelRoles.StaffPermissions;
import org.taxireferral.api.ModelRoles.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sumeet on 28/8/17.
 */
public class DAOStaff {

    private HikariDataSource dataSource = Globals.getDataSource();


    /*

        saveStaffByAdmin(User staff)
    *  getStaffPublic()
    *  getStaffForAdmin()
    *
    *
    *
    * */





    public int updateUserByAdmin(User user)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "

                + User.NAME + "=?,"
                + User.GENDER + "=?,"

                + User.PROFILE_IMAGE_URL + "=?,"
                + User.IS_ACCOUNT_PRIVATE + "=?,"
                + User.ABOUT + "=?,"
                + User.IS_VERIFIED + "=?"

                + " WHERE " + User.USER_ID + " = ?";





        String insertStaffPermissions =

                "INSERT INTO " + StaffPermissions.TABLE_NAME
                        + "("
                        + StaffPermissions.STAFF_ID + ","
                        + StaffPermissions.DESIGNATION + ","
                        + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + ""
                        + ") values(?,?,?)"
                        + " ON CONFLICT (" + StaffPermissions.STAFF_ID + ")"
                        + " DO UPDATE "
                        + " SET " + StaffPermissions.DESIGNATION + "= excluded." + StaffPermissions.DESIGNATION + " , "
                        + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + "= excluded." + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL;




        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);



            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setString(++i,user.getName());
            statement.setObject(++i,user.getGender());

            statement.setString(++i,user.getProfileImagePath());
            statement.setObject(++i,user.isAccountPrivate());
            statement.setString(++i,user.getAbout());
            statement.setObject(++i,user.isVerified());

            statement.setObject(++i,user.getUserID());


            rowCountUpdated = statement.executeUpdate();
            System.out.println("Total rows updated: " + rowCountUpdated);


            statement = connection.prepareStatement(insertStaffPermissions,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0;

            StaffPermissions permissions = user.getRt_staff_permissions();

            if(permissions!=null)
            {
                statement.setObject(++i,user.getUserID());
                statement.setString(++i,permissions.getDesignation());
                statement.setObject(++i,permissions.isPermitTaxiRegistrationAndRenewal());

                statement.executeUpdate();
            }






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









    public UserEndpoint getStaffForAdmin(
            Boolean gender,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata
    ) {


        boolean isfirst = true;

        String queryCount = "";


        String queryJoin = "SELECT DISTINCT "


                + User.TABLE_NAME + "." + User.USER_ID + ","
                + User.TABLE_NAME + "." + User.USERNAME + ","
                + User.TABLE_NAME + "." + User.E_MAIL + ","
                + User.TABLE_NAME + "." + User.PHONE + ","

                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","

                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ","
                + User.TABLE_NAME + "." + User.IS_ACCOUNT_PRIVATE + ","
                + User.TABLE_NAME + "." + User.ABOUT + ","

                + User.TABLE_NAME + "." + User.TIMESTAMP_CREATED + ","
                + User.TABLE_NAME + "." + User.IS_VERIFIED + ","

                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.DESIGNATION + ","
                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + ""

                + " FROM " + User.TABLE_NAME
                + " LEFT OUTER JOIN " + StaffPermissions.TABLE_NAME + " ON (" + StaffPermissions.TABLE_NAME + "." + StaffPermissions.STAFF_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE TRUE "
                + " AND " + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_STAFF_CODE;




        if(gender != null)
        {
            queryJoin = queryJoin + " AND " + User.TABLE_NAME + "." + User.GENDER + " = ?";
        }
//



        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMISSION_ID + ","
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


        UserEndpoint endPoint = new UserEndpoint();

        ArrayList<User> itemList = new ArrayList<>();
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

                    User user = new User();

                    user.setUserID(rs.getInt(User.USER_ID));
                    user.setUsername(rs.getString(User.USERNAME));
                    user.setEmail(rs.getString(User.E_MAIL));
                    user.setPhone(rs.getString(User.PHONE));


                    user.setName(rs.getString(User.NAME));
                    user.setGender(rs.getBoolean(User.GENDER));


                    user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
                    user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                    user.setAbout(rs.getString(User.ABOUT));

                    user.setTimestampCreated(rs.getTimestamp(User.TIMESTAMP_CREATED));
                    user.setVerified(rs.getBoolean(User.IS_VERIFIED));

                    StaffPermissions permissions = new StaffPermissions();

                    permissions.setDesignation(rs.getString(StaffPermissions.DESIGNATION));
                    permissions.setPermitTaxiRegistrationAndRenewal(rs.getBoolean(StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL));

                    user.setRt_staff_permissions(permissions);

                    itemList.add(user);
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






}
