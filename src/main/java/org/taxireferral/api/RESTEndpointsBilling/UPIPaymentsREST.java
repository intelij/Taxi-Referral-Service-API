package org.taxireferral.api.RESTEndpointsBilling;

import org.taxireferral.api.DAOBilling.DAOUPIPayments;
import org.taxireferral.api.DAOs.VehicleDAO;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelBilling.Endpoints.UPIPaymentEndPoint;
import org.taxireferral.api.ModelBilling.UPIPaymentRequest;
import org.taxireferral.api.ModelEndpoints.VehicleEndPoint;
import org.taxireferral.api.ModelRoles.StaffPermissions;
import org.taxireferral.api.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/api/v1/UPIPayments")
public class UPIPaymentsREST {

    private DAOUPIPayments daoUPIPayments = Globals.daoupiPayments;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response createRequest(UPIPaymentRequest upiPaymentRequest)
    {
        User user = (User) Globals.accountApproved;
        upiPaymentRequest.setUserID(user.getUserID());


        int idOfInsertedRow = -1;

        idOfInsertedRow = daoUPIPayments.createPaymentRequest(upiPaymentRequest,false);
        upiPaymentRequest.setUpiPaymentID(idOfInsertedRow);



        if(idOfInsertedRow >=1)
        {
            return Response.status(Response.Status.CREATED)
                    .entity(upiPaymentRequest)
                    .build();

        }else if(idOfInsertedRow <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


        return null;
    }






//    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})



    @PUT
    @Path("/ApprovePayments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response approvePayments(UPIPaymentRequest upiPaymentRequest)
    {

//        User user = (User) Globals.accountApproved;
//        StaffPermissions permissions = Globals.daoStaff.getStaffPermissions(user.getUserID());
//
//
//        // check staff permissions
//        if(user.getRole()!=GlobalConstants.ROLE_ADMIN_CODE)
//        {
//            if(permissions==null || !permissions.isPermitAcceptPayments())
//            {
//                return Response.status(Response.Status.EXPECTATION_FAILED)
//                        .build();
//            }
//        }
//


        int rowCount = daoUPIPayments.approveTaxPaymentRequest(upiPaymentRequest);


//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


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








//    @RolesAllowed({GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUPIPaymentRequests(
            @QueryParam("Status") Integer status,
            @QueryParam("SearchString")String searchString,
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



        UPIPaymentEndPoint endPoint = daoUPIPayments.getUPIPaymentRequests(
                status,
                searchString,
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



}
