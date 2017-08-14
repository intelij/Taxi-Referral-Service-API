package org.taxireferral.api.RESTEndpointRoles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sargue.mailgun.Mail;
import okhttp3.*;
import org.taxireferral.api.DAORoles.DAOUserSignUp;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelNotifications.FirebaseNotification;
import org.taxireferral.api.ModelNotifications.NotificationData;
import org.taxireferral.api.ModelRoles.EmailVerificationCode;
import org.taxireferral.api.ModelRoles.PhoneVerificationCode;
import org.taxireferral.api.ModelRoles.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by sumeet on 14/8/17.
 */

@Path("/api/v1/User/SignUp")
public class UserSignUpRESTEndpoint {

    private DAOUserSignUp daoUser = Globals.daoUserSignUp;


    /* Sign Up */

//    driverRegistration(User user)
//    endUserRegistration(User user)
//    staffRegistration(User user)
//    public Response checkUsername(@PathParam("username")String username)
//    public Response checkEmailVerificationCode(
//    public Response sendVerificationEmail(@PathParam("email")String email)






    @POST
    @Path("/DriverRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertItem(User user)
    {
        return userRegistration(user, GlobalConstants.ROLE_DRIVER_CODE);
    }



    @POST
    @Path("/EndUserRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertEndUser(User user)
    {
        return userRegistration(user,GlobalConstants.ROLE_END_USER_CODE);
    }


    @POST
    @Path("/StaffRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertStaff(User user)
    {
        return userRegistration(user,GlobalConstants.ROLE_STAFF_CODE);
    }




    private Response userRegistration(User user, int role)
    {
        if(user==null)
        {
            throw new WebApplicationException();
        }


        user.setRole(role);


        int idOfInsertedRow =-1;



        if(user.getRt_registration_mode()==User.REGISTRATION_MODE_EMAIL)
        {
            idOfInsertedRow = Globals.daoUserSignUp.registerUsingEmail(user,false);


            System.out.println("Email : " + user.getEmail()
                    + "\nPassword : " + user.getPassword()
                    + "\nRegistration Mode : " + user.getRt_registration_mode()
                    + "\nName : " + user.getName()
                    + "\nInsert Count : " + idOfInsertedRow
                    + "\nVerificationCode : " + user.getRt_email_verification_code()
            );


            if(idOfInsertedRow>=1)
            {
                // registration successful therefore send email to notify the user
                Mail.using(Globals.configurationMailgun)
                        .body()
                        .h1("Registration successful for your account")
                        .p("Your account has been Created.")
                        .h3("Your E-mail : " + user.getEmail())
                        .p("You can login with your email and password that you have provided. Thank you for registering with Taxi Referral Service (TRS).")
                        .mail()
                        .to(user.getEmail())
                        .subject("Taxi Referral Service : Account Registered")
                        .from("Taxi Referral Service","noreply@taxireferral.org")
                        .build()
                        .send();


            }
        }
        else if(user.getRt_registration_mode()==User.REGISTRATION_MODE_PHONE)
        {
            idOfInsertedRow = daoUser.registerUsingPhone(user,false);


            System.out.println("Phone : " + user.getPhone()
                    + "\nEmail : " + user.getEmail()
                    + "\nPassword : " + user.getPassword()
                    + "\nRegistration Mode : " + user.getRt_registration_mode()
                    + "\nName : " + user.getName()
                    + "\nInsert Count : " + idOfInsertedRow
                    + "\nVerificationCode : " + user.getRt_phone_verification_code()
            );

            // send notification to the mobile number via SMS

            if(idOfInsertedRow>=1)
            {
                sendSMS("Congratulations your account has been registered with Taxi Referral Service.",
                        user.getPhone());

            }

        }


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



    @GET
    @Path("/CheckUsernameExists/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkUsername(@PathParam("username")String username)
    {
        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information

        boolean result = daoUser.checkUsernameExists(username);

        System.out.println(username);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(result)
        {
            return Response.status(Response.Status.OK)
                    .build();

        } else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }






    @GET
    @Path("/CheckEmailVerificationCode/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkEmailVerificationCode(
            @PathParam("email")String email,
            @QueryParam("VerificationCode")String verificationCode
    )
    {
        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information

        boolean result = Globals.daoEmailVerificationCodes.checkEmailVerificationCode(email,verificationCode);

        System.out.println(email);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(result)
        {
            return Response.status(Response.Status.OK)
                    .build();

        } else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }












    @PUT
    @Path("/SendEmailVerificationCode/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendVerificationEmail(@PathParam("email")String email)
    {

        int rowCount = 0;


        EmailVerificationCode verificationCode = Globals.daoEmailVerificationCodes.checkEmailVerificationCode(email);

        if(verificationCode==null)
        {
            // verification code not generated for this email so generate one and send this to the user

            String emailVerificationCode = new BigInteger(30, Globals.random).toString(32);

            Timestamp timestampExpiry
                    = new Timestamp(
                    System.currentTimeMillis()
                            + GlobalConstants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES *60*1000
            );


            rowCount = Globals.daoEmailVerificationCodes.insertEmailVerificationCode(
                    email,emailVerificationCode,timestampExpiry,true
            );


            if(rowCount==1)
            {
                // saved successfully


                Mail.using(Globals.configurationMailgun)
                        .body()
                        .h1("Your E-mail Verification Code is given below")
                        .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
                        .h3("The e-mail verification code is : " + emailVerificationCode)
                        .p("This verification code will expire at " + timestampExpiry.toLocaleString() + ". Please use this code before it expires.")
                        .mail()
                        .to(email)
                        .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
                        .from("Taxi Referral Service","noreply@taxireferral.org")
                        .build()
                        .send();

            }


        }
        else
        {

            System.out.println("Email Verification Code : " + verificationCode.getVerificationCode());

            Mail.using(Globals.configurationMailgun)
                    .body()
                    .h1("Your E-mail Verification Code is given below")
                    .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
                    .h3("The e-mail verification code is : " + verificationCode.getVerificationCode())
                    .p("This verification code will expire at " + verificationCode.getTimestampExpires().toLocaleString() + ". Please use this code before it expires.")
                    .mail()
                    .to(email)
                    .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
                    .from("Taxi Referral Service","noreply@taxireferral.org")
                    .build()
                    .send();


            rowCount = 1;
        }




//
//        // verification code has been generated : we need to check if it has expired or not . If the code is
//        // not expired send the existing code.
//        // if the code is expired then generate the new code and save this code and then send it to the user
//
//        if(verificationCode.getTimestampExpires().before(new Timestamp(System.currentTimeMillis())))
//        {
//            // code is expired
//
//            String emailVerificationCode = new BigInteger(30, Globals.random).toString(32);
//            Timestamp timestampExpiry
//                    = new Timestamp(
//                    System.currentTimeMillis()
//                            + GlobalConstants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES *60*1000
//            );
//
//            rowCount = Globals.daoEmailVerificationCodes.updateEmailVerificationCode(
//                    email,emailVerificationCode,timestampExpiry);
//
//            if(rowCount==1)
//            {
//                // new code saved successful
//
//                System.out.println("Email Verification Code : " + emailVerificationCode);
//
//                Mail.using(Globals.configurationMailgun)
//                        .body()
//                        .h1("Your E-mail Verification Code is given below")
//                        .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
//                        .h3("The e-mail verification code is : " + emailVerificationCode)
//                        .p("This verification code will expire at " + timestampExpiry.toLocaleString() + ". Please use this code before it expires.")
//                        .mail()
//                        .to(email)
//                        .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
//                        .from("Taxi Referral Service","noreply@taxireferral.org")
//                        .build()
//                        .send();
//
////                    .p("Please use this code within 15 minutes because it will expire after 15 minutes.")
//
//            }
//
//
//        }
//        else
//        {
//            // code is not expired : email the existing code
//
//
//        }


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






    /* Methods for Phone Verification for Sign-Up */





    @GET
    @Path("/CheckPhoneVerificationCode/{phone}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkPhoneVerificationCode(
            @PathParam("phone")String phone,
            @QueryParam("VerificationCode")String verificationCode
    )
    {
        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information

        boolean result = Globals.daoPhoneVerificationCodes.checkPhoneVerificationCode(phone,verificationCode);

        System.out.println(phone);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(result)
        {
            return Response.status(Response.Status.OK)
                    .build();

        } else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }












    @PUT
    @Path("/SendPhoneVerificationCode/{phone}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendVerificationPhone(@PathParam("phone")String phone)
    {

        int rowCount = 0;


        PhoneVerificationCode verificationCode = Globals.daoPhoneVerificationCodes.checkPhoneVerificationCode(phone);

        if(verificationCode==null)
        {
            // verification code not generated for this email so generate one and send this to the user


            BigInteger phoneCode = new BigInteger(15, Globals.random);

//            String emailVerificationCode = new BigInteger(30, Globals.random).toString(32);

            int phoneOTP = phoneCode.intValue();

            Timestamp timestampExpiry
                    = new Timestamp(
                    System.currentTimeMillis()
                            + GlobalConstants.PHONE_OTP_EXPIRY_MINUTES *60*1000
            );


            rowCount = Globals.daoPhoneVerificationCodes.insertPhoneVerificationCode(
                    phone,String.valueOf(phoneOTP),timestampExpiry,true
            );


            if(rowCount==1)
            {
                // saved successfully

                System.out.println("Phone Verification Code : " + phoneOTP);

//                Mail.using(Globals.configurationMailgun)
//                        .body()
//                        .h1("Your Phone Verification Code is given below")
//                        .p("You have requested to verify your phone. If you did not request the phone verification please ignore this e-mail message.")
//                        .h3("The e-mail verification code is : " + emailVerificationCode)
//                        .p("This verification code will expire at " + timestampExpiry.toLocaleString() + ". Please use this code before it expires.")
//                        .mail()
//                        .to(phone)
//                        .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
//                        .from("Taxi Referral Service","noreply@taxireferral.org")
//                        .build()
//                        .send();


                sendOTP(String.valueOf(phoneOTP),phone);

            }


        }
        else
        {

            System.out.println("Phone Verification Code : " + verificationCode.getVerificationCode());


//            Mail.using(Globals.configurationMailgun)
//                    .body()
//                    .h1("Your E-mail Verification Code is given below")
//                    .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
//                    .h3("The e-mail verification code is : " + verificationCode.getVerificationCode())
//                    .p("This verification code will expire at " + verificationCode.getTimestampExpires().toLocaleString() + ". Please use this code before it expires.")
//                    .mail()
//                    .to(email)
//                    .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
//                    .from("Taxi Referral Service","noreply@taxireferral.org")
//                    .build()
//                    .send();




            sendOTP(verificationCode.getVerificationCode(),phone);


            rowCount = 1;
        }



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









    public void sendOTP(String otp, String phone)
    {




//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
//        String json = gson.toJson(firebaseNotification);




        String urlOTP = "https://control.msg91.com/api/sendotp.php?authkey=" +
                "169752ACa9EZW5gjr59903723" +
                "&mobile=91" +
                phone +
                "&message=Your%20one time password (OTP) for Taxi Referral is " +
                otp +
                "&sender=TRSAPP&otp=" +
                otp;


        final Request request = new Request.Builder()
                .url(urlOTP)
                .get()
                .build();



//        System.out.print(json + "\n");


        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                System.out.print("Sending notification failed !");
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {


                if(response.isSuccessful())
                {
                    System.out.print(response.body().string() + "\n");
//                    FirebaseResponse firebaseResponse = gson.fromJson(response.body().string(),FirebaseResponse.class);
                }
                else
                {
                    System.out.print(response.toString()+ "\n");
                }

            }
        });
    }







    public void sendSMS(String message, String phone)
    {




//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
//        String json = gson.toJson(firebaseNotification);



        String urlMessage = "http://api.msg91.com/api/sendhttp.php?authkey=" +
                "169752ACa9EZW5gjr59903723" +
                "&mobiles=" +
                "91" +
                phone +
                "&message=" +
                message +
                "&sender=TRSAPP&route=4&country=91";


        final Request request = new Request.Builder()
                .url(urlMessage)
                .get()
                .build();



//        System.out.print(json + "\n");


        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                System.out.print("Sending notification failed !");
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {


                if(response.isSuccessful())
                {
                    System.out.print(response.body().string() + "\n");
//                    FirebaseResponse firebaseResponse = gson.fromJson(response.body().string(),FirebaseResponse.class);
                }
                else
                {
                    System.out.print(response.toString()+ "\n");
                }

            }
        });
    }


}
