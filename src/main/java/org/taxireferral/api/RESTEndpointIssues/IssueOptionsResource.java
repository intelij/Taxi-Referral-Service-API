package org.taxireferral.api.RESTEndpointIssues;

import org.taxireferral.api.DAOIssues.TripIssueOptionsDAO;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelIssues.TripIssueOption;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/IssueOptions")
public class IssueOptionsResource {

    private TripIssueOptionsDAO daoIssueOptions = Globals.issueOptionsDAO;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN})
    public Response insertOption(TripIssueOption issueOption)
    {

        int idOfInsertedRow = -1;


        idOfInsertedRow = daoIssueOptions.insert(issueOption,false);
        issueOption.setTripOptionID(idOfInsertedRow);


        if(idOfInsertedRow >=1)
        {
            return Response.status(Response.Status.CREATED)
                    .entity(issueOption)
                    .build();

        }else if(idOfInsertedRow <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


        return null;
    }









//
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed({GlobalConstants.ROLE_ADMIN})
//    public Response updateImage(TaxiImage image)
//    {
//
//        int rowCount = taxiImagesDAO.updateImage(image);
//
//
//        if(rowCount >= 1)
//        {
//
//            return Response.status(Response.Status.OK)
//                    .build();
//        }
//        else if(rowCount <= 0)
//        {
//
//            return Response.status(Response.Status.NOT_MODIFIED)
//                    .build();
//        }
//
//        return null;
//    }


//
//
//    @DELETE
//    @Path("/{ImageID}")
//    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
//    public Response deleteImage(@PathParam("ImageID")int imageID)
//    {
//
//        int rowCount = taxiImagesDAO.deleteImage(imageID);
//
//        if(rowCount>=1)
//        {
//
//            return Response.status(Response.Status.OK)
//                    .build();
//        }
//        else if(rowCount == 0)
//        {
//
//            return Response.status(Response.Status.NOT_MODIFIED)
//                    .build();
//        }
//
//        return null;
//
//    }









}
