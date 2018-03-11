package org.taxireferral.api.RESTEndpoints;

import net.coobird.thumbnailator.Thumbnails;
import org.taxireferral.api.DAOImages.TaxiImagesDAO;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelImages.Image;
import org.taxireferral.api.ModelImages.TaxiImage;
import org.taxireferral.api.ModelImages.TaxiImageEndpoint;
import org.taxireferral.api.ModelRoles.StaffPermissions;
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
import java.sql.Timestamp;


@Path("/api/v1/TaxiImages")
public class TaxiImagesRESTEndpoint {

    private TaxiImagesDAO taxiImagesDAO = Globals.daoTaxiImages;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_DRIVER})
    public Response insertImage(TaxiImage image)
    {
        User user = (User) Globals.accountApproved;
        image.setSubmittedBy(user.getUserID());


        if(user.getRole()==GlobalConstants.ROLE_STAFF_CODE)
        {
            StaffPermissions permissions = Globals.daoStaff.getStaffPermissions(user.getUserID());


            if(permissions.isPermitAddEditTaxiImages())
            {
                image.setApproved(true);
                image.setApprovedBy(user.getUserID());
                image.setTimestampApproved(new Timestamp(System.currentTimeMillis()));

            }
            else
            {
                image.setApproved(false);
            }

        }
        else if(user.getRole()==GlobalConstants.ROLE_ADMIN_CODE)
        {
            image.setApproved(true);
            image.setApprovedBy(user.getUserID());
            image.setTimestampApproved(new Timestamp(System.currentTimeMillis()));

        }
        else
        {
            image.setApproved(false);
        }



        int idOfInsertedRow = -1;


        image.setApprovedBy(((User)Globals.accountApproved).getUserID());

        idOfInsertedRow = taxiImagesDAO.saveTaxiImage(image,false);
        image.setImageID(idOfInsertedRow);


        if(idOfInsertedRow >=1)
        {
            return Response.status(Response.Status.CREATED)
                    .entity(image)
                    .build();

        }else if(idOfInsertedRow <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


        return null;
    }




    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
    public Response updateImage(TaxiImage image)
    {
        User user = (User) Globals.accountApproved;

//        image.setSubmittedBy(user.getUserID());


        if(user.getRole()==GlobalConstants.ROLE_STAFF_CODE)
        {
            StaffPermissions permissions = Globals.daoStaff.getStaffPermissions(user.getUserID());


            if(permissions.isPermitAddEditTaxiImages())
            {
                image.setApproved(true);
                image.setApprovedBy(user.getUserID());
                image.setTimestampApproved(new Timestamp(System.currentTimeMillis()));

            }
            else
            {
                image.setApproved(false);
            }

        }
        else if(user.getRole()==GlobalConstants.ROLE_ADMIN_CODE)
        {
            image.setApproved(true);
            image.setApprovedBy(user.getUserID());
            image.setTimestampApproved(new Timestamp(System.currentTimeMillis()));

        }
        else
        {
            image.setApproved(false);
        }



        int rowCount = taxiImagesDAO.updateImage(image);


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






    @DELETE
    @Path("/{ImageID}")
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
    public Response deleteImage(@PathParam("ImageID")int imageID)
    {

        int rowCount = taxiImagesDAO.deleteImage(imageID);

        if(rowCount>=1)
        {

            return Response.status(Response.Status.OK)
                    .build();
        }
        else if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;

    }







    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaxisAvailable(
            @QueryParam("DriverID") Integer driverID,
            @QueryParam("VehicleID") Integer vehicleID,
            @QueryParam("IsDocument") Boolean isDocument,
            @QueryParam("IsPrivate") Boolean isPrivate,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {

        TaxiImageEndpoint endPoint = new TaxiImageEndpoint();


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



        endPoint = taxiImagesDAO.getTaxiImages(
                vehicleID,
                driverID,
                isDocument,isPrivate,
                null,null,
                null,
                sortBy,limit,offset,
                getRowCount,getOnlyMetaData

        );




//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


        //Marker

        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }















    // Image MEthods

    private static final java.nio.file.Path BASE_DIR = Paths.get("./images/TaxiImages");
    private static final double MAX_IMAGE_SIZE_MB = 5;


    @POST
    @Path("/Image")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM})
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
    public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
                                @QueryParam("PreviousImageName") String previousImageName
    ) throws Exception
    {


        if(previousImageName!=null)
        {
            Files.deleteIfExists(BASE_DIR.resolve(previousImageName));
            Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + previousImageName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + previousImageName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("nine_hundred_" + previousImageName + ".jpg"));
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



            Thumbnails.of(BASE_DIR.toString() + "/" + filename)
                    .size(900,900)
                    .outputFormat("jpg")
                    .toFile(new File(BASE_DIR.toString() + "/" + "nine_hundred_" + filename));




        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    @GET
    @Path("/Image/{name}")
    @Produces("image/jpeg")
    public InputStream getImage(@PathParam("name") String fileName) {


//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


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
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
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
