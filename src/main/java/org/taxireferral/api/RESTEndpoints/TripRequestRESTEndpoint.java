package org.taxireferral.api.RESTEndpoints;

import org.taxireferral.api.DAOs.DAOTripRequest;
import org.taxireferral.api.DAOs.VehicleDAO;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.TripRequest;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelEndpoints.TripRequestEndPoint;
import org.taxireferral.api.ModelEndpoints.VehicleEndPoint;

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



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTripRequest(TripRequest tripRequest)
    {

        int idOfInsertedRow = -1;

//        if (Globals.accountApproved instanceof User)
//        {
//            if(((User)Globals.accountApproved).getRole() != GlobalConstants.ROLE_DRIVER_CODE)
//            {
//                throw new ForbiddenException();
//            }
//
//
//            idOfInsertedRow = daoVehicle.insert_vehicle(vehicle,false);
//
//            vehicle.setVehicleID(idOfInsertedRow);
//        }




        idOfInsertedRow = daoTripRequest.create_trip_request(tripRequest,false);
        tripRequest.setTripRequestID(idOfInsertedRow);



        if(idOfInsertedRow >=1)
        {
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
    @Path("/SetRequestApproved/{TripRequestID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response setRequestApproved(@PathParam("TripRequestID")int tripRequestID)
    {


//        if(Globals.accountApproved instanceof User) {

        // checking permission
//            Staff staff = (Staff) Globals.accountApproved;
//            if (!staff.isApproveShops())
//            {
//                 the staff member doesnt have persmission to post Item Category
//                throw new ForbiddenException("Not Permitted");
//            }
//        }


        int rowCount = daoTripRequest.set_request_approved(tripRequestID);


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
    @Path("/RequestPickup/{TripRequestID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER})
    public Response requestPickUp(@PathParam("TripRequestID")int tripRequestID)
    {


//        if(Globals.accountApproved instanceof User) {

        // checking permission
//            Staff staff = (Staff) Globals.accountApproved;
//            if (!staff.isApproveShops())
//            {
//                 the staff member doesnt have persmission to post Item Category
//                throw new ForbiddenException("Not Permitted");
//            }
//        }


        int rowCount = daoTripRequest.request_pick_up(tripRequestID);


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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaxisAvailable(
            @QueryParam("EndUserID") Integer endUserID,
            @QueryParam("VehicleID") Integer vehicleID,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {




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



        endPoint = daoTripRequest.getTripRequests(endUserID,vehicleID
                ,sortBy,limit,offset,
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
