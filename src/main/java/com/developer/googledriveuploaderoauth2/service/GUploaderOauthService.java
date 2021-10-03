package com.developer.googledriveuploaderoauth2.service;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import com.google.api.client.auth.oauth2.Credential;

public interface GUploaderOauthService {

	boolean isUserAuthenticated() throws Exception;

	Credential getCredentials() throws IOException;

	String authenticateUserThroughGoogle() throws Exception;

	void exchangeAuthenticationCodeForTokens(String authenticateCode) throws Exception;
	
	void deleteUserSession(HttpServletRequest request) throws Exception;
}
