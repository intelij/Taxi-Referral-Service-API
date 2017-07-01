package org.taxireferral.api.DAOs.Old;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.Deprecated.VehicleTypeOld;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sumeet on 18/4/17.
 */
public class VehicleTypeDAO {

    private HikariDataSource dataSource = Globals.getDataSource();

    //----------------------------------------------------------------------------
    // difference between an item and its version : Version has a Parent while for Item parent is null
    //
    // Item refers to an Entity which can be anything. Example of entity can be book, car, Product, etc

    // InsertItem and
        //Cases 1 : Insert made by admin which doesnt require an approval
        //  Insert (Insert an item without a parentID and isApproved TRUE)
        // Case 2 : New Item Submitted by user which needs to be approved
        // makeInsertSubmission (1. Insert an Item without a parent ID and isApproved FALSE
                                //  2. Timestamp approved and Timestamp Applied can be null because for submission not approved these fields will be set later)\

        // Case 3: An Update made to an existing Item which needs to be approved
        // makeUpdateSubmission (1. Insert an Item with isApproved False and give a parent ID)


        //


    //---------------
    // GeneralUpdate (1. Take a backup | 2. apply update from user input)

    // restore_a_version (restore a version) - restore a version (1. Take a backup if not exist 2. copy and apply update from table)
    // approve_an_update (1. Take a backup | 2. apply update | 3. Mark isApproved as true for the update)


    //----------------
    // RejectUpdate (1. Mark isApproved as False and do nothing)
    // approve_an_insert


    // delete Item : Item with all of its versions get deleted
    // delete Older Versions : (For Cleanup and optimization) Versions older than the latest 10-15 versions get deleted
    // get
    // getVersions




