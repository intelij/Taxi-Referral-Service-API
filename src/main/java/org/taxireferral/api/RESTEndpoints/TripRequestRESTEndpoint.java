package org.taxireferral.api.RESTEndpoints;

import org.taxireferral.api.DAOs.DAOTripRequest;
import org.taxireferral.api.DAOs.VehicleDAO;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.TripRequest;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelEndpoints.TripRequestEndPoint;
import org.taxireferral.api.ModelEndpoints.VehicleEndPoint;
import org.taxireferral.api.ModelNotifications.NotificationData;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.ModelUtility.Location;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sumeet on 4/7/17.
 */

@Path("/api/v1/TripRequest")
public class TripRequestRESTEndpoint {


    private DAOTripRequest daoTripRequest = Globals.tripRequestDAO;


//    ,GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_DRIVER


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER})
    public Response createTripRequest(TripRequest tripRequest)
    {

        int idOfInsertedRow = -1;


        if(Globals.accountApproved instanceof User)
        {
            tripRequest.setEndUserID(((User) Globals.accountApproved).getUserID());
        }


        idOfInsertedRow = daoTripRequest.create_trip_request(tripRequest,false);
        tripRequest.setTripRequestID(idOfInsertedRow);
        tripRequest.setTripRequestStatus(GlobalConstants.TAXI_REQUESTED);



        if(idOfInsertedRow >=1)
        {

            Globals.userNotifications.sendNotificationToDriver(
                    tripRequest.getRt_vehicle().getDriverID(),
                    NotificationData.NOTIFICATION_TYPE_TAXI_REQUEST,
                    NotificationData.NOTIFICATION_SUB_TYPE_TAXI_REQUEST_REQUEST_RECEIVED
            );


            return Response.status(Response.Status.CREATED)
                    .entity(tripRequest)
                    .build();

        }else if(idOfInsertedRow <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


        return null;

    }





    @PUT
    @Path("/SetRequestApproved/{TripRequestID}/UserID/{UserID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response setRequestApproved(@PathParam("TripRequestID")int tripRequestID,@PathParam("UserID")int userID)
    {



//        if(! (Globals.accountApproved instanceof User)) {
//            return null;
//        }


        int rowCount = daoTripRequest.set_request_approved(
                tripRequestID,
                ((User) Globals.accountApproved).getUserID(),
                userID
        );


        if(rowCount >= 1)
        {

            Globals.userNotifications.sendNotificationToEndUser(userID,
                    NotificationData.NOTIFICATION_TYPE_TAXI_REQUEST,
                    NotificationData.NOTIFICATION_SUB_TYPE_TAXI_REQUEST_REQUEST_ACCEPTED
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



    //        GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_DRIVER



    @PUT
    @Path("/RequestPickup/{TripRequestID}/DriverID/{DriverID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER})
    public Response requestPickUp(@PathParam("TripRequestID")int tripRequestID,
                                  @PathParam("DriverID")int driverID)
    {

        if(Globals.accountApproved instanceof User)
        {
            int rowCount = daoTripRequest.request_pick_up(tripRequestID,((User) Globals.accountApproved).getUserID());

            if(rowCount >= 1)
            {

                Globals.userNotifications.sendNotificationToDriver(
                        driverID,
                        NotificationData.NOTIFICATION_TYPE_TAXI_REQUEST,
                        NotificationData.NOTIFICATION_SUB_TYPE_TAXI_REQUEST_PICKUP_REQUESTED
                );


                return Response.status(Response.Status.OK)
                        .build();
            }
            else if(rowCount <= 0)
            {

                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }

        }


        return null;
    }






    @PUT
    @Path("/ApprovePickup/{TripRequestID}/UserID/{UserID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response approvePickup( Location location,
                                   @PathParam("TripRequestID")int tripRequestID,
                                   @PathParam("UserID")int userID
    )

    {



        int rowCount = daoTripRequest.approve_pickup(
                tripRequestID,((User) Globals.accountApproved).getUserID(),
                true,location
        );



        if(rowCount >= 1)
        {

            Globals.userNotifications.sendNotificationToEndUser(
                    userID,
                    NotificationData.NOTIFICATION_TYPE_TAXI_REQUEST,
                    NotificationData.NOTIFICATION_SUB_TYPE_TAXI_REQUEST_PICKUP_STARTED
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






    @GET
    @Path("/CheckTripRequestExists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkTripRequestExist(
            @QueryParam("EndUserID") int endUserID,
            @QueryParam("VehicleID") int vehicleID
    )
    {
        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information

        boolean result = daoTripRequest.checkTripRequestExists(endUserID,vehicleID);

//        System.out.println(email);



//        try {
//            Thread.sleep(400);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        if(result)
        {
            return Response.status(Response.Status.OK)
                    .build();

        }
        else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }

    }


//    ,GlobalConstants.ROLE_DRIVER,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN


    @GET
    @Path("/CheckTripStatus")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER})
    public Response checkTripStatus(
            @QueryParam("TripRequestID") int tripRequestID
    )
    {
        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information


        User user = (User) Globals.accountApproved;

        TripRequest result = daoTripRequest.checkTripRequestStatus(tripRequestID,user.getUserID());

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
    @Path("/CheckTripStatusForDriver")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response checkTripStatusForDriver(
            @QueryParam("TripRequestID") int tripRequestID
    )
    {

        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information


        User user = (User) Globals.accountApproved;

        TripRequest result = daoTripRequest.checkTripRequestStatusForDriver(tripRequestID,user.getUserID());

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
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER})
    public Response getTripRequestsEndUser(
            @QueryParam("VehicleID") Integer vehicleID,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {


//        @QueryParam("EndUserID") Integer endUserID,


        TripRequestEndPoint endPoint = new TripRequestEndPoint();

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

            endPoint.setLimit(limit);
            endPoint.setOffset(offset);
            endPoint.setMax_limit(GlobalConstants.max_limit);

        }



        endPoint = daoTripRequest.getTripRequests(
                ((User)Globals.accountApproved).getUserID(),vehicleID,
                sortBy,limit,offset, getRowCount,getOnlyMetaData
        );



//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


        //Marker

        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }





//    @QueryParam("VehicleID") Integer vehicleID,


    @GET
    @Path("/TripRequestsForDriver")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response getTripRequestsForDriver(
            @QueryParam("EndUserID") Integer endUserID,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {



//        TripRequestEndPoint endPoint = new TripRequestEndPoint();

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

//            endPoint.setLimit(limit);
//            endPoint.setOffset(offset);
//            endPoint.setMax_limit(GlobalConstants.max_limit);
        }





        TripRequestEndPoint endPoint
                = daoTripRequest.getTripRequestsForDriver(endUserID,
                            ((User)Globals.accountApproved).getUserID(),
                            sortBy,limit,offset,
                            getRowCount,getOnlyMetaData
        );




//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


        //Marker

        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }





}
