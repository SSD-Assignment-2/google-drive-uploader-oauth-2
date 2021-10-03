package com.developer.googledriveuploaderoauth2.service;

import org.springframework.web.multipart.MultipartFile;

public interface GUploaderDriveService {

	void uploadNewFile(MultipartFile multipartFile) throws Exception;
}
