package org.taxireferral.api.RESTEndpoints;

import jdk.nashorn.internal.objects.Global;
import org.taxireferral.api.DAOs.VehicleTypeDAONew;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.VehicleTypeVersion;
import org.taxireferral.api.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sumeet on 18/4/17.
 */

@Path("/api/v1/VehicleType")
public class VehicleTypeResource {

    /**
     *
     * insert
     * update
     * restore-a-version
     * approve
     * reject
     *
     * delete
     *
     * get
     *
     *
     *
     */


    private VehicleTypeDAONew vehicleTypeDAONew = Globals.daoVehicleTypeNew;



    @POST
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(VehicleTypeVersion vehicleTypeVersion)
    {
        if(vehicleTypeVersion==null)
        {
            throw new WebApplicationException();
        }


        int idOfInsertedRow = -1;

        User userApproved = (User) Globals.accountApproved;

        if(userApproved.getRole()==GlobalConstants.ROLE_STAFF_CODE)
        {
            // not permitted so take a submission and put under review


            idOfInsertedRow = vehicleTypeDAONew.submit_an_entry(vehicleTypeVersion,false);



            if(idOfInsertedRow >=1)
            {

                vehicleTypeVersion.setRt_response_code(
                        VehicleTypeVersion.RESPONSE_SUBMISSION_FOR_CREATE_ACCEPTED);
                vehicleTypeVersion.setRt_message_from_server(
                        "Thanks for your submission. Your submission is received. " +
                                "It will be accepted after a review.");

                return Response.status(Response.Status.CREATED)
                        .entity(vehicleTypeVersion)
                        .build();

            }else if(idOfInsertedRow <= 0)
            {
                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }



        }
        else if(userApproved.getRole()==GlobalConstants.ROLE_ADMIN_CODE)
        {
            // permitted to directly insert and update an entry

            idOfInsertedRow = vehicleTypeDAONew.insert_an_entry(vehicleTypeVersion,false);
            vehicleTypeVersion.setVersionID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                vehicleTypeVersion.setRt_response_code(VehicleTypeVersion.RESPONSE_CREATED);
                vehicleTypeVersion.setRt_message_from_server(
                        "Your submission is accepted into the database !"
                );

                return Response.status(Response.Status.CREATED)
                        .entity(vehicleTypeVersion)
                        .build();

            }else if(idOfInsertedRow <= 0)
            {
                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }

        }


        return null;
    }



//    @Path("/{UserID}")
//    , @PathParam("UserID")int userID

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER,GlobalConstants.ROLE_END_USER,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
    public Response update(VehicleTypeVersion vehicleTypeVersion) {

        if (vehicleTypeVersion == null) {
            throw new WebApplicationException();
        }


        int rowCount = -1;

        User userApproved = (User) Globals.accountApproved;

        if (userApproved.getRole() == GlobalConstants.ROLE_STAFF_CODE) {
            // not permitted so take a submission and put under review


            rowCount = vehicleTypeDAONew.submit_an_entry(vehicleTypeVersion, true);


            if (rowCount >= 1) {

                vehicleTypeVersion.setRt_response_code(
                        VehicleTypeVersion.RESPONSE_SUBMISSION_FOR_UPDATE_ACCEPTED);
                vehicleTypeVersion.setRt_message_from_server(
                        "Thanks for your submission. Your submission is received. " +
                                "It will be accepted after a review.");

                return Response.status(Response.Status.OK)
                        .entity(vehicleTypeVersion)
                        .build();

            } else if (rowCount <= 0) {
                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }


        } else if (userApproved.getRole() == GlobalConstants.ROLE_ADMIN_CODE) {
            // permitted to directly insert and update an entry

            rowCount = vehicleTypeDAONew.update_an_entry(vehicleTypeVersion, true);

            if (rowCount >= 1) {
                vehicleTypeVersion.setRt_response_code(VehicleTypeVersion.RESPONSE_UPDATED);
                vehicleTypeVersion.setRt_message_from_server(
                        "Your update is successful !"
                );

                return Response.status(Response.Status.OK)
                        .entity(vehicleTypeVersion)
                        .build();

            } else if (rowCount <= 0) {
                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }

        }


        return null;
    }



