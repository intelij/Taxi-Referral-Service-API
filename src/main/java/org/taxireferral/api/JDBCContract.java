package org.taxireferral.api;

import org.taxireferral.api.ModelRoles.Deprecated.Admin;


public class JDBCContract {
	
	private static final String DERBY_EMBEDDED_CONNECTION_URL = "jdbc:derby:sampleDB;create=true";
	private static String DERBY_USERNAME = "ME";
	private static String DERBY_PASSWORD = "MINE";
	

	private static final String POSTGRES_CONNECTION_URL_PREPARED = "jdbc:postgresql://localhost:5432/TaxiReferralDB?prepareThreshold=3";


//	private static final String POSTGRES_CONNECTION_URL = "jdbc:postgresql://localhost:5432/TaxiReferralDB";
//	private static String POSTGRES_USERNAME = "postgres";
//	private static String POSTGRES_PASSWORD = "password";
	
	
//	public static String CURRENT_CONNECTION_URL = POSTGRES_CONNECTION_URL;

//	public static String CURRENT_USERNAME = POSTGRES_USERNAME;
//	public static String CURRENT_PASSWORD = POSTGRES_PASSWORD;


}
