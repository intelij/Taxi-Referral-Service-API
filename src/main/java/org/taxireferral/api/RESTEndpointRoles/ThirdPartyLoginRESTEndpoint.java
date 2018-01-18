package org.taxireferral.api.RESTEndpointRoles;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import net.sargue.mailgun.Mail;
import org.taxireferral.api.Globals.GlobalConstants;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.ModelRoles.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Collections;

/**
 * Created by sumeet on 14/8/17.
 */
public class ThirdPartyLoginRESTEndpoint {


    /* Methods */

//    googleSignIn(String idTokenString)
//



//
//    @POST
//    @Path("/DriverRegistrationGoogle/{IDToken}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response googleSignIn(@PathParam("IDToken")String idTokenString)
//    {
//        int idOfInsertedRow;
//
//        System.out.println("Google ID Token" + idTokenString);
//
//        HttpTransport httpTransport = new NetHttpTransport();
//        JsonFactory jsonFactory = new JacksonFactory();
//
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport,jsonFactory)
//                .setAudience(Collections.singletonList("128070485514-t0rt5t6at3j888cuclgi1lj1t6a0ds99.apps.googleusercontent.com"))
//                // Or, if multiple clients access the backend:
//                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
//                .build();
//
//
//
//        GoogleIdToken idToken = null;
//
//        try {
//
//            idToken = verifier.verify(idTokenString);
//
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String userId  = "";
//        String email = "";
//        String name = "";
//
//
//        if (idToken != null) {
//            GoogleIdToken.Payload payload = idToken.getPayload();
//
//            // Print user identifier
//            userId = payload.getSubject();
//            System.out.println("User ID: " + userId);
//
//            // Get profile information from payload
//            email = payload.getEmail();
//            boolean emailVerified = payload.getEmailVerified();
//            name = (String) payload.get("name");
//            String pictureUrl = (String) payload.get("picture");
//            String locale = (String) payload.get("locale");
//            String familyName = (String) payload.get("family_name");
//            String givenName = (String) payload.get("given_name");
//
//            // Use or store profile information
//            // ...
//
//
//            System.out.println("Email : " + email
//                    + "\nName : " + name);
//
//
//        }
//        else
//        {
//            System.out.println("Invalid ID token.");
//
//            return Response.status(Response.Status.PROXY_AUTHENTICATION_REQUIRED)
//                    .build();
//        }
//
//
//
//
//        User userChecked = Globals.daoUserNew.checkGoogleID(userId);
//
//        String token = new BigInteger(130, Globals.random).toString(32);
//        Timestamp timestampExpiry = new Timestamp(System.currentTimeMillis() + GlobalConstants.TOKEN_DURATION_MINUTES*60*1000);
//
//
//
//
////        boolean userCreated = false;
//
//        if(userChecked==null)
//        {
//            // create new user
//
//            userChecked = new User();
//
////            userChecked.setUsername(email);
//            userChecked.setGoogleID(userId);
//            userChecked.setName(name);
//            userChecked.setEmail(email);
////            userChecked.setEmailVerified(true);
//            userChecked.setRole(GlobalConstants.ROLE_DRIVER_CODE);
//
//
//            SecureRandom random = new SecureRandom();
//            String password = new BigInteger(30, random).toString(32);
//
//            userChecked.setPassword(password);
//            userChecked.setToken(token);
//            userChecked.setTimestampTokenExpires(timestampExpiry);
//
//
//            // updates the token also
//            idOfInsertedRow  = Globals.daoUserNew.saveGoogleProfile(userChecked,false);
//
//            if(idOfInsertedRow==-1)
//            {
////                userChecked = null;
//
//
//                return Response.status(Response.Status.NOT_MODIFIED)
//                        .build();
//            }
//            else
//            {
//                userChecked.setUserID(idOfInsertedRow);
//
//                // send an email notifying the user about new account creation and also giving him the password
//                Mail.using(Globals.getMailgunConfiguration())
//                        .body()
//                        .h1("Taxi Referral : Account Registered via Google Profile")
//                        .p("Your account has been Created.")
//                        .h3("Your Username is : " + userChecked.getEmail())
//                        .h3("Your Password (auto-generated) is  : " + userChecked.getPassword())
//                        .p("You can login via google account or also using your username and password. In case login via google Account does not work you can use the username and password provided here to login directly to your account. Please store these credentials in the safe place because you may need them in future. ")
//                        .mail()
//                        .to(userChecked.getEmail())
//                        .subject("Taxi Referral Service : Account registered via Google Profile")
//                        .from("Taxi Referral Service","noreply@taxireferral.org")
//                        .build()
//                        .send();
//
//
//
//                return Response.status(Response.Status.CREATED)
//                        .entity(userChecked)
//                        .build();
//            }
//
//
//        }
//        else
//        {
//
//            userChecked.setToken(token);
//            userChecked.setTimestampTokenExpires(timestampExpiry);
//
//            // update the new token
//            Globals.daoUserNew.updateToken(userChecked);
//
//
////            if(SimpleServer.connection!=null && SimpleServer.connection.isOpen())
////            {
////                SimpleServer.connection.send("Logged IN View Google Account !");
////            }
//
////            Globals.sendSms("Your Verification code is : aioib3");
//
//
//            return Response.status(Response.Status.OK)
//                    .entity(userChecked)
//                    .build();
//        }
//    }



}
