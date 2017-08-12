package org.taxireferral.api.RESTEndpoints;

import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelEndpoints.TripHistoryEndPoint;
import org.taxireferral.api.ModelEndpoints.TripRequestEndPoint;
import org.taxireferral.api.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sumeet on 2/8/17.
 */


@Path("/api/v1/TripHistory")
public class TripHistoryRESTEndpoint {




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




		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


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




        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //Marker

        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }




}
