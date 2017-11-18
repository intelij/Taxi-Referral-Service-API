package org.taxireferral.api.DAOImages;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelImages.TaxiImage;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelEndpoints.VehicleEndPoint;
import org.taxireferral.api.ModelImages.TaxiImageEndpoint;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sumeet on 28/2/17.
 */
public class TaxiImagesDAO {

    private HikariDataSource dataSource = Globals.getDataSource();


    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }


//    public int updateStaffLocation(TaxiImage permissions)
//    {
//
//
//        String insertStaffPermissions =
//
//                "INSERT INTO " + StaffPermissions.TABLE_NAME
//                        + "("
//                        + StaffPermissions.STAFF_ID + ","
//                        + StaffPermissions.LAT_CURRENT + ","
//                        + StaffPermissions.LON_CURRENT + ""
//                        + ") values(?,?,?)"
//                        + " ON CONFLICT (" + StaffPermissions.STAFF_ID + ")"
//                        + " DO UPDATE "
//                        + " SET "
//                        + StaffPermissions.LAT_CURRENT + "= excluded." + StaffPermissions.LAT_CURRENT + " , "
//                        + StaffPermissions.LON_CURRENT + "= excluded." + StaffPermissions.LON_CURRENT;
//
//
//
//
//        Connection connection = null;
//        PreparedStatement statement = null;
//
//        int rowCountUpdated = 0;
//
//        try {
//
//            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);
//
//
//            statement = connection.prepareStatement(insertStaffPermissions,PreparedStatement.RETURN_GENERATED_KEYS);
//            int i = 0;
//
//
//            if(permissions!=null)
//            {
//                statement.setObject(++i,permissions.getStaffUserID());
//                statement.setObject(++i,permissions.getLatCurrent());
//                statement.setObject(++i,permissions.getLonCurrent());
//
//                rowCountUpdated = statement.executeUpdate();
//            }
//
//
//            connection.commit();
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//
//            if (connection != null) {
//                try {
//
////                    idOfInsertedRow=-1;
////                    rowCountItems = 0;
//
//                    connection.rollback();
//                } catch (SQLException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//        finally
//
//        {
//
//            try {
//
//                if(statement!=null)
//                {statement.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            try {
//
//                if(connection!=null)
//                {connection.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        return rowCountUpdated;
//    }





    public int saveTaxiImage(TaxiImage taxiImage, boolean getRowCount)
    {


        Connection connection = null;
        PreparedStatement statement = null;
        int idOfInsertedRow = -1;
        int rowCount = 0;


        String insert = "INSERT INTO "
                + TaxiImage.TABLE_NAME
                + "("

                + TaxiImage.DRIVER_ID + ","
                + TaxiImage.VEHICLE_ID + ","
                + TaxiImage.IMAGE_FILENAME + ","

                + TaxiImage.CAPTION_TITLE + ","
                + TaxiImage.CAPTION + ","
                + TaxiImage.IMAGE_COPYRIGHTS + ","
                + TaxiImage.IMAGE_ORDER + ","

                + TaxiImage.NOTES_FOR_REVIEWER + ","
                + TaxiImage.IS_DOCUMENT + ","
                + TaxiImage.IS_PRIVATE + ""

                + ") VALUES(?,?,? ,?,?,?,? ,?,?,?)";

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);

            int i = 0;
            statement.setObject(++i,taxiImage.getDriverID());
            statement.setObject(++i,taxiImage.getVehicleID());
            statement.setString(++i,taxiImage.getImageFilename());

            statement.setString(++i,taxiImage.getCaptionTitle());
            statement.setString(++i,taxiImage.getCaption());
            statement.setString(++i,taxiImage.getImageCopyrights());
            statement.setObject(++i,taxiImage.getImageOrder());

            statement.setString(++i,taxiImage.getNotesForReviewer());
            statement.setObject(++i,taxiImage.isDocument());
            statement.setObject(++i,taxiImage.isPrivate());


            rowCount = statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally
        {

            try {

                if(statement!=null)
                {statement.close();}

            }
            catch (SQLException e) {
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


        if(getRowCount)
        {
            return rowCount;
        }
        else
        {
            return idOfInsertedRow;
        }

    }





    public int updateItemImage(TaxiImage taxiImage)
    {

        String updateStatement = "UPDATE " + TaxiImage.TABLE_NAME

                + " SET "

                + TaxiImage.DRIVER_ID + "=?,"
                + TaxiImage.VEHICLE_ID + "=?,"
                + TaxiImage.IMAGE_FILENAME + "=?,"

                + TaxiImage.CAPTION_TITLE + "=?,"
                + TaxiImage.CAPTION + "=?,"
                + TaxiImage.IMAGE_COPYRIGHTS + "=?,"
                + TaxiImage.IMAGE_ORDER + "=?,"

                + TaxiImage.NOTES_FOR_REVIEWER + "=?,"
                + TaxiImage.IS_DOCUMENT + "=?,"
                + TaxiImage.IS_PRIVATE + "=?"

                + " WHERE " + TaxiImage.IMAGE_ID + "=?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);



            int i = 0;
            statement.setObject(++i,taxiImage.getDriverID());
            statement.setObject(++i,taxiImage.getVehicleID());
            statement.setString(++i,taxiImage.getImageFilename());

            statement.setString(++i,taxiImage.getCaptionTitle());
            statement.setString(++i,taxiImage.getCaption());
            statement.setString(++i,taxiImage.getImageCopyrights());
            statement.setObject(++i,taxiImage.getImageOrder());

            statement.setString(++i,taxiImage.getNotesForReviewer());
            statement.setObject(++i,taxiImage.isDocument());
            statement.setObject(++i,taxiImage.isPrivate());

            statement.setObject(++i,taxiImage.getImageID());

            rowCountUpdated = statement.executeUpdate();
            System.out.println("Total rows updated: " + rowCountUpdated);


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

        return rowCountUpdated;
    }





    public int deleteItemImage(int imageID)
    {

        String deleteStatement = "DELETE FROM " + TaxiImage.TABLE_NAME + " WHERE " + TaxiImage.IMAGE_ID + " = ?";

        Connection connection= null;
        PreparedStatement statement = null;
        int rowCountDeleted = 0;
        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteStatement);
            int i = 0;
            statement.setInt(++i,imageID);

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
















    public TaxiImageEndpoint getTaxiImages(
            Boolean isDocument,
            Boolean isPrivate,
            Boolean isApproved,
            Boolean approvalPending,
            String searchString,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata
    ) {


        boolean isfirst = true;

        String queryCount = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


        String queryJoin = "SELECT "

                + TaxiImage.TABLE_NAME + "." + TaxiImage.IMAGE_ID + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.DRIVER_ID + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.VEHICLE_ID + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.IMAGE_FILENAME + ","

                + TaxiImage.TABLE_NAME + "." + TaxiImage.TIMESTAMP_CREATED + ","

                + TaxiImage.TABLE_NAME + "." + TaxiImage.CAPTION_TITLE + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.CAPTION + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.IMAGE_COPYRIGHTS + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.IMAGE_ORDER + ","

                + TaxiImage.TABLE_NAME + "." + TaxiImage.IS_APPROVED + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.APPROVED_BY + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.TIMESTAMP_APPROVED + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.REVIEWER_FEEDBACK + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.NOTES_FOR_REVIEWER + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.IS_DOCUMENT + ","
                + TaxiImage.TABLE_NAME + "." + TaxiImage.IS_PRIVATE + ""

                + " FROM " + TaxiImage.TABLE_NAME
                + " WHERE TRUE ";


        if (isDocument != null) {
            queryJoin = queryJoin + " AND " + TaxiImage.TABLE_NAME + "." + TaxiImage.IS_DOCUMENT + " = ?";
        }


        if (isPrivate != null) {
            queryJoin = queryJoin + " AND " + TaxiImage.TABLE_NAME + "." + TaxiImage.IS_PRIVATE + " = ?";
        }


        if (isApproved != null) {
            queryJoin = queryJoin + " AND " + TaxiImage.TABLE_NAME + "." + TaxiImage.IS_APPROVED + " = ?";
        }


        if ((approvalPending != null) && approvalPending)
        {
            queryJoin = queryJoin + " AND " + TaxiImage.TABLE_NAME + "." + TaxiImage.IS_APPROVED + " IS NULL ";
        }





//
//        if(searchString !=null) {
//
//            String queryPartSearch = " AND CAST ( " + TaxiImage.TABLE_NAME + "." + TaxiImage.USER_ID + " AS text )" + " ilike '%" + searchString + "%'" + " ";
//
//            queryJoin = queryJoin + queryPartSearch;
//        }

//



        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + TaxiImage.TABLE_NAME + "." + TaxiImage.IMAGE_ID + "";




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


        TaxiImageEndpoint endPoint = new TaxiImageEndpoint();

        ArrayList<TaxiImage> itemList = new ArrayList<>();
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

                if(isDocument!=null)
                {
                    statement.setObject(++i,isDocument);
                }

                if(isPrivate!=null)
                {
                    statement.setObject(++i,isPrivate);
                }

                if(isApproved!=null)
                {
                    statement.setObject(++i,isApproved);
                }


                rs = statement.executeQuery();

                while(rs.next())
                {

                    TaxiImage taxiImage = new TaxiImage();

                    taxiImage.setImageID(rs.getInt(TaxiImage.IMAGE_ID));
                    taxiImage.setDriverID(rs.getInt(TaxiImage.DRIVER_ID));
                    taxiImage.setVehicleID(rs.getInt(TaxiImage.VEHICLE_ID));
                    taxiImage.setImageFilename(rs.getString(TaxiImage.IMAGE_FILENAME));

                    taxiImage.setTimestampCreated(rs.getTimestamp(TaxiImage.TIMESTAMP_CREATED));

                    taxiImage.setCaptionTitle(rs.getString(TaxiImage.CAPTION_TITLE));
                    taxiImage.setCaption(rs.getString(TaxiImage.CAPTION));
                    taxiImage.setImageCopyrights(rs.getString(TaxiImage.IMAGE_COPYRIGHTS));
                    taxiImage.setImageOrder(rs.getInt(TaxiImage.IMAGE_ORDER));

                    itemList.add(taxiImage);
                }

                endPoint.setResults(itemList);

            }


            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;


                if(isDocument!=null)
                {
                    statementCount.setObject(++i,isDocument);
                }

                if(isPrivate!=null)
                {
                    statementCount.setObject(++i,isPrivate);
                }

                if(isApproved!=null)
                {
                    statementCount.setObject(++i,isApproved);
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