//    @Path("/{UserID}")
//    , @PathParam("UserID")int userID

    @PUT
    @Path("/Restore/{VersionID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
    public Response restore_a_version(@PathParam("VersionID")int versionID)
    {

        int rowCount = -1;

        User userApproved = (User) Globals.accountApproved;

        if ((userApproved.getRole() == GlobalConstants.ROLE_ADMIN_CODE) || (userApproved.getRole() == GlobalConstants.ROLE_STAFF_CODE))
        {
            // permitted to directly insert and update an entry

            rowCount = vehicleTypeDAONew.restore_a_version(versionID);

            if (rowCount >= 1) {
//                vehicleTypeVersion.setRt_response_code(VehicleTypeVersion.RESTORE_SUCCESSFUL);
//                vehicleTypeVersion.setRt_message_from_server(
//                        " Restore Successful !"
//                );

//                      .entity(vehicleTypeVersion)

                return Response.status(Response.Status.OK)
                        .build();

            }
            else if (rowCount <= 0) {

                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }

        }
        else
        {
            throw new ForbiddenException("Not Permitted !");
        }


        return null;
    }




    @PUT
    @Path("/Approve")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
    public Response approve_a_submission(VehicleTypeVersion vehicleTypeVersion)
    {

        if (vehicleTypeVersion == null) {
            throw new WebApplicationException();
        }

        int rowCount = -1;

        User userApproved = (User) Globals.accountApproved;

        if ((userApproved.getRole() == GlobalConstants.ROLE_ADMIN_CODE) || (userApproved.getRole() == GlobalConstants.ROLE_STAFF_CODE))
        {
            // permitted to directly insert and update an entry

            if(vehicleTypeVersion.getParent()==null)
            {
                // indicates an insert
                rowCount = vehicleTypeDAONew.approve_an_insert(vehicleTypeVersion);
            }
            else
            {
                // indicates an update
                rowCount = vehicleTypeDAONew.approve_an_update(vehicleTypeVersion);
            }



            if (rowCount >= 1) {
                vehicleTypeVersion.setRt_response_code(VehicleTypeVersion.APPROVE_SUCCESSFUL);
                vehicleTypeVersion.setRt_message_from_server(
                        " Approve Successful !"
                );

                return Response.status(Response.Status.OK)
                        .entity(vehicleTypeVersion)
                        .build();

            }
            else if (rowCount <= 0) {

                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }

        }
        else
        {
            throw new ForbiddenException("Not Permitted !");
        }


        return null;
    }



    @PUT
    @Path("/Reject")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
    public Response reject_a_submission(VehicleTypeVersion vehicleTypeVersion)
    {

        if (vehicleTypeVersion == null) {
            throw new WebApplicationException();
        }

        int rowCount = -1;

        User userApproved = (User) Globals.accountApproved;

        if ((userApproved.getRole() == GlobalConstants.ROLE_ADMIN_CODE) || (userApproved.getRole() == GlobalConstants.ROLE_STAFF_CODE))
        {
            // permitted to directly insert and update an entry

            rowCount = vehicleTypeDAONew.reject_an_entry(vehicleTypeVersion);

            if (rowCount >= 1) {
                vehicleTypeVersion.setRt_response_code(VehicleTypeVersion.REJECT_SUCCESSFUL);
                vehicleTypeVersion.setRt_message_from_server(
                        " Reject Successful !"
                );


                return Response.status(Response.Status.OK)
                        .entity(vehicleTypeVersion)
                        .build();

            }
            else if (rowCount <= 0) {

                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }

        }
        else
        {
            throw new ForbiddenException("Not Permitted !");
        }


        return null;
    }


















}
