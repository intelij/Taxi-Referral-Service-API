package org.taxireferral.api.RESTEndpoints;

import jdk.nashorn.internal.objects.Global;
import net.coobird.thumbnailator.Thumbnails;
import org.taxireferral.api.DAOs.VehicleTypeDAONew;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.VehicleTypeVersion;
import org.taxireferral.api.ModelEndpoints.VehicleTypeEndPoint;
import org.taxireferral.api.ModelImages.Image;
import org.taxireferral.api.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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









    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicleType(
            @QueryParam("SubmittedBy")Integer submittedBy,
            @QueryParam("ParentIDNull")Boolean parentIDNULL,
            @QueryParam("ParentID")Integer parentID,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount)
    {


        final int max_limit = 100;

        VehicleTypeEndPoint endPoint = new VehicleTypeEndPoint();

        if(limit!= null)
        {
            if(limit>=max_limit)
            {
                limit = max_limit;
            }

            endPoint.setLimit(limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(offset);
        }




        endPoint =  Globals.daoVehicleTypeGet.getVehicleType(
            submittedBy,parentIDNULL,parentID,sortBy,limit,offset,getRowCount
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




















    // Image MEthods

    private static final java.nio.file.Path BASE_DIR = Paths.get("./images/vehicleType");
    private static final double MAX_IMAGE_SIZE_MB = 2;


    @POST
    @Path("/Image")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM})
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_DRIVER,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_END_USER})
    public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
                                @QueryParam("PreviousImageName") String previousImageName
    ) throws Exception
    {


        if(previousImageName!=null)
        {
            Files.deleteIfExists(BASE_DIR.resolve(previousImageName));
            Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + previousImageName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + previousImageName + ".jpg"));
        }


        File theDir = new File(BASE_DIR.toString());

        // if the directory does not exist, create it
        if (!theDir.exists()) {

            System.out.println("Creating directory: " + BASE_DIR.toString());

            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            }
            catch(Exception se){
                //handle it
            }
            if(result) {
                System.out.println("DIR created");
            }
        }



        String fileName = "" + System.currentTimeMillis();

        // Copy the file to its location.
        long filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
        {
            // delete file if it exceeds the file size limit
            Files.deleteIfExists(BASE_DIR.resolve(fileName));

            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }


        createThumbnails(fileName);


        Image imageOld = new Image();
        imageOld.setPath(fileName);

        // Return a 201 Created response with the appropriate Location header.

        return Response.status(Response.Status.CREATED).location(URI.create("/api/Images/" + fileName)).entity(imageOld).build();
    }



    private void createThumbnails(String filename)
    {
        try {

            Thumbnails.of(BASE_DIR.toString() + "/" + filename)
                    .size(300,300)
                    .outputFormat("jpg")
                    .toFile(new File(BASE_DIR.toString() + "/" + "three_hundred_" + filename));

            //.toFile(new File("five-" + filename + ".jpg"));

            //.toFiles(Rename.PREFIX_DOT_THUMBNAIL);


            Thumbnails.of(BASE_DIR.toString() + "/" + filename)
                    .size(500,500)
                    .outputFormat("jpg")
                    .toFile(new File(BASE_DIR.toString() + "/" + "five_hundred_" + filename));



        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @GET
    @Path("/Image/{name}")
    @Produces("image/jpeg")
    public InputStream getImage(@PathParam("name") String fileName) {

        //fileName += ".jpg";
        java.nio.file.Path dest = BASE_DIR.resolve(fileName);

        if (!Files.exists(dest)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }


        try {
            return Files.newInputStream(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @DELETE
    @Path("/Image/{name}")
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_DRIVER,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_END_USER})
    public Response deleteImageFile(@PathParam("name")String fileName)
    {

        boolean deleteStatus = false;

        Response response;

        System.out.println("Filename: " + fileName);

        try {


            //Files.delete(BASE_DIR.resolve(fileName));
            deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));

            // delete thumbnails
            Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if(!deleteStatus)
        {
            response = Response.status(Response.Status.NOT_MODIFIED).build();

        }else
        {
            response = Response.status(Response.Status.OK).build();
        }

        return response;
    }











}
