package org.taxireferral.api.RESTEndpointsBilling;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import jdk.nashorn.internal.parser.JSONParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

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
    @Produces(MediaType.TEXT_PLAIN)
    public String getProfileByDriver(@QueryParam("latCenter")double latCenter,
                                       @QueryParam("lonCenter")double lonCenter) throws IOException {

        System.out.println("Payments");

        String clientID = "ocVqgTdqgKEbwuKyozbHGl0yIBDEHuqLKfM6Jh0R";
        String clientSecret = "NsANuLkqsTS0joP7uN4IYwu7PgRHSbwltRmi4x07FGgZluHOUXfXtC1q2Wwz2Iat8pk4hBq2stjl6pnkU3FS1vzwqrdbjlNnBqsR4vcl8mzPXcGaega1vl9SmDBPhxCB";

        OkHttpClient client = new OkHttpClient();

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + clientID + "&client_secret=" + clientSecret);
        Request request = new Request.Builder()
                .url("https://www.instamojo.com/oauth2/token/")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .post(body)
                .build();


        okhttp3.Response response = client.newCall(request).execute();


        JSONObject jsonObject = new JSONObject(response.body().string());
        String access_token = jsonObject.getString("access_token");




//        System.out.println("Response \n" + response.body().string());


//        if(access_token !=null )
//        {
//            access_token = "production" + access_token;
//            return access_token;
//        }
//        else
//        {
//            return Response.status(Response.Status.NO_CONTENT)
//                    .build();
//        }


        access_token = "production" + access_token;
        return access_token;


    }




}