    public int insert(VehicleTypeOld vehicleType, boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertStatement = "INSERT INTO "
                + VehicleTypeOld.TABLE_NAME
                + "("

                + VehicleTypeOld.NAME + ","
                + VehicleTypeOld.IMAGE_PATH + ","
                + VehicleTypeOld.DESCRIPTION + ","

                + VehicleTypeOld.PARENT + ","
                + VehicleTypeOld.BACKUP_EXISTS + ","
                + VehicleTypeOld.TIMESTAMP_CREATED + ","
                + VehicleTypeOld.TIMESTAMP_UPDATED + ","
                + VehicleTypeOld.TIMESTAMP_APPLIED + ","
                + VehicleTypeOld.TIMESTAMP_APPROVED + ","

                + VehicleTypeOld.IS_APPROVED + ","
                + VehicleTypeOld.SUBMITTED_BY + ","
                + VehicleTypeOld.NOTES_FOR_REVIEWER + ","
//                + VehicleTypeOld.REVIEWER_FEEDBACK + "," // commented out because inserts will be reviewed later
//                + VehicleTypeOld.REVIEWED_BY + ""

                + ") VALUES(?,?,?, ?,?,?,?,?,? ,?,?,?)";



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statement.setString(++i,vehicleType.getName());
            statement.setString(++i,vehicleType.getImagePath());
            statement.setString(++i,vehicleType.getDescription());

            statement.setObject(++i,vehicleType.getParent());
            statement.setObject(++i,false); // backup does not exist in any case
            statement.setTimestamp(++i,vehicleType.getTimestampCreated());
            statement.setTimestamp(++i,vehicleType.getTimestampUpdated());
            statement.setTimestamp(++i,vehicleType.getTimestampApplied());
            statement.setTimestamp(++i,vehicleType.getTimestampApproved());

            statement.setObject(++i,vehicleType.isApproved());
            statement.setObject(++i,vehicleType.getSubmittedBy());
            statement.setString(++i,vehicleType.getNotesForReviewer());
//            statement.setString(++i,vehicleType.getReviewerFeedback());
//            statement.setObject(++i,vehicleType.getReviewedBy());

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




    public int generalUpdate(VehicleTypeOld vehicleType)
    {

        Connection connection = null;
        PreparedStatement statementBackup = null;
        PreparedStatement statementUpdate = null;

        String updateStatement = "";

        int idOfInsertedRow = -1;
        int rowCountItems = -1;
        int rowCountUpdatedItems = -1;


        String insertStatement = "INSERT INTO " + VehicleTypeOld.TABLE_NAME + "("
                                                    + VehicleTypeOld.NAME + ","
                                                    + VehicleTypeOld.IMAGE_PATH + ","
                                                    + VehicleTypeOld.DESCRIPTION + ","

                                                    + VehicleTypeOld.PARENT + ","

                                                    + VehicleTypeOld.TIMESTAMP_CREATED + ","
                                                    + VehicleTypeOld.TIMESTAMP_UPDATED + ","
                                                    + VehicleTypeOld.TIMESTAMP_APPLIED + ","
                                                    + VehicleTypeOld.TIMESTAMP_APPROVED + ","

                                                    + VehicleTypeOld.IS_APPROVED + ","
                                                    + VehicleTypeOld.SUBMITTED_BY + ","
                                                    + VehicleTypeOld.NOTES_FOR_REVIEWER + ","
                                                    + VehicleTypeOld.REVIEWER_FEEDBACK + ","
                                                    + VehicleTypeOld.REVIEWED_BY + "" + ") " +
                                " SELECT "
                                        + VehicleTypeOld.NAME + ","
                                        + VehicleTypeOld.IMAGE_PATH + ","
                                        + VehicleTypeOld.DESCRIPTION + ","

                                        + VehicleTypeOld.VEHICLE_TYPE_ID + ","

                                        + VehicleTypeOld.TIMESTAMP_CREATED + ","
                                        + VehicleTypeOld.TIMESTAMP_UPDATED + ","
                                        + VehicleTypeOld.TIMESTAMP_APPLIED + ","
                                        + VehicleTypeOld.TIMESTAMP_APPROVED + ","

                                        + VehicleTypeOld.IS_APPROVED + ","
                                        + VehicleTypeOld.SUBMITTED_BY + ","
                                        + VehicleTypeOld.NOTES_FOR_REVIEWER + ","
                                        + VehicleTypeOld.REVIEWER_FEEDBACK + ","
                                        + VehicleTypeOld.REVIEWED_BY + "" +
                                " FROM " + VehicleTypeOld.TABLE_NAME +
                                " WHERE " + VehicleTypeOld.VEHICLE_TYPE_ID + " = ?" +
                                " AND " + VehicleTypeOld.BACKUP_EXISTS + " = FALSE ";


        updateStatement = "UPDATE " + VehicleTypeOld.TABLE_NAME
                                + " SET "

                                + VehicleTypeOld.NAME + " = ?,"
                                + VehicleTypeOld.IMAGE_PATH + " = ?,"
                                + VehicleTypeOld.DESCRIPTION + "=?,"

                                + VehicleTypeOld.BACKUP_EXISTS + "= FALSE," // false because no backup version exist for this submission
                                + VehicleTypeOld.TIMESTAMP_APPLIED + "= now() ,"
                                + VehicleTypeOld.TIMESTAMP_APPROVED + "= ?,"

//                                + VehicleTypeOld.IS_APPROVED + " = true," // Important : we do not change is approved here because a non-approved item can also have versions and updates
                                + VehicleTypeOld.SUBMITTED_BY + "=?,"

                        + " WHERE " + VehicleTypeOld.VEHICLE_TYPE_ID + " = ?";



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementBackup = connection.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;
            statementBackup.setObject(++i,vehicleType.getVehicleTypeID());

            rowCountItems = statementBackup.executeUpdate();
            ResultSet rs = statementBackup.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }


            statementUpdate = connection.prepareStatement(updateStatement,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0; // reset flag counter

            statementUpdate.setString(++i,vehicleType.getName());
            statementUpdate.setString(++i,vehicleType.getImagePath());
            statementUpdate.setString(++i,vehicleType.getDescription());

//            statementUpdate.setObject(++i, vehicleType.isBackupExists());
            statementUpdate.setTimestamp(++i,vehicleType.getTimestampApproved());

            statementUpdate.setObject(++i,vehicleType.getSubmittedBy());

            rowCountUpdatedItems = statementUpdate.executeUpdate();


            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

                    idOfInsertedRow= -1;
                    rowCountItems = 0;
                    rowCountUpdatedItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {

            if (statementBackup != null) {
                try {
                    statementBackup.close();
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

        return rowCountUpdatedItems;
    }







//    VehicleTypeOld vehicleType
    public int restore_a_version(int versionID)
    {

        Connection connection = null;
        PreparedStatement statementBackup = null;
        PreparedStatement statementUpdateApplied = null;
        PreparedStatement statementUpdate = null;

        String updateStatement = "";
        String updateVersion = "";
        String mark_approved = "";

        int idOfInsertedRow = -1;
        int rowCountItems = -1;
        int rowCountUpdatedItems = -1;


        // we are taking a backup of the Item if the backup for it does not already exist
        String insertStatement = "INSERT INTO " + VehicleTypeOld.TABLE_NAME + "("
                + VehicleTypeOld.NAME + ","
                + VehicleTypeOld.IMAGE_PATH + ","
                + VehicleTypeOld.DESCRIPTION + ","

                + VehicleTypeOld.PARENT + ","

                + VehicleTypeOld.TIMESTAMP_CREATED + ","
                + VehicleTypeOld.TIMESTAMP_UPDATED + ","
                + VehicleTypeOld.TIMESTAMP_APPLIED + ","
                + VehicleTypeOld.TIMESTAMP_APPROVED + ","

                + VehicleTypeOld.IS_APPROVED + ","
                + VehicleTypeOld.SUBMITTED_BY + ","
                + VehicleTypeOld.NOTES_FOR_REVIEWER + ","
                + VehicleTypeOld.REVIEWER_FEEDBACK + ","
                + VehicleTypeOld.REVIEWED_BY + "" + ") " +

                " SELECT "
                            + "items." + VehicleTypeOld.NAME + ","
                            + "items." + VehicleTypeOld.IMAGE_PATH + ","
                            + "items." + VehicleTypeOld.DESCRIPTION + ","

                            + "items." + VehicleTypeOld.VEHICLE_TYPE_ID + ","

                            + "items." + VehicleTypeOld.TIMESTAMP_CREATED + ","
                            + "items." + VehicleTypeOld.TIMESTAMP_UPDATED + ","
                            + "items." + VehicleTypeOld.TIMESTAMP_APPLIED + ","
                            + "items." + VehicleTypeOld.TIMESTAMP_APPROVED + ","

                            + "items." + VehicleTypeOld.IS_APPROVED + ","
                            + "items." + VehicleTypeOld.SUBMITTED_BY + ","
                            + "items." + VehicleTypeOld.NOTES_FOR_REVIEWER + ","
                            + "items." + VehicleTypeOld.REVIEWER_FEEDBACK + ","
                            + "items." + VehicleTypeOld.REVIEWED_BY + "" +
                " FROM " + VehicleTypeOld.TABLE_NAME + " as items" +
                " INNER JOIN " + VehicleTypeOld.TABLE_NAME + " as versions" + " ON (" + "items." + VehicleTypeOld.VEHICLE_TYPE_ID + " = " + "versions." + VehicleTypeOld.PARENT + ")" +
                " WHERE " + "versions." + VehicleTypeOld.VEHICLE_TYPE_ID + " = ?" +
                " AND " + "items." + VehicleTypeOld.BACKUP_EXISTS + " = FALSE ";



        mark_approved = "UPDATE " + VehicleTypeOld.TABLE_NAME
                + " SET "
//                + VehicleTypeOld.IS_APPROVED + " = TRUE,"
                + VehicleTypeOld.TIMESTAMP_APPLIED + " = now(),"
//                + VehicleTypeOld.TIMESTAMP_APPROVED + " = now()"
//                + VehicleTypeOld.REVIEWED_BY + " = ?"
                + " WHERE " + VehicleTypeOld.VEHICLE_TYPE_ID + " = ?";



        //
        updateStatement = "UPDATE " + VehicleTypeOld.TABLE_NAME
                + " SET "

                + VehicleTypeOld.NAME + " = " + VehicleTypeOld.NAME +","
                + VehicleTypeOld.IMAGE_PATH + " = " + VehicleTypeOld.IMAGE_PATH + ","
                + VehicleTypeOld.DESCRIPTION + " = " + VehicleTypeOld.DESCRIPTION + ","

                + VehicleTypeOld.BACKUP_EXISTS + " = TRUE ," // true because backup version for this already exist
//                + VehicleTypeOld.TIMESTAMP_APPLIED + "=" + VehicleTypeOld.TIMESTAMP_APPLIED + ","
//                + VehicleTypeOld.TIMESTAMP_APPROVED + "= ?,"

//                + VehicleTypeOld.IS_APPROVED + " = true," // dont change the field let is keep as it is
                + VehicleTypeOld.SUBMITTED_BY + "= " + VehicleTypeOld.SUBMITTED_BY + "," +

                " FROM " + VehicleTypeOld.TABLE_NAME +
                " WHERE " + VehicleTypeOld.VEHICLE_TYPE_ID + " = ?" ; // version ID of the version that is being applied



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementBackup = connection.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;
            statementBackup.setObject(++i,versionID);

            rowCountItems = statementBackup.executeUpdate();
            ResultSet rs = statementBackup.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }


            statementUpdateApplied = connection.prepareStatement(mark_approved,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0; // reset the flag

            statementUpdateApplied.setObject(++i,versionID);
            statementUpdateApplied.executeUpdate();




            statementUpdate = connection.prepareStatement(updateStatement,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0; // reset flag counter

            statementUpdate.setObject(++i,versionID);
            rowCountUpdatedItems = statementUpdate.executeUpdate();


            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

                    idOfInsertedRow= -1;
                    rowCountItems = 0;
                    rowCountUpdatedItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {

            if (statementUpdateApplied != null) {
                try {
                    statementUpdateApplied.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statementBackup != null) {
                try {
                    statementBackup.close();
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

        return rowCountUpdatedItems;
    }






    // 1. make a backup if backup not exist 2. mark approved to the update 3. apply update
    public int approve_an_update(int versionID, int reviewedByID)
    {

        Connection connection = null;
        PreparedStatement statementBackup = null;
        PreparedStatement statementMarkApproved = null;
        PreparedStatement statementUpdate = null;

        String updateStatement = "";
        String mark_approved = "";

        int idOfInsertedRow = -1;
        int rowCountItems = -1;
        int rowCountUpdatedItems = -1;


        // we are taking a backup of the Item if the backup for it does not already exist
        String insertStatement = "INSERT INTO " + VehicleTypeOld.TABLE_NAME + "("
                + VehicleTypeOld.NAME + ","
                + VehicleTypeOld.IMAGE_PATH + ","
                + VehicleTypeOld.DESCRIPTION + ","

                + VehicleTypeOld.PARENT + ","

                + VehicleTypeOld.TIMESTAMP_CREATED + ","
                + VehicleTypeOld.TIMESTAMP_UPDATED + ","
                + VehicleTypeOld.TIMESTAMP_APPLIED + ","
                + VehicleTypeOld.TIMESTAMP_APPROVED + ","

                + VehicleTypeOld.IS_APPROVED + ","
                + VehicleTypeOld.SUBMITTED_BY + ","
                + VehicleTypeOld.NOTES_FOR_REVIEWER + ","
                + VehicleTypeOld.REVIEWER_FEEDBACK + ","
                + VehicleTypeOld.REVIEWED_BY + "" + ") " +

                " SELECT "
                + "items." + VehicleTypeOld.NAME + ","
                + "items." + VehicleTypeOld.IMAGE_PATH + ","
                + "items." + VehicleTypeOld.DESCRIPTION + ","

                + "items." + VehicleTypeOld.VEHICLE_TYPE_ID + ","

                + "items." + VehicleTypeOld.TIMESTAMP_CREATED + ","
                + "items." + VehicleTypeOld.TIMESTAMP_UPDATED + ","
                + "items." + VehicleTypeOld.TIMESTAMP_APPLIED + ","
                + "items." + VehicleTypeOld.TIMESTAMP_APPROVED + ","

                + "items." + VehicleTypeOld.IS_APPROVED + ","
                + "items." + VehicleTypeOld.SUBMITTED_BY + ","
                + "items." + VehicleTypeOld.NOTES_FOR_REVIEWER + ","
                + "items." + VehicleTypeOld.REVIEWER_FEEDBACK + ","
                + "items." + VehicleTypeOld.REVIEWED_BY + "" +
                " FROM " + VehicleTypeOld.TABLE_NAME + " as items" +
                " INNER JOIN " + VehicleTypeOld.TABLE_NAME + " as versions" + " ON (" + "items." + VehicleTypeOld.VEHICLE_TYPE_ID + " = " + "versions." + VehicleTypeOld.PARENT + ")" +
                " WHERE " + "versions." + VehicleTypeOld.VEHICLE_TYPE_ID + " = ?" +
                " AND " + "items." + VehicleTypeOld.BACKUP_EXISTS + " = FALSE ";



        mark_approved = "UPDATE " + VehicleTypeOld.TABLE_NAME
                        + " SET "
                        + VehicleTypeOld.IS_APPROVED + " = TRUE,"
                        + VehicleTypeOld.TIMESTAMP_APPLIED + " = now(),"
                        + VehicleTypeOld.TIMESTAMP_APPROVED + " = now()"
                        + VehicleTypeOld.REVIEWED_BY + " = ?"
                        + " WHERE " + VehicleTypeOld.VEHICLE_TYPE_ID + " = ?";


        //
        updateStatement = "UPDATE " + VehicleTypeOld.TABLE_NAME
                + " SET "

                + VehicleTypeOld.NAME + " = " + VehicleTypeOld.NAME +","
                + VehicleTypeOld.IMAGE_PATH + " = " + VehicleTypeOld.IMAGE_PATH + ","
                + VehicleTypeOld.DESCRIPTION + " = " + VehicleTypeOld.DESCRIPTION + ","

                + VehicleTypeOld.BACKUP_EXISTS + "= TRUE ," // true because backup version for this already exist
//                + VehicleTypeOld.TIMESTAMP_APPLIED + "=" + VehicleTypeOld.TIMESTAMP_APPLIED + ","
//                + VehicleTypeOld.TIMESTAMP_APPROVED + "= ?,"

                + VehicleTypeOld.IS_APPROVED + " = true,"
                + VehicleTypeOld.SUBMITTED_BY + " = " + VehicleTypeOld.SUBMITTED_BY + "," +

                " FROM " + VehicleTypeOld.TABLE_NAME +
                " WHERE " + VehicleTypeOld.VEHICLE_TYPE_ID + " = ?" ; // version ID of the version that is being applied



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementBackup = connection.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;
            statementBackup.setObject(++i,versionID);

            rowCountItems = statementBackup.executeUpdate();
            ResultSet rs = statementBackup.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }



            statementMarkApproved = connection.prepareStatement(mark_approved,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0; // reset the flag

            statementMarkApproved.setObject(++i,reviewedByID);
            statementMarkApproved.setObject(++i,versionID);
            statementMarkApproved.executeUpdate();



            statementUpdate = connection.prepareStatement(updateStatement,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0; // reset flag counter

            statementUpdate.setObject(++i,versionID);
            rowCountUpdatedItems = statementUpdate.executeUpdate();


            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

                    idOfInsertedRow= -1;
                    rowCountItems = 0;
                    rowCountUpdatedItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {

            if (statementMarkApproved != null) {
                try {
                    statementMarkApproved.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statementBackup != null) {
                try {
                    statementBackup.close();
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

        return rowCountUpdatedItems;
    }




}
