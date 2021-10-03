package com.developer.googledriveuploaderoauth2.impl;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.googledriveuploaderoauth2.constant.GUploaderApplicationConstant;
import com.developer.googledriveuploaderoauth2.service.GUploaderOauthService;
import com.developer.googledriveuploaderoauth2.util.GUploaderApplicationConfig;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.util.store.FileDataStoreFactory;

@Service
public class GUploaderOauthServiceImpl implements GUploaderOauthService {

	private final Logger logger = LoggerFactory.getLogger(GUploaderOauthServiceImpl.class);
	private GoogleAuthorizationCodeFlow codeFlow;
	private FileDataStoreFactory fileDataStoreFactory;

	@Autowired
	private GUploaderApplicationConfig appConfig;

	@PostConstruct
	public void init() throws Exception {
		InputStreamReader inputStreamReader = new InputStreamReader(appConfig.getSecretKeys().getInputStream());
		fileDataStoreFactory = new FileDataStoreFactory(appConfig.getCredentialsFolder().getFile());

		GoogleClientSecrets googleClientSecrets = GoogleClientSecrets.load(GUploaderApplicationConstant.JSON_FACTORY, inputStreamReader);
		codeFlow = new GoogleAuthorizationCodeFlow.Builder(GUploaderApplicationConstant.HTTP_TRANSPORT, GUploaderApplicationConstant.JSON_FACTORY, googleClientSecrets,
				GUploaderApplicationConstant.SCOPES).setDataStoreFactory(fileDataStoreFactory).build();

	}
}
