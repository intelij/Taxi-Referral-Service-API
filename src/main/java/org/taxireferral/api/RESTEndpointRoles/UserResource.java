package org.taxireferral.api.RESTEndpointRoles;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import net.coobird.thumbnailator.Thumbnails;
import net.sargue.mailgun.Mail;
import org.taxireferral.api.DAORoles.DAOUser;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelImages.Image;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.WebSocket.SimpleServer;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by sumeet on 2/4/17.
 */


//@Path("/api/v1/User")
public class UserResource {

    // insert and sign up
    // update profile
    // check username exist
    // login
    // verify email
    // verify phone

    // Image_old Methods
    //

    private DAOUser daoUser = Globals.daoUser;


    @POST
    @Path("/DriverRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertItem(User user)
    {
        if(user==null)
        {
            throw new WebApplicationException();
        }


        user.setRole(GlobalConstants.ROLE_DRIVER_CODE);
        int idOfInsertedRow = daoUser.insertUser(user,false);

        user.setUserID(idOfInsertedRow);


        if(idOfInsertedRow >=1)
        {

            return Response.status(Response.Status.CREATED)
                    .entity(user)
                    .build();

        }else if(idOfInsertedRow <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


        return null;
    }














    @PUT
    @Path("/ChangeEmail/{NewEmail}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
    public Response changeEmail(@PathParam("NewEmail")String newEmail)
    {

        User user = (User) Globals.accountApproved;
        user.setEmail(newEmail);



        int rowCount = daoUser.updateEmail(user);


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





//    @PUT
//    @Path("/SendEmailVerificationCode")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
//    public Response sendVerificationEmail()
//    {
//
//        User user = (User) Globals.accountApproved;
//        int rowCount = 0;
//
//        System.out.println("Username : " + user.getUsername());
//
//        User userTemp = daoUser.checkEmailCode(user.getUsername());
//
////        System.out.println("User TEMP : " + userTemp.getEmailVerificationCodeTimestampExpires().toLocaleString());
//
//        Timestamp timestampExpries = userTemp.getEmailVerificationCodeTimestampExpires();
//
//        if(timestampExpries==null || (timestampExpries!=null && timestampExpries.before(new Timestamp(System.currentTimeMillis()))))
//        {
//           // verification code expired so generate new code and email it to the user
//
//            String emailVerificationCode = new BigInteger(30, Globals.random).toString(32);
//            Timestamp timestampExpiry = new Timestamp(System.currentTimeMillis() + 15*60*1000);
//
//            user.setEmailVerificationCode(emailVerificationCode);
//            user.setEmailVerificationCodeTimestampExpires(timestampExpiry);
//
//
//            rowCount = daoUser.setEmailVerificationCode(user);
//
//            if(rowCount==1)
//            {
//
//                System.out.println("Email Verification Code : " + emailVerificationCode);
//
//                Mail.using(Globals.configurationMailgun)
//                        .body()
//                        .h1("Your E-mail Verification Code is given below")
//                        .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
//                        .h3("The e-mail verification code is : " + emailVerificationCode)
//                        .p("Please use this code within 15 minutes because it will expire after 15 minutes.")
//                        .mail()
//                        .to(userTemp.getEmail())
//                        .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
//                        .from("Taxi Referral Service","noreply@taxireferral.org")
//                        .build()
//                        .send();
//            }
//
//        }
//        else
//        {
//            // the existing code has not expired to email the existing code
//
//            System.out.println("Email Verification Code (Resent): " + userTemp.getEmailVerificationCode());
//
//            Mail.using(Globals.configurationMailgun)
//                    .body()
//                    .h1("Your E-mail Verification Code is given below")
//                    .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
//                    .h3("The e-mail verification code is : " + userTemp.getEmailVerificationCode())
//                    .p("Please use this code within 15 minutes because it will expire after 15 minutes.")
//                    .mail()
//                    .to(userTemp.getEmail())
//                    .subject("E-mail Verification Code for Taxi Referral : Resend Request")
//                    .from("Taxi Referral Service","noreply@taxireferral.org")
//                    .build()
//                    .send();
//
//
//            rowCount = 1;
//        }
//
//
//
//
//        if(rowCount >= 1)
//        {
//
//
//
//            return Response.status(Response.Status.OK)
//                    .build();
//        }
//        if(rowCount == 0)
//        {
//
//            return Response.status(Response.Status.NOT_MODIFIED)
//                    .build();
//        }
//
//        return null;
//    }




//    @PUT
//    @Path("/VerifyEmail/{VerificationCode}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed({GlobalConstants.ROLE_DRIVER})
//    public Response verifyEmail(@PathParam("VerificationCode")String verificationCode)
//    {
//
//        User user = (User) Globals.accountApproved;
//        user.setEmailVerificationCode(verificationCode);
//
//        int rowCount = daoUser.setEmailVerified(user);
//
//
//        if(rowCount >= 1)
//        {
//            return Response.status(Response.Status.OK)
//                    .build();
//        }
//        if(rowCount == 0)
//        {
//
//            return Response.status(Response.Status.NOT_MODIFIED)
//                    .build();
//        }
//
//        return null;
//    }





//    @GET
//    public Response getSample()
//    {
//
//        System.out.println("Get method !");
//
//        return Response.status(Response.Status.OK)
//                .entity(new String("Hello this is a test !"))
//                .build();
//
//    }






//    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_DRIVER,GlobalConstants.ROLE_END_USER})










}
