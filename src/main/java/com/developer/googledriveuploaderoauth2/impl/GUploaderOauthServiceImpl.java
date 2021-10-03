package com.developer.googledriveuploaderoauth2.impl;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

	@Override
	public boolean isUserAuthenticated() throws Exception {
		Credential credential = getCredentials();
		if (credential != null) {
			boolean isValidToken = credential.refreshToken();
			System.out.println("Whether Token is valid, " + isValidToken);
			return isValidToken;
		}
		return false;
	}

	@Override
	public Credential getCredentials() throws IOException {
		return codeFlow.loadCredential(GUploaderApplicationConstant.KEY_FOR_IDENTIFY_USER);
	}

	@Override
	public String authenticateUserThroughGoogle() throws Exception {
		GoogleAuthorizationCodeRequestUrl codeRequestUrl = codeFlow.newAuthorizationUrl();
		String redirectUrl = codeRequestUrl.setRedirectUri(appConfig.getCallbackURI()).setAccessType("offline").build();
		System.out.println("Redirected URL, " + redirectUrl);
		return redirectUrl;
	}
	
	@Override
	public void exchangeAuthenticationCodeForTokens(String code) throws Exception {
		GoogleTokenResponse googleTokenResponse = codeFlow.newTokenRequest(code).setRedirectUri(appConfig.getCallbackURI()).execute();
		codeFlow.createAndStoreCredential(googleTokenResponse, GUploaderApplicationConstant.KEY_FOR_IDENTIFY_USER);
	}

}
	


