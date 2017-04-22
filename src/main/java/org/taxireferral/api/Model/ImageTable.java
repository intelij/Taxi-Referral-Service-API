package org.taxireferral.api.Model;

import org.taxireferral.api.ModelRoles.User;

import java.sql.Timestamp;

public class ImageTable {

	// id
	// image_url
	// title
	// description
	// copyright information
	// global_id (concatenation of service_url and id)

	// Table Name for Distributor
	public static final String TABLE_NAME = "IMAGE";

	// Column names

	public static final String IMAGE_ID = "IMAGE_ID";
	public static final String IMAGE_PATH = "IMAGE_PATH";
	public static final String TITLE = "TITLE";

	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String COPYRIGHT_INFORMATION = "COPYRIGHT_INFORMATION";
	public static final String GLOBAL_ID = "GLOBAL_ID";

	public static final String ADDED_BY = "ADDED_BY";
	public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
	public static final String TIMESTAMP_UPDATED = "TIMESTAMP_UPDATED";


	public static final String createTableUsernamesPostgres =

			"CREATE TABLE IF NOT EXISTS "
					+ ImageTable.TABLE_NAME + "("
					+ " " + ImageTable.IMAGE_ID + " SERIAL PRIMARY KEY,"
					+ " " + ImageTable.IMAGE_PATH + " text UNIQUE,"
					+ " " + ImageTable.TITLE + " text ,"

					+ " " + ImageTable.DESCRIPTION + " text ,"
					+ " " + ImageTable.COPYRIGHT_INFORMATION + " text ,"
					+ " " + ImageTable.GLOBAL_ID + " text ,"

					+ " " + ImageTable.ADDED_BY + " int ,"
					+ " " + ImageTable.TIMESTAMP_CREATED + " text timestamp with time zone NOT NULL DEFAULT now(),"
					+ " " + ImageTable.TIMESTAMP_UPDATED + " text   timestamp with time zone NOT NULL DEFAULT now(),"

					+ " FOREIGN KEY(" + ImageTable.ADDED_BY +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL,"

					+ ")";





	// instance variables

	private int imageID;
	private String imagePath;
	private String title;

	private String description;
	private String copyrightInformation;
	private String globalID;

	private int addedBy;
	private Timestamp timestampCreated;
	private Timestamp timestampUpdated;


	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCopyrightInformation() {
		return copyrightInformation;
	}

	public void setCopyrightInformation(String copyrightInformation) {
		this.copyrightInformation = copyrightInformation;
	}

	public String getGlobalID() {
		return globalID;
	}

	public void setGlobalID(String globalID) {
		this.globalID = globalID;
	}

	public int getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(int addedBy) {
		this.addedBy = addedBy;
	}

	public Timestamp getTimestampCreated() {
		return timestampCreated;
	}

	public void setTimestampCreated(Timestamp timestampCreated) {
		this.timestampCreated = timestampCreated;
	}

	public Timestamp getTimestampUpdated() {
		return timestampUpdated;
	}

	public void setTimestampUpdated(Timestamp timestampUpdated) {
		this.timestampUpdated = timestampUpdated;
	}
}
