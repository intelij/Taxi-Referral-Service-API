package org.taxireferral.api.RESTEndpointsBilling;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.taxireferral.api.Globals.GlobalConstants;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/api/v1/Payments")
public class InstaMojoREST {



    @GET
    @Path("/GetToken")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfileByDriver(@QueryParam("latCenter")double latCenter,
                                       @QueryParam("lonCenter")double lonCenter) throws IOException {

        System.out.println("Payments");

        OkHttpClient client = new OkHttpClient();

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=test&client_secret=test");
        Request request = new Request.Builder()
                .url("https://test.instamojo.com/oauth2/token/")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .post(body)
                .build();


        okhttp3.Response response = client.newCall(request).execute();


//        System.out.println("Response \n" + response.body().string());



        return Response.status(Response.Status.OK)
                .entity(response.body().string())
                .build();

//            return Response.status(Response.Status.NO_CONTENT)
//                    .build();
    }




}
