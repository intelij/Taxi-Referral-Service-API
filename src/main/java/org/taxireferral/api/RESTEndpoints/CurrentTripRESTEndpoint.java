package org.taxireferral.api.RESTEndpoints;

import org.taxireferral.api.DAOs.DAOCurrentTrip;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.CurrentTrip;
import org.taxireferral.api.Model.TripHistory;
import org.taxireferral.api.ModelEndpoints.CurrentTripEndpoint;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.ModelUtility.LocationCurrentTrip;


import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.taxireferral.api.Globals.GlobalConstants.*;

/**
 * Created by sumeet on 27/7/17.
 */

@Path("/api/v1/CurrentTrip")
public class CurrentTripRESTEndpoint {


    private DAOCurrentTrip daoCurrentTrip = Globals.daoCurrentTrip;




    @PUT
    @Path("/UpdateDistanceAndLocation")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response updateDistanceAndLocation(LocationCurrentTrip locationCurrentTrip)
    {


        int rowCount = daoCurrentTrip.update_location_and_distance(
                ((User) Globals.accountApproved).getUserID(),
                locationCurrentTrip
        );


        if(rowCount >= 2)
        {




//            Globals.userNotifications.sendNotificationToEndUser(
//                    locationCurrentTrip.getRt_end_user_id(),
//                    NotificationData.NOTIFICATION_TYPE_CURRENT_TRIP,
//                    NotificationData.NOTIFICATION_SUB_TYPE_CURRENT_TRIP_LOCATION_UPDATE,
//                    locationCurrentTrip
//            );
////


            return Response.status(Response.Status.OK)
                    .build();
        }
        else
        {
//            if(rowCount <= 0)


            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


    }















    @PUT
    @Path("/FinishTrip")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response finishTrip(TripHistory tripHistory)
    {


        tripHistory.setCancelled(false);

        int rowCount = daoCurrentTrip.finish_trip(
                ((User) Globals.accountApproved).getUserID(),
                true,tripHistory
        );



        if(rowCount >= 1)
        {
//            Globals.userNotifications.sendNotificationToEndUser(
//                    tripHistory.getEndUserID(),
//                    NotificationData.NOTIFICATION_TYPE_CURRENT_TRIP,
//                    NotificationData.NOTIFICATION_SUB_TYPE_CURRENT_TRIP_TRIP_FINISHED
//            );


            Globals.oneSignalNotifications.sendNotificationToEndUser(
                    tripHistory.getEndUserID(),
                    "https://triplogic.org/wp-content/uploads/2018/01/cropped-bitmap-copy.png",
                    "http://static4.uk.businessinsider.com/image/59c295c0ba785e2b2c41a3d2-1190-625/a-greenwich-hedge-fund-is-behind-the-mysterious-buyer-of-the-nyc-taxi-kings-medallions.jpg",
                    null,
                    10,
                    "Trip Finished",
                    "Your Trip has finished. Hope you had a nice ride !",
                    3,
                    1
            );



            return Response.status(Response.Status.OK)
                    .build();
        }
        else if(rowCount <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }







    @PUT
    @Path("/CancelTripByDriver")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response CancelTrip(TripHistory tripHistory)
    {

        tripHistory.setCancelled(true);
        tripHistory.setCancelledByUser(false);

        int rowCount = daoCurrentTrip.cancel_trip(
                ((User) Globals.accountApproved).getUserID(),
                true,tripHistory
        );



        if(rowCount >= 1)
        {

//            Globals.userNotifications.sendNotificationToEndUser(
//                    tripHistory.getEndUserID(),
//                    NotificationData.NOTIFICATION_TYPE_CURRENT_TRIP,
//                    NotificationData.NOTIFICATION_SUB_TYPE_CURRENT_TRIP_TRIP_CANCELLED_BY_DRIVER
//            );


            Globals.oneSignalNotifications.sendNotificationToEndUser(
                    tripHistory.getEndUserID(),
                    "https://triplogic.org/wp-content/uploads/2018/01/cropped-bitmap-copy.png",
                    "http://static4.uk.businessinsider.com/image/59c295c0ba785e2b2c41a3d2-1190-625/a-greenwich-hedge-fund-is-behind-the-mysterious-buyer-of-the-nyc-taxi-kings-medallions.jpg",
                    null,
                    10,
                    "Trip Cancelled !",
                    "Driver has cancelled the trip !",
                    3,
                    1
            );




            return Response.status(Response.Status.OK)
                    .build();
        }
        else if(rowCount <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }







    @PUT
    @Path("/CancelTripByEndUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER})
    public Response CancelTripByEndUser(TripHistory tripHistory)
    {

        tripHistory.setCancelled(true);
        tripHistory.setCancelledByUser(true);


        int rowCount = daoCurrentTrip.cancel_trip_by_end_user(
                ((User) Globals.accountApproved).getUserID(),
                true,tripHistory
        );




        if(rowCount >= 1)
        {

//            Globals.userNotifications.sendNotificationToDriver(
//                    tripHistory.getRt_driver_id(),
//                    NotificationData.NOTIFICATION_TYPE_CURRENT_TRIP,
//                    NotificationData.NOTIFICATION_SUB_TYPE_CURRENT_TRIP_TRIP_CANCELLED_BY_DRIVER
//            );


            Globals.oneSignalNotifications.sendNotificationToDriver(
                    tripHistory.getRt_driver_id(),
                    "https://triplogic.org/wp-content/uploads/2018/01/cropped-bitmap-copy.png",
                    "http://static4.uk.businessinsider.com/image/59c295c0ba785e2b2c41a3d2-1190-625/a-greenwich-hedge-fund-is-behind-the-mysterious-buyer-of-the-nyc-taxi-kings-medallions.jpg",
                    null,
                    10,
                    "Trip Cancelled !",
                    "Customer has cancelled the trip !",
                    2,
                    1
            );




            return Response.status(Response.Status.OK)
                    .build();
        }
        else if(rowCount <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }





    //    @PUT
//    @Path("/StartTripByDriver")
//    @RolesAllowed({ROLE_DRIVER})
    public Response startTripByDriver()
    {
        /* DEPRECATED FUNCTION */

        int rowCount = daoCurrentTrip.start_trip_by_driver(((User) Globals.accountApproved).getUserID());

        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .build();
        }
        else if(rowCount <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }












    @PUT
    @Path("/StartTripByEndUser/{DriverID}/{CurrentTripID}")
    @RolesAllowed({GlobalConstants.ROLE_END_USER})
    public Response startTripByEndUser(@PathParam("DriverID")int driverID,@PathParam("CurrentTripID")int currentTripID)
    {

//        /{CurrentTripID}
//        @PathParam("CurrentTripID")int currentTripID

        int rowCount = daoCurrentTrip.start_trip_by_end_user(((User) Globals.accountApproved).getUserID(),currentTripID);

        if(rowCount >= 1)
        {


//            Globals.userNotifications.sendNotificationToDriver(
//                    driverID,
//                    NotificationData.NOTIFICATION_TYPE_CURRENT_TRIP,
//                    NotificationData.NOTIFICATION_SUB_TYPE_CURRENT_TRIP_START_REQUESTED
//            );


            Globals.oneSignalNotifications.sendNotificationToDriver(
                    driverID,
                    "https://triplogic.org/wp-content/uploads/2018/01/cropped-bitmap-copy.png",
                    "http://static4.uk.businessinsider.com/image/59c295c0ba785e2b2c41a3d2-1190-625/a-greenwich-hedge-fund-is-behind-the-mysterious-buyer-of-the-nyc-taxi-kings-medallions.jpg",
                    null,
                    10,
                    "Trip Start Requested",
                    "Customer has requested you to start the trip !",
                    2,
                    1
            );





            return Response.status(Response.Status.OK)
                    .build();
        }
        else if(rowCount <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }




    @PUT
    @Path("/ApproveStartByDriver")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ROLE_DRIVER})
    public Response approveStartByDriver(CurrentTrip currentTrip)
    {

        int rowCount = daoCurrentTrip.approve_start_by_driver(currentTrip,((User) Globals.accountApproved).getUserID());

        if(rowCount >= 1)
        {
//            Globals.userNotifications.sendNotificationToEndUser(
//                    currentTrip.getEndUserID(),
//                    NotificationData.NOTIFICATION_TYPE_CURRENT_TRIP,
//                    NotificationData.NOTIFICATION_SUB_TYPE_CURRENT_TRIP_TRIP_STARTED
//            );


            Globals.oneSignalNotifications.sendNotificationToEndUser(
                    currentTrip.getEndUserID(),
                    "https://triplogic.org/wp-content/uploads/2018/01/cropped-bitmap-copy.png",
                    "http://static4.uk.businessinsider.com/image/59c295c0ba785e2b2c41a3d2-1190-625/a-greenwich-hedge-fund-is-behind-the-mysterious-buyer-of-the-nyc-taxi-kings-medallions.jpg",
                    null,
                    10,
                    "Trip Started",
                    "Driver has started the trip ",
                    3,
                    1
            );






            return Response.status(Response.Status.OK)
                    .build();
        }
        else if(rowCount <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }


//    @PUT
//    @Path("/ApproveStartByEndUser")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed({ROLE_DRIVER})
    public Response approveStartByEndUser(CurrentTrip currentTrip)
    {
        /* CAUTION : DEPRECATED FUNCTION */

        int rowCount = daoCurrentTrip.approve_start_by_end_user(currentTrip,((User) Globals.accountApproved).getUserID());

        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .build();
        }
        else if(rowCount <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }








    @GET
    @Path("/GetCurrentTripForEndUser")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER,ROLE_DRIVER,ROLE_STAFF,ROLE_ADMIN})
    public Response getCurrentTripForEndUser(
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {


        if(limit!=null)
        {
            if(limit >= GlobalConstants.max_limit)
            {
                limit = GlobalConstants.max_limit;
            }

            if(offset==null)
            {
                offset = 0;
            }
        }



        CurrentTripEndpoint endPoint = Globals.daoCurrentTrip.getCurrentTripForEnduserNew(
                ((User) Globals.accountApproved).getUserID(),
                sortBy,limit,offset,
                getRowCount,getOnlyMetaData
        );




        if(limit!=null)
        {
            endPoint.setLimit(limit);
            endPoint.setOffset(offset);
            endPoint.setMax_limit(GlobalConstants.max_limit);
        }







        //Marker
        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }


    @GET
    @Path("/GetCurrentTripListForStaff")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ROLE_STAFF,ROLE_ADMIN})
    public Response getCurrentTripsForStaff(
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {


        if(limit!=null)
        {
            if(limit >= GlobalConstants.max_limit)
            {
                limit = GlobalConstants.max_limit;
            }

            if(offset==null)
            {
                offset = 0;
            }
        }




        CurrentTripEndpoint endPoint = Globals.daoCurrentTrip.getCurrentTripForStaff(
                null,
                sortBy,limit,offset,
                getRowCount,getOnlyMetaData
        );



        if(limit!=null)
        {
            endPoint.setLimit(limit);
            endPoint.setOffset(offset);
            endPoint.setMax_limit(GlobalConstants.max_limit);
        }




        //Marker
        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }


    @GET
    @Path("/GetCurrentTripListForEndUser")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER,ROLE_DRIVER,ROLE_STAFF,ROLE_ADMIN})
    public Response getCurrentTripsForEndUser(
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {


        if(limit!=null)
        {
            if(limit >= GlobalConstants.max_limit)
            {
                limit = GlobalConstants.max_limit;
            }

            if(offset==null)
            {
                offset = 0;
            }
        }



        CurrentTripEndpoint endPoint = Globals.daoCurrentTrip.getCurrentTripForEnduserNew(
                ((User) Globals.accountApproved).getUserID(),
                sortBy,limit,offset,
                getRowCount,getOnlyMetaData
        );




        if(limit!=null)
        {
            endPoint.setLimit(limit);
            endPoint.setOffset(offset);
            endPoint.setMax_limit(GlobalConstants.max_limit);
        }




        //Marker
        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }







    @GET
    @Path("/GetCurrentTripForEndUser/{CurrentTripID}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER})
    public Response getCurrentTripForEndUser(@PathParam("CurrentTripID")int currentTripID)
    {

        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information

        User user = (User) Globals.accountApproved;
        CurrentTrip result = daoCurrentTrip.getCurrentTripForEndUser(user.getUserID(),currentTripID);

//        System.out.println(email);


//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//

        if(result!=null)
        {
            return Response.status(Response.Status.OK)
                    .entity(result)
                    .build();

        }
        else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }

    }









    @GET
    @Path("/GetCurrentTripForDriver")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ROLE_DRIVER})
    public Response getCurrentTripForDriver()
    {

        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information


        User user = (User) Globals.accountApproved;

        CurrentTrip result = daoCurrentTrip.getCurrentTripForDriver(user.getUserID());

//        System.out.println(email);


//        try {
//            Thread.sleep(400);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        if(result!=null)
        {
            return Response.status(Response.Status.OK)
                    .entity(result)
                    .build();

        }
        else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }






