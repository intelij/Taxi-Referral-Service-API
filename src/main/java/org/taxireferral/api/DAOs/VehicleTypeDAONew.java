package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.VehicleType;
import org.taxireferral.api.Model.VehicleTypeOld;
import org.taxireferral.api.Model.VehicleTypeVersion;

import java.sql.*;

/**
 * Created by sumeet on 25/4/17.
 */
public class VehicleTypeDAONew {


    private HikariDataSource dataSource = Globals.getDataSource();


    /*******
     * Important points to understand here :
     *
     * In this file we are designing a versioning and review submission system for a model.
     * A model can be any entity which can be like a blog_post, a vehicle, a book etc. Typically each model has one table in the database.
     *
     * In certain situations we want to enable versioning for the model table.
     * This requirement will generally arise if more than one person is updating a model.
     * It could be situation where we have a community based wiki database. Any person could update or add an entry into the model table of the database.
     * Consider we have a table of books which stores the authors, title, description, isbn for each book.
     * We can have a scenario where we might allow all the members of a community or every person the ability to add a book or update a book.
     *
     * In such a scenario we would want to save versions of our model. Each row in a model table can be updated by more than one person.
     * In this situation we need a versioning system and also a submission review system.
     * Because if an unwanted update happens to a row in a model table we can always restore our model to a previous version.
     *
     *
     *
     *
     * There are two tables one is the actual
     *
     * parentID in version table represents
     *
     *
     *
     *
     *
     *
     */



    public int insert_an_entry(VehicleTypeVersion vehicleType, boolean getRowCount)
    {
        Connection connection = null;
        PreparedStatement statementInsert = null;
        PreparedStatement statementVersion = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;


        int idOfInsertedRowVersion = -1;
        int rowCountVersion = -1;

        String insert = "";
        String insertVersion = "";

        insert = "INSERT INTO "
                        + VehicleType.TABLE_NAME
                        + "(" + VehicleType.NAME + ","
                                + VehicleType.IMAGE_PATH + ","
                                + VehicleType.DESCRIPTION + "" + ") "
                + "VALUES(?,?,?)";



        insertVersion = "INSERT INTO "
                            + VehicleTypeVersion.TABLE_NAME
                            + "(" + VehicleTypeVersion.NAME + ","
                                    + VehicleTypeVersion.IMAGE_PATH + ","
                                    + VehicleTypeVersion.DESCRIPTION + ","

                                    + VehicleTypeVersion.PARENT + ","
                                    + VehicleTypeVersion.SUBMITTED_BY + ","
                                    + VehicleTypeVersion.TIMESTAMP_APPLIED + ","
                                    + VehicleTypeVersion.TIMESTAMP_APPROVED + ","

                                    + VehicleTypeVersion.IS_APPROVED + ""
                                + ") " +
                            "VALUES(?,?,?, ?,?,?,? ,?)";


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementInsert = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementInsert.setString(++i,vehicleType.getName());
            statementInsert.setString(++i,vehicleType.getImagePath());
            statementInsert.setString(++i,vehicleType.getDescription());


            rowCountItems = statementInsert.executeUpdate();
            ResultSet rs = statementInsert.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }


