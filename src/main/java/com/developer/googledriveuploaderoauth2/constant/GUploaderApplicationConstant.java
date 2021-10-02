package com.developer.googledriveuploaderoauth2.constant;

import java.util.Collections;
import java.util.List;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;

public class GUploaderApplicationConstant {

	public static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	public static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	public static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

	public static final String PARENT_FOLDER_NAME = "OAuth2 Demo Application Uploaded";
	public static final String APPLICATION_NAME = "SSD Assignment2 GUploader OAuth-2 Application";
	public static final String KEY_FOR_IDENTIFY_USER = "TEST_USER";
}
