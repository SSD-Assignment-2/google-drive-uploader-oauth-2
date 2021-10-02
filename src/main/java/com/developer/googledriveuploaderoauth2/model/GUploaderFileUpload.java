package com.developer.googledriveuploaderoauth2.model;

import org.springframework.web.multipart.MultipartFile;

public class GUploaderFileUpload {

	private MultipartFile multipartFile;

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
}