            statementVersion = connection.prepareStatement(insertVersion,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0;

            statementVersion.setString(++i,vehicleType.getName());
            statementVersion.setString(++i,vehicleType.getImagePath());
            statementVersion.setString(++i,vehicleType.getDescription());

            statementVersion.setObject(++i,idOfInsertedRow);
            statementVersion.setObject(++i,vehicleType.getSubmittedBy());
            statementVersion.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));
            statementVersion.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));
            statementVersion.setObject(++i,true);  // approval is true for all approved inserts

            rowCountVersion = statementVersion.executeUpdate();
            ResultSet rs_version = statementInsert.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRowVersion = rs_version.getInt(1);
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


            if (statementVersion != null) {
                try {
                    statementVersion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


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







    public int update_an_entry(VehicleTypeVersion vehicleType, boolean getRowCount)
    {
        Connection connection = null;
        PreparedStatement statementUpdate = null;
        PreparedStatement statementVersion = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String update = "";
        String version = "";

        update = "UPDATE " + VehicleType.TABLE_NAME
                + " SET "
                    + VehicleType.NAME + "=?,"
                    + VehicleType.IMAGE_PATH + "=?,"
                    + VehicleType.DESCRIPTION + "=?"
                + " WHERE " + VehicleType.VEHICLE_TYPE_ID + " = ?";


        version = "INSERT INTO "
                    + VehicleTypeVersion.TABLE_NAME
                    + "(" + VehicleTypeVersion.NAME + ","
                            + VehicleTypeVersion.IMAGE_PATH + ","
                            + VehicleTypeVersion.DESCRIPTION + ","

                            + VehicleTypeVersion.PARENT + ","
                            + VehicleTypeVersion.SUBMITTED_BY + ","
                            + VehicleTypeVersion.TIMESTAMP_APPLIED + ","
                            + VehicleTypeVersion.TIMESTAMP_APPROVED + ","

                            + VehicleTypeVersion.IS_APPROVED + "" + ") " +
                "VALUES(?,?,?, ?,?,?,?, ?)";


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statementUpdate.setString(++i,vehicleType.getName());
            statementUpdate.setString(++i,vehicleType.getImagePath());
            statementUpdate.setString(++i,vehicleType.getDescription());
            statementUpdate.setObject(++i,vehicleType.getVersionID());

            rowCountItems = statementUpdate.executeUpdate();




            statementVersion = connection.prepareStatement(version,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0;

            statementVersion.setString(++i,vehicleType.getName());
            statementVersion.setString(++i,vehicleType.getImagePath());
            statementVersion.setString(++i,vehicleType.getDescription());

            statementVersion.setObject(++i,vehicleType.getVersionID());
            statementVersion.setObject(++i,vehicleType.getSubmittedBy());
            statementVersion.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));
            statementVersion.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));

            statementVersion.setObject(++i,true);  // approval is true for all approved inserts

            statementVersion.executeUpdate();


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


            if (statementVersion != null) {
                try {
                    statementVersion.close();
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

        if(getRowCount)
        {
            return rowCountItems;
        }
        else
        {
            return idOfInsertedRow;
        }
    }






    public int restore_a_version(int version_id)
    {

        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statementVersion = null;

//        int idOfInsertedRow = -1;
        int rowCount = 0;

//        int idOfInsertedRowVersion = -1;
        int rowCountVersion = 0;

        String update = "";
        String version = "";


        // copy data from versions table to actual table
        update =  " UPDATE "+ VehicleType.TABLE_NAME
                + " SET "   + VehicleType.NAME + "=" + VehicleTypeVersion.NAME + ","
                            + VehicleType.IMAGE_PATH + "=" + VehicleTypeVersion.IMAGE_PATH + ","
                            + VehicleType.DESCRIPTION + "=" + VehicleTypeVersion.DESCRIPTION + ""
                + " FROM "  + VehicleTypeVersion.TABLE_NAME
                + " WHERE " + VehicleType.TABLE_NAME + "." + VehicleType.VEHICLE_TYPE_ID + " = " + VehicleTypeVersion.TABLE_NAME + "." + VehicleTypeVersion.PARENT
                + " AND "   + VehicleTypeVersion.TABLE_NAME + "." + VehicleTypeVersion.VERSION_ID + " = ?"; // version id goes here


        // update the metadata of the version entry
        version =  " UPDATE "+ VehicleTypeVersion.TABLE_NAME
                + " SET "   + VehicleTypeVersion.TIMESTAMP_APPLIED + "= now()"
                + " WHERE " + VehicleTypeVersion.VERSION_ID + " = ?"; // version id goes here



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statement.setObject(++i,version_id);
            rowCount = statement.executeUpdate();


            statementVersion = connection.prepareStatement(version,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0;
            statement.setObject(++i,version_id);
            rowCountVersion = statementVersion.executeUpdate();


            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

//                    idOfInsertedRow=-1;
                    rowCount = 0;
                    rowCountVersion = 0;
//                    idOfInsertedRow

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {


            if (statementVersion != null) {
                try {
                    statementVersion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


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


        return (rowCount + rowCountVersion);
    }



        public int submit_an_entry(VehicleTypeVersion vehicleType, boolean getRowCount)
        {

            Connection connection = null;
            PreparedStatement statement = null;

            int idOfInsertedRow = -1;
            int rowCount = 0;

            String insert = "";


            insert = "INSERT INTO "
                    + VehicleTypeVersion.TABLE_NAME
                    + "(" + VehicleTypeVersion.NAME + ","
                            + VehicleTypeVersion.IMAGE_PATH + ","
                            + VehicleTypeVersion.DESCRIPTION + ","

                            + VehicleTypeVersion.PARENT + ","
                            + VehicleTypeVersion.SUBMITTED_BY + ","
                            + VehicleTypeVersion.NOTES_FOR_REVIEWER + "" + ") " +
                    "VALUES(?,?,?, ?,?,?)";


            try {

                connection = dataSource.getConnection();
                connection.setAutoCommit(false);


                statement = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
                int i = 0;

                statement.setString(++i,vehicleType.getName());
                statement.setString(++i,vehicleType.getImagePath());
                statement.setString(++i,vehicleType.getDescription());

                if(vehicleType.getVersionID()==0)
                {
                    statement.setObject(++i,null);
                }
                else
                {
                    statement.setObject(++i,vehicleType.getVersionID());
                }

                statement.setObject(++i,vehicleType.getSubmittedBy());
                statement.setString(++i,vehicleType.getNotesForReviewer());

                rowCount = statement.executeUpdate();

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
                        rowCount = 0;

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
                return rowCount;
            }
            else
            {
                return idOfInsertedRow;
            }
        }






        public int approve_an_update(VehicleTypeVersion vehicleTypeVersion)
        {

            Connection connection = null;
            PreparedStatement statement = null;
            PreparedStatement statementVersion = null;

//        int idOfInsertedRow = -1;
            int rowCount = 0;

//        int idOfInsertedRowVersion = -1;
            int rowCountVersion = 0;

            String update = "";
            String version = "";


            // copy data from versions table to actual table
            update =  " UPDATE "+ VehicleType.TABLE_NAME
                    + " SET "   + VehicleType.NAME + "=" + VehicleTypeVersion.NAME + ","
                                + VehicleType.IMAGE_PATH + "=" + VehicleTypeVersion.IMAGE_PATH + ","
                                + VehicleType.DESCRIPTION + "=" + VehicleTypeVersion.DESCRIPTION + ""
                    + " FROM "  + VehicleTypeVersion.TABLE_NAME
                    + " WHERE " + VehicleType.TABLE_NAME + "." + VehicleType.VEHICLE_TYPE_ID + " = " + VehicleTypeVersion.TABLE_NAME + "." + VehicleTypeVersion.PARENT
                    + " AND "   + VehicleTypeVersion.TABLE_NAME + "." + VehicleTypeVersion.VERSION_ID + " = ?"; // version id goes here


            // update the metadata of the version entry
            version =   " UPDATE "+ VehicleTypeVersion.TABLE_NAME +
                        " SET "   + VehicleTypeVersion.TIMESTAMP_APPLIED + "= now(),"
                                  + VehicleTypeVersion.TIMESTAMP_APPROVED + " = now() ,"

                                  + VehicleTypeVersion.IS_APPROVED + " = TRUE ,"
                                  + VehicleTypeVersion.REVIEWER_FEEDBACK + " = ?,"
                                  + VehicleTypeVersion.REVIEWED_BY + " = ?" +
                        " WHERE " + VehicleTypeVersion.VERSION_ID + " = ?"; // version id goes here



            try {

                connection = dataSource.getConnection();
                connection.setAutoCommit(false);


                statement = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
                int i = 0;

                statement.setObject(++i,vehicleTypeVersion.getVersionID());
                rowCount = statement.executeUpdate();


                statementVersion = connection.prepareStatement(version,PreparedStatement.RETURN_GENERATED_KEYS);
                i = 0;
                statementVersion.setString(++i,vehicleTypeVersion.getReviewerFeedback());
                statementVersion.setObject(++i,vehicleTypeVersion.getReviewedBy());
                statementVersion.setObject(++i,vehicleTypeVersion.getVersionID());

                rowCountVersion = statementVersion.executeUpdate();


                connection.commit();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                if (connection != null) {
                    try {

//                    idOfInsertedRow=-1;
                        rowCount = 0;
                        rowCountVersion = 0;
//                    idOfInsertedRow

                        connection.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            finally
            {


                if (statementVersion != null) {
                    try {
                        statementVersion.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


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


            return (rowCount + rowCountVersion);
        }





    public int approve_an_insert(VehicleTypeVersion vehicleTypeVersion)
    {

        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statementVersion = null;

//        int idOfInsertedRow = -1;
        int rowCount = 0;

//        int idOfInsertedRowVersion = -1;
        int rowCountVersion = 0;

//        String update = "";
        String insert = "";
        String version = "";



        insert = "INSERT INTO " + VehicleType.TABLE_NAME
                                + "(" + VehicleType.NAME + ","
                                + VehicleType.IMAGE_PATH + ","
                                + VehicleType.DESCRIPTION + "" + ") " +
                " SELECT "      + VehicleTypeVersion.NAME + ","
                                + VehicleTypeVersion.IMAGE_PATH + ","
                                + VehicleTypeVersion.DESCRIPTION + "" +
                " FROM "        + VehicleTypeVersion.TABLE_NAME +
                " WHERE "       + VehicleTypeVersion.VERSION_ID + "=?" +
                " AND "         + VehicleTypeVersion.PARENT + " IS NULL ";
        // IS NULL check is done to ensure that the submission was for inserting new entry and not for the update




//        // copy data from versions table to actual table
//        update =  " UPDATE "+ VehicleType.TABLE_NAME
//                + " SET "   + VehicleType.NAME + "=" + VehicleTypeVersion.NAME + ","
//                            + VehicleType.IMAGE_PATH + "=" + VehicleTypeVersion.IMAGE_PATH + ","
//                            + VehicleType.DESCRIPTION + "=" + VehicleTypeVersion.DESCRIPTION + ""
//                + " FROM "  + VehicleTypeVersion.TABLE_NAME
//                + " WHERE " + VehicleType.TABLE_NAME + "." + VehicleType.VEHICLE_TYPE_ID + " = " + VehicleTypeVersion.TABLE_NAME + "." + VehicleTypeVersion.PARENT
//                + " AND "   + VehicleTypeVersion.TABLE_NAME + "." + VehicleTypeVersion.VERSION_ID + " = ?"; // version id goes here




        // update the metadata of the version entry
        version =   " UPDATE "+ VehicleTypeVersion.TABLE_NAME +
                " SET "   + VehicleTypeVersion.TIMESTAMP_APPLIED + "= now(),"
                + VehicleTypeVersion.TIMESTAMP_APPROVED + " = now() ,"

                + VehicleTypeVersion.IS_APPROVED + " = TRUE ,"
                + VehicleTypeVersion.REVIEWER_FEEDBACK + " = ?,"
                + VehicleTypeVersion.REVIEWED_BY + " = ?" +
                " WHERE " + VehicleTypeVersion.VERSION_ID + " = ?"; // version id goes here



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;
            statement.setObject(++i,vehicleTypeVersion.getVersionID());
            rowCount = statement.executeUpdate();


            statementVersion = connection.prepareStatement(version,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0;
            statementVersion.setString(++i,vehicleTypeVersion.getReviewerFeedback());
            statementVersion.setObject(++i,vehicleTypeVersion.getReviewedBy());
            statementVersion.setObject(++i,vehicleTypeVersion.getVersionID());

            rowCountVersion = statementVersion.executeUpdate();


            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

//                    idOfInsertedRow=-1;
                    rowCount = 0;
                    rowCountVersion = 0;
//                    idOfInsertedRow

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {


            if (statementVersion != null) {
                try {
                    statementVersion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


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


        return (rowCount + rowCountVersion);
    }






    public int reject_an_entry(VehicleTypeVersion vehicleTypeVersion) {

        Connection connection = null;
        PreparedStatement statementVersion = null;

//        int idOfInsertedRowVersion = -1;
        int rowCountVersion = 0;

        String version = "";


        // update the metadata of the version entry
        version = " UPDATE " + VehicleTypeVersion.TABLE_NAME +
                " SET "
                + VehicleTypeVersion.TIMESTAMP_APPROVED + " = now() ,"

                + VehicleTypeVersion.IS_APPROVED + " = FALSE ,"
                + VehicleTypeVersion.REVIEWER_FEEDBACK + " = ?,"
                + VehicleTypeVersion.REVIEWED_BY + " = ?" +
                " WHERE " + VehicleTypeVersion.VERSION_ID + " = ?"; // version id goes here


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            int i = 0;

            statementVersion = connection.prepareStatement(version, PreparedStatement.RETURN_GENERATED_KEYS);
            statementVersion.setString(++i, vehicleTypeVersion.getReviewerFeedback());
            statementVersion.setObject(++i, vehicleTypeVersion.getReviewedBy());
            statementVersion.setObject(++i, vehicleTypeVersion.getVersionID());

            rowCountVersion = statementVersion.executeUpdate();


            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {


                    rowCountVersion = 0;
//                    idOfInsertedRow

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {


            if (statementVersion != null) {
                try {
                    statementVersion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            try {

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return rowCountVersion;
    }





}
