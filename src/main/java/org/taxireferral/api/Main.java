package org.taxireferral.api;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.taxireferral.api.Globals.Globals;
import org.taxireferral.api.Model.TripRequest;
import org.taxireferral.api.Model.Vehicle;
import org.taxireferral.api.Model.VehicleType;
import org.taxireferral.api.Model.VehicleTypeVersion;
import org.taxireferral.api.ModelRoles.EmailVerificationCode;
import org.taxireferral.api.ModelRoles.User;
import org.taxireferral.api.WebSocket.SimpleServer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Main class.
 *
 */



public class Main {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:5500";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
//    public static HttpServer startServer() {
//        // create a resource config that scans for JAX-RS resources and providers
//        // in org.taxireferral.api package
//        final ResourceConfig rc = new ResourceConfig().packages("org.taxireferral.api");
//
//        // create and start a new instance of grizzly http server
//        // exposing the Jersey application at BASE_URI
//        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
//    }



    public static Server startJettyServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in org.taxireferral.api package
        final ResourceConfig rc = new ResourceConfig().packages("org.taxireferral.api");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
//        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        return JettyHttpContainerFactory.createServer(URI.create(BASE_URI),rc);

    }


    public static Server startJettyServerThree() {

        final ResourceConfig rc = new ResourceConfig().packages("org.taxireferral.api");

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("./keystore.jks");
        sslContextFactory.setKeyStorePassword("123456");
        sslContextFactory.setKeyManagerPassword("123456");

//        sslContextFactory.setKeyStorePath("/media/sumeet/data/ataxiRefferal/taxiReferralAPI/keystore.jks");

        return JettyHttpContainerFactory.createServer(URI.create(BASE_URI),sslContextFactory,rc);

    }






    public static Server startJettyTLS()
    {
//        final ResourceConfig rc = new ResourceConfig().packages("org.taxireferral.api");

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9999);
        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("/media/sumeet/data/ataxiRefferal/taxiReferralAPI/keystore.jks");
        sslContextFactory.setKeyStorePassword("123456");
        sslContextFactory.setKeyManagerPassword("123456");
        ServerConnector sslConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(https));
        sslConnector.setPort(9998);
        server.setConnectors(new Connector[] { connector, sslConnector });

        return server;
    }


    void startJettyTLSTwo()
    {
        Server server = new Server();
        // Creating the web application context

//        WebAppContext webapp = new WebAppContext();
//        webapp.setResourceBase("src/main/webapp");
//        server.setHandler(webapp);



        // HTTP Configuration

        HttpConfiguration http = new HttpConfiguration();
        http.addCustomizer(new SecureRequestCustomizer());


        // Configuration for HTTPS redirect
        http.setSecurePort(8443);
        http.setSecureScheme("https");
        ServerConnector connector = new ServerConnector(server);
        connector.addConnectionFactory(new HttpConnectionFactory(http));

        // Setting HTTP port
        connector.setPort(8080);

        // HTTPS configuration
        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        // Configuring SSL
        SslContextFactory sslContextFactory = new SslContextFactory();

        // Defining keystore path and passwords
        sslContextFactory.setKeyStorePath(Main.class.getResource("keystore").toExternalForm());
        sslContextFactory.setKeyStorePassword("javacodegeeks");
        sslContextFactory.setKeyManagerPassword("javacodegeeks");

        // Configuring the connector
        ServerConnector sslConnector = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(https));
        sslConnector.setPort(8443);


        // Setting HTTP and HTTPS connectors
        server.setConnectors(new Connector[]{connector, sslConnector});

        // Starting the Server
//        server.start();
//        server.join();
    }


    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

//        final HttpServer server = startServer();
//        System.out.println(String.format("Jersey app started with WADL available at "
//                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
//        System.in.read();
//        server.stop();


        createDB();
        upgradeTables();

        createTables();
        startJettyServer();


        SimpleServer.main(null);

//        startJettyServerThree();
    }




    public static void createDB()
    {

        Connection conn = null;
        Statement stmt = null;

        try {

            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres"
                    ,JDBCContract.CURRENT_USERNAME
                    ,JDBCContract.CURRENT_PASSWORD);

            stmt = conn.createStatement();

            String createDB = "CREATE DATABASE \"TaxiReferralDB\" WITH ENCODING='UTF8' OWNER=postgres CONNECTION LIMIT=-1";

            stmt.executeUpdate(createDB);

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{


            // close the connection and statement accountApproved

            if(stmt !=null)
            {

                try {
                    stmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


            if(conn!=null)
            {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }





    private static void upgradeTables()
    {

        Connection connection = null;
        Statement statement = null;

        try {

            connection = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
                    ,JDBCContract.CURRENT_USERNAME
                    ,JDBCContract.CURRENT_PASSWORD);

            statement = connection.createStatement();

//            statement.executeUpdate(User.upgradeTableSchema);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{


            // close the connection and statement accountApproved

            if(statement !=null)
            {

                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


            if(connection!=null)
            {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }






    private static void createTables()
    {

        Connection connection = null;
        Statement statement = null;

        try {

            connection = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
                    ,JDBCContract.CURRENT_USERNAME
                    ,JDBCContract.CURRENT_PASSWORD);

            statement = connection.createStatement();


            statement.executeUpdate(User.createTableUsernamesPostgres);
            statement.executeUpdate(Vehicle.createTableVehiclePostgres);
            statement.executeUpdate(TripRequest.createTablePostgres);

            statement.executeUpdate(EmailVerificationCode.createTablePostgres);
            statement.executeUpdate(VehicleType.createTablePostgres);
            statement.executeUpdate(VehicleTypeVersion.createTablePostgres);



            System.out.println("Tables Created ... !");






            // developers Note: whenever adding a table please check that its dependencies are already created.

            // Insert the default administrator if it does not exit


            User admin = new User();
            admin.setUsername("admin");
            admin.setRole(1);
            admin.setPassword("password");

            try
            {
                int rowCount = Globals.daoUserNew.registerUsingUsername(admin,true);

                if(rowCount==1)
                {
                    System.out.println("Admin Account created !");
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }



            // Insert Default Settings

            // Insert Default Service Configuration




            // create directory images

            final java.nio.file.Path BASE_DIR = Paths.get("./images");

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





        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{


            // close the connection and statement accountApproved

            if(statement !=null)
            {

                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


            if(connection!=null)
            {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


}

