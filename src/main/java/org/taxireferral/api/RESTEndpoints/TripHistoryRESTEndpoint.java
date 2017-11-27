package org.taxireferral.api.RESTEndpoints;

import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.TripHistory;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelEndpoints.TripHistoryEndPoint;
import org.taxireferral.api.ModelEndpoints.TripRequestEndPoint;
import org.taxireferral.api.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sumeet on 2/8/17.
 */


@Path("/api/v1/TripHistory")
public class TripHistoryRESTEndpoint {




    @PUT
    @Path("/ReviewByDriver")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response reviewByDriver(TripHistory tripHistory)
    {

        int rowCount = Globals.daoTripHistory.reviewTripByDriver(tripHistory);

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
    @Path("/ReviewByEndUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER})
    public Response reviewByEndUser(TripHistory tripHistory)
    {

        int endUserID = ((User)Globals.accountApproved).getUserID();

        int rowCount = Globals.daoTripHistory.reviewTripByEndUser(tripHistory,endUserID);

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
    @Path("/TripHistoryForEndUser")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER})
    public Response getTripHistoryForEndUser(
            @QueryParam("IsCancelled") Boolean isCancelled,
            @QueryParam("IsCancelledByEndUser") Boolean isCancelledByEndUser,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {

//        @QueryParam("EndUserID") Integer endUserID,



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




        TripHistoryEndPoint endPoint
                = Globals.daoTripHistory.getTripHistoryForEndUser(
                                    ((User) Globals.accountApproved).getUserID(),
                                    null,
                                    isCancelled,isCancelledByEndUser,
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




    @GET
    @Path("/TripHistoryForDriver")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response getTripHistoryForDriver(
            @QueryParam("EndUserID") Integer endUserID,
            @QueryParam("IsCancelled") Boolean isCancelled,
            @QueryParam("IsCancelledByEndUser") Boolean isCancelledByEndUser,
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




        TripHistoryEndPoint endPoint
                = Globals.daoTripHistory.getTripHistoryForDriver(
                                endUserID,
                                ((User) Globals.accountApproved).getUserID(),
                                isCancelled,isCancelledByEndUser,
                                sortBy,limit,offset,
                                getRowCount,getOnlyMetaData
        );



//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        //Marker

        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }




}
