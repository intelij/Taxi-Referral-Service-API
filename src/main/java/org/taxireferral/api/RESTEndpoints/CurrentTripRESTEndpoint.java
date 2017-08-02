package org.taxireferral.api.RESTEndpoints;

import org.taxireferral.api.DAOs.DAOCurrentTrip;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.CurrentTrip;
import org.taxireferral.api.Model.TripHistory;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.ModelUtility.LocationCurrentTrip;


import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.taxireferral.api.Globals.GlobalConstants.ROLE_ADMIN;
import static org.taxireferral.api.Globals.GlobalConstants.ROLE_DRIVER;
import static org.taxireferral.api.Globals.GlobalConstants.ROLE_STAFF;

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


        int rowCount = daoCurrentTrip.finish_trip(
                ((User) Globals.accountApproved).getUserID(),
                true,tripHistory
        );



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
    @Path("/StartTripByDriver")
    @RolesAllowed({ROLE_DRIVER})
    public Response startTripByDriver()
    {

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
    @Path("/StartTripByEndUser")
    @RolesAllowed({GlobalConstants.ROLE_END_USER, ROLE_DRIVER})
    public Response startTripByEndUser()
    {

//        /{CurrentTripID}
//        @PathParam("CurrentTripID")int currentTripID

        int rowCount = daoCurrentTrip.start_trip_by_end_user(((User) Globals.accountApproved).getUserID());

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
    @Path("/ApproveStartByDriver")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ROLE_DRIVER})
    public Response approveStartByDriver(CurrentTrip currentTrip)
    {

        int rowCount = daoCurrentTrip.approve_start_by_driver(currentTrip,((User) Globals.accountApproved).getUserID());

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
    @Path("/ApproveStartByEndUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ROLE_DRIVER})
    public Response approveStartByEndUser(CurrentTrip currentTrip)
    {

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
    public Response getCurrentTripForEndUser()
    {

        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information


        User user = (User) Globals.accountApproved;

        CurrentTrip result = daoCurrentTrip.getCurrentTripForEndUser(user.getUserID());

//        System.out.println(email);


        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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


        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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


        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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