    @GET
    @Path("/GetCurrentTripStatusForDriver")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ROLE_DRIVER})
    public Response getCurrentTripStatusForDriver()
    {

        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information


        User user = (User) Globals.accountApproved;

        CurrentTrip result = daoCurrentTrip.getCurrentTripStatusForDriver(user.getUserID());

//        System.out.println(email);


//
//        try {
//            Thread.sleep(400);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        if(result!=null)
        {
            return Response.status(Response.Status.OK)
                    .entity(result)
                    .build();

        }
        else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }











    @GET
    @Path("/GetCurrentTripStatusForEndUser/{CurrentTripID}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ROLE_END_USER})
    public Response getCurrentTripStatusForEndUser(@PathParam("CurrentTripID")int currentTripID)
    {

        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information


        User user = (User) Globals.accountApproved;

        CurrentTrip result = daoCurrentTrip.getCurrentTripStatusForEndUser(user.getUserID(),currentTripID);

//        System.out.println(email);


//        try {
//            Thread.sleep(400);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        if(result!=null)
        {
            return Response.status(Response.Status.OK)
                    .entity(result)
                    .build();

        }
        else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }





    @GET
    @Path("/GetCurrentTripForStaff/{CurrentTripID}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ROLE_STAFF,ROLE_ADMIN})
    public Response getCurrentTripForStaff(@PathParam("CurrentTripID")int currentTripID)
    {


        CurrentTrip result = daoCurrentTrip.getCurrentTripForStaff(currentTripID);

//        System.out.println(email);


//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//

        if(result!=null)
        {
            return Response.status(Response.Status.OK)
                    .entity(result)
                    .build();

        }
        else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }

    }


}
