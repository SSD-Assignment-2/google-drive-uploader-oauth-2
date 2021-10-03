package com.developer.googledriveuploaderoauth2.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.developer.googledriveuploaderoauth2.constant.GUploaderApplicationConstant;
import com.developer.googledriveuploaderoauth2.service.GUploaderDriveService;
import com.developer.googledriveuploaderoauth2.service.GUploaderOauthService;
import com.developer.googledriveuploaderoauth2.util.GUploaderApplicationConfig;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

@Service
public class GUploaderDriveServiceImpl implements GUploaderDriveService {

	private final Logger logger = LoggerFactory.getLogger(GUploaderDriveServiceImpl.class);

	private Drive driveService;

	@Autowired
	GUploaderOauthService gUploaderOauthService;

	@Autowired
	GUploaderApplicationConfig gUploaderApplicationConfig;

	@PostConstruct
	public void init() throws Exception {
		Credential credential = gUploaderOauthService.getCredentials();
		driveService = new Drive.Builder(GUploaderApplicationConstant.HTTP_TRANSPORT, GUploaderApplicationConstant.JSON_FACTORY, credential)
				.setApplicationName(GUploaderApplicationConstant.APPLICATION_NAME).build();
	}

	@Override
	public void uploadNewFile(MultipartFile multipartFile) throws Exception {
		System.out.println("Implementation for uploading a new file");
        
		String content_Type = multipartFile.getContentType();
		String path = gUploaderApplicationConfig.getTemporaryFolder();
		String file_Name = multipartFile.getOriginalFilename();
		
		java.io.File transfered_File = new java.io.File(path, file_Name);
		multipartFile.transferTo(transfered_File);

		File file_metadata = new File();
		file_metadata.setName(file_Name);
		
		FileContent media_Content = new FileContent(content_Type, transfered_File);
		File file = driveService.files().create(file_metadata, media_Content).setFields("id").execute();

		System.out.println("Uploaded File ID: " + file.getName() + ", " + file.getId());
	}

}
