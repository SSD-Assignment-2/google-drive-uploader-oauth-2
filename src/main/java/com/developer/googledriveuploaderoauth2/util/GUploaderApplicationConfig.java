package com.developer.googledriveuploaderoauth2.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties
public class GUploaderApplicationConfig {

	@Value("${google.oauth.callback.uri}")
	private String CallbackURI;

	@Value("${google.secret.key.path}")
	private Resource secretKeys;

	@Value("${google.credentials.folder.path}")
	private Resource credentialsFolder;

	@Value("${myapp.temp.path}")
	private String tempFolder;

	//getters and setters
	public String getCallbackURI() {
		return CallbackURI;
	}

	public void setCallbackURI(String Callback_URI) {
		CallbackURI = Callback_URI;
	}

	public Resource getSecretKeys() {
		return secretKeys;
	}

	public void setSecretKeys(Resource Secret_Keys) {
		this.secretKeys = Secret_Keys;
	}

	public Resource getCredentialsFolder() {
		return credentialsFolder;
	}

	public void setCredentialsFolder(Resource credentials_Folder) {
		this.credentialsFolder = credentials_Folder;
	}

	public String getTemporaryFolder() {
		return tempFolder;
	}

	public void setTemporaryFolder(String temporary_Folder) {
		this.tempFolder = temporary_Folder;
	}

}


