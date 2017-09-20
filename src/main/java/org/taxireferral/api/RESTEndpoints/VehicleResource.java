package org.taxireferral.api.RESTEndpoints;

import net.coobird.thumbnailator.Thumbnails;
import org.taxireferral.api.DAOs.VehicleDAO;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.ModelEndpoints.VehicleEndPoint;
import org.taxireferral.api.ModelImages.Image;
import org.taxireferral.api.ModelRoles.StaffPermissions;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.ModelUtility.Location;

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
 * Created by sumeet on 7/6/17.
 */



@Path("/api/v1/Vehicle")
public class VehicleResource {

    private VehicleDAO daoVehicle = Globals.vehicleDAO;


//    @RolesAllowed({GlobalConstants.ROLE_DRIVER})

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertVehicle(Vehicle vehicle)
    {

        int idOfInsertedRow = -1;



        idOfInsertedRow = daoVehicle.insert_vehicle(vehicle,false);
        vehicle.setVehicleID(idOfInsertedRow);



        if(idOfInsertedRow >=1)
        {
            return Response.status(Response.Status.CREATED)
                    .entity(vehicle)
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
    @Path("/EnableTaxi/{VehicleID}/{Enabled}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
    public Response enableTaxi(@PathParam("VehicleID")int vehicleID,
                               @PathParam("Enabled")boolean enabled)
    {


        User user = (User) Globals.accountApproved;
        StaffPermissions permissions = Globals.daoStaff.getStaffPermissions(user.getUserID());

        // check staff permissions

        if(user.getRole()!=GlobalConstants.ROLE_ADMIN_CODE)
        {
            if(permissions==null || !permissions.isPermitTaxiRegistrationAndRenewal())
            {
                return Response.status(Response.Status.EXPECTATION_FAILED)
                        .build();
            }
        }


        System.out.println("Update By Admin");
        int rowCount = daoVehicle.enableVehicleByAdmin(enabled,vehicleID);


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




    @PUT
    @Path("/ExtendRegistration/{VehicleID}/{MonthsToExtend}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
    public Response extendRegistration(@PathParam("VehicleID")int vehicleID,
                                  @PathParam("MonthsToExtend")int monthsToExtend)
    {

        User user = (User) Globals.accountApproved;
        StaffPermissions permissions = Globals.daoStaff.getStaffPermissions(user.getUserID());

        // check staff permissions

        if(user.getRole()!=GlobalConstants.ROLE_ADMIN_CODE)
        {
            if(permissions==null || !permissions.isPermitTaxiRegistrationAndRenewal())
            {
                return Response.status(Response.Status.EXPECTATION_FAILED)
                        .build();
            }
        }


        System.out.println("Extend Registration : Months " + monthsToExtend);
        int rowCount = daoVehicle.extend_registration(vehicleID,monthsToExtend);


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





//    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
//        , @PathParam("VehicleID")int vehicleID
///{VehicleID}

    @PUT
    @Path("/UpdateBySelf")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response updateBySelf(Vehicle vehicle)
    {

        vehicle.setDriverID(((User)Globals.accountApproved).getUserID());
        int rowCount = daoVehicle.updateVehicleByDriver(vehicle);

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
    @Path("/UpdateByAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
    public Response updateByAdmin(Vehicle vehicle)
    {
        User user = (User) Globals.accountApproved;
        StaffPermissions permissions = Globals.daoStaff.getStaffPermissions(user.getUserID());




        if(user.getRole()!=GlobalConstants.ROLE_ADMIN_CODE) {

            if (permissions == null || !permissions.isPermitTaxiProfileUpdate()) {
                return Response.status(Response.Status.EXPECTATION_FAILED)
                        .build();
            }
        }


        int rowCount = daoVehicle.updateVehicleByAdmin(vehicle);

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





//    @RolesAllowed({GlobalConstants.ROLE_DRIVER})

    @PUT
    @Path("/UpdateLocation")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response updateLocation(Location location)
    {

        int rowCount = daoVehicle.updateLocationByDriver(location,((User)Globals.accountApproved).getUserID());

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




//    @RolesAllowed({GlobalConstants.ROLE_DRIVER})

//    @PUT
//    @Path("/UpdateStatus/{VehicleID}/Status/{Status}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateStatus(@PathParam("Status") int status, @PathParam("VehicleID")int vehicleID)
//    {
//
////        if(Globals.accountApproved instanceof User)
////        {
////            shop.setShopAdminID(((ShopAdmin) Globals.accountApproved).getShopAdminID());
////        }
////        else
////        {
////            throw new ForbiddenException();
////        }
//
//
//
//        int rowCount = daoVehicle.update_status(status,vehicleID);
//
//        if(rowCount >= 1)
//        {
//            return Response.status(Response.Status.OK)
//                    .build();
//        }
//        else if(rowCount <= 0)
//        {
//            return Response.status(Response.Status.NOT_MODIFIED)
//                    .build();
//        }
//
//
//        return null;
//    }



    @PUT
    @Path("/SetTaxiAvailable")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response setTaxiAvailable()
    {

        int rowCount = daoVehicle.setTaxiAvailable(((User)Globals.accountApproved).getUserID());



//        try {
//            Thread.sleep(300);
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


    @PUT
    @Path("/SetTaxiNotAvailable")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response setTaxiNotAvailable()
    {

        int rowCount = daoVehicle.setTaxiNotAvailable(((User)Globals.accountApproved).getUserID());



//
//        try {
//            Thread.sleep(300);
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





//    @RolesAllowed({GlobalConstants.ROLE_DRIVER,GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})

    @DELETE
    @Path("/{VehicleID}")
    public Response deleteVehicle(@PathParam("VehicleID")int vehicleID)
    {

        int rowCount = daoVehicle.deleteVehicle(vehicleID);


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

//    @QueryParam("Status")Integer status,



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaxisAvailable(
            @QueryParam("LatPickup") Double latPickUp, @QueryParam("LonPickup") Double lonPickUp,
            @QueryParam("DriversGender") Boolean driversGender,
            @QueryParam("AdultMalesCount") Integer adultMalesCount,
            @QueryParam("AdultFemalesCount") Integer adultFemalesCount,
            @QueryParam("ChildrensCount") Integer childrensCount,
            @QueryParam("ShowFavouritesForUserID") Integer showFavouritesForUserID,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {



        VehicleEndPoint endPoint = new VehicleEndPoint();

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



        endPoint = daoVehicle.getTaxisAvailable(
                latPickUp,lonPickUp,driversGender,sortBy,limit,offset,
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
    @Path("/GetTaxiProfileForAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaxisForAdmin(
            @QueryParam("LatPickup") Double latPickUp, @QueryParam("LonPickup") Double lonPickUp,
            @QueryParam("DriversGender") Boolean driversGender,
            @QueryParam("IsEnabled") Boolean isEnabled,
            @QueryParam("RegistrationExpired") Boolean registrationExpired,
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



        VehicleEndPoint endPoint = daoVehicle.getTaxiProfileForAdmin(
                latPickUp,lonPickUp,
                driversGender,isEnabled,
                registrationExpired,
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






//    @Path("/{DriverID}")
//@PathParam("DriverID")int driverID,

    @GET
    @Path("/GetProfile")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response getProfileByDriver(@QueryParam("latCenter")double latCenter,
                                       @QueryParam("lonCenter")double lonCenter)
    {

        System.out.println("Vehicle Get Taxi Profile !");

        User user = (User) Globals.accountApproved;

        Vehicle vehicle = daoVehicle.getProfileByDriver(user.getUserID(),latCenter,lonCenter);

        if(vehicle!= null)
        {
            return Response.status(Response.Status.OK)
                    .entity(vehicle)
                    .build();
        }
        else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }





    // Image MEthods

    private static final java.nio.file.Path BASE_DIR = Paths.get("./images/Vehicle");
    private static final double MAX_IMAGE_SIZE_MB = 2;


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
