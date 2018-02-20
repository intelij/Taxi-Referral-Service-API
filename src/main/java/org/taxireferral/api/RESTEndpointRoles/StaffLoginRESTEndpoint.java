package org.taxireferral.api.RESTEndpointRoles;

import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelEndpoints.UserEndpoint;
import org.taxireferral.api.ModelRoles.StaffPermissions;
import org.taxireferral.api.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.taxireferral.api.Globals.Globals.daoStaff;

/**
 * Created by sumeet on 30/8/17.
 */


@Path("/api/v1/User/StaffLogin")
public class StaffLoginRESTEndpoint {





    @PUT
    @Path("/UpdateProfileStaff")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
    public Response updateProfileStaff(User user)
    {
//        /{UserID}
//        @PathParam("UserID")int userID

        user.setUserID(((User)Globals.accountApproved).getUserID());
        int rowCount = daoStaff.updateStaffProfile(user);


        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }







    @PUT
    @Path("/{UserID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN})
    public Response updateStaffByAdmin(User user, @PathParam("UserID")int userID)
    {

        user.setUserID(userID);
        int rowCount = daoStaff.updateStaffByAdmin(user);


        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }





    @PUT
    @Path("/UpdateStaffLocation")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
    public Response updateStaffLocation(StaffPermissions permissions)
    {

        permissions.setStaffUserID(((User)Globals.accountApproved).getUserID());
        int rowCount = daoStaff.updateStaffLocation(permissions);


        if(rowCount >= 1)
        {
            return Response.status(Response.Status.OK)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }





    @GET
    @Path("/GetStaffForAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN})
    public Response getStaffForAdmin(
            @QueryParam("latCurrent") Double latPickUp, @QueryParam("lonCurrent") Double lonPickUp,
            @QueryParam("PermitProfileUpdate") Boolean permitProfileUpdate,
            @QueryParam("PermitRegistrationAndRenewal") Boolean permitRegistrationAndRenewal,
            @QueryParam("Gender") Boolean gender,
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
        else
        {
            limit = GlobalConstants.max_limit;
        }



        UserEndpoint endPoint = daoStaff.getStaffForAdmin(
                latPickUp,lonPickUp,
                permitProfileUpdate,permitRegistrationAndRenewal,
                gender,
                sortBy,limit,offset,
                getRowCount,getOnlyMetaData
        );



        endPoint.setLimit(limit);
        endPoint.setOffset(offset);
        endPoint.setMax_limit(GlobalConstants.max_limit);


        //Marker
        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }






    @GET
    @Path("/GetStaffListPublic")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStaffListPublic(
            @QueryParam("latCurrent") Double latPickUp, @QueryParam("lonCurrent") Double lonPickUp,
            @QueryParam("PermitProfileUpdate") Boolean permitProfileUpdate,
            @QueryParam("PermitRegistrationAndRenewal") Boolean permitRegistrationAndRenewal,
            @QueryParam("Gender") Boolean gender,
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



        UserEndpoint endPoint = daoStaff.getStaffListPublic(
                latPickUp,lonPickUp,
                permitProfileUpdate,permitRegistrationAndRenewal,
                gender,
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
